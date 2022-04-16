package wtf.rich.client.features.impl.visuals;

import java.awt.Color;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.ColorSetting;

public class NightMode extends Feature {
     public static ColorSetting worldColor;

     public NightMode() {
          super("NightMode", "Меняет цвет мира", 0, Category.VISUALS);
          this.addSettings(new Setting[]{worldColor});
     }

     static {
          worldColor = new ColorSetting("World Color", Color.RED.getRGB(), () -> {
               return true;
          });
     }
}
