package wtf.rich.client.ui.clickgui.component.impl;

import java.awt.Color;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import wtf.rich.api.utils.render.ClientHelper;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.client.features.Feature;
import wtf.rich.client.features.impl.display.ClickGUI;
import wtf.rich.client.ui.clickgui.ClickGuiScreen;
import wtf.rich.client.ui.clickgui.Panel;
import wtf.rich.client.ui.clickgui.SorterHelper;
import wtf.rich.client.ui.clickgui.component.AnimationState;
import wtf.rich.client.ui.clickgui.component.Component;
import wtf.rich.client.ui.clickgui.component.ExpandableComponent;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.ColorSetting;
import wtf.rich.client.ui.settings.impl.ListSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public final class ModuleComponent extends ExpandableComponent {
     Minecraft mc = Minecraft.getMinecraft();
     private final Feature module;
     private final AnimationState state;
     private boolean binding;
     private int buttonLeft;
     private int buttonTop;
     private int buttonRight;
     private int buttonBottom;

     public ModuleComponent(Component parent, Feature module, int x, int y, int width, int height) {
          super(parent, module.getLabel(), x, y, width, height);
          this.module = module;
          this.state = AnimationState.STATIC;
          int propertyX = 1;
          Iterator var8 = module.getSettings().iterator();

          while(var8.hasNext()) {
               Setting setting = (Setting)var8.next();
               if (setting instanceof BooleanSetting) {
                    this.components.add(new BooleanSettingComponent(this, (BooleanSetting)setting, propertyX, height, width - 2, 21));
               } else if (setting instanceof ColorSetting) {
                    this.components.add(new ColorPickerComponent(this, (ColorSetting)setting, propertyX, height, width - 2, 15));
               } else if (setting instanceof NumberSetting) {
                    this.components.add(new NumberSettingComponent(this, (NumberSetting)setting, propertyX, height, width - 2, 20));
               } else if (setting instanceof ListSetting) {
                    this.components.add(new ListSettingComponent(this, (ListSetting)setting, propertyX, height, width - 2, 22));
               }
          }

     }

     public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
          this.components.sort(new SorterHelper());
          float x = (float)this.getX();
          float y = (float)(this.getY() - 2);
          int width = this.getWidth();
          int height = this.getHeight();
          int childY;
          if (this.isExpanded()) {
               childY = 15;
               Iterator var9 = this.components.iterator();

               label107:
               while(true) {
                    Component child;
                    int cHeight;
                    ListSettingComponent listSettingComponent;
                    do {
                         ColorPickerComponent colorPickerComponent;
                         do {
                              NumberSettingComponent numberSettingComponent;
                              do {
                                   BooleanSettingComponent booleanSettingComponent;
                                   do {
                                        if (!var9.hasNext()) {
                                             break label107;
                                        }

                                        child = (Component)var9.next();
                                        cHeight = child.getHeight();
                                        if (!(child instanceof BooleanSettingComponent)) {
                                             break;
                                        }

                                        booleanSettingComponent = (BooleanSettingComponent)child;
                                   } while(!booleanSettingComponent.booleanSetting.isVisible());

                                   if (!(child instanceof NumberSettingComponent)) {
                                        break;
                                   }

                                   numberSettingComponent = (NumberSettingComponent)child;
                              } while(!numberSettingComponent.numberSetting.isVisible());

                              if (!(child instanceof ColorPickerComponent)) {
                                   break;
                              }

                              colorPickerComponent = (ColorPickerComponent)child;
                         } while(!colorPickerComponent.getSetting().isVisible());

                         if (!(child instanceof ListSettingComponent)) {
                              break;
                         }

                         listSettingComponent = (ListSettingComponent)child;
                    } while(!listSettingComponent.getSetting().isVisible());

                    if (child instanceof ExpandableComponent) {
                         ExpandableComponent expandableComponent = (ExpandableComponent)child;
                         if (expandableComponent.isExpanded()) {
                              cHeight = expandableComponent.getHeightWithExpand();
                         }
                    }

                    child.setY(childY);
                    child.drawComponent(scaledResolution, mouseX, mouseY);
                    childY += cHeight;
               }
          }

          childY = 0;
          Color onecolor = new Color(ClickGUI.color.getColorValue());
          Color twocolor = new Color(ClickGUI.colorTwo.getColorValue());
          double speed = (double)ClickGUI.speed.getNumberValue();
          String var13 = ClickGUI.clickGuiColor.currentMode;
          byte var14 = -1;
          switch(var13.hashCode()) {
          case -1656737386:
               if (var13.equals("Rainbow")) {
                    var14 = 4;
               }
               break;
          case -311641137:
               if (var13.equals("Color Two")) {
                    var14 = 2;
               }
               break;
          case 2181788:
               if (var13.equals("Fade")) {
                    var14 = 1;
               }
               break;
          case 115155230:
               if (var13.equals("Category")) {
                    var14 = 5;
               }
               break;
          case 961091784:
               if (var13.equals("Astolfo")) {
                    var14 = 3;
               }
               break;
          case 2021122027:
               if (var13.equals("Client")) {
                    var14 = 0;
               }
          }

          switch(var14) {
          case 0:
               childY = DrawHelper.fadeColor(ClientHelper.getClientColor().getRGB(), ClientHelper.getClientColor().darker().getRGB(), (float)Math.abs(((double)System.currentTimeMillis() / speed / speed + (double)(y * 6.0F / 60.0F * 2.0F)) % 2.0D - 1.0D));
               break;
          case 1:
               childY = DrawHelper.fadeColor(onecolor.getRGB(), onecolor.darker().getRGB(), (float)Math.abs(((double)System.currentTimeMillis() / speed / speed + (double)(y * 6.0F / 60.0F * 2.0F)) % 2.0D - 1.0D));
               break;
          case 2:
               childY = DrawHelper.fadeColor(onecolor.getRGB(), twocolor.getRGB(), (float)Math.abs(((double)System.currentTimeMillis() / speed / speed + (double)(y * 6.0F / 60.0F * 2.0F)) % 2.0D - 1.0D));
               break;
          case 3:
               childY = DrawHelper.astolfo(true, (int)y).getRGB();
               break;
          case 4:
               childY = DrawHelper.rainbow(300, 1.0F, 1.0F).getRGB();
               break;
          case 5:
               Panel panel = (Panel)this.parent;
               childY = panel.type.getColor();
          }

          this.isHovered(mouseX, mouseY);
          if ((double)this.components.size() > 0.5D) {
               this.mc.neverlose500_16.drawStringWithShadow(this.isExpanded() ? "<" : ">", (double)(x + (float)width) - 8.5D, (double)(y + (float)height / 2.0F) - 3.5D, Color.GRAY.getRGB());
          }

          this.components.sort(new SorterHelper());
          this.mc.neverlose500_16.drawCenteredStringWithShadow(this.binding ? "Press a key.. " + Keyboard.getKeyName(this.module.getKey()) : this.getName(), x + 50.0F, y + (float)height / 2.0F - 3.0F, this.module.isToggled() ? childY : Color.GRAY.getRGB());
     }

     public boolean canExpand() {
          return !this.components.isEmpty();
     }

     public void onPress(int mouseX, int mouseY, int button) {
          switch(button) {
          case 0:
               this.module.toggle();
               break;
          case 2:
               this.binding = !this.binding;
          }

     }

     public void onKeyPress(int keyCode) {
          if (this.binding) {
               ClickGuiScreen.escapeKeyInUse = true;
               this.module.setKey(keyCode == 1 ? 0 : keyCode);
               this.binding = false;
          }

     }

     public int getHeightWithExpand() {
          int height = this.getHeight();
          if (this.isExpanded()) {
               Iterator var2 = this.components.iterator();

               while(true) {
                    Component child;
                    int cHeight;
                    ListSettingComponent listSettingComponent;
                    do {
                         ColorPickerComponent colorPickerComponent;
                         do {
                              NumberSettingComponent numberSettingComponent;
                              do {
                                   BooleanSettingComponent booleanSettingComponent;
                                   do {
                                        if (!var2.hasNext()) {
                                             return height;
                                        }

                                        child = (Component)var2.next();
                                        cHeight = child.getHeight();
                                        if (!(child instanceof BooleanSettingComponent)) {
                                             break;
                                        }

                                        booleanSettingComponent = (BooleanSettingComponent)child;
                                   } while(!booleanSettingComponent.booleanSetting.isVisible());

                                   if (!(child instanceof NumberSettingComponent)) {
                                        break;
                                   }

                                   numberSettingComponent = (NumberSettingComponent)child;
                              } while(!numberSettingComponent.numberSetting.isVisible());

                              if (!(child instanceof ColorPickerComponent)) {
                                   break;
                              }

                              colorPickerComponent = (ColorPickerComponent)child;
                         } while(!colorPickerComponent.getSetting().isVisible());

                         if (!(child instanceof ListSettingComponent)) {
                              break;
                         }

                         listSettingComponent = (ListSettingComponent)child;
                    } while(!listSettingComponent.getSetting().isVisible());

                    if (child instanceof ExpandableComponent) {
                         ExpandableComponent expandableComponent = (ExpandableComponent)child;
                         if (expandableComponent.isExpanded()) {
                              cHeight = expandableComponent.getHeightWithExpand();
                         }
                    }

                    height += cHeight;
               }
          } else {
               return height;
          }
     }
}
