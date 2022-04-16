package wtf.rich.client.ui.settings;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import wtf.rich.Main;
import wtf.rich.client.ui.settings.impls.FriendConfig;
import wtf.rich.client.ui.settings.impls.MacroConfig;

public class FileManager {
     public static File directory;
     public static ArrayList files;

     public FileManager() {
          files.add(new FriendConfig("FriendConfig", true));
          files.add(new MacroConfig("MacroConfig", true));
     }

     public void loadFiles() {
          Iterator var1 = files.iterator();

          while(var1.hasNext()) {
               FileManager.CustomFile file = (FileManager.CustomFile)var1.next();

               try {
                    if (file.loadOnStart()) {
                         file.loadFile();
                    }
               } catch (Exception var4) {
                    var4.printStackTrace();
               }
          }

     }

     public void saveFiles() {
          Iterator var1 = files.iterator();

          while(var1.hasNext()) {
               FileManager.CustomFile f = (FileManager.CustomFile)var1.next();

               try {
                    f.saveFile();
               } catch (Exception var4) {
               }
          }

     }

     public FileManager.CustomFile getFile(Class clazz) {
          Iterator customFileIterator = files.iterator();

          while(customFileIterator.hasNext()) {
               FileManager.CustomFile file = (FileManager.CustomFile)customFileIterator.next();
               if (file.getClass() == clazz) {
                    return file;
               }
          }

          return null;
     }

     static {
          directory = new File(Main.instance.name);
          files = new ArrayList();
     }

     public abstract static class CustomFile {
          private final File file;
          private final String name;
          private final boolean load;

          public CustomFile(String name, boolean loadOnStart) {
               this.name = name;
               this.load = loadOnStart;
               this.file = new File(FileManager.directory, name + ".json");
               if (!this.file.exists()) {
                    try {
                         this.saveFile();
                    } catch (Exception var4) {
                    }
               }

          }

          public final File getFile() {
               return this.file;
          }

          private boolean loadOnStart() {
               return this.load;
          }

          public final String getName() {
               return this.name;
          }

          public abstract void loadFile() throws Exception;

          public abstract void saveFile() throws Exception;
     }
}
