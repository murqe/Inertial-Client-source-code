package me.rich.helpers.other;

import java.util.ArrayList;
import java.util.Objects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class BlockUtil implements MCUtil {
      public static boolean collideBlock(AxisAlignedBB axisAlignedBB, BlockUtil.Collidable collide) {
            for(int x = MathHelper.floor(Minecraft.player.getEntityBoundingBox().minX); x < MathHelper.floor(Minecraft.player.getEntityBoundingBox().maxX) + 1; ++x) {
                  for(int z = MathHelper.floor(Minecraft.player.getEntityBoundingBox().minZ); z < MathHelper.floor(Minecraft.player.getEntityBoundingBox().maxZ) + 1; ++z) {
                        Block block = getBlock(new BlockPos((double)x, axisAlignedBB.minY, (double)z));
                        if (!collide.collideBlock(block)) {
                              return false;
                        }
                  }
            }

            return true;
      }

      public static float getHorizontalPlayerBlockDistance(BlockPos blockPos) {
            Minecraft var10000 = mc;
            float xDiff = (float)(Minecraft.player.posX - (double)blockPos.getX());
            var10000 = mc;
            float zDiff = (float)(Minecraft.player.posZ - (double)blockPos.getZ());
            return MathHelper.sqrt((xDiff - 0.5F) * (xDiff - 0.5F) + (zDiff - 0.5F) * (zDiff - 0.5F));
      }

      public static boolean isOnIce(Entity entity) {
            if (entity == null) {
                  return false;
            } else {
                  boolean onIce = false;
                  int y = (int)entity.getEntityBoundingBox().offset(0.0D, -0.01D, 0.0D).minY;

                  for(int x = MathHelper.floor(entity.getEntityBoundingBox().minX); x < MathHelper.floor(entity.getEntityBoundingBox().maxX) + 1; ++x) {
                        for(int z = MathHelper.floor(entity.getEntityBoundingBox().minZ); z < MathHelper.floor(entity.getEntityBoundingBox().maxZ) + 1; ++z) {
                              Block block = mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                              if (block != null && !(block instanceof BlockAir)) {
                                    if (!(block instanceof BlockIce) && !(block instanceof BlockPackedIce)) {
                                          return false;
                                    }

                                    onIce = true;
                              }
                        }
                  }

                  return onIce;
            }
      }

      public static EnumFacing getFacingDirection(BlockPos pos) {
            EnumFacing direction = null;
            if (!mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().isBlockSolid(mc.world, pos, direction)) {
                  direction = EnumFacing.UP;
            }

            WorldClient var10000 = mc.world;
            Minecraft var10003 = mc;
            double var3 = Minecraft.player.posX;
            Minecraft var10004 = mc;
            Minecraft var10005 = mc;
            double var4 = Minecraft.player.posY + (double)Minecraft.player.getEyeHeight();
            var10005 = mc;
            RayTraceResult rayResult = var10000.rayTraceBlocks(new Vec3d(var3, var4, Minecraft.player.posZ), new Vec3d((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D));
            return rayResult != null ? rayResult.sideHit : direction;
      }

      public static boolean isOnLadder(Entity entity) {
            if (entity == null) {
                  return false;
            } else {
                  boolean onLadder = false;
                  int y = (int)entity.getEntityBoundingBox().offset(0.0D, 1.0D, 0.0D).minY;

                  for(int x = MathHelper.floor(entity.getEntityBoundingBox().minX); x < MathHelper.floor(entity.getEntityBoundingBox().maxX) + 1; ++x) {
                        for(int z = MathHelper.floor(entity.getEntityBoundingBox().minZ); z < MathHelper.floor(entity.getEntityBoundingBox().maxZ) + 1; ++z) {
                              Block block = mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                              if (Objects.nonNull(block) && !(block instanceof BlockAir)) {
                                    if (!(block instanceof BlockLadder) && !(block instanceof BlockVine)) {
                                          return false;
                                    }

                                    onLadder = true;
                              }
                        }
                  }

                  return onLadder || Minecraft.player.isOnLadder();
            }
      }

      public static boolean isOnLiquid(double profondeur) {
            return mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - profondeur, Minecraft.player.posZ)).getBlock().getMaterial((IBlockState)null).isLiquid();
      }

      public static boolean isTotalOnLiquid(double profondeur) {
            for(double x = Minecraft.player.boundingBox.minX; x < Minecraft.player.boundingBox.maxX; x += 0.009999999776482582D) {
                  for(double z = Minecraft.player.boundingBox.minZ; z < Minecraft.player.boundingBox.maxZ; z += 0.009999999776482582D) {
                        Block block = mc.world.getBlockState(new BlockPos(x, Minecraft.player.posY - profondeur, z)).getBlock();
                        if (!(block instanceof BlockLiquid) && !(block instanceof BlockAir)) {
                              return false;
                        }
                  }
            }

            return true;
      }

      public static Block getBlock(BlockPos pos) {
            return getState(pos).getBlock();
      }

      public static IBlockState getState(BlockPos pos) {
            return mc.world.getBlockState(pos);
      }

      public static ArrayList getAllInBox(BlockPos from, BlockPos to) {
            ArrayList blocks = new ArrayList();
            BlockPos min = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
            BlockPos max = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));

            for(int x = min.getX(); x <= max.getX(); ++x) {
                  for(int y = min.getY(); y <= max.getY(); ++y) {
                        for(int z = min.getZ(); z <= max.getZ(); ++z) {
                              blocks.add(new BlockPos(x, y, z));
                        }
                  }
            }

            return blocks;
      }

      public static Block getBlock(int x, int y, int z) {
            return mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
      }

      public static boolean canSeeBlock(float x, float y, float z) {
            return getFacing(new BlockPos((double)x, (double)y, (double)z)) != null;
      }

      public static EnumFacing getFacing(BlockPos pos) {
            EnumFacing[] orderedValues = new EnumFacing[]{EnumFacing.UP, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.DOWN};
            EnumFacing[] var2 = orderedValues;
            int var3 = orderedValues.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                  EnumFacing facing = var2[var4];
                  EntitySnowball temp = new EntitySnowball(mc.world);
                  temp.posX = (double)pos.getX() + 0.5D;
                  temp.posY = (double)pos.getY() + 0.5D;
                  temp.posZ = (double)pos.getZ() + 0.5D;
                  temp.posX += (double)facing.getDirectionVec().getX() * 0.5D;
                  temp.posY += (double)facing.getDirectionVec().getY() * 0.5D;
                  temp.posZ += (double)facing.getDirectionVec().getZ() * 0.5D;
                  if (Minecraft.player.canEntityBeSeen(temp)) {
                        return facing;
                  }
            }

            return null;
      }

      public interface Collidable {
            boolean collideBlock(Block var1);
      }
}
