package wtf.rich.client.features.impl.misc;

import java.util.Iterator;
import wtf.rich.Main;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;

public class Panic extends Feature {
     public Panic() {
          super("Panic", "Выключает все модули чита", 0, Category.MISC);
     }

     @EventTarget
     public void onUpdate(EventUpdate event) {
          Iterator var2 = Main.instance.featureDirector.getFeatureList().iterator();

          while(var2.hasNext()) {
               Feature feature = (Feature)var2.next();
               if (feature.isToggled()) {
                    feature.toggle();
               }
          }

     }
}
