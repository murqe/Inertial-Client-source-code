package wtf.rich.api.command.macro;

import java.util.ArrayList;
import java.util.List;

public class MacroManager {
     public List macros = new ArrayList();

     public List getMacros() {
          return this.macros;
     }

     public void addMacro(Macro macro) {
          this.macros.add(macro);
     }

     public void deleteMacroByKey(int key) {
          this.macros.removeIf((macro) -> {
               return macro.getKey() == key;
          });
     }
}
