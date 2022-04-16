package wtf.rich.client.ui.settings.impl;

import java.util.function.Supplier;
import wtf.rich.client.ui.settings.Setting;

public class StringSetting extends Setting {
     public String defaultText;
     public String currentText;

     public StringSetting(String name, String defaultText, String currentText, Supplier visible) {
          this.name = name;
          this.defaultText = defaultText;
          this.currentText = currentText;
          this.setVisible(visible);
     }

     public String getDefaultText() {
          return this.defaultText;
     }

     public void setDefaultText(String defaultText) {
          this.defaultText = defaultText;
     }

     public String getCurrentText() {
          return this.currentText;
     }

     public void setCurrentText(String currentText) {
          this.currentText = currentText;
     }
}
