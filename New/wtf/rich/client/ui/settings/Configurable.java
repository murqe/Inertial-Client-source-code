package wtf.rich.client.ui.settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Configurable {
     private final ArrayList settingList = new ArrayList();

     public final void addSettings(Setting... options) {
          this.settingList.addAll(Arrays.asList(options));
     }

     public final List getSettings() {
          return this.settingList;
     }
}
