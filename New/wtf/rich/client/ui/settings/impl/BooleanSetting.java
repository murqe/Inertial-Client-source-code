package wtf.rich.client.ui.settings.impl;

import java.util.function.Supplier;
import wtf.rich.client.ui.settings.Setting;

public class BooleanSetting extends Setting {
     private boolean state;
     private String desc;

     public BooleanSetting(String name, String desc, boolean state, Supplier visible) {
          this.name = name;
          this.desc = desc;
          this.state = state;
          this.setVisible(visible);
     }

     public BooleanSetting(String name, boolean state, Supplier visible) {
          this.name = name;
          this.state = state;
          this.setVisible(visible);
     }

     public String getDesc() {
          return this.desc;
     }

     public void setDesc(String desc) {
          this.desc = desc;
     }

     public boolean getBoolValue() {
          return this.state;
     }

     public void setBoolValue(boolean state) {
          this.state = state;
     }
}
