package wtf.rich.client.ui.clickgui.component;

public abstract class ExpandableComponent extends Component {
     private boolean expanded;

     public ExpandableComponent(Component parent, String name, int x, int y, int width, int height) {
          super(parent, name, x, y, width, height);
     }

     public boolean isExpanded() {
          return this.expanded;
     }

     public void setExpanded(boolean expanded) {
          this.expanded = expanded;
     }

     public void onMouseClick(int mouseX, int mouseY, int button) {
          if (this.isHovered(mouseX, mouseY)) {
               this.onPress(mouseX, mouseY, button);
               if (this.canExpand() && button == 1) {
                    this.expanded = !this.expanded;
               }
          }

          if (this.isExpanded()) {
               super.onMouseClick(mouseX, mouseY, button);
          }

     }

     public abstract boolean canExpand();

     public abstract int getHeightWithExpand();

     public abstract void onPress(int var1, int var2, int var3);
}
