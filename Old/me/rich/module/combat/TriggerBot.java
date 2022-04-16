package me.rich.module.combat;

import de.Hero.settings.Setting;
import java.util.Iterator;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.helpers.friend.Friend;
import me.rich.helpers.friend.FriendManager;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

public class TriggerBot extends Feature {
      EntityLivingBase target;

      public TriggerBot() {
            super("TriggerBot", 0, Category.COMBAT);
            Main.settingsManager.rSetting(new Setting("Players", this, true));
            Main.settingsManager.rSetting(new Setting("Mobs", this, false));
            Main.settingsManager.rSetting(new Setting("Animals", this, false));
            Main.settingsManager.rSetting(new Setting("Villagers", this, false));
            Main.settingsManager.rSetting(new Setting("Invisibles", this, false));
      }

      @EventTarget
      public void pank(EventUpdate drain) {
            this.target = (EntityLivingBase)mc.objectMouseOver.entityHit;
            if (Minecraft.player != null && mc.world != null && this.target.isEntityAlive() && Minecraft.player.getCooledAttackStrength(0.0F) == 1.0F && Minecraft.player.getDistanceToEntity(this.target) <= 3.0F && this.blob(this.target)) {
                  Minecraft var10001 = mc;
                  mc.playerController.attackEntity(Minecraft.player, this.target);
                  Minecraft var10000 = mc;
                  Minecraft.player.swingArm(EnumHand.MAIN_HAND);
            }

      }

      public boolean blob(EntityLivingBase player) {
            if (player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob || player instanceof EntityVillager) {
                  if (player instanceof EntityPlayer && !Main.settingsManager.getSettingByName(this, "Players").getValBoolean()) {
                        return false;
                  }

                  if (player instanceof EntityMob && !Main.settingsManager.getSettingByName(this, "Mobs").getValBoolean()) {
                        return false;
                  }

                  if (player instanceof EntityAnimal && !Main.settingsManager.getSettingByName(this, "Animals").getValBoolean()) {
                        return false;
                  }

                  if (player instanceof EntityVillager && !Main.settingsManager.getSettingByName(this, "Villagers").getValBoolean()) {
                        return false;
                  }
            }

            if (player.isInvisible() && !Main.settingsManager.getSettingByName(this, "Invisibles").getValBoolean()) {
                  return false;
            } else {
                  Iterator var2 = FriendManager.friendsList.iterator();

                  Friend friend;
                  do {
                        if (!var2.hasNext()) {
                              return player != Minecraft.player;
                        }

                        friend = (Friend)var2.next();
                  } while(!player.getName().equals(friend.getName()));

                  return false;
            }
      }
}
