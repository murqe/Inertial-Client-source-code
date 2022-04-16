package clickgui;

import clickgui.panel.Panel;
import com.google.common.collect.Lists;
import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import me.rich.Main;
import me.rich.helpers.other.ChatUtils;
import me.rich.helpers.render.Translate;
import me.rich.module.Category;
import me.rich.module.render.ClickGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public final class ClickGuiScreen extends GuiScreen {
      private static ClickGuiScreen INSTANCE;
      private final List panels = Lists.newArrayList();
      public Translate translate;
      public static double scaling;
      private float curAlpha;

      public ClickGuiScreen() {
            Category[] category = Category.values();
            scaling = 0.0D;

            for(int i = category.length - 1; i >= 0; --i) {
                  this.panels.add(new Panel(category[i], 5 + 110 * i, 10));
                  this.translate = new Translate(0.0F, 0.0F);
            }

      }

      public void drawScreen(int mouseX, int mouseY, float partialTicks) {
            int i = 0;
            if (Minecraft.player != null && this.mc.world != null) {
                  new ScaledResolution(Minecraft.getMinecraft());
                  float alpha = 150.0F;
                  int step = (int)(alpha / 100.0F);
                  if (this.curAlpha < alpha - (float)step) {
                        this.curAlpha += (float)step;
                  } else if (this.curAlpha > alpha - (float)step && this.curAlpha != alpha) {
                        this.curAlpha = (float)((int)alpha);
                  } else if (this.curAlpha != alpha) {
                        this.curAlpha = (float)((int)alpha);
                  }

                  for(int panelsSize = this.panels.size(); i < panelsSize; ++i) {
                        ((Panel)this.panels.get(i)).onDraw(mouseX, mouseY);
                        this.updateMouseWheel();
                  }
            }

      }

      public void updateMouseWheel() {
            int scrollWheel = Mouse.getDWheel();
            int panelsSize = this.panels.size();

            for(int i = 0; i < panelsSize; ++i) {
                  if (scrollWheel < 0) {
                        ((Panel)this.panels.get(i)).setY(((Panel)this.panels.get(i)).getY() - 15);
                  } else if (scrollWheel > 0) {
                        ((Panel)this.panels.get(i)).setY(((Panel)this.panels.get(i)).getY() + 15);
                  }
            }

      }

      protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
            new ScaledResolution(this.mc);
            int panelsSize = this.panels.size();

            for(int i = 0; i < panelsSize; ++i) {
                  ((Panel)this.panels.get(i)).onMouseClick(mouseX, mouseY, mouseButton);
            }

            super.mouseClicked(mouseX, mouseY, mouseButton);
      }

      protected void mouseReleased(int mouseX, int mouseY, int state) {
            int panelsSize = this.panels.size();

            for(int i = 0; i < panelsSize; ++i) {
                  ((Panel)this.panels.get(i)).onMouseRelease(mouseX, mouseY, state);
            }

      }

      public void loadGui() {
            try {
                  File file1 = new File(this.mc.mcDataDir + File.separator + "WintWare/cfgs");
                  File file = new File(file1, "clickgui.cfg");
                  if (!file1.exists()) {
                        file1.mkdirs();
                  }

                  FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
                  DataInputStream in = new DataInputStream(fstream);
                  BufferedReader br = new BufferedReader(new InputStreamReader(in));

                  String line;
                  while((line = br.readLine()) != null) {
                        String readString = line.trim();
                        String[] split = readString.split(":");
                        Iterator var9 = SettingsManager.getSettings().iterator();

                        while(var9.hasNext()) {
                              Setting s = (Setting)var9.next();
                              if (s.getName().equals(split[0])) {
                                    s.setValString(split[1]);
                                    s.setValBoolean(Boolean.valueOf(split[2]));
                                    s.setValDouble((double)Float.valueOf(split[3]));
                              }
                        }
                  }

                  br.close();
            } catch (Exception var11) {
            }

      }

      public void saveGui() {
            try {
                  File file1 = new File(this.mc.mcDataDir + File.separator + "ToperClient/cfgs");
                  File file = new File(file1, "clickgui.cfg");
                  if (!file1.exists()) {
                        file1.mkdirs();
                  }

                  BufferedWriter out = new BufferedWriter(new FileWriter(file));
                  new FileInputStream(file.getAbsolutePath());
                  out.write("Config:Default");
                  out.write("\r\n");
                  Iterator var5 = SettingsManager.getSettings().iterator();

                  while(var5.hasNext()) {
                        Setting s = (Setting)var5.next();
                        if (!s.getName().equals("Configs")) {
                              out.write(s.getName() + ":" + s.getValString() + ":" + s.getValBoolean() + ":" + s.getValDouble());
                              out.write("\r\n");
                        }
                  }

                  out.close();
            } catch (Exception var7) {
                  ChatUtils.addChatMessage("Failed to save configs!");
            }

      }

      public void onGuiClosed() {
            if (this.mc.entityRenderer.isShaderActive()) {
                  this.mc.entityRenderer.theShaderGroup = null;
            }

      }

      public void initGui() {
            if (Main.settingsManager.getSettingByName(Main.moduleManager.getModule(ClickGUI.class), "Blur").getValBoolean() && !this.mc.gameSettings.ofFastRender && !this.mc.entityRenderer.isShaderActive()) {
                  this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
            }

      }

      protected void keyTyped(char typedChar, int keyCode) throws IOException {
            int panelsSize = this.panels.size();

            for(int i = 0; i < panelsSize; ++i) {
                  ((Panel)this.panels.get(i)).onKeyPress(typedChar, keyCode);
            }

            super.keyTyped(typedChar, keyCode);
      }

      public static ClickGuiScreen getInstance() {
            if (INSTANCE == null) {
                  INSTANCE = new ClickGuiScreen();
            }

            return INSTANCE;
      }
}
