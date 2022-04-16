package wtf.rich.client.ui.clickgui.component;

import net.minecraft.client.gui.ScaledResolution;

public abstract class DraggablePanel extends ExpandableComponent {
     private boolean dragging;
     private int prevX;
     private int prevY;

     public DraggablePanel(Component parent, String name, int x, int y, int width, int height) {
          super(parent, name, x, y, width, height);
          this.prevX = x;
          this.prevY = y;
     }

     public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
          if (this.dragging) {
               this.setX(mouseX - this.prevX);
               this.setY(mouseY - this.prevY);
          }

     }

     public void onPress(int mouseX, int mouseY, int button) {
          if (button == 0) {
               this.dragging = true;
               this.prevX = mouseX - this.getX();
               this.prevY = mouseY - this.getY();
          }

     }

     public void onMouseRelease(int button) {
          super.onMouseRelease(button);
          this.dragging = false;
     }
}
