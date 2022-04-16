package wtf.rich.client.features.impl.visuals;

import java.awt.Color;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventRender2D;
import wtf.rich.api.utils.movement.MovementHelper;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.ColorSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class Crosshair extends Feature {
     public static ColorSetting colorGlobal;
     public BooleanSetting dynamic = new BooleanSetting("Dynamic", false, () -> {
          return true;
     });
     public BooleanSetting tmode;
     public NumberSetting width;
     public NumberSetting gap;
     public NumberSetting length;
     public NumberSetting dynamicGap;

     public Crosshair() {
          super("Crosshair", "Изменяет ваш прицел", 0, Category.VISUALS);
          BooleanSetting var10008 = this.dynamic;
          var10008.getClass();
          this.dynamicGap = new NumberSetting("Dynamic Gap", 3.0F, 1.0F, 20.0F, 1.0F, var10008::getBoolValue);
          this.gap = new NumberSetting("Gap", 2.0F, 0.0F, 10.0F, 0.1F, () -> {
               return true;
          });
          this.tmode = new BooleanSetting("T-Mode", false, () -> {
               return true;
          });
          colorGlobal = new ColorSetting("Crosshair Color", (new Color(16777215)).getRGB(), () -> {
               return true;
          });
          this.width = new NumberSetting("Width", 1.0F, 0.0F, 8.0F, 1.0F, () -> {
               return true;
          });
          this.length = new NumberSetting("Length", 3.0F, 0.5F, 30.0F, 1.0F, () -> {
               return true;
          });
          this.addSettings(new Setting[]{this.tmode, this.dynamic, this.dynamicGap, this.gap, colorGlobal, this.width, this.length});
     }

     @EventTarget
     public void onRender2D(EventRender2D event) {
          int crosshairColor = colorGlobal.getColorValue();
          float screenWidth = (float)event.getResolution().getScaledWidth();
          float screenHeight = (float)event.getResolution().getScaledHeight();
          float width = screenWidth / 2.0F;
          float height = screenHeight / 2.0F;
          boolean dyn = this.dynamic.getBoolValue();
          float dyngap = this.dynamicGap.getNumberValue();
          float wid = this.width.getNumberValue();
          float len = this.length.getNumberValue();
          boolean isMoving = dyn && MovementHelper.isMoving();
          float gaps = isMoving ? dyngap : this.gap.getNumberValue();
          DrawHelper.drawRect((double)(width - gaps - len), (double)(height - wid / 2.0F), (double)(width - gaps), (double)(height + wid / 2.0F), crosshairColor);
          DrawHelper.drawRect((double)(width + gaps), (double)(height - wid / 2.0F), (double)(width + gaps + len), (double)(height + wid / 2.0F), crosshairColor);
          if (!this.tmode.getBoolValue()) {
               DrawHelper.drawRect((double)(width - wid / 2.0F), (double)(height - gaps - len), (double)(width + wid / 2.0F), (double)(height - gaps), crosshairColor);
          }

          DrawHelper.drawRect((double)(width - wid / 2.0F), (double)(height + gaps), (double)(width + wid / 2.0F), (double)(height + gaps + len), crosshairColor);
     }
}
