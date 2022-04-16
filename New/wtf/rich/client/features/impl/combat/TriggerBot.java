package wtf.rich.client.features.impl.combat;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import wtf.rich.Main;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.api.utils.friend.Friend;
import wtf.rich.api.utils.movement.MovementHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class TriggerBot extends Feature {
     public static NumberSetting range;
     public static BooleanSetting players;
     public static BooleanSetting mobs;
     public static BooleanSetting onlyCrit = new BooleanSetting("Only Crits", false, () -> {
          return true;
     });
     public static NumberSetting critFallDist = new NumberSetting("Fall Distance", 0.2F, 0.08F, 1.0F, 0.01F, () -> {
          return onlyCrit.getBoolValue();
     });

     public TriggerBot() {
          super("TriggerBot", "Автоматически наносит удар при наводке на сущность", 0, Category.COMBAT);
          players = new BooleanSetting("Players", true, () -> {
               return true;
          });
          mobs = new BooleanSetting("Mobs", false, () -> {
               return true;
          });
          range = new NumberSetting("Trigger Range", 4.0F, 1.0F, 6.0F, 0.1F, () -> {
               return true;
          });
          this.addSettings(new Setting[]{range, players, mobs, onlyCrit, critFallDist});
     }

     public static boolean canTrigger(EntityLivingBase player) {
          Iterator var1 = Main.instance.friendManager.getFriends().iterator();

          while(var1.hasNext()) {
               Friend friend = (Friend)var1.next();
               if (player.getName().equals(friend.getName())) {
                    return false;
               }
          }

          if (player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob || player instanceof EntityVillager) {
               if (player instanceof EntityPlayer && !players.getBoolValue()) {
                    return false;
               }

               if (player instanceof EntityAnimal && !mobs.getBoolValue()) {
                    return false;
               }

               if (player instanceof EntityMob && !mobs.getBoolValue()) {
                    return false;
               }

               if (player instanceof EntityVillager && !mobs.getBoolValue()) {
                    return false;
               }
          }

          return player != mc.player;
     }

     @EventTarget
     public void onUpdate(EventUpdate event) {
          Entity entity = mc.objectMouseOver.entityHit;
          if (entity != null && mc.player.getDistanceToEntity(entity) <= range.getNumberValue() && !(entity instanceof EntityEnderCrystal) && !entity.isDead && ((EntityLivingBase)entity).getHealth() > 0.0F) {
               if (MovementHelper.isBlockAboveHead()) {
                    if (mc.player.fallDistance < critFallDist.getNumberValue() && mc.player.getCooledAttackStrength(0.8F) == 1.0F && onlyCrit.getBoolValue() && !mc.player.isOnLadder() && !mc.player.isInLiquid() && !mc.player.isInWeb && mc.player.getRidingEntity() == null) {
                         return;
                    }
               } else if (mc.player.fallDistance != 0.0F && onlyCrit.getBoolValue() && !mc.player.isOnLadder() && !mc.player.isInLiquid() && !mc.player.isInWeb && mc.player.getRidingEntity() == null) {
                    return;
               }

               if (canTrigger((EntityLivingBase)entity) && mc.player.getCooledAttackStrength(0.0F) == 1.0F) {
                    mc.playerController.attackEntity(mc.player, entity);
                    mc.player.swingArm(EnumHand.MAIN_HAND);
               }

          }
     }
}
