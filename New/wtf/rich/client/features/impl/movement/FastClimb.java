package wtf.rich.client.features.impl.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventPreMotionUpdate;
import wtf.rich.api.utils.movement.MovementHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.ListSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class FastClimb extends Feature {
     public static ListSetting ladderMode;
     public static NumberSetting ladderSpeed;

     public FastClimb() {
          super("FastClimb", "Позволяет быстро забираться по лестницам и лианам", 0, Category.MOVEMENT);
          ladderMode = new ListSetting("FastClimb Mode", "Matrix", () -> {
               return true;
          }, new String[]{"Matrix", "Vanilla"});
          ladderSpeed = new NumberSetting("Ladder Speed", 0.5F, 0.1F, 2.0F, 0.1F, () -> {
               return ladderMode.currentMode.equals("Vanilla");
          });
          this.addSettings(new Setting[]{ladderMode});
     }

     @EventTarget
     public void onPreMotion(EventPreMotionUpdate event) {
          this.setSuffix(ladderMode.getCurrentMode());
          if (mc.player != null && mc.world != null) {
               String var2 = ladderMode.getOptions();
               byte var3 = -1;
               switch(var2.hashCode()) {
               case -1997372447:
                    if (var2.equals("Matrix")) {
                         var3 = 0;
                    }
                    break;
               case 1897755483:
                    if (var2.equals("Vanilla")) {
                         var3 = 1;
                    }
               }

               EntityPlayerSP var10000;
               switch(var3) {
               case 0:
                    if (mc.player.isOnLadder() && mc.player.isCollidedHorizontally && MovementHelper.isMoving()) {
                         var10000 = mc.player;
                         var10000.motionY += 0.31200000643730164D;
                         event.setGround(true);
                    }
                    break;
               case 1:
                    if (mc.player.isOnLadder() && mc.player.isCollidedHorizontally && MovementHelper.isMoving()) {
                         var10000 = mc.player;
                         var10000.motionY += (double)ladderSpeed.getNumberValue();
                    }
               }

          }
     }
}
