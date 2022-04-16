package wtf.rich.client.ui.clickgui.component.impl;

import java.awt.Color;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import wtf.rich.client.ui.clickgui.component.Component;
import wtf.rich.client.ui.clickgui.component.ExpandableComponent;
import wtf.rich.client.ui.clickgui.component.PropertyComponent;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.ListSetting;

public class ListSettingComponent extends ExpandableComponent implements PropertyComponent {
     private final ListSetting listSetting;
     Minecraft mc = Minecraft.getMinecraft();

     public ListSettingComponent(Component parent, ListSetting listSetting, int x, int y, int width, int height) {
          super(parent, listSetting.getName(), x, y, width, height);
          this.listSetting = listSetting;
     }

     public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
          super.drawComponent(scaledResolution, mouseX, mouseY);
          int x = this.getX();
          int y = this.getY();
          int width = this.getWidth();
          int height = this.getHeight();
          String selectedText = this.listSetting.currentMode;
          int dropDownBoxY = y + 10;
          int textColor = (new Color(180, 180, 180)).getRGB();
          Gui.drawRect((double)x, (double)y, (double)(x + width), (double)(y + height), (new Color(20, 20, 20)).getRGB());
          this.mc.neverlose500_13.drawCenteredString(this.getName(), (float)(x + width - 46), (float)y + 3.5F, (new Color(200, 200, 200)).getRGB());
          Gui.drawRect((double)(x + 2), (double)dropDownBoxY, (double)(x + this.getWidth() - 2), (double)((int)((double)dropDownBoxY + 9.5D)), (new Color(25, 25, 25)).getRGB());
          Gui.drawRect((double)x + 1.5D, (double)dropDownBoxY + 0.5D, (double)(x + this.getWidth()) - 1.5D, (double)(dropDownBoxY + 9), (new Color(17, 17, 17)).getRGB());
          this.mc.neverlose500_15.drawCenteredString(selectedText, (float)(x + width / 2 + 1), (float)dropDownBoxY + 2.5F, (new Color(200, 200, 200)).getRGB());
          this.mc.neverlose500_18.drawString(this.isExpanded() ? "<" : ">", (float)(x + width - 1 - 8), (float)(y + height - 11), (new Color(200, 200, 200)).getRGB());
          if (this.isExpanded()) {
               Gui.drawRect((double)(x + 1), (double)(y + height), (double)(x + width - 1), (double)(y + this.getHeightWithExpand()), (new Color(20, 20, 20)).getRGB());
               this.handleRender(x, y + this.getHeight() + 2, width, textColor);
          }

     }

     public void onMouseClick(int mouseX, int mouseY, int button) {
          super.onMouseClick(mouseX, mouseY, button);
          if (this.isExpanded()) {
               this.handleClick(mouseX, mouseY, this.getX(), this.getY() + this.getHeight() + 2, this.getWidth());
          }

     }

     private void handleRender(int x, int y, int width, int textColor) {
          Iterator var5 = this.listSetting.modes.iterator();

          while(var5.hasNext()) {
               String e = (String)var5.next();
               if (!e.equalsIgnoreCase(this.listSetting.currentMode)) {
                    this.mc.neverlose500_13.drawCenteredString(e, (float)(x + 1 + width / 2), (float)y + 2.5F, this.listSetting.currentMode.equals(e) ? textColor : Color.GRAY.getRGB());
                    y += 12;
               }
          }

     }

     private void handleClick(int mouseX, int mouseY, int x, int y, int width) {
          Iterator var6 = this.listSetting.modes.iterator();

          while(var6.hasNext()) {
               String e = (String)var6.next();
               if (!e.equalsIgnoreCase(this.listSetting.currentMode)) {
                    if (mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + 15 - 3) {
                         this.listSetting.setListMode(e);
                    }

                    y += 12;
               }
          }

     }

     public int getHeightWithExpand() {
          return this.getHeight() + (this.listSetting.modes.toArray().length - 1) * 12;
     }

     public void onPress(int mouseX, int mouseY, int button) {
     }

     public boolean canExpand() {
          return this.listSetting.modes.toArray().length > 0;
     }

     public Setting getSetting() {
          return this.listSetting;
     }
}
