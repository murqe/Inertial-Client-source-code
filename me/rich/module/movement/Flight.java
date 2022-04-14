package me.rich.module.movement;

import de.Hero.settings.Setting;
import java.util.ArrayList;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;

public class Flight extends Feature {
      Setting speed;

      public Flight() {
            super("Fly", 0, Category.MOVEMENT);
            ArrayList mode = new ArrayList();
            mode.add("Glide");
            mode.add("ReallyWorld");
            Main.settingsManager.rSetting(new Setting("Fly Mode", this, "Glide", mode));
      }

      @EventTarget
      public void onUpdate(EventUpdate event) {
            String mode = Main.settingsManager.getSettingByName("Fly Mode").getValString();
            if (mode.equalsIgnoreCase("Glide")) {
                  if (Minecraft.player.onGround) {
                        Minecraft.player.jump();
                        timerHelper.resetTime();
                  } else if (!Minecraft.player.onGround && timerHelper.check(280.0F)) {
                        Minecraft.player.motionY = -0.01D;
                        Minecraft.player.capabilities.isFlying = true;
                        Minecraft.player.capabilities.flySpeed = 0.3F;
                  }
            }

            if (mode.equalsIgnoreCase("ReallyWorld") && mc.gameSettings.keyBindJump.isKeyDown()) {
                  Minecraft.player.jumpMovementFactor = 0.0F;
                  Minecraft.player.jump();
            }

      }

      public void onEnable() {
            super.onEnable();
      }

      public void onDisable() {
            Minecraft.player.capabilities.isFlying = false;
            Minecraft.player.capabilities.flySpeed = 0.05F;
            super.onDisable();
      }
}
