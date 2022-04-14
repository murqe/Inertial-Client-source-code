package me.rich.module.render;

import java.util.Iterator;
import me.rich.event.EventTarget;
import me.rich.event.events.EventRender2D;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

public class ArmorHUD extends Feature {
      public boolean on;
      private static final RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();

      public ArmorHUD() {
            super("ArmorHUD", 0, Category.RENDER);
      }

      @EventTarget
      public void renderOverlay(EventRender2D event) {
            if (this.on) {
                  GlStateManager.enableTexture2D();
                  ScaledResolution resolution = new ScaledResolution(mc);
                  int i = resolution.getScaledWidth() / 2;
                  int iteration = 0;
                  int var10000 = resolution.getScaledHeight() - 55;
                  Minecraft var10001 = mc;
                  int y = var10000 - (Minecraft.player.isInWater() ? 10 : 0);
                  Iterator var6 = Minecraft.player.inventory.armorInventory.iterator();

                  while(var6.hasNext()) {
                        ItemStack is = (ItemStack)var6.next();
                        ++iteration;
                        if (!is.func_190926_b()) {
                              int x = i - 90 + (9 - iteration) * 24 - 25;
                              GlStateManager.enableDepth();
                              itemRender.zLevel = 200.0F;
                              itemRender.renderItemAndEffectIntoGUI(is, x, y);
                              GlStateManager.enableTexture2D();
                              GlStateManager.disableLighting();
                              GlStateManager.disableDepth();
                              float green = ((float)is.getMaxDamage() - (float)is.getItemDamage()) / (float)is.getMaxDamage();
                              float red = 1.0F - green;
                              int dmg = 100 - (int)(red * 100.0F);
                              Minecraft var12 = mc;
                              FontRenderer var13 = Minecraft.fontRendererObj;
                              String var14 = dmg + "%";
                              int var10002 = x + 8;
                              Minecraft var10003 = mc;
                              var13.drawStringWithShadow(var14, (float)(var10002 - Minecraft.fontRendererObj.getStringWidth(dmg + "%") / 2), (float)(y - 8), -1);
                        }
                  }

                  GlStateManager.enableDepth();
                  GlStateManager.disableLighting();
            }

      }

      public void onEnable() {
            super.onEnable();
            this.on = true;
      }

      public void onDisable() {
            super.onDisable();
            this.on = false;
      }
}
