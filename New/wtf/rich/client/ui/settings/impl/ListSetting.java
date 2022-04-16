package wtf.rich.client.ui.settings.impl;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import wtf.rich.client.ui.settings.Setting;

public class ListSetting extends Setting {
     public final List modes;
     public String currentMode;
     public int index;

     public ListSetting(String name, String currentMode, Supplier visible, String... options) {
          this.name = name;
          this.modes = Arrays.asList(options);
          this.index = this.modes.indexOf(currentMode);
          this.currentMode = (String)this.modes.get(this.index);
          this.setVisible(visible);
          this.addSettings(new Setting[]{this});
     }

     public String getCurrentMode() {
          return this.currentMode;
     }

     public void setListMode(String selected) {
          this.currentMode = selected;
          this.index = this.modes.indexOf(selected);
     }

     public List getModes() {
          return this.modes;
     }

     public String getOptions() {
          return (String)this.modes.get(this.index);
     }
}
