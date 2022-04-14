package me.rich.module.movement;

import de.Hero.settings.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;

public class Timer extends Feature {
      public Timer() {
            super("Timer", 0, Category.MOVEMENT);
            Main var10000 = Main.instance;
            Main.settingsManager.rSetting(new Setting("Timer", this, 1.0D, 0.1D, 10.0D, false));
      }

      @EventTarget
      public void onUpdate(EventUpdate event) {
            if (this.isToggled()) {
                  Main var10001 = Main.instance;
                  mc.timer.timerSpeed = Main.settingsManager.getSettingByName("Timer").getValFloat();
                  StringBuilder var2 = (new StringBuilder()).append("");
                  Main var10002 = Main.instance;
                  this.setSuffix(var2.append(Main.settingsManager.getSettingByName("Timer").getValFloat()).toString());
            }

      }

      public void onDisable() {
            super.onDisable();
            mc.timer.timerSpeed = 1.0F;
      }
}
