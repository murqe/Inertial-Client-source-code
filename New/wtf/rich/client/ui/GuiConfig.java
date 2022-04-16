package wtf.rich.client.ui;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import wtf.rich.Main;
import wtf.rich.api.utils.notifications.NotificationPublisher;
import wtf.rich.api.utils.notifications.NotificationType;
import wtf.rich.api.utils.other.ParticleEngine;
import wtf.rich.api.utils.render.ClientHelper;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.impl.display.ClickGUI;
import wtf.rich.client.ui.clickgui.ScreenHelper;
import wtf.rich.client.ui.settings.button.ConfigGuiButton;
import wtf.rich.client.ui.settings.button.ImageButton;
import wtf.rich.client.ui.settings.config.Config;
import wtf.rich.client.ui.settings.config.ConfigManager;

public class GuiConfig extends GuiScreen {
     public static GuiTextField search;
     public static Config selectedConfig = null;
     public ScreenHelper screenHelper = new ScreenHelper(0.0F, 0.0F);
     protected ArrayList imageButtons = new ArrayList();
     private int width;
     private int height;
     private float scrollOffset;
     ParticleEngine idinahyi = new ParticleEngine();
     public Category type;

     protected void actionPerformed(GuiButton button) throws IOException {
          if (button.id == 1) {
               Main.instance.configManager.saveConfig(search.getText());
               Main.msg(ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "created config: " + ChatFormatting.RED + "\"" + search.getText() + "\"", true);
               NotificationPublisher.queue("Config", ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "created config: " + ChatFormatting.RED + "\"" + search.getText() + "\"", NotificationType.SUCCESS);
               ConfigManager.getLoadedConfigs().clear();
               Main.instance.configManager.load();
               search.setFocused(false);
               search.setText("");
          }

          if (selectedConfig != null) {
               if (button.id == 2) {
                    if (Main.instance.configManager.loadConfig(selectedConfig.getName())) {
                         Main.msg(ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "loaded config: " + ChatFormatting.RED + "\"" + selectedConfig.getName() + "\"", true);
                         NotificationPublisher.queue("Config", ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "loaded config: " + ChatFormatting.RED + "\"" + selectedConfig.getName() + "\"", NotificationType.SUCCESS);
                    } else {
                         Main.msg(ChatFormatting.RED + "Failed " + ChatFormatting.WHITE + "load config: " + ChatFormatting.RED + "\"" + selectedConfig.getName() + "\"", true);
                         NotificationPublisher.queue("Config", ChatFormatting.RED + "Failed " + ChatFormatting.WHITE + "load config: " + ChatFormatting.RED + "\"" + selectedConfig.getName() + "\"", NotificationType.ERROR);
                    }
               } else if (button.id == 3) {
                    if (Main.instance.configManager.saveConfig(selectedConfig.getName())) {
                         Main.msg(ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "saved config: " + ChatFormatting.RED + "\"" + selectedConfig.getName() + "\"", true);
                         NotificationPublisher.queue("Config", ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "saved config: " + ChatFormatting.RED + "\"" + selectedConfig.getName() + "\"", NotificationType.SUCCESS);
                    } else {
                         Main.msg(ChatFormatting.RED + "Failed " + ChatFormatting.WHITE + "to save config: " + ChatFormatting.RED + "\"" + search.getText() + "\"", true);
                         NotificationPublisher.queue("Config", ChatFormatting.RED + "Failed " + ChatFormatting.WHITE + "to save config: " + ChatFormatting.RED + "\"" + search.getText() + "\"", NotificationType.ERROR);
                    }
               } else if (button.id == 4) {
                    if (Main.instance.configManager.deleteConfig(selectedConfig.getName())) {
                         Main.msg(ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "deleted config: " + ChatFormatting.RED + "\"" + selectedConfig.getName() + "\"", true);
                         NotificationPublisher.queue("Config", ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "deleted config: " + ChatFormatting.RED + "\"" + selectedConfig.getName() + "\"", NotificationType.SUCCESS);
                    } else {
                         Main.msg(ChatFormatting.RED + "Failed " + ChatFormatting.WHITE + "to delete config: " + ChatFormatting.RED + "\"" + selectedConfig.getName() + "\"", true);
                         NotificationPublisher.queue("Config", ChatFormatting.RED + "Failed " + ChatFormatting.WHITE + "to delete config: " + ChatFormatting.RED + "\"" + selectedConfig.getName() + "\"", NotificationType.ERROR);
                    }
               }
          }

          super.actionPerformed(button);
     }

     private boolean isHoveredConfig(int x, int y, int width, int height, int mouseX, int mouseY) {
          return MouseHelper.isHovered((double)x, (double)y, (double)(x + width), (double)(y + height), mouseX, mouseY);
     }

     public void initGui() {
          if (ClickGUI.blur.getBoolValue()) {
               this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
          }

          ScaledResolution sr = new ScaledResolution(this.mc);
          this.screenHelper = new ScreenHelper(0.0F, 0.0F);
          this.width = sr.getScaledWidth() / 2;
          this.height = sr.getScaledHeight() / 2;
          Minecraft var10003 = this.mc;
          search = new GuiTextField(228, Minecraft.fontRendererObj, this.width - 125, this.height - 133, 250, 13);
          this.buttonList.add(new ConfigGuiButton(1, this.width - 220, this.height + 85, "Create"));
          this.buttonList.add(new ConfigGuiButton(2, this.width - 155, this.height + 85, "Load"));
          this.buttonList.add(new ConfigGuiButton(3, this.width - 90, this.height + 85, "Save"));
          this.buttonList.add(new ConfigGuiButton(4, this.width - 25, this.height + 85, "Delete"));
          this.imageButtons.clear();
          super.initGui();
     }

     public void drawScreen(int mouseX, int mouseY, float partialTicks) {
          ScaledResolution sr = new ScaledResolution(this.mc);
          Color colora = Color.WHITE;
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
               colora = ClientHelper.getClientColor();
               break;
          case 1:
               colora = new Color(ClickGUI.color.getColorValue());
               break;
          case 2:
               colora = DrawHelper.astolfo(true, this.width);
               break;
          case 3:
               colora = new Color(DrawHelper.fadeColor(onecolor.getRGB(), twocolor.getRGB(), (float)Math.abs(((double)System.currentTimeMillis() / speed / speed + (double)((long)this.height * 6L / 60L * 2L)) % 2.0D - 1.0D)));
               break;
          case 4:
               colora = DrawHelper.rainbow(300, 1.0F, 1.0F);
               break;
          case 5:
               colora = new Color(this.type.getColor());
               break;
          case 6:
               colora = onecolor;
          }

          Color none = new Color(0, 0, 0, 0);
          if (ClickGUI.background.getBoolValue()) {
               this.drawDefaultBackground();
               this.drawGradientRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), none.getRGB(), colora.getRGB());
          }

          Iterator var19 = Main.instance.configManager.getContents().iterator();

          while(var19.hasNext()) {
               Config config = (Config)var19.next();
               if (config != null && Mouse.hasWheel() && this.isHoveredConfig(this.width - 100, this.height - 122, 151, this.height + 59, mouseX, mouseY)) {
                    int wheel = Mouse.getDWheel();
                    if (wheel < 0) {
                         this.scrollOffset += 13.0F;
                         if (this.scrollOffset < 0.0F) {
                              this.scrollOffset = 0.0F;
                         }
                    } else if (wheel > 0) {
                         this.scrollOffset -= 13.0F;
                         if (this.scrollOffset < 0.0F) {
                              this.scrollOffset = 0.0F;
                         }
                    }
               }
          }

          GlStateManager.pushMatrix();
          DrawHelper.drawRect((double)(this.width - 150), (double)(this.height - 150), (double)(this.width + 150), (double)(this.height + 65), (new Color(26, 26, 26)).getRGB());
          DrawHelper.drawRect((double)(this.width - 127), (double)(this.height - 120), (double)(this.width + 127), (double)(this.height + 40), (new Color(1, 1, 1, 30)).getRGB());
          this.mc.neverlose500_16.drawCenteredStringWithShadow("Config Manager", (float)this.width, (float)(this.height - 143), -1);
          search.drawTextBox();
          if (search.getText().isEmpty() && !search.isFocused()) {
               this.mc.neverlose500_16.drawStringWithShadow("Config name...", (double)(this.width - 125), (double)(this.height - 130), DrawHelper.getColor(200));
          }

          var19 = this.imageButtons.iterator();

          while(var19.hasNext()) {
               ImageButton imageButton = (ImageButton)var19.next();
               imageButton.draw(mouseX, mouseY, Color.WHITE);
               if (Mouse.isButtonDown(0)) {
                    imageButton.onClick(mouseX, mouseY);
               }
          }

          int yDist = 0;
          GL11.glEnable(3089);
          DrawHelper.scissorRect(0.0F, (float)(this.height - 119), (float)(this.width + 130), (double)(this.height + 40));
          Iterator var18 = Main.instance.configManager.getContents().iterator();

          while(var18.hasNext()) {
               Config config = (Config)var18.next();
               if (config != null) {
                    int color;
                    if (this.isHoveredConfig(this.width - 60, (int)((float)(this.height - 117 + yDist) - this.scrollOffset), this.width + 60, 14, mouseX, mouseY)) {
                         color = -1;
                         if (Mouse.isButtonDown(0)) {
                              selectedConfig = new Config(config.getName());
                         }
                    } else {
                         color = DrawHelper.getColor(200);
                    }

                    if (selectedConfig != null && config.getName().equals(selectedConfig.getName())) {
                         DrawHelper.drawBorderedRect((double)(this.width - 125), (double)((float)(this.height - 119 + yDist) - this.scrollOffset), (double)(this.width + 125), (double)((float)(this.height - 107 + yDist) - this.scrollOffset), 0.6499999761581421D, (new Color(66, 66, 66, 105)).getRGB(), (new Color(0, 0, 0, 255)).getRGB(), true);
                    }

                    Minecraft var10000 = this.mc;
                    Minecraft.fontRendererObj.drawCenteredString(config.getName(), (float)this.width, (float)(this.height - 117 + yDist) - this.scrollOffset, color);
                    yDist += 15;
               }
          }

          GL11.glDisable(3089);
          GlStateManager.popMatrix();
          super.drawScreen(mouseX, mouseY, partialTicks);
     }

     protected void mouseReleased(int mouseX, int mouseY, int state) {
          super.mouseReleased(mouseX, mouseY, state);
     }

     protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
          search.mouseClicked(mouseX, mouseY, mouseButton);
          if (this.scrollOffset < 0.0F) {
               this.scrollOffset = 0.0F;
          }

          super.mouseClicked(mouseX, mouseY, mouseButton);
     }

     protected void keyTyped(char typedChar, int keyCode) throws IOException {
          Iterator var3 = Main.instance.configManager.getContents().iterator();

          while(var3.hasNext()) {
               Config config = (Config)var3.next();
               if (config != null) {
                    if (keyCode == 200) {
                         this.scrollOffset += 13.0F;
                    } else if (keyCode == 208) {
                         this.scrollOffset -= 13.0F;
                    }

                    if (this.scrollOffset < 0.0F) {
                         this.scrollOffset = 0.0F;
                    }
               }
          }

          search.textboxKeyTyped(typedChar, keyCode);
          search.setText(search.getText().replace(" ", ""));
          if ((typedChar == '\t' || typedChar == '\r') && search.isFocused()) {
               search.setFocused(!search.isFocused());
          }

          try {
               super.keyTyped(typedChar, keyCode);
          } catch (IOException var5) {
               var5.printStackTrace();
          }

          super.keyTyped(typedChar, keyCode);
     }

     public void onGuiClosed() {
          this.screenHelper.interpolate((float)this.width, (float)this.height, 2.0D * Minecraft.frameTime / 6.0D);
          selectedConfig = null;
          this.mc.entityRenderer.theShaderGroup = null;
          super.onGuiClosed();
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
}
