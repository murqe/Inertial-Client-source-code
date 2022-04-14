package me.rich.helpers.combat;

import me.rich.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RotationSpoofer {
      static Minecraft mc = Minecraft.getMinecraft();

      public static Float[] getLookAngles(Vec3d vec) {
            Float[] angles = new Float[2];
            Minecraft mc = Minecraft.getMinecraft();
            angles[0] = (float)(Math.atan2(Minecraft.player.posZ - vec.zCoord, Minecraft.player.posX - vec.xCoord) / 3.141592653589793D * 180.0D) + 90.0F;
            float heightdiff = (float)(Minecraft.player.posY + (double)Minecraft.player.getEyeHeight() - vec.yCoord);
            float distance = (float)Math.sqrt((Minecraft.player.posZ - vec.zCoord) * (Minecraft.player.posZ - vec.zCoord) + (Minecraft.player.posX - vec.xCoord) * (Minecraft.player.posX - vec.xCoord));
            angles[1] = (float)(Math.atan2((double)heightdiff, (double)distance) / 3.141592653589793D * 180.0D);
            return angles;
      }

      public static boolean isFacingEntity(EntityLivingBase entityLivingBase) {
            float yaw = getNeededRotations(entityLivingBase)[0];
            float pitch = getNeededRotations(entityLivingBase)[1];
            float playerYaw = Minecraft.player.rotationYaw;
            float playerPitch = Minecraft.player.rotationPitch;
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

      public static float getAngleChange(EntityLivingBase entityIn) {
            float yaw = getNeededRotations(entityIn)[0];
            float pitch = getNeededRotations(entityIn)[1];
            float playerYaw = Minecraft.player.rotationYaw;
            float playerPitch = Minecraft.player.rotationPitch;
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
            double d0 = entityIn.posX - Minecraft.player.posX;
            double d1 = entityIn.posZ - Minecraft.player.posZ;
            double d2 = entityIn.posY + (double)entityIn.getEyeHeight() - (Minecraft.player.getEntityBoundingBox().minY + (double)Minecraft.player.getEyeHeight());
            double d3 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1);
            float f = (float)(MathHelper.atan2(d1, d0) * 180.0D / 3.141592653589793D) - 90.0F;
            float f1 = (float)(-(MathHelper.atan2(d2, d3) * 180.0D / 3.141592653589793D));
            return new float[]{f, f1};
      }

      public static float[] getRotations(EntityLivingBase entityIn, float speed) {
            float yaw = updateRotation(Minecraft.player.rotationYaw, getNeededRotations(entityIn)[0], speed);
            float pitch = updateRotation(Minecraft.player.rotationPitch, getNeededRotations(entityIn)[1], speed);
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

      public static boolean isLookingAtEntity(Entity e) {
            return isLookingAt(e.getPositionEyes(Minecraft.getMinecraft().timer.renderPartialTicks));
      }

      public static boolean isLookingAt(Vec3d vec) {
            Float[] targetangles = getLookAngles(vec);
            targetangles = getLookAngles(vec);
            float var10000 = Math.abs(MathHelper.wrapAngleTo180_float(targetangles[0] - Minecraft.player.rotationYaw));
            Main var10001 = Main.instance;
            float change = var10000 / (float)Main.settingsManager.getSettingByName("RayCast Box").getValDouble();
            return change < 20.0F;
      }

      public static boolean isLookingAt2(Vec3d vec) {
            Float[] targetangles = getLookAngles(vec);
            targetangles = getLookAngles(vec);
            float change = Math.abs(MathHelper.wrapAngleTo180_float(targetangles[0] - Minecraft.player.rotationYaw)) / 0.6F;
            return change < 20.0F;
      }

      public static boolean isLookingAt1(Vec3d vec) {
            Float[] targetangles = getLookAngles(vec);
            targetangles = getLookAngles(vec);
            float change = Math.abs(MathHelper.wrapAngleTo180_float(targetangles[0] - Minecraft.player.rotationYaw));
            return change < 20.0F;
      }

      public static void lookAtPosSmooth(Vec3d vec, float maxyawchange, float maxpitchchange) {
            Minecraft mc = Minecraft.getMinecraft();
            Float[] targetangles = getLookAngles(vec);
            Minecraft.player.rotationYaw = clampF(targetangles[0], Minecraft.player.rotationYaw, maxyawchange);
            Minecraft.player.rotationPitch = clampF(targetangles[1], Minecraft.player.rotationPitch, maxpitchchange);
      }

      public static void lookAtPos(Vec3d vec) {
            Minecraft mc = Minecraft.getMinecraft();
            Float[] targetangles = getLookAngles(vec);
            Minecraft.player.rotationYaw = targetangles[0];
            Minecraft.player.rotationPitch = targetangles[1];
      }

      public static double getLookDiff(Vec3d vec) {
            Minecraft mc = Minecraft.getMinecraft();
            Float[] targetangles = getLookAngles(vec);
            return Math.sqrt((double)((Minecraft.player.rotationYaw - targetangles[0]) * (Minecraft.player.rotationYaw - targetangles[0]) + (Minecraft.player.rotationPitch - targetangles[1]) * (Minecraft.player.rotationPitch - targetangles[1])));
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
