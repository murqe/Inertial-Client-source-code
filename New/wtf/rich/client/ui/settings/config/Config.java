package wtf.rich.client.ui.settings.config;

import com.google.gson.JsonObject;
import java.io.File;
import java.util.Iterator;
import wtf.rich.Main;
import wtf.rich.client.features.Feature;

public final class Config implements ConfigUpdater {
     private final String name;
     private final File file;

     public Config(String name) {
          this.name = name;
          this.file = new File(ConfigManager.configDirectory, name + ".json");
          if (!this.file.exists()) {
               try {
                    this.file.createNewFile();
               } catch (Exception var3) {
               }
          }

     }

     public File getFile() {
          return this.file;
     }

     public String getName() {
          return this.name;
     }

     public JsonObject save() {
          JsonObject jsonObject = new JsonObject();
          JsonObject modulesObject = new JsonObject();
          new JsonObject();
          Iterator var4 = Main.instance.featureDirector.getFeatureList().iterator();

          while(var4.hasNext()) {
               Feature module = (Feature)var4.next();
               modulesObject.add(module.getLabel(), module.save());
          }

          jsonObject.add("Features", modulesObject);
          return jsonObject;
     }

     public void load(JsonObject object) {
          if (object.has("Features")) {
               JsonObject modulesObject = object.getAsJsonObject("Features");
               Iterator var3 = Main.instance.featureDirector.getFeatureList().iterator();

               while(var3.hasNext()) {
                    Feature module = (Feature)var3.next();
                    module.setEnabled(false);
                    module.load(modulesObject.getAsJsonObject(module.getLabel()));
               }
          }

     }
}
