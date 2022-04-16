package wtf.rich.api.utils.render;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.text.NumberFormat;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import wtf.rich.api.utils.Helper;
import wtf.rich.api.utils.combat.KillAuraHelper;
import wtf.rich.api.utils.shader.ShaderShell;
import wtf.rich.client.features.impl.combat.KillAura;
import wtf.rich.client.features.impl.display.ClickGUI;
import wtf.rich.client.features.impl.display.FeatureList;

public class DrawHelper implements Helper {
     private static Minecraft mc = Minecraft.getMinecraft();
     private static int time;
     private static float animtest;
     private static boolean anim;
     private static int test;
     private static float alpheble;
     protected static float zLevel;
     public static Frustum frustum = new Frustum();
     private static final Frustum frustrum = new Frustum();
     private static final Vec3d LIGHT0_POS = (new Vec3d(0.20000000298023224D, 1.0D, -0.699999988079071D)).normalize();
     private static final Vec3d LIGHT1_POS = (new Vec3d(-0.20000000298023224D, 1.0D, 0.699999988079071D)).normalize();

     public static int getColor2(Color color) {
          return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
     }

     public static void blockEspFrame(BlockPos blockPos, float red, float green, float blue) {
          double var10000 = (double)blockPos.getX();
          mc.getRenderManager();
          double x = var10000 - RenderManager.renderPosX;
          var10000 = (double)blockPos.getY();
          mc.getRenderManager();
          double y = var10000 - RenderManager.renderPosY;
          var10000 = (double)blockPos.getZ();
          mc.getRenderManager();
          double z = var10000 - RenderManager.renderPosZ;
          GL11.glBlendFunc(770, 771);
          GL11.glEnable(3042);
          GL11.glLineWidth(2.0F);
          GL11.glDisable(3553);
          GL11.glDisable(2929);
          GL11.glDepthMask(false);
          GlStateManager.color(red, green, blue, 1.0F);
          drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
          GL11.glEnable(3553);
          GL11.glEnable(2929);
          GL11.glDepthMask(true);
          GL11.glDisable(3042);
     }

     public static void drawCompleteImage(double posX, double posY, double width, double height) {
          GL11.glPushMatrix();
          GL11.glTranslatef((float)posX, (float)posY, 0.0F);
          GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
          GL11.glBegin(7);
          GL11.glTexCoord2f(0.0F, 0.0F);
          GL11.glVertex3f(0.0F, 0.0F, 0.0F);
          GL11.glTexCoord2f(0.0F, 1.0F);
          GL11.glVertex3f(0.0F, (float)height, 0.0F);
          GL11.glTexCoord2f(1.0F, 1.0F);
          GL11.glVertex3f((float)width, (float)height, 0.0F);
          GL11.glTexCoord2f(1.0F, 0.0F);
          GL11.glVertex3f((float)width, 0.0F, 0.0F);
          GL11.glEnd();
          GL11.glPopMatrix();
     }

     public static void drawTriangle(float cx, float cy, float r, float n, int color) {
          cx = (float)((double)cx * 2.0D);
          cy = (float)((double)cy * 2.0D);
          float b = 6.283185F / n;
          float p = (float)Math.cos((double)b);
          float s = (float)Math.sin((double)b);
          float x = r *= 3.0F;
          float y = 0.0F;
          GL11.glPushMatrix();
          enableGL2D();
          GL11.glLineWidth(2.0F);
          GL11.glScalef(0.5F, 0.5F, 0.5F);
          setColor(color);
          GL11.glDisable(2929);
          GL11.glDepthMask(false);
          enableSmoothLine(1.5F);
          GL11.glBegin(2);

          for(int ii = 0; (float)ii < n; ++ii) {
               GL11.glVertex2f(x + cx, y + cy);
               float t = x;
               x = p * x - s * y;
               y = s * t + p * y;
          }

          GL11.glEnd();
          GL11.glScalef(4.0F, 4.0F, 4.0F);
          disableSmoothLine();
          disableGL2D();
          GL11.glEnable(2929);
          GL11.glDepthMask(true);
          GL11.glPopMatrix();
     }

     public static boolean isInViewFrustum(Entity entity) {
          return isInViewFrustum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
     }

     private static boolean isInViewFrustum(AxisAlignedBB bb) {
          Entity current = mc.getRenderViewEntity();
          if (current != null) {
               frustum.setPosition(current.posX, current.posY, current.posZ);
          }

          return frustum.isBoundingBoxInFrustum(bb);
     }

     public static void drawCircle3D(Entity entity, double radius, float partialTicks, int points, float width, int color) {
          GL11.glPushMatrix();
          GL11.glDisable(3553);
          GL11.glEnable(2848);
          GL11.glHint(3154, 4354);
          GL11.glDisable(2929);
          GL11.glLineWidth(width);
          GL11.glEnable(3042);
          GL11.glBlendFunc(770, 771);
          GL11.glDisable(2929);
          GL11.glBegin(3);
          double var10000 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks;
          mc.getRenderManager();
          double x = var10000 - RenderManager.renderPosX;
          var10000 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks;
          mc.getRenderManager();
          double y = var10000 - RenderManager.renderPosY;
          var10000 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks;
          mc.getRenderManager();
          double z = var10000 - RenderManager.renderPosZ;
          setColor(color);

          for(int i = 0; i <= points; ++i) {
               GL11.glVertex3d(x + radius * Math.cos((double)((float)i * 6.2831855F / (float)points)), y, z + radius * Math.sin((double)((float)i * 6.2831855F / (float)points)));
          }

          GL11.glEnd();
          GL11.glDepthMask(true);
          GL11.glDisable(3042);
          GL11.glEnable(2929);
          GL11.glDisable(2848);
          GL11.glEnable(2929);
          GL11.glEnable(3553);
          GL11.glPopMatrix();
     }

     public static void drawEntityBox(Entity entity, Color color, boolean fullBox, float alpha) {
          GlStateManager.pushMatrix();
          GlStateManager.blendFunc(770, 771);
          GL11.glEnable(3042);
          GlStateManager.glLineWidth(2.0F);
          GlStateManager.disableTexture2D();
          GL11.glDisable(2929);
          GlStateManager.depthMask(false);
          double var10000 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)mc.timer.renderPartialTicks;
          mc.getRenderManager();
          double x = var10000 - RenderManager.renderPosX;
          var10000 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)mc.timer.renderPartialTicks;
          mc.getRenderManager();
          double y = var10000 - RenderManager.renderPosY;
          var10000 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)mc.timer.renderPartialTicks;
          mc.getRenderManager();
          double z = var10000 - RenderManager.renderPosZ;
          AxisAlignedBB axisAlignedBB = entity.getEntityBoundingBox();
          AxisAlignedBB axisAlignedBB2 = new AxisAlignedBB(axisAlignedBB.minX - entity.posX + x - 0.05D, axisAlignedBB.minY - entity.posY + y, axisAlignedBB.minZ - entity.posZ + z - 0.05D, axisAlignedBB.maxX - entity.posX + x + 0.05D, axisAlignedBB.maxY - entity.posY + y + 0.15D, axisAlignedBB.maxZ - entity.posZ + z + 0.05D);
          GlStateManager.glLineWidth(2.0F);
          GL11.glEnable(2848);
          GlStateManager.color((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, alpha);
          if (fullBox) {
               drawColorBox(axisAlignedBB2, (float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, alpha);
               GlStateManager.color(0.0F, 0.0F, 0.0F, 0.5F);
          }

          drawSelectionBoundingBox(axisAlignedBB2);
          GlStateManager.glLineWidth(2.0F);
          GlStateManager.enableTexture2D();
          GL11.glEnable(2929);
          GlStateManager.depthMask(true);
          GlStateManager.disableBlend();
          GlStateManager.popMatrix();
     }

     public static Color injectAlpha(Color color, int alpha) {
          return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
     }

     public static void drawOutlineRect(float x, float y, float width, float height, Color color, Color colorTwo) {
          drawRect((double)x, (double)y, (double)(x + width), (double)(y + height), color.getRGB());
          int colorRgb = colorTwo.getRGB();
          drawRect((double)(x - 1.0F), (double)y, (double)x, (double)(y + height), colorRgb);
          drawRect((double)(x + width), (double)y, (double)(x + width + 1.0F), (double)(y + height), colorRgb);
          drawRect((double)(x - 1.0F), (double)(y - 1.0F), (double)(x + width + 1.0F), (double)y, colorRgb);
          drawRect((double)(x - 1.0F), (double)(y + height), (double)(x + width + 1.0F), (double)(y + height + 1.0F), colorRgb);
     }

     public static void drawGlowRoundedRect(float startX, float startY, float endX, float endY, int color, float radius, float force) {
          GL11.glPushMatrix();
          GL11.glEnable(3042);
          GL11.glDisable(3008);
          float alpha = (float)(color >> 24 & 255) / 255.0F;
          float red = (float)(color >> 16 & 255) / 255.0F;
          float green = (float)(color >> 8 & 255) / 255.0F;
          float blue = (float)(color & 255) / 255.0F;
          ShaderShell.ROUNDED_RECT.attach();
          ShaderShell.ROUNDED_RECT.set4F("color", red, green, blue, alpha);
          ShaderShell.ROUNDED_RECT.set2F("resolution", (float)Minecraft.getMinecraft().displayWidth, (float)Minecraft.getMinecraft().displayHeight);
          ShaderShell.ROUNDED_RECT.set2F("center", (startX + (endX - startX) / 2.0F) * 2.0F, (startY + (endY - startY) / 2.0F) * 2.0F);
          ShaderShell.ROUNDED_RECT.set2F("dst", (endX - startX - radius) * 2.0F, (endY - startY - radius) * 2.0F);
          ShaderShell.ROUNDED_RECT.set1F("radius", radius);
          ShaderShell.ROUNDED_RECT.set1F("force", force);
          GL11.glBegin(7);
          GL11.glVertex2d((double)endX, (double)startY);
          GL11.glVertex2d((double)startX, (double)startY);
          GL11.glVertex2d((double)startX, (double)endY);
          GL11.glVertex2d((double)endX, (double)endY);
          GL11.glEnd();
          ShaderShell.ROUNDED_RECT.detach();
          GL11.glEnable(3008);
          GL11.glDisable(3042);
          GL11.glPopMatrix();
     }

     public static void scissorRect(float x, float y, float width, double height) {
          ScaledResolution sr = new ScaledResolution(mc);
          int factor = ScaledResolution.getScaleFactor();
          GL11.glScissor((int)(x * (float)factor), (int)(((double)((float)sr.getScaledHeight()) - height) * (double)((float)factor)), (int)((width - x) * (float)factor), (int)((height - (double)y) * (double)((float)factor)));
     }

     public static void drawSkeetButton(float x, float y, float right, float bottom) {
          drawSmoothRect(x - 31.0F, y - 43.0F, right + 31.0F, bottom - 30.0F, (new Color(0, 0, 0, 255)).getRGB());
          drawSmoothRect(x - 30.5F, y - 42.5F, right + 30.5F, bottom - 30.5F, (new Color(45, 45, 45, 255)).getRGB());
          drawGradientRect((double)((int)x - 30), (double)((int)y - 42), (double)(right + 30.0F), (double)(bottom - 31.0F), (new Color(48, 48, 48, 255)).getRGB(), (new Color(19, 19, 19, 255)).getRGB());
     }

     public static void drawBorder(float left, float top, float right, float bottom, float borderWidth, int insideColor, int borderColor, boolean borderIncludedInBounds) {
          drawRect((double)(left - (!borderIncludedInBounds ? borderWidth : 0.0F)), (double)(top - (!borderIncludedInBounds ? borderWidth : 0.0F)), (double)(right + (!borderIncludedInBounds ? borderWidth : 0.0F)), (double)(bottom + (!borderIncludedInBounds ? borderWidth : 0.0F)), borderColor);
          drawRect((double)(left + (borderIncludedInBounds ? borderWidth : 0.0F)), (double)(top + (borderIncludedInBounds ? borderWidth : 0.0F)), (double)(right - (borderIncludedInBounds ? borderWidth : 0.0F)), (double)(bottom - (borderIncludedInBounds ? borderWidth : 0.0F)), insideColor);
     }

     public static void drawSkeetRectWithoutBorder(float x, float y, float right, float bottom) {
          drawSmoothRect1(x - 41.0F, y - 61.0F, right + 41.0F, bottom + 61.0F, (new Color(48, 48, 48, 255)).getRGB());
          drawSmoothRect1(x - 40.0F, y - 60.0F, right + 40.0F, bottom + 60.0F, (new Color(17, 17, 17, 255)).getRGB());
     }

     public static void drawSmoothRectBetter(float x, float y, float width, float height, int color) {
          drawSmoothRect1(x, y, x + width, y + height, color);
     }

     public static void drawGradientRectBetter(float x, float y, float width, float height, int color, int color2) {
          drawGradientRect1((double)x, (double)y, (double)(x + width), (double)(y + height), color, color2);
     }

     public static void drawGradientRect1(double left, double top, double right, double bottom, int color, int color2) {
          float f = (float)(color >> 24 & 255) / 255.0F;
          float f1 = (float)(color >> 16 & 255) / 255.0F;
          float f2 = (float)(color >> 8 & 255) / 255.0F;
          float f3 = (float)(color & 255) / 255.0F;
          float f4 = (float)(color2 >> 24 & 255) / 255.0F;
          float f5 = (float)(color2 >> 16 & 255) / 255.0F;
          float f6 = (float)(color2 >> 8 & 255) / 255.0F;
          float f7 = (float)(color2 & 255) / 255.0F;
          GlStateManager.disableTexture2D();
          GlStateManager.enableBlend();
          GlStateManager.disableAlpha();
          GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
          GlStateManager.shadeModel(7425);
          Tessellator tessellator = Tessellator.getInstance();
          BufferBuilder bufferbuilder = tessellator.getBuffer();
          bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
          bufferbuilder.pos(left, top, (double)gui.zLevel).color(f1, f2, f3, f).endVertex();
          bufferbuilder.pos(left, bottom, (double)gui.zLevel).color(f1, f2, f3, f).endVertex();
          bufferbuilder.pos(right, bottom, (double)gui.zLevel).color(f5, f6, f7, f4).endVertex();
          bufferbuilder.pos(right, top, (double)gui.zLevel).color(f5, f6, f7, f4).endVertex();
          tessellator.draw();
          GlStateManager.shadeModel(7424);
          GlStateManager.disableBlend();
          GlStateManager.enableAlpha();
          GlStateManager.enableTexture2D();
     }

     public static void drawSmoothRect1(float left, float top, float right, float bottom, int color) {
          GL11.glEnable(3042);
          GL11.glEnable(2848);
          drawRect((double)left, (double)top, (double)right, (double)bottom, color);
          GL11.glScalef(0.5F, 0.5F, 0.5F);
          drawRect((double)(left * 2.0F - 1.0F), (double)(top * 2.0F), (double)(left * 2.0F), (double)(bottom * 2.0F - 1.0F), color);
          drawRect((double)(left * 2.0F), (double)(top * 2.0F - 1.0F), (double)(right * 2.0F), (double)(top * 2.0F), color);
          drawRect((double)(right * 2.0F), (double)(top * 2.0F), (double)(right * 2.0F + 1.0F), (double)(bottom * 2.0F - 1.0F), color);
          drawRect((double)(left * 2.0F), (double)(bottom * 2.0F - 1.0F), (double)(right * 2.0F), (double)(bottom * 2.0F), color);
          GL11.glDisable(2848);
          GL11.glDisable(3042);
          GL11.glScalef(2.0F, 2.0F, 2.0F);
     }

     public static int darker(int color, float factor) {
          int r = (int)((float)(color >> 16 & 255) * factor);
          int g = (int)((float)(color >> 8 & 255) * factor);
          int b = (int)((float)(color & 255) * factor);
          int a = color >> 24 & 255;
          return (r & 255) << 16 | (g & 255) << 8 | b & 255 | (a & 255) << 24;
     }

     public static void drawCircle(float x, float y, float start, float end, float radius, float width, boolean filled, Color color) {
          GlStateManager.color(0.0F, 0.0F, 0.0F, 0.0F);
          if (start > end) {
               float endOffset = end;
               end = start;
               start = endOffset;
          }

          GlStateManager.enableBlend();
          GlStateManager.disableTexture2D();
          GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
          setColor(color.getRGB());
          GL11.glEnable(2848);
          GL11.glLineWidth(width);
          GL11.glBegin(3);

          float sin;
          float cos;
          float i;
          for(i = end; i >= start; i -= 4.0F) {
               cos = (float)(Math.cos((double)i * 3.141592653589793D / 180.0D) * (double)radius * 1.0D);
               sin = (float)(Math.sin((double)i * 3.141592653589793D / 180.0D) * (double)radius * 1.0D);
               GL11.glVertex2f(x + cos, y + sin);
          }

          GL11.glEnd();
          GL11.glDisable(2848);
          GL11.glEnable(2848);
          GL11.glBegin(filled ? 6 : 3);

          for(i = end; i >= start; i -= 4.0F) {
               cos = (float)Math.cos((double)i * 3.141592653589793D / 180.0D) * radius;
               sin = (float)Math.sin((double)i * 3.141592653589793D / 180.0D) * radius;
               GL11.glVertex2f(x + cos, y + sin);
          }

          GL11.glEnd();
          GL11.glDisable(2848);
          GlStateManager.enableTexture2D();
          GlStateManager.disableBlend();
     }

     public static Color astolfo(boolean clickgui, int yOffset) {
          float speed = clickgui ? ClickGUI.speed.getNumberValue() * 100.0F : 200.0F;
          float hue = (float)(System.currentTimeMillis() % (long)((int)speed) + (long)yOffset);
          if (hue > speed) {
               hue -= speed;
          }

          hue /= speed;
          if (hue > 0.5F) {
               hue = 0.5F - (hue - 0.5F);
          }

          hue += 0.5F;
          return Color.getHSBColor(hue, 0.4F, 1.0F);
     }

     public static int fadeColor(int startColor, int endColor, float progress) {
          if (progress > 1.0F) {
               progress = 1.0F - progress % 1.0F;
          }

          return fade(startColor, endColor, progress);
     }

     public static void glColor(int red, int green, int blue, int alpha) {
          GlStateManager.color((float)red / 255.0F, (float)green / 255.0F, (float)blue / 255.0F, (float)alpha / 255.0F);
     }

     public static int rainbowNew(int delay, float saturation, float brightness) {
          double rainbow = Math.ceil((double)((System.currentTimeMillis() + (long)delay) / 16L));
          rainbow %= 360.0D;
          return Color.getHSBColor((float)(rainbow / 360.0D), saturation, brightness).getRGB();
     }

     public static void drawRectWithEdge(double x, double y, double width, double height, Color color, Color color2) {
          drawRect(x, y, x + width, y + height, color.getRGB());
          int c = color2.getRGB();
          drawRect(x - 1.0D, y, x, y + height, c);
          drawRect(x + width, y, x + width + 1.0D, y + height, c);
          drawRect(x - 1.0D, y - 1.0D, x + width + 1.0D, y, c);
          drawRect(x - 1.0D, y + height, x + width + 1.0D, y + height + 1.0D, c);
     }

     public static void glColor(Color color) {
          float red = (float)color.getRed() / 255.0F;
          float green = (float)color.getGreen() / 255.0F;
          float blue = (float)color.getBlue() / 255.0F;
          float alpha = (float)color.getAlpha() / 255.0F;
          GlStateManager.color(red, green, blue, alpha);
     }

     public static void glColor(Color color, int alpha) {
          glColor(color, (float)alpha / 255.0F);
     }

     public static void glColor(Color color, float alpha) {
          float red = (float)color.getRed() / 255.0F;
          float green = (float)color.getGreen() / 255.0F;
          float blue = (float)color.getBlue() / 255.0F;
          GlStateManager.color(red, green, blue, alpha);
     }

     public static void glColor(int hex, int alpha) {
          float red = (float)(hex >> 16 & 255) / 255.0F;
          float green = (float)(hex >> 8 & 255) / 255.0F;
          float blue = (float)(hex & 255) / 255.0F;
          GlStateManager.color(red, green, blue, (float)alpha / 255.0F);
     }

     public static void glColor(int hex, float alpha) {
          float red = (float)(hex >> 16 & 255) / 255.0F;
          float green = (float)(hex >> 8 & 255) / 255.0F;
          float blue = (float)(hex & 255) / 255.0F;
          GlStateManager.color(red, green, blue, alpha);
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

     public static int getColor1(int brightness) {
          return getColor(brightness, brightness, brightness, 255);
     }

     public static void drawRect2(double x, double y, double width, double height, int color) {
          drawRect(x, y, x + width, y + height, color);
     }

     public static void blockEsp(BlockPos blockPos, Color color, boolean outline) {
          double var10000 = (double)blockPos.getX();
          mc.getRenderManager();
          double x = var10000 - RenderManager.renderPosX;
          var10000 = (double)blockPos.getY();
          mc.getRenderManager();
          double y = var10000 - RenderManager.renderPosY;
          var10000 = (double)blockPos.getZ();
          mc.getRenderManager();
          double z = var10000 - RenderManager.renderPosZ;
          GL11.glPushMatrix();
          GL11.glBlendFunc(770, 771);
          GL11.glEnable(3042);
          GL11.glLineWidth(2.0F);
          GL11.glDisable(3553);
          GL11.glDisable(2929);
          GL11.glDepthMask(false);
          GlStateManager.color((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, 0.15F);
          drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), 0.0F, 0.0F, 0.0F, 0.0F);
          if (outline) {
               GlStateManager.color(0.0F, 0.0F, 0.0F, 0.5F);
               drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
          }

          GL11.glLineWidth(2.0F);
          GL11.glEnable(3553);
          GL11.glEnable(2929);
          GL11.glDepthMask(true);
          GL11.glDisable(3042);
          GL11.glPopMatrix();
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

     public static void drawColorBox(AxisAlignedBB axisalignedbb, float red, float green, float blue, float alpha) {
          Tessellator ts = Tessellator.getInstance();
          BufferBuilder vb = ts.getBuffer();
          vb.begin(7, DefaultVertexFormats.POSITION_TEX);
          vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
          ts.draw();
          vb.begin(7, DefaultVertexFormats.POSITION_TEX);
          vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
          ts.draw();
          vb.begin(7, DefaultVertexFormats.POSITION_TEX);
          vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
          ts.draw();
          vb.begin(7, DefaultVertexFormats.POSITION_TEX);
          vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
          ts.draw();
          vb.begin(7, DefaultVertexFormats.POSITION_TEX);
          vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
          ts.draw();
          vb.begin(7, DefaultVertexFormats.POSITION_TEX);
          vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
          vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
          ts.draw();
     }

     public static void drawBoundingBox(AxisAlignedBB axisalignedbb) {
          GL11.glBegin(7);
          GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
          GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
          GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
          GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
          GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
          GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
          GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
          GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
          GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
          GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
          GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
          GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
          GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
          GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
          GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
          GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
          GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
          GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
          GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
          GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
          GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
          GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
          GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
          GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
          GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
          GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
          GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
          GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
          GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
          GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
          GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
          GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
          GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
          GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
          GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
          GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
          GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
          GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
          GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
          GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
          GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
          GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
          GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
          GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
          GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
          GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
          GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
          GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
          GL11.glEnd();
     }

     public static void drawLinesAroundPlayer(Entity entity, double radius, float partialTicks, int points, float width, int color) {
          GL11.glPushMatrix();
          enableGL2D3();
          GL11.glDisable(3553);
          GL11.glEnable(2848);
          GL11.glHint(3154, 4354);
          GL11.glDisable(2929);
          GL11.glLineWidth(width);
          GL11.glEnable(3042);
          GL11.glBlendFunc(770, 771);
          GL11.glDisable(2929);
          GL11.glBegin(3);
          RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
          double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks - renderManager.viewerPosX;
          double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks - renderManager.viewerPosY;
          double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks - renderManager.viewerPosZ;
          color228(color);

          for(int i = 0; i <= points; ++i) {
               GL11.glVertex3d(x + radius * Math.cos((double)i * 6.283185307179586D / (double)points), y, z + radius * Math.sin((double)i * 6.283185307179586D / (double)points));
          }

          GL11.glEnd();
          GL11.glDepthMask(true);
          GL11.glDisable(3042);
          GL11.glEnable(2929);
          GL11.glDisable(2848);
          GL11.glEnable(2929);
          GL11.glEnable(3553);
          disableGL2D3();
          GL11.glPopMatrix();
     }

     public static void enableGL2D3() {
          GL11.glDisable(2929);
          GL11.glEnable(3042);
          GL11.glDisable(3553);
          GL11.glBlendFunc(770, 771);
          GL11.glDepthMask(true);
          GL11.glEnable(2848);
          GL11.glHint(3154, 4354);
          GL11.glHint(3155, 4354);
     }

     public static void disableGL2D3() {
          GL11.glEnable(3553);
          GL11.glDisable(3042);
          GL11.glEnable(2929);
          GL11.glDisable(2848);
          GL11.glHint(3154, 4352);
          GL11.glHint(3155, 4352);
     }

     public static void color228(int color) {
          GL11.glColor4ub((byte)(color >> 16 & 255), (byte)(color >> 8 & 255), (byte)(color & 255), (byte)(color >> 24 & 255));
     }

     public static void drawEntityOnScreen(double posX, double posY, double scale, float mouseX, float mouseY, EntityLivingBase ent) {
          GlStateManager.enableColorMaterial();
          GlStateManager.pushMatrix();
          GlStateManager.translate((float)posX, (float)posY, 50.0F);
          GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
          GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
          float f = ent.renderYawOffset;
          float f1 = ent.rotationYaw;
          float f2 = ent.rotationPitch;
          float f3 = ent.prevRotationYawHead;
          float f4 = ent.rotationYawHead;
          GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
          RenderHelper.enableStandardItemLighting();
          GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
          GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
          ent.renderYawOffset = (float)Math.atan((double)(mouseX / 40.0F)) * 20.0F;
          ent.rotationYaw = (float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
          ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
          ent.rotationYawHead = ent.rotationYaw;
          ent.prevRotationYawHead = ent.rotationYaw;
          GlStateManager.translate(0.0F, 0.0F, 0.0F);
          RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
          rendermanager.setPlayerViewY(180.0F);
          rendermanager.setRenderShadow(false);
          rendermanager.doRenderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
          rendermanager.setRenderShadow(true);
          ent.renderYawOffset = f;
          ent.rotationYaw = f1;
          ent.rotationPitch = f2;
          ent.prevRotationYawHead = f3;
          ent.rotationYawHead = f4;
          GlStateManager.popMatrix();
          RenderHelper.disableStandardItemLighting();
          GlStateManager.disableRescaleNormal();
          GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
          GlStateManager.disableTexture2D();
          GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
     }

     public static void endSmooth() {
          GL11.glDisable(2848);
          GL11.glDisable(2881);
          GL11.glEnable(2832);
     }

     public static void enableGL2D() {
          GL11.glDisable(2929);
          GL11.glDisable(3553);
          GL11.glBlendFunc(770, 771);
          GL11.glDepthMask(true);
          GL11.glEnable(2848);
          GL11.glHint(3154, 4354);
          GL11.glHint(3155, 4354);
     }

     public static void enableStandardItemLighting() {
          GlStateManager.enableLighting();
          GlStateManager.enableLight(0);
          GlStateManager.enableLight(1);
          GlStateManager.enableColorMaterial();
          GlStateManager.colorMaterial(1032, 5634);
          GlStateManager.glLight(16384, 4611, setColorBuffer(LIGHT0_POS.xCoord, LIGHT0_POS.yCoord, LIGHT0_POS.zCoord, 0.0D));
          float f = 0.6F;
          GlStateManager.glLight(16384, 4609, setColorBuffer(0.6000000238418579D, 0.6000000238418579D, 0.6000000238418579D, 1.0D));
          GlStateManager.glLight(16384, 4608, setColorBuffer(0.0D, 0.0D, 0.0D, 1.0D));
          GlStateManager.glLight(16384, 4610, setColorBuffer(0.0D, 0.0D, 0.0D, 1.0D));
          GlStateManager.glLight(16385, 4611, setColorBuffer(LIGHT1_POS.xCoord, LIGHT1_POS.yCoord, LIGHT1_POS.zCoord, 0.0D));
          GlStateManager.glLight(16385, 4609, setColorBuffer(0.6000000238418579D, 0.6000000238418579D, 0.6000000238418579D, 1.0D));
          GlStateManager.glLight(16385, 4608, setColorBuffer(0.0D, 0.0D, 0.0D, 1.0D));
          GlStateManager.glLight(16385, 4610, setColorBuffer(0.0D, 0.0D, 0.0D, 1.0D));
          GlStateManager.shadeModel(7424);
          float f1 = 0.4F;
          GlStateManager.glLightModel(2899, setColorBuffer(0.4000000059604645D, 0.4000000059604645D, 0.4000000059604645D, 1.0D));
     }

     private static FloatBuffer setColorBuffer(double p_74517_0_, double p_74517_2_, double p_74517_4_, double p_74517_6_) {
          return setColorBuffer((double)((float)p_74517_0_), (double)((float)p_74517_2_), (double)((float)p_74517_4_), (double)((float)p_74517_6_));
     }

     public static void enableGUIStandardItemLighting() {
          GlStateManager.pushMatrix();
          GlStateManager.rotate(-30.0F, 0.0F, 1.0F, 0.0F);
          GlStateManager.rotate(165.0F, 1.0F, 0.0F, 0.0F);
          enableStandardItemLighting();
          GlStateManager.popMatrix();
     }

     public static void disableStandardItemLighting() {
          GlStateManager.disableLighting();
          GlStateManager.disableLight(0);
          GlStateManager.disableLight(1);
          GlStateManager.disableColorMaterial();
     }

     public static void disableGL2D() {
          GL11.glEnable(3553);
          GL11.glEnable(2929);
          GL11.glDisable(2848);
          GL11.glHint(3154, 4352);
          GL11.glHint(3155, 4352);
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

     public static void drawImage(ResourceLocation resourceLocation, float x, float y, float width, float height) {
          GL11.glDisable(2929);
          GL11.glEnable(3042);
          GL11.glDepthMask(false);
          OpenGlHelper.glBlendFunc(770, 771, 1, 0);
          Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
          Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, width, height);
          GL11.glDepthMask(true);
          GL11.glDisable(3042);
          GL11.glEnable(2929);
     }

     public static void drawImage(ResourceLocation resourceLocation, float x, float y, float width, float height, Color color) {
          GL11.glDisable(2929);
          GL11.glEnable(3042);
          GL11.glDepthMask(false);
          OpenGlHelper.glBlendFunc(770, 771, 1, 0);
          setColor(color.getRGB());
          Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
          Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, width, height);
          GL11.glDepthMask(true);
          GL11.glDisable(3042);
          GL11.glEnable(2929);
     }

     public static void setColor(int color) {
          GL11.glColor4ub((byte)(color >> 16 & 255), (byte)(color >> 8 & 255), (byte)(color & 255), (byte)(color >> 24 & 255));
     }

     public static void drawRectWithGlow(double X, double Y, double Width, double Height, double GlowRange, double GlowMultiplier, Color color) {
          for(float i = 1.0F; (double)i < GlowRange; i += 0.5F) {
               drawRoundedRect99(X - (GlowRange - (double)i), Y - (GlowRange - (double)i), Width + (GlowRange - (double)i), Height + (GlowRange - (double)i), injectAlpha(color, (int)Math.round((double)i * GlowMultiplier)).getRGB());
          }

     }

     public static void drawRoundedRect99(double x, double y, double x1, double y1, int insideC) {
          drawRect(x + 0.5D, y, x1 - 0.5D, y + 0.5D, insideC);
          drawRect(x + 0.5D, y1 - 0.5D, x1 - 0.5D, y1, insideC);
          drawRect(x, y + 0.5D, x1, y1 - 0.5D, insideC);
     }

     public static void drawGlow(double x, double y, double x1, double y1, int color) {
          GlStateManager.disableTexture2D();
          GlStateManager.enableBlend();
          GlStateManager.disableAlpha();
          GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
          GlStateManager.shadeModel(7425);
          drawVGradientRect((float)((int)x), (float)((int)y), (float)((int)x1), (float)((int)(y + (y1 - y) / 2.0D)), setAlpha(new Color(color), 0).getRGB(), color);
          drawVGradientRect((float)((int)x), (float)((int)(y + (y1 - y) / 2.0D)), (float)((int)x1), (float)((int)y1), color, setAlpha(new Color(color), 0).getRGB());
          int radius = (int)((y1 - y) / 2.0D);
          drawPolygonPart(x, y + (y1 - y) / 2.0D, radius, 0, color, setAlpha(new Color(color), 0).getRGB());
          drawPolygonPart(x, y + (y1 - y) / 2.0D, radius, 1, color, setAlpha(new Color(color), 0).getRGB());
          drawPolygonPart(x1, y + (y1 - y) / 2.0D, radius, 2, color, setAlpha(new Color(color), 0).getRGB());
          drawPolygonPart(x1, y + (y1 - y) / 2.0D, radius, 3, color, setAlpha(new Color(color), 0).getRGB());
          GlStateManager.shadeModel(7424);
          GlStateManager.disableBlend();
          GlStateManager.enableAlpha();
          GlStateManager.enableTexture2D();
     }

     public static void drawPolygonPart(double x, double y, int radius, int part, int color, int endcolor) {
          float alpha = (float)(color >> 24 & 255) / 255.0F;
          float red = (float)(color >> 16 & 255) / 255.0F;
          float green = (float)(color >> 8 & 255) / 255.0F;
          float blue = (float)(color & 255) / 255.0F;
          float alpha2 = (float)(endcolor >> 24 & 255) / 255.0F;
          float red2 = (float)(endcolor >> 16 & 255) / 255.0F;
          float green2 = (float)(endcolor >> 8 & 255) / 255.0F;
          float blue2 = (float)(endcolor & 255) / 255.0F;
          GlStateManager.disableTexture2D();
          GlStateManager.enableBlend();
          GlStateManager.disableAlpha();
          GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
          GlStateManager.shadeModel(7425);
          Tessellator tessellator = Tessellator.getInstance();
          BufferBuilder bufferbuilder = tessellator.getBuffer();
          bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
          bufferbuilder.pos(x, y, 0.0D).color(red, green, blue, alpha).endVertex();
          double TWICE_PI = 6.283185307179586D;

          for(int i = part * 90; i <= part * 90 + 90; ++i) {
               double angle = 6.283185307179586D * (double)i / 360.0D + Math.toRadians(180.0D);
               bufferbuilder.pos(x + Math.sin(angle) * (double)radius, y + Math.cos(angle) * (double)radius, 0.0D).color(red2, green2, blue2, alpha2).endVertex();
          }

          tessellator.draw();
          GlStateManager.shadeModel(7424);
          GlStateManager.disableBlend();
          GlStateManager.enableAlpha();
          GlStateManager.enableTexture2D();
     }

     public static void drawVGradientRect(float left, float top, float right, float bottom, int startColor, int endColor) {
          float f = (float)(startColor >> 24 & 255) / 255.0F;
          float f2 = (float)(startColor >> 16 & 255) / 255.0F;
          float f3 = (float)(startColor >> 8 & 255) / 255.0F;
          float f4 = (float)(startColor & 255) / 255.0F;
          float f5 = (float)(endColor >> 24 & 255) / 255.0F;
          float f6 = (float)(endColor >> 16 & 255) / 255.0F;
          float f7 = (float)(endColor >> 8 & 255) / 255.0F;
          float f8 = (float)(endColor & 255) / 255.0F;
          GlStateManager.disableTexture2D();
          GlStateManager.enableBlend();
          GlStateManager.disableAlpha();
          GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
          GlStateManager.shadeModel(7425);
          Tessellator tessellator = Tessellator.getInstance();
          BufferBuilder bufferbuilder = tessellator.getBuffer();
          bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
          bufferbuilder.pos((double)right, (double)top, 0.0D).color(f2, f3, f4, f).endVertex();
          bufferbuilder.pos((double)left, (double)top, 0.0D).color(f2, f3, f4, f).endVertex();
          bufferbuilder.pos((double)left, (double)bottom, 0.0D).color(f6, f7, f8, f5).endVertex();
          bufferbuilder.pos((double)right, (double)bottom, 0.0D).color(f6, f7, f8, f5).endVertex();
          tessellator.draw();
          GlStateManager.shadeModel(7424);
          GlStateManager.disableBlend();
          GlStateManager.enableAlpha();
          GlStateManager.enableTexture2D();
     }

     public static void drawBorderedRect(double left, double top, double right, double bottom, double borderWidth, int insideColor, int borderColor, boolean borderIncludedInBounds) {
          drawRect(left - (!borderIncludedInBounds ? borderWidth : 0.0D), top - (!borderIncludedInBounds ? borderWidth : 0.0D), right + (!borderIncludedInBounds ? borderWidth : 0.0D), bottom + (!borderIncludedInBounds ? borderWidth : 0.0D), borderColor);
          drawRect(left + (borderIncludedInBounds ? borderWidth : 0.0D), top + (borderIncludedInBounds ? borderWidth : 0.0D), right - (borderIncludedInBounds ? borderWidth : 0.0D), bottom - (borderIncludedInBounds ? borderWidth : 0.0D), insideColor);
     }

     public static Color astolfoColors1(float yDist, float yTotal) {
          float speed = 3500.0F;

          float hue;
          for(hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + (yTotal - yDist) * 12.0F; hue > speed; hue -= speed) {
          }

          hue /= speed;
          if ((double)hue > 0.5D) {
               hue = 0.5F - (hue - 0.5F);
          }

          hue += 0.5F;
          return new Color(hue, 0.4F, 1.0F);
     }

     public static final void color(Color color) {
          if (color == null) {
               color = Color.white;
          }

          color((double)((float)color.getRed() / 255.0F), (double)((float)color.getGreen() / 255.0F), (double)((float)color.getBlue() / 255.0F), (double)((float)color.getAlpha() / 255.0F));
     }

     public static void glColor(int hex) {
          float alpha = (float)(hex >> 24 & 255) / 255.0F;
          float red = (float)(hex >> 16 & 255) / 255.0F;
          float green = (float)(hex >> 8 & 255) / 255.0F;
          float blue = (float)(hex & 255) / 255.0F;
          GL11.glColor4f(red, green, blue, alpha);
     }

     public static final void color(double red, double green, double blue, double alpha) {
          GL11.glColor4d(red, green, blue, alpha);
     }

     public static void staticJelloCircle() {
          if (KillAuraHelper.canAttack(KillAura.target) && KillAura.target.getHealth() > 0.0F && mc.player.getDistanceToEntity(KillAura.target) <= KillAura.range.getNumberValue() && !KillAura.target.isDead) {
               double height = 0.8D * (1.0D + Math.sin(6.283185307179586D * (double)time * 0.3D));
               double width = (double)KillAura.target.width;
               double x = KillAura.target.lastTickPosX + (KillAura.target.posX - KillAura.target.lastTickPosX) * (double)mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosX;
               double y = KillAura.target.lastTickPosY + (KillAura.target.posY - KillAura.target.lastTickPosY) * (double)mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosY;
               double z = KillAura.target.lastTickPosZ + (KillAura.target.posZ - KillAura.target.lastTickPosZ) * (double)mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosZ;
               GlStateManager.enableBlend();
               GL11.glBlendFunc(770, 771);
               GL11.glEnable(2848);
               GlStateManager.disableDepth();
               GlStateManager.disableTexture2D();
               GlStateManager.disableAlpha();
               GL11.glLineWidth(1.2F);
               GL11.glShadeModel(7425);
               GL11.glDisable(2884);
               GL11.glBegin(5);

               int j;
               for(j = 0; j < 361; ++j) {
                    color(setAlpha(astolfoColors45((float)(j - j + 1), (float)j, 50.0F, 10.0F), (int)(255.0D * (1.3D - height))));
                    double var10000 = x + Math.cos(Math.toRadians((double)j)) * 0.7D;
                    var10000 = z - Math.sin(Math.toRadians((double)j)) * 0.7D;
                    GL11.glVertex3d(x + Math.cos(Math.toRadians((double)j)) * width, y + 0.05D, z - Math.sin(Math.toRadians((double)j)) * width);
                    color(setAlpha(astolfoColors45((float)(j - j + 1), (float)j, 50.0F, 10.0F), 0));
                    GL11.glVertex3d(x + Math.cos(Math.toRadians((double)j)) * width, y + 0.05D + 0.13D * height, z - Math.sin(Math.toRadians((double)j)) * width);
               }

               GL11.glEnd();
               GL11.glBegin(2);

               for(j = 0; j < 365; ++j) {
                    setColor(astolfoColors45((float)(j - j + 15), (float)j, 50.0F, 10.0F).getRGB());
                    GL11.glVertex3d(x + Math.cos(Math.toRadians((double)j)) * width, y + 0.05D, z - Math.sin(Math.toRadians((double)j)) * width);
               }

               GL11.glEnd();
               GlStateManager.enableAlpha();
               GL11.glShadeModel(7424);
               GL11.glDisable(2848);
               GL11.glEnable(2884);
               GlStateManager.enableTexture2D();
               GlStateManager.enableDepth();
               GlStateManager.disableBlend();
               GlStateManager.resetColor();
          }

     }

     public static void staticJelloCircle1() {
          double height = 0.8D * (1.0D + Math.sin(6.283185307179586D * (double)time * 0.3D));
          double width = (double)mc.player.width;
          double x = mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * (double)mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosX;
          double y = mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * (double)mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosY;
          double z = mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * (double)mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosZ;
          GlStateManager.enableBlend();
          GL11.glBlendFunc(770, 771);
          GL11.glEnable(2848);
          GlStateManager.disableTexture2D();
          GlStateManager.disableAlpha();
          GL11.glLineWidth(1.2F);
          GL11.glShadeModel(7425);
          GL11.glDisable(2884);
          GL11.glBegin(5);

          int j;
          for(j = 0; j < 361; ++j) {
               color(setAlpha(ClientHelper.getClientColor(), (int)(255.0D * (1.3D - height))));
               double var10000 = x + Math.cos(Math.toRadians((double)j)) * 0.7D;
               var10000 = z - Math.sin(Math.toRadians((double)j)) * 0.7D;
               GL11.glVertex3d(x + Math.cos(Math.toRadians((double)j)) * width, y + 0.1D, z - Math.sin(Math.toRadians((double)j)) * width);
               color(setAlpha(ClientHelper.getClientColor(), 0));
               GL11.glVertex3d(x + Math.cos(Math.toRadians((double)j)) * width, y + 0.1D + 0.13D * height, z - Math.sin(Math.toRadians((double)j)) * width);
          }

          GL11.glEnd();
          GL11.glBegin(2);

          for(j = 0; j < 365; ++j) {
               color(ClientHelper.getClientColor());
               GL11.glVertex3d(x + Math.cos(Math.toRadians((double)j)) * width, y + 0.1D, z - Math.sin(Math.toRadians((double)j)) * width);
          }

          GL11.glEnd();
          GlStateManager.enableAlpha();
          GL11.glShadeModel(7424);
          GL11.glDisable(2848);
          GL11.glEnable(2884);
          GlStateManager.enableTexture2D();
          GlStateManager.disableBlend();
          GlStateManager.resetColor();
     }

     public static void drawRoundedRect(float left, float top, float right, float bottom, int smooth, Color color) {
          Gui.drawRect((double)((int)left + smooth), (double)((int)top), (double)((int)right - smooth), (double)((int)bottom), color.getRGB());
          Gui.drawRect((double)((int)left), (double)((int)top + smooth), (double)((int)right), (double)((int)bottom - smooth), color.getRGB());
          drawFilledCircle((int)left + smooth, (int)top + smooth, (float)smooth, color);
          drawFilledCircle((int)right - smooth, (int)top + smooth, (float)smooth, color);
          drawFilledCircle((int)right - smooth, (int)bottom - smooth, (float)smooth, color);
          drawFilledCircle((int)left + smooth, (int)bottom - smooth, (float)smooth, color);
     }

     public static final void drawSmoothRect(float left, float top, float right, float bottom, int color) {
          GL11.glEnable(3042);
          GL11.glEnable(2848);
          drawRect((double)left, (double)top, (double)right, (double)bottom, color);
          GL11.glScalef(0.5F, 0.5F, 0.5F);
          drawRect((double)(left * 2.0F - 1.0F), (double)(top * 2.0F), (double)(left * 2.0F), (double)(bottom * 2.0F - 1.0F), color);
          drawRect((double)(left * 2.0F), (double)(top * 2.0F - 1.0F), (double)(right * 2.0F), (double)(top * 2.0F), color);
          drawRect((double)(right * 2.0F), (double)(top * 2.0F), (double)(right * 2.0F + 1.0F), (double)(bottom * 2.0F - 1.0F), color);
          drawRect((double)(left * 2.0F), (double)(bottom * 2.0F - 1.0F), (double)(right * 2.0F), (double)(bottom * 2.0F), color);
          GL11.glDisable(3042);
          GL11.glScalef(2.0F, 2.0F, 2.0F);
     }

     public static void drawSmoothRect(double left, double top, double right, double bottom, int color) {
          GL11.glEnable(3042);
          GL11.glEnable(2848);
          drawRect(left, top, right, bottom, color);
          GL11.glScalef(0.5F, 0.5F, 0.5F);
          drawRect(left * 2.0D - 1.0D, top * 2.0D, left * 2.0D, bottom * 2.0D - 1.0D, color);
          drawRect(left * 2.0D, top * 2.0D - 1.0D, right * 2.0D, top * 2.0D, color);
          drawRect(right * 2.0D, top * 2.0D, right * 2.0D + 1.0D, bottom * 2.0D - 1.0D, color);
          drawRect(left * 2.0D, bottom * 2.0D - 1.0D, right * 2.0D, bottom * 2.0D, color);
          GL11.glDisable(3042);
          GL11.glScalef(2.0F, 2.0F, 2.0F);
     }

     public static void drawFilledCircle(int xx, int yy, float radius, Color color) {
          int sections = 50;
          double dAngle = 6.283185307179586D / (double)sections;
          GL11.glPushAttrib(8192);
          GL11.glEnable(3042);
          GL11.glDisable(3553);
          GL11.glBlendFunc(770, 771);
          GL11.glEnable(2848);
          GL11.glBegin(6);

          for(int i = 0; i < sections; ++i) {
               float x = (float)((double)radius * Math.sin((double)i * dAngle));
               float y = (float)((double)radius * Math.cos((double)i * dAngle));
               GL11.glColor4f((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
               GL11.glVertex2f((float)xx + x, (float)yy + y);
          }

          GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
          GL11.glEnd();
          GL11.glPopAttrib();
     }

     public static Color setAlpha(Color color, int alpha) {
          alpha = MathHelper.clamp(alpha, 0, 255);
          return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
     }

     public static Color getColorWithOpacity(Color color, int alpha) {
          return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
     }

     public static void renderItem(ItemStack itemStack, int x, int y) {
          GlStateManager.enableBlend();
          GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
          GlStateManager.enableDepth();
          RenderHelper.enableGUIStandardItemLighting();
          Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(itemStack, x, y);
          RenderItem var10000 = Minecraft.getMinecraft().getRenderItem();
          Minecraft.getMinecraft();
          var10000.renderItemOverlays(Minecraft.fontRendererObj, itemStack, x, y);
          RenderHelper.disableStandardItemLighting();
          GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
          GlStateManager.disableDepth();
     }

     public static void drawRoundedRect1(double x, double y, double x1, double y1, int insideC) {
          drawRect(x + 0.5D, y, x1 - 0.5D, y + 0.5D, insideC);
          drawRect(x + 0.5D, y1 - 0.5D, x1 - 0.5D, y1, insideC);
          drawRect(x, y + 0.5D, x1, y1 - 0.5D, insideC);
     }

     public static void drawRoundedRect2(double x, double y, double x1, double y1, int borderC, int insideC) {
          drawRect(x + 0.5D, y, x1 - 0.5D, y + 0.5D, insideC);
          drawRect(x + 0.5D, y1 - 0.5D, x1 - 0.5D, y1, insideC);
          drawRect(x, y + 0.5D, x1, y1 - 0.5D, insideC);
     }

     public static void prepareScissorBox(float x, float y, float x2, float y2) {
          ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
          int factor = ScaledResolution.getScaleFactor();
          GL11.glScissor((int)(x * (float)factor), (int)(((float)scale.getScaledHeight() - y2) * (float)factor), (int)((x2 - x) * (float)factor), (int)((y2 - y) * (float)factor));
     }

     public static void drawGradientRect(double d, double e, double e2, double g, int startColor, int endColor) {
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
          BufferBuilder bufferbuilder = tessellator.getBuffer();
          bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
          bufferbuilder.pos(e2, e, (double)zLevel).color(f1, f2, f3, f).endVertex();
          bufferbuilder.pos(d, e, (double)zLevel).color(f1, f2, f3, f).endVertex();
          bufferbuilder.pos(d, g, (double)zLevel).color(f5, f6, f7, f4).endVertex();
          bufferbuilder.pos(e2, g, (double)zLevel).color(f5, f6, f7, f4).endVertex();
          tessellator.draw();
          GlStateManager.shadeModel(7424);
          GlStateManager.disableBlend();
          GlStateManager.enableAlpha();
          GlStateManager.enableTexture2D();
     }

     public static double interpolate(double current, double old, double scale) {
          return old + (current - old) * scale;
     }

     public static boolean isInViewFrustrum(Entity entity) {
          return isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
     }

     private static boolean isInViewFrustrum(AxisAlignedBB bb) {
          Entity current = Minecraft.getMinecraft().getRenderViewEntity();
          frustrum.setPosition(current.posX, current.posY, current.posZ);
          return frustrum.isBoundingBoxInFrustum(bb);
     }

     public static void putVertex3d(Vec3d vec) {
          GL11.glVertex3d(vec.xCoord, vec.yCoord, vec.zCoord);
     }

     public static Vec3d getRenderPos(double x, double y, double z) {
          mc.getRenderManager();
          x -= RenderManager.renderPosX;
          mc.getRenderManager();
          y -= RenderManager.renderPosY;
          mc.getRenderManager();
          z -= RenderManager.renderPosZ;
          return new Vec3d(x, y, z);
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
          BufferBuilder bufferbuilder = tessellator.getBuffer();
          GlStateManager.enableBlend();
          GlStateManager.disableTexture2D();
          GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
          GlStateManager.color(f, f1, f2, f3);
          bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
          bufferbuilder.pos(left, bottom, 0.0D).endVertex();
          bufferbuilder.pos(right, bottom, 0.0D).endVertex();
          bufferbuilder.pos(right, top, 0.0D).endVertex();
          bufferbuilder.pos(left, top, 0.0D).endVertex();
          tessellator.draw();
          GlStateManager.enableTexture2D();
          GlStateManager.disableBlend();
     }

     public static int color(int n, int n2, int n3, int n4) {
          int n4 = 255;
          return (new Color(n, n2, n3, n4)).getRGB();
     }

     public static Color rainbow(int delay, float saturation, float brightness) {
          double rainbow = Math.ceil((double)((System.currentTimeMillis() + (long)delay) / 16L));
          rainbow %= 360.0D;
          return Color.getHSBColor((float)(rainbow / 360.0D), saturation, brightness);
     }

     public static Color getRainbow(int offset, int speed) {
          float hue = (float)((System.currentTimeMillis() + (long)offset) % (long)speed);
          hue /= (float)speed;
          return Color.getHSBColor(hue, 0.7F, 1.0F);
     }

     public static Color rainbow2(int delay, float saturation, float brightness) {
          double rainbow = Math.ceil((double)((System.currentTimeMillis() + (long)delay) / 16L));
          rainbow %= 360.0D;
          return Color.getHSBColor((float)(rainbow / 360.0D), saturation, brightness);
     }

     public static int getHealthColor(float health, float maxHealth) {
          return Color.HSBtoRGB(Math.max(0.0F, Math.min(health, maxHealth) / maxHealth) / 3.0F, 1.0F, 0.8F) | -16777216;
     }

     public static Color getHealthColor(EntityLivingBase entityLivingBase) {
          float health = entityLivingBase.getHealth();
          float[] fractions = new float[]{0.0F, 0.15F, 0.55F, 0.7F, 0.9F};
          Color[] colors = new Color[]{new Color(133, 0, 0), Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN};
          float progress = health / entityLivingBase.getMaxHealth();
          return health >= 0.0F ? blendColors(fractions, colors, progress).brighter() : colors[0];
     }

     public static Color getHealthColor2(float health, float maxHealth) {
          float[] fractions = new float[]{0.0F, 0.5F, 1.0F};
          Color[] colors = new Color[]{new Color(108, 0, 0), new Color(255, 51, 0), Color.GREEN};
          float progress = health / maxHealth;
          return blendColors(fractions, colors, progress).brighter();
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

     public static int getColor(Color color) {
          return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
     }

     public static int getColor(int bright) {
          return getColor(bright, bright, bright, 255);
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

     public static int fade(int startColor, int endColor, float progress) {
          float invert = 1.0F - progress;
          int r = (int)((float)(startColor >> 16 & 255) * invert + (float)(endColor >> 16 & 255) * progress);
          int g = (int)((float)(startColor >> 8 & 255) * invert + (float)(endColor >> 8 & 255) * progress);
          int b = (int)((float)(startColor & 255) * invert + (float)(endColor & 255) * progress);
          int a = (int)((float)(startColor >> 24 & 255) * invert + (float)(endColor >> 24 & 255) * progress);
          return (a & 255) << 24 | (r & 255) << 16 | (g & 255) << 8 | b & 255;
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

     public static int astolfo(int yOffset, int yTotal) {
          float speed = FeatureList.colortime.getNumberValue() * 1000.0F;

          float hue;
          for(hue = (float)(System.currentTimeMillis() % (long)((int)speed) + (long)(yOffset - yTotal) * 9L); hue > speed; hue -= speed) {
          }

          hue /= speed;
          if ((double)hue > 0.5D) {
               hue = 0.5F - (hue - 0.5F);
          }

          hue += 0.5F;
          return Color.HSBtoRGB(hue, 0.6F, 1.0F);
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

     public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
          float f = (float)(col1 >> 24 & 255) / 255.0F;
          float f1 = (float)(col1 >> 16 & 255) / 255.0F;
          float f2 = (float)(col1 >> 8 & 255) / 255.0F;
          float f3 = (float)(col1 & 255) / 255.0F;
          float f4 = (float)(col2 >> 24 & 255) / 255.0F;
          float f5 = (float)(col2 >> 16 & 255) / 255.0F;
          float f6 = (float)(col2 >> 8 & 255) / 255.0F;
          float f7 = (float)(col2 & 255) / 255.0F;
          GL11.glEnable(3042);
          GL11.glDisable(3553);
          GL11.glBlendFunc(770, 771);
          GL11.glEnable(2848);
          GL11.glShadeModel(7425);
          GL11.glPushMatrix();
          GL11.glBegin(7);
          GL11.glColor4f(f1, f2, f3, f);
          GL11.glVertex2d(left, top);
          GL11.glVertex2d(left, bottom);
          GL11.glColor4f(f5, f6, f7, f4);
          GL11.glVertex2d(right, bottom);
          GL11.glVertex2d(right, top);
          GL11.glEnd();
          GL11.glPopMatrix();
          GL11.glEnable(3553);
          GL11.glDisable(3042);
     }

     public static int getTeamColor(Entity entityIn) {
          int i = true;
          int i;
          if (entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase("РїС—Р…f[РїС—Р…cRРїС—Р…f]РїС—Р…c" + entityIn.getName())) {
               i = getColor(new Color(255, 60, 60));
          } else if (entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase("РїС—Р…f[РїС—Р…9BРїС—Р…f]РїС—Р…9" + entityIn.getName())) {
               i = getColor(new Color(60, 60, 255));
          } else if (entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase("РїС—Р…f[РїС—Р…eYРїС—Р…f]РїС—Р…e" + entityIn.getName())) {
               i = getColor(new Color(255, 255, 60));
          } else if (entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase("РїС—Р…f[РїС—Р…aGРїС—Р…f]РїС—Р…a" + entityIn.getName())) {
               i = getColor(new Color(60, 255, 60));
          } else {
               i = getColor(new Color(255, 255, 255));
          }

          return i;
     }

     public static Color astolfoColors12(int yOffset, int yTotal) {
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

     public static Color astolfoColors5(float yDist, float yTotal, float saturation, float speedt) {
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

     public static Color rainbowCol(float yDist, float yTotal, float saturation, float speedt) {
          float speed = 1800.0F;

          float hue;
          for(hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + (yTotal - yDist) * speedt; hue > speed; hue -= speed) {
          }

          hue /= speed;
          if (hue > 5.0F) {
               hue = 5.0F - (hue - 5.0F);
          }

          hue += 5.0F;
          return Color.getHSBColor(hue, saturation, 1.0F);
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

     public static float lerp(float a, float b, float f) {
          return a + f * (b - a);
     }

     public static class Colors {
          public static final int WHITE = DrawHelper.toRGBA(255, 255, 255, 255);
          public static final int BLACK = DrawHelper.toRGBA(0, 0, 0, 255);
          public static final int RED = DrawHelper.toRGBA(255, 0, 0, 255);
          public static final int GREEN = DrawHelper.toRGBA(0, 255, 0, 255);
          public static final int BLUE = DrawHelper.toRGBA(0, 0, 255, 255);
          public static final int ORANGE = DrawHelper.toRGBA(255, 128, 0, 255);
          public static final int PURPLE = DrawHelper.toRGBA(163, 73, 163, 255);
          public static final int GRAY = DrawHelper.toRGBA(127, 127, 127, 255);
          public static final int DARK_RED = DrawHelper.toRGBA(64, 0, 0, 255);
          public static final int YELLOW = DrawHelper.toRGBA(255, 255, 0, 255);
          public static final int RAINBOW = Integer.MIN_VALUE;
     }
}
