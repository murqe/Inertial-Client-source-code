package me.rich.module.player;

import me.rich.event.EventTarget;
import me.rich.event.events.EventPreMotionUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;

public class SpeedMine extends Feature {
      public SpeedMine() {
            super("SpeedMine", 0, Category.PLAYER);
      }

      @EventTarget
      public void onUpdate(EventPreMotionUpdate event) {
            if (mc.playerController.curBlockDamageMP >= 0.7F) {
                  mc.playerController.curBlockDamageMP = 1.0F;
            }

      }
}
