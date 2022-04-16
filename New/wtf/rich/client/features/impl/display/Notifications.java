package wtf.rich.client.features.impl.display;

import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.ListSetting;

public class Notifications extends Feature {
     public static ListSetting notifMode;
     public static BooleanSetting state;

     public Notifications() {
          super("Notifications", "Показывает необходимую информацию о модулях", 0, Category.DISPLAY);
          state = new BooleanSetting("Module State", true, () -> {
               return true;
          });
          notifMode = new ListSetting("Notification Mode", "Rect", () -> {
               return true;
          }, new String[]{"Rect", "Chat"});
          this.addSettings(new Setting[]{notifMode, state});
     }
}
