package wtf.rich.client.ui.clickgui;

import java.util.Comparator;
import wtf.rich.client.ui.clickgui.component.Component;
import wtf.rich.client.ui.clickgui.component.impl.ModuleComponent;

public class SorterHelper implements Comparator {
     public int compare(Component component, Component component2) {
          return component instanceof ModuleComponent && component2 instanceof ModuleComponent ? component.getName().compareTo(component2.getName()) : 0;
     }
}
