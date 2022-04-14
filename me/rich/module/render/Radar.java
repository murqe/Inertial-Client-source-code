package me.rich.module.render;

import java.awt.Color;
import java.util.Iterator;
import me.rich.event.EventTarget;
import me.rich.event.events.EventRender2D;
import me.rich.helpers.render.ColorUtils;
import me.rich.helpers.render.RenderHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class Radar extends Feature {
      private boolean dragging;
      public int scale;
      float hue;

      public Radar() {
            super("Radar", 0, Category.RENDER);
      }

      @EventTarget
      public void onRender2D(EventRender2D event) {
            new Color(-1);
            double psx = 862.0D;
            double psy = 40.0D;
            if (mc.gameSettings.showDebugInfo) {
                  psy = 260.0D;
            }

            ScaledResolution sr = new ScaledResolution(mc);
            this.scale = 2;
            int size = 100;
            float xOffset = (float)((double)(sr.getScaledWidth() - size) - psx);
            float yOffset = (float)psy;
            Minecraft var10000 = mc;
            float playerOffsetX = (float)Minecraft.player.posX;
            var10000 = mc;
            float playerOffsetZ = (float)Minecraft.player.posZ;
            RenderHelper.rectangleBordered((double)xOffset + 2.5D, (double)yOffset - 0.5D, (double)(xOffset + (float)size) - 2.5D, (double)(yOffset + (float)size) - 2.5D, 0.5D, ColorUtils.getColor(11), ColorUtils.getColor(88));
            RenderHelper.rectangleBordered((double)(xOffset + 3.0F), (double)(yOffset + 3.0F), (double)(xOffset + (float)size - 3.0F), (double)(yOffset + (float)size - 3.0F), 0.2D, ColorUtils.getColor(11), ColorUtils.getColor(88));
            RenderHelper.drawGradientSideways(2.0D, 40.5D, 33.0D, 42.5D, (new Color(81, 149, 219, 255)).getRGB(), (new Color(180, 49, 218, 255)).getRGB());
            RenderHelper.drawGradientSideways(33.0D, 40.5D, 65.0D, 42.5D, (new Color(180, 49, 218, 255)).getRGB(), (new Color(236, 93, 128, 255)).getRGB());
            RenderHelper.drawGradientSideways(65.0D, 40.5D, 94.0D, 42.5D, (new Color(236, 93, 128, 255)).getRGB(), (new Color(235, 255, 0, 255)).getRGB());
            Iterator var14 = mc.world.getLoadedEntityList().iterator();

            while(var14.hasNext()) {
                  Entity obj = (Entity)var14.next();
                  if (obj instanceof EntityPlayer) {
                        EntityPlayer ent = (EntityPlayer)obj;
                        Minecraft var10001 = mc;
                        if (ent != Minecraft.player && !ent.isInvisible()) {
                              float pTicks = mc.timer.renderPartialTicks;
                              float posX = (float)((ent.posX + (ent.posX - ent.lastTickPosX) * (double)pTicks - (double)playerOffsetX) * (double)this.scale);
                              float posZ = (float)((ent.posZ + (ent.posZ - ent.lastTickPosZ) * (double)pTicks - (double)playerOffsetZ) * (double)this.scale);
                              var10000 = mc;
                              float cos = (float)Math.cos((double)Minecraft.player.rotationYaw * 0.017453292519943295D);
                              var10000 = mc;
                              float sin = (float)Math.sin((double)Minecraft.player.rotationYaw * 0.017453292519943295D);
                              float rotY = -(posZ * cos - posX * sin);
                              float rotX = -(posX * cos + posZ * sin);
                              if (rotY > (float)size / 2.0F - 5.0F) {
                                    rotY = (float)size / 2.0F - 5.0F;
                              } else if (rotY < -((float)size / 2.0F - 5.0F)) {
                                    rotY = -((float)size / 2.0F - 5.0F);
                              }

                              if (rotX > (float)size / 2.0F - 5.0F) {
                                    rotX = (float)size / 2.0F - 5.0F;
                              } else if (rotX < -((float)size / 2.0F - 5.0F)) {
                                    rotX = -((float)size / 2.0F - 5.0F);
                              }

                              RenderHelper.rectangleBordered((double)(xOffset + (float)(size / 2) + rotX) - 1.5D, (double)(yOffset + (float)(size / 2) + rotY) - 1.5D, (double)(xOffset + (float)(size / 2) + rotX) + 1.5D, (double)(yOffset + (float)(size / 2) + rotY) + 1.5D, 0.5D, ColorUtils.astolfoColors1(0.0F, 0.0F), -1);
                        }
                  }
            }

      }
}
