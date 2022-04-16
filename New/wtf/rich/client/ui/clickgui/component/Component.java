package wtf.rich.client.ui.clickgui.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.ScaledResolution;

public class Component {
     public final Component parent;
     protected final List components = new ArrayList();
     private final String name;
     private int x;
     private int y;
     private int width;
     private int height;

     public Component(Component parent, String name, int x, int y, int width, int height) {
          this.parent = parent;
          this.name = name;
          this.x = x;
          this.y = y;
          this.width = width;
          this.height = height;
     }

     public Component getParent() {
          return this.parent;
     }

     public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
          Iterator var4 = this.components.iterator();

          while(var4.hasNext()) {
               Component child = (Component)var4.next();
               child.drawComponent(scaledResolution, mouseX, mouseY);
          }

     }

     public void onMouseClick(int mouseX, int mouseY, int button) {
          Iterator var4 = this.components.iterator();

          while(var4.hasNext()) {
               Component child = (Component)var4.next();
               child.onMouseClick(mouseX, mouseY, button);
          }

     }

     public void onMouseRelease(int button) {
          Iterator var2 = this.components.iterator();

          while(var2.hasNext()) {
               Component child = (Component)var2.next();
               child.onMouseRelease(button);
          }

     }

     public void onKeyPress(int keyCode) {
          Iterator var2 = this.components.iterator();

          while(var2.hasNext()) {
               Component child = (Component)var2.next();
               child.onKeyPress(keyCode);
          }

     }

     public String getName() {
          return this.name;
     }

     public int getX() {
          Component familyMember = this.parent;

          int familyTreeX;
          for(familyTreeX = this.x; familyMember != null; familyMember = familyMember.parent) {
               familyTreeX += familyMember.x;
          }

          return familyTreeX;
     }

     public void setX(int x) {
          this.x = x;
     }

     protected boolean isHovered(int mouseX, int mouseY) {
          int x;
          int y;
          return mouseX >= (x = this.getX()) && mouseY >= (y = this.getY()) && mouseX < x + this.getWidth() && mouseY < y + this.getHeight();
     }

     public int getY() {
          Component familyMember = this.parent;

          int familyTreeY;
          for(familyTreeY = this.y; familyMember != null; familyMember = familyMember.parent) {
               familyTreeY += familyMember.y;
          }

          return familyTreeY;
     }

     public void setY(int y) {
          this.y = y;
     }

     public int getWidth() {
          return this.width;
     }

     public void setWidth(int width) {
          this.width = width;
     }

     public int getHeight() {
          return this.height;
     }

     public void setHeight(int height) {
          this.height = height;
     }

     public List getComponents() {
          return this.components;
     }
}
