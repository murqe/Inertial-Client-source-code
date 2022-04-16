package wtf.rich.api.utils.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventSendPacket;
import wtf.rich.api.utils.math.MathematicHelper;
import wtf.rich.api.utils.math.RandomHelper;
import wtf.rich.client.features.impl.combat.KillAura;

public class RotationHelper {
     private static Minecraft mc = Minecraft.getMinecraft();

     public static float[] getRatations(Entity entity) {
          Vec3d eyesPos = new Vec3d(mc.player.posX + (double)(MathematicHelper.randomizeFloat(-1.0F, 2.0F) / 15.0F), mc.player.posY + (double)mc.player.getEyeHeight() - 0.6D, mc.player.posZ + (double)(MathematicHelper.randomizeFloat(-1.0F, 2.0F) / 15.0F));
          double diffX = entity.getPositionVector().xCoord - eyesPos.xCoord;
          double diffY = entity.getPositionVector().yCoord - eyesPos.yCoord;
          double diffZ = entity.getPositionVector().zCoord - eyesPos.zCoord;
          double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
          float yaw = MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F);
          float pitch = MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(diffY, diffXZ))) - 10.0F);
          float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
          float gcd = f * f * f * 1.2F;
          yaw -= yaw % gcd;
          pitch -= pitch % gcd;
          return new float[]{yaw, pitch};
     }

     public static float[] getRotationVector(Vec3d vec, boolean randomRotation, float yawRandom, float pitchRandom, float speedRotation) {
          Vec3d eyesPos = getEyesPos();
          double diffX = vec.xCoord - eyesPos.xCoord;
          double diffY = vec.yCoord - (mc.player.posY + (double)mc.player.getEyeHeight() + 0.5D);
          double diffZ = vec.zCoord - eyesPos.zCoord;
          double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
          float randomYaw = 0.0F;
          if (randomRotation) {
               randomYaw = MathematicHelper.randomizeFloat(-yawRandom, yawRandom);
          }

          float randomPitch = 0.0F;
          if (randomRotation) {
               randomPitch = MathematicHelper.randomizeFloat(-pitchRandom, pitchRandom);
          }

          float yaw = (float)(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0D) + randomYaw;
          float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ))) + randomPitch;
          yaw = mc.player.rotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yaw - mc.player.rotationYaw));
          pitch = mc.player.rotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitch - mc.player.rotationPitch));
          pitch = MathHelper.clamp(pitch, -90.0F, 90.0F);
          yaw = updateRotation(mc.player.rotationYaw, yaw, speedRotation);
          pitch = updateRotation(mc.player.rotationPitch, pitch, speedRotation);
          return new float[]{yaw, pitch};
     }

     public static float[] getSunriseRotations(Entity entityIn, boolean random, float maxSpeed, float minSpeed, float yawRandom, float pitchRandom) {
          double diffX = entityIn.posX + (entityIn.posX - entityIn.prevPosX) * (double)KillAura.rotPredict.getNumberValue() - mc.player.posX - mc.player.motionX * (double)KillAura.rotPredict.getNumberValue();
          double diffZ = entityIn.posZ + (entityIn.posZ - entityIn.prevPosZ) * (double)KillAura.rotPredict.getNumberValue() - mc.player.posZ - mc.player.motionZ * (double)KillAura.rotPredict.getNumberValue();
          double diffY;
          if (entityIn instanceof EntityLivingBase) {
               diffY = entityIn.posY + (double)entityIn.getEyeHeight() - (mc.player.posY + (double)mc.player.getEyeHeight()) - 0.6D - (KillAura.walls.getBoolValue() && !((EntityLivingBase)entityIn).canEntityBeSeen(mc.player) ? -0.38D : 0.0D);
          } else {
               diffY = (entityIn.getEntityBoundingBox().minY + entityIn.getEntityBoundingBox().maxY) / 2.0D - (mc.player.posY + (double)mc.player.getEyeHeight());
          }

          double diffXZ = (double)MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
          diffY /= diffXZ;
          float randomYaw = 0.0F;
          if (random) {
               randomYaw = MathematicHelper.randomizeFloat(yawRandom, -yawRandom);
          }

          float randomPitch = 0.0F;
          if (random) {
               randomPitch = MathematicHelper.randomizeFloat(pitchRandom, -pitchRandom);
          }

          float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D - 90.0D) + randomYaw;
          float pitch = (float)(-(Math.atan2(diffY, diffXZ) * 180.0D / 3.141592653589793D)) + randomPitch;
          yaw = mc.player.rotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yaw - mc.player.rotationYaw));
          pitch = mc.player.rotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitch - mc.player.rotationPitch));
          pitch = MathHelper.clamp(pitch, -90.0F, 90.0F);
          yaw = updateRotation(mc.player.rotationYaw, yaw, MathematicHelper.randomizeFloat(minSpeed, maxSpeed));
          pitch = updateRotation(mc.player.rotationPitch, pitch, MathematicHelper.randomizeFloat(minSpeed, maxSpeed));
          return new float[]{yaw, pitch};
     }

     public static float[] getMatrixRotations(Entity entityIn, boolean random, float yawRandom, float pitchRandom) {
          double diffX = entityIn.posX + (entityIn.posX - entityIn.prevPosX) * (double)KillAura.rotPredict.getNumberValue() - mc.player.posX - mc.player.motionX * (double)KillAura.rotPredict.getNumberValue();
          double diffZ = entityIn.posZ + (entityIn.posZ - entityIn.prevPosZ) * (double)KillAura.rotPredict.getNumberValue() - mc.player.posZ - mc.player.motionZ * (double)KillAura.rotPredict.getNumberValue();
          double diffY;
          if (entityIn instanceof EntityLivingBase) {
               diffY = entityIn.posY + (double)entityIn.getEyeHeight() - 0.2D - (mc.player.posY + (double)mc.player.getEyeHeight()) - (KillAura.walls.getBoolValue() && !((EntityLivingBase)entityIn).canEntityBeSeen(mc.player) ? -0.38D : 0.0D);
          } else {
               diffY = (entityIn.getEntityBoundingBox().minY + entityIn.getEntityBoundingBox().maxY) / 2.0D - (mc.player.posY + (double)mc.player.getEyeHeight());
          }

          float randomYaw = 0.0F;
          if (random) {
               randomYaw = MathematicHelper.randomizeFloat(yawRandom, -yawRandom);
          }

          float randomPitch = 0.0F;
          if (random) {
               randomPitch = MathematicHelper.randomizeFloat(pitchRandom, -pitchRandom);
          }

          double diffXZ = (double)MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
          diffY /= diffXZ;
          float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D - 90.0D) + randomYaw;
          float pitch = (float)(-(Math.atan2(diffY, diffXZ) * 180.0D / 3.141592653589793D)) + randomPitch;
          yaw = mc.player.rotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yaw - mc.player.rotationYaw));
          pitch = mc.player.rotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitch - mc.player.rotationPitch));
          pitch = MathHelper.clamp(pitch, -90.0F, 90.0F);
          return new float[]{yaw, pitch};
     }

     public static boolean isLookingAtEntity(float yaw, float pitch, float xExp, float yExp, float zExp, Entity entity, double range) {
          Vec3d src = mc.player.getPositionEyes(1.0F);
          Vec3d vectorForRotation = Entity.getVectorForRotation(pitch, yaw);
          Vec3d dest = src.addVector(vectorForRotation.xCoord * range, vectorForRotation.yCoord * range, vectorForRotation.zCoord * range);
          RayTraceResult rayTraceResult = mc.world.rayTraceBlocks(src, dest, false, false, true);
          if (rayTraceResult == null) {
               return false;
          } else {
               return entity.getEntityBoundingBox().expand((double)xExp, (double)yExp, (double)zExp).calculateIntercept(src, dest) != null;
          }
     }

     public static float updateRotation(float current, float newValue, float speed) {
          float f = MathHelper.wrapDegrees(newValue - current);
          if (f > speed) {
               f = speed;
          }

          if (f < -speed) {
               f = -speed;
          }

          return current + f;
     }

     public static float[] getNewFaceRotating(Entity entity) {
          double xDelta = entity.posX - entity.lastTickPosX;
          double zDelta = entity.posZ - entity.lastTickPosZ;
          double xMulti = 1.0D;
          double zMulti = 1.0D;
          boolean sprint = entity.isSprinting();
          xMulti = xDelta * (sprint ? 0.97D : 1.0D);
          zMulti = zDelta * (sprint ? 0.97D : 1.0D);
          double x = entity.posX + xMulti - mc.player.posX;
          double z = entity.posZ + zMulti - mc.player.posZ;
          double y;
          if (entity instanceof EntityLivingBase) {
               EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
               float randomed = RandomHelper.nextFloat((float)(entitylivingbase.posY + (double)(entitylivingbase.getEyeHeight() / 1.5F)), (float)(entitylivingbase.posY + (double)entitylivingbase.getEyeHeight() - (double)(entitylivingbase.getEyeHeight() / 3.0F)));
               y = (double)randomed - mc.player.posY + (double)mc.player.getEyeHeight();
          } else {
               y = (double)RandomHelper.nextFloat((float)entity.getEntityBoundingBox().minY, (float)entity.getEntityBoundingBox().maxY) - mc.player.posY + (double)mc.player.getEyeHeight();
          }

          double distance = (double)mc.player.getDistanceToEntity(entity);
          float yaw = (float)(Math.toDegrees(Math.atan2(z, x)) - 90.0D) + RandomHelper.nextFloat(-1.0F, 2.0F);
          float pitch = (float)(Math.toDegrees(Math.atan2(y, distance)) + (double)RandomHelper.nextFloat(-1.0F, 2.0F));
          yaw = mc.player.rotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yaw - mc.player.rotationYaw));
          pitch = mc.player.rotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitch - mc.player.rotationPitch));
          pitch = MathHelper.clamp(pitch, -90.0F, 90.0F);
          return new float[]{yaw, pitch};
     }

     public static boolean isAimAtMe(Entity entity, float breakRadius) {
          float entityYaw = MathHelper.wrapDegrees(entity.rotationYaw);
          return Math.abs(MathHelper.wrapDegrees(getYawToEntity(entity, Minecraft.getMinecraft().player)) - entityYaw) <= breakRadius;
     }

     public static boolean isAimAtMe(Entity entity) {
          float entityYaw = getNormalizedYaw(entity.rotationYaw);
          float entityPitch = entity.rotationPitch;
          double pMinX = mc.player.getEntityBoundingBox().minX;
          double pMaxX = mc.player.getEntityBoundingBox().maxX;
          double pMaxY = mc.player.posY + (double)mc.player.height;
          double pMinY = mc.player.getEntityBoundingBox().minY;
          double pMaxZ = mc.player.getEntityBoundingBox().maxZ;
          double pMinZ = mc.player.getEntityBoundingBox().minZ;
          double eX = entity.posX;
          double eY = entity.posY + (double)entity.height;
          double eZ = entity.posZ;
          double dMaxX = pMaxX - eX;
          double dMaxY = pMaxY - eY;
          double dMaxZ = pMaxZ - eZ;
          double dMinX = pMinX - eX;
          double dMinY = pMinY - eY;
          double dMinZ = pMinZ - eZ;
          double dMinH = Math.sqrt(Math.pow(dMinX, 2.0D) + Math.pow(dMinZ, 2.0D));
          double dMaxH = Math.sqrt(Math.pow(dMaxX, 2.0D) + Math.pow(dMaxZ, 2.0D));
          double maxPitch = 90.0D - Math.toDegrees(Math.atan2(dMaxH, dMaxY));
          double minPitch = 90.0D - Math.toDegrees(Math.atan2(dMinH, dMinY));
          boolean yawAt = Math.abs(getNormalizedYaw(getYawToEntity(entity, mc.player)) - entityYaw) <= 16.0F - mc.player.getDistanceToEntity(entity) / 2.0F;
          boolean pitchAt = maxPitch >= (double)entityPitch && (double)entityPitch >= minPitch || minPitch >= (double)entityPitch && (double)entityPitch >= maxPitch;
          return yawAt && pitchAt;
     }

     public static float getYawToEntity(Entity mainEntity, Entity targetEntity) {
          double pX = mainEntity.posX;
          double pZ = mainEntity.posZ;
          double eX = targetEntity.posX;
          double eZ = targetEntity.posZ;
          double dX = pX - eX;
          double dZ = pZ - eZ;
          double yaw = Math.toDegrees(Math.atan2(dZ, dX)) + 90.0D;
          return (float)yaw;
     }

     public static float getNormalizedYaw(float yaw) {
          float yawStageFirst = yaw % 360.0F;
          if (yawStageFirst > 180.0F) {
               yawStageFirst -= 360.0F;
               return yawStageFirst;
          } else if (yawStageFirst < -180.0F) {
               yawStageFirst += 360.0F;
               return yawStageFirst;
          } else {
               return yawStageFirst;
          }
     }

     public static float[] getRotations(Entity entityIn, boolean random, float yawRandom, float pitchRandom) {
          double diffX = entityIn.posX + (entityIn.posX - entityIn.prevPosX) - mc.player.posX - mc.player.motionX;
          double diffZ = entityIn.posZ + (entityIn.posZ - entityIn.prevPosZ) - mc.player.posZ - mc.player.motionZ;
          double diffY;
          if (entityIn instanceof EntityLivingBase) {
               diffY = entityIn.posY + (double)entityIn.getEyeHeight() - (mc.player.posY + (double)mc.player.getEyeHeight()) - 0.35D;
          } else {
               diffY = (entityIn.getEntityBoundingBox().minY + entityIn.getEntityBoundingBox().maxY) / 2.0D - (mc.player.posY + (double)mc.player.getEyeHeight());
          }

          if (!mc.player.canEntityBeSeen(entityIn)) {
               diffY = entityIn.posY + (double)entityIn.height - (mc.player.posY + (double)mc.player.getEyeHeight());
          }

          double diffXZ = (double)MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
          float randomYaw = 0.0F;
          if (random) {
               randomYaw = MathematicHelper.randomizeFloat(yawRandom, -yawRandom);
          }

          float randomPitch = 0.0F;
          if (random) {
               randomPitch = MathematicHelper.randomizeFloat(pitchRandom, -pitchRandom);
          }

          float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D - 90.0D) + randomYaw;
          float pitch = (float)(-(Math.atan2(diffY, diffXZ) * 180.0D / 3.141592653589793D)) + randomPitch;
          yaw = mc.player.rotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yaw - mc.player.rotationYaw));
          pitch = mc.player.rotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitch - mc.player.rotationPitch));
          pitch = MathHelper.clamp(pitch, -90.0F, 90.0F);
          yaw = updateRotation(mc.player.rotationYaw, yaw, MathematicHelper.randomizeFloat(360.0F, 360.0F));
          pitch = updateRotation(mc.player.rotationPitch, pitch, MathematicHelper.randomizeFloat(360.0F, 360.0F));
          return new float[]{yaw, pitch};
     }

     public static float[] getAACRotation(Entity entityIn, boolean random, float maxSpeed, float minSpeed, float yawRandom, float pitchRandom) {
          double diffX = entityIn.posX + (entityIn.posX - entityIn.prevPosX) * (double)KillAura.rotPredict.getNumberValue() - mc.player.posX - mc.player.motionX * (double)KillAura.rotPredict.getNumberValue();
          double diffZ = entityIn.posZ + (entityIn.posZ - entityIn.prevPosZ) * (double)KillAura.rotPredict.getNumberValue() - mc.player.posZ - mc.player.motionZ * (double)KillAura.rotPredict.getNumberValue();
          double diffY = entityIn.posY + (double)entityIn.getEyeHeight() - (mc.player.posY + (double)mc.player.getEyeHeight()) - 0.16D - (KillAura.walls.getBoolValue() && !((EntityLivingBase)entityIn).canEntityBeSeen(mc.player) ? -0.38D : 0.0D);
          float randomYaw = 0.0F;
          if (random) {
               randomYaw = MathematicHelper.randomizeFloat(yawRandom, -yawRandom);
          }

          float randomPitch = 0.0F;
          if (random) {
               randomPitch = MathematicHelper.randomizeFloat(pitchRandom, -pitchRandom);
          }

          double diffXZ = (double)MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
          float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D - 90.0D) + randomYaw;
          float pitch = (float)(-(Math.atan2(diffY, diffXZ) * 180.0D / 3.141592653589793D)) + randomPitch;
          yaw = mc.player.rotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yaw - mc.player.rotationYaw));
          pitch = mc.player.rotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitch - mc.player.rotationPitch));
          pitch = MathHelper.clamp(pitch, -90.0F, 90.0F);
          yaw = updateRotation(mc.player.rotationYaw, yaw, MathematicHelper.randomizeFloat(minSpeed, maxSpeed));
          pitch = updateRotation(mc.player.rotationPitch, pitch, MathematicHelper.randomizeFloat(minSpeed, maxSpeed));
          return new float[]{yaw, pitch};
     }

     public static float[] getRotationFromPosition(double x, double z, double y) {
          double xDiff = x - Minecraft.getMinecraft().player.posX;
          double zDiff = z - Minecraft.getMinecraft().player.posZ;
          double yDiff = y - Minecraft.getMinecraft().player.posY - 1.7D;
          double dist = (double)MathHelper.sqrt(xDiff * xDiff + zDiff * zDiff);
          float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
          float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D));
          return new float[]{yaw, pitch};
     }

     public static boolean canSeeEntityAtFov(Entity entityLiving, float scope) {
          Minecraft.getMinecraft();
          double diffX = entityLiving.posX - mc.player.posX;
          Minecraft.getMinecraft();
          double diffZ = entityLiving.posZ - mc.player.posZ;
          float newYaw = (float)(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0D);
          double d = (double)newYaw;
          Minecraft.getMinecraft();
          double difference = angleDifference(d, (double)mc.player.rotationYaw);
          return difference <= (double)scope;
     }

     public static double angleDifference(double a, double b) {
          float yaw360 = (float)(Math.abs(a - b) % 360.0D);
          if (yaw360 > 180.0F) {
               yaw360 = 360.0F - yaw360;
          }

          return (double)yaw360;
     }

     public static Vec3d getEyesPos() {
          return new Vec3d(mc.player.posX, mc.player.posY + (double)mc.player.getEyeHeight(), mc.player.posZ);
     }

     public static float[] getNeededFacing(Vec3d vec) {
          Vec3d eyesPos = getEyesPos();
          double diffX = vec.xCoord - eyesPos.xCoord;
          double diffY = vec.yCoord - mc.player.posY + (double)mc.player.getEyeHeight() + 0.2D;
          double diffZ = vec.zCoord - eyesPos.zCoord;
          double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
          float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
          float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
          yaw = mc.player.rotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yaw - mc.player.rotationYaw));
          pitch = mc.player.rotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitch - mc.player.rotationPitch));
          pitch = MathHelper.clamp(pitch, -90.0F, 90.0F);
          return new float[]{yaw, pitch};
     }

     public static float[] getLookAngles(Vec3d vec) {
          float[] angles = new float[]{(float)(Math.atan2(mc.player.posZ - vec.zCoord, mc.player.posX - vec.xCoord) / 3.141592653589793D * 180.0D) + 90.0F, 0.0F};
          float heights = (float)(mc.player.posY + (double)mc.player.getEyeHeight() - vec.yCoord);
          float distance = (float)Math.sqrt((mc.player.posZ - vec.zCoord) * (mc.player.posZ - vec.zCoord) + (mc.player.posX - vec.xCoord) * (mc.player.posX - vec.xCoord));
          angles[1] = (float)(Math.atan2((double)heights, (double)distance) / 3.141592653589793D * 180.0D);
          return angles;
     }

     public static class Rotation {
          public static boolean isReady = false;
          public static float packetPitch;
          public static float packetYaw;
          public static float lastPacketPitch;
          public static float lastPacketYaw;
          public static float renderPacketYaw;
          public static float lastRenderPacketYaw;
          public static float bodyYaw;
          public static float lastBodyYaw;
          public static int rotationCounter;
          public static boolean isAiming;

          public static boolean isAiming() {
               return !isAiming;
          }

          public static double calcMove() {
               double x = RotationHelper.mc.player.posX - RotationHelper.mc.player.prevPosX;
               double z = RotationHelper.mc.player.posZ - RotationHelper.mc.player.prevPosZ;
               return Math.hypot(x, z);
          }

          @EventTarget
          public void onSendPacket(EventSendPacket eventSendPacket) {
               if (eventSendPacket.getPacket() instanceof CPacketAnimation) {
                    rotationCounter = 10;
               }

          }
     }
}
