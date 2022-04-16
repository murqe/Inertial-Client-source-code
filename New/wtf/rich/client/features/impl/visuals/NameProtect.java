package wtf.rich.client.features.impl.visuals;

import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;

public class NameProtect extends Feature {
     public static BooleanSetting otherNames;
     public static BooleanSetting skinSpoof;
     public static BooleanSetting tabSpoof;
     public static BooleanSetting scoreBoardSpoof;

     public NameProtect() {
          super("NameProtect", "Позволяет скрывать информацию о себе и других игроках на видео или стриме", 0, Category.PLAYER);
          otherNames = new BooleanSetting("Other Names", true, () -> {
               return true;
          });
          tabSpoof = new BooleanSetting("Tab Spoof", true, () -> {
               return true;
          });
          skinSpoof = new BooleanSetting("Skin Spoof", true, () -> {
               return true;
          });
          scoreBoardSpoof = new BooleanSetting("ScoreBoard Spoof", true, () -> {
               return true;
          });
          this.addSettings(new Setting[]{otherNames, tabSpoof, skinSpoof, scoreBoardSpoof});
     }
}
