package me.rich.module.render;

import de.Hero.settings.Setting;
import me.rich.Main;
import me.rich.module.Category;
import me.rich.module.Feature;

public class ScoreBoard extends Feature {
      public ScoreBoard() {
            super("Scoreboard", 0, Category.RENDER);
            Main.settingsManager.rSetting(new Setting("Position Y", this, 100.0D, 1.0D, 200.0D, true));
            Main.settingsManager.rSetting(new Setting("Remove", this, true));
            Main.settingsManager.rSetting(new Setting("Remove Points", this, true));
      }
}
