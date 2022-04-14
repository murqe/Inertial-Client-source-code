package me.rich.font;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import me.rich.helpers.render.ColorUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;

public class CFontRenderer extends CFont {
      protected CFont.CharData[] boldChars = new CFont.CharData[167];
      protected CFont.CharData[] italicChars = new CFont.CharData[167];
      protected CFont.CharData[] boldItalicChars = new CFont.CharData[167];
      private float[] charWidthFloat = new float[256];
      private final byte[] glyphWidth = new byte[65536];
      private final int[] charWidth = new int[256];
      private final int[] colorCode = new int[32];
      private final String colorcodeIdentifiers = "0123456789abcdefklmnor";
      protected DynamicTexture texBold;
      protected DynamicTexture texItalic;
      protected DynamicTexture texItalicBold;

      public CFontRenderer(Font font, boolean antiAlias, boolean fractionalMetrics) {
            super(font, antiAlias, fractionalMetrics);
            this.setupMinecraftColorcodes();
            this.setupBoldItalicIDs();
      }

      public float drawStringWithShadow(String text, double x, double y, int color) {
            float shadowWidth = this.drawString(text, x + 0.5D, y + 0.5D, color, true);
            return Math.max(shadowWidth, this.drawString(text, x, y, color, false));
      }

      public float drawString(String text, double x, double y, int color) {
            return this.drawString(text, x, y, color, false);
      }

      public float drawCenteredString(String text, double x, double y, int color) {
            return this.drawString(text, x - (double)(this.getStringWidth(text) / 2), y, color);
      }

      public void drawTotalCenteredStringWithShadow(String text, double x, double y, int color) {
            this.drawStringWithShadow(text, x - (double)((float)this.getStringWidth(text) / 2.0F), y - (double)((float)this.getStringHeight(text) / 2.0F), color);
      }

      public float drawCenteredStringWithShadow(String text, double x, double y, int color) {
            return this.drawStringWithShadow(text, x - (double)((float)this.getStringWidth(text) / 2.0F), y, color);
      }

      public void drawTotalCenteredString(String text, double x, double y, int color) {
            this.drawString(text, x - (double)((float)this.getStringWidth(text) / 2.0F), y - (double)((float)this.getStringHeight(text) / 2.0F), color);
      }

      public List listFormattedStringToWidth(String str, int wrapWidth) {
            return Arrays.asList(this.wrapFormattedStringToWidth(str, wrapWidth).split("\n"));
      }

      public String trimStringToWidth(String text, int width) {
            return this.trimStringToWidth(text, width, false);
      }

      public void drawBorderedString(String text, double d, double e, int color) {
            GlStateManager.pushMatrix();
            GL11.glTranslated(0.5D, 0.0D, 0.0D);
            this.drawString(text, d, e, ColorUtils.getColor(0, color >> 24 & 255), false);
            GL11.glTranslated(1.0D, 0.0D, 0.0D);
            this.drawString(text, d, e, ColorUtils.getColor(0, color >> 24 & 255), false);
            GL11.glTranslated(-0.5D, 0.5D, 0.0D);
            this.drawString(text, d, e, ColorUtils.getColor(0, color >> 24 & 255), false);
            GL11.glTranslated(0.0D, -1.0D, 0.0D);
            this.drawString(text, d, e, ColorUtils.getColor(0, color >> 24 & 255), false);
            GL11.glTranslated(0.0D, 0.5D, 0.0D);
            this.drawString(text, d, e, color, false);
            GL11.glTranslated(-1.0D, -1.0D, 0.0D);
            GlStateManager.popMatrix();
      }

      public float drawString(String text, double x, double y, int color, boolean shadow) {
            --x;
            if (text == null) {
                  return 0.0F;
            } else {
                  if (color == 553648127) {
                        color = 16777215;
                  }

                  if ((color & -67108864) == 0) {
                        color |= -16777216;
                  }

                  if (shadow) {
                        color = (color & 16579836) >> 2 | color & (new Color(20, 20, 20, 200)).getRGB();
                  }

                  CFont.CharData[] currentData = this.charData;
                  float alpha = (float)(color >> 24 & 255) / 255.0F;
                  boolean bold = false;
                  boolean italic = false;
                  boolean strikethrough = false;
                  boolean underline = false;
                  x *= 2.0D;
                  y = (y - 3.0D) * 2.0D;
                  GL11.glPushMatrix();
                  GlStateManager.scale(0.5D, 0.5D, 0.5D);
                  GlStateManager.enableBlend();
                  GlStateManager.blendFunc(770, 771);
                  GlStateManager.color((float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, alpha);
                  int size = text.length();
                  GlStateManager.enableTexture2D();
                  GlStateManager.bindTexture(this.tex.getGlTextureId());
                  GL11.glBindTexture(3553, this.tex.getGlTextureId());

                  for(int i = 0; i < size; ++i) {
                        char character = text.charAt(i);
                        if (String.valueOf(character).equals("§")) {
                              int colorIndex = 21;

                              try {
                                    colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
                              } catch (Exception var19) {
                                    var19.printStackTrace();
                              }

                              if (colorIndex < 16) {
                                    bold = false;
                                    italic = false;
                                    underline = false;
                                    strikethrough = false;
                                    GlStateManager.bindTexture(this.tex.getGlTextureId());
                                    currentData = this.charData;
                                    if (colorIndex < 0) {
                                          colorIndex = 15;
                                    }

                                    if (shadow) {
                                          colorIndex += 16;
                                    }

                                    int colorcode = this.colorCode[colorIndex];
                                    GlStateManager.color((float)(colorcode >> 16 & 255) / 255.0F, (float)(colorcode >> 8 & 255) / 255.0F, (float)(colorcode & 255) / 255.0F, alpha);
                              } else if (colorIndex == 17) {
                                    bold = true;
                                    if (italic) {
                                          GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
                                          currentData = this.boldItalicChars;
                                    } else {
                                          GlStateManager.bindTexture(this.texBold.getGlTextureId());
                                          currentData = this.boldChars;
                                    }
                              } else if (colorIndex == 18) {
                                    strikethrough = true;
                              } else if (colorIndex == 19) {
                                    underline = true;
                              } else if (colorIndex == 20) {
                                    italic = true;
                                    if (bold) {
                                          GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
                                          currentData = this.boldItalicChars;
                                    } else {
                                          GlStateManager.bindTexture(this.texItalic.getGlTextureId());
                                          currentData = this.italicChars;
                                    }
                              } else if (colorIndex == 21) {
                                    bold = false;
                                    italic = false;
                                    underline = false;
                                    strikethrough = false;
                                    GlStateManager.color((float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, alpha);
                                    GlStateManager.bindTexture(this.tex.getGlTextureId());
                                    currentData = this.charData;
                              }

                              ++i;
                        } else if (character < currentData.length) {
                              GL11.glBegin(4);
                              this.drawChar(currentData, character, (float)x, (float)y);
                              GL11.glEnd();
                              if (strikethrough) {
                                    this.drawLine(x, y + (double)((float)currentData[character].height / 2.0F), x + (double)currentData[character].width - 8.0D, y + (double)((float)currentData[character].height / 2.0F), 1.0F);
                              }

                              if (underline) {
                                    this.drawLine(x, y + (double)currentData[character].height - 2.0D, x + (double)currentData[character].width - 8.0D, y + (double)currentData[character].height - 2.0D, 1.0F);
                              }

                              x += (double)(currentData[character].width - 8 + this.charOffset);
                        }
                  }

                  GL11.glPopMatrix();
                  return (float)x / 2.0F;
            }
      }

      public int getStringWidth(String text) {
            if (text == null) {
                  return 0;
            } else {
                  int width = 0;
                  CFont.CharData[] currentData = this.charData;
                  boolean bold = false;
                  boolean italic = false;
                  int size = text.length();

                  for(int i = 0; i < size; ++i) {
                        char character = text.charAt(i);
                        if (character == 167 && i < size) {
                              int colorIndex = "0123456789abcdefklmnor".indexOf(character);
                              if (colorIndex < 16) {
                                    bold = false;
                                    italic = false;
                              } else if (colorIndex == 17) {
                                    bold = true;
                                    if (italic) {
                                          currentData = this.boldItalicChars;
                                    } else {
                                          currentData = this.boldChars;
                                    }
                              } else if (colorIndex == 20) {
                                    italic = true;
                                    if (bold) {
                                          currentData = this.boldItalicChars;
                                    } else {
                                          currentData = this.italicChars;
                                    }
                              } else if (colorIndex == 21) {
                                    bold = false;
                                    italic = false;
                                    currentData = this.charData;
                              }

                              ++i;
                        } else if (character < currentData.length && character >= 0) {
                              width += currentData[character].width - 8 + this.charOffset;
                        }
                  }

                  return width / 2;
            }
      }

      public void setFont(Font font) {
            super.setFont(font);
            this.setupBoldItalicIDs();
      }

      public void setAntiAlias(boolean antiAlias) {
            super.setAntiAlias(antiAlias);
            this.setupBoldItalicIDs();
      }

      public void setFractionalMetrics(boolean fractionalMetrics) {
            super.setFractionalMetrics(fractionalMetrics);
            this.setupBoldItalicIDs();
      }

      private void setupBoldItalicIDs() {
            this.texBold = this.setupTexture(this.font.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldChars);
            this.texItalic = this.setupTexture(this.font.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
      }

      private void drawLine(double x, double y, double x1, double y1, float width) {
            GL11.glDisable(3553);
            GL11.glLineWidth(width);
            GL11.glBegin(1);
            GL11.glVertex2d(x, y);
            GL11.glVertex2d(x1, y1);
            GL11.glEnd();
            GL11.glEnable(3553);
      }

      public List wrapWords(String text, double width) {
            List finalWords = new ArrayList();
            if ((double)this.getStringWidth(text) > width) {
                  String[] words = text.split(" ");
                  String currentWord = "";
                  char lastColorCode = '\uffff';
                  String[] arrayOfString1 = words;
                  int j = words.length;

                  String s;
                  for(int i = 0; i < j; ++i) {
                        s = arrayOfString1[i];

                        for(boolean var12 = false; i < s.toCharArray().length; ++i) {
                              char c = s.toCharArray()[i];
                              if (c == 167 && i < s.toCharArray().length - 1) {
                                    lastColorCode = s.toCharArray()[i + 1];
                              }
                        }

                        if ((double)this.getStringWidth(currentWord + s + " ") < width) {
                              currentWord = currentWord + s + " ";
                        } else {
                              finalWords.add(currentWord);
                              currentWord = 167 + lastColorCode + s + " ";
                        }
                  }

                  if (currentWord.length() > 0) {
                        if ((double)this.getStringWidth(currentWord) < width) {
                              finalWords.add(167 + lastColorCode + currentWord + " ");
                              currentWord = "";
                        } else {
                              Iterator var15 = this.formatString(currentWord, width).iterator();

                              while(var15.hasNext()) {
                                    s = (String)var15.next();
                                    finalWords.add(s);
                              }
                        }
                  }
            } else {
                  finalWords.add(text);
            }

            return finalWords;
      }

      public List formatString(String string, double width) {
            List finalWords = new ArrayList();
            String currentWord = "";
            char lastColorCode = '\uffff';
            char[] chars = string.toCharArray();

            for(int i = 0; i < chars.length; ++i) {
                  char c = chars[i];
                  if (c == 167 && i < chars.length - 1) {
                        lastColorCode = chars[i + 1];
                  }

                  if ((double)this.getStringWidth(currentWord + c) < width) {
                        currentWord = currentWord + c;
                  } else {
                        finalWords.add(currentWord);
                        currentWord = 167 + lastColorCode + String.valueOf(c);
                  }
            }

            if (currentWord.length() > 0) {
                  finalWords.add(currentWord);
            }

            return finalWords;
      }

      String wrapFormattedStringToWidth(String str, int wrapWidth) {
            if (str.length() <= 1) {
                  return str;
            } else {
                  int i = this.sizeStringToWidth(str, wrapWidth);
                  if (str.length() <= i) {
                        return str;
                  } else {
                        String s = str.substring(0, i);
                        char c0 = str.charAt(i);
                        boolean flag = c0 == ' ' || c0 == '\n';
                        String s1 = getFormatFromString(s) + str.substring(i + (flag ? 1 : 0));
                        return s + "\n" + this.wrapFormattedStringToWidth(s1, wrapWidth);
                  }
            }
      }

      public static String getFormatFromString(String text) {
            String s = "";
            int i = -1;
            int j = text.length();

            while((i = text.indexOf(167, i + 1)) != -1) {
                  if (i < j - 1) {
                        char c0 = text.charAt(i + 1);
                        if (isFormatColor(c0)) {
                              s = "§" + c0;
                        } else if (isFormatSpecial(c0)) {
                              s = s + "§" + c0;
                        }
                  }
            }

            return s;
      }

      private int sizeStringToWidth(String str, int wrapWidth) {
            int i = str.length();
            float f = 0.0F;
            int j = 0;
            int k = -1;

            for(boolean flag = false; j < i; ++j) {
                  char c0 = str.charAt(j);
                  switch(c0) {
                  case '\n':
                        --j;
                        break;
                  case ' ':
                        k = j;
                  default:
                        f += this.getCharWidthFloat(c0);
                        if (flag) {
                              ++f;
                        }
                        break;
                  case '§':
                        if (j < i - 1) {
                              ++j;
                              char c1 = str.charAt(j);
                              if (c1 != 'l' && c1 != 'L') {
                                    if (c1 == 'r' || c1 == 'R' || isFormatColor(c1)) {
                                          flag = false;
                                    }
                              } else {
                                    flag = true;
                              }
                        }
                  }

                  if (c0 == '\n') {
                        ++j;
                        k = j;
                        break;
                  }

                  if (Math.round(f) > wrapWidth) {
                        break;
                  }
            }

            return j != i && k != -1 && k < j ? k : j;
      }

      private float getCharWidthFloat(char p_getCharWidthFloat_1_) {
            if (p_getCharWidthFloat_1_ == 167) {
                  return -1.0F;
            } else if (p_getCharWidthFloat_1_ != ' ' && p_getCharWidthFloat_1_ != 160) {
                  int i = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000".indexOf(p_getCharWidthFloat_1_);
                  if (p_getCharWidthFloat_1_ > 0 && i != -1) {
                        return this.charWidthFloat[i];
                  } else if (this.glyphWidth[p_getCharWidthFloat_1_] != 0) {
                        int j = this.glyphWidth[p_getCharWidthFloat_1_] & 255;
                        int k = j >>> 4;
                        int l = j & 15;
                        ++l;
                        return (float)((l - k) / 2 + 1);
                  } else {
                        return 0.0F;
                  }
            } else {
                  return this.charWidthFloat[32];
            }
      }

      public String trimStringToWidth(String text, int width, boolean reverse) {
            StringBuilder stringbuilder = new StringBuilder();
            float f = 0.0F;
            int i = reverse ? text.length() - 1 : 0;
            int j = reverse ? -1 : 1;
            boolean flag = false;
            boolean flag1 = false;

            for(int k = i; k >= 0 && k < text.length() && f < (float)width; k += j) {
                  char c0 = text.charAt(k);
                  float f1 = this.getCharWidthFloat(c0);
                  if (flag) {
                        flag = false;
                        if (c0 != 'l' && c0 != 'L') {
                              if (c0 == 'r' || c0 == 'R') {
                                    flag1 = false;
                              }
                        } else {
                              flag1 = true;
                        }
                  } else if (f1 < 0.0F) {
                        flag = true;
                  } else {
                        f += f1;
                        if (flag1) {
                              ++f;
                        }
                  }

                  if (f > (float)width) {
                        break;
                  }

                  if (reverse) {
                        stringbuilder.insert(0, c0);
                  } else {
                        stringbuilder.append(c0);
                  }
            }

            return stringbuilder.toString();
      }

      private static boolean isFormatSpecial(char formatChar) {
            return formatChar >= 'k' && formatChar <= 'o' || formatChar >= 'K' && formatChar <= 'O' || formatChar == 'r' || formatChar == 'R';
      }

      private static boolean isFormatColor(char colorChar) {
            return colorChar >= '0' && colorChar <= '9' || colorChar >= 'a' && colorChar <= 'f' || colorChar >= 'A' && colorChar <= 'F';
      }

      private void setupMinecraftColorcodes() {
            for(int index = 0; index < 32; ++index) {
                  int noClue = (index >> 3 & 1) * 85;
                  int red = (index >> 2 & 1) * 170 + noClue;
                  int green = (index >> 1 & 1) * 170 + noClue;
                  int blue = (index >> 0 & 1) * 170 + noClue;
                  if (index == 6) {
                        red += 85;
                  }

                  if (index >= 16) {
                        red /= 4;
                        green /= 4;
                        blue /= 4;
                  }

                  this.colorCode[index] = (red & 255) << 16 | (green & 255) << 8 | blue & 255;
            }

      }
}
