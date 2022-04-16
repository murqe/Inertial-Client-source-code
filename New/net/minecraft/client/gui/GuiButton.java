package net.minecraft.client.gui;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import wtf.rich.api.utils.render.DrawHelper;

public class GuiButton extends Gui {
     protected static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("textures/gui/widgets.png");
     protected int width;
     protected int height;
     public int xPosition;
     public int yPosition;
     public String displayString;
     public int id;
     public boolean enabled;
     public boolean visible;
     protected boolean hovered;
     private int fade;
     private int fadeOutline;
     public static ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

     public GuiButton(int buttonId, int x, int y, String buttonText) {
          this(buttonId, x, y, 200, 20, buttonText);
     }

     public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
          this.fade = 20;
          this.fadeOutline = 20;
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

     public void drawButton(Minecraft mc, int mouseX, int mouseY, float mouseButton) {
          if (this.visible) {
               this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
               Color text = new Color(215, 215, 215, 255);
               Color color = new Color(0, 0, 0, 73);
               if (this.hovered) {
                    if (this.fade < 100) {
                         this.fade += 7;
                    }

                    text = Color.white;
               } else if (this.fade > 20) {
                    this.fade -= 7;
               }

               new Color(this.fade + 60, this.fade, this.fade);
               if (this.hovered) {
                    if (this.fadeOutline < 100) {
                         this.fadeOutline += 7;
                    }
               } else if (this.fadeOutline > 20) {
                    this.fadeOutline -= 7;
               }

               DrawHelper.drawOutlineRect((float)this.xPosition, (float)this.yPosition, (float)this.width, (float)this.height, color, new Color(255, 255, 255, 0));
               mc.neverlose500_20.drawCenteredString(this.displayString, (float)this.xPosition + (float)this.width / 2.0F, (float)this.yPosition + ((float)this.height - 6.5F) / 2.0F, text.getRGB());
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

     public void setWidth(int width) {
          this.width = width;
     }
}
