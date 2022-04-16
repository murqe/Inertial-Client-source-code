package me.rich.helpers;

import java.awt.Color;
import me.rich.helpers.gl.GLUtils;
import me.rich.helpers.math.MathHelper;
import me.rich.module.render.JumpCircle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public class DrawHelper {
      private static Minecraft mc = Minecraft.getMinecraft();
      private static int time;
      private static float animtest;
      private static boolean anim;
      private static int test;
      private static float alpheble;
      protected static float zLevel;
      private static final Frustum frustrum = new Frustum();

      public static void drawOutline(AxisAlignedBB axisAlignedBB, float width, Color color) {
            GL11.glPushMatrix();
            GlStateManager.glLineWidth(width);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();
            buffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
            buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
            buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
            buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
            buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
            buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
            buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
            buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
            buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
            buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
            buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
            buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
            buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
            buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
            buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
            buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
            buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
            tessellator.draw();
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glPopMatrix();
      }

      public static void draw3DCircle(JumpCircle.Circle circle, float partialTicks, double rad, float lineWidth, Color color) {
            GL11.glPushMatrix();
            GL11.glDisable(3553);
            GLUtils.startSmooth();
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            enableSmoothLine(lineWidth);
            GL11.glBegin(3);
            Minecraft mc = Minecraft.getMinecraft();
            double x = (double)circle.spawnX - mc.getRenderManager().viewerPosX;
            double y = (double)circle.spawnY - mc.getRenderManager().viewerPosY;
            double z = (double)circle.spawnZ - mc.getRenderManager().viewerPosZ;

            for(int i = 0; i <= 90; ++i) {
                  (new Color(i - i + 15, i, 120)).getRGB();
                  GL11.glVertex3d(x + rad * Math.cos((double)i * 6.283185307179586D / 45.0D), y, z + rad * Math.sin((double)i * 6.283185307179586D / 45.0D));
            }

            GL11.glEnd();
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            disableSmoothLine();
            GLUtils.endSmooth();
            GL11.glEnable(3553);
            GL11.glPopMatrix();
      }

      public static void enableSmoothLine(float width) {
            GL11.glDisable(3008);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glEnable(2884);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glHint(3155, 4354);
            GL11.glLineWidth(width);
      }

      public static void disableSmoothLine() {
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDisable(3042);
            GL11.glEnable(3008);
            GL11.glDepthMask(true);
            GL11.glCullFace(1029);
            GL11.glDisable(2848);
            GL11.glHint(3154, 4352);
            GL11.glHint(3155, 4352);
      }

      public static Color setAlpha(Color color, int alpha) {
            alpha = (int)MathHelper.clamp((double)alpha, 0.0D, 255.0D);
            return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
      }

      public static int setColor(int colorHex) {
            float alpha = (float)(colorHex >> 24 & 255) / 255.0F;
            float red = (float)(colorHex >> 16 & 255) / 255.0F;
            float green = (float)(colorHex >> 8 & 255) / 255.0F;
            float blue = (float)(colorHex & 255) / 255.0F;
            GL11.glColor4f(red, green, blue, alpha == 0.0F ? 1.0F : alpha);
            return colorHex;
      }

      public static Color astolfoColors45(float yDist, float yTotal, float saturation, float speedt) {
            float speed = 1800.0F;

            float hue;
            for(hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + (yTotal - yDist) * speedt; hue > speed; hue -= speed) {
            }

            hue /= speed;
            if ((double)hue > 0.5D) {
                  hue = 0.5F - (hue - 0.5F);
            }

            hue += 0.5F;
            return Color.getHSBColor(hue, saturation, 1.0F);
      }

      public static int astolfoColors(int yOffset, int yTotal, float saturation, float speedt) {
            float speed = 2900.0F;

            float hue;
            for(hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + (float)((yTotal - yOffset) * 9); hue > speed; hue -= speed) {
            }

            if ((double)(hue /= speed) > 0.5D) {
                  hue = 0.5F - (hue - 0.5F);
            }

            return Color.HSBtoRGB(hue += 0.5F, 0.5F, 1.0F);
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

      public static int astolfoColors5(float yDist, float yTotal, float saturation, float speedt) {
            float speed = 1800.0F;

            float hue;
            for(hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + (yTotal - yDist) * speedt; hue > speed; hue -= speed) {
            }

            hue /= speed;
            if ((double)hue > 0.5D) {
                  hue = 0.5F - (hue - 0.5F);
            }

            hue += 0.5F;
            return Color.HSBtoRGB(hue, saturation, 1.0F);
      }

      public static Color Rainbow1337(float yDist, float yTotal, float saturation, float speedt) {
            float speed = 3600.0F;

            float hue;
            for(hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + (yTotal - yDist) * speedt; hue > speed; hue -= speed) {
            }

            hue /= speed;
            if ((double)hue > 1.5D) {
                  hue = 1.5F - (hue - 1.5F);
            }

            ++hue;
            return Color.getHSBColor(hue, saturation, 1.0F);
      }

      public static void drawImage(ResourceLocation image, int x, int y, int width, int height, int color) {
            GL11.glPushMatrix();
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            setColor(color);
            mc.getTextureManager().bindTexture(image);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexParameteri(3553, 10241, 9729);
            Gui.drawModalRectWithCustomSizedTexture((float)x, (float)y, 0.0F, 0.0F, width, height, (float)width, (float)height);
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            GL11.glPopMatrix();
      }

      public static void prepareScissorBox(float x, float y, float x2, float y2) {
            ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
            int factor = ScaledResolution.getScaleFactor();
            GL11.glScissor((int)(x * (float)factor), (int)(((float)scale.getScaledHeight() - y2) * (float)factor), (int)((x2 - x) * (float)factor), (int)((y2 - y) * (float)factor));
      }

      public static void drawNewRect(double left, double top, double right, double bottom, int color) {
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
            BufferBuilder vertexbuffer = tessellator.getBuffer();
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.color(f, f1, f2, f3);
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
            vertexbuffer.pos(left, bottom, 0.0D).endVertex();
            vertexbuffer.pos(right, bottom, 0.0D).endVertex();
            vertexbuffer.pos(right, top, 0.0D).endVertex();
            vertexbuffer.pos(left, top, 0.0D).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
      }

      public static Color getColorWithOpacity(Color color, int alpha) {
            return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
      }

      public static Color TwoColoreffect(Color cl1, Color cl2, double speed) {
            double thing = speed / 4.0D % 1.0D;
            float val = MathHelper.clamp((double)((float)Math.sin(18.84955592153876D * thing) / 2.0F + 0.5F), 0.0D, 1.0D);
            return new Color(net.minecraft.util.math.MathHelper.lerp((float)cl1.getRed() / 255.0F, (float)cl2.getRed() / 255.0F, val), net.minecraft.util.math.MathHelper.lerp((float)cl1.getGreen() / 255.0F, (float)cl2.getGreen() / 255.0F, val), net.minecraft.util.math.MathHelper.lerp((float)cl1.getBlue() / 255.0F, (float)cl2.getBlue() / 255.0F, val));
      }
}
