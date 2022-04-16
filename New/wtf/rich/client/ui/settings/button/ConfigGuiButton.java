package wtf.rich.client.ui.settings.button;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import org.lwjgl.input.Mouse;
import wtf.rich.api.utils.render.DrawHelper;

public class ConfigGuiButton extends GuiButton {
     private int fade;

     public ConfigGuiButton(int buttonId, int x, int y, String buttonText) {
          this(buttonId, x, y, 200, 35, buttonText);
     }

     public ConfigGuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
          super(buttonId, x, y, widthIn, heightIn, buttonText);
          this.fade = 20;
     }

     public static int getMouseX() {
          return Mouse.getX() * sr.getScaledWidth() / Minecraft.getMinecraft().displayWidth;
     }

     public static int getMouseY() {
          return sr.getScaledHeight() - Mouse.getY() * sr.getScaledHeight() / Minecraft.getMinecraft().displayHeight - 1;
     }

     public void drawButton(Minecraft mc, int mouseX, int mouseY, float mouseButton) {
          if (this.visible) {
               int height = this.height - 31;
               this.hovered = mouseX >= this.xPosition + 93 && mouseY >= this.yPosition - 41 && mouseX < this.xPosition + this.width - 43 && mouseY < height + this.yPosition - 30;
               Color text = new Color(155, 155, 155, 255);
               if (this.enabled) {
                    if (this.hovered) {
                         if (this.fade < 100) {
                              this.fade += 8;
                         }

                         text = Color.white;
                    } else if (this.fade > 20) {
                         this.fade -= 8;
                    }
               }

               GlStateManager.enableBlend();
               GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
               GlStateManager.blendFunc(770, 771);
               DrawHelper.drawRect((double)(this.xPosition + 155), (double)(this.yPosition - 40), (double)(this.xPosition + 95), (double)(height + this.yPosition - 30), (new Color(1, 1, 1, 30)).getRGB());
               Minecraft.fontRendererObj.drawCenteredString(this.displayString, (float)this.xPosition + (float)this.width / 2.0F + 25.0F, (float)(this.yPosition + (this.height - 73)), text.getRGB());
               this.mouseDragged(mc, mouseX, mouseY);
          }

     }

     protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
     }

     public void mouseReleased(int mouseX, int mouseY) {
     }

     public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
          int height = this.height - 31;
          return this.enabled && this.visible && mouseX >= this.xPosition + 93 && mouseY >= this.yPosition - 45 && mouseX < this.xPosition + this.width - 43 && mouseY < height + this.yPosition - 30;
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
