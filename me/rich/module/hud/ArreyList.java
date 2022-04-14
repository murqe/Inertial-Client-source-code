package me.rich.module.hud;

import de.Hero.settings.Setting;
import java.util.ArrayList;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventRender2D;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;

public class ArreyList extends Feature {
      public static Setting backGround;
      public static Setting border;
      public static Setting rectRight;
      public static Setting red;
      public static Setting green;
      public static Setting blue;
      public static Setting red1;
      public static Setting green1;
      public static Setting blue1;
      public static Setting red2;
      public static Setting green2;
      public static Setting blue2;
      public static Setting time;

      public ArreyList() {
            super("ArrayList", 0, Category.HUD);
            ArrayList color = new ArrayList();
            color.add("Custom");
            color.add("Rainbow");
            color.add("GreenWhite");
            color.add("Pulse");
            color.add("Astolfo");
            color.add("YellAstolfo");
            color.add("Red-Blue");
            color.add("Grape");
            color.add("None");
            Main.settingsManager.rSetting(new Setting("ArrayList Color", this, "Astolfo", color));
            Main.settingsManager.rSetting(backGround = new Setting("Background", this, true));
            Main.settingsManager.rSetting(border = new Setting("Border", this, true));
            Main.settingsManager.rSetting(rectRight = new Setting("RectRight", this, true));
            Main.settingsManager.rSetting(new Setting("BackgroundBright", this, 35.0D, 1.0D, 255.0D, false));
            Main.settingsManager.rSetting(red1 = new Setting("Custom One Red", this, 255.0D, 0.0D, 255.0D, false));
            Main.settingsManager.rSetting(green1 = new Setting("Custom One Green", this, 255.0D, 0.0D, 255.0D, false));
            Main.settingsManager.rSetting(blue1 = new Setting("Custom One Blue", this, 255.0D, 0.0D, 255.0D, false));
            Main.settingsManager.rSetting(red2 = new Setting("Custom Two Red", this, 255.0D, 0.0D, 255.0D, false));
            Main.settingsManager.rSetting(green2 = new Setting("Custom Two Green", this, 255.0D, 0.0D, 255.0D, false));
            Main.settingsManager.rSetting(blue2 = new Setting("Custom Two Blue", this, 255.0D, 0.0D, 255.0D, false));
            Main.settingsManager.rSetting(time = new Setting("Custom Color Time", this, 10.0D, 1.0D, 100.0D, false));
      }

      @EventTarget
      public void onUpdate(EventUpdate event) {
            String mode = Main.settingsManager.getSettingByName("ArrayList Color").getValString();
            this.setSuffix(mode);
      }

      @EventTarget
      public void onRender2D(EventRender2D e) {
            HUD hud = new HUD();
            hud.renderArrayList(e.getResolution());
      }
}
