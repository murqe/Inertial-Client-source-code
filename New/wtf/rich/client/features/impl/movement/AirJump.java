package wtf.rich.client.features.impl.movement;

import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;

public class AirJump extends Feature {
     public AirJump() {
          super("AirJump", "Позволяет прыгать по воздуху", 0, Category.MOVEMENT);
     }

     @EventTarget
     public void sdd(EventUpdate eventUpdate) {
          if (mc.gameSettings.keyBindJump.isKeyDown()) {
               mc.player.jump();
          }

     }
}
