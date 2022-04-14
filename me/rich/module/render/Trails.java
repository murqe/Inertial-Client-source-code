package me.rich.module.render;

import java.util.ArrayList;
import me.rich.event.EventTarget;
import me.rich.event.events.Event3D;
import me.rich.event.events.EventUpdate;
import me.rich.helpers.DrawHelper;
import me.rich.helpers.render.TrailsHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;

public class Trails extends Feature {
      ArrayList bcs = new ArrayList();

      public Trails() {
            super("Trails", 0, Category.RENDER);
      }

      @EventTarget
      public void bek(EventUpdate bek) {
            this.bcs.add(new TrailsHelper(Minecraft.player.getPositionVector()));
      }

      @EventTarget
      public void xoxol(Event3D xlol) {
            if (this.isToggled()) {
                  GL11.glPushMatrix();
                  DrawHelper.enableSmoothLine(2.0F);
                  GL11.glDisable(3553);
                  GL11.glBlendFunc(770, 771);
                  GL11.glEnable(2929);
                  GL11.glEnable(3042);
                  GL11.glShadeModel(7424);
                  GL11.glDisable(2884);
                  mc.entityRenderer.disableLightmap();
                  GlStateManager.resetColor();
                  double lastPosX = 114514.0D;
                  double lastPosY = 114514.0D;
                  double lastPosZ = 114514.0D;

                  for(int i = 0; i < this.bcs.size(); ++i) {
                        TrailsHelper bc = (TrailsHelper)this.bcs.get(i);
                        if (this.bcs.size() > 8) {
                              this.bcs.remove(bc);
                        }

                        if (lastPosX != 114514.0D || lastPosY != 114514.0D || lastPosZ != 114514.0D) {
                              DrawHelper.setColor(DrawHelper.setAlpha(DrawHelper.astolfoColors45(0.0F, (float)i, 0.5F, 15.0F), 180).getRGB());
                              GL11.glBegin(7);
                              GL11.glVertex3d(bc.getVector().xCoord - RenderManager.renderPosX, bc.getVector().yCoord - RenderManager.renderPosY, bc.getVector().zCoord - RenderManager.renderPosZ);
                              GL11.glVertex3d(lastPosX, lastPosY, lastPosZ);
                              GL11.glVertex3d(lastPosX, lastPosY + (double)Minecraft.player.height, lastPosZ);
                              GL11.glVertex3d(bc.getVector().xCoord - RenderManager.renderPosX, bc.getVector().yCoord - RenderManager.renderPosY + (double)Minecraft.player.height, bc.getVector().zCoord - RenderManager.renderPosZ);
                              GL11.glEnd();
                        }

                        lastPosX = bc.getVector().xCoord - RenderManager.renderPosX;
                        lastPosY = bc.getVector().yCoord - RenderManager.renderPosY;
                        lastPosZ = bc.getVector().zCoord - RenderManager.renderPosZ;
                  }

                  GL11.glEnable(3553);
                  GL11.glEnable(2884);
                  GL11.glDisable(2929);
                  GL11.glDisable(2848);
                  GL11.glDisable(3042);
                  DrawHelper.disableSmoothLine();
                  GL11.glPopMatrix();
            }

      }
}
