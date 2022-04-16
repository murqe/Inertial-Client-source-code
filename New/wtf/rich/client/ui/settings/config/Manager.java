package wtf.rich.client.ui.settings.config;

import java.util.ArrayList;
import java.util.List;

public abstract class Manager {
     private List contents = new ArrayList();

     public List getContents() {
          return this.contents;
     }

     public void setContents(ArrayList contents) {
          this.contents = contents;
     }
}
