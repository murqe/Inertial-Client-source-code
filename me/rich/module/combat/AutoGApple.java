package me.rich.module.combat;

import de.Hero.settings.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;

public class AutoGApple extends Feature {
      private boolean eating = false;

      public void setup() {
            Main.settingsManager.rSetting(new Setting("Health", this, 15.0D, 1.0D, 20.0D, false));
      }

      public AutoGApple() {
            super("AutoGApple", 0, Category.COMBAT);
      }

      @EventTarget
      public void onUpdate(EventUpdate e) {
            this.setModuleName("AutoGApple §7" + Main.settingsManager.getSettingByName("Health").getValDouble() + "");
            Minecraft var10000 = mc;
            float var2 = Minecraft.player.getHealth();
            Minecraft var10001 = mc;
            if ((double)(var2 + Minecraft.player.getAbsorptionAmount()) > Main.settingsManager.getSettingByName("Health").getValDouble() && this.eating) {
                  this.eating = false;
                  this.stop();
            } else if (this.canEat()) {
                  var10001 = mc;
                  if (this.isFood(Minecraft.player.getHeldItemOffhand())) {
                        var10000 = mc;
                        if ((double)Minecraft.player.getHealth() <= Main.settingsManager.getSettingByName("Health").getValDouble() && this.canEat()) {
                              KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                              this.eating = true;
                        }
                  }

                  if (!this.canEat()) {
                        this.stop();
                  }

            }
      }

      public static boolean isNullOrEmptyStack(ItemStack itemStack) {
            return itemStack == null || itemStack.func_190926_b();
      }

      boolean isFood(ItemStack itemStack) {
            return !isNullOrEmptyStack(itemStack) && itemStack.getItem() instanceof ItemAppleGold;
      }

      public boolean canEat() {
            return mc.objectMouseOver == null || !(mc.objectMouseOver.entityHit instanceof EntityVillager);
      }

      void stop() {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
      }

      public void onEnable() {
            super.onEnable();
            NotificationPublisher.queue(this.getName(), "Was enabled.", NotificationType.INFO);
      }

      public void onDisable() {
            NotificationPublisher.queue(this.getName(), "Was disabled.", NotificationType.INFO);
            super.onDisable();
      }
}
