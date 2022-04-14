package me.rich.module.render;

import java.awt.Color;
import me.rich.event.EventTarget;
import me.rich.event.events.EventRenderModel;
import me.rich.module.Category;
import me.rich.module.Feature;

public class SkyColor extends Feature {
      public SkyColor() {
            super("SkyColor", 0, Category.RENDER);
      }

      @EventTarget
      public void onFogColorRender(EventRenderModel.EventColorFov event) {
            double rainbowState = Math.ceil((double)(System.currentTimeMillis() + 300L + 300L)) / 15.0D;
            Color color = Color.getHSBColor((float)((rainbowState %= 360.0D) / 360.0D), 0.4F, 1.0F);
            event.setRed((float)color.getRed() / 255.0F);
            event.setGreen((float)color.getGreen() / 255.0F);
            event.setBlue((float)color.getBlue() / 255.0F);
      }
}
