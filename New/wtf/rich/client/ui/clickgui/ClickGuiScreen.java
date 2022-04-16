package wtf.rich.client.ui.clickgui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import wtf.rich.api.utils.render.ClientHelper;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.impl.display.ClickGUI;
import wtf.rich.client.ui.clickgui.component.Component;
import wtf.rich.client.ui.clickgui.component.ExpandableComponent;
import wtf.rich.client.ui.settings.button.ImageButton;

public class ClickGuiScreen extends GuiScreen {
     public static boolean escapeKeyInUse;
     public List components = new ArrayList();
     public ScreenHelper screenHelper;
     public boolean exit = false;
     public Category type;
     private Component selectedPanel;
     protected ArrayList imageButtons = new ArrayList();

     public ClickGuiScreen() {
          int height = 20;
          int x = 20;
          int y = 80;
          Category[] var4 = Category.values();
          int var5 = var4.length;

          for(int var6 = 0; var6 < var5; ++var6) {
               Category type = var4[var6];
               this.type = type;
               this.components.add(new Panel(type, x, y));
               this.selectedPanel = new Panel(type, x, y);
               x += height + 90;
          }

          this.screenHelper = new ScreenHelper(0.0F, 0.0F);
     }

     public void initGui() {
          new ScaledResolution(this.mc);
          this.screenHelper = new ScreenHelper(0.0F, 0.0F);
          this.imageButtons.clear();
          this.imageButtons.add(new ImageButton(new ResourceLocation("richclient/config.png"), 5, 500, 20, 20, "", 22));
          if (ClickGUI.blur.getBoolValue()) {
               this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
          }

          super.initGui();
     }

     public void drawScreen(int mouseX, int mouseY, float partialTicks) {
          ScaledResolution sr = new ScaledResolution(this.mc);
          Color color = Color.WHITE;
          Color onecolor = new Color(ClickGUI.color.getColorValue());
          Color twocolor = new Color(ClickGUI.colorTwo.getColorValue());
          double speed = (double)ClickGUI.speed.getNumberValue();
          String var10 = ClickGUI.clickGuiColor.currentMode;
          byte var11 = -1;
          switch(var10.hashCode()) {
          case -1808614770:
               if (var10.equals("Static")) {
                    var11 = 6;
               }
               break;
          case -1656737386:
               if (var10.equals("Rainbow")) {
                    var11 = 4;
               }
               break;
          case -311641137:
               if (var10.equals("Color Two")) {
                    var11 = 3;
               }
               break;
          case 2181788:
               if (var10.equals("Fade")) {
                    var11 = 1;
               }
               break;
          case 115155230:
               if (var10.equals("Category")) {
                    var11 = 5;
               }
               break;
          case 961091784:
               if (var10.equals("Astolfo")) {
                    var11 = 2;
               }
               break;
          case 2021122027:
               if (var10.equals("Client")) {
                    var11 = 0;
               }
          }

          switch(var11) {
          case 0:
               color = ClientHelper.getClientColor();
               break;
          case 1:
               color = new Color(ClickGUI.color.getColorValue());
               break;
          case 2:
               color = DrawHelper.astolfo(true, this.width);
               break;
          case 3:
               color = new Color(DrawHelper.fadeColor(onecolor.getRGB(), twocolor.getRGB(), (float)Math.abs(((double)System.currentTimeMillis() / speed / speed + (double)((long)this.height * 6L / 60L * 2L)) % 2.0D - 1.0D)));
               break;
          case 4:
               color = DrawHelper.rainbow(300, 1.0F, 1.0F);
               break;
          case 5:
               color = new Color(this.type.getColor());
               break;
          case 6:
               color = onecolor;
          }

          Color none = new Color(0, 0, 0, 0);
          String mode = ClickGUI.backGroundMode.getOptions();
          String mode2 = ClickGUI.guiImage.getOptions();
          if (ClickGUI.anime.getBoolValue() && mode2.equalsIgnoreCase("Taksa")) {
               DrawHelper.drawImage(new ResourceLocation("taksa.png"), (float)(sr.getScaledWidth() - 280), (float)(sr.getScaledHeight() - 380), 280.0F, 380.0F, new Color(255, 255, 255, 255));
          }

          if (ClickGUI.anime.getBoolValue() && mode2.equalsIgnoreCase("Anime")) {
               DrawHelper.drawImage(new ResourceLocation("anime.png"), (float)(sr.getScaledWidth() - 280), (float)(sr.getScaledHeight() - 380), 280.0F, 380.0F, new Color(255, 255, 255, 255));
          }

          if (ClickGUI.background.getBoolValue()) {
               if (mode.equalsIgnoreCase("Top")) {
                    this.drawDefaultBackground();
                    this.drawGradientRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), color.getRGB(), none.getRGB());
               } else if (mode.equalsIgnoreCase("Bottom")) {
                    this.drawDefaultBackground();
                    this.drawGradientRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), none.getRGB(), color.getRGB());
               } else if (mode.equalsIgnoreCase("Everywhere")) {
                    this.drawDefaultBackground();
                    this.drawGradientRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), DrawHelper.setAlpha(color, 100).getRGB(), DrawHelper.setAlpha(color, 100).getRGB());
               }
          }

          Iterator var13 = this.components.iterator();

          while(var13.hasNext()) {
               Panel panel = (Panel)var13.next();
               panel.drawComponent(sr, mouseX, mouseY);
          }

          var13 = this.imageButtons.iterator();

          while(var13.hasNext()) {
               ImageButton imageButton = (ImageButton)var13.next();
               imageButton.draw(mouseX, mouseY, Color.WHITE);
               if (Mouse.isButtonDown(0)) {
                    imageButton.onClick(mouseX, mouseY);
               }
          }

          this.updateMouseWheel();
          if (this.exit) {
               this.screenHelper.interpolate(0.0F, 0.0F, 2.0D);
               if (this.screenHelper.getY() < 200.0F) {
                    this.exit = false;
                    this.mc.displayGuiScreen((GuiScreen)null);
                    if (this.mc.currentScreen == null) {
                         this.mc.setIngameFocus();
                    }
               }
          } else {
               this.screenHelper.interpolate((float)this.width, (float)this.height, 3.0D * Minecraft.frameTime / 6.0D);
          }

          super.drawScreen(mouseX, mouseY, partialTicks);
     }

     public void updateMouseWheel() {
          int scrollWheel = Mouse.getDWheel();
          Iterator var2 = this.components.iterator();

          while(var2.hasNext()) {
               Component panel = (Component)var2.next();
               if (scrollWheel > 0) {
                    panel.setY(panel.getY() + 15);
               }

               if (scrollWheel < 0) {
                    panel.setY(panel.getY() - 15);
               }
          }

     }

     protected void keyTyped(char typedChar, int keyCode) throws IOException {
          if (keyCode == 1) {
               this.exit = true;
          }

          if (!this.exit) {
               this.selectedPanel.onKeyPress(keyCode);
               if (!escapeKeyInUse) {
                    super.keyTyped(typedChar, keyCode);
               }

               escapeKeyInUse = false;
          }
     }

     protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
          Iterator var4 = this.components.iterator();

          while(var4.hasNext()) {
               Component component = (Component)var4.next();
               int x = component.getX();
               int y = component.getY();
               int cHeight = component.getHeight();
               if (component instanceof ExpandableComponent) {
                    ExpandableComponent expandableComponent = (ExpandableComponent)component;
                    if (expandableComponent.isExpanded()) {
                         cHeight = expandableComponent.getHeightWithExpand();
                    }
               }

               if (mouseX > x && mouseY > y && mouseX < x + component.getWidth() && mouseY < y + cHeight) {
                    this.selectedPanel = component;
                    component.onMouseClick(mouseX, mouseY, mouseButton);
                    break;
               }
          }

     }

     protected void mouseReleased(int mouseX, int mouseY, int state) {
          this.selectedPanel.onMouseRelease(state);
     }

     public void onGuiClosed() {
          this.screenHelper = new ScreenHelper(0.0F, 0.0F);
          this.mc.entityRenderer.theShaderGroup = null;
          super.onGuiClosed();
     }
}
