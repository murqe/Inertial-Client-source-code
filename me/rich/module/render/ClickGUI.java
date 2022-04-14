package me.rich.module.render;

import de.Hero.settings.Setting;
import java.util.ArrayList;
import me.rich.Main;
import me.rich.module.Category;
import me.rich.module.Feature;

public class ClickGUI extends Feature {
      public ClickGUI() {
            super("ClickGUI", 54, Category.HUD);
            ArrayList Anime = new ArrayList();
            Anime.add("Anime");
            Anime.add("Anime2");
            Anime.add("Anime3");
            Main.settingsManager.rSetting(new Setting("Blur", this, true));
            Main.settingsManager.rSetting(new Setting("Anime Mode", this, "Anime", Anime));
      }

      public void onEnable() {
            mc.displayGuiScreen(Main.clickGui1);
            this.toggle();
            super.onEnable();
      }
}
