package wtf.rich.client.features.impl.visuals;

import java.awt.Color;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.ColorSetting;
import wtf.rich.client.ui.settings.impl.ListSetting;

public class Chams extends Feature {
     public static ColorSetting colorChams;
     public static BooleanSetting clientColor;
     public static ListSetting chamsMode;

     public Chams() {
          super("Chams", "Подсвечивает игроков", 0, Category.VISUALS);
          chamsMode = new ListSetting("Chams Mode", "Fill", () -> {
               return true;
          }, new String[]{"Fill", "Walls"});
          clientColor = new BooleanSetting("Client Colored", false, () -> {
               return !chamsMode.currentMode.equals("Walls");
          });
          colorChams = new ColorSetting("Chams Color", (new Color(16777215)).getRGB(), () -> {
               return !chamsMode.currentMode.equals("Walls") && !clientColor.getBoolValue();
          });
          this.addSettings(new Setting[]{chamsMode, colorChams, clientColor});
     }
}
