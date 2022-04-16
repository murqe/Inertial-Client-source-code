package net.minecraft.client.gui;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.mojang.authlib.GameProfile;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.IScoreCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;
import wtf.rich.Main;
import wtf.rich.client.features.impl.visuals.NameProtect;

public class GuiPlayerTabOverlay extends Gui {
     public static Ordering ENTRY_ORDERING = Ordering.from(new GuiPlayerTabOverlay.PlayerComparator());
     private final Minecraft mc;
     private final GuiIngame guiIngame;
     private ITextComponent footer;
     private ITextComponent header;
     private long lastTimeOpened;
     private boolean isBeingRendered;

     public GuiPlayerTabOverlay(Minecraft mcIn, GuiIngame guiIngameIn) {
          this.mc = mcIn;
          this.guiIngame = guiIngameIn;
     }

     public static String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn) {
          return networkPlayerInfoIn.getDisplayName() != null ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName(networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
     }

     public void updatePlayerList(boolean willBeRendered) {
          if (willBeRendered && !this.isBeingRendered) {
               this.lastTimeOpened = Minecraft.getSystemTime();
          }

          this.isBeingRendered = willBeRendered;
     }

     public void renderPlayerlist(int width, Scoreboard scoreboardIn, @Nullable ScoreObjective scoreObjectiveIn) {
          NetHandlerPlayClient nethandlerplayclient = this.mc.player.connection;
          List list = ENTRY_ORDERING.sortedCopy(nethandlerplayclient.getPlayerInfoMap());
          int i = 0;
          int j = 0;
          Iterator var8 = list.iterator();

          int j4;
          Minecraft var10000;
          while(var8.hasNext()) {
               NetworkPlayerInfo networkplayerinfo = (NetworkPlayerInfo)var8.next();
               var10000 = this.mc;
               j4 = Minecraft.fontRendererObj.getStringWidth(getPlayerName(networkplayerinfo));
               i = Math.max(i, j4);
               if (scoreObjectiveIn != null && scoreObjectiveIn.getRenderType() != IScoreCriteria.EnumRenderType.HEARTS) {
                    var10000 = this.mc;
                    j4 = Minecraft.fontRendererObj.getStringWidth(" " + scoreboardIn.getOrCreateScore(networkplayerinfo.getGameProfile().getName(), scoreObjectiveIn).getScorePoints());
                    j = Math.max(j, j4);
               }
          }

          list = list.subList(0, Math.min(list.size(), 80));
          int l3 = list.size();
          int i4 = l3;

          for(j4 = 1; i4 > 20; i4 = (l3 + j4 - 1) / j4) {
               ++j4;
          }

          boolean flag = this.mc.isIntegratedServerRunning() || this.mc.getConnection().getNetworkManager().isEncrypted();
          int l;
          if (scoreObjectiveIn != null) {
               if (scoreObjectiveIn.getRenderType() == IScoreCriteria.EnumRenderType.HEARTS) {
                    l = 90;
               } else {
                    l = j;
               }
          } else {
               l = 0;
          }

          int i1 = Math.min(j4 * ((flag ? 9 : 0) + i + l + 13), width - 50) / j4;
          int j1 = width / 2 - (i1 * j4 + (j4 - 1) * 5) / 2;
          int k1 = 10;
          int l1 = i1 * j4 + (j4 - 1) * 5;
          List list1 = null;
          Minecraft var10001;
          if (this.header != null) {
               var10000 = this.mc;
               list1 = Minecraft.fontRendererObj.listFormattedStringToWidth(this.header.getFormattedText(), width - 50);

               String s;
               for(Iterator var18 = list1.iterator(); var18.hasNext(); l1 = Math.max(l1, Minecraft.fontRendererObj.getStringWidth(s))) {
                    s = (String)var18.next();
                    var10001 = this.mc;
               }
          }

          List list2 = null;
          String s3;
          Iterator var38;
          if (this.footer != null) {
               var10000 = this.mc;
               list2 = Minecraft.fontRendererObj.listFormattedStringToWidth(this.footer.getFormattedText(), width - 50);

               for(var38 = list2.iterator(); var38.hasNext(); l1 = Math.max(l1, Minecraft.fontRendererObj.getStringWidth(s3))) {
                    s3 = (String)var38.next();
                    var10001 = this.mc;
               }
          }

          double var10002;
          int var10004;
          Minecraft var10005;
          int j5;
          double var35;
          double var36;
          if (list1 != null) {
               var35 = (double)(width / 2 - l1 / 2 - 1);
               var36 = (double)(k1 - 1);
               var10002 = (double)(width / 2 + l1 / 2 + 1);
               var10004 = list1.size();
               var10005 = this.mc;
               drawRect(var35, var36, var10002, (double)(k1 + var10004 * Minecraft.fontRendererObj.FONT_HEIGHT), Integer.MIN_VALUE);

               for(var38 = list1.iterator(); var38.hasNext(); k1 += Minecraft.fontRendererObj.FONT_HEIGHT) {
                    s3 = (String)var38.next();
                    var10000 = this.mc;
                    j5 = Minecraft.fontRendererObj.getStringWidth(s3);
                    var10000 = this.mc;
                    Minecraft.fontRendererObj.drawStringWithShadow(s3, (float)(width / 2 - j5 / 2), (float)k1, -1);
                    var10001 = this.mc;
               }

               ++k1;
          }

          drawRect((double)(width / 2 - l1 / 2 - 1), (double)(k1 - 1), (double)(width / 2 + l1 / 2 + 1), (double)(k1 + i4 * 9), Integer.MIN_VALUE);

          for(int k4 = 0; k4 < l3; ++k4) {
               int l4 = k4 / i4;
               j5 = k4 % i4;
               int j2 = j1 + l4 * i1 + l4 * 5;
               int k2 = k1 + j5 * 9;
               drawRect((double)j2, (double)k2, (double)(j2 + i1), (double)(k2 + 8), 553648127);
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               GlStateManager.enableAlpha();
               GlStateManager.enableBlend();
               GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
               if (k4 < list.size()) {
                    NetworkPlayerInfo networkplayerinfo1 = (NetworkPlayerInfo)list.get(k4);
                    GameProfile gameprofile = networkplayerinfo1.getGameProfile();
                    String s4 = getPlayerName(networkplayerinfo1);
                    if (Main.instance.featureDirector.getFeatureByClass(NameProtect.class).isToggled()) {
                         if (NameProtect.tabSpoof.getBoolValue() && !s4.contains(this.mc.player.getName())) {
                              s4 = "§aProtected";
                         }

                         if (Main.instance.featureDirector.getFeatureByClass(NameProtect.class).isToggled() && NameProtect.otherNames.getBoolValue()) {
                              s4 = s4.replace(this.mc.player.getName(), ChatFormatting.GREEN + "Protected");
                         }
                    }

                    if (flag) {
                         EntityPlayer entityplayer = this.mc.world.getPlayerEntityByUUID(gameprofile.getId());
                         boolean flag1 = entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.CAPE) && ("Dinnerbone".equals(gameprofile.getName()) || "Grumm".equals(gameprofile.getName()));
                         this.mc.getTextureManager().bindTexture(networkplayerinfo1.getLocationSkin());
                         int l2 = 8 + (flag1 ? 8 : 0);
                         int i3 = 8 * (flag1 ? -1 : 1);
                         Gui.drawScaledCustomSizeModalRect(j2, k2, 8.0F, (float)l2, 8, i3, 8, 8, 64.0F, 64.0F);
                         if (entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.HAT)) {
                              int j3 = 8 + (flag1 ? 8 : 0);
                              int k3 = 8 * (flag1 ? -1 : 1);
                              Gui.drawScaledCustomSizeModalRect(j2, k2, 40.0F, (float)j3, 8, k3, 8, 8, 64.0F, 64.0F);
                         }

                         j2 += 9;
                    }

                    if (networkplayerinfo1.getGameType() == GameType.SPECTATOR) {
                         var10000 = this.mc;
                         Minecraft.fontRendererObj.drawStringWithShadow(TextFormatting.ITALIC + s4, (float)j2, (float)k2, -1862270977);
                    } else {
                         var10000 = this.mc;
                         Minecraft.fontRendererObj.drawStringWithShadow(s4, (float)j2, (float)k2, -1);
                    }

                    if (scoreObjectiveIn != null && networkplayerinfo1.getGameType() != GameType.SPECTATOR) {
                         int k5 = j2 + i + 1;
                         int l5 = k5 + l;
                         if (l5 - k5 > 5) {
                              this.drawScoreboardValues(scoreObjectiveIn, k2, gameprofile.getName(), k5, l5, networkplayerinfo1);
                         }
                    }

                    this.drawPing(i1, j2 - (flag ? 9 : 0), k2, networkplayerinfo1);
               }
          }

          if (list2 != null) {
               k1 = k1 + i4 * 9 + 1;
               var35 = (double)(width / 2 - l1 / 2 - 1);
               var36 = (double)(k1 - 1);
               var10002 = (double)(width / 2 + l1 / 2 + 1);
               var10004 = list2.size();
               var10005 = this.mc;
               drawRect(var35, var36, var10002, (double)(k1 + var10004 * Minecraft.fontRendererObj.FONT_HEIGHT), Integer.MIN_VALUE);

               for(var38 = list2.iterator(); var38.hasNext(); k1 += Minecraft.fontRendererObj.FONT_HEIGHT) {
                    s3 = (String)var38.next();
                    var10000 = this.mc;
                    j5 = Minecraft.fontRendererObj.getStringWidth(s3);
                    var10000 = this.mc;
                    Minecraft.fontRendererObj.drawStringWithShadow(s3, (float)(width / 2 - j5 / 2), (float)k1, -1);
                    var10001 = this.mc;
               }
          }

     }

     protected void drawPing(int p_175245_1_, int p_175245_2_, int p_175245_3_, NetworkPlayerInfo networkPlayerInfoIn) {
          GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
          this.mc.getTextureManager().bindTexture(ICONS);
          int i = false;
          byte j;
          if (networkPlayerInfoIn.getResponseTime() < 0) {
               j = 5;
          } else if (networkPlayerInfoIn.getResponseTime() < 150) {
               j = 0;
          } else if (networkPlayerInfoIn.getResponseTime() < 300) {
               j = 1;
          } else if (networkPlayerInfoIn.getResponseTime() < 600) {
               j = 2;
          } else if (networkPlayerInfoIn.getResponseTime() < 1000) {
               j = 3;
          } else {
               j = 4;
          }

          this.zLevel += 100.0F;
          this.drawTexturedModalRect(p_175245_2_ + p_175245_1_ - 11, p_175245_3_, 0, 176 + j * 8, 10, 8);
          this.zLevel -= 100.0F;
     }

     private void drawScoreboardValues(ScoreObjective objective, int p_175247_2_, String name, int p_175247_4_, int p_175247_5_, NetworkPlayerInfo info) {
          int i = objective.getScoreboard().getOrCreateScore(name, objective).getScorePoints();
          Minecraft var10000;
          Minecraft var10003;
          if (objective.getRenderType() == IScoreCriteria.EnumRenderType.HEARTS) {
               this.mc.getTextureManager().bindTexture(ICONS);
               if (this.lastTimeOpened == info.getRenderVisibilityId()) {
                    if (i < info.getLastHealth()) {
                         info.setLastHealthTime(Minecraft.getSystemTime());
                         info.setHealthBlinkTime((long)(this.guiIngame.getUpdateCounter() + 20));
                    } else if (i > info.getLastHealth()) {
                         info.setLastHealthTime(Minecraft.getSystemTime());
                         info.setHealthBlinkTime((long)(this.guiIngame.getUpdateCounter() + 10));
                    }
               }

               if (Minecraft.getSystemTime() - info.getLastHealthTime() > 1000L || this.lastTimeOpened != info.getRenderVisibilityId()) {
                    info.setLastHealth(i);
                    info.setDisplayHealth(i);
                    info.setLastHealthTime(Minecraft.getSystemTime());
               }

               info.setRenderVisibilityId(this.lastTimeOpened);
               info.setLastHealth(i);
               int j = MathHelper.ceil((float)Math.max(i, info.getDisplayHealth()) / 2.0F);
               int k = Math.max(MathHelper.ceil((float)(i / 2)), Math.max(MathHelper.ceil((float)(info.getDisplayHealth() / 2)), 10));
               boolean flag = info.getHealthBlinkTime() > (long)this.guiIngame.getUpdateCounter() && (info.getHealthBlinkTime() - (long)this.guiIngame.getUpdateCounter()) / 3L % 2L == 1L;
               if (j > 0) {
                    float f = Math.min((float)(p_175247_5_ - p_175247_4_ - 4) / (float)k, 9.0F);
                    if (f > 3.0F) {
                         int j1;
                         for(j1 = j; j1 < k; ++j1) {
                              this.drawTexturedModalRect((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, flag ? 25 : 16, 0, 9, 9);
                         }

                         for(j1 = 0; j1 < j; ++j1) {
                              this.drawTexturedModalRect((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, flag ? 25 : 16, 0, 9, 9);
                              if (flag) {
                                   if (j1 * 2 + 1 < info.getDisplayHealth()) {
                                        this.drawTexturedModalRect((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, 70, 0, 9, 9);
                                   }

                                   if (j1 * 2 + 1 == info.getDisplayHealth()) {
                                        this.drawTexturedModalRect((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, 79, 0, 9, 9);
                                   }
                              }

                              if (j1 * 2 + 1 < i) {
                                   this.drawTexturedModalRect((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, j1 >= 10 ? 160 : 52, 0, 9, 9);
                              }

                              if (j1 * 2 + 1 == i) {
                                   this.drawTexturedModalRect((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, j1 >= 10 ? 169 : 61, 0, 9, 9);
                              }
                         }
                    } else {
                         float f1 = MathHelper.clamp((float)i / 20.0F, 0.0F, 1.0F);
                         int i1 = (int)((1.0F - f1) * 255.0F) << 16 | (int)(f1 * 255.0F) << 8;
                         String s = "" + (float)i / 2.0F;
                         Minecraft var10001 = this.mc;
                         if (p_175247_5_ - Minecraft.fontRendererObj.getStringWidth(s + "hp") >= p_175247_4_) {
                              s = s + "hp";
                         }

                         var10000 = this.mc;
                         int var10002 = (p_175247_5_ + p_175247_4_) / 2;
                         var10003 = this.mc;
                         Minecraft.fontRendererObj.drawStringWithShadow(s, (float)(var10002 - Minecraft.fontRendererObj.getStringWidth(s) / 2), (float)p_175247_2_, i1);
                    }
               }
          } else {
               String s1 = TextFormatting.YELLOW + "" + i;
               var10000 = this.mc;
               var10003 = this.mc;
               Minecraft.fontRendererObj.drawStringWithShadow(s1, (float)(p_175247_5_ - Minecraft.fontRendererObj.getStringWidth(s1)), (float)p_175247_2_, 16777215);
          }

     }

     public void setFooter(@Nullable ITextComponent footerIn) {
          this.footer = footerIn;
     }

     public void setHeader(@Nullable ITextComponent headerIn) {
          this.header = headerIn;
     }

     public void resetFooterHeader() {
          this.header = null;
          this.footer = null;
     }

     public static class PlayerComparator implements Comparator {
          public int compare(NetworkPlayerInfo p_compare_1_, NetworkPlayerInfo p_compare_2_) {
               ScorePlayerTeam scoreplayerteam = p_compare_1_.getPlayerTeam();
               ScorePlayerTeam scoreplayerteam1 = p_compare_2_.getPlayerTeam();
               return ComparisonChain.start().compareTrueFirst(p_compare_1_.getGameType() != GameType.SPECTATOR, p_compare_2_.getGameType() != GameType.SPECTATOR).compare(scoreplayerteam != null ? scoreplayerteam.getRegisteredName() : "", scoreplayerteam1 != null ? scoreplayerteam1.getRegisteredName() : "").compare(p_compare_1_.getGameProfile().getName(), p_compare_2_.getGameProfile().getName()).result();
          }
     }
}
