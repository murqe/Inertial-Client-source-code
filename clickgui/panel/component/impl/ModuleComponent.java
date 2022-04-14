package clickgui.panel.component.impl;

import clickgui.panel.AnimationState;
import clickgui.panel.Panel;
import clickgui.panel.component.Component;
import de.Hero.settings.Setting;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.rich.Main;
import me.rich.font.Fonts;
import me.rich.helpers.render.AnimationHelper;
import me.rich.helpers.render.ColorUtils;
import me.rich.helpers.render.RenderUtil;
import me.rich.module.Feature;
import org.lwjgl.input.Keyboard;

public final class ModuleComponent extends Component {
      public final List components = new ArrayList();
      private final ArrayList children = new ArrayList();
      private static Color BACKGROUND_COLOR = new Color(180, 180, 180);
      private final Feature module;
      private int opacity = 120;
      private int childrenHeight;
      private double scissorBoxHeight;
      private AnimationState state;
      private boolean binding;
      private float activeRectAnimate;
      public float animation;
      int onlySettingsY;

      public ModuleComponent(Feature module, Panel parent, int x, int y, int width, int height) {
            super(parent, x, y, width, height);
            this.state = AnimationState.STATIC;
            this.activeRectAnimate = 0.0F;
            this.animation = 0.0F;
            this.onlySettingsY = 0;
            this.module = module;
            int y2 = height;
            boolean i = false;
            if (Main.settingsManager.getSettingsByMod(module) != null) {
                  Iterator var9 = Main.settingsManager.getSettingsByMod(module).iterator();

                  while(var9.hasNext()) {
                        Setting s = (Setting)var9.next();
                        if (s.isCombo()) {
                              this.children.add(new EnumOptionComponent(s, this.getPanel(), x, y + y2, width, height));
                              y2 += height + 20;
                        }

                        if (s.isSlider()) {
                              this.children.add(new NumberOptionComponent(s, this.getPanel(), x, y, width, 16));
                              y2 += height + 20;
                              y2 += height + 20;
                        }

                        if (s.isCheck()) {
                              this.children.add(new BoolOptionComponent(s, this.getPanel(), x, y + y2, width, height));
                              y2 += height + 20;
                        }
                  }
            }

      }

      public double getOffset() {
            return this.scissorBoxHeight;
      }

      private void drawChildren(int mouseX, int mouseY) {
            int childY = 15;
            ArrayList children = this.children;
            int componentListSize = children.size();

            for(int i = 0; i < componentListSize; ++i) {
                  Component child = (Component)children.get(i);
                  if (!child.isHidden()) {
                        child.setY(this.getY() + childY);
                        child.onDraw(mouseX, mouseY);
                        childY += 15;
                  }
            }

      }

      private int calculateChildrenHeight() {
            int height = 0;
            ArrayList children = this.children;
            int childrenSize = children.size();

            for(int i = 0; i < childrenSize; ++i) {
                  Component component = (Component)children.get(i);
                  if (!component.isHidden()) {
                        height = (int)((double)(height + component.getHeight()) + component.getOffset());
                  }
            }

            return height;
      }

      public void onDraw(int mouseX, int mouseY) {
            Panel parent = this.getPanel();
            int x = parent.getX() + this.getX();
            int y = parent.getY() + this.getY();
            int height = this.getHeight();
            int width = this.getWidth();
            boolean hovered = this.isMouseOver(mouseX, mouseY);
            this.handleScissorBox();
            this.childrenHeight = this.calculateChildrenHeight();
            if (hovered) {
                  if (this.opacity < 200) {
                        this.opacity += 5;
                  }
            } else if (this.opacity > 120) {
                  this.opacity -= 5;
            }

            this.activeRectAnimate = AnimationHelper.animation(this.activeRectAnimate, hovered ? 4.0F : 2.0F, 0.001F);
            int opacity = this.opacity;
            int color = this.module.isToggled() ? ColorUtils.getColor(0, 255, 255) : (new Color(opacity, opacity, opacity)).getRGB();
            Fonts.neverlose502.drawCenteredStringWithShadow(this.binding ? "Binding... Key:" + Keyboard.getKeyName(this.module.getKey()) : this.module.getName(), (double)((float)x + 50.5F), (double)((float)y + (float)height / 1.5F - 4.0F), color);
            if (this.scissorBoxHeight > 0.0D) {
                  if (parent.state != AnimationState.RETRACTING) {
                        RenderUtil.prepareScissorBox((float)x, (float)y, (float)(x + width), (float)((double)y + Math.min(this.scissorBoxHeight, parent.scissorBoxHeight) + (double)height));
                  }

                  this.drawChildren(mouseX, mouseY);
            }

      }

      public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
            if (this.scissorBoxHeight > 0.0D) {
                  ArrayList componentList = this.children;
                  int componentListSize = componentList.size();

                  for(int i = 0; i < componentListSize; ++i) {
                        ((Component)componentList.get(i)).onMouseClick(mouseX, mouseY, mouseButton);
                  }
            }

            if (this.isMouseOver(mouseX, mouseY) && mouseButton == 2) {
                  boolean var7 = this.binding = !this.binding;
            }

            if (this.isMouseOver(mouseX, mouseY)) {
                  if (mouseButton == 0) {
                        this.module.toggle();
                  } else if (mouseButton == 1 && !this.children.isEmpty()) {
                        if (this.scissorBoxHeight <= 0.0D || this.state != AnimationState.EXPANDING && this.state != AnimationState.STATIC) {
                              if (this.scissorBoxHeight < (double)this.childrenHeight && (this.state == AnimationState.EXPANDING || this.state == AnimationState.STATIC)) {
                                    this.state = AnimationState.EXPANDING;
                              }
                        } else {
                              this.state = AnimationState.RETRACTING;
                        }
                  }
            }

      }

      public void onMouseRelease(int mouseX, int mouseY, int mouseButton) {
            if (this.scissorBoxHeight > 0.0D) {
                  ArrayList componentList = this.children;
                  int componentListSize = componentList.size();

                  for(int i = 0; i < componentListSize; ++i) {
                        ((Component)componentList.get(i)).onMouseRelease(mouseX, mouseY, mouseButton);
                  }
            }

      }

      public void onKeyPress(int typedChar, int keyCode) {
            if (this.binding) {
                  this.module.setKey(keyCode);
                  this.binding = false;
                  if (keyCode == 211) {
                        this.module.setKey(0);
                  } else if (keyCode == 1) {
                        this.setBinding(false);
                  }
            }

            if (this.scissorBoxHeight > 0.0D) {
                  ArrayList componentList = this.children;
                  int componentListSize = componentList.size();

                  for(int i = 0; i < componentListSize; ++i) {
                        ((Component)componentList.get(i)).onKeyPress(typedChar, keyCode);
                  }
            }

      }

      public void setBinding(boolean binding) {
            this.binding = binding;
      }

      private void handleScissorBox() {
            int childrenHeight = this.childrenHeight;
            switch(this.state) {
            case EXPANDING:
                  if (this.scissorBoxHeight < (double)childrenHeight) {
                        this.scissorBoxHeight = AnimationHelper.animate((double)childrenHeight, this.scissorBoxHeight, 0.06D);
                  } else if (this.scissorBoxHeight >= (double)childrenHeight) {
                        this.state = AnimationState.STATIC;
                  }

                  this.scissorBoxHeight = this.clamp(this.scissorBoxHeight, (double)childrenHeight);
                  break;
            case RETRACTING:
                  if (this.scissorBoxHeight > 0.0D) {
                        this.scissorBoxHeight = AnimationHelper.animate(0.0D, this.scissorBoxHeight, 0.06D);
                  } else if (this.scissorBoxHeight <= 0.0D) {
                        this.state = AnimationState.STATIC;
                  }

                  this.scissorBoxHeight = this.clamp(this.scissorBoxHeight, (double)childrenHeight);
                  break;
            case STATIC:
                  if (this.scissorBoxHeight > 0.0D && this.scissorBoxHeight != (double)childrenHeight) {
                        this.scissorBoxHeight = AnimationHelper.animate((double)childrenHeight, this.scissorBoxHeight, 0.06D);
                  }

                  this.scissorBoxHeight = this.clamp(this.scissorBoxHeight, (double)childrenHeight);
            }

      }

      private double clamp(double a, double max) {
            return a < 0.0D ? 0.0D : Math.min(a, max);
      }
}
