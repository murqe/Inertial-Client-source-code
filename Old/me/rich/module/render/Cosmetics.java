package me.rich.module.render;

import de.Hero.settings.Setting;
import java.util.ArrayList;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;

public class Cosmetics extends Feature {
      private final String CAPEMODE = "Cape Mode";
      private final String WINGMODE = "Wing Mode";
      private final String CAPE = "Cape";
      private final String WING = "Wing";
      private final String SCALE = "Scale";
      public static boolean cape;
      public static boolean wing;
      public static double scale;
      public static String capepng;
      public static String wingpng;

      public Cosmetics() {
            super("Cosmetic", 0, Category.RENDER);
            ArrayList wing = new ArrayList();
            ArrayList cape = new ArrayList();
            cape.add("Pink");
            cape.add("Black");
            wing.add("White");
            wing.add("Gray");
            Main var10000 = Main.instance;
            Main.settingsManager.rSetting(new Setting("Cape Mode", this, "Pink", cape));
            var10000 = Main.instance;
            Main.settingsManager.rSetting(new Setting("Wing Mode", this, "Gray", wing));
            Main.settingsManager.rSetting(new Setting("Wing", this, false));
            Main.settingsManager.rSetting(new Setting("Cape", this, false));
            Main.settingsManager.rSetting(new Setting("Scale", this, 0.3D, 0.4D, 2.0D, false));
      }

      @EventTarget
      public void onUpdate(EventUpdate event) {
            cape = Main.settingsManager.getSettingByName("Cape").getValBoolean();
            wing = Main.settingsManager.getSettingByName("Wing").getValBoolean();
            scale = (double)Main.settingsManager.getSettingByName("Scale").getValFloat();
            capepng = Main.settingsManager.getSettingByName("Cape Mode").getValString();
            wingpng = Main.settingsManager.getSettingByName("Wing Mode").getValString();
            this.setModuleName("Cosmetics [" + Main.settingsManager.getSettingByName(Main.moduleManager.getModule(Cosmetics.class), "Scale").getValFloat() + "]");
      }
}
