package wtf.rich.api.utils.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import wtf.rich.api.utils.Helper;
import wtf.rich.api.utils.font.FontRenderer;
import wtf.rich.client.features.impl.display.ClientFont;
import wtf.rich.client.features.impl.display.FeatureList;

public class ClientHelper implements Helper {
     public static ServerData serverData;

     public static Color getClientColor() {
          Color color = Color.white;
          Color onecolor = new Color(FeatureList.onecolor.getColorValue());
          Color twoColor = new Color(FeatureList.twocolor.getColorValue());
          double time = (double)FeatureList.colortime.getNumberValue();
          String mode = FeatureList.colorList.getOptions();
          float yDist = 4.0F;
          int yTotal = 0;

          for(int i = 0; i < 30; ++i) {
               yTotal += Minecraft.getMinecraft().sfui18.getFontHeight() + 5;
          }

          if (mode.equalsIgnoreCase("Rainbow")) {
               color = DrawHelper.rainbow(20, 0.5F, 1.0F);
          } else if (mode.equalsIgnoreCase("Astolfo")) {
               color = DrawHelper.astolfoColors1((float)((int)yDist), (float)yTotal);
          } else if (mode.equalsIgnoreCase("Pulse")) {
               color = DrawHelper.TwoColoreffect(new Color(255, 50, 50), new Color(79, 9, 9), Math.abs((double)System.currentTimeMillis() / time) / 100.0D + 6.0D * (double)yDist * 2.55D / 60.0D);
          } else if (mode.equalsIgnoreCase("Custom")) {
               color = DrawHelper.TwoColoreffect(new Color(onecolor.getRGB()), new Color(twoColor.getRGB()), Math.abs((double)System.currentTimeMillis() / time) / 100.0D + 3.0D * (double)yDist * 2.55D / 60.0D);
          } else if (mode.equalsIgnoreCase("None")) {
               color = new Color(255, 255, 255);
          }

          return color;
     }

     public static Color getClientColor(float yStep, float yStepFull, int speed) {
          Color color = Color.white;
          Color onecolor = new Color(FeatureList.onecolor.getColorValue());
          Color twoColor = new Color(FeatureList.twocolor.getColorValue());
          double time = (double)FeatureList.colortime.getNumberValue();
          String mode = FeatureList.colorList.getOptions();
          float yDist = 4.0F;
          int yTotal = 0;

          for(int i = 0; i < 30; ++i) {
               yTotal += Minecraft.getMinecraft().sfui18.getFontHeight() + 5;
          }

          if (mode.equalsIgnoreCase("Rainbow")) {
               color = DrawHelper.rainbowCol(yStep, yStepFull, 0.5F, (float)speed);
          } else if (mode.equalsIgnoreCase("Astolfo")) {
               color = DrawHelper.astolfoColors45(yStep, yStepFull, 0.5F, (float)speed);
          } else if (mode.equalsIgnoreCase("Pulse")) {
               color = DrawHelper.TwoColoreffect(new Color(255, 50, 50), new Color(79, 9, 9), (double)Math.abs(System.currentTimeMillis() / 10L) / 100.0D + 6.0D * (double)yStep * 2.55D / 60.0D);
          } else if (mode.equalsIgnoreCase("Custom")) {
               color = DrawHelper.TwoColoreffect(new Color(onecolor.getRGB()), new Color(twoColor.getRGB()), Math.abs((double)System.currentTimeMillis() / time) / 100.0D + 3.0D * (double)yStep * 2.55D / 60.0D);
          } else if (mode.equalsIgnoreCase("None")) {
               color = new Color(255, 255, 255);
          }

          return color;
     }

     public static Color getClientColor(float yStep, float astolfoastep, float yStepFull, int speed) {
          Color color = Color.white;
          Color onecolor = new Color(FeatureList.onecolor.getColorValue());
          Color twoColor = new Color(FeatureList.twocolor.getColorValue());
          double time = (double)FeatureList.colortime.getNumberValue();
          String mode = FeatureList.colorList.getOptions();
          float yDist = 4.0F;
          int yTotal = 0;

          for(int i = 0; i < 30; ++i) {
               yTotal += Minecraft.getMinecraft().sfui18.getFontHeight() + 5;
          }

          if (mode.equalsIgnoreCase("Rainbow")) {
               color = DrawHelper.rainbowCol(yStep, yStepFull, 0.5F, (float)speed);
          } else if (mode.equalsIgnoreCase("Astolfo")) {
               color = DrawHelper.astolfoColors45(astolfoastep, yStepFull, 0.5F, (float)speed);
          } else if (mode.equalsIgnoreCase("Pulse")) {
               color = DrawHelper.TwoColoreffect(new Color(255, 50, 50), new Color(79, 9, 9), (double)Math.abs(System.currentTimeMillis() / 10L) / 100.0D + 6.0D * (double)yStep * 2.55D / 60.0D);
          } else if (mode.equalsIgnoreCase("Custom")) {
               color = DrawHelper.TwoColoreffect(new Color(onecolor.getRGB()), new Color(twoColor.getRGB()), Math.abs((double)System.currentTimeMillis() / time) / 100.0D + 3.0D * (double)yStep * 2.55D / 60.0D);
          } else if (mode.equalsIgnoreCase("None")) {
               color = new Color(255, 255, 255);
          }

          return color;
     }

     public static FontRenderer getFontRender() {
          Minecraft mc = Minecraft.getMinecraft();
          FontRenderer font = mc.sfui18;
          String mode = ClientFont.fontMode.getOptions();
          byte var4 = -1;
          switch(mode.hashCode()) {
          case -535237183:
               if (mode.equals("Roboto Regular")) {
                    var4 = 3;
               }
               break;
          case 2361040:
               if (mode.equals("Lato")) {
                    var4 = 2;
               }
               break;
          case 2542631:
               if (mode.equals("SFUI")) {
                    var4 = 1;
               }
               break;
          case 74829585:
               if (mode.equals("Myseo")) {
                    var4 = 0;
               }
               break;
          case 245219335:
               if (mode.equals("URWGeometric")) {
                    var4 = 4;
               }
          }

          switch(var4) {
          case 0:
               font = mc.neverlose500_18;
               break;
          case 1:
               font = mc.sfui18;
               break;
          case 2:
               font = mc.lato;
               break;
          case 3:
               font = mc.robotoRegular;
               break;
          case 4:
               font = mc.urwgeometric;
          }

          return font;
     }
}
