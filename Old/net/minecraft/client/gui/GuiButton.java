package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

public class GuiButton extends Gui {
      protected static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("textures/gui/widgets.png");
      protected int width;
      float speed;
      private static int fade = 20;
      protected int height;
      public int xPosition;
      public int yPosition;
      public String displayString;
      public int id;
      public boolean enabled;
      public boolean visible;
      protected boolean hovered;

      public GuiButton(int buttonId, int x, int y, String buttonText) {
            this(buttonId, x, y, 200, 20, buttonText);
      }

      public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
            this.speed = 0.0F;
            this.width = 200;
            this.height = 20;
            this.enabled = true;
            this.visible = true;
            this.id = buttonId;
            this.xPosition = x;
            this.yPosition = y;
            this.width = widthIn;
            this.height = heightIn;
            this.displayString = buttonText;
      }

      protected int getHoverState(boolean mouseOver) {
            int i = 1;
            if (!this.enabled) {
                  i = 0;
            } else if (mouseOver) {
                  i = 2;
            }

            return i;
      }

      public void func_191745_a(Minecraft mc, int p_1917452, int p_1917453, float p_1917454) {
            if (this.visible) {
                  FontRenderer fontrenderer = Minecraft.fontRendererObj;
                  mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
                  GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                  this.hovered = p_1917452 >= this.xPosition && p_1917453 >= this.yPosition && p_1917452 < this.xPosition + this.width && p_1917453 < this.yPosition + this.height;
                  int i = this.getHoverState(this.hovered);
                  GlStateManager.enableBlend();
                  GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                  GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                  this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + i * 20, this.width / 2, this.height);
                  this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
                  this.mouseDragged(mc, p_1917452, p_1917453);
                  int j = 14737632;
                  if (!this.enabled) {
                        j = 10526880;
                  } else if (this.hovered) {
                        j = 16777120;
                  }

                  drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, j);
            }

      }

      protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
      }

      public void mouseReleased(int mouseX, int mouseY) {
      }

      public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
            return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
      }

      public boolean isMouseOver() {
            return this.hovered;
      }

      public void drawButtonForegroundLayer(int mouseX, int mouseY) {
      }

      public void playPressSound(SoundHandler soundHandlerIn) {
            soundHandlerIn.playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
      }

      public int getButtonWidth() {
            return this.width;
      }

      public static void drawRect(double left, double top, double right, double bottom, int color) {
            double j;
            if (left < right) {
                  j = left;
                  left = right;
                  right = j;
            }

            if (top < bottom) {
                  j = top;
                  top = bottom;
                  bottom = j;
            }

            float f3 = (float)(color >> 24 & 255) / 255.0F;
            float f = (float)(color >> 16 & 255) / 255.0F;
            float f1 = (float)(color >> 8 & 255) / 255.0F;
            float f2 = (float)(color & 255) / 255.0F;
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.color(f, f1, f2, f3);
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
            bufferBuilder.pos(left, bottom, 0.0D).endVertex();
            bufferBuilder.pos(right, bottom, 0.0D).endVertex();
            bufferBuilder.pos(right, top, 0.0D).endVertex();
            bufferBuilder.pos(left, top, 0.0D).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
      }

      public void setWidth(int width) {
            this.width = width;
      }
}
