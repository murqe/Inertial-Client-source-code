package wtf.rich.client.features.impl.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.api.utils.movement.MovementHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;

public class LongJump extends Feature {
     public LongJump() {
          super("LongJump", "Позволяет прыгать на большую длинну", 0, Category.MOVEMENT);
     }

     @EventTarget
     public void onUpdate(EventUpdate eventUpdate) {
          if (mc.player.hurtTime > 0) {
               MovementHelper.strafe(MovementHelper.getSpeed());
               EntityPlayerSP var10000 = mc.player;
               var10000.motionY += 0.41D;
               mc.player.speedInAir = 0.8057F;
          } else {
               mc.player.speedInAir = 0.02F;
          }

     }

     public void onDisable() {
          mc.player.speedInAir = 0.02F;
          super.onDisable();
     }
}
