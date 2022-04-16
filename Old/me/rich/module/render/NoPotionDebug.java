package me.rich.module.render;

import me.rich.event.EventTarget;
import me.rich.event.events.Event2D;
import me.rich.module.Category;
import me.rich.module.Feature;

public class NoPotionDebug extends Feature {
      public NoPotionDebug() {
            super("NoPotionDebug", 0, Category.RENDER);
      }

      @EventTarget
      public void onRender2D(Event2D event) {
      }
}
