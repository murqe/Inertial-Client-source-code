package wtf.rich.client.features.impl.visuals;

import java.awt.Color;
import java.util.Iterator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import wtf.rich.Main;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.Event3D;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.ColorSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class Tracers extends Feature {
     public static ColorSetting colorGlobal;
     public static BooleanSetting friend;
     public static BooleanSetting onlyPlayer = new BooleanSetting("Only Player", true, () -> {
          return true;
     });
     public static NumberSetting width;
     public static BooleanSetting seeOnly = new BooleanSetting("See Only", false, () -> {
          return true;
     });

     public Tracers() {
          super("Tracers", "Показывает линию к игрокам", 0, Category.VISUALS);
          friend = new BooleanSetting("Friend Highlight", true, () -> {
               return true;
          });
          colorGlobal = new ColorSetting("Tracers Color", (new Color(16777215)).getRGB(), () -> {
               return true;
          });
          width = new NumberSetting("Tracers Width", 1.5F, 0.1F, 5.0F, 0.1F, () -> {
               return true;
          });
          this.addSettings(new Setting[]{colorGlobal, friend, seeOnly, onlyPlayer, width});
     }

     public static boolean canSeeEntityAtFov(Entity entityLiving, float scope) {
          double diffX = entityLiving.posX - mc.player.posX;
          double diffZ = entityLiving.posZ - mc.player.posZ;
          float yaw = (float)(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0D);
          double difference = angleDifference(yaw, mc.player.rotationYaw);
          return difference <= (double)scope;
     }

     public static double angleDifference(float oldYaw, float newYaw) {
          float yaw = Math.abs(oldYaw - newYaw) % 360.0F;
          if (yaw > 180.0F) {
               yaw = 360.0F - yaw;
          }

          return (double)yaw;
     }

     @EventTarget
     public void onEvent3D(Event3D event) {
          new Color(colorGlobal.getColorValue());
          Iterator var3 = mc.world.loadedEntityList.iterator();

          while(true) {
               Entity entity;
               do {
                    do {
                         if (!var3.hasNext()) {
                              return;
                         }

                         entity = (Entity)var3.next();
                    } while(entity == mc.player);
               } while(onlyPlayer.getBoolValue() && !(entity instanceof EntityPlayer));

               if (seeOnly.getBoolValue() && !canSeeEntityAtFov(entity, 150.0F)) {
                    return;
               }

               boolean old = mc.gameSettings.viewBobbing;
               mc.gameSettings.viewBobbing = false;
               mc.entityRenderer.setupCameraTransform(event.getPartialTicks(), 0);
               mc.gameSettings.viewBobbing = old;
               double var10000 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)event.getPartialTicks();
               mc.getRenderManager();
               double x = var10000 - RenderManager.renderPosX;
               var10000 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)event.getPartialTicks();
               mc.getRenderManager();
               double y = var10000 - RenderManager.renderPosY - 1.0D;
               var10000 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)event.getPartialTicks();
               mc.getRenderManager();
               double z = var10000 - RenderManager.renderPosZ;
               GlStateManager.blendFunc(770, 771);
               GlStateManager.enable(3042);
               GlStateManager.enable(2848);
               GlStateManager.glLineWidth(width.getNumberValue());
               GlStateManager.disable(3553);
               GlStateManager.disable(2929);
               GlStateManager.depthMask(false);
               if (Main.instance.friendManager.isFriend(entity.getName()) && friend.getBoolValue()) {
                    DrawHelper.glColor(new Color(0, 255, 0));
               } else {
                    DrawHelper.glColor(new Color(colorGlobal.getColorValue()));
               }

               GlStateManager.glBegin(3);
               Vec3d vec = (new Vec3d(0.0D, 0.0D, 1.0D)).rotatePitch((float)(-Math.toRadians((double)mc.player.rotationPitch))).rotateYaw((float)(-Math.toRadians((double)mc.player.rotationYaw)));
               GlStateManager.glVertex3d(vec.xCoord, (double)mc.player.getEyeHeight() + vec.yCoord, vec.zCoord);
               GlStateManager.glVertex3d(x, y + 1.1D, z);
               GlStateManager.glEnd();
               GlStateManager.enable(3553);
               GlStateManager.disable(2848);
               GlStateManager.enable(2929);
               GlStateManager.depthMask(true);
               GlStateManager.disable(3042);
               GlStateManager.resetColor();
          }
     }
}
