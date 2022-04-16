package wtf.rich.client.features.impl.display;

import java.awt.Color;
import wtf.rich.Main;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.ColorSetting;
import wtf.rich.client.ui.settings.impl.ListSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class ClickGUI extends Feature {
     public static BooleanSetting background;
     public static ListSetting backGroundMode;
     public static BooleanSetting particle;
     public static BooleanSetting blur = new BooleanSetting("Blur", true, () -> {
          return true;
     });
     public static ListSetting clickGuiColor = new ListSetting("ClickGui Color", "Astolfo", () -> {
          return true;
     }, new String[]{"Astolfo", "Rainbow", "Static", "Color Two", "Client", "Fade"});
     public static ListSetting guiImage = new ListSetting("Image setting", "Taksa", () -> {
          return true;
     }, new String[]{"Taksa", "Anime"});
     public static ColorSetting color;
     public static BooleanSetting anime;
     public static ColorSetting colorTwo;
     public static NumberSetting speed = new NumberSetting("Speed", 35.0F, 10.0F, 100.0F, 1.0F, () -> {
          return true;
     });

     public ClickGUI() {
          super("ClickGUI", "Cheat menu.", 54, Category.DISPLAY);
          this.setKey(54);
          color = new ColorSetting("Color One", (new Color(255, 255, 255, 120)).getRGB(), () -> {
               return clickGuiColor.currentMode.equals("Fade") || clickGuiColor.currentMode.equals("Color Two") || clickGuiColor.currentMode.equals("Static");
          });
          colorTwo = new ColorSetting("Color Two", (new Color(154, 154, 154, 120)).getRGB(), () -> {
               return clickGuiColor.currentMode.equals("Color Two");
          });
          background = new BooleanSetting("Background", false, () -> {
               return true;
          });
          backGroundMode = new ListSetting("Background Mode", "Bottom", () -> {
               return background.getBoolValue();
          }, new String[]{"Bottom", "Top", "Everywhere"});
          anime = new BooleanSetting("Anime", false, () -> {
               return true;
          });
          this.addSettings(new Setting[]{clickGuiColor, guiImage, color, colorTwo, speed, background, backGroundMode, blur, anime});
     }

     public void onEnable() {
          mc.displayGuiScreen(Main.instance.clickGui);
          Main.instance.featureDirector.getFeatureByClass(ClickGUI.class).setEnabled(false);
          super.onEnable();
     }
}
