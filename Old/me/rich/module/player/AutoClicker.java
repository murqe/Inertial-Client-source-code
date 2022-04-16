package me.rich.module.player;

import me.rich.event.EventTarget;
import me.rich.event.events.EventPreMotionUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;

public class AutoClicker extends Feature {
      public AutoClicker() {
            super("AutoClicker", 0, Category.COMBAT);
      }

      @EventTarget
      public void onUpdate(EventPreMotionUpdate event) {
            if (Minecraft.player.getCooledAttackStrength(0.0F) == 1.0F && mc.gameSettings.keyBindAttack.pressed) {
                  mc.clickMouse();
            }

      }
}
