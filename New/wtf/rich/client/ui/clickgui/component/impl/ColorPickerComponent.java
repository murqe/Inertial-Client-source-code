package wtf.rich.client.ui.clickgui.component.impl;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.client.ui.clickgui.component.Component;
import wtf.rich.client.ui.clickgui.component.ExpandableComponent;
import wtf.rich.client.ui.clickgui.component.PropertyComponent;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.ColorSetting;

public class ColorPickerComponent extends ExpandableComponent implements PropertyComponent {
     private static final int COLOR_PICKER_HEIGHT = 80;
     public static Tessellator tessellator = Tessellator.getInstance();
     public static BufferBuilder buffer;
     private final ColorSetting colorSetting;
     private float hue;
     private float saturation;
     private float brightness;
     private float alpha;
     private boolean colorSelectorDragging;
     private boolean hueSelectorDragging;
     private boolean alphaSelectorDragging;
     Minecraft mc = Minecraft.getMinecraft();

     public ColorPickerComponent(Component parent, ColorSetting colorSetting, int x, int y, int width, int height) {
          super(parent, colorSetting.getName(), x, y, width, height);
          this.colorSetting = colorSetting;
          int value = colorSetting.getColorValue();
          float[] hsb = this.getHSBFromColor(value);
          this.hue = hsb[0];
          this.saturation = hsb[1];
          this.brightness = hsb[2];
          this.alpha = (float)(value >> 24 & 255) / 255.0F;
     }

     public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
          super.drawComponent(scaledResolution, mouseX, mouseY);
          int x = this.getX();
          int y = this.getY();
          int width = this.getWidth();
          int height = this.getHeight();
          int textColor = 16777215;
          Gui.drawRect((double)x, (double)y, (double)(x + width), (double)(y + height), (new Color(20, 20, 20, 160)).getRGB());
          this.mc.sfui18.drawStringWithShadow(this.getName(), (double)(x + 2), (double)((float)y + (float)height / 2.0F - 3.0F), textColor);
          this.mc.makslol.drawStringWithShadow("!", (double)(x + 83), (double)(y + 5), textColor);
          if (this.isExpanded()) {
               Gui.drawRect((double)(x + 1), (double)(y + height), (double)(x + width - 1), (double)(y + this.getHeightWithExpand()), (new Color(20, 20, 20, 160)).getRGB());
               float sLeft = (float)(x + 2);
               float sTop = (float)(y + height + 2);
               float sRight = (float)(x + 80 - 2);
               float sBottom = (float)(y + height + 80 - 2);
               if ((float)mouseX <= sLeft || (float)mouseY <= sTop || (float)mouseX >= sRight || (float)mouseY >= sBottom) {
                    this.colorSelectorDragging = false;
               }

               float hueSelectorY = this.saturation * (sRight - sLeft);
               float inc = (1.0F - this.brightness) * (sBottom - sTop);
               float times;
               float sHeight;
               float alphaSelectorY;
               float selectorHeight;
               if (this.colorSelectorDragging) {
                    times = sRight - sLeft;
                    sHeight = (float)mouseX - sLeft;
                    this.saturation = sHeight / times;
                    hueSelectorY = sHeight;
                    alphaSelectorY = sBottom - sTop;
                    selectorHeight = (float)mouseY - sTop;
                    this.brightness = 1.0F - selectorHeight / alphaSelectorY;
                    inc = selectorHeight;
                    this.updateColor(Color.HSBtoRGB(this.hue, this.saturation, this.brightness), false);
               }

               Gui.drawRect((double)sLeft, (double)sTop, (double)sRight, (double)sBottom, -16777216);
               this.drawColorPickerRect(sLeft + 0.5F, sTop + 0.5F, sRight - 0.5F, sBottom - 0.5F);
               times = 2.0F;
               sHeight = 0.5F;
               alphaSelectorY = times / 2.0F;
               selectorHeight = sLeft + hueSelectorY - alphaSelectorY;
               float outlineWidth = sTop + inc - alphaSelectorY;
               float half = sLeft + hueSelectorY + alphaSelectorY;
               float csTop = sTop + inc + alphaSelectorY;
               Gui.drawRect((double)(selectorHeight - sHeight), (double)(outlineWidth - sHeight), (double)(half + sHeight), (double)(csTop + sHeight), -16777216);
               Gui.drawRect((double)selectorHeight, (double)outlineWidth, (double)half, (double)csTop, Color.HSBtoRGB(this.hue, this.saturation, this.brightness));
               sLeft = (float)(x + 80 - 1);
               sTop = (float)(y + height + 2);
               sRight = sLeft + 5.0F;
               sBottom = (float)(y + height + 80 - 2);
               if ((float)mouseX <= sLeft || (float)mouseY <= sTop || (float)mouseX >= sRight || (float)mouseY >= sBottom) {
                    this.hueSelectorDragging = false;
               }

               hueSelectorY = this.hue * (sBottom - sTop);
               if (this.hueSelectorDragging) {
                    inc = sBottom - sTop;
                    times = (float)mouseY - sTop;
                    this.hue = times / inc;
                    hueSelectorY = times;
                    this.updateColor(Color.HSBtoRGB(this.hue, this.saturation, this.brightness), false);
               }

               Gui.drawRect((double)sLeft, (double)sTop, (double)sRight, (double)sBottom, -16777216);
               inc = 0.2F;
               times = 1.0F / inc;
               sHeight = sBottom - sTop;
               alphaSelectorY = sTop + 0.5F;
               selectorHeight = sHeight / times;

               for(int i = 0; (float)i < times; ++i) {
                    boolean last = (float)i == times - 1.0F;
                    if (last) {
                         --selectorHeight;
                    }

                    DrawHelper.drawGradientRect((double)(sLeft + 0.5F), (double)alphaSelectorY, (double)(sRight - 0.5F), (double)(alphaSelectorY + selectorHeight), Color.HSBtoRGB(inc * (float)i, 1.0F, 1.0F), Color.HSBtoRGB(inc * (float)(i + 1), 1.0F, 1.0F));
                    if (!last) {
                         alphaSelectorY += selectorHeight;
                    }
               }

               outlineWidth = 2.0F;
               half = 0.5F;
               csTop = outlineWidth / 2.0F;
               float csBottom = sTop + hueSelectorY - csTop;
               float bx = sTop + hueSelectorY + csTop;
               Gui.drawRect((double)(sLeft - half), (double)(csBottom - half), (double)(sRight + half), (double)(bx + half), -16777216);
               Gui.drawRect((double)sLeft, (double)csBottom, (double)sRight, (double)bx, Color.HSBtoRGB(this.hue, 1.0F, 1.0F));
               sLeft = (float)(x + 80 + 6);
               sTop = (float)(y + height + 2);
               sRight = sLeft + 5.0F;
               sBottom = (float)(y + height + 80 - 2);
               if ((float)mouseX <= sLeft || (float)mouseY <= sTop || (float)mouseX >= sRight || (float)mouseY >= sBottom) {
                    this.alphaSelectorDragging = false;
               }

               int color = Color.HSBtoRGB(this.hue, this.saturation, this.brightness);
               int r = color >> 16 & 255;
               int g = color >> 8 & 255;
               int b = color & 255;
               alphaSelectorY = this.alpha * (sBottom - sTop);
               if (this.alphaSelectorDragging) {
                    selectorHeight = sBottom - sTop;
                    outlineWidth = (float)mouseY - sTop;
                    this.alpha = outlineWidth / selectorHeight;
                    alphaSelectorY = outlineWidth;
                    this.updateColor((new Color(r, g, b, (int)(this.alpha * 255.0F))).getRGB(), true);
               }

               Gui.drawRect((double)sLeft, (double)sTop, (double)sRight, (double)sBottom, -16777216);
               this.drawCheckeredBackground(sLeft + 0.5F, sTop + 0.5F, sRight - 0.5F, sBottom - 0.5F);
               DrawHelper.drawGradientRect((double)(sLeft + 0.5F), (double)(sTop + 0.5F), (double)(sRight - 0.5F), (double)(sBottom - 0.5F), (new Color(r, g, b, 0)).getRGB(), (new Color(r, g, b, 255)).getRGB());
               selectorHeight = 2.0F;
               outlineWidth = 0.5F;
               half = selectorHeight / 2.0F;
               csTop = sTop + alphaSelectorY - half;
               csBottom = sTop + alphaSelectorY + half;
               bx = sRight + outlineWidth;
               float ay = csTop - outlineWidth;
               float by = csBottom + outlineWidth;
               GL11.glDisable(3553);
               DrawHelper.setColor(-16777216);
               GL11.glBegin(2);
               GL11.glVertex2f(sLeft, ay);
               GL11.glVertex2f(sLeft, by);
               GL11.glVertex2f(bx, by);
               GL11.glVertex2f(bx, ay);
               GL11.glEnd();
               GL11.glEnable(3553);
          }

     }

     private void drawCheckeredBackground(float x, float y, float right, float bottom) {
          DrawHelper.drawRect((double)x, (double)y, (double)right, (double)bottom, -1);

          for(boolean off = false; y < bottom; ++y) {
               for(float x1 = x + (float)((off = !off) ? 1 : 0); x1 < right; x1 += 2.0F) {
                    DrawHelper.drawRect((double)x1, (double)y, (double)(x1 + 1.0F), (double)(y + 1.0F), -8355712);
               }
          }

     }

     private void updateColor(int hex, boolean hasAlpha) {
          if (hasAlpha) {
               this.colorSetting.setColorValue(hex);
          } else {
               this.colorSetting.setColorValue((new Color(hex >> 16 & 255, hex >> 8 & 255, hex & 255, (int)(this.alpha * 255.0F))).getRGB());
          }

     }

     public void onMouseClick(int mouseX, int mouseY, int button) {
          super.onMouseClick(mouseX, mouseY, button);
          if (this.isExpanded() && button == 0) {
               int x = this.getX();
               int y = this.getY();
               float cpLeft = (float)(x + 2);
               float cpTop = (float)(y + this.getHeight() + 2);
               float cpRight = (float)(x + 80 - 2);
               float cpBottom = (float)(y + this.getHeight() + 80 - 2);
               float sLeft = (float)(x + 80 - 1);
               float sTop = (float)(y + this.getHeight() + 2);
               float sRight = sLeft + 5.0F;
               float sBottom = (float)(y + this.getHeight() + 80 - 2);
               float asLeft = (float)(x + 80 + 6);
               float asTop = (float)(y + this.getHeight() + 2);
               float asRight = asLeft + 5.0F;
               float asBottom = (float)(y + this.getHeight() + 80 - 2);
               this.colorSelectorDragging = !this.colorSelectorDragging && (float)mouseX > cpLeft && (float)mouseY > cpTop && (float)mouseX < cpRight && (float)mouseY < cpBottom;
               this.hueSelectorDragging = !this.hueSelectorDragging && (float)mouseX > sLeft && (float)mouseY > sTop && (float)mouseX < sRight && (float)mouseY < sBottom;
               this.alphaSelectorDragging = !this.alphaSelectorDragging && (float)mouseX > asLeft && (float)mouseY > asTop && (float)mouseX < asRight && (float)mouseY < asBottom;
          }

     }

     public void onMouseRelease(int button) {
          if (this.hueSelectorDragging) {
               this.hueSelectorDragging = false;
          } else if (this.colorSelectorDragging) {
               this.colorSelectorDragging = false;
          } else if (this.alphaSelectorDragging) {
               this.alphaSelectorDragging = false;
          }

     }

     private float[] getHSBFromColor(int hex) {
          int r = hex >> 16 & 255;
          int g = hex >> 8 & 255;
          int b = hex & 255;
          return Color.RGBtoHSB(r, g, b, (float[])null);
     }

     public void drawColorPickerRect(float left, float top, float right, float bottom) {
          int hueBasedColor = Color.HSBtoRGB(this.hue, 1.0F, 1.0F);
          GL11.glDisable(3553);
          GlStateManager.enableBlend();
          GL11.glShadeModel(7425);
          buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
          buffer.pos((double)right, (double)top, 0.0D).color(hueBasedColor).endVertex();
          buffer.pos((double)left, (double)top, 0.0D).color(-1).endVertex();
          buffer.pos((double)left, (double)bottom, 0.0D).color(-1).endVertex();
          buffer.pos((double)right, (double)bottom, 0.0D).color(hueBasedColor).endVertex();
          tessellator.draw();
          buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
          buffer.pos((double)right, (double)top, 0.0D).color(402653184).endVertex();
          buffer.pos((double)left, (double)top, 0.0D).color(402653184).endVertex();
          buffer.pos((double)left, (double)bottom, 0.0D).color(-16777216).endVertex();
          buffer.pos((double)right, (double)bottom, 0.0D).color(-16777216).endVertex();
          tessellator.draw();
          GlStateManager.disableBlend();
          GL11.glShadeModel(7425);
          GL11.glEnable(3553);
     }

     public boolean canExpand() {
          return true;
     }

     public int getHeightWithExpand() {
          return this.getHeight() + 80;
     }

     public void onPress(int mouseX, int mouseY, int button) {
     }

     public Setting getSetting() {
          return this.colorSetting;
     }

     static {
          buffer = tessellator.getBuffer();
     }
}
