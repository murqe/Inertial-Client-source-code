package wtf.rich.client.features.impl.display;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import optifine.CustomColors;
import wtf.rich.Main;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventRender2D;
import wtf.rich.api.utils.render.AnimationHelper;
import wtf.rich.api.utils.render.ClientHelper;
import wtf.rich.api.utils.world.TimerHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;

public class HUD extends Feature {
     float xd = 0.0F;
     private static FontRenderer font;
     public static String clientName;
     public static float count = 0.0F;
     public TimerHelper timer;
     double potionCheck = 0.0D;
     ScaledResolution sr;
     double width;
     double time;
     float listOffset;
     float yDist;
     int yTotal;
     Feature nextModule;
     int colorArray;
     public static float globalOffset;

     public HUD() {
          super("HUD", "Показывает информацию на экране", 0, Category.DISPLAY);
          this.sr = new ScaledResolution(mc);
          this.width = (double)(this.sr.getScaledWidth() - 2);
          this.time = (double)FeatureList.colortime.getNumberValue();
          this.listOffset = 10.0F;
          this.yDist = 4.0F;
          this.yTotal = 0;
          this.nextModule = null;
          this.colorArray = -1;
     }

     @EventTarget
     public void onRender2D(EventRender2D e) {
          float target = mc.currentScreen instanceof GuiChat ? 15.0F : 0.0F;
          float delta = globalOffset - target;
          globalOffset -= delta / (float)Math.max(1, Minecraft.getDebugFPS()) * 10.0F;
          if (!Double.isFinite((double)globalOffset)) {
               globalOffset = 0.0F;
          }

          if (globalOffset > 15.0F) {
               globalOffset = 15.0F;
          }

          if (globalOffset < 0.0F) {
               globalOffset = 0.0F;
          }

          this.hotBar();
          renderPotionStatus(e.getResolution());
     }

     public void hotBar() {
          if (!Main.instance.featureDirector.getFeatureByClass(Hotbar.class).isToggled()) {
               ScaledResolution sr = new ScaledResolution(mc);
               this.xd = AnimationHelper.animation(this.xd, mc.currentScreen instanceof GuiChat ? (float)(sr.getScaledHeight() - 22) : (float)(sr.getScaledHeight() - 9), 1.0E-4F);
               double prevX = mc.player.posX - mc.player.prevPosX;
               double prevZ = mc.player.posZ - mc.player.prevPosZ;
               double lastDist = Math.sqrt(prevX * prevX + prevZ * prevZ);
               double currSpeed = lastDist * 15.3571428571D;
               String speed = String.format("%.2f blocks/sec", currSpeed);
               NetHandlerPlayClient var12 = (NetHandlerPlayClient)Objects.requireNonNull(mc.getConnection());
               float var10003 = this.xd + -9.0F;
               String cords;
               if (!ClientFont.minecraftfont.getBoolValue()) {
                    cords = "X:" + (int)mc.player.posX + " Y:" + (int)mc.player.posY + " Z:" + (int)mc.player.posZ;
                    ClientHelper.getFontRender().drawStringWithShadow(cords, 3.0D, (double)(var10003 + 10.0F), -1);
                    ClientHelper.getFontRender().drawStringWithShadow("FPS: " + ChatFormatting.GRAY + Minecraft.getDebugFPS(), 2.0D, (double)(this.xd + -18.0F), -1);
                    ClientHelper.getFontRender().drawStringWithShadow(speed, 2.0D, (double)var10003, -1);
               } else {
                    cords = "X:" + (int)mc.player.posX + " Y:" + (int)mc.player.posY + " Z:" + (int)mc.player.posZ;
                    Minecraft var10000 = mc;
                    Minecraft.fontRendererObj.drawStringWithShadow(cords, 3.0F, var10003 + 10.0F, -1);
                    var10000 = mc;
                    Minecraft.fontRendererObj.drawStringWithShadow("FPS: " + ChatFormatting.GRAY + Minecraft.getDebugFPS(), 2.0F, this.xd + -18.0F, -1);
                    var10000 = mc;
                    Minecraft.fontRendererObj.drawStringWithShadow(speed, 2.0F, var10003, -1);
               }
          }

     }

     public static void renderPotionStatus(ScaledResolution scaledResolution) {
          float offset = globalOffset;
          float pY = -2.0F;
          List potions = new ArrayList(mc.player.getActivePotionEffects());
          potions.sort(Comparator.comparingDouble((effectx) -> {
               Minecraft var10000 = mc;
               return (double)Minecraft.fontRendererObj.getStringWidth(((Potion)Objects.requireNonNull(Potion.getPotionById(CustomColors.getPotionId(effectx.getEffectName())))).getName());
          }));

          for(Iterator var4 = potions.iterator(); var4.hasNext(); pY -= 11.0F) {
               PotionEffect effect = (PotionEffect)var4.next();
               Potion potion = Potion.getPotionById(CustomColors.getPotionId(effect.getEffectName()));

               assert potion != null;

               String name = I18n.format(potion.getName());
               String PType = "";
               if (effect.getAmplifier() == 1) {
                    name = name + " 2";
               } else if (effect.getAmplifier() == 2) {
                    name = name + " 3";
               } else if (effect.getAmplifier() == 3) {
                    name = name + " 4";
               }

               if (effect.getDuration() < 600 && effect.getDuration() > 300) {
                    PType = PType + " " + Potion.getDurationString(effect);
               } else if (effect.getDuration() < 300) {
                    PType = PType + " " + Potion.getDurationString(effect);
               } else if (effect.getDuration() > 600) {
                    PType = PType + " " + Potion.getDurationString(effect);
               }

               int getPotionColor = -1;
               if (effect.getDuration() < 200) {
                    getPotionColor = (new Color(215, 59, 59)).getRGB();
               } else if (effect.getDuration() < 400) {
                    getPotionColor = (new Color(231, 143, 32)).getRGB();
               } else if (effect.getDuration() > 400) {
                    getPotionColor = (new Color(172, 171, 171)).getRGB();
               }

               if (!ClientFont.minecraftfont.getBoolValue()) {
                    Minecraft var10000 = mc;
                    FontRenderer var10 = Minecraft.fontRendererObj;
                    int var10002 = scaledResolution.getScaledWidth();
                    Minecraft var10003 = mc;
                    var10.drawStringWithShadow(name, (float)(var10002 - Minecraft.fontRendererObj.getStringWidth(name + PType) - 3), (float)(scaledResolution.getScaledHeight() - 28) + pY - offset, potion.getLiquidColor());
                    var10000 = mc;
                    var10 = Minecraft.fontRendererObj;
                    var10002 = scaledResolution.getScaledWidth();
                    var10003 = mc;
                    var10.drawStringWithShadow(PType, (float)(var10002 - Minecraft.fontRendererObj.getStringWidth(PType) - 2), (float)(scaledResolution.getScaledHeight() - 28) + pY - offset, getPotionColor);
               } else {
                    ClientHelper.getFontRender().drawStringWithShadow(name, (double)(scaledResolution.getScaledWidth() - ClientHelper.getFontRender().getStringWidth(name + PType) - 3), (double)((float)(scaledResolution.getScaledHeight() - 28) + pY - offset), potion.getLiquidColor());
                    ClientHelper.getFontRender().drawStringWithShadow(PType, (double)(scaledResolution.getScaledWidth() - ClientHelper.getFontRender().getStringWidth(PType) - 2), (double)((float)(scaledResolution.getScaledHeight() - 28) + pY - offset), getPotionColor);
               }
          }

     }

     private static Feature getNextEnabledModule(ArrayList features, int startingIndex) {
          for(int i = startingIndex; i < features.size(); ++i) {
               Feature feature = (Feature)features.get(i);
               if (feature.isToggled() && !feature.getModuleName().equals("ClickGui")) {
                    return feature;
               }
          }

          return null;
     }

     public static int rainbow(int delay, long index) {
          double rainbowState = Math.ceil((double)(System.currentTimeMillis() + index + (long)delay)) / 15.0D;
          rainbowState %= 360.0D;
          return Color.getHSBColor((float)(rainbowState / 360.0D), 0.4F, 1.0F).getRGB();
     }
}
