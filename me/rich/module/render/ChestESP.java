package me.rich.module.render;

import java.util.Iterator;
import me.rich.event.EventTarget;
import me.rich.event.events.Event3D;
import me.rich.helpers.render.RenderUtil;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public class ChestESP extends Feature {
      public ChestESP() {
            super("ChestESP", 0, Category.RENDER);
      }

      @EventTarget
      public void render3d(Event3D event) {
            Iterator var2 = mc.world.loadedTileEntityList.iterator();

            while(var2.hasNext()) {
                  TileEntity tile = (TileEntity)var2.next();
                  double var10000 = (double)tile.getPos().getX();
                  mc.getRenderManager();
                  double posX = var10000 - RenderManager.renderPosX;
                  var10000 = (double)tile.getPos().getY();
                  mc.getRenderManager();
                  double posY = var10000 - RenderManager.renderPosY;
                  var10000 = (double)tile.getPos().getZ();
                  mc.getRenderManager();
                  double posZ = var10000 - RenderManager.renderPosZ;
                  if (tile instanceof TileEntityChest) {
                        AxisAlignedBB bb = (new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.94D, 0.875D, 0.94D)).offset(posX, posY, posZ);
                        TileEntityChest adjacent = null;
                        if (((TileEntityChest)tile).adjacentChestXNeg != null) {
                              adjacent = ((TileEntityChest)tile).adjacentChestXNeg;
                        }

                        if (((TileEntityChest)tile).adjacentChestXPos != null) {
                              adjacent = ((TileEntityChest)tile).adjacentChestXPos;
                        }

                        if (((TileEntityChest)tile).adjacentChestZNeg != null) {
                              adjacent = ((TileEntityChest)tile).adjacentChestZNeg;
                        }

                        if (((TileEntityChest)tile).adjacentChestZPos != null) {
                              adjacent = ((TileEntityChest)tile).adjacentChestZPos;
                        }

                        if (adjacent != null) {
                              AxisAlignedBB var10001 = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.94D, 0.875D, 0.94D);
                              double var10002 = (double)adjacent.getPos().getX();
                              mc.getRenderManager();
                              var10002 -= RenderManager.renderPosX;
                              double var10003 = (double)adjacent.getPos().getY();
                              mc.getRenderManager();
                              var10003 -= RenderManager.renderPosY;
                              double var10004 = (double)adjacent.getPos().getZ();
                              mc.getRenderManager();
                              bb = bb.union(var10001.offset(var10002, var10003, var10004 - RenderManager.renderPosZ));
                        }

                        this.drawBlockESP(bb, 66.0F, 66.0F, 66.0F, 120.0F, 1.1F);
                  }
            }

      }

      private void drawBlockESP(AxisAlignedBB bb, float red, float green, float blue, float alpha, float width) {
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            GL11.glEnable(2848);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glColor4f(0.54901963F, 0.54901963F, 0.54901963F, 0.3F);
            RenderUtil.drawBoundingBox(bb);
            GL11.glLineWidth(width);
            GL11.glColor4f(red / 255.0F, green / 255.0F, blue / 255.0F, alpha / 255.0F);
            RenderUtil.drawOutlinedBoundingBox(bb);
            GL11.glDisable(2848);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }
}
