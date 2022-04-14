package de.Hero.settings;

import java.util.ArrayList;
import java.util.Iterator;
import me.rich.module.Feature;

public class SettingsManager {
      private static ArrayList settings;

      public SettingsManager() {
            settings = new ArrayList();
      }

      public void rSetting(Setting in) {
            settings.add(in);
      }

      public static ArrayList getSettings() {
            return settings;
      }

      public ArrayList getSettingsByMod(Feature mod) {
            ArrayList out = new ArrayList();
            Iterator var3 = getSettings().iterator();

            while(var3.hasNext()) {
                  Setting s = (Setting)var3.next();
                  if (s.getParentMod().equals(mod)) {
                        out.add(s);
                  }
            }

            if (out.isEmpty()) {
                  return null;
            } else {
                  return out;
            }
      }

      public Setting getSettingByName(Feature mod, String name) {
            Iterator var3 = getSettings().iterator();

            Setting set;
            do {
                  if (!var3.hasNext()) {
                        System.err.println("[] Error Setting NOT found: '" + name + "'!");
                        return null;
                  }

                  set = (Setting)var3.next();
            } while(!set.getName().equalsIgnoreCase(name) || set.getParentMod() != mod);

            return set;
      }

      public boolean hasSettings(Feature mod) {
            ArrayList out = new ArrayList();
            Iterator var3 = getSettings().iterator();

            while(var3.hasNext()) {
                  Setting s = (Setting)var3.next();
                  if (s.getParentMod().equals(mod)) {
                        out.add(s);
                  }
            }

            if (out.isEmpty()) {
                  return false;
            } else {
                  return true;
            }
      }

      public Setting getSettingByName(String name) {
            Iterator var2 = getSettings().iterator();

            Setting set;
            do {
                  if (!var2.hasNext()) {
                        return null;
                  }

                  set = (Setting)var2.next();
            } while(!set.getName().equalsIgnoreCase(name));

            return set;
      }
}
