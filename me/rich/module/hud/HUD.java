package me.rich.module.hud;

import de.Hero.settings.Setting;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventRender2D;
import me.rich.font.Fonts;
import me.rich.helpers.render.AnimationHelper;
import me.rich.helpers.render.ColorUtils;
import me.rich.helpers.render.RenderUtil;
import me.rich.helpers.render.Translate;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.module.FeatureDirector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class HUD extends Feature {
      float xd = 0.0F;
      public static String clientName;
      public static float count = 0.0F;
      int category;
      public static float globalOffset;

      public HUD() {
            super("HUD", 0, Category.HUD);
            Main var10000 = Main.instance;
            Main.settingsManager.rSetting(new Setting("Info", this, false));
      }

      @EventTarget
      public void onRender2D(EventRender2D e) {
            float target = mc.currentScreen instanceof GuiChat ? 15.0F : 0.0F;
            float delta = globalOffset - target;
            Minecraft var10003 = mc;
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

            new ScaledResolution(mc);
            this.hotBar();
      }

      public void hotBar() {
            if (Main.settingsManager.getSettingByName(Main.moduleManager.getModule(HUD.class), "Info").getValBoolean()) {
                  ScaledResolution sr = new ScaledResolution(mc);
                  this.xd = AnimationHelper.animation(this.xd, mc.currentScreen instanceof GuiChat ? (float)(sr.getScaledHeight() - 22) : (float)(sr.getScaledHeight() - 9), 1.0E-4F);
                  Minecraft var10000 = mc;
                  Minecraft var10001 = mc;
                  double prevX = Minecraft.player.posX - Minecraft.player.prevPosX;
                  var10000 = mc;
                  var10001 = mc;
                  double prevZ = Minecraft.player.posZ - Minecraft.player.prevPosZ;
                  double lastDist = Math.sqrt(prevX * prevX + prevZ * prevZ);
                  double currSpeed = lastDist * 15.3571428571D;
                  String speed = String.format("%.2f blocks/sec", currSpeed);
                  NetHandlerPlayClient var12 = (NetHandlerPlayClient)Objects.requireNonNull(mc.getConnection());
                  var10001 = mc;
                  int ping = var12.getPlayerInfo(Minecraft.player.getUniqueID()).getResponseTime();
                  Minecraft var10000 = mc;
                  FontRenderer var13 = Minecraft.fontRendererObj;
                  StringBuilder var14 = new StringBuilder();
                  Minecraft var10002 = mc;
                  var14 = var14.append(Math.round(Minecraft.player.posX)).append(" ");
                  var10002 = mc;
                  var14 = var14.append(Math.round(Minecraft.player.posY)).append(" ");
                  var10002 = mc;
                  var13.drawStringWithShadow(var14.append(Math.round(Minecraft.player.posZ)).toString(), 2.0F, this.xd, -1);
                  Fonts.sfui18.drawStringWithShadow("FPS: " + Minecraft.getDebugFPS(), 2.0D, (double)(this.xd + -18.0F), ColorUtils.astolfoColors1(0.0F, 0.0F));
                  float var10003 = this.xd + -9.0F;
                  Fonts.sfui18.drawStringWithShadow(speed, 2.0D, (double)var10003, -1);
                  Fonts.sfui20.drawStringWithShadow("Free version", (double)((float)(sr.getScaledWidth() - Fonts.sfui20.getStringWidth("Free version"))), (double)this.xd, ColorUtils.astolfoColors1(0.0F, 0.0F));
            }

      }

      public void renderArrayList(ScaledResolution scaledResolution) {
            Main var10000 = Main.instance;
            if (Main.moduleManager.getModule(ArreyList.class).isToggled()) {
                  double width = (double)(scaledResolution.getScaledWidth() - 2);
                  float[] var54 = new float[]{0.125F, 0.125F, 0.125F};
                  var10000 = Main.instance;
                  if (Main.moduleManager.getModule(ArreyList.class).isToggled()) {
                        Main var10002 = Main.instance;
                        FeatureDirector var55 = Main.moduleManager;
                        ArrayList sortedList = new ArrayList(FeatureDirector.getModules());
                        List enabledModules = (List)sortedList.stream().filter(Feature::isToggled).sorted(Comparator.comparingDouble((e) -> {
                              return (double)(-Fonts.roboto_18.getStringWidth(e.getModuleName()));
                        })).collect(Collectors.toList());
                        float yDist = 4.0F;
                        int yTotal = 0;

                        int i;
                        for(i = 0; i < enabledModules.size(); ++i) {
                              yTotal += Fonts.roboto_18.getHeight() + 111;
                        }

                        i = 0;

                        for(int sortedListSize = enabledModules.size(); i < sortedListSize; ++i) {
                              Feature module = (Feature)enabledModules.get(i);
                              if (!module.getModuleName().equals("ClickGui")) {
                                    Translate translate = module.getTranslate();
                                    String moduleLabel = module.getModuleName();
                                    float listOffset = 10.0F;
                                    float length = (float)Fonts.roboto_18.getStringWidth(moduleLabel);
                                    float featureX = (float)(width - (double)length);
                                    boolean enable = module.isToggled() && module.isToggled();
                                    if (enable) {
                                          translate.interpolate(featureX, yDist, 6.05D * Minecraft.frameTime / 5.0D);
                                    } else {
                                          translate.interpolate((float)(width + 200.0D), yDist, 0.05D * Minecraft.frameTime / 5.0D);
                                    }

                                    label116: {
                                          Minecraft var48 = mc;
                                          double var19;
                                          if (!Minecraft.player.isPotionActive(MobEffects.SPEED)) {
                                                var48 = mc;
                                                if (!Minecraft.player.isPotionActive(MobEffects.STRENGTH)) {
                                                      var48 = mc;
                                                      if (!Minecraft.player.isPotionActive(MobEffects.FIRE_RESISTANCE)) {
                                                            var19 = 0.0D;
                                                            break label116;
                                                      }
                                                }
                                          }

                                          var19 = 24.0D;
                                    }

                                    double translateX = (double)translate.getX() - 2.5D;
                                    double translateY = (double)translate.getY();
                                    int color = 0;
                                    double d = ArreyList.red1.getValDouble();
                                    double d1 = ArreyList.green1.getValDouble();
                                    double d2 = ArreyList.blue1.getValDouble();
                                    double f = ArreyList.red2.getValDouble();
                                    double f1 = ArreyList.green2.getValDouble();
                                    double f2 = (double)ArreyList.blue2.getValInt();
                                    double time = ArreyList.time.getValDouble();
                                    String mode = Main.settingsManager.getSettingByName("ArrayList Color").getValString();
                                    String var40 = mode.toLowerCase();
                                    byte var41 = -1;
                                    switch(var40.hashCode()) {
                                    case -1349088399:
                                          if (var40.equals("custom")) {
                                                var41 = 6;
                                          }
                                          break;
                                    case -832025578:
                                          if (var40.equals("red-blue")) {
                                                var41 = 7;
                                          }
                                          break;
                                    case -703561496:
                                          if (var40.equals("astolfo")) {
                                                var41 = 2;
                                          }
                                          break;
                                    case -316348666:
                                          if (var40.equals("greenwhite")) {
                                                var41 = 1;
                                          }
                                          break;
                                    case -150969892:
                                          if (var40.equals("yellastolfo")) {
                                                var41 = 5;
                                          }
                                          break;
                                    case 3387192:
                                          if (var40.equals("none")) {
                                                var41 = 9;
                                          }
                                          break;
                                    case 98615627:
                                          if (var40.equals("grape")) {
                                                var41 = 8;
                                          }
                                          break;
                                    case 107027353:
                                          if (var40.equals("pulse")) {
                                                var41 = 4;
                                          }
                                          break;
                                    case 973576630:
                                          if (var40.equals("rainbow")) {
                                                var41 = 0;
                                          }
                                    }

                                    switch(var41) {
                                    case 0:
                                          color = ColorUtils.rainbowNew((int)(yDist * 200.0F * 0.1F), 0.8F, 1.0F);
                                          break;
                                    case 1:
                                          color = ColorUtils.TwoColoreffect(new Color(255, 255, 255), new Color(0, 150, 150), (double)Math.abs(System.currentTimeMillis() / 10L) / 100.0D + 3.0D * (double)yDist * 2.55D / 60.0D).getRGB();
                                          break;
                                    case 2:
                                          color = ColorUtils.astolfoColors((int)yDist, yTotal);
                                    case 3:
                                    default:
                                          break;
                                    case 4:
                                          color = ColorUtils.TwoColoreffect(new Color(0, 255, 255), new Color(0, 150, 255), (double)Math.abs(System.currentTimeMillis() / 10L) / 100.0D + 3.0D * (double)yDist * 2.55D / 60.0D).getRGB();
                                          break;
                                    case 5:
                                          color = ColorUtils.Yellowastolfo((int)yDist, (float)yTotal);
                                          break;
                                    case 6:
                                          color = ColorUtils.TwoColoreffect(new Color((int)d, (int)d1, (int)d2), new Color((int)f, (int)f1, (int)f2), (double)Math.abs(System.currentTimeMillis() / (long)time) / 100.0D + 3.0D * (double)yDist * 2.55D / 60.0D).getRGB();
                                          break;
                                    case 7:
                                          color = ColorUtils.TwoColoreffect(new Color(255, 25, 25), new Color(0, 150, 255), (double)Math.abs(System.currentTimeMillis() / 20L) / 100.0D + 3.0D * (double)yDist * 2.55D / 60.0D).getRGB();
                                          break;
                                    case 8:
                                          color = ColorUtils.TwoColoreffect(new Color(155, 55, 255), new Color(155, 100, 200), (double)Math.abs(System.currentTimeMillis() / 10L) / 100.0D + 0.1275D).getRGB();
                                          break;
                                    case 9:
                                          color = -1;
                                    }

                                    int back2 = (int)Main.settingsManager.getSettingByName("BackgroundBright").getValDouble();
                                    int nextIndex = enabledModules.indexOf(module) + 1;
                                    if (ArreyList.backGround.getValBoolean()) {
                                          RenderUtil.drawNewRect(translateX - 2.0D, translateY - 1.0D, width, translateY + (double)listOffset - 1.0D, ColorUtils.getColor(back2));
                                    }

                                    if (ArreyList.rectRight.getValBoolean()) {
                                          double rightCheck = ArreyList.border.getValBoolean() ? 9.5D : 9.0D;
                                          RenderUtil.drawNewRect(width, translateY - 1.0D, width + 1.0D, translateY + rightCheck, color);
                                    }

                                    Feature nextModule = null;
                                    if (enabledModules.size() > nextIndex) {
                                          nextModule = getNextEnabledModule((ArrayList)enabledModules, nextIndex);
                                    }

                                    if (ArreyList.border.getValBoolean()) {
                                          RenderUtil.drawNewRect(translateX - 2.6D, translateY - 1.0D, translateX - 2.0D, translateY + (double)listOffset - 1.0D, color);
                                    }

                                    double offsetY = (double)listOffset;
                                    if (nextModule != null) {
                                          double dif = (double)(length - (float)Fonts.roboto_18.getStringWidth(nextModule.getModuleName()));
                                          if (ArreyList.border.getValBoolean()) {
                                                RenderUtil.drawNewRect(translateX - 2.6D, translateY + offsetY - 1.0D, translateX - 2.6D + dif, translateY + offsetY - 0.6D, color);
                                          }
                                    } else if (ArreyList.border.getValBoolean()) {
                                          RenderUtil.drawNewRect(translateX - 2.6D, translateY + offsetY - 1.0D, width, translateY + offsetY - 0.6D, color);
                                    }

                                    Fonts.roboto_18.drawStringWithShadow(moduleLabel, (double)((float)translateX), (double)((float)translateY + 1.0F), color);
                                    if (module.isToggled()) {
                                          yDist += listOffset;
                                    }
                              }
                        }
                  }
            }

      }

      private static Feature getNextEnabledModule(ArrayList modules, int startingIndex) {
            int i = startingIndex;

            for(int modulesSize = modules.size(); i < modulesSize; ++i) {
                  Feature module = (Feature)modules.get(i);
                  if (module.isToggled() && module.getModuleName() != "ClickGui") {
                        return module;
                  }
            }

            return null;
      }

      public static int rainbow(int delay, long index) {
            double rainbowState = Math.ceil((double)(System.currentTimeMillis() + index + (long)delay)) / 15.0D;
            rainbowState %= 360.0D;
            return Color.getHSBColor((float)(rainbowState / 360.0D), 0.4F, 1.0F).getRGB();
      }

      private void renderPotions(ScaledResolution scaledResolution) {
            int xd = mc.currentScreen instanceof GuiChat ? scaledResolution.getScaledHeight() - 30 : scaledResolution.getScaledHeight() - 20;
            int potionY = 11;
            Minecraft var10002 = mc;
            new ArrayList(Minecraft.player.getActivePotionEffects());
            List potions = new ArrayList();
            Minecraft var10000 = mc;
            Iterator var6 = Minecraft.player.getActivePotionEffects().iterator();

            while(var6.hasNext()) {
                  Object o = var6.next();
                  potions.add((PotionEffect)o);
            }

            potions.sort(Comparator.comparingDouble((effect) -> {
                  return (double)Fonts.roboto_18.getStringWidth(effect.getPotion().getName() + effect.getAmplifier() + Potion.getPotionDurationString(effect, 1.0F));
            }));

            for(var6 = potions.iterator(); var6.hasNext(); potionY += 11) {
                  PotionEffect potionEffect = (PotionEffect)var6.next();
                  String effectName = I18n.format(potionEffect.getPotion().getName());
                  if (potionEffect.getAmplifier() == 1) {
                        effectName = effectName + " " + I18n.format("enchantment.level.2");
                  } else if (potionEffect.getAmplifier() == 2) {
                        effectName = effectName + " " + I18n.format("enchantment.level.3");
                  } else if (potionEffect.getAmplifier() == 3) {
                        effectName = effectName + " " + I18n.format("enchantment.level.4");
                  }

                  String finalName = effectName + "§7 " + Potion.getPotionDurationString(potionEffect, 1.0F);
                  float x = (float)(scaledResolution.getScaledWidth() - Fonts.roboto_18.getStringWidth(finalName) - 4);
                  float y2 = (float)(xd - potionY);
                  Fonts.roboto_18.drawStringWithShadow(finalName, (double)x, (double)y2, potionEffect.getPotion().getLiquidColor());
            }

      }
}
