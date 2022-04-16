package wtf.rich.client.ui.settings.impl;

import java.util.function.Supplier;
import wtf.rich.client.ui.settings.Setting;

public class ColorSetting extends Setting {
     private int color;

     public ColorSetting(String name, int color, Supplier visible) {
          this.name = name;
          this.color = color;
          this.setVisible(visible);
     }

     public int getColorValue() {
          return this.color;
     }

     public void setColorValue(int color) {
          this.color = color;
     }
}
