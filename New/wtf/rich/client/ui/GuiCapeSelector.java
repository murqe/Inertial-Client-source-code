package wtf.rich.client.ui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.client.ui.settings.button.ImageButton;

public class GuiCapeSelector extends GuiScreen {
     protected ArrayList imageButtons = new ArrayList();
     private int width;
     private int height;
     private float spin;

     public void initGui() {
          ScaledResolution sr = new ScaledResolution(this.mc);
          this.width = sr.getScaledWidth() / 2;
          this.height = sr.getScaledHeight() / 2;
          this.imageButtons.clear();
          this.imageButtons.add(new ImageButton(new ResourceLocation("richclient/close-button.png"), this.width + 106, this.height - 135, 8, 8, "", 19));
          this.imageButtons.add(new ImageButton(new ResourceLocation("richclient/arrow/arrow-right.png"), this.width + 30, this.height + 52, 32, 25, "", 56));
          this.imageButtons.add(new ImageButton(new ResourceLocation("richclient/arrow/arrow-left.png"), this.width - 50, this.height + 52, 32, 25, "", 55));
          super.initGui();
     }

     public void drawScreen(int mouseX, int mouseY, float partialTicks) {
          this.drawWorldBackground(0);
          GlStateManager.pushMatrix();
          GlStateManager.disableBlend();
          GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
          DrawHelper.drawSkeetRectWithoutBorder((float)(this.width - 70), (float)(this.height - 80), (float)(this.width + 80), (float)(this.height + 20));
          DrawHelper.drawSkeetButton((float)(this.width - 70), (float)(this.height - 78), (float)(this.width + 80), (float)(this.height + 80));
          this.mc.neverlose500_16.drawStringWithOutline("Cape Changer", (double)(this.width - 100), (double)(this.height - 133), -1);
          this.drawEntityOnScreen((float)(this.width + 7), (float)(this.height + 38), this.spin, this.mc.player);
          this.spin = (float)((double)this.spin + 0.9D);
          Iterator var4 = this.imageButtons.iterator();

          while(var4.hasNext()) {
               ImageButton imageButton = (ImageButton)var4.next();
               imageButton.draw(mouseX, mouseY, Color.LIGHT_GRAY);
          }

          GlStateManager.popMatrix();
          super.drawScreen(mouseX, mouseY, partialTicks);
     }

     protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
          if (mouseButton == 0) {
               Iterator var4 = this.imageButtons.iterator();

               while(var4.hasNext()) {
                    ImageButton imageButton = (ImageButton)var4.next();
                    imageButton.onClick(mouseX, mouseY);
               }
          }

          super.mouseClicked(mouseX, mouseY, mouseButton);
     }

     private void drawEntityOnScreen(float posX, float posY, float mouseX, EntityLivingBase entity) {
          GlStateManager.enableColorMaterial();
          GlStateManager.pushMatrix();
          GlStateManager.translate(posX, posY, 50.0F);
          GlStateManager.scale(-80.0F, 80.0F, 80.0F);
          GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
          float f = entity.renderYawOffset;
          float f1 = entity.rotationYaw;
          float f2 = entity.rotationPitchHead;
          float f3 = entity.prevRotationYawHead;
          float f4 = entity.rotationYawHead;
          GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
          RenderHelper.enableStandardItemLighting();
          entity.renderYawOffset = mouseX;
          entity.rotationYaw = mouseX;
          entity.rotationPitchHead = 0.0F;
          entity.rotationYawHead = entity.rotationYaw;
          entity.prevRotationYawHead = entity.rotationYaw;
          entity.prevRotationPitchHead = 0.0F;
          GlStateManager.translate(0.0F, 0.0F, 0.0F);
          RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
          rendermanager.setPlayerViewY(180.0F);
          rendermanager.setRenderShadow(false);
          rendermanager.doRenderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
          rendermanager.setRenderShadow(true);
          entity.renderYawOffset = f;
          entity.rotationYaw = f1;
          entity.rotationPitchHead = f2;
          entity.prevRotationPitchHead = f2;
          entity.prevRotationYawHead = f3;
          entity.rotationYawHead = f4;
          GlStateManager.popMatrix();
          RenderHelper.disableStandardItemLighting();
          GlStateManager.disableRescaleNormal();
          GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
          GlStateManager.disableTexture2D();
          GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
     }

     public static class Selector {
          public static String capeName;

          public static String getCapeName() {
               return capeName;
          }

          public static void setCapeName(String capeName) {
               GuiCapeSelector.Selector.capeName = capeName;
          }
     }
}
