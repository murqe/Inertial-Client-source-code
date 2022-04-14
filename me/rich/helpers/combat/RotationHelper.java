package me.rich.helpers.combat;

import me.rich.Main;
import me.rich.helpers.other.CountHelper;
import me.rich.module.combat.KillAura;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.lang3.RandomUtils;

public class RotationHelper {
      private static Minecraft mc = Minecraft.getMinecraft();

      public static float[] getRatations(Entity e) {
            double diffX = e.posX - Minecraft.player.posX;
            double diffZ = e.posZ - Minecraft.player.posZ;
            double var10000;
            Minecraft var10001;
            Minecraft var10002;
            double diffY;
            if (e instanceof EntityLivingBase) {
                  var10000 = e.posY + (double)e.getEyeHeight();
                  var10001 = mc;
                  var10002 = mc;
                  diffY = var10000 - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight()) - 0.4D;
            } else {
                  var10000 = (e.getEntityBoundingBox().minY + e.getEntityBoundingBox().maxY) / 2.0D;
                  var10001 = mc;
                  var10002 = mc;
                  diffY = var10000 - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
            }

            double dist = (double)MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
            float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D - 90.0D) + CountHelper.nextFloat(-2.0F, 2.0F);
            float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D)) + CountHelper.nextFloat(-2.0F, 2.0F);
            Minecraft var11 = mc;
            var10002 = mc;
            yaw = Minecraft.player.rotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yaw - Minecraft.player.rotationYaw));
            var11 = mc;
            var10002 = mc;
            pitch = Minecraft.player.rotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitch - Minecraft.player.rotationPitch));
            pitch = MathHelper.clamp(pitch, -90.0F, 90.0F);
            return new float[]{yaw, pitch};
      }

      public static float[] getRotations(Entity ent) {
            double x = ent.posX;
            double z = ent.posZ;
            double y = ent.posY + (double)(ent.getEyeHeight() / 2.0F);
            return getRotationFromPosition(x, z, y);
      }

      public static float[] getMatrixRotations(Entity e, boolean oldPositionUse) {
            double diffX = (oldPositionUse ? e.prevPosX : e.posX) - (oldPositionUse ? Minecraft.player.prevPosX : Minecraft.player.posX);
            double diffZ = (oldPositionUse ? e.prevPosZ : e.posZ) - (oldPositionUse ? Minecraft.player.prevPosZ : Minecraft.player.posZ);
            double diffY;
            if (e instanceof EntityLivingBase) {
                  EntityLivingBase entitylivingbase = (EntityLivingBase)e;
                  float randomed = RandomUtils.nextFloat((float)(entitylivingbase.posY + (double)(entitylivingbase.getEyeHeight() / 1.5F)), (float)(entitylivingbase.posY + (double)entitylivingbase.getEyeHeight() - (double)(entitylivingbase.getEyeHeight() / 3.0F)));
                  diffY = (double)randomed - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
            } else {
                  diffY = (double)RandomUtils.nextFloat((float)e.getEntityBoundingBox().minY, (float)e.getEntityBoundingBox().maxY) - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
            }

            double dist = (double)MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
            float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D - 90.0D) + RandomUtils.nextFloat(-2.0F, 2.0F);
            float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D)) + RandomUtils.nextFloat(-2.0F, 2.0F);
            yaw = Minecraft.player.rotationYaw + GCDFix.getFixedRotation(MathHelper.wrapAngleTo180_float(yaw - Minecraft.player.rotationYaw));
            pitch = Minecraft.player.rotationPitch + GCDFix.getFixedRotation(MathHelper.wrapAngleTo180_float(pitch - Minecraft.player.rotationPitch));
            pitch = MathHelper.clamp(pitch, -90.0F, 90.0F);
            return new float[]{yaw, pitch};
      }

      public static float fixRotation(float p_70663_1_, float p_70663_2_) {
            float var4 = MathHelper.wrapDegrees(p_70663_2_ - p_70663_1_);
            if (var4 > 360.0F) {
                  var4 = 360.0F;
            }

            if (var4 < -360.0F) {
                  var4 = -360.0F;
            }

            return p_70663_1_ + var4;
      }

      public static float[] getRotationFromPosition(double x, double z, double y) {
            Minecraft.getMinecraft();
            double xDiff = x - Minecraft.player.posX;
            Minecraft.getMinecraft();
            double zDiff = z - Minecraft.player.posZ;
            Minecraft.getMinecraft();
            double yDiff = y - Minecraft.player.posY - 1.7D;
            double dist = (double)MathHelper.sqrt(xDiff * xDiff + zDiff * zDiff);
            float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
            float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D));
            return new float[]{yaw, pitch};
      }

      public static boolean canSeeEntityAtFov(Entity entityLiving, float scope) {
            Minecraft.getMinecraft();
            double diffX = entityLiving.posX - Minecraft.player.posX;
            Minecraft.getMinecraft();
            double diffZ = entityLiving.posZ - Minecraft.player.posZ;
            float newYaw = (float)(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0D);
            double d = (double)newYaw;
            Minecraft.getMinecraft();
            double difference = angleDifference(d, (double)Minecraft.player.rotationYaw);
            return difference <= (double)scope;
      }

      public static double angleDifference(double a, double b) {
            float yaw360 = (float)(Math.abs(a - b) % 360.0D);
            if (yaw360 > 180.0F) {
                  yaw360 = 360.0F - yaw360;
            }

            return (double)yaw360;
      }

      public static boolean isLookingAtEntity(Entity e) {
            return isLookingAt(e.getPositionEyes(mc.timer.elapsedPartialTicks));
      }

      public static boolean isLookingAt(Vec3d vec) {
            Float[] targetangles = getLookAngles(vec);
            targetangles = getLookAngles(vec);
            float var10000 = targetangles[0];
            Main var10001 = Main.instance;
            float change = Math.abs(MathHelper.wrapDegrees(var10000 / Main.settingsManager.getSettingByName("RayTrace Box").getValFloat()));
            return change < 20.0F;
      }

      public static Float[] getLookAngles(Vec3d vec) {
            Float[] angles = new Float[2];
            Minecraft mc = Minecraft.getMinecraft();
            angles[0] = (float)(Math.atan2(Minecraft.player.posZ - vec.zCoord, Minecraft.player.posX - vec.xCoord) / 3.141592653589793D * 180.0D) + 90.0F;
            float heightdiff = (float)(Minecraft.player.posY + (double)Minecraft.player.getEyeHeight() - vec.yCoord);
            float distance = (float)Math.sqrt((Minecraft.player.posZ - vec.zCoord) * (Minecraft.player.posZ - vec.zCoord) + (Minecraft.player.posX - vec.xCoord) * (Minecraft.player.posX - vec.xCoord));
            angles[1] = (float)(Math.atan2((double)heightdiff, (double)distance) / 3.141592653589793D * 180.0D);
            return angles;
      }

      public static Vec3d getEyesPos() {
            return new Vec3d(Minecraft.player.posX, Minecraft.player.posY + (double)Minecraft.player.getEyeHeight(), Minecraft.player.posZ);
      }

      public static float[] getNeededFacing(Vec3d vec) {
            Vec3d eyesPos = getEyesPos();
            double diffX = vec.xCoord - eyesPos.xCoord;
            double diffY = vec.yCoord - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight() + 0.2D);
            double diffZ = vec.zCoord - eyesPos.zCoord;
            double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
            float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
            float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
            pitch = MathHelper.clamp(pitch, -90.0F, 90.0F);
            return new float[]{MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch)};
      }

      public static float[] getRatationsG1(Entity entity) {
            Minecraft var10001 = mc;
            double diffX = entity.posX - Minecraft.player.posX;
            var10001 = mc;
            double diffZ = entity.posZ - Minecraft.player.posZ;
            double diffY;
            double var10000;
            Minecraft var10002;
            if (entity instanceof EntityLivingBase) {
                  var10000 = entity.posY + (double)entity.getEyeHeight();
                  var10001 = mc;
                  var10002 = mc;
                  diffY = var10000 - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight()) - 2.8D;
            } else {
                  var10000 = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0D;
                  var10001 = mc;
                  var10002 = mc;
                  diffY = var10000 - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
            }

            double dist = (double)MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
            float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D - 90.0D) + CountHelper.nextFloat(-1.5F, 2.0F);
            float yawBody = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D - 90.0D);
            float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0D / 5.0D + (double)CountHelper.nextFloat(-1.0F, 1.0F)));
            float pitch2 = (float)(-(Math.atan2(diffY, dist) * 180.0D / 5.0D));
            var10001 = mc;
            Minecraft var13;
            if (Math.abs(yaw - Minecraft.player.rotationYaw) > 160.0F) {
                  var13 = mc;
                  Minecraft.player.setSprinting(false);
            }

            var13 = mc;
            var10002 = mc;
            yaw = Minecraft.player.prevRotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yaw - Minecraft.player.rotationYaw));
            var13 = mc;
            var10002 = mc;
            yawBody = Minecraft.player.prevRotationYaw + MathHelper.wrapDegrees(yawBody - Minecraft.player.rotationYaw);
            var13 = mc;
            var10002 = mc;
            pitch = Minecraft.player.prevRotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitch - Minecraft.player.rotationPitch));
            pitch = MathHelper.clamp(pitch, -90.0F, 90.0F);
            return new float[]{yaw, pitch, yawBody, pitch2};
      }

      public static float[] getRotations1(Entity ent) {
            double x = ent.posX;
            double z = ent.posZ;
            double y = ent.posY + (double)(ent.getEyeHeight() / 2.0F);
            return getRotationFromPosition(x, z, y);
      }

      public static float[] rotats(Entity entity) {
            MathHelper mathHelper = new MathHelper();
            float predictz = (float)(entity.lastTickPosZ - entity.posZ);
            float predictx = (float)(entity.lastTickPosX - entity.posX);
            float predicty = (float)(entity.lastTickPosY - entity.posY);
            float var10000 = (float)Main.settingsManager.getSettingByName(Main.moduleManager.getModule(KillAura.class), "Predict").getValInt();
            Minecraft var10001 = mc;
            float predictvalue = (var10000 + Minecraft.player.getDistanceToEntity(entity)) / 2.0F;
            var10001 = mc;
            double diffX = entity.posX - Minecraft.player.posX + (double)(-predictx * predictvalue);
            var10001 = mc;
            double diffZ = entity.posZ - Minecraft.player.posZ + (double)(-predictz * predictvalue);
            double diffY;
            Minecraft var10002;
            double var23;
            if (entity instanceof EntityLivingBase) {
                  var23 = entity.posY + (double)entity.getEyeHeight();
                  var10001 = mc;
                  var10002 = mc;
                  diffY = var23 - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight()) - 0.4D + (double)(-predicty * predictvalue);
            } else {
                  var23 = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0D;
                  var10001 = mc;
                  var10002 = mc;
                  diffY = var23 - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight()) + (double)(-predicty * predictvalue);
            }

            float randomYaw = Main.settingsManager.getSettingByName(Main.moduleManager.getModule(KillAura.class), "Yaw Random").getValFloat();
            float randomPitch = Main.settingsManager.getSettingByName(Main.moduleManager.getModule(KillAura.class), "Pitch Random").getValFloat();
            double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
            float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D - 90.0D) + CountHelper.nextFloat(-randomYaw, randomYaw);
            float yawBody = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D - 90.0D);
            float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D)) + CountHelper.nextFloat(-randomPitch, randomPitch);
            Minecraft var24 = mc;
            float yawDegrees = MathHelper.wrapDegrees(Minecraft.player.prevRotationYaw);
            var24 = mc;
            float yawBodyDegrees = MathHelper.wrapDegrees(Minecraft.player.prevRotationYaw);
            var24 = mc;
            float pitchDegrees = MathHelper.wrapDegrees(Minecraft.player.prevRotationPitch);
            var10001 = mc;
            if (Math.abs(yaw - Minecraft.player.rotationYaw) > 155.0F) {
                  var24 = mc;
                  Minecraft.player.setSprinting(false);
            }

            float point = 0.0334F;
            if (entity != null) {
                  yaw = yawDegrees + GCDFix.getFixedRotation(MathHelper.wrapDegrees(me.rich.helpers.math.MathHelper.lerp(yaw - yawDegrees, yaw, point)));
                  yawBody = yawBodyDegrees + GCDFix.getFixedRotation(MathHelper.wrapDegrees(me.rich.helpers.math.MathHelper.lerp(yawBody - yawBodyDegrees, yawBody, point)));
                  pitch = pitchDegrees + GCDFix.getFixedRotation(MathHelper.wrapDegrees(me.rich.helpers.math.MathHelper.lerp(pitch - pitchDegrees, pitch, point)));
            } else {
                  yaw = yawDegrees + GCDFix.getFixedRotation(MathHelper.wrapDegrees(me.rich.helpers.math.MathHelper.lerp(yaw, yaw - yawDegrees, point)));
                  yawBody = yawBodyDegrees + GCDFix.getFixedRotation(MathHelper.wrapDegrees(me.rich.helpers.math.MathHelper.lerp(yawBody, yawBody - yawBodyDegrees, point)));
                  pitch = pitchDegrees + GCDFix.getFixedRotation(MathHelper.wrapDegrees(me.rich.helpers.math.MathHelper.lerp(pitch, pitch - pitchDegrees, point)));
            }

            pitch = MathHelper.clamp(pitch, -90.0F, 90.0F);
            return new float[]{yaw, pitch, yawBody};
      }

      public static float[] getRotations3(Entity entity) {
            Minecraft var10001 = mc;
            double diffX = entity.posX - Minecraft.player.posX;
            var10001 = mc;
            double diffZ = entity.posZ - Minecraft.player.posZ;
            double diffY;
            double var10000;
            Minecraft var10002;
            if (entity instanceof EntityLivingBase) {
                  var10000 = entity.posY + (double)entity.getEyeHeight();
                  var10001 = mc;
                  var10002 = mc;
                  diffY = var10000 - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight()) - 1.2D;
            } else {
                  var10000 = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0D;
                  var10001 = mc;
                  var10002 = mc;
                  diffY = var10000 - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
            }

            double dist = (double)MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
            float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D - 90.0D) + CountHelper.nextFloat(0.0F, 0.0F);
            float yawBody = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D - 90.0D);
            float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0D / 5.0D + (double)CountHelper.nextFloat(-1.0F, 1.0F)));
            float pitch2 = (float)(-(Math.atan2(diffY, dist) * 180.0D / 5.0D));
            var10001 = mc;
            Minecraft var13;
            if (Math.abs(yaw - Minecraft.player.rotationYaw) > 180.0F) {
                  var13 = mc;
                  Minecraft.player.setSprinting(false);
            }

            var13 = mc;
            var10002 = mc;
            yaw = Minecraft.player.prevRotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yaw - Minecraft.player.rotationYaw));
            var13 = mc;
            var10002 = mc;
            yawBody = Minecraft.player.prevRotationYaw + MathHelper.wrapDegrees(yawBody - Minecraft.player.rotationYaw);
            var13 = mc;
            var10002 = mc;
            pitch = Minecraft.player.prevRotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitch - Minecraft.player.rotationPitch));
            pitch = MathHelper.clamp(pitch, -90.0F, 90.0F);
            return new float[]{yaw, pitch, yawBody, pitch2};
      }

      public static float[] rotats1(Entity entity) {
            Minecraft var10001 = mc;
            double diffX = entity.posX - Minecraft.player.posX;
            var10001 = mc;
            double diffZ = entity.posZ - Minecraft.player.posZ;
            double var10000 = entity.posY + (double)entity.getEyeHeight();
            var10001 = mc;
            Minecraft var10002 = mc;
            double diffY = var10000 - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight()) - 0.4D;
            double dist = (double)MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
            float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D - 90.0D) + CountHelper.nextFloat(-1.0F, 1.0F);
            float yawBody = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D - 90.0D);
            float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D)) + CountHelper.nextFloat(-1.0F, 1.0F);
            float pitch2 = (float)(-(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D));
            Minecraft var13 = mc;
            Minecraft.player.setSprinting(false);
            var13 = mc;
            var10002 = mc;
            yaw = Minecraft.player.prevRotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yaw - Minecraft.player.rotationYaw));
            var13 = mc;
            var10002 = mc;
            yawBody = Minecraft.player.prevRotationYaw + MathHelper.wrapDegrees(yawBody - Minecraft.player.rotationYaw);
            var13 = mc;
            var10002 = mc;
            pitch = Minecraft.player.prevRotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitch - Minecraft.player.rotationPitch));
            pitch = MathHelper.clamp(pitch, -90.0F, 90.0F);
            return new float[]{yaw, pitch, yawBody, pitch2};
      }
}
