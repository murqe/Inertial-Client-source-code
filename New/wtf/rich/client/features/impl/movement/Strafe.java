package wtf.rich.client.features.impl.movement;

import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.api.utils.movement.MovementHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.ListSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class Strafe extends Feature {
     private final ListSetting strafeMode = new ListSetting("Strafe Mode", "Matrix", () -> {
          return true;
     }, new String[]{"Vanilla", "Matrix"});
     private final NumberSetting strafeSpeed = new NumberSetting("Strafe Speed", 1.0F, 0.1F, 3.0F, 0.1F, () -> {
          return this.strafeMode.currentMode.equalsIgnoreCase("Vanilla");
     });

     public Strafe() {
          super("Strafe", "Позволяет вам стрейфить", 0, Category.MOVEMENT);
          this.addSettings(new Setting[]{this.strafeMode, this.strafeSpeed});
     }

     @EventTarget
     public void onUpdate(EventUpdate eventUpdate) {
          String mode = this.strafeMode.getOptions();
          if (mode.equalsIgnoreCase("Matrix")) {
               if (!mc.gameSettings.keyBindForward.pressed) {
                    return;
               }

               if (mc.player.motionY == 0.33319999363422365D) {
                    MovementHelper.strafe(MovementHelper.getSpeed());
               }
          } else if (mode.equalsIgnoreCase("Vanilla")) {
               MovementHelper.strafe(this.strafeSpeed.getNumberValue());
          }

     }
}
