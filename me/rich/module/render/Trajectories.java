package me.rich.module.render;

import java.util.Iterator;
import me.rich.event.EventTarget;
import me.rich.event.events.Event3D;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class Trajectories extends Feature {
      private double x;
      private double y;
      private double z;
      private double motionX;
      private double motionY;
      private double motionZ;
      private final boolean hitEntity = false;
      private double r;
      private double g;
      private double b;
      public double pX = -9000.0D;
      public double pY = -9000.0D;
      public double pZ = -9000.0D;
      private EntityLivingBase entity;
      private RayTraceResult blockCollision;
      private RayTraceResult entityCollision;
      private static AxisAlignedBB aim;

      public Trajectories() {
            super("Trajectories", 0, Category.RENDER);
      }

      @EventTarget
      public void onRender3D(Event3D event) {
            try {
                  if (Minecraft.player.inventory.getCurrentItem() != null) {
                        EntityPlayerSP player = Minecraft.player;
                        ItemStack stack = player.inventory.getCurrentItem();
                        int itemMain = Item.getIdFromItem(Minecraft.player.getHeldItem(EnumHand.MAIN_HAND).getItem());
                        int itemOff = Item.getIdFromItem(Minecraft.player.getHeldItem(EnumHand.OFF_HAND).getItem());
                        if (itemMain == 261 || itemOff == 261 || itemMain == 368 || itemOff == 368 || itemMain == 332 || itemOff == 332 || itemMain == 344 || itemOff == 344) {
                              double posX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)mc.timer.renderPartialTicks - Math.cos(Math.toRadians((double)player.rotationYaw)) * 0.1599999964237213D;
                              double posY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)mc.timer.renderPartialTicks + (double)player.getEyeHeight() - 0.1D;
                              double posZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)mc.timer.renderPartialTicks - Math.sin(Math.toRadians((double)player.rotationYaw)) * 0.1599999964237213D;
                              double itemBow = stack.getItem() instanceof ItemBow ? 1.0D : 0.4000000059604645D;
                              double yaw = Math.toRadians((double)player.rotationYaw);
                              double pitch = Math.toRadians((double)player.rotationPitch);
                              double trajectoryX = -Math.sin(yaw) * Math.cos(pitch) * itemBow;
                              double trajectoryY = -Math.sin(pitch) * itemBow;
                              double trajectoryZ = Math.cos(yaw) * Math.cos(pitch) * itemBow;
                              double trajectory = Math.sqrt(trajectoryX * trajectoryX + trajectoryY * trajectoryY + trajectoryZ * trajectoryZ);
                              trajectoryX /= trajectory;
                              trajectoryY /= trajectory;
                              trajectoryZ /= trajectory;
                              if (stack.getItem() instanceof ItemBow) {
                                    float bowPower = (float)(72000 - player.getItemInUseCount()) / 20.0F;
                                    if ((bowPower = (bowPower * bowPower + bowPower * 2.0F) / 3.0F) > 1.0F) {
                                          bowPower = 1.0F;
                                    }

                                    trajectoryX *= (double)(bowPower *= 3.0F);
                                    trajectoryY *= (double)bowPower;
                                    trajectoryZ *= (double)bowPower;
                              } else {
                                    trajectoryX *= 1.5D;
                                    trajectoryY *= 1.5D;
                                    trajectoryZ *= 1.5D;
                              }

                              GL11.glPushMatrix();
                              GL11.glDisable(3553);
                              GL11.glEnable(3042);
                              GL11.glBlendFunc(770, 771);
                              GL11.glDisable(2929);
                              GL11.glDepthMask(false);
                              GL11.glEnable(2848);
                              GL11.glLineWidth(1.0F);
                              double gravity = stack.getItem() instanceof ItemBow ? 0.05D : 0.03D;
                              GL11.glColor4f(255.0F, 255.0F, 255.0F, 0.5F);
                              GL11.glBegin(3);

                              for(int i = 0; i < 2000; ++i) {
                                    mc.getRenderManager();
                                    mc.getRenderManager();
                                    mc.getRenderManager();
                                    GL11.glVertex3d(posX - RenderManager.renderPosX, posY - RenderManager.renderPosY, posZ - RenderManager.renderPosZ);
                                    trajectoryY *= 0.999D;
                                    Vec3d vec = new Vec3d(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ);
                                    this.blockCollision = mc.world.rayTraceBlocks(vec, new Vec3d(posX += (trajectoryX *= 0.999D) * 0.1D, posY += (trajectoryY -= gravity * 0.1D) * 0.1D, posZ += (trajectoryZ *= 0.999D) * 0.1D));
                                    Iterator var31 = mc.world.getLoadedEntityList().iterator();

                                    while(var31.hasNext()) {
                                          Entity o = (Entity)var31.next();
                                          if (o instanceof EntityLivingBase && !(o instanceof EntityPlayerSP)) {
                                                this.entity = (EntityLivingBase)o;
                                                AxisAlignedBB entityBoundingBox = this.entity.getEntityBoundingBox().expand(0.3D, 0.3D, 0.3D);
                                                this.entityCollision = entityBoundingBox.calculateIntercept(vec, new Vec3d(posX, posY, posZ));
                                                if (this.entityCollision != null) {
                                                      this.blockCollision = this.entityCollision;
                                                }

                                                if (this.entityCollision != null) {
                                                      GL11.glColor4f(1.0F, 0.0F, 0.2F, 0.5F);
                                                }

                                                if (this.entityCollision != null) {
                                                      this.blockCollision = this.entityCollision;
                                                }
                                          }
                                    }

                                    if (this.blockCollision != null) {
                                          break;
                                    }
                              }

                              GL11.glEnd();
                              mc.getRenderManager();
                              double renderX = posX - RenderManager.renderPosX;
                              mc.getRenderManager();
                              double renderY = posY - RenderManager.renderPosY;
                              mc.getRenderManager();
                              double renderZ = posZ - RenderManager.renderPosZ;
                              GL11.glPushMatrix();
                              GL11.glTranslated(renderX - 0.5D, renderY - 0.5D, renderZ - 0.5D);
                              switch(this.blockCollision.sideHit.getIndex()) {
                              case 2:
                              case 3:
                                    GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                                    aim = new AxisAlignedBB(0.0D, 0.5D, -1.0D, 1.0D, 0.45D, 0.0D);
                                    break;
                              case 4:
                              case 5:
                                    GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                                    aim = new AxisAlignedBB(0.0D, -0.5D, 0.0D, 1.0D, -0.45D, 1.0D);
                                    break;
                              default:
                                    aim = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 0.45D, 1.0D);
                              }

                              onDrawBox(aim);
                              func_181561_a(aim);
                              GL11.glPopMatrix();
                              GL11.glDisable(3042);
                              GL11.glEnable(3553);
                              GL11.glEnable(2929);
                              GL11.glDepthMask(true);
                              GL11.glDisable(2848);
                              GL11.glPopMatrix();
                        }
                  }
            } catch (Exception var34) {
            }

      }

      public static void func_181561_a(AxisAlignedBB p_181561_0_) {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder worldrenderer = tessellator.getBuffer();
            worldrenderer.begin(3, DefaultVertexFormats.POSITION);
            worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
            worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
            worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
            worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
            worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
            tessellator.draw();
            worldrenderer.begin(3, DefaultVertexFormats.POSITION);
            worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
            worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
            worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
            worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
            worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
            tessellator.draw();
            worldrenderer.begin(1, DefaultVertexFormats.POSITION);
            worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
            worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
            worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
            worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
            worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
            worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
            worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
            worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
            tessellator.draw();
      }

      public static void onDrawBox(AxisAlignedBB bb) {
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
}
