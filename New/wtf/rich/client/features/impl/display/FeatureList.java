package wtf.rich.client.features.impl.display;

import java.awt.Color;
import java.util.Comparator;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import wtf.rich.Main;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventRender2D;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.api.utils.render.ClientHelper;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.clickgui.ScreenHelper;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.ColorSetting;
import wtf.rich.client.ui.settings.impl.ListSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class FeatureList extends Feature {
     public final ListSetting fontRenderType;
     public final ListSetting borderMode;
     public BooleanSetting backGround;
     public final ColorSetting backGroundColor;
     public BooleanSetting border;
     public final BooleanSetting rightBorder;
     public NumberSetting xOffset;
     public NumberSetting yOffset;
     public final NumberSetting offset;
     public NumberSetting size;
     public final NumberSetting borderWidth;
     public final NumberSetting rainbowSaturation;
     public final NumberSetting rainbowBright;
     public final NumberSetting fontX;
     public final ListSetting backgroundMode;
     public final NumberSetting fontY;
     public BooleanSetting blur;
     public final BooleanSetting suffix;
     public static ListSetting colorList;
     public static NumberSetting colortime = new NumberSetting("Color Time", 30.0F, 1.0F, 100.0F, 1.0F, () -> {
          return colorList.currentMode.equalsIgnoreCase("Custom");
     });
     public BooleanSetting shadow;
     public final NumberSetting shadowRadius;
     public final ColorSetting shadowColor;
     public static ColorSetting onecolor = new ColorSetting("One Color", (new Color(16777215)).getRGB(), () -> {
          return colorList.currentMode.equals("Custom");
     });
     public static ColorSetting twocolor = new ColorSetting("Two Color", (new Color(16711680)).getRGB(), () -> {
          return colorList.currentMode.equals("Custom");
     });

     public FeatureList() {
          super("ArrayList", "Показывает список всех включенных модулей", 0, Category.DISPLAY);
          this.backGroundColor = new ColorSetting("BackGround Color", Color.BLACK.getRGB(), () -> {
               return this.backGround.getBoolValue();
          });
          this.blur = new BooleanSetting("Blur", false, () -> {
               return this.backGround.getBoolValue();
          });
          colorList = new ListSetting("ArrayList Color", "Astolfo", () -> {
               return true;
          }, new String[]{"Custom", "Rainbow", "Astolfo"});
          this.borderMode = new ListSetting("Border Mode", "Full", () -> {
               return this.border.getBoolValue();
          }, new String[]{"Full", "Single"});
          this.fontRenderType = new ListSetting("FontRender Type", "Shadow", () -> {
               return true;
          }, new String[]{"Default", "Shadow", "Outline"});
          this.backGround = new BooleanSetting("Background", true, () -> {
               return true;
          });
          this.backgroundMode = new ListSetting("Background Mode", "Rect", () -> {
               return this.backGround.getBoolValue();
          }, new String[]{"Rect", "Smooth Rect"});
          this.shadow = new BooleanSetting("Shadow", false, () -> {
               return true;
          });
          this.shadowRadius = new NumberSetting("Shadow Radius", 1.0F, 1.0F, 10.0F, 1.0F, () -> {
               return this.shadow.getBoolValue();
          });
          this.shadowColor = new ColorSetting("Shadow Color", (new Color(16777215)).getRGB(), () -> {
               return this.shadow.getBoolValue();
          });
          this.border = new BooleanSetting("Border", true, () -> {
               return true;
          });
          this.rightBorder = new BooleanSetting("Right Border", true, () -> {
               return true;
          });
          this.suffix = new BooleanSetting("Suffix", true, () -> {
               return true;
          });
          this.rainbowSaturation = new NumberSetting("Rainbow Saturation", 0.8F, 0.1F, 1.0F, 0.1F, () -> {
               return colorList.currentMode.equals("Rainbow");
          });
          this.rainbowBright = new NumberSetting("Rainbow Brightness", 1.0F, 0.1F, 1.0F, 0.1F, () -> {
               return colorList.currentMode.equals("Rainbow");
          });
          this.fontX = new NumberSetting("FontX", 0.0F, -4.0F, 20.0F, 0.1F, () -> {
               return true;
          });
          this.fontY = new NumberSetting("FontY", 0.0F, -4.0F, 20.0F, 0.01F, () -> {
               return true;
          });
          this.xOffset = new NumberSetting("FeatureList X", 0.0F, 0.0F, 500.0F, 1.0F, () -> {
               return !this.blur.getBoolValue();
          });
          this.yOffset = new NumberSetting("FeatureList Y", 0.0F, 0.0F, 500.0F, 1.0F, () -> {
               return !this.blur.getBoolValue();
          });
          this.offset = new NumberSetting("Font Offset", 11.0F, 7.0F, 20.0F, 0.5F, () -> {
               return true;
          });
          this.borderWidth = new NumberSetting("Border Width", 1.0F, 0.0F, 10.0F, 0.1F, () -> {
               return this.rightBorder.getBoolValue();
          });
          this.addSettings(new Setting[]{colorList, onecolor, twocolor, this.fontRenderType, this.borderMode, this.fontX, this.fontY, this.border, this.rightBorder, this.suffix, this.borderWidth, this.backGround, this.backgroundMode, this.backGroundColor, this.shadow, this.shadowRadius, this.shadowColor, colortime, this.rainbowSaturation, this.rainbowBright, this.xOffset, this.yOffset, this.offset});
     }

     @EventTarget
     public void onUpdate(EventUpdate event) {
          this.setSuffix(colorList.getCurrentMode());
     }

     @EventTarget
     public void onRender2D(EventRender2D event) {
          float width = (float)event.getResolution().getScaledWidth() - (this.rightBorder.getBoolValue() ? this.borderWidth.getNumberValue() : 0.0F);
          float y = 1.0F;
          int yTotal = 0;

          for(int i = 0; i < Main.instance.featureDirector.getFeatureList().size(); ++i) {
               yTotal += ClientHelper.getFontRender().getFontHeight() + 3;
          }

          if (Main.instance.featureDirector.getFeatureByClass(FeatureList.class).isToggled() && !mc.gameSettings.showDebugInfo) {
               Main.instance.featureDirector.getFeatureList().sort(Comparator.comparingInt((module) -> {
                    int var10000;
                    if (!ClientFont.minecraftfont.getBoolValue()) {
                         var10000 = -ClientHelper.getFontRender().getStringWidth(this.suffix.getBoolValue() ? module.getSuffix() : module.getLabel());
                    } else {
                         Minecraft var2 = mc;
                         var10000 = -Minecraft.fontRendererObj.getStringWidth(this.suffix.getBoolValue() ? module.getSuffix() : module.getLabel());
                    }

                    return var10000;
               }));
               Iterator var26 = Main.instance.featureDirector.getFeatureList().iterator();

               while(true) {
                    Feature feature;
                    float listOffset;
                    float translateY;
                    float translateX;
                    int color;
                    int colorCustom;
                    int colorCustom2;
                    double time;
                    String mode;
                    boolean visible;
                    ScreenHelper nextFeature;
                    float yOffset;
                    Minecraft var24;
                    do {
                         if (!var26.hasNext()) {
                              return;
                         }

                         feature = (Feature)var26.next();
                         nextFeature = feature.getScreenHelper();
                         String featureSuffix = this.suffix.getBoolValue() ? feature.getSuffix() : feature.getLabel();
                         listOffset = this.offset.getNumberValue();
                         float var10000;
                         if (!ClientFont.minecraftfont.getBoolValue()) {
                              var10000 = (float)ClientHelper.getFontRender().getStringWidth(featureSuffix);
                         } else {
                              var24 = mc;
                              var10000 = (float)Minecraft.fontRendererObj.getStringWidth(featureSuffix);
                         }

                         float length = var10000;
                         yOffset = width - length;
                         boolean state = feature.isToggled() && feature.visible;
                         if (state) {
                              nextFeature.interpolate(yOffset, y, 4.0D * Minecraft.frameTime / 4.0D);
                         } else {
                              nextFeature.interpolate(width, y, 4.0D * Minecraft.frameTime / 4.0D);
                         }

                         translateY = nextFeature.getY();
                         translateX = nextFeature.getX() - (this.rightBorder.getBoolValue() ? 2.5F : 1.5F) - this.fontX.getNumberValue();
                         color = 0;
                         colorCustom = onecolor.getColorValue();
                         colorCustom2 = twocolor.getColorValue();
                         time = (double)colortime.getNumberValue();
                         mode = colorList.getOptions();
                         visible = nextFeature.getX() < width;
                    } while(!visible);

                    String nextFeature = mode.toLowerCase();
                    byte var23 = -1;
                    switch(nextFeature.hashCode()) {
                    case -1349088399:
                         if (nextFeature.equals("custom")) {
                              var23 = 2;
                         }
                         break;
                    case -703561496:
                         if (nextFeature.equals("astolfo")) {
                              var23 = 1;
                         }
                         break;
                    case 973576630:
                         if (nextFeature.equals("rainbow")) {
                              var23 = 0;
                         }
                    }

                    switch(var23) {
                    case 0:
                         color = DrawHelper.rainbow((int)((double)y * time), this.rainbowSaturation.getNumberValue(), this.rainbowBright.getNumberValue()).getRGB();
                         break;
                    case 1:
                         color = DrawHelper.astolfoColors45(y, (float)yTotal, 0.5F, 5.0F).getRGB();
                         break;
                    case 2:
                         color = DrawHelper.fadeColor((new Color(colorCustom)).getRGB(), (new Color(colorCustom2)).getRGB(), (float)Math.abs(((double)System.currentTimeMillis() / time / time + (double)(y * 6.0F / 61.0F * 2.0F)) % 2.0D));
                    }

                    GlStateManager.pushMatrix();
                    GlStateManager.translate(-this.xOffset.getNumberValue(), this.yOffset.getNumberValue(), 1.0F);
                    nextFeature = null;
                    if (this.border.getBoolValue() && this.borderMode.currentMode.equals("Full")) {
                         DrawHelper.drawRect((double)translateX - 3.5D, (double)(translateY - 1.0F), (double)(translateX - 2.0F), (double)(translateY + listOffset - 1.0F), color);
                    }

                    if (this.shadow.getBoolValue()) {
                         DrawHelper.drawNewRect((double)(translateX - 5.0F - this.shadowRadius.getNumberValue()), (double)(translateY - 3.0F), (double)(width + 2.0F), (double)(translateY + listOffset + 2.0F), (new Color(255, 255, 255, 0)).getRGB());
                         DrawHelper.drawGlowRoundedRect(translateX - 5.0F - this.shadowRadius.getNumberValue(), translateY - 3.0F, width + 2.0F, translateY + listOffset + 2.0F, this.shadowColor.getColorValue(), 10.0F, 8.0F);
                    }

                    if (nextFeature != null && this.borderMode.currentMode.equals("Full")) {
                         if (this.border.getBoolValue() && this.borderMode.currentMode.equals("Full")) {
                         }
                    } else if (this.border.getBoolValue() && this.borderMode.currentMode.equals("Full")) {
                         DrawHelper.drawRect((double)translateX - 3.5D, (double)(translateY - 1.0F), (double)(translateX - 2.0F), (double)(translateY + listOffset - 1.0F), color);
                    }

                    if (this.borderMode.currentMode.equals("Single") && this.border.getBoolValue()) {
                         DrawHelper.drawRect((double)translateX - 3.5D, (double)(translateY - 1.0F), (double)(translateX - 2.0F), (double)(translateY + listOffset - 1.0F), color);
                    }

                    if (this.backGround.getBoolValue() && this.backgroundMode.currentMode.equalsIgnoreCase("Smooth Rect")) {
                         DrawHelper.drawSmoothRect(translateX - 2.0F, translateY - 1.0F, width, (float)((double)(translateY + listOffset) - 1.5D), this.backGroundColor.getColorValue());
                    }

                    if (this.backGround.getBoolValue() && this.backgroundMode.currentMode.equalsIgnoreCase("Rect")) {
                         DrawHelper.drawRect((double)(translateX - 2.0F), (double)(translateY - 1.0F), (double)width, (double)(translateY + listOffset - 1.0F), this.backGroundColor.getColorValue());
                    }

                    if (!ClientFont.minecraftfont.getBoolValue()) {
                         String modeArrayFont = ClientFont.fontMode.getOptions();
                         yOffset = modeArrayFont.equalsIgnoreCase("Lato") ? 1.2F : (modeArrayFont.equalsIgnoreCase("Myseo") ? 0.5F : (modeArrayFont.equalsIgnoreCase("URWGeometric") ? 0.5F : (modeArrayFont.equalsIgnoreCase("Roboto Regular") ? 0.5F : (modeArrayFont.equalsIgnoreCase("SFUI") ? 1.3F : 2.0F))));
                         if (!ClientFont.minecraftfont.getBoolValue() && this.fontRenderType.currentMode.equals("Shadow")) {
                              if (this.suffix.getBoolValue()) {
                                   ClientHelper.getFontRender().drawStringWithShadow(feature.getSuffix(), (double)translateX, (double)(translateY + yOffset + this.fontY.getNumberValue()), (new Color(192, 192, 192)).getRGB());
                              }

                              ClientHelper.getFontRender().drawStringWithShadow(feature.getLabel(), (double)translateX, (double)(translateY + yOffset + this.fontY.getNumberValue()), color);
                         } else if (!ClientFont.minecraftfont.getBoolValue() && this.fontRenderType.currentMode.equals("Default")) {
                              if (this.suffix.getBoolValue()) {
                                   ClientHelper.getFontRender().drawString(feature.getSuffix(), translateX, translateY + yOffset + this.fontY.getNumberValue(), (new Color(192, 192, 192)).getRGB());
                              }

                              ClientHelper.getFontRender().drawString(feature.getLabel(), translateX, translateY + yOffset + this.fontY.getNumberValue(), color);
                         } else if (!ClientFont.minecraftfont.getBoolValue() && this.fontRenderType.currentMode.equals("Outline")) {
                              if (this.suffix.getBoolValue()) {
                                   ClientHelper.getFontRender().drawStringWithOutline(feature.getSuffix(), (double)translateX, (double)(translateY + yOffset + this.fontY.getNumberValue()), (new Color(192, 192, 192)).getRGB());
                              }

                              ClientHelper.getFontRender().drawStringWithOutline(feature.getLabel(), (double)translateX, (double)(translateY + yOffset + this.fontY.getNumberValue()), color);
                         }
                    } else if (this.fontRenderType.currentMode.equals("Shadow")) {
                         if (this.suffix.getBoolValue()) {
                              var24 = mc;
                              Minecraft.fontRendererObj.drawStringWithShadow(feature.getSuffix(), translateX, translateY + 1.0F + this.fontY.getNumberValue(), (new Color(192, 192, 192)).getRGB());
                         }

                         var24 = mc;
                         Minecraft.fontRendererObj.drawStringWithShadow(feature.getLabel(), translateX, translateY + 1.0F + this.fontY.getNumberValue(), color);
                    } else if (this.fontRenderType.currentMode.equals("Default")) {
                         if (this.suffix.getBoolValue()) {
                              var24 = mc;
                              Minecraft.fontRendererObj.drawString(feature.getSuffix(), translateX, translateY + 1.0F + this.fontY.getNumberValue(), (new Color(192, 192, 192)).getRGB());
                         }

                         var24 = mc;
                         Minecraft.fontRendererObj.drawString(feature.getLabel(), translateX, translateY + 1.0F + this.fontY.getNumberValue(), color);
                    } else if (this.fontRenderType.currentMode.equals("Outline")) {
                         if (this.suffix.getBoolValue()) {
                              var24 = mc;
                              Minecraft.fontRendererObj.drawStringWithOutline(feature.getSuffix(), translateX, translateY + 1.0F + this.fontY.getNumberValue(), (new Color(192, 192, 192)).getRGB());
                         }

                         var24 = mc;
                         Minecraft.fontRendererObj.drawStringWithOutline(feature.getLabel(), translateX, translateY + 1.0F + this.fontY.getNumberValue(), color);
                    }

                    y += listOffset;
                    if (this.rightBorder.getBoolValue()) {
                         float checkY = this.border.getBoolValue() ? 0.0F : 0.6F;
                         DrawHelper.drawRect((double)width, (double)(translateY - 1.0F), (double)(width + this.borderWidth.getNumberValue()), (double)(translateY + listOffset - checkY), color);
                    }

                    GlStateManager.popMatrix();
               }
          }
     }
}
