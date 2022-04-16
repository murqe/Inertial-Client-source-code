package me.rich.notifications;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import me.rich.font.Fonts;
import me.rich.helpers.render.AnimationHelper;
import me.rich.helpers.render.RenderHelper;
import me.rich.helpers.render.Translate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

public final class NotificationPublisher {
      private static final List NOTIFICATIONS = new CopyOnWriteArrayList();
      private Minecraft mc;

      public static void publish(ScaledResolution sr) {
            int srScaledHeight = sr.getScaledHeight();
            int scaledWidth = sr.getScaledWidth();
            int y = srScaledHeight - 50;

            for(Iterator var4 = NOTIFICATIONS.iterator(); var4.hasNext(); y -= 35) {
                  Notification notification = (Notification)var4.next();
                  Translate translate = notification.getTranslate();
                  int width = notification.getWidth();
                  if (!notification.getTimer().elapsed((long)notification.getTime())) {
                        notification.scissorBoxWidth = AnimationHelper.animate((double)width, notification.scissorBoxWidth, 0.05D * Minecraft.frameTime / 2.0D);
                        translate.interpolate((float)(scaledWidth - width), (float)y, 0.03D);
                  } else {
                        notification.scissorBoxWidth = AnimationHelper.animate(0.0D, notification.scissorBoxWidth, 0.05D * Minecraft.frameTime / 4.0D);
                        if (notification.scissorBoxWidth < 1.0D) {
                              NOTIFICATIONS.remove(notification);
                        }

                        y += 30;
                  }

                  float translateX = translate.getX();
                  float translateY = translate.getY();
                  GlStateManager.pushMatrix();
                  GlStateManager.disableBlend();
                  RenderHelper.drawRect(translateX, translateY, (float)scaledWidth, translateY + 24.0F, (new Color(35, 34, 34)).getRGB());
                  RenderHelper.drawRect(translateX, translateY, translateX + 2.0F, translateY + 24.0F, -1);
                  Fonts.neverlose14.drawStringWithShadow(TextFormatting.BOLD + notification.getTitle(), (double)(translateX + 5.0F), (double)(translateY + 4.0F), -1);
                  Fonts.neverlose14.drawStringWithShadow(notification.getContent(), (double)(translateX + 5.0F), (double)(translateY + 15.0F), (new Color(245, 245, 245)).getRGB());
                  Fonts.neverlose14.drawStringWithShadow(TextFormatting.BOLD + notification.getTitle(), (double)(translateX + 5.0F), (double)(translateY + 4.0F), -1);
                  Fonts.neverlose14.drawStringWithShadow(notification.getContent(), (double)(translateX + 5.0F), (double)(translateY + 15.0F), (new Color(245, 245, 245)).getRGB());
                  GL11.glDisable(3089);
                  GlStateManager.popMatrix();
            }

      }

      public static void prepareScissorBox(float x, float y, float x2, float y2) {
            ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
            int factor = ScaledResolution.getScaleFactor();
            GL11.glScissor((int)(x * (float)factor), (int)(((float)scale.getScaledHeight() - y2) * (float)factor), (int)((x2 - x) * (float)factor), (int)((y2 - y) * (float)factor));
      }

      public static void queue(String title, String content, NotificationType type) {
            Minecraft mc = Minecraft.getMinecraft();
            FontRenderer fr = Minecraft.fontRendererObj;
            NOTIFICATIONS.add(new Notification(title, content, type, fr));
      }
}
