package clickgui.panel;

import clickgui.panel.component.Component;
import clickgui.panel.component.impl.ModuleComponent;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.rich.Main;
import me.rich.font.Fonts;
import me.rich.helpers.DrawHelper;
import me.rich.helpers.render.AnimationHelper;
import me.rich.helpers.render.RenderUtil;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.module.render.ClickGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public final class Panel {
      public static final int HEADER_SIZE = 20;
      public static final int HEADER_OFFSET = 2;
      public final Category category;
      public final List components = new ArrayList();
      public final int width;
      public double scissorBoxHeight;
      public int x;
      public int lastX;
      public int y;
      public int lastY;
      public int height;
      public AnimationState state;
      public boolean dragging;
      public int activeRectAnimate;
      public double scalling;

      public Panel(Category category, int x, int y) {
            this.state = AnimationState.STATIC;
            this.category = category;
            this.x = x;
            this.y = y;
            this.width = 100;
            int componentY = 20;
            List modulesForCategory = Arrays.asList(Main.moduleManager.getModulesInCategory(category));
            int modulesForCategorySize = modulesForCategory.size();

            for(int i = 0; i < modulesForCategorySize; ++i) {
                  Feature feature = (Feature)modulesForCategory.get(i);
                  ModuleComponent component = new ModuleComponent(feature, this, 0, componentY, this.width, 15);
                  this.components.add(component);
                  componentY += 15;
            }

            this.height = componentY - 20;
      }

      public int getX() {
            return this.x;
      }

      public int getY() {
            return this.y;
      }

      public int getWidth() {
            return this.width;
      }

      public int getHeight() {
            return this.height;
      }

      public void setHeight(int height) {
            this.height = height;
      }

      private void updateComponentHeight() {
            int componentY = 20;
            List componentList = this.components;
            int componentListSize = componentList.size();

            for(int i = 0; i < componentListSize; ++i) {
                  Component component = (Component)componentList.get(i);
                  component.setY(componentY);
                  componentY = (int)((double)componentY + (double)component.getHeight() + component.getOffset());
            }

            this.height = componentY - 20;
      }

      public final void onDraw(int mouseX, int mouseY) {
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            int x = this.x;
            int y = this.y;
            int width = this.width;
            this.updateComponentHeight();
            this.handleScissorBox();
            this.handleDragging(mouseX, mouseY);
            double scissorBoxHeight = this.scissorBoxHeight;
            int backgroundColor = (new Color(20, 20, 20, 20)).getRGB();
            this.activeRectAnimate = (int)AnimationHelper.animate((double)this.activeRectAnimate, this.dragging ? -1.879048192E9D : -1.0D, 0.10000000149011612D);
            RenderUtil.drawSmoothRect((float)x, (float)y, (float)(x + width), (float)((double)(y + 18) + scissorBoxHeight), (new Color(20, 20, 20, 200)).getRGB());
            RenderUtil.drawNewRect((double)x, (double)y, (double)(x + width), (double)(y + 20), backgroundColor);
            String name = "toperclient/" + this.category.name + ".png";
            RenderUtil.drawSmoothRect((float)this.x, (float)(this.y + -2), (float)(this.x + this.width), (float)this.y, (new Color(0, 255, 255)).getRGB());
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(name));
            Fonts.neverlose501.drawStringWithShadow(this.category.name, (double)(x + 5), (double)((float)y + 10.0F - 3.0F), -1);
            GL11.glPushMatrix();
            GL11.glEnable(3089);
            RenderUtil.prepareScissorBox((float)(x - 2), (float)(y + 20 - 2), (float)(x + width + 2), (float)((double)(y + 20) + scissorBoxHeight));
            List components = this.components;
            int componentsSize = components.size();

            for(int i = 0; i < componentsSize; ++i) {
                  ((Component)components.get(i)).onDraw(mouseX, mouseY);
                  if (i != componentsSize - 1) {
                        RenderUtil.prepareScissorBox((float)(x - 2), (float)(y + 20), (float)(x + width + 2), (float)((double)(y + 20) + scissorBoxHeight));
                  }
            }

            GL11.glDisable(3089);
            GL11.glPopMatrix();
            String mode = Main.settingsManager.getSettingByName(Main.moduleManager.getModule(ClickGUI.class), "Anime Mode").getValString();
            if (mode.equalsIgnoreCase("Anime")) {
                  DrawHelper.drawImage(new ResourceLocation("toperclient/cosmetic/cape/anime.png"), (int)((float)sr.getScaledWidth() / 1.4F) - 12 + 20, (int)((float)sr.getScaledHeight() / 2.0F) - 47, 250, 300, -1);
            } else if (mode.equalsIgnoreCase("Anime2")) {
                  DrawHelper.drawImage(new ResourceLocation("toperclient/cosmetic/cape/123123.png"), (int)((float)sr.getScaledWidth() / 1.4F) - 12 + 20, (int)((float)sr.getScaledHeight() / 2.0F) - 47, 250, 300, -1);
            } else if (mode.equalsIgnoreCase("Anime3")) {
                  DrawHelper.drawImage(new ResourceLocation("toperclient/cosmetic/cape/12345.png"), (int)((float)sr.getScaledWidth() / 1.4F) - 12 + 20, (int)((float)sr.getScaledHeight() / 2.0F) - 47, 250, 300, -1);
            }

      }

      public final void onMouseClick(int mouseX, int mouseY, int mouseButton) {
            int x = this.x;
            int y = this.y;
            int width = this.width;
            double scissorBoxHeight = this.scissorBoxHeight;
            if (mouseX > x - 2 && mouseX < x + width + 2 && mouseY > y && mouseY < y + 20) {
                  if (mouseButton == 1) {
                        if (scissorBoxHeight > 0.0D && (this.state == AnimationState.EXPANDING || this.state == AnimationState.STATIC)) {
                              this.state = AnimationState.RETRACTING;
                        } else if (scissorBoxHeight < (double)(this.height + 2) && (this.state == AnimationState.EXPANDING || this.state == AnimationState.STATIC)) {
                              this.state = AnimationState.EXPANDING;
                        }
                  } else if (mouseButton == 0 && !this.dragging) {
                        this.lastX = x - mouseX;
                        this.lastY = y - mouseY;
                        this.dragging = true;
                  }
            }

            List components = this.components;
            int componentsSize = components.size();

            for(int i = 0; i < componentsSize; ++i) {
                  Component component = (Component)components.get(i);
                  int componentY = component.getY();
                  if ((double)componentY < scissorBoxHeight + 20.0D) {
                        component.onMouseClick(mouseX, mouseY, mouseButton);
                  }
            }

      }

      public final void onMouseRelease(int mouseX, int mouseY, int mouseButton) {
            if (this.dragging) {
                  this.dragging = false;
            }

            if (this.scissorBoxHeight > 0.0D) {
                  List components = this.components;
                  int componentsSize = components.size();

                  for(int i = 0; i < componentsSize; ++i) {
                        ((Component)components.get(i)).onMouseRelease(mouseX, mouseY, mouseButton);
                  }
            }

      }

      public void setY(int y) {
            this.y = y;
      }

      public final void onKeyPress(char typedChar, int keyCode) {
            if (this.scissorBoxHeight > 0.0D) {
                  List components = this.components;
                  int componentsSize = components.size();

                  for(int i = 0; i < componentsSize; ++i) {
                        ((Component)components.get(i)).onKeyPress(typedChar, keyCode);
                  }
            }

      }

      private void handleDragging(int mouseX, int mouseY) {
            if (this.dragging) {
                  this.x = mouseX + this.lastX;
                  this.y = mouseY + this.lastY;
            }

      }

      private void handleScissorBox() {
            int height = this.height;
            switch(this.state) {
            case EXPANDING:
                  if (this.scissorBoxHeight < (double)(height + 2)) {
                        this.scissorBoxHeight = AnimationHelper.animate((double)(height + 2), this.scissorBoxHeight, 0.07D);
                  } else if (this.scissorBoxHeight >= (double)(height + 2)) {
                        this.state = AnimationState.STATIC;
                  }
                  break;
            case RETRACTING:
                  if (this.scissorBoxHeight > 0.0D) {
                        this.scissorBoxHeight = AnimationHelper.animate(0.0D, this.scissorBoxHeight, 0.07D);
                  } else if (this.scissorBoxHeight <= 0.0D) {
                        this.state = AnimationState.STATIC;
                  }
                  break;
            case STATIC:
                  if (this.scissorBoxHeight > 0.0D && this.scissorBoxHeight != (double)(height + 2)) {
                        this.scissorBoxHeight = AnimationHelper.animate((double)(height + 2), this.scissorBoxHeight, 0.07D);
                  }

                  this.scissorBoxHeight = this.clamp(this.scissorBoxHeight, (double)(height + 2));
            }

      }

      private double clamp(double a, double max) {
            return a < 0.0D ? 0.0D : Math.min(a, max);
      }
}
