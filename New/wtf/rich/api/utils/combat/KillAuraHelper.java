package wtf.rich.api.utils.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import wtf.rich.Main;
import wtf.rich.api.utils.Helper;
import wtf.rich.api.utils.friend.Friend;
import wtf.rich.api.utils.math.MathematicHelper;
import wtf.rich.api.utils.world.InventoryHelper;
import wtf.rich.client.features.impl.combat.AntiBot;
import wtf.rich.client.features.impl.combat.KillAura;

public class KillAuraHelper implements Helper {
     public static boolean canAttack(EntityLivingBase player) {
          if (player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob || player instanceof EntityVillager) {
               if (player instanceof EntityPlayer && !KillAura.auraplayers.getBoolValue()) {
                    return false;
               }

               if (player instanceof EntityAnimal && !KillAura.auramobs.getBoolValue()) {
                    return false;
               }

               if (player instanceof EntityMob && !KillAura.auramobs.getBoolValue()) {
                    return false;
               }

               if (player instanceof EntityVillager && !KillAura.auramobs.getBoolValue()) {
                    return false;
               }

               if (KillAura.ignoreNakedPlayer.getBoolValue() && !InventoryHelper.isArmorPlayer(player)) {
                    return false;
               }
          }

          if (player.isInvisible() && !KillAura.invisiblecheck.getBoolValue()) {
               return false;
          } else if (player instanceof EntityArmorStand) {
               return false;
          } else {
               Iterator var1 = Main.instance.friendManager.getFriends().iterator();

               Friend friend;
               do {
                    if (!var1.hasNext()) {
                         if (Main.instance.featureDirector.getFeatureByClass(AntiBot.class).isToggled() && AntiBot.isBotPlayer.contains(player)) {
                              return false;
                         }

                         if (KillAura.teamCheck.getBoolValue() && isOnSameTeam(player)) {
                              return false;
                         }

                         if (!RotationHelper.canSeeEntityAtFov(player, KillAura.fov.getNumberValue()) && !canSeeEntityAtFov(player, KillAura.fov.getNumberValue())) {
                              return false;
                         }

                         if (!range(player, (double)KillAura.range.getNumberValue())) {
                              return false;
                         }

                         if (!player.canEntityBeSeen(mc.player)) {
                              return KillAura.walls.getBoolValue();
                         }

                         return player != mc.player;
                    }

                    friend = (Friend)var1.next();
               } while(!player.getName().equals(friend.getName()));

               return false;
          }
     }

     private static boolean range(EntityLivingBase entity, double range) {
          mc.player.getDistanceToEntity(entity);
          return (double)mc.player.getDistanceToEntity(entity) <= range;
     }

     public static boolean isOnSameTeam(Entity entityIn) {
          return mc.player.getDisplayName().getUnformattedText().substring(0, 2).equals(entityIn.getDisplayName().getUnformattedText().substring(0, 2)) || mc.player.isOnSameTeam(entityIn);
     }

     public static boolean canSeeEntityAtFov(Entity entityLiving, float scope) {
          double diffX = entityLiving.posX - mc.player.posX;
          double diffZ = entityLiving.posZ - mc.player.posZ;
          float newYaw = (float)(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0D);
          double d = (double)newYaw;
          double difference = angleDifference(d, (double)mc.player.rotationYaw);
          return difference <= (double)scope;
     }

     public static EntityLivingBase getSortEntities() {
          List e2 = new ArrayList();
          Iterator var1 = mc.world.loadedEntityList.iterator();

          while(var1.hasNext()) {
               Entity e = (Entity)var1.next();
               if (e instanceof EntityLivingBase) {
                    EntityLivingBase player = (EntityLivingBase)e;
                    if (mc.player.getDistanceToEntity(player) < KillAura.range.getNumberValue() && canAttack(player)) {
                         e2.add(player);
                    }
               }
          }

          String sort = KillAura.sortMode.getOptions();
          if (sort.equalsIgnoreCase("Health")) {
               e2.sort((o1, o2) -> {
                    return (int)(o1.getHealth() - o2.getHealth());
               });
          } else if (sort.equalsIgnoreCase("Distance")) {
               EntityPlayerSP var10001 = mc.player;
               var10001.getClass();
               e2.sort(Comparator.comparingDouble(var10001::getDistanceToEntity));
          }

          if (e2.isEmpty()) {
               return null;
          } else {
               return (EntityLivingBase)e2.get(0);
          }
     }

     public static boolean canApsAttack() {
          int apsMultiplier = (int)(14.0F / MathematicHelper.randomizeFloat((float)((int)KillAura.maxAps.getNumberValue()), (float)((int)KillAura.minAps.getNumberValue())));
          if (timerHelper.hasReached((double)(50 * apsMultiplier))) {
               timerHelper.reset();
               return true;
          } else {
               return false;
          }
     }

     public static double angleDifference(double a, double b) {
          float yaw360 = (float)(Math.abs(a - b) % 360.0D);
          if (yaw360 > 180.0F) {
               yaw360 = 360.0F - yaw360;
          }

          return (double)yaw360;
     }
}
