package wtf.rich.client.ui.settings;

import java.util.function.Supplier;

public class Setting extends Configurable {
     protected String name;
     protected Supplier visible;

     public boolean isVisible() {
          return (Boolean)this.visible.get();
     }

     public void setVisible(Supplier visible) {
          this.visible = visible;
     }

     public String getName() {
          return this.name;
     }
}
