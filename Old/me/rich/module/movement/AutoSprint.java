package me.rich.module.movement;

import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;

public class AutoSprint extends Feature {
      public AutoSprint() {
            super("AutoSprint", 0, Category.MOVEMENT);
      }

      @EventTarget
      public void lox(EventUpdate event) {
            if (this.isToggled() && mc.gameSettings.keyBindForward.isKeyDown()) {
                  Minecraft.player.setSprinting(true);
            }

      }

      public void onEnables() {
      }

      public void onDisable() {
            Minecraft var10000 = mc;
            Minecraft.player.setSprinting(false);
      }
}
