package wtf.rich.client.features.impl.visuals;

import java.awt.Color;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.ColorSetting;

public class Weather extends Feature {
     public static ColorSetting weatherColor;

     public Weather() {
          super("Weather", "Добавляет частички снега в мир", 0, Category.VISUALS);
          weatherColor = new ColorSetting("Weather", (new Color(16777215)).getRGB(), () -> {
               return true;
          });
          this.addSettings(new Setting[]{weatherColor});
     }
}
