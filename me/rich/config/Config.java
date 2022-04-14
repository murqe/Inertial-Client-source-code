package me.rich.config;

import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import me.rich.Main;
import me.rich.module.Feature;
import me.rich.module.FeatureDirector;
import net.minecraft.client.Minecraft;

public class Config {
      public File dir;
      public File configs;
      public File dataFile;

      public Config() {
            this.dir = new File(Minecraft.getMinecraft().mcDataDir, "DeathClient");
            if (!this.dir.exists()) {
                  this.dir.mkdir();
            }

            this.dataFile = new File(this.dir, "config.txt");
            if (!this.dataFile.exists()) {
                  try {
                        this.dataFile.createNewFile();
                  } catch (IOException var2) {
                        var2.printStackTrace();
                  }
            }

            this.load();
      }

      public void save() {
            ArrayList toSave = new ArrayList();
            Iterator var2 = FeatureDirector.getModules().iterator();

            while(var2.hasNext()) {
                  Feature mod = (Feature)var2.next();
                  toSave.add("Module:" + mod.getName() + ":" + mod.isToggled() + ":" + mod.getKey());
            }

            SettingsManager var10000 = Main.settingsManager;
            Iterator var8 = SettingsManager.getSettings().iterator();

            while(var8.hasNext()) {
                  Setting set = (Setting)var8.next();
                  if (set.isCheck()) {
                        toSave.add("Setting:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValBoolean());
                  }

                  if (set.isCombo()) {
                        toSave.add("Setting:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValString());
                  }

                  if (set.isSlider()) {
                        toSave.add("Setting:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValFloat());
                  }
            }

            try {
                  PrintWriter pw = new PrintWriter(this.dataFile);
                  Iterator var10 = toSave.iterator();

                  while(var10.hasNext()) {
                        String str = (String)var10.next();
                        pw.println(str);
                  }

                  pw.close();
            } catch (FileNotFoundException var6) {
                  var6.printStackTrace();
            }

      }

      public void load() {
            ArrayList lines = new ArrayList();

            String s;
            try {
                  BufferedReader reader = new BufferedReader(new FileReader(this.dataFile));

                  for(s = reader.readLine(); s != null; s = reader.readLine()) {
                        lines.add(s);
                  }

                  reader.close();
            } catch (Exception var7) {
                  var7.printStackTrace();
            }

            Iterator var8 = lines.iterator();

            while(var8.hasNext()) {
                  s = (String)var8.next();
                  String[] args = s.split(":");
                  if (s.toLowerCase().startsWith("module:")) {
                        Feature m = Main.moduleManager.getModuleByName(args[1]);
                        if (m != null) {
                              m.setEnabled(Boolean.parseBoolean(args[2]));
                              m.setKey(Integer.parseInt(args[3]));
                        }
                  } else {
                        Setting set;
                        if (s.toLowerCase().startsWith("setting:") && Main.moduleManager.getModuleByName(args[2]) != null && (set = Main.settingsManager.getSettingByName(args[1])) != null) {
                              if (set.isCheck()) {
                                    set.setValBoolean(Boolean.parseBoolean(args[3]));
                              }

                              if (set.isCombo()) {
                                    set.setValString(args[3]);
                              }

                              if (set.isSlider()) {
                                    set.setValDouble(Double.parseDouble(args[3]));
                                    set.setValFloat(Float.parseFloat(args[3]));
                              }
                        }
                  }
            }

      }
}
