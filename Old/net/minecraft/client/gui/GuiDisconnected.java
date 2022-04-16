package net.minecraft.client.gui;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import me.rich.altmgr.AltLoginThread;
import me.rich.altmgr.GuiAlt;
import me.rich.helpers.other.ConnectingHelper;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;

public class GuiDisconnected extends GuiScreen {
      private final long initTime = System.currentTimeMillis();
      private final String reason;
      private final ITextComponent message;
      private List multilineMessage;
      private final GuiScreen parentScreen;
      private AltLoginThread thread;
      private int textHeight;

      public GuiDisconnected(GuiScreen screen, String reasonLocalizationKey, ITextComponent chatComp) {
            this.parentScreen = screen;
            this.reason = I18n.format(reasonLocalizationKey);
            this.message = chatComp;
      }

      protected void keyTyped(char typedChar, int keyCode) throws IOException {
      }

      public void initGui() {
            this.buttonList.clear();
            this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(), this.width - 50);
            this.textHeight = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
            this.buttonList.add(new GuiButton(0, this.width / 2 - 100, Math.min(this.height / 2 + this.textHeight / 2 + this.fontRendererObj.FONT_HEIGHT, this.height - 30), I18n.format("gui.toMenu")));
            this.buttonList.add(new GuiButton(1, this.width / 2 - 100, Math.min(this.height / 2 + this.textHeight / 2 + this.fontRendererObj.FONT_HEIGHT + 23, this.height - 30), I18n.format("Reconnect")));
      }

      protected void actionPerformed(GuiButton button) throws IOException {
            switch(button.id) {
            case 0:
                  this.mc.displayGuiScreen(this.parentScreen);
                  break;
            case 1:
                  this.mc.displayGuiScreen(new GuiConnecting(this, this.mc, ConnectingHelper.serverData));
                  break;
            case 2:
                  this.thread = new AltLoginThread("lmao" + GuiAlt.randomString(5), "");
                  this.thread.start();
            }

      }

      public void drawScreen(int mouseX, int mouseY, float partialTicks) {
            this.drawDefaultBackground();
            drawCenteredString(this.fontRendererObj, this.reason, this.width / 2, this.height / 2 - this.textHeight / 2 - this.fontRendererObj.FONT_HEIGHT * 2, 11184810);
            int i = this.height / 2 - this.textHeight / 2;
            if (this.multilineMessage != null) {
                  for(Iterator var5 = this.multilineMessage.iterator(); var5.hasNext(); i += this.fontRendererObj.FONT_HEIGHT) {
                        String s = (String)var5.next();
                        drawCenteredString(this.fontRendererObj, s, this.width / 2, i, 16777215);
                  }
            }

            super.drawScreen(mouseX, mouseY, partialTicks);
      }
}
