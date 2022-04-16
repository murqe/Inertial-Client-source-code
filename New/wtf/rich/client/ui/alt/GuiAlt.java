package wtf.rich.client.ui.alt;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.api.utils.render.glsandbox.animbackground;
import wtf.rich.client.features.impl.display.MainMenu;

public final class GuiAlt extends GuiScreen {
     private ResourceLocation resourceLocation;
     private PasswordField password;
     private final GuiScreen previousScreen;
     private AltLoginThread thread;
     private GuiTextField username;
     private final long initTime = System.currentTimeMillis();
     private static String alphabet = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890";
     private static final SecureRandom secureRandom = new SecureRandom();
     private final animbackground backgroundShader;
     private final animbackground backgroundShaders;
     private final animbackground backgroundShaderd;
     private final animbackground backgroundShaderh;
     private final animbackground backgroundShaderj;
     private final animbackground backgroundShaderk;
     private final animbackground backgroundShaderl;

     public GuiAlt(GuiScreen previousScreen) {
          try {
               this.backgroundShader = new animbackground("/minecraft.fsh");
               this.backgroundShaders = new animbackground("/sky.fsh");
               this.backgroundShaderd = new animbackground("/ukraine.fsh");
               this.backgroundShaderh = new animbackground("/pisulka.fsh");
               this.backgroundShaderj = new animbackground("/svastika.fsh");
               this.backgroundShaderk = new animbackground("/skydrag.fsh");
               this.backgroundShaderl = new animbackground("/noise.fsh");
          } catch (IOException var3) {
               throw new IllegalStateException("Failed to load backgound shader", var3);
          }

          this.previousScreen = previousScreen;
     }

     public static String randomString(int strLength) {
          StringBuilder stringBuilder = new StringBuilder(strLength);

          for(int i = 0; i < strLength; ++i) {
               stringBuilder.append(alphabet.charAt(secureRandom.nextInt(alphabet.length())));
          }

          return stringBuilder.toString();
     }

     protected void actionPerformed(GuiButton button) {
          switch(button.id) {
          case 0:
               this.thread = new AltLoginThread(this.username.getText(), this.password.getText());
               this.thread.start();
               break;
          case 2:
               this.thread = new AltLoginThread("Inertial" + randomString(5), "");
               this.thread.start();
          }

     }

     private void getDownloadImageSkin(ResourceLocation resourceLocationIn, String username) {
          TextureManager textureManager = this.mc.getTextureManager();
          textureManager.getTexture(resourceLocationIn);
          ThreadDownloadImageData textureObject = new ThreadDownloadImageData((File)null, String.format("https://minotar.net/avatar/%s/64.png", StringUtils.stripControlCodes(username)), DefaultPlayerSkin.getDefaultSkin(AbstractClientPlayer.getOfflineUUID(username)), new ImageBufferDownload());
          textureManager.loadTexture(resourceLocationIn, textureObject);
     }

     public void drawScreen(int x2, int y2, float z2) {
          ScaledResolution sr = new ScaledResolution(this.mc);
          GlStateManager.disableCull();
          if (MainMenu.mainMenu.currentMode.equals("Minecraft")) {
               this.backgroundShader.useShader(sr.getScaledWidth(), sr.getScaledHeight(), (float)x2, (float)y2, (float)(System.currentTimeMillis() - this.initTime) / 1000.0F);
          }

          if (MainMenu.mainMenu.currentMode.equals("Sky")) {
               this.backgroundShaders.useShader(sr.getScaledWidth(), sr.getScaledHeight(), (float)x2, (float)y2, (float)(System.currentTimeMillis() - this.initTime) / 1000.0F);
          }

          if (MainMenu.mainMenu.currentMode.equals("Ukraine")) {
               this.backgroundShaderd.useShader(sr.getScaledWidth(), sr.getScaledHeight(), (float)x2, (float)y2, (float)(System.currentTimeMillis() - this.initTime) / 1000.0F);
          }

          if (MainMenu.mainMenu.currentMode.equals("Cocks")) {
               this.backgroundShaderh.useShader(sr.getScaledWidth(), sr.getScaledHeight(), (float)x2, (float)y2, (float)(System.currentTimeMillis() - this.initTime) / 1000.0F);
          }

          if (MainMenu.mainMenu.currentMode.equals("Svaston")) {
               this.backgroundShaderj.useShader(sr.getScaledWidth() + 1000, sr.getScaledHeight() + 400, (float)x2, (float)y2, (float)(System.currentTimeMillis() - this.initTime) / 1000.0F);
          }

          if (MainMenu.mainMenu.currentMode.equals("SkyDrag")) {
               this.backgroundShaderk.useShader(sr.getScaledWidth() + 1000, sr.getScaledHeight() + 400, (float)x2, (float)y2, (float)(System.currentTimeMillis() - this.initTime) / 1000.0F);
          }

          if (MainMenu.mainMenu.currentMode.equals("Rainbow")) {
               this.backgroundShaderl.useShader(sr.getScaledWidth() + 1000, sr.getScaledHeight() + 400, (float)x2, (float)y2, (float)(System.currentTimeMillis() - this.initTime) / 1000.0F);
          }

          GL11.glBegin(7);
          GL11.glVertex2f(-1.0F, -1.0F);
          GL11.glVertex2f(-1.0F, 1.0F);
          GL11.glVertex2f(1.0F, 1.0F);
          GL11.glVertex2f(1.0F, -1.0F);
          GL11.glEnd();
          GL20.glUseProgram(0);
          GlStateManager.disableCull();
          int lol = this.height / 4 + 24;
          DrawHelper.drawRectWithGlow((double)(this.width / 2 - 120), (double)(lol - 10), (double)(this.width / 2 + 110), (double)(lol + 110), 5.0D, 3.0D, new Color(0, 0, 0, 42));
          this.username.drawTextBoxalt();
          this.password.drawTextBox();
          float var10002 = (float)(this.width / 2 + -6);
          Minecraft.getMinecraft().neverlose500_18.drawCenteredString("AltManager", var10002, (float)lol, -1);
          Minecraft.getMinecraft().neverlose500_15.drawCenteredString(this.thread == null ? TextFormatting.GRAY + "" : this.thread.getStatus(), (float)(this.width / 2 - 5), (float)(lol + 98), -1);
          double var6;
          if (this.username.getText().isEmpty() && !this.username.isFocused()) {
               var6 = (double)(this.width / 2 - 52);
               Minecraft.getMinecraft().neverlose500_16.drawStringWithShadow("Email", var6, (double)(lol + 24), -7829368);
          }

          if (this.password.getText().isEmpty() && !this.password.isFocused()) {
               var6 = (double)(this.width / 2 - 52);
               Minecraft.getMinecraft().neverlose500_16.drawStringWithShadow("Pass", var6, (double)(lol + 44), -7829368);
          }

          GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
          this.resourceLocation = AbstractClientPlayer.getLocationSkin(this.mc.session.getUsername());
          this.getDownloadImageSkin(this.resourceLocation, this.mc.session.getUsername());
          this.mc.getTextureManager().bindTexture(this.resourceLocation);
          Gui.drawScaledCustomSizeModalRect(this.width / 2 - 99, lol + 22, 8.0F, 8.0F, 8, 8, 30, 30, 64.0F, 64.0F);
          super.drawScreen(x2, y2, z2);
     }

     public void initGui() {
          int lol = this.height / 4 + 24;
          this.buttonList.add(new GuiButton(0, this.width / 2 - 50, lol + 60, 90, 13, "Login"));
          this.buttonList.add(new GuiButton(2, this.width / 2 - 50, lol + 64 + 12, 90, 13, "Random name"));
          this.username = new GuiTextField(lol, Minecraft.fontRendererObj, this.width / 2 - 55, lol + 20, 100, 13);
          this.password = new PasswordField(Minecraft.fontRendererObj, this.width / 2 - 55, lol + 40, 100, 13);
          this.username.setFocused(true);
          Keyboard.enableRepeatEvents(true);
     }

     protected void keyTyped(char character, int key) {
          try {
               super.keyTyped(character, key);
          } catch (IOException var4) {
          }

          if (character == '\t') {
               if (!this.username.isFocused() && !this.password.isFocused()) {
                    this.username.setFocused(true);
               } else {
                    this.username.setFocused(this.password.isFocused());
                    this.password.setFocused(!this.username.isFocused());
               }
          }

          if (character == '\r') {
               this.actionPerformed((GuiButton)this.buttonList.get(0));
          }

          this.username.textboxKeyTyped(character, key);
          this.password.textboxKeyTyped(character, key);
     }

     protected void mouseClicked(int x2, int y2, int button) {
          try {
               super.mouseClicked(x2, y2, button);
          } catch (IOException var5) {
               var5.printStackTrace();
          }

          this.username.mouseClicked(x2, y2, button);
          this.password.mouseClicked(x2, y2, button);
     }

     public void onGuiClosed() {
          Keyboard.enableRepeatEvents(false);
     }

     public void updateScreen() {
          this.username.updateCursorCounter();
          this.password.updateCursorCounter();
     }

     static {
          alphabet = alphabet + alphabet.toLowerCase();
     }
}
