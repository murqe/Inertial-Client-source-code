package wtf.rich.client.features.impl.movement;

import net.minecraft.init.MobEffects;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.api.utils.movement.MovementHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class WaterSpeed extends Feature {
     public static NumberSetting speed;
     private final BooleanSetting speedCheck;

     public WaterSpeed() {
          super("WaterSpeed", "Делает вас быстрее в воде", 0, Category.MOVEMENT);
          speed = new NumberSetting("Speed Amount", 1.0F, 0.1F, 4.0F, 0.01F, () -> {
               return true;
          });
          this.speedCheck = new BooleanSetting("Speed Potion Check", false, () -> {
               return true;
          });
          this.addSettings(new Setting[]{this.speedCheck, speed});
     }

     @EventTarget
     public void onUpdate(EventUpdate event) {
          if (mc.player.isPotionActive(MobEffects.SPEED) || !this.speedCheck.getBoolValue()) {
               if (mc.player.isInLiquid()) {
                    MovementHelper.setSpeed((double)speed.getNumberValue());
               }

          }
     }
}
