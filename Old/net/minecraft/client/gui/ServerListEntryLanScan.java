package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class ServerListEntryLanScan implements GuiListExtended.IGuiListEntry {
      private final Minecraft mc = Minecraft.getMinecraft();

      public void func_192634_a(int p_192634_1_, int p_192634_2_, int p_192634_3_, int p_192634_4_, int p_192634_5_, int p_192634_6_, int p_192634_7_, boolean p_192634_8_, float p_192634_9_) {
            int var10000 = p_192634_3_ + p_192634_5_ / 2;
            Minecraft var10001 = this.mc;
            int i = var10000 - Minecraft.fontRendererObj.FONT_HEIGHT / 2;
            Minecraft var12 = this.mc;
            FontRenderer var13 = Minecraft.fontRendererObj;
            String var14 = I18n.format("lanServer.scanning");
            int var10002 = this.mc.currentScreen.width / 2;
            Minecraft var10003 = this.mc;
            var13.drawString(var14, var10002 - Minecraft.fontRendererObj.getStringWidth(I18n.format("lanServer.scanning")) / 2, i, 16777215);
            String s;
            switch((int)(Minecraft.getSystemTime() / 300L % 4L)) {
            case 0:
            default:
                  s = "O o o";
                  break;
            case 1:
            case 3:
                  s = "o O o";
                  break;
            case 2:
                  s = "o o O";
            }

            var12 = this.mc;
            var13 = Minecraft.fontRendererObj;
            var10002 = this.mc.currentScreen.width / 2;
            var10003 = this.mc;
            var10002 -= Minecraft.fontRendererObj.getStringWidth(s) / 2;
            Minecraft var10004 = this.mc;
            var13.drawString(s, var10002, i + Minecraft.fontRendererObj.FONT_HEIGHT, 8421504);
      }

      public void func_192633_a(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_) {
      }

      public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY) {
            return false;
      }

      public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
      }
}
