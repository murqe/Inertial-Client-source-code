package wtf.rich.client.features.impl.combat;

import java.util.Iterator;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventRender2D;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.api.utils.world.InventoryHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class AutoTotem extends Feature {
     public static NumberSetting health;
     public static BooleanSetting countTotem;
     public static BooleanSetting checkCrystal;
     public static BooleanSetting inventoryOnly;
     public static NumberSetting radiusCrystal;

     public AutoTotem() {
          super("AutoTotem", "Автоматически берет в руку тотем при опредленном здоровье", 0, Category.COMBAT);
          health = new NumberSetting("Health Amount", 10.0F, 1.0F, 20.0F, 0.5F, () -> {
               return true;
          });
          inventoryOnly = new BooleanSetting("Only Inventory", false, () -> {
               return true;
          });
          countTotem = new BooleanSetting("Count Totem", true, () -> {
               return true;
          });
          checkCrystal = new BooleanSetting("Check Crystal", true, () -> {
               return true;
          });
          radiusCrystal = new NumberSetting("Distance to Crystal", 6.0F, 1.0F, 8.0F, 1.0F, () -> {
               return checkCrystal.getBoolValue();
          });
          this.addSettings(new Setting[]{health, inventoryOnly, countTotem, checkCrystal, radiusCrystal});
     }

     private int fountTotemCount() {
          int count = 0;

          for(int i = 0; i < mc.player.inventory.getSizeInventory(); ++i) {
               ItemStack stack = mc.player.inventory.getStackInSlot(i);
               if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
                    ++count;
               }
          }

          return count;
     }

     private boolean checkCrystal() {
          Iterator var1 = mc.world.loadedEntityList.iterator();

          Entity entity;
          do {
               if (!var1.hasNext()) {
                    return false;
               }

               entity = (Entity)var1.next();
          } while(!(entity instanceof EntityEnderCrystal) || mc.player.getDistanceToEntity(entity) > radiusCrystal.getNumberValue());

          return true;
     }

     @EventTarget
     public void onRender2D(EventRender2D event) {
          if (this.fountTotemCount() > 0 && countTotem.getBoolValue()) {
               mc.sfui18.drawStringWithShadow(this.fountTotemCount() + "", (double)((float)event.getResolution().getScaledWidth() / 2.0F + 19.0F), (double)((float)event.getResolution().getScaledHeight() / 2.0F), -1);

               for(int i = 0; i < mc.player.inventory.getSizeInventory(); ++i) {
                    ItemStack stack = mc.player.inventory.getStackInSlot(i);
                    if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
                         GlStateManager.pushMatrix();
                         GlStateManager.disableBlend();
                         mc.getRenderItem().renderItemAndEffectIntoGUI(stack, event.getResolution().getScaledWidth() / 2 + 4, event.getResolution().getScaledHeight() / 2 - 7);
                         GlStateManager.popMatrix();
                    }
               }
          }

     }

     @EventTarget
     public void onUpdate(EventUpdate event) {
          if (!inventoryOnly.getBoolValue() || mc.currentScreen instanceof GuiInventory) {
               if (mc.player.getHealth() <= health.getNumberValue()) {
                    if (mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING && InventoryHelper.getTotemAtHotbar() != -1) {
                         mc.playerController.windowClick(0, InventoryHelper.getTotemAtHotbar(), 1, ClickType.PICKUP, mc.player);
                         mc.playerController.windowClick(0, 45, 1, ClickType.PICKUP, mc.player);
                    }

                    if (this.checkCrystal() && checkCrystal.getBoolValue() && mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING && InventoryHelper.getTotemAtHotbar() != -1) {
                         mc.playerController.windowClick(0, InventoryHelper.getTotemAtHotbar(), 1, ClickType.PICKUP, mc.player);
                         mc.playerController.windowClick(0, 45, 1, ClickType.PICKUP, mc.player);
                    }

               }
          }
     }
}
