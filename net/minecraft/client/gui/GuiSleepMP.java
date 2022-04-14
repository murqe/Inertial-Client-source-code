package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.CPacketEntityAction;

public class GuiSleepMP extends GuiChat {
      public void initGui() {
            super.initGui();
            this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height - 40, I18n.format("multiplayer.stopSleeping")));
      }

      protected void keyTyped(char typedChar, int keyCode) throws IOException {
            if (keyCode == 1) {
                  this.wakeFromSleep();
            } else if (keyCode != 28 && keyCode != 156) {
                  super.keyTyped(typedChar, keyCode);
            } else {
                  String s = this.inputField.getText().trim();
                  if (!s.isEmpty()) {
                        Minecraft var10000 = this.mc;
                        Minecraft.player.sendChatMessage(s);
                  }

                  this.inputField.setText("");
                  this.mc.ingameGUI.getChatGUI().resetScroll();
            }

      }

      protected void actionPerformed(GuiButton button) throws IOException {
            if (button.id == 1) {
                  this.wakeFromSleep();
            } else {
                  super.actionPerformed(button);
            }

      }

      private void wakeFromSleep() {
            Minecraft var10000 = this.mc;
            NetHandlerPlayClient nethandlerplayclient = Minecraft.player.connection;
            Minecraft var10003 = this.mc;
            nethandlerplayclient.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.STOP_SLEEPING));
      }
}
