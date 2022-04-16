package wtf.rich.client.features.impl.visuals;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.Event2D;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;

public class ArmorHUD extends Feature {
     private static final RenderItem itemRender;

     public ArmorHUD() {
          super("ArmorHUD", "Показывает прочность предметов, которые в данный момент одеты", 0, Category.VISUALS);
     }

     @EventTarget
     public void onRender2D(Event2D event) {
          GlStateManager.enableTexture2D();
          ScaledResolution resolution = new ScaledResolution(mc);
          int i = resolution.getScaledWidth() / 2;
          int iteration = 0;
          int y = resolution.getScaledHeight() - 65 - (mc.player.isInWater() ? 10 : 0);
          Iterator var6 = mc.player.inventory.armorInventory.iterator();

          while(var6.hasNext()) {
               ItemStack is = (ItemStack)var6.next();
               ++iteration;
               if (!is.func_190926_b()) {
                    int x = i - 90 + (9 - iteration) * 20 + 2;
                    GlStateManager.enableDepth();
                    itemRender.zLevel = 200.0F;
                    itemRender.renderItemAndEffectIntoGUI(is, x, y);
                    Minecraft var10001 = mc;
                    itemRender.renderItemOverlayIntoGUI(Minecraft.fontRendererObj, is, x, y, "");
                    itemRender.zLevel = 0.0F;
                    GlStateManager.enableTexture2D();
                    GlStateManager.disableLighting();
                    GlStateManager.disableDepth();
                    String s = is.func_190916_E() > 1 ? is.func_190916_E() + "" : "";
                    mc.neverlose500_14.drawStringWithShadow(s, (double)(x + 19 - 2 - mc.neverlose500_14.getStringWidth(s)), (double)(y + 20), 16777215);
                    int green = Math.abs(is.getMaxDamage() - is.getItemDamage());
                    mc.neverlose500_14.drawStringWithShadow(green + "", (double)(x + 8 - mc.neverlose500_14.getStringWidth(green + "") / 2), (double)(y - -18), -1);
               }
          }

          GlStateManager.enableDepth();
          GlStateManager.disableLighting();
     }

     static {
          Minecraft var10000 = mc;
          itemRender = Minecraft.getMinecraft().getRenderItem();
     }
}
