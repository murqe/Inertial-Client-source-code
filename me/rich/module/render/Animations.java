package me.rich.module.render;

import de.Hero.settings.Setting;
import me.rich.Main;
import me.rich.module.Category;
import me.rich.module.Feature;

public class Animations extends Feature {
      public void setup() {
            Main.settingsManager.rSetting(new Setting("ItemDistance", this, 1.0D, 1.0D, 2.5D, false));
            Main.settingsManager.rSetting(new Setting("ItemSize", this, 1.0D, 0.10000000149011612D, 5.0D, false));
            Main.settingsManager.rSetting(new Setting("RotateItem", this, false));
      }

      public Animations() {
            super("Animations", 0, Category.RENDER);
      }
}
