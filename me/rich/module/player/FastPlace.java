package me.rich.module.player;

import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;

public class FastPlace extends Feature {
      public FastPlace() {
            super("FastPlace", 0, Category.PLAYER);
      }

      public void onDisable() {
            super.onDisable();
            mc.rightClickDelayTimer = 6;
      }

      @EventTarget
      public void update(EventUpdate e) {
            mc.rightClickDelayTimer = 0;
      }

      public void onEnable() {
            super.onEnable();
      }
}
