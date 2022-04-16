package wtf.rich.client.ui.clickgui;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import wtf.rich.Main;
import wtf.rich.api.utils.render.ClientHelper;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.features.impl.display.ClickGUI;
import wtf.rich.client.ui.clickgui.component.AnimationState;
import wtf.rich.client.ui.clickgui.component.Component;
import wtf.rich.client.ui.clickgui.component.DraggablePanel;
import wtf.rich.client.ui.clickgui.component.ExpandableComponent;
import wtf.rich.client.ui.clickgui.component.impl.ModuleComponent;

public final class Panel extends DraggablePanel {
     Minecraft mc = Minecraft.getMinecraft();
     public static final int HEADER_WIDTH = 100;
     public static final int X_ITEM_OFFSET = 1;
     public static final int ITEM_HEIGHT = 15;
     public static final int HEADER_HEIGHT = 17;
     private final List features;
     public Category type;
     public AnimationState state;
     private int prevX;
     private int prevY;
     private boolean dragging;

     public Panel(Category category, int x, int y) {
          super((Component)null, category.name(), x, y, 100, 17);
          int moduleY = 17;
          this.state = AnimationState.STATIC;
          this.features = Main.instance.featureDirector.getFeaturesForCategory(category);

          for(Iterator var5 = this.features.iterator(); var5.hasNext(); moduleY += 15) {
               Feature module = (Feature)var5.next();
               this.components.add(new ModuleComponent(this, module, 1, moduleY, 98, 15));
          }

          this.type = category;
     }

     public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
          if (this.dragging) {
               this.setX(mouseX - this.prevX);
               this.setY(mouseY - this.prevY);
          }

          int x = this.getX();
          int y = this.getY();
          int width = this.getWidth();
          int height = this.getHeight();
          int heightWithExpand = this.getHeightWithExpand();
          int headerHeight = this.isExpanded() ? heightWithExpand : height;
          int color = 0;
          Color onecolor = new Color(ClickGUI.color.getColorValue());
          Color twocolor = new Color(ClickGUI.colorTwo.getColorValue());
          double speed = (double)ClickGUI.speed.getNumberValue();
          String var15 = ClickGUI.clickGuiColor.currentMode;
          byte var16 = -1;
          switch(var15.hashCode()) {
          case -1656737386:
               if (var15.equals("Rainbow")) {
                    var16 = 4;
               }
               break;
          case -311641137:
               if (var15.equals("Color Two")) {
                    var16 = 2;
               }
               break;
          case 2181788:
               if (var15.equals("Fade")) {
                    var16 = 1;
               }
               break;
          case 115155230:
               if (var15.equals("Category")) {
                    var16 = 5;
               }
               break;
          case 961091784:
               if (var15.equals("Astolfo")) {
                    var16 = 3;
               }
               break;
          case 2021122027:
               if (var15.equals("Client")) {
                    var16 = 0;
               }
          }

          switch(var16) {
          case 0:
               color = DrawHelper.fadeColor(ClientHelper.getClientColor().getRGB(), ClientHelper.getClientColor().darker().getRGB(), (float)Math.abs(((double)System.currentTimeMillis() / speed / speed + (double)((long)y * 6L / 60L * 2L)) % 2.0D - 1.0D));
               break;
          case 1:
               color = DrawHelper.fadeColor(onecolor.getRGB(), onecolor.darker().getRGB(), (float)Math.abs(((double)System.currentTimeMillis() / speed / speed + (double)((long)y * 6L / 60L * 2L)) % 2.0D - 1.0D));
               break;
          case 2:
               color = DrawHelper.fadeColor(onecolor.getRGB(), twocolor.getRGB(), (float)Math.abs(((double)System.currentTimeMillis() / speed / speed + (double)((long)y * 6L / 60L * 2L)) % 2.0D - 1.0D));
               break;
          case 3:
               color = DrawHelper.astolfo(true, y).getRGB();
               break;
          case 4:
               color = DrawHelper.rainbow(300, 1.0F, 1.0F).getRGB();
               break;
          case 5:
               Panel panel = (Panel)this.parent;
               color = panel.type.getColor();
          }

          float extendedHeight = 2.0F;
          DrawHelper.drawRectWithGlow((double)x, (double)(y - 4), (double)(x + width), (double)((float)(y + headerHeight) - extendedHeight), 7.0D, 7.0D, new Color(0, 0, 0, 100));
          DrawHelper.drawSmoothRect((float)x, (float)(y - 4), (float)(x + width), (float)(y + headerHeight) - extendedHeight, (new Color(4, 4, 4, 210)).hashCode());
          DrawHelper.drawSmoothRect((float)x - 0.3F, (float)(y + headerHeight - 2), (float)(x + width) + 0.3F, (float)(y + headerHeight - 3), color);
          DrawHelper.drawRectWithGlow((double)(x - 2), (double)(y - 4), (double)(x + width + 2), (double)(y + height - 2), 7.0D, 7.0D, new Color(0, 0, 0, 100));
          DrawHelper.drawSmoothRect((float)(x - 2), (float)(y - 4), (float)(x + width + 2), (float)(y + height - 2), (new Color(1, 1, 1)).getRGB());
          this.mc.neverlose500_18.drawStringWithShadow(this.getName(), (double)(x + 4), (double)((float)y + 8.5F - 6.0F), Color.LIGHT_GRAY.getRGB());
          super.drawComponent(scaledResolution, mouseX, mouseY);
          int cHeight;
          if (this.isExpanded()) {
               for(Iterator var22 = this.components.iterator(); var22.hasNext(); height += cHeight) {
                    Component component = (Component)var22.next();
                    component.setY(height);
                    component.drawComponent(scaledResolution, mouseX, mouseY);
                    cHeight = component.getHeight();
                    if (component instanceof ExpandableComponent) {
                         ExpandableComponent expandableComponent = (ExpandableComponent)component;
                         if (expandableComponent.isExpanded()) {
                              cHeight = expandableComponent.getHeightWithExpand() + 5;
                         }
                    }
               }
          }

     }

     public void onPress(int mouseX, int mouseY, int button) {
          if (button == 0 && !this.dragging) {
               this.dragging = true;
               this.prevX = mouseX - this.getX();
               this.prevY = mouseY - this.getY();
          }

     }

     public void onMouseRelease(int button) {
          super.onMouseRelease(button);
          this.dragging = false;
     }

     public boolean canExpand() {
          return !this.features.isEmpty();
     }

     public int getHeightWithExpand() {
          int height = this.getHeight();
          int cHeight;
          if (this.isExpanded()) {
               for(Iterator var2 = this.components.iterator(); var2.hasNext(); height += cHeight) {
                    Component component = (Component)var2.next();
                    cHeight = component.getHeight();
                    if (component instanceof ExpandableComponent) {
                         ExpandableComponent expandableComponent = (ExpandableComponent)component;
                         if (expandableComponent.isExpanded()) {
                              cHeight = expandableComponent.getHeightWithExpand() + 5;
                         }
                    }
               }
          }

          return height;
     }
}
