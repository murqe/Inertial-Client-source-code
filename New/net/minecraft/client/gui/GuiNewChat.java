package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wtf.rich.api.event.event.EventReceiveMessage;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.client.features.impl.display.HUD;
import wtf.rich.client.features.impl.visuals.NoRender;

public class GuiNewChat extends Gui {
     private static final Logger LOGGER = LogManager.getLogger();
     private final Minecraft mc;
     private final List sentMessages = Lists.newArrayList();
     private final List chatLines = Lists.newArrayList();
     private final List drawnChatLines = Lists.newArrayList();
     private int scrollPos;
     private boolean isScrolled;
     private float offset;
     private double deltaY;

     public GuiNewChat(Minecraft mcIn) {
          this.mc = mcIn;
     }

     public void drawChat(int updateCounter) {
          this.offset = (float)((double)this.offset + this.deltaY);
          this.deltaY /= Math.pow(0.99D, -Minecraft.frameTime);
          if ((int)this.offset != 0 && (int)this.offset % 8 == 0) {
               this.offset = 8.0F;
          }

          if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
               int i = this.getLineCount();
               int j = this.drawnChatLines.size();
               float f = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;
               if (j > 0) {
                    boolean flag = this.getChatOpen();
                    float f1 = this.getChatScale();
                    int k = MathHelper.ceil((float)this.getChatWidth() / f1);
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(2.0F, 8.0F, 0.0F);
                    GlStateManager.scale(f1, f1, 1.0F);
                    int l = 0;

                    int i1;
                    int j1;
                    int l1;
                    Minecraft var10000;
                    for(i1 = 0; i1 + this.scrollPos < this.drawnChatLines.size() && i1 < i; ++i1) {
                         ChatLine chatline = (ChatLine)this.drawnChatLines.get(i1 + this.scrollPos);
                         if (chatline != null) {
                              j1 = updateCounter - chatline.getUpdatedCounter();
                              if (j1 < 200 || flag) {
                                   double d0 = (double)j1 / 200.0D;
                                   d0 = 1.0D - d0;
                                   d0 *= 10.0D;
                                   d0 = MathHelper.clamp(d0, 0.0D, 1.0D);
                                   d0 *= d0;
                                   l1 = (int)(255.0D * d0);
                                   if (flag) {
                                        l1 = 255;
                                   }

                                   l1 = (int)((float)l1 * f);
                                   ++l;
                                   if (l1 > 3) {
                                        int i2 = false;
                                        int j2 = -i1 * 9;
                                        if (!NoRender.chatRect.getBoolValue()) {
                                             DrawHelper.drawRect(-2.0D, (double)((float)(j2 - 1) - this.offset - HUD.globalOffset / 5.0F), (double)(k + 4), (double)((float)(j2 + 8) - this.offset - HUD.globalOffset / 5.0F), l1 / 2 << 24);
                                        }

                                        String s = chatline.getChatComponent().getFormattedText();
                                        GlStateManager.enableBlend();
                                        var10000 = this.mc;
                                        Minecraft.fontRendererObj.drawStringWithShadow(s, 0.0F, (float)j2 - this.offset - HUD.globalOffset / 5.0F, 16777215 + (l1 << 24));
                                        GlStateManager.disableAlpha();
                                        GlStateManager.disableBlend();
                                   }
                              }
                         }
                    }

                    if (flag) {
                         var10000 = this.mc;
                         i1 = Minecraft.fontRendererObj.FONT_HEIGHT;
                         GlStateManager.translate(-3.0F, 0.0F, 0.0F);
                         int l2 = j * i1 + j;
                         j1 = l * i1 + l;
                         int j3 = this.scrollPos * j1 / j;
                         int k1 = j1 * j1 / l2;
                         if (l2 != j1) {
                              l1 = j3 > 0 ? 170 : 96;
                              int l3 = this.isScrolled ? 13382451 : 3355562;
                              drawRect(0.0D, (double)(-j3), 2.0D, (double)(-j3 - k1), l3 + (l1 << 24));
                              drawRect(2.0D, (double)(-j3), 1.0D, (double)(-j3 - k1), 13421772 + (l1 << 24));
                         }
                    }

                    GlStateManager.popMatrix();
               }
          }

     }

     public void clearChatMessages(boolean p_146231_1_) {
          this.drawnChatLines.clear();
          this.chatLines.clear();
          if (p_146231_1_) {
               this.sentMessages.clear();
          }

     }

     public void printChatMessage(ITextComponent chatComponent) {
          this.deltaY = 1.0D;
          this.offset = 0.0F;
          this.printChatMessageWithOptionalDeletion(chatComponent, 0);
          EventReceiveMessage event = new EventReceiveMessage(chatComponent.getUnformattedText());
          event.call();
     }

     public void printChatMessageWithOptionalDeletion(ITextComponent chatComponent, int chatLineId) {
          this.setChatLine(chatComponent, chatLineId, this.mc.ingameGUI.getUpdateCounter(), false);
          LOGGER.info("[CHAT] {}", chatComponent.getUnformattedText().replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n"));
     }

     private void setChatLine(ITextComponent chatComponent, int chatLineId, int updateCounter, boolean displayOnly) {
          if (chatLineId != 0) {
               this.deleteChatLine(chatLineId);
          }

          int i = MathHelper.floor((float)this.getChatWidth() / this.getChatScale());
          Minecraft var10002 = this.mc;
          List list = GuiUtilRenderComponents.splitText(chatComponent, i, Minecraft.fontRendererObj, false, false);
          boolean flag = this.getChatOpen();

          ITextComponent itextcomponent;
          for(Iterator var8 = list.iterator(); var8.hasNext(); this.drawnChatLines.add(0, new ChatLine(updateCounter, itextcomponent, chatLineId))) {
               itextcomponent = (ITextComponent)var8.next();
               if (flag && this.scrollPos > 0) {
                    this.isScrolled = true;
                    this.scroll(1);
               }
          }

          while(this.drawnChatLines.size() > 100) {
               this.drawnChatLines.remove(this.drawnChatLines.size() - 1);
          }

          if (!displayOnly) {
               this.chatLines.add(0, new ChatLine(updateCounter, chatComponent, chatLineId));

               while(this.chatLines.size() > 100) {
                    this.chatLines.remove(this.chatLines.size() - 1);
               }
          }

     }

     public void refreshChat() {
          this.drawnChatLines.clear();
          this.resetScroll();

          for(int i = this.chatLines.size() - 1; i >= 0; --i) {
               ChatLine chatline = (ChatLine)this.chatLines.get(i);
               this.setChatLine(chatline.getChatComponent(), chatline.getChatLineID(), chatline.getUpdatedCounter(), true);
          }

     }

     public List getSentMessages() {
          return this.sentMessages;
     }

     public void addToSentMessages(String message) {
          if (this.sentMessages.isEmpty() || !((String)this.sentMessages.get(this.sentMessages.size() - 1)).equals(message)) {
               this.sentMessages.add(message);
          }

     }

     public void resetScroll() {
          this.scrollPos = 0;
          this.isScrolled = false;
     }

     public void scroll(int amount) {
          this.scrollPos += amount;
          int i = this.drawnChatLines.size();
          if (this.scrollPos > i - this.getLineCount()) {
               this.scrollPos = i - this.getLineCount();
          }

          if (this.scrollPos <= 0) {
               this.scrollPos = 0;
               this.isScrolled = false;
          }

     }

     @Nullable
     public ITextComponent getChatComponent(int mouseX, int mouseY) {
          if (!this.getChatOpen()) {
               return null;
          } else {
               ScaledResolution scaledresolution = new ScaledResolution(this.mc);
               int i = ScaledResolution.getScaleFactor();
               float f = this.getChatScale();
               int j = mouseX / i - 2;
               int k = mouseY / i - 40;
               j = MathHelper.floor((float)j / f);
               k = MathHelper.floor((float)k / f);
               if (j >= 0 && k >= 0) {
                    int l = Math.min(this.getLineCount(), this.drawnChatLines.size());
                    if (j <= MathHelper.floor((float)this.getChatWidth() / this.getChatScale())) {
                         Minecraft var10001 = this.mc;
                         if (k < Minecraft.fontRendererObj.FONT_HEIGHT * l + l) {
                              var10001 = this.mc;
                              int i1 = k / Minecraft.fontRendererObj.FONT_HEIGHT + this.scrollPos;
                              if (i1 >= 0 && i1 < this.drawnChatLines.size()) {
                                   ChatLine chatline = (ChatLine)this.drawnChatLines.get(i1);
                                   int j1 = 0;
                                   Iterator var12 = chatline.getChatComponent().iterator();

                                   while(var12.hasNext()) {
                                        ITextComponent itextcomponent = (ITextComponent)var12.next();
                                        if (itextcomponent instanceof TextComponentString) {
                                             var10001 = this.mc;
                                             j1 += Minecraft.fontRendererObj.getStringWidth(GuiUtilRenderComponents.removeTextColorsIfConfigured(((TextComponentString)itextcomponent).getText(), false));
                                             if (j1 > j) {
                                                  return itextcomponent;
                                             }
                                        }
                                   }
                              }

                              return null;
                         }
                    }

                    return null;
               } else {
                    return null;
               }
          }
     }

     public boolean getChatOpen() {
          return this.mc.currentScreen instanceof GuiChat;
     }

     public void deleteChatLine(int id) {
          Iterator iterator = this.drawnChatLines.iterator();

          ChatLine chatline1;
          while(iterator.hasNext()) {
               chatline1 = (ChatLine)iterator.next();
               if (chatline1.getChatLineID() == id) {
                    iterator.remove();
               }
          }

          iterator = this.chatLines.iterator();

          while(iterator.hasNext()) {
               chatline1 = (ChatLine)iterator.next();
               if (chatline1.getChatLineID() == id) {
                    iterator.remove();
                    break;
               }
          }

     }

     public int getChatWidth() {
          return calculateChatboxWidth(this.mc.gameSettings.chatWidth);
     }

     public int getChatHeight() {
          return calculateChatboxHeight(this.getChatOpen() ? this.mc.gameSettings.chatHeightFocused : this.mc.gameSettings.chatHeightUnfocused);
     }

     public float getChatScale() {
          return this.mc.gameSettings.chatScale;
     }

     public static int calculateChatboxWidth(float scale) {
          int i = true;
          int j = true;
          return MathHelper.floor(scale * 280.0F + 40.0F);
     }

     public static int calculateChatboxHeight(float scale) {
          int i = true;
          int j = true;
          return MathHelper.floor(scale * 160.0F + 20.0F);
     }

     public int getLineCount() {
          return this.getChatHeight() / 9;
     }
}
