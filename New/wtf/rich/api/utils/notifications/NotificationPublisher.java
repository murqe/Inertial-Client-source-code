package wtf.rich.api.utils.notifications;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextFormatting;
import wtf.rich.Main;
import wtf.rich.api.utils.Helper;
import wtf.rich.api.utils.font.FontRenderer;
import wtf.rich.api.utils.render.AnimationHelper;
import wtf.rich.api.utils.render.ClientHelper;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.api.utils.render.Shifting;
import wtf.rich.client.features.impl.display.Notifications;

public final class NotificationPublisher implements Helper {
     private static final List notifications = new CopyOnWriteArrayList();

     public static void publish() {
          if (Main.instance.featureDirector.getFeatureByClass(Notifications.class).isToggled()) {
               ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
               int srScaledHeight = sr.getScaledHeight();
               int scaledWidth = sr.getScaledWidth();
               int y = srScaledHeight - 60;
               Iterator var4 = notifications.iterator();

               while(var4.hasNext()) {
                    Notification notification = (Notification)var4.next();
                    Shifting translate = notification.getTranslate();
                    int width = notification.getWidth();
                    if (!notification.getTimer().elapsed((long)notification.getTime())) {
                         notification.scissorBoxWidth = AnimationHelper.animate((double)width, notification.scissorBoxWidth, 0.05D * (double)Minecraft.getSystemTime() / 5.0D);
                         translate.interpolate((double)(scaledWidth - width), (double)y, 0.015D);
                    } else {
                         notification.scissorBoxWidth = AnimationHelper.animate(0.0D, notification.scissorBoxWidth, 0.05D * (double)Minecraft.getSystemTime() / 4.0D);
                         if (notification.scissorBoxWidth < 1.0D) {
                              notifications.remove(notification);
                         }

                         y += 30;
                    }

                    float translateX = (float)translate.getX();
                    float translateY = (float)translate.getY();
                    GlStateManager.pushMatrix();
                    GlStateManager.disableBlend();
                    DrawHelper.drawGlowRoundedRect(translateX, translateY, (float)scaledWidth, translateY + 28.0F, (new Color(35, 34, 34, 220)).getRGB(), 5.0F, 5.0F);
                    DrawHelper.drawGlowRoundedRect(translateX - 0.5F, translateY, translateX + (float)width - 0.15F, translateY + 1.2F, ClientHelper.getClientColor().getRGB(), 5.0F, 20.0F);
                    mc.lato.drawStringWithShadow(TextFormatting.BOLD + notification.getTitle(), (double)(translateX + 5.0F), (double)(translateY + 4.0F), -1);
                    mc.lato15.drawStringWithShadow(notification.getContent(), (double)(translateX + 5.0F), (double)(translateY + 17.0F), (new Color(245, 245, 245)).getRGB());
                    GlStateManager.popMatrix();
                    if (notifications.size() > 1) {
                         y -= 35;
                    }
               }
          }

     }

     public static void queue(String title, String content, NotificationType type) {
          FontRenderer fr = mc.neverlose500_16;
          notifications.add(new Notification(title, content, type, fr));
     }
}
