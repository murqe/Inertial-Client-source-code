package me.rich.module.render;

import de.Hero.settings.Setting;
import me.rich.Main;
import me.rich.module.Category;
import me.rich.module.Feature;

public class NoRender extends Feature {
      public NoRender() {
            super("NoRender", 0, Category.RENDER);
            Main.settingsManager.rSetting(new Setting("HurtCam", this, true));
            Main.settingsManager.rSetting(new Setting("CameraClip", this, true));
            Main.settingsManager.rSetting(new Setting("AntiTotemAnimation", this, true));
            Main.settingsManager.rSetting(new Setting("NoPotionDebug", this, true));
            Main.settingsManager.rSetting(new Setting("NoExpBar", this, true));
            Main.settingsManager.rSetting(new Setting("NoPumpkinOverlay", this, true));
            Main.settingsManager.rSetting(new Setting("NoBossBar", this, true));
            Main.settingsManager.rSetting(new Setting("NoArrowInPlayer", this, true));
            Main.settingsManager.rSetting(new Setting("NoArmor", this, true));
            Main.settingsManager.rSetting(new Setting("NoFireOverlay", this, true));
            Main.settingsManager.rSetting(new Setting("NoScoreBoard", this, true));
            Main.settingsManager.rSetting(new Setting("PosYplus", this, 1.0D, 1.0D, 252.0D, false));
      }

      public void onEnable() {
            super.onEnable();
      }

      public void onDisable() {
            super.onDisable();
      }
}
