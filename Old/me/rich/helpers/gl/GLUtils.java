package me.rich.helpers.gl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public final class GLUtils {
      private static final AxisAlignedBB DEFAULT_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
      private static final Minecraft mc = Minecraft.getMinecraft();
      private static final Random random = new Random();
      private static final Tessellator tessellator = Tessellator.getInstance();
      public static float zLevel;
      public static List vbos = new ArrayList();
      public static List textures = new ArrayList();

      public static void glScissor(int[] rect) {
            glScissor((float)rect[0], (float)rect[1], (float)(rect[0] + rect[2]), (float)(rect[1] + rect[3]));
      }

      public static void glScissor(float x, float y, float x1, float y1) {
            int scaleFactor = getScaleFactor();
            GL11.glScissor((int)(x * (float)scaleFactor), (int)((float)Minecraft.getMinecraft().displayHeight - y1 * (float)scaleFactor), (int)((x1 - x) * (float)scaleFactor), (int)((y1 - y) * (float)scaleFactor));
      }

      public static int getScaleFactor() {
            int scaleFactor = 1;
            boolean isUnicode = Minecraft.getMinecraft().isUnicode();
            int guiScale = Minecraft.getMinecraft().gameSettings.guiScale;
            if (guiScale == 0) {
                  guiScale = 1000;
            }

            while(scaleFactor < guiScale && Minecraft.getMinecraft().displayWidth / (scaleFactor + 1) >= 320 && Minecraft.getMinecraft().displayHeight / (scaleFactor + 1) >= 240) {
                  ++scaleFactor;
            }

            if (isUnicode && scaleFactor % 2 != 0 && scaleFactor != 1) {
                  --scaleFactor;
            }

            return scaleFactor;
      }

      public static int getMouseX() {
            return Mouse.getX() * getScreenWidth() / Minecraft.getMinecraft().displayWidth;
      }

      public static int getMouseY() {
            return getScreenHeight() - Mouse.getY() * getScreenHeight() / Minecraft.getMinecraft().displayWidth - 1;
      }

      public static int getScreenWidth() {
            return Minecraft.getMinecraft().displayWidth / getScaleFactor();
      }

      public static int getScreenHeight() {
            return Minecraft.getMinecraft().displayHeight / getScaleFactor();
      }

      public static boolean isHovered(int x, int y, int width, int height, int mouseX, int mouseY) {
            return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY < y + height;
      }

      public static int genVBO() {
            int id = GL15.glGenBuffers();
            vbos.add(id);
            GL15.glBindBuffer(34962, id);
            return id;
      }

      public static int getTexture() {
            int textureID = GL11.glGenTextures();
            textures.add(textureID);
            return textureID;
      }

      public static int applyTexture(int texId, File file, int filter, int wrap) throws IOException {
            applyTexture(texId, ImageIO.read(file), filter, wrap);
            return texId;
      }

      public static int applyTexture(int texId, BufferedImage image, int filter, int wrap) {
            int[] pixels = new int[image.getWidth() * image.getHeight()];
            image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
            ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

            for(int y = 0; y < image.getHeight(); ++y) {
                  for(int x = 0; x < image.getWidth(); ++x) {
                        int pixel = pixels[y * image.getWidth() + x];
                        buffer.put((byte)(pixel >> 16 & 255));
                        buffer.put((byte)(pixel >> 8 & 255));
                        buffer.put((byte)(pixel & 255));
                        buffer.put((byte)(pixel >> 24 & 255));
                  }
            }

            buffer.flip();
            applyTexture(texId, image.getWidth(), image.getHeight(), buffer, filter, wrap);
            return texId;
      }

      public static int applyTexture(int texId, int width, int height, ByteBuffer pixels, int filter, int wrap) {
            GL11.glBindTexture(3553, texId);
            GL11.glTexParameteri(3553, 10241, filter);
            GL11.glTexParameteri(3553, 10240, filter);
            GL11.glTexParameteri(3553, 10242, wrap);
            GL11.glTexParameteri(3553, 10243, wrap);
            GL11.glPixelStorei(3317, 1);
            GL11.glTexImage2D(3553, 0, 32856, width, height, 0, 6408, 5121, pixels);
            GL11.glBindTexture(3553, 0);
            return texId;
      }

      public static void cleanup() {
            GL15.glBindBuffer(34962, 0);
            GL11.glBindTexture(3553, 0);
            Iterator var0 = vbos.iterator();

            int texture;
            while(var0.hasNext()) {
                  texture = (Integer)var0.next();
                  GL15.glDeleteBuffers(texture);
            }

            var0 = textures.iterator();

            while(var0.hasNext()) {
                  texture = (Integer)var0.next();
                  GL11.glDeleteTextures(texture);
            }

      }

      public static void drawBorderRect(float x, float y, float x1, float y1, float borderSize) {
            drawBorder(borderSize, x, y, x1, y1);
            drawRect(x, y, x1, y1);
      }

      public static void drawBorder(float size, float x, float y, float x1, float y1) {
            GL11.glLineWidth(size);
            GlStateManager.disableTexture2D();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            BufferBuilder vertexBuffer = tessellator.getBuffer();
            vertexBuffer.begin(2, DefaultVertexFormats.POSITION);
            vertexBuffer.pos((double)x, (double)y, 0.0D).endVertex();
            vertexBuffer.pos((double)x, (double)y1, 0.0D).endVertex();
            vertexBuffer.pos((double)x1, (double)y1, 0.0D).endVertex();
            vertexBuffer.pos((double)x1, (double)y, 0.0D).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
      }

      public static void drawRect(float x, float y, float w, float h) {
            GlStateManager.disableTexture2D();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            BufferBuilder vertexBuffer = tessellator.getBuffer();
            vertexBuffer.begin(7, DefaultVertexFormats.POSITION);
            vertexBuffer.pos((double)x, (double)h, 0.0D).endVertex();
            vertexBuffer.pos((double)w, (double)h, 0.0D).endVertex();
            vertexBuffer.pos((double)w, (double)y, 0.0D).endVertex();
            vertexBuffer.pos((double)x, (double)y, 0.0D).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
      }

      public static void drawSolidBox() {
            drawSolidBox(DEFAULT_AABB);
      }

      public static void drawSolidBox(AxisAlignedBB bb) {
            GL11.glBegin(7);
            GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
            GL11.glEnd();
      }

      public static void drawOutlinedBox() {
            drawOutlinedBox(DEFAULT_AABB);
      }

      public static void drawOutlinedBox(AxisAlignedBB bb) {
            GL11.glBegin(1);
            GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
            GL11.glEnd();
      }

      public static void drawSelectionBoundingBox(AxisAlignedBB boundingBox) {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder vertexbuffer = tessellator.getBuffer();
            vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
            vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
            vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
            vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
            vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
            vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
            tessellator.draw();
            vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
            vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
            vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
            vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
            vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
            vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
            tessellator.draw();
            vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
            vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
            vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
            vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
            vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
            vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
            vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
            vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
            vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
            tessellator.draw();
      }

      public static void drawBlockESP(BlockPos pos, float red, float green, float blue) {
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(2848);
            GL11.glLineWidth(1.0F);
            GL11.glDisable(3553);
            GL11.glEnable(2884);
            GL11.glDisable(2929);
            GL11.glDisable(2896);
            double renderPosX = mc.getRenderManager().viewerPosX;
            double renderPosY = mc.getRenderManager().viewerPosY;
            double renderPosZ = mc.getRenderManager().viewerPosZ;
            GL11.glTranslated(-renderPosX, -renderPosY, -renderPosZ);
            GL11.glTranslated((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
            GL11.glColor4f(red, green, blue, 0.3F);
            drawSolidBox();
            GL11.glColor4f(red, green, blue, 0.7F);
            drawOutlinedBox();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(2896);
            GL11.glEnable(2929);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glDisable(2848);
            GL11.glPopMatrix();
      }

      public static void drawGradientRect(int x, int y, int w, int h, int startColor, int endColor) {
            float f = (float)(startColor >> 24 & 255) / 255.0F;
            float f1 = (float)(startColor >> 16 & 255) / 255.0F;
            float f2 = (float)(startColor >> 8 & 255) / 255.0F;
            float f3 = (float)(startColor & 255) / 255.0F;
            float f4 = (float)(endColor >> 24 & 255) / 255.0F;
            float f5 = (float)(endColor >> 16 & 255) / 255.0F;
            float f6 = (float)(endColor >> 8 & 255) / 255.0F;
            float f7 = (float)(endColor & 255) / 255.0F;
            GlStateManager.disableTexture2D();
            GlStateManager.enableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.shadeModel(7425);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder vertexbuffer = tessellator.getBuffer();
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            vertexbuffer.pos((double)x + (double)w, (double)y, 0.0D).color(f1, f2, f3, f).endVertex();
            vertexbuffer.pos((double)x, (double)y, 0.0D).color(f1, f2, f3, f).endVertex();
            vertexbuffer.pos((double)x, (double)y + (double)h, 0.0D).color(f5, f6, f7, f4).endVertex();
            vertexbuffer.pos((double)x + (double)w, (double)y + (double)h, 0.0D).color(f5, f6, f7, f4).endVertex();
            tessellator.draw();
            GlStateManager.shadeModel(7424);
            GlStateManager.disableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.enableTexture2D();
      }

      public static void enableGL2D() {
            GL11.glDisable(2929);
            GL11.glEnable(3042);
            GL11.glDisable(3553);
            GL11.glBlendFunc(770, 771);
            GL11.glDepthMask(true);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glHint(3155, 4354);
      }

      public static void disableGL2D() {
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glHint(3154, 4352);
            GL11.glHint(3155, 4352);
      }

      public static void glColor(float red, float green, float blue, float alpha) {
            GlStateManager.color(red, green, blue, alpha);
      }

      public static void glColor(Color color) {
            GlStateManager.color((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
      }

      public static void glColor(int color) {
            GlStateManager.color((float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, (float)(color >> 24 & 255) / 255.0F);
      }

      public static Color getHSBColor(float hue, float sturation, float luminance) {
            return Color.getHSBColor(hue, sturation, luminance);
      }

      public static Color getRandomColor(int saturationRandom, float luminance) {
            float hue = random.nextFloat();
            float saturation = ((float)random.nextInt(saturationRandom) + (float)saturationRandom) / (float)saturationRandom + (float)saturationRandom;
            return getHSBColor(hue, saturation, luminance);
      }

      public static Color getRandomColor() {
            return getRandomColor(1000, 0.6F);
      }

      public static void startSmooth() {
            GL11.glEnable(2848);
            GL11.glEnable(2881);
            GL11.glEnable(2832);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glHint(3154, 4354);
            GL11.glHint(3155, 4354);
            GL11.glHint(3153, 4354);
      }

      public static void endSmooth() {
            GL11.glDisable(2848);
            GL11.glDisable(2881);
            GL11.glEnable(2832);
      }
}
