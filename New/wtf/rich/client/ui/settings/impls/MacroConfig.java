package wtf.rich.client.ui.settings.impls;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Iterator;
import org.lwjgl.input.Keyboard;
import wtf.rich.Main;
import wtf.rich.api.command.macro.Macro;
import wtf.rich.client.ui.settings.FileManager;

public class MacroConfig extends FileManager.CustomFile {
     public MacroConfig(String name, boolean loadOnStart) {
          super(name, loadOnStart);
     }

     public void loadFile() {
          try {
               FileInputStream fileInputStream = new FileInputStream(this.getFile().getAbsolutePath());
               DataInputStream in = new DataInputStream(fileInputStream);
               BufferedReader br = new BufferedReader(new InputStreamReader(in));

               String line;
               while((line = br.readLine()) != null) {
                    String curLine = line.trim();
                    String bind = curLine.split(":")[0];
                    String value = curLine.split(":")[1];
                    if (Main.instance.macroManager != null) {
                         Main.instance.macroManager.addMacro(new Macro(Keyboard.getKeyIndex(bind), value));
                    }
               }

               br.close();
          } catch (Exception var8) {
          }

     }

     public void saveFile() {
          try {
               BufferedWriter out = new BufferedWriter(new FileWriter(this.getFile()));
               Iterator var2 = Main.instance.macroManager.getMacros().iterator();

               while(var2.hasNext()) {
                    Macro m = (Macro)var2.next();
                    if (m != null) {
                         out.write(Keyboard.getKeyName(m.getKey()) + ":" + m.getValue());
                         out.write("\r\n");
                    }
               }

               out.close();
          } catch (Exception var4) {
          }

     }
}
