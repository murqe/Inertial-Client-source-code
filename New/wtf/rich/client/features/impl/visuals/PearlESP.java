package wtf.rich.client.features.impl.visuals;

import java.awt.Color;
import java.util.Iterator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.Event3D;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;

public class PearlESP extends Feature {
     public BooleanSetting tracers = new BooleanSetting("Tracers", true, () -> {
          return true;
     });
     public BooleanSetting esp = new BooleanSetting("ESP", true, () -> {
          return true;
     });

     public PearlESP() {
          super("PearlESP", "Показывает есп перла", 0, Category.VISUALS);
          this.addSettings(new Setting[]{this.esp, this.tracers});
     }

     @EventTarget
     public void onRender3D(Event3D event) {
          GlStateManager.pushMatrix();
          Iterator var2 = mc.world.loadedEntityList.iterator();

          while(var2.hasNext()) {
               Entity entity = (Entity)var2.next();
               if (entity instanceof EntityEnderPearl) {
                    boolean viewBobbing = mc.gameSettings.viewBobbing;
                    mc.gameSettings.viewBobbing = false;
                    mc.entityRenderer.setupCameraTransform(event.getPartialTicks(), 0);
                    mc.gameSettings.viewBobbing = viewBobbing;
                    if (this.tracers.getBoolValue()) {
                         GL11.glPushMatrix();
                         GL11.glEnable(2848);
                         GL11.glDisable(2929);
                         GL11.glDisable(3553);
                         GL11.glDisable(2896);
                         GL11.glDepthMask(false);
                         GL11.glBlendFunc(770, 771);
                         GL11.glEnable(3042);
                         GL11.glLineWidth(1.0F);
                         double var10000 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)event.getPartialTicks();
                         mc.getRenderManager();
                         double x = var10000 - RenderManager.renderPosX;
                         var10000 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)event.getPartialTicks();
                         mc.getRenderManager();
                         double y = var10000 - RenderManager.renderPosY - 1.0D;
                         var10000 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)event.getPartialTicks();
                         mc.getRenderManager();
                         double z = var10000 - RenderManager.renderPosZ;
                         DrawHelper.setColor(-1);
                         Vec3d vec = (new Vec3d(0.0D, 0.0D, 1.0D)).rotatePitch((float)(-Math.toRadians((double)mc.player.rotationPitch))).rotateYaw((float)(-Math.toRadians((double)mc.player.rotationYaw)));
                         GL11.glBegin(2);
                         GL11.glVertex3d(vec.xCoord, (double)mc.player.getEyeHeight() + vec.yCoord, vec.zCoord);
                         GL11.glVertex3d(x, y + 1.1D, z);
                         GL11.glEnd();
                         GL11.glDisable(3042);
                         GL11.glDepthMask(true);
                         GL11.glEnable(3553);
                         GL11.glEnable(2929);
                         GL11.glDisable(2848);
                         GL11.glPopMatrix();
                    }

                    if (this.esp.getBoolValue()) {
                         DrawHelper.drawEntityBox(entity, Color.WHITE, true, 0.2F);
                    }
               }
          }

          GlStateManager.popMatrix();
     }
}
