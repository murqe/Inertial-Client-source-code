package me.rich.module.combat;

import de.Hero.settings.Setting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.helpers.world.TimerHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class AutoArmor extends Feature {
      public static Setting openInventory;
      private final Setting delay = new Setting("Delay", this, 1.0D, 0.0D, 10.0D, false);
      TimerHelper timerUtils = new TimerHelper();

      public AutoArmor() {
            super("AutoArmor", 0, Category.COMBAT);
            openInventory = new Setting("Open Inventory", this, true);
            Main.settingsManager.rSetting(this.delay);
            Main.settingsManager.rSetting(openInventory);
      }

      public static boolean isNullOrEmpty(ItemStack stack) {
            return stack == null || stack.isEmpty();
      }

      @EventTarget
      public void onUpdate(EventUpdate event) {
            this.setSuffix("" + this.delay.getValFloat());
            if (mc.currentScreen instanceof GuiInventory || !openInventory.getValBoolean()) {
                  if (!(mc.currentScreen instanceof GuiContainer) || mc.currentScreen instanceof InventoryEffectRenderer) {
                        InventoryPlayer inventory = Minecraft.player.inventory;
                        int[] bestArmorSlots = new int[4];
                        int[] bestArmorValues = new int[4];

                        int slot;
                        ItemStack stack;
                        ItemArmor item;
                        for(slot = 0; slot < 4; ++slot) {
                              bestArmorSlots[slot] = -1;
                              stack = inventory.armorItemInSlot(slot);
                              if (!isNullOrEmpty(stack) && stack.getItem() instanceof ItemArmor) {
                                    item = (ItemArmor)stack.getItem();
                                    bestArmorValues[slot] = this.getArmorValue(item, stack);
                              }
                        }

                        int j;
                        for(slot = 0; slot < 36; ++slot) {
                              stack = inventory.getStackInSlot(slot);
                              if (!isNullOrEmpty(stack) && stack.getItem() instanceof ItemArmor) {
                                    item = (ItemArmor)stack.getItem();
                                    j = item.armorType.getIndex();
                                    int armorValue = this.getArmorValue(item, stack);
                                    if (armorValue > bestArmorValues[j]) {
                                          bestArmorSlots[j] = slot;
                                          bestArmorValues[j] = armorValue;
                                    }
                              }
                        }

                        ArrayList types = new ArrayList(Arrays.asList(0, 1, 2, 3));
                        Collections.shuffle(types);
                        Iterator var11 = types.iterator();

                        while(var11.hasNext()) {
                              int i = (Integer)var11.next();
                              j = bestArmorSlots[i];
                              if (j != -1) {
                                    ItemStack oldArmor = inventory.armorItemInSlot(i);
                                    if (isNullOrEmpty(oldArmor) || inventory.getFirstEmptyStack() != -1) {
                                          if (j < 9) {
                                                j += 36;
                                          }

                                          if (this.timerUtils.hasReached((double)(this.delay.getValFloat() * 100.0F))) {
                                                if (!isNullOrEmpty(oldArmor)) {
                                                      mc.playerController.windowClick(0, 8 - i, 0, ClickType.QUICK_MOVE, Minecraft.player);
                                                }

                                                mc.playerController.windowClick(0, j, 0, ClickType.QUICK_MOVE, Minecraft.player);
                                                this.timerUtils.reset();
                                          }
                                          break;
                                    }
                              }
                        }

                  }
            }
      }

      private int getArmorValue(ItemArmor item, ItemStack stack) {
            int armorPoints = item.damageReduceAmount;
            int prtPoints = false;
            int armorToughness = (int)item.toughness;
            int armorType = item.getArmorMaterial().getDamageReductionAmount(EntityEquipmentSlot.LEGS);
            Enchantment protection = Enchantments.PROTECTION;
            int prtLvl = EnchantmentHelper.getEnchantmentLevel(protection, stack);
            DamageSource dmgSource = DamageSource.causePlayerDamage(Minecraft.player);
            int prtPoints = protection.calcModifierDamage(prtLvl, dmgSource);
            return armorPoints * 5 + prtPoints * 3 + armorToughness + armorType;
      }
}
