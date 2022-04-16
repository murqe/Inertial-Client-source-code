package wtf.rich.client.features.impl.visuals;

import java.awt.Color;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventFogColor;
import wtf.rich.api.utils.render.ClientHelper;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.ColorSetting;
import wtf.rich.client.ui.settings.impl.ListSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class FogColor extends Feature {
     public static NumberSetting distance;
     public ListSetting colorMode = new ListSetting("Fog Color", "Rainbow", () -> {
          return true;
     }, new String[]{"Rainbow", "Client", "Custom"});
     public ColorSetting customColor;

     public FogColor() {
          super("FogColor", "Меняет цвет тумана", 0, Category.VISUALS);
          distance = new NumberSetting("Distance", 0.1F, 0.001F, 2.0F, 0.01F, () -> {
               return true;
          });
          this.customColor = new ColorSetting("Custom Fog", (new Color(11219403)).getRGB(), () -> {
               return this.colorMode.currentMode.equals("Custom");
          });
          this.addSettings(new Setting[]{this.colorMode, distance, this.customColor});
     }

     @EventTarget
     public void onFogColor(EventFogColor event) {
          String colorModeValue = this.colorMode.getOptions();
          Color customColorValue;
          if (colorModeValue.equalsIgnoreCase("Rainbow")) {
               customColorValue = DrawHelper.rainbow(1, 1.0F, 1.0F);
               event.setRed((float)customColorValue.getRed());
               event.setGreen((float)customColorValue.getGreen());
               event.setBlue((float)customColorValue.getBlue());
          } else if (colorModeValue.equalsIgnoreCase("Client")) {
               customColorValue = ClientHelper.getClientColor();
               event.setRed((float)customColorValue.getRed());
               event.setGreen((float)customColorValue.getGreen());
               event.setBlue((float)customColorValue.getBlue());
          } else if (colorModeValue.equalsIgnoreCase("Custom")) {
               customColorValue = new Color(this.customColor.getColorValue());
               event.setRed((float)customColorValue.getRed());
               event.setGreen((float)customColorValue.getGreen());
               event.setBlue((float)customColorValue.getBlue());
          }

     }
}
