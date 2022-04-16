package wtf.rich.client.features.impl.display;

import wtf.rich.Main;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.ListSetting;

public class MainMenu extends Feature {
     public static ListSetting mainMenu = new ListSetting("Menu image", "SkyDrag", () -> {
          return true;
     }, new String[]{"Sky", "Minecraft", "Ukraine", "Cocks", "Svaston", "SkyDrag", "Rainbow"});

     public MainMenu() {
          super("MainMenu", "Картинка в мейнменю", 0, Category.DISPLAY);
          this.addSettings(new Setting[]{mainMenu});
     }

     public void onEnable() {
          Main.instance.featureDirector.getFeatureByClass(MainMenu.class).setEnabled(false);
          super.onEnable();
     }
}
