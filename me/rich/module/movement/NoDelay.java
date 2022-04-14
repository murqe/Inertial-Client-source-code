package me.rich.module.movement;

import de.Hero.settings.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;

public class NoDelay extends Feature {
      public NoDelay() {
            super("NoDelay", 0, Category.RENDER);
            Main.settingsManager.rSetting(new Setting("NoJumpDelay", this, true));
      }

      @EventTarget
      public void onUpdate(EventUpdate event) {
            if (Main.settingsManager.getSettingByName("NoJumpDelay").getValBoolean()) {
                  Minecraft var10000 = mc;
                  Minecraft.player.jumpTicks = 0;
            }

      }

      public void onDisable() {
            super.onDisable();
            mc.rightClickDelayTimer = 6;
            Minecraft var10000 = mc;
            Minecraft.player.jumpTicks = 6;
      }
}
