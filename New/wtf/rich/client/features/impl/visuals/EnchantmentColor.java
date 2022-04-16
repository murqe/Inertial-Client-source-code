package wtf.rich.client.features.impl.visuals;

import java.awt.Color;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.ColorSetting;
import wtf.rich.client.ui.settings.impl.ListSetting;

public class EnchantmentColor extends Feature {
     public static ListSetting colorMode;
     public static ColorSetting customColor;

     public EnchantmentColor() {
          super("EnchantmentColor", "Изменяет цвет зачарований", 0, Category.VISUALS);
          colorMode = new ListSetting("Crumbs Color", "Rainbow", () -> {
               return true;
          }, new String[]{"Rainbow", "Client", "Custom"});
          customColor = new ColorSetting("Custom Enchantment", (new Color(16777215)).getRGB(), () -> {
               return colorMode.currentMode.equals("Custom");
          });
          this.addSettings(new Setting[]{colorMode, customColor});
     }
}
