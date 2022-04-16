package wtf.rich.client.features.impl.player;

import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventPreMotionUpdate;
import wtf.rich.api.utils.movement.MovementHelper;
import wtf.rich.api.utils.world.TimerHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class Spider extends Feature {
     public static NumberSetting climbTicks;
     private final TimerHelper timerHelper = new TimerHelper();

     public Spider() {
          super("Spider", "Автоматически взберается на стены", 0, Category.PLAYER);
          climbTicks = new NumberSetting("Climb Ticks", 1.0F, 0.0F, 5.0F, 0.1F, () -> {
               return true;
          });
          this.addSettings(new Setting[]{climbTicks});
     }

     @EventTarget
     public void onPreMotion(EventPreMotionUpdate event) {
          this.setSuffix("" + climbTicks.getNumberValue());
          if (MovementHelper.isMoving() && mc.player.isCollidedHorizontally && this.timerHelper.hasReached((double)(climbTicks.getNumberValue() * 100.0F))) {
               event.setGround(true);
               mc.player.onGround = true;
               mc.player.isCollidedVertically = true;
               mc.player.isCollidedHorizontally = true;
               mc.player.isAirBorne = true;
               mc.player.jump();
               this.timerHelper.reset();
          }

     }
}
