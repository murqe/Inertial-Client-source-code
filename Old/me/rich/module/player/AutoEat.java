package me.rich.module.player;

import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.helpers.Wrapper;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.block.BlockContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;

public class AutoEat extends Feature {
      public AutoEat() {
            super("AutoEat", 0, Category.PLAYER);
      }

      @EventTarget
      public void onUpdate(EventUpdate event) {
            ItemStack curStack = Minecraft.player.inventory.getCurrentItem();
            if (!this.shouldEat()) {
                  Wrapper.getMinecraft().gameSettings.keyBindUseItem.pressed = false;
            } else {
                  FoodStats foodStats = Minecraft.player.getFoodStats();
                  this.eatFood();
            }
      }

      public void onDisable() {
            Wrapper.getMinecraft().gameSettings.keyBindUseItem.pressed = false;
      }

      private void eatFood() {
            for(int slot = 44; slot >= 9; --slot) {
                  ItemStack stack = Wrapper.getPlayer().inventoryContainer.getSlot(slot).getStack();
                  if (stack != null) {
                        if (slot >= 36 && slot <= 44) {
                              if (stack.getItem() instanceof ItemFood && !(stack.getItem() instanceof ItemAppleGold)) {
                                    Wrapper.getPlayer().inventory.currentItem = slot - 36;
                                    Wrapper.getMinecraft().gameSettings.keyBindUseItem.pressed = true;
                                    return;
                              }
                        } else if (stack.getItem() instanceof ItemFood && !(stack.getItem() instanceof ItemAppleGold)) {
                              int currentSlot = Wrapper.getPlayer().inventory.currentItem + 36;
                              Wrapper.getMinecraft().playerController.windowClick(0, slot, 0, ClickType.PICKUP, Wrapper.getPlayer());
                              Wrapper.getMinecraft().playerController.windowClick(0, currentSlot, 0, ClickType.PICKUP, Wrapper.getPlayer());
                              Wrapper.getMinecraft().playerController.windowClick(0, slot, 0, ClickType.PICKUP, Wrapper.getPlayer());
                              return;
                        }
                  }
            }

      }

      private boolean shouldEat() {
            if (!Wrapper.getPlayer().canEat(false)) {
                  return false;
            } else if (Wrapper.getMinecraft().currentScreen != null) {
                  return false;
            } else {
                  if (Wrapper.getMinecraft().objectMouseOver != null) {
                        Entity entity = Wrapper.getMinecraft().objectMouseOver.entityHit;
                        if (entity instanceof EntityVillager || entity instanceof EntityTameable) {
                              return false;
                        }

                        if (Wrapper.getWorld().getBlockState(Wrapper.getMinecraft().objectMouseOver.getBlockPos()).getBlock() instanceof BlockContainer) {
                              return false;
                        }
                  }

                  return true;
            }
      }
}
