package wtf.rich.client.features.impl.display;

import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.ListSetting;

public class ClientFont extends Feature {
     public static ListSetting fontMode;
     public static BooleanSetting minecraftfont;

     public ClientFont() {
          super("ClientFont", "Меняет шрифт во всём клиенте", 0, Category.DISPLAY);
          fontMode = new ListSetting("FontList", "URWGeometric", () -> {
               return !minecraftfont.getBoolValue();
          }, new String[]{"URWGeometric", "Myseo", "SFUI", "Lato", "Roboto Regular"});
          minecraftfont = new BooleanSetting("Minecraft Font", false, () -> {
               return true;
          });
          this.addSettings(new Setting[]{fontMode, minecraftfont});
     }

     public void onEnable() {
          this.toggle();
          super.onEnable();
     }
}
