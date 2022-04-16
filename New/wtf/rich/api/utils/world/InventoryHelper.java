package wtf.rich.api.utils.world;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class InventoryHelper {
     private static final Minecraft mc = Minecraft.getMinecraft();
     static TimerHelper timer = new TimerHelper();

     public static int getItemCount(Container container, Item item) {
          int itemCount = 0;

          for(int i = 0; i < 45; ++i) {
               ItemStack is;
               if (container.getSlot(i).getHasStack() && (is = container.getSlot(i).getStack()).getItem() == item) {
                    itemCount += is.getMaxStackSize();
               }
          }

          return itemCount;
     }

     public static boolean isArmorPlayer(Entity entity) {
          Iterator var1 = entity.getArmorInventoryList().iterator();

          ItemStack stack;
          do {
               if (!var1.hasNext()) {
                    return true;
               }

               stack = (ItemStack)var1.next();
          } while(!stack.func_190926_b());

          return false;
     }

     public static int getTotemAtHotbar() {
          for(int i = 0; i < 45; ++i) {
               ItemStack itemStack = mc.player.inventoryContainer.getSlot(i).getStack();
               if (itemStack.getItem() == Items.TOTEM_OF_UNDYING) {
                    return i;
               }
          }

          return -1;
     }

     public static boolean doesHotbarHaveAxe() {
          for(int i = 0; i < 9; ++i) {
               mc.player.inventory.getStackInSlot(i);
               if (mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemAxe) {
                    return true;
               }
          }

          return false;
     }

     public static void attackEntity(Entity entity, boolean packet, boolean swingArm) {
          if (packet) {
               mc.player.connection.sendPacket(new CPacketUseEntity(entity));
          } else {
               mc.playerController.attackEntity(mc.player, entity);
          }

          if (swingArm) {
               mc.player.swingArm(EnumHand.MAIN_HAND);
          }

     }

     public static boolean hasAnyBlock() {
          for(int i = 0; i < 9; ++i) {
               Minecraft.getMinecraft();
               Item item = mc.player.inventory.getStackInSlot(i).getItem();
               if (item instanceof ItemBlock) {
                    return true;
               }
          }

          return false;
     }

     public static boolean isShiftable(ItemStack preferedItem) {
          if (preferedItem == null) {
               return true;
          } else {
               for(int o = 36; o < 45; ++o) {
                    if (!mc.player.inventoryContainer.getSlot(o).getHasStack()) {
                         return true;
                    }

                    ItemStack item = mc.player.inventoryContainer.getSlot(o).getStack();
                    if (item == null) {
                         return true;
                    }

                    if (Item.getIdFromItem(item.getItem()) == Item.getIdFromItem(preferedItem.getItem()) && item.getMaxStackSize() + preferedItem.getMaxStackSize() <= preferedItem.getMaxStackSize()) {
                         return true;
                    }
               }

               return false;
          }
     }

     public static boolean isPotion(ItemStack itemStack) {
          if (itemStack.getItem() instanceof ItemSplashPotion) {
               Iterator var1 = PotionUtils.getEffectsFromStack(itemStack).iterator();

               while(var1.hasNext()) {
                    PotionEffect effect = (PotionEffect)var1.next();
                    Potion potion = effect.getPotion();
                    effect.getPotion();
                    if (potion == Potion.getPotionById(5)) {
                         return true;
                    }
               }
          }

          return false;
     }

     public static void usePotion() {
          EnumHand hand = EnumHand.MAIN_HAND;
          ItemStack item = mc.player.getHeldItem(EnumHand.MAIN_HAND);
          if (item != null && mc.playerController.processRightClick(mc.player, mc.world, hand) == EnumActionResult.SUCCESS) {
          }

     }

     public static boolean isIntercepted(BlockPos pos) {
          Iterator var1 = Minecraft.getMinecraft().world.loadedEntityList.iterator();

          Entity entity;
          do {
               if (!var1.hasNext()) {
                    return false;
               }

               entity = (Entity)var1.next();
          } while(!(new AxisAlignedBB(pos)).intersectsWith(entity.getEntityBoundingBox()));

          return true;
     }

     public static int getSwordAtHotbar() {
          for(int i = 0; i < 9; ++i) {
               ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
               if (itemStack.getItem() instanceof ItemSword) {
                    return i;
               }
          }

          return 1;
     }

     public static int getAxe() {
          for(int i = 0; i < 9; ++i) {
               ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
               if (itemStack.getItem() instanceof ItemAxe) {
                    return i;
               }
          }

          return 1;
     }

     public static int getAnyBlockInHotbar() {
          for(int i = 0; i < 9; ++i) {
               Minecraft.getMinecraft();
               Item item = mc.player.inventory.getStackInSlot(i).getItem();
               if (item instanceof ItemBlock) {
                    return i;
               }
          }

          return -1;
     }

     public static int getArmorItemsEquipSlot(ItemStack stack, boolean equipmentSlot) {
          if (stack.getUnlocalizedName().contains("helmet")) {
               return equipmentSlot ? 4 : 5;
          } else if (stack.getUnlocalizedName().contains("chestplate")) {
               return equipmentSlot ? 3 : 6;
          } else if (stack.getUnlocalizedName().contains("leggings")) {
               return equipmentSlot ? 2 : 7;
          } else if (stack.getUnlocalizedName().contains("boots")) {
               return equipmentSlot ? 1 : 8;
          } else {
               return -1;
          }
     }

     public static boolean doesHotbarHaveBlock() {
          for(int i = 0; i < 9; ++i) {
               if (mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemBlock) {
                    return true;
               }
          }

          return false;
     }

     public static int getSwordH() {
          for(int i = 1; i < 9; ++i) {
               ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
               if (itemStack.getItem() instanceof ItemSword) {
                    return i;
               }
          }

          return 0;
     }

     public static int getSwordSlot() {
          int bestSword = -1;
          float bestDamage = 1.0F;

          for(int i = 9; i < 45; ++i) {
               if (mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                    ItemStack item = mc.player.inventoryContainer.getSlot(i).getStack();
                    if (item != null && item.getItem() instanceof ItemSword) {
                         ItemSword is = (ItemSword)item.getItem();
                         float damage = is.getDamageVsEntity();
                         if ((damage += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(16), item) * 1.26F + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(20), item) * 0.01F) > bestDamage) {
                              bestDamage = damage;
                              bestSword = i;
                         }
                    }
               }
          }

          return bestSword;
     }

     public static int getItemSlot(Container container, Item item) {
          int slot = 0;

          for(int i = 9; i < 45; ++i) {
               if (container.getSlot(i).getHasStack() && container.getSlot(i).getStack().getItem() == item) {
                    slot = i;
               }
          }

          return slot;
     }

     public static void swap(int slot, int hotbarNum) {
          mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, mc.player);
          mc.playerController.windowClick(mc.player.inventoryContainer.windowId, hotbarNum, 0, ClickType.PICKUP, mc.player);
          mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, mc.player);
          mc.playerController.updateController();
     }

     public static boolean isBestArmor(ItemStack stack, int type) {
          float prot = getProtection(stack);
          String strType = "";
          if (type == 1) {
               strType = "helmet";
          } else if (type == 2) {
               strType = "chestplate";
          } else if (type == 3) {
               strType = "leggings";
          } else if (type == 4) {
               strType = "boots";
          }

          if (!stack.getUnlocalizedName().contains(strType)) {
               return false;
          } else {
               for(int i = 5; i < 45; ++i) {
                    if (mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                         ItemStack is = mc.player.inventoryContainer.getSlot(i).getStack();
                         if (getProtection(is) > prot && is.getUnlocalizedName().contains(strType)) {
                              return false;
                         }
                    }
               }

               return true;
          }
     }

     public static float getProtection(ItemStack stack) {
          float prot = 0.0F;
          if (stack.getItem() instanceof ItemArmor) {
               ItemArmor armor = (ItemArmor)stack.getItem();
               prot = (float)((double)prot + (double)armor.damageReduceAmount + (double)((100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(0), stack)) * 0.0075D);
               prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(3), stack) / 100.0D);
               prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(1), stack) / 100.0D);
               prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(7), stack) / 100.0D);
               prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(34), stack) / 50.0D);
               prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(4), stack) / 100.0D);
          }

          return prot;
     }

     public static double getProtectionValue(ItemStack stack) {
          return !(stack.getItem() instanceof ItemArmor) ? 0.0D : (double)((ItemArmor)stack.getItem()).damageReduceAmount + (double)((100 - ((ItemArmor)stack.getItem()).damageReduceAmount * 4) * EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(0), stack) * 4) * 0.0075D;
     }
}
