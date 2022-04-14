package net.minecraft.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiChest extends GuiContainer {
      private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
      private final IInventory upperChestInventory;
      private final IInventory lowerChestInventory;
      private final int inventoryRows;

      public GuiChest(IInventory upperInv, IInventory lowerInv) {
            Minecraft.getMinecraft();
            super(new ContainerChest(upperInv, lowerInv, Minecraft.player));
            this.upperChestInventory = upperInv;
            this.lowerChestInventory = lowerInv;
            this.allowUserInput = false;
            int i = true;
            int j = true;
            this.inventoryRows = lowerInv.getSizeInventory() / 9;
            this.ySize = 114 + this.inventoryRows * 18;
      }

      public void drawScreen(int mouseX, int mouseY, float partialTicks) {
            this.drawDefaultBackground();
            super.drawScreen(mouseX, mouseY, partialTicks);
            this.func_191948_b(mouseX, mouseY);
      }

      protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
            this.fontRendererObj.drawString(this.lowerChestInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
            this.fontRendererObj.drawString(this.upperChestInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
      }

      protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);
            int i = (this.width - this.xSize) / 2;
            int j = (this.height - this.ySize) / 2;
            this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
            this.drawTexturedModalRect(i, j + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
      }
}
