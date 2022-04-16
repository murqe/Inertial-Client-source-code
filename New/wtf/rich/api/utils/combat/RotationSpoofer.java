package wtf.rich.api.utils.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class RotationSpoofer {
     static Minecraft mc = Minecraft.getMinecraft();

     public static Float[] getLookAngles(Vec3d vec) {
          Float[] angles = new Float[2];
          Minecraft mc = Minecraft.getMinecraft();
          angles[0] = (float)(Math.atan2(mc.player.posZ - vec.zCoord, mc.player.posX - vec.xCoord) / 3.141592653589793D * 180.0D) + 90.0F;
          float heightdiff = (float)(mc.player.posY + (double)mc.player.getEyeHeight() - vec.yCoord);
          float distance = (float)Math.sqrt((mc.player.posZ - vec.zCoord) * (mc.player.posZ - vec.zCoord) + (mc.player.posX - vec.xCoord) * (mc.player.posX - vec.xCoord));
          angles[1] = (float)(Math.atan2((double)heightdiff, (double)distance) / 3.141592653589793D * 180.0D);
          return angles;
     }

     public static boolean isFacingEntity(EntityLivingBase entityLivingBase) {
          float yaw = getNeededRotations(entityLivingBase)[0];
          float pitch = getNeededRotations(entityLivingBase)[1];
          float playerYaw = mc.player.rotationYaw;
          float playerPitch = mc.player.rotationPitch;
          float boudingBoxSize = 21.0F + entityLivingBase.getCollisionBorderSize();
          if (playerYaw < 0.0F) {
               playerYaw += 360.0F;
          }

          if (playerPitch < 0.0F) {
               playerPitch += 360.0F;
          }

          if (yaw < 0.0F) {
               yaw += 360.0F;
          }

          if (pitch < 0.0F) {
               pitch += 360.0F;
          }

          if (playerYaw >= yaw - boudingBoxSize && playerYaw <= yaw + boudingBoxSize) {
               return playerPitch >= pitch - boudingBoxSize && playerPitch <= pitch + boudingBoxSize;
          } else {
               return false;
          }
     }

     public static boolean isLookingAtEntity1(Entity e) {
          return isLookingAt21(e.getPositionEyes(Minecraft.getMinecraft().timer.renderPartialTicks));
     }

     public static float getAngleChange(EntityLivingBase entityIn) {
          float yaw = getNeededRotations(entityIn)[0];
          float pitch = getNeededRotations(entityIn)[1];
          float playerYaw = mc.player.rotationYaw;
          float playerPitch = mc.player.rotationPitch;
          if (playerYaw < 0.0F) {
               playerYaw += 360.0F;
          }

          if (playerPitch < 0.0F) {
               playerPitch += 360.0F;
          }

          if (yaw < 0.0F) {
               yaw += 360.0F;
          }

          if (pitch < 0.0F) {
               pitch += 360.0F;
          }

          float yawChange = Math.max(playerYaw, yaw) - Math.min(playerYaw, yaw);
          float pitchChange = Math.max(playerPitch, pitch) - Math.min(playerPitch, pitch);
          return yawChange + pitchChange;
     }

     public static float[] getNeededRotations(EntityLivingBase entityIn) {
          double d0 = entityIn.posX - mc.player.posX;
          double d1 = entityIn.posZ - mc.player.posZ;
          double d2 = entityIn.posY + (double)entityIn.getEyeHeight() - (mc.player.getEntityBoundingBox().minY + (double)mc.player.getEyeHeight());
          double d3 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1);
          float f = (float)(MathHelper.atan2(d1, d0) * 180.0D / 3.141592653589793D) - 90.0F;
          float f1 = (float)(-(MathHelper.atan2(d2, d3) * 180.0D / 3.141592653589793D));
          return new float[]{f, f1};
     }

     public static float[] getRotations(EntityLivingBase entityIn, float speed) {
          float yaw = updateRotation(mc.player.rotationYaw, getNeededRotations(entityIn)[0], speed);
          float pitch = updateRotation(mc.player.rotationPitch, getNeededRotations(entityIn)[1], speed);
          return new float[]{yaw, pitch};
     }

     public static float updateRotation(float currentRotation, float intendedRotation, float increment) {
          float f = MathHelper.wrapAngleTo180_float(intendedRotation - currentRotation);
          if (f > increment) {
               f = increment;
          }

          if (f < -increment) {
               f = -increment;
          }

          return currentRotation + f;
     }

     public static boolean isLookingAtTarget(float yaw, float pitch, Entity entity, double range) {
          Vec3d src = mc.player.getPositionEyes(1.0F);
          Vec3d vectorForRotation = Entity.getVectorForRotation(pitch, yaw);
          Vec3d dest = src.addVector(vectorForRotation.xCoord * range, vectorForRotation.yCoord * range, vectorForRotation.zCoord * range);
          RayTraceResult rayTraceResult = mc.world.rayTraceBlocks(src, dest, false, false, true);
          if (rayTraceResult == null) {
               return true;
          } else {
               return entity.getEntityBoundingBox().expand(0.06D, 0.06D, 0.06D).calculateIntercept(src, dest) == null;
          }
     }

     public static boolean isLookingAt21(Vec3d vec) {
          Float[] targetangles = getLookAngles(vec);
          targetangles = getLookAngles(vec);
          float change = Math.abs(MathHelper.wrapAngleTo180_float(targetangles[0] - mc.player.rotationYaw)) / 0.7F;
          return change < 20.0F;
     }

     public static void lookAtPosSmooth(Vec3d vec, float maxyawchange, float maxpitchchange) {
          Minecraft mc = Minecraft.getMinecraft();
          Float[] targetangles = getLookAngles(vec);
          mc.player.rotationYaw = clampF(targetangles[0], mc.player.rotationYaw, maxyawchange);
          mc.player.rotationPitch = clampF(targetangles[1], mc.player.rotationPitch, maxpitchchange);
     }

     public static void lookAtPos(Vec3d vec) {
          Minecraft mc = Minecraft.getMinecraft();
          Float[] targetangles = getLookAngles(vec);
          mc.player.rotationYaw = targetangles[0];
          mc.player.rotationPitch = targetangles[1];
     }

     public static double getLookDiff(Vec3d vec) {
          Minecraft mc = Minecraft.getMinecraft();
          Float[] targetangles = getLookAngles(vec);
          return Math.sqrt((double)((mc.player.rotationYaw - targetangles[0]) * (mc.player.rotationYaw - targetangles[0]) + (mc.player.rotationPitch - targetangles[1]) * (mc.player.rotationPitch - targetangles[1])));
     }

     public static float clampF(float intended, float current, float maxchange) {
          float change = MathHelper.wrapAngleTo180_float(intended - current);
          if (change > maxchange) {
               change = maxchange;
          } else if (change < -maxchange) {
               change = -maxchange;
          }

          return MathHelper.wrapAngleTo180_float(current + change);
     }

     public static float clampFPercentage(float intended, float current, float percentage) {
          float change = MathHelper.wrapAngleTo180_float(intended - current);
          return MathHelper.wrapAngleTo180_float(current + change * percentage);
     }
}
