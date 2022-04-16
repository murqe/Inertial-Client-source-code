package wtf.rich.api.utils.shader;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class FramebufferShader extends Shader {
     public static Minecraft mc = Minecraft.getMinecraft();
     private Framebuffer framebuffer;
     protected float red;
     protected float green;
     protected float blue;
     protected float alpha = 1.0F;
     protected float radius = 2.0F;
     protected float quality = 1.0F;
     protected float saturation;
     protected float speed;
     protected float x;
     protected float y;
     private boolean entityShadows;

     public FramebufferShader(String fragmentShader) {
          super(fragmentShader);
     }

     public void startDraw(float partialTicks) {
          GlStateManager.enableAlpha();
          GlStateManager.pushMatrix();
          GlStateManager.pushAttrib();
          (this.framebuffer = this.setupFrameBuffer(this.framebuffer)).framebufferClear();
          this.framebuffer.bindFramebuffer(true);
          this.entityShadows = mc.gameSettings.entityShadows;
          mc.gameSettings.entityShadows = false;
          mc.entityRenderer.setupCameraTransform(partialTicks, 0);
     }

     public void stopDraw(Color color, float radius, float quality, float saturation, float speed, float x, float y) {
          mc.gameSettings.entityShadows = this.entityShadows;
          this.framebuffer.unbindFramebuffer();
          GL11.glEnable(3042);
          GL11.glBlendFunc(770, 771);
          mc.getFramebuffer().bindFramebuffer(true);
          this.saturation = saturation;
          this.speed = speed;
          this.x = x;
          this.y = y;
          this.red = (float)color.getRed() / 255.0F;
          this.green = (float)color.getGreen() / 255.0F;
          this.blue = (float)color.getBlue() / 255.0F;
          this.alpha = (float)color.getAlpha() / 255.0F;
          this.radius = radius;
          this.quality = quality;
          mc.entityRenderer.disableLightmap();
          RenderHelper.disableStandardItemLighting();
          this.startShader();
          mc.entityRenderer.setupOverlayRendering();
          this.drawFramebuffer(this.framebuffer);
          this.stopShader();
          mc.entityRenderer.disableLightmap();
          GlStateManager.popMatrix();
          GlStateManager.popAttrib();
     }

     public Framebuffer setupFrameBuffer(Framebuffer frameBuffer) {
          if (frameBuffer == null) {
               return new Framebuffer(mc.displayWidth, mc.displayHeight, true);
          } else {
               if (frameBuffer.framebufferWidth != mc.displayWidth || frameBuffer.framebufferHeight != mc.displayHeight) {
                    frameBuffer.deleteFramebuffer();
                    frameBuffer = new Framebuffer(mc.displayWidth, mc.displayHeight, true);
               }

               return frameBuffer;
          }
     }

     public void drawFramebuffer(Framebuffer framebuffer) {
          ScaledResolution scaledResolution = new ScaledResolution(mc);
          GL11.glBindTexture(3553, framebuffer.framebufferTexture);
          GL11.glBegin(7);
          GL11.glTexCoord2d(0.0D, 1.0D);
          GL11.glVertex2d(0.0D, 0.0D);
          GL11.glTexCoord2d(0.0D, 0.0D);
          GL11.glVertex2d(0.0D, (double)scaledResolution.getScaledHeight());
          GL11.glTexCoord2d(1.0D, 0.0D);
          GL11.glVertex2d((double)scaledResolution.getScaledWidth(), (double)scaledResolution.getScaledHeight());
          GL11.glTexCoord2d(1.0D, 1.0D);
          GL11.glVertex2d((double)scaledResolution.getScaledWidth(), 0.0D);
          GL11.glEnd();
          GL20.glUseProgram(0);
     }

     public Framebuffer getFramebuffer() {
          return this.framebuffer;
     }
}
