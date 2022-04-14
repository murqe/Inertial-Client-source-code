package me.rich.module.render;

import de.Hero.settings.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;

public class FullBright extends Feature {
      private float oldBrightness;

      public FullBright() {
            super("FullBright", 0, Category.RENDER);
            Main.settingsManager.rSetting(new Setting("Brightness", this, 0.0D, -10.0D, 10.0D, true));
      }

      @EventTarget
      public void onUpdate(EventUpdate event) {
            mc.gameSettings.gammaSetting = Main.settingsManager.getSettingByName("Brightness").getValFloat();
      }

      public void onEnable() {
            super.onEnable();
            this.oldBrightness = mc.gameSettings.gammaSetting;
      }

      public void onDisable() {
            mc.gameSettings.gammaSetting = this.oldBrightness;
            super.onDisable();
      }
}
