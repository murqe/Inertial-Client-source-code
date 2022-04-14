package net.minecraft.client.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.util.text.TextFormatting;

public abstract class GuiResourcePackList extends GuiListExtended {
      protected final Minecraft mc;
      protected final List resourcePackEntries;

      public GuiResourcePackList(Minecraft mcIn, int p_i45055_2_, int p_i45055_3_, List p_i45055_4_) {
            super(mcIn, p_i45055_2_, p_i45055_3_, 32, p_i45055_3_ - 55 + 4, 36);
            this.mc = mcIn;
            this.resourcePackEntries = p_i45055_4_;
            this.centerListVertically = false;
            this.setHasListHeader(true, (int)((float)Minecraft.fontRendererObj.FONT_HEIGHT * 1.5F));
      }

      protected void drawListHeader(int insideLeft, int insideTop, Tessellator tessellatorIn) {
            String s = TextFormatting.UNDERLINE + "" + TextFormatting.BOLD + this.getListHeader();
            Minecraft var10000 = this.mc;
            int var10002 = insideLeft + this.width / 2;
            Minecraft var10003 = this.mc;
            Minecraft.fontRendererObj.drawString(s, var10002 - Minecraft.fontRendererObj.getStringWidth(s) / 2, Math.min(this.top + 3, insideTop), 16777215);
      }

      protected abstract String getListHeader();

      public List getList() {
            return this.resourcePackEntries;
      }

      protected int getSize() {
            return this.getList().size();
      }

      public ResourcePackListEntry getListEntry(int index) {
            return (ResourcePackListEntry)this.getList().get(index);
      }

      public int getListWidth() {
            return this.width;
      }

      protected int getScrollBarX() {
            return this.right - 6;
      }
}
