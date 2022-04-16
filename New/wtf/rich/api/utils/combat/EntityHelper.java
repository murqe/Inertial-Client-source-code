package wtf.rich.api.utils.combat;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import wtf.rich.api.utils.Helper;

public class EntityHelper implements Helper {
     public static double getDistance(double x, double y, double z, double x1, double y1, double z1) {
          double posX = x - x1;
          double posY = y - y1;
          double posZ = z - z1;
          return Math.sqrt(posX * posX + posY * posY + posZ * posZ);
     }

     public static double getDistance(double x1, double z1, double x2, double z2) {
          double deltaX = x1 - x2;
          double deltaZ = z1 - z2;
          return Math.hypot(deltaX, deltaZ);
     }

     public static Entity rayCast(Entity entityIn, double range) {
          Vec3d vec = entityIn.getPositionVector().add(new Vec3d(0.0D, (double)entityIn.getEyeHeight(), 0.0D));
          Vec3d vecPositionVector = mc.player.getPositionVector().add(new Vec3d(0.0D, (double)mc.player.getEyeHeight(), 0.0D));
          AxisAlignedBB axis = mc.player.getEntityBoundingBox().addCoord(vec.xCoord - vecPositionVector.xCoord, vec.yCoord - vecPositionVector.yCoord, vec.zCoord - vecPositionVector.zCoord).expand(1.0D, 1.0D, 1.0D);
          Entity entityRayCast = null;
          Iterator var7 = mc.world.getEntitiesWithinAABBExcludingEntity(mc.player, axis).iterator();

          while(true) {
               while(true) {
                    Entity entity;
                    do {
                         do {
                              if (!var7.hasNext()) {
                                   return entityRayCast;
                              }

                              entity = (Entity)var7.next();
                         } while(!entity.canBeCollidedWith());
                    } while(!(entity instanceof EntityLivingBase));

                    float size = entity.getCollisionBorderSize();
                    AxisAlignedBB axis1 = entity.getEntityBoundingBox().expand((double)size, (double)size, (double)size);
                    RayTraceResult rayTrace = axis1.calculateIntercept(vecPositionVector, vec);
                    if (axis1.isVecInside(vecPositionVector)) {
                         if (range >= 0.0D) {
                              entityRayCast = entity;
                              range = 0.0D;
                         }
                    } else if (rayTrace != null) {
                         double dist = vecPositionVector.distanceTo(rayTrace.hitVec);
                         if (range == 0.0D || dist < range) {
                              entityRayCast = entity;
                              range = dist;
                         }
                    }
               }
          }
     }

     public static boolean isTeamWithYou(EntityLivingBase entity) {
          if (mc.player != null && entity != null && mc.player.getDisplayName() != null && entity.getDisplayName() != null) {
               if (mc.player.getTeam() != null && entity.getTeam() != null && mc.player.getTeam().isSameTeam(entity.getTeam())) {
                    return true;
               } else {
                    String targetName = entity.getDisplayName().getFormattedText().replace("§r", "");
                    String clientName = mc.player.getDisplayName().getFormattedText().replace("§r", "");
                    return targetName.startsWith("§" + clientName.charAt(1));
               }
          } else {
               return false;
          }
     }

     public static boolean checkArmor(Entity entity) {
          return ((EntityLivingBase)entity).getTotalArmorValue() < 6;
     }

     public static int getPing(EntityPlayer entityPlayer) {
          return mc.player.connection.getPlayerInfo(entityPlayer.getUniqueID()).getResponseTime();
     }

     public static double getDistanceOfEntityToBlock(Entity entity, BlockPos pos) {
          return getDistance(entity.posX, entity.posY, entity.posZ, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
     }
}
