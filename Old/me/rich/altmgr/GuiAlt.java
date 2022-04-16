package me.rich.altmgr;

import java.io.IOException;
import java.security.SecureRandom;
import me.rich.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

public final class GuiAlt extends GuiScreen {
      private PasswordField password;
      private final GuiScreen previousScreen;
      private AltLoginThread thread;
      private GuiTextField username;
      private final long initTime = System.currentTimeMillis();
      private static String alphabet = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890";
      private static final SecureRandom secureRandom = new SecureRandom();

      public GuiAlt(GuiScreen previousScreen) {
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
                  this.thread = new AltLoginThread("ShitUser" + randomString(3), "");
                  this.thread.start();
            }

      }

      public void drawScreen(int x2, int y2, float z2) {
            new ScaledResolution(this.mc);
            int lol = this.height / 4 + 24;
            this.mc.getTextureManager().bindTexture(new ResourceLocation("toperclient/1613224280_151-p-fon-sinii-kosmos-226.png"));
            Gui.drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, this.width, this.height, this.width, this.height, (float)this.width, (float)this.height);
            this.username.drawTextBox();
            this.password.drawTextBox();
            Fonts.roboto_16.drawCenteredString("Alt", (double)(this.width / 2 + -6), (double)lol, -1);
            Fonts.roboto_16.drawCenteredString(this.thread == null ? TextFormatting.GRAY + "" : this.thread.getStatus(), (double)(this.width / 2 - 5), (double)(lol + 98), -1);
            if (this.username.getText().isEmpty() && !this.username.isFocused()) {
                  Fonts.roboto_16.drawStringWithShadow("Type email.", (double)(this.width / 2 - 52), (double)lol + 24.5D, -7829368);
            }

            if (this.password.getText().isEmpty() && !this.password.isFocused()) {
                  Fonts.roboto_16.drawStringWithShadow("Type pass.", (double)(this.width / 2 - 52), (double)lol + 44.5D, -7829368);
            }

            super.drawScreen(x2, y2, z2);
      }

      public void initGui() {
            int lol = this.height / 4 + 24;
            this.buttonList.add(new GuiButton(0, this.width / 2 - 50, lol + 60, 90, 13, "Login"));
            this.buttonList.add(new GuiButton(2, this.width / 2 - 50, lol + 64 + 12, 90, 13, "Random name"));
            Minecraft var10004 = this.mc;
            this.username = new GuiTextField(lol, Minecraft.fontRendererObj, this.width / 2 - 55, lol + 20, 100, 13);
            Minecraft var10003 = this.mc;
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
