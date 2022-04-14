package me.rich.helpers.render;

import java.awt.Color;
import java.text.NumberFormat;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ColorUtils {
      public static int getColor(Color color) {
            return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
      }

      public static Color getColorWithOpacity(Color color, int alpha) {
            return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
      }

      public static Color fade(Color color) {
            return fade(color, 2, 100);
      }

      public static int color(int n, int n2, int n3, int n4) {
            int n4 = 255;
            return (new Color(n, n2, n3, n4)).getRGB();
      }

      public static int getColor1(Color color) {
            return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
      }

      public static int rainbow(int delay, float saturation, float brightness) {
            double rainbow = Math.ceil((double)((System.currentTimeMillis() + (long)delay) / 16L));
            rainbow %= 360.0D;
            return Color.getHSBColor((float)(rainbow / 360.0D), saturation, brightness).getRGB();
      }

      public static Color getHealthColor(EntityLivingBase entityLivingBase) {
            float health = entityLivingBase.getHealth();
            float[] fractions = new float[]{0.0F, 0.15F, 0.55F, 0.7F, 0.9F};
            Color[] colors = new Color[]{new Color(133, 0, 0), Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN};
            float progress = health / entityLivingBase.getMaxHealth();
            return health >= 0.0F ? blendColors(fractions, colors, progress).brighter() : colors[0];
      }

      public static int getColor2(int brightness) {
            return getColor(brightness, brightness, brightness, 255);
      }

      public static int getColor3(int brightness, int alpha) {
            return getColor(brightness, brightness, brightness, alpha);
      }

      public static int getColor4(int red, int green, int blue) {
            return getColor(red, green, blue, 255);
      }

      public static int getColor5(int red, int green, int blue, int alpha) {
            int color = 0;
            int color = color | alpha << 24;
            color |= red << 16;
            color |= green << 8;
            color |= blue;
            return color;
      }

      public static int getRandomColor() {
            char[] letters = "012345678".toCharArray();
            String color = "0x";

            for(int i = 0; i < 6; ++i) {
                  color = color + letters[(new Random()).nextInt(letters.length)];
            }

            return Integer.decode(color);
      }

      public static int reAlpha(int color, float alpha) {
            Color c = new Color(color);
            float r = 0.003921569F * (float)c.getRed();
            float g = 0.003921569F * (float)c.getGreen();
            float b = 0.003921569F * (float)c.getBlue();
            return (new Color(r, g, b, alpha)).getRGB();
      }

      public static Color getGradientOffset(Color color1, Color color2, double offset) {
            double inverse_percent;
            int redPart;
            if (offset > 1.0D) {
                  inverse_percent = offset % 1.0D;
                  redPart = (int)offset;
                  offset = redPart % 2 == 0 ? inverse_percent : 1.0D - inverse_percent;
            }

            inverse_percent = 1.0D - offset;
            redPart = (int)((double)color1.getRed() * inverse_percent + (double)color2.getRed() * offset);
            int greenPart = (int)((double)color1.getGreen() * inverse_percent + (double)color2.getGreen() * offset);
            int bluePart = (int)((double)color1.getBlue() * inverse_percent + (double)color2.getBlue() * offset);
            return new Color(redPart, greenPart, bluePart);
      }

      public static int toRGBA(int r, int g, int b, int a) {
            return (r << 16) + (g << 8) + (b << 0) + (a << 24);
      }

      public static int toRGBA(float r, float g, float b, float a) {
            return toRGBA((int)(r * 255.0F), (int)(g * 255.0F), (int)(b * 255.0F), (int)(a * 255.0F));
      }

      public static int toRGBA(float[] colors) {
            if (colors.length != 4) {
                  throw new IllegalArgumentException("colors[] must have a length of 4!");
            } else {
                  return toRGBA(colors[0], colors[1], colors[2], colors[3]);
            }
      }

      public static int toRGBA(double[] colors) {
            if (colors.length != 4) {
                  throw new IllegalArgumentException("colors[] must have a length of 4!");
            } else {
                  return toRGBA((float)colors[0], (float)colors[1], (float)colors[2], (float)colors[3]);
            }
      }

      public static int getColor1(int brightness) {
            return getColor(brightness, brightness, brightness, 255);
      }

      public static int getColor(int red, int green, int blue) {
            return getColor(red, green, blue, 255);
      }

      public static int getColor(int red, int green, int blue, int alpha) {
            int color = 0;
            int color = color | alpha << 24;
            color |= red << 16;
            color |= green << 8;
            return color | blue;
      }

      public static int getColor(int brightness) {
            return getColor(brightness, brightness, brightness, 255);
      }

      public static int getColor(int brightness, int alpha) {
            return getColor(brightness, brightness, brightness, alpha);
      }

      public static Color fade(Color color, int index, int count) {
            float[] hsb = new float[3];
            Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
            float brightness = Math.abs(((float)(System.currentTimeMillis() % 2000L) / 1000.0F + (float)index / (float)count * 2.0F) % 2.0F - 1.0F);
            brightness = 0.5F + 0.5F * brightness;
            hsb[2] = brightness % 2.0F;
            return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
      }

      public static Color blendColors(float[] fractions, Color[] colors, float progress) {
            if (fractions == null) {
                  throw new IllegalArgumentException("Fractions can't be null");
            } else if (colors == null) {
                  throw new IllegalArgumentException("Colours can't be null");
            } else if (fractions.length != colors.length) {
                  throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
            } else {
                  int[] indicies = getFractionIndicies(fractions, progress);
                  float[] range = new float[]{fractions[indicies[0]], fractions[indicies[1]]};
                  Color[] colorRange = new Color[]{colors[indicies[0]], colors[indicies[1]]};
                  float max = range[1] - range[0];
                  float value = progress - range[0];
                  float weight = value / max;
                  return blend(colorRange[0], colorRange[1], (double)(1.0F - weight));
            }
      }

      public static int[] getFractionIndicies(float[] fractions, float progress) {
            int[] range = new int[2];

            int startPoint;
            for(startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; ++startPoint) {
            }

            if (startPoint >= fractions.length) {
                  startPoint = fractions.length - 1;
            }

            range[0] = startPoint - 1;
            range[1] = startPoint;
            return range;
      }

      public static Color blend(Color color1, Color color2, double ratio) {
            float r = (float)ratio;
            float ir = 1.0F - r;
            float[] rgb1 = new float[3];
            float[] rgb2 = new float[3];
            color1.getColorComponents(rgb1);
            color2.getColorComponents(rgb2);
            float red = rgb1[0] * r + rgb2[0] * ir;
            float green = rgb1[1] * r + rgb2[1] * ir;
            float blue = rgb1[2] * r + rgb2[2] * ir;
            if (red < 0.0F) {
                  red = 0.0F;
            } else if (red > 255.0F) {
                  red = 255.0F;
            }

            if (green < 0.0F) {
                  green = 0.0F;
            } else if (green > 255.0F) {
                  green = 255.0F;
            }

            if (blue < 0.0F) {
                  blue = 0.0F;
            } else if (blue > 255.0F) {
                  blue = 255.0F;
            }

            Color color = null;

            try {
                  color = new Color(red, green, blue);
            } catch (IllegalArgumentException var14) {
                  NumberFormat var13 = NumberFormat.getNumberInstance();
            }

            return color;
      }

      public static int astolfo(int delay, float offset) {
            float speed = 3000.0F;

            float hue;
            for(hue = Math.abs((float)(System.currentTimeMillis() % (long)delay) + -offset / 21.0F * 2.0F); hue > speed; hue -= speed) {
            }

            if ((double)(hue /= speed) > 0.5D) {
                  hue = 0.5F - (hue - 0.5F);
            }

            return Color.HSBtoRGB(hue += 0.5F, 0.5F, 1.0F);
      }

      public static int Yellowastolfo(int delay, float offset) {
            float speed = 2900.0F;

            float hue;
            for(hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + ((float)(-delay) - offset) * 9.0F; hue > speed; hue -= speed) {
            }

            if ((double)(hue /= speed) > 0.6D) {
                  hue = 0.6F - (hue - 0.6F);
            }

            return Color.HSBtoRGB(hue += 0.6F, 0.5F, 1.0F);
      }

      public static Color Yellowastolfo1(int delay, float offset) {
            float speed = 2900.0F;

            float hue;
            for(hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + ((float)delay - offset) * 9.0F; hue > speed; hue -= speed) {
            }

            if ((double)(hue /= speed) > 0.6D) {
                  hue = 0.6F - (hue - 0.6F);
            }

            return new Color(hue += 0.6F, 0.5F, 1.0F);
      }

      public static Color TwoColoreffect(Color cl1, Color cl2, double speed) {
            double thing = speed / 4.0D % 1.0D;
            float val = MathHelper.clamp((float)Math.sin(18.84955592153876D * thing) / 2.0F + 0.5F, 0.0F, 1.0F);
            return new Color(lerp((float)cl1.getRed() / 255.0F, (float)cl2.getRed() / 255.0F, val), lerp((float)cl1.getGreen() / 255.0F, (float)cl2.getGreen() / 255.0F, val), lerp((float)cl1.getBlue() / 255.0F, (float)cl2.getBlue() / 255.0F, val));
      }

      public static int astolfoColors(int yOffset, int yTotal) {
            float speed = 2900.0F;

            float hue;
            for(hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + (float)((yTotal - yOffset) * 9); hue > speed; hue -= speed) {
            }

            if ((double)(hue /= speed) > 0.5D) {
                  hue = 0.5F - (hue - 0.5F);
            }

            return Color.HSBtoRGB(hue += 0.5F, 0.5F, 1.0F);
      }

      public static Color astolfoColor(int yOffset, int yTotal) {
            float speed = 2900.0F;

            float hue;
            for(hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + (float)((yTotal - yOffset) * 9); hue > speed; hue -= speed) {
            }

            hue /= speed;
            if ((double)hue > 0.5D) {
                  hue = 0.5F - (hue - 0.5F);
            }

            hue += 0.5F;
            return new Color(hue, 0.5F, 1.0F);
      }

      public static int getTeamColor(Entity entityIn) {
            int i = true;
            int i = entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase("пїЅf[пїЅcRпїЅf]пїЅc" + entityIn.getName()) ? getColor(new Color(255, 60, 60)) : (entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase("пїЅf[пїЅ9BпїЅf]пїЅ9" + entityIn.getName()) ? getColor(new Color(60, 60, 255)) : (entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase("пїЅf[пїЅeYпїЅf]пїЅe" + entityIn.getName()) ? getColor(new Color(255, 255, 60)) : (entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase("пїЅf[пїЅaGпїЅf]пїЅa" + entityIn.getName()) ? getColor(new Color(60, 255, 60)) : getColor(new Color(255, 255, 255)))));
            return i;
      }

      public static int astolfoColors1(float yDist, float yTotal) {
            float speed = 3500.0F;

            float hue;
            for(hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + (yTotal - yDist) * 12.0F; hue > speed; hue -= speed) {
            }

            hue /= speed;
            if ((double)hue > 0.5D) {
                  hue = 0.5F - (hue - 0.5F);
            }

            hue += 0.5F;
            return Color.HSBtoRGB(hue, 0.4F, 1.0F);
      }

      public static int astolfoColors4(float yDist, float yTotal, float saturation) {
            float speed = 1800.0F;

            float hue;
            for(hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + (yTotal - yDist) * 12.0F; hue > speed; hue -= speed) {
            }

            hue /= speed;
            if ((double)hue > 0.5D) {
                  hue = 0.5F - (hue - 0.5F);
            }

            hue += 0.5F;
            return Color.HSBtoRGB(hue, saturation, 1.0F);
      }

      public static int astolfoColors2(float yDist, float yTotal) {
            float speed = 3500.0F;

            float hue;
            for(hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + (yTotal - yDist) * 12.0F; hue > speed; hue -= speed) {
            }

            hue /= speed;
            if ((double)hue > 0.5D) {
                  hue = 0.5F - (hue - 0.5F);
            }

            hue += 0.5F;
            return Color.HSBtoRGB(hue, 0.5F, 0.75F);
      }

      public static int astolfoColors3(float yDist, float yTotal) {
            float speed = 3500.0F;

            float hue;
            for(hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + (yTotal - yDist) * 12.0F; hue > speed; hue -= speed) {
            }

            hue /= speed;
            if ((double)hue > 0.5D) {
                  hue = 0.5F - (hue - 0.5F);
            }

            hue += 0.5F;
            return Color.HSBtoRGB(hue, 0.2F, 1.0F);
      }

      public static float lerp(float a, float b, float f) {
            return a + f * (b - a);
      }

      public static int rainbowNew(int delay, float saturation, float brightness) {
            double rainbow = Math.ceil((double)((System.currentTimeMillis() + (long)delay) / 16L));
            return Color.getHSBColor((float)((rainbow %= 360.0D) / 360.0D), saturation, brightness).getRGB();
      }

      public static class Colors {
            public static final int WHITE = ColorUtils.toRGBA(255, 255, 255, 255);
            public static final int BLACK = ColorUtils.toRGBA(0, 0, 0, 255);
            public static final int RED = ColorUtils.toRGBA(255, 0, 0, 255);
            public static final int GREEN = ColorUtils.toRGBA(0, 255, 0, 255);
            public static final int BLUE = ColorUtils.toRGBA(0, 0, 255, 255);
            public static final int ORANGE = ColorUtils.toRGBA(255, 128, 0, 255);
            public static final int PURPLE = ColorUtils.toRGBA(163, 73, 163, 255);
            public static final int GRAY = ColorUtils.toRGBA(127, 127, 127, 255);
            public static final int DARK_RED = ColorUtils.toRGBA(64, 0, 0, 255);
            public static final int YELLOW = ColorUtils.toRGBA(255, 255, 0, 255);
            public static final int RAINBOW = Integer.MIN_VALUE;
      }
}
