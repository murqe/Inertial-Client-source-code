package wtf.rich.client.features.impl.visuals;

import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class ScoreBoard extends Feature {
     public static BooleanSetting noScore;
     public NumberSetting y = new NumberSetting("PositionY", "Позиция скорборда по Y", 5.0F, 0.0F, 215.0F, 1.0F, () -> {
          return !noScore.getBoolValue();
     });

     public ScoreBoard() {
          super("Scoreboard", "Позволяет настроить скорборд на сервере", 0, Category.VISUALS);
          noScore = new BooleanSetting("No Scoreboard", false, () -> {
               return true;
          });
          this.addSettings(new Setting[]{noScore, this.y});
     }
}
