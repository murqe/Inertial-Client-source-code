package wtf.rich.client.features.impl.combat;

import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.api.utils.math.MathematicHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class Reach extends Feature {
     public static NumberSetting reachValue;

     public Reach() {
          super("Reach", "Увеличивает дистанцию удара", 0, Category.COMBAT);
          reachValue = new NumberSetting("Expand", 3.2F, 3.0F, 5.0F, 0.1F, () -> {
               return true;
          });
          this.addSettings(new Setting[]{reachValue});
     }

     @EventTarget
     public void onUpdate(EventUpdate event) {
          this.setSuffix("" + MathematicHelper.round(reachValue.getNumberValue(), 1));
     }
}
