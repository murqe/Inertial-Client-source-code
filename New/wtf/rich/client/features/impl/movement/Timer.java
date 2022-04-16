package wtf.rich.client.features.impl.movement;

import net.minecraft.util.text.TextFormatting;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class Timer extends Feature {
     public NumberSetting timer = new NumberSetting("Timer", 2.0F, 0.1F, 10.0F, 0.1F, () -> {
          return true;
     });

     public Timer() {
          super("Timer", "Увеличивает скорость игры", 0, Category.MOVEMENT);
          this.addSettings(new Setting[]{this.timer});
     }

     @EventTarget
     public void onUpdate(EventUpdate event) {
          if (this.isToggled()) {
               mc.timer.timerSpeed = this.timer.getNumberValue();
               this.setModuleName("Timer " + TextFormatting.GRAY + "[" + this.timer.getNumberValue() + "]");
          }

     }

     public void onDisable() {
          super.onDisable();
          mc.timer.timerSpeed = 1.0F;
     }

     public void onEnable() {
          super.onEnable();
     }
}
