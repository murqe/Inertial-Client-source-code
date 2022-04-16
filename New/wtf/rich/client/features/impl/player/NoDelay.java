package wtf.rich.client.features.impl.player;

import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;

public class NoDelay extends Feature {
     public BooleanSetting rightClickDelay = new BooleanSetting("NoRightClickDelay", true, () -> {
          return true;
     });
     public BooleanSetting jumpDelay = new BooleanSetting("NoJumpDelay", true, () -> {
          return true;
     });

     public NoDelay() {
          super("NoDelay", "Убирает задержку", 0, Category.PLAYER);
          this.addSettings(new Setting[]{this.rightClickDelay, this.jumpDelay});
     }

     public void onDisable() {
          mc.rightClickDelayTimer = 4;
          super.onDisable();
     }

     @EventTarget
     public void onUpdate(EventUpdate event) {
          if (this.isToggled()) {
               if (this.rightClickDelay.getBoolValue()) {
                    mc.rightClickDelayTimer = 0;
               }

               if (this.jumpDelay.getBoolValue()) {
                    mc.player.jumpTicks = 0;
               }

          }
     }
}
