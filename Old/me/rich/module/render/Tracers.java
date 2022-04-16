package me.rich.module.render;

import de.Hero.settings.Setting;
import java.util.Iterator;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.Event3D;
import me.rich.helpers.friend.FriendManager;
import me.rich.helpers.render.ColorUtils;
import me.rich.helpers.render.RenderUtil;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class Tracers extends Feature {
      public Tracers() {
            super("Tracers", 0, Category.RENDER);
            Main.settingsManager.rSetting(new Setting("FriendAstolfo", this, false));
      }

      @EventTarget
      public void onEvent3D(Event3D event) {
            boolean old = mc.gameSettings.viewBobbing;
            mc.gameSettings.viewBobbing = false;
            mc.entityRenderer.setupCameraTransform(event.getPartialTicks(), 2);
            mc.gameSettings.viewBobbing = old;
            GL11.glPushMatrix();
            GL11.glEnable(2848);
            GL11.glDisable(2929);
            RenderUtil.startSmooth();
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glDepthMask(false);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glLineWidth(0.6F);
            Iterator var3 = mc.world.loadedEntityList.iterator();

            while(true) {
                  Minecraft var10001;
                  Entity entity;
                  do {
                        do {
                              if (!var3.hasNext()) {
                                    GL11.glDisable(3042);
                                    GL11.glDepthMask(true);
                                    RenderUtil.endSmooth();
                                    GL11.glEnable(3553);
                                    GL11.glEnable(2929);
                                    GL11.glDisable(2848);
                                    GL11.glPopMatrix();
                                    return;
                              }

                              entity = (Entity)var3.next();
                              var10001 = mc;
                        } while(entity == Minecraft.player);
                  } while(!(entity instanceof EntityPlayer));

                  assert mc.getRenderViewEntity() != null;

                  mc.getRenderViewEntity().getDistanceToEntity(entity);
                  double d = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) - mc.getRenderManager().viewerPosX;
                  double d2 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) - mc.getRenderManager().viewerPosY;
                  double d3 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) - mc.getRenderManager().viewerPosZ;
                  if (!FriendManager.isFriend(entity.getName())) {
                        GL11.glColor4f(255.0F, 255.0F, 255.0F, 255.0F);
                  } else if (FriendManager.isFriend(entity.getName()) && Main.settingsManager.getSettingByName(Main.moduleManager.getModule(Tracers.class), "FriendAstolfo").getValBoolean()) {
                        RenderUtil.setColor(ColorUtils.astolfoColors1(0.0F, 0.0F));
                  } else if (FriendManager.isFriend(entity.getName()) && !Main.settingsManager.getSettingByName(Main.moduleManager.getModule(Tracers.class), "FriendAstolfo").getValBoolean()) {
                        GL11.glColor4f(0.0F, 255.0F, 0.0F, 255.0F);
                  }

                  Vec3d vec3d = new Vec3d(0.0D, 0.0D, 1.0D);
                  var10001 = mc;
                  vec3d = vec3d.rotatePitch(-((float)Math.toRadians((double)Minecraft.player.rotationPitch)));
                  var10001 = mc;
                  Vec3d vec3d2 = vec3d.rotateYaw(-((float)Math.toRadians((double)Minecraft.player.rotationYaw)));
                  GL11.glBegin(2);
                  var10001 = mc;
                  GL11.glVertex3d(vec3d2.xCoord, (double)Minecraft.player.getEyeHeight() + vec3d2.yCoord, vec3d2.zCoord);
                  GL11.glVertex3d(d, d2 + 1.1D, d3);
                  GL11.glEnd();
            }
      }

      public void onEnable() {
            super.onEnable();
            NotificationPublisher.queue(this.getName(), "Was enabled.", NotificationType.INFO);
      }

      public void onDisable() {
            NotificationPublisher.queue(this.getName(), "Was disabled.", NotificationType.INFO);
            super.onDisable();
      }
}
