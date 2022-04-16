package wtf.rich.client.features.impl.player;

import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;

public class NoPush extends Feature {
     public static BooleanSetting pushplayers = new BooleanSetting("Players", true, () -> {
          return true;
     });
     public static BooleanSetting pushblocks = new BooleanSetting("Blocks", true, () -> {
          return true;
     });
     public static BooleanSetting pushwater = new BooleanSetting("Water", true, () -> {
          return true;
     });

     public NoPush() {
          super("NoPush", "Не отталкивает вас от воды,блоков,игроков", 0, Category.PLAYER);
          this.addSettings(new Setting[]{pushblocks, pushplayers, pushwater});
     }
}
