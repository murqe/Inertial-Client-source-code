package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class RecipeRepairItem implements IRecipe {
      public boolean matches(InventoryCrafting inv, World worldIn) {
            List list = Lists.newArrayList();

            for(int i = 0; i < inv.getSizeInventory(); ++i) {
                  ItemStack itemstack = inv.getStackInSlot(i);
                  if (!itemstack.func_190926_b()) {
                        list.add(itemstack);
                        if (list.size() > 1) {
                              ItemStack itemstack1 = (ItemStack)list.get(0);
                              if (itemstack.getItem() != itemstack1.getItem() || itemstack1.func_190916_E() != 1 || itemstack.func_190916_E() != 1 || !itemstack1.getItem().isDamageable()) {
                                    return false;
                              }
                        }
                  }
            }

            return list.size() == 2;
      }

      public ItemStack getCraftingResult(InventoryCrafting inv) {
            List list = Lists.newArrayList();

            ItemStack itemstack;
            for(int i = 0; i < inv.getSizeInventory(); ++i) {
                  itemstack = inv.getStackInSlot(i);
                  if (!itemstack.func_190926_b()) {
                        list.add(itemstack);
                        if (list.size() > 1) {
                              ItemStack itemstack1 = (ItemStack)list.get(0);
                              if (itemstack.getItem() != itemstack1.getItem() || itemstack1.func_190916_E() != 1 || itemstack.func_190916_E() != 1 || !itemstack1.getItem().isDamageable()) {
                                    return ItemStack.field_190927_a;
                              }
                        }
                  }
            }

            if (list.size() == 2) {
                  ItemStack itemstack2 = (ItemStack)list.get(0);
                  itemstack = (ItemStack)list.get(1);
                  if (itemstack2.getItem() == itemstack.getItem() && itemstack2.func_190916_E() == 1 && itemstack.func_190916_E() == 1 && itemstack2.getItem().isDamageable()) {
                        Item item = itemstack2.getItem();
                        int j = item.getMaxDamage() - itemstack2.getItemDamage();
                        int k = item.getMaxDamage() - itemstack.getItemDamage();
                        int l = j + k + item.getMaxDamage() * 5 / 100;
                        int i1 = item.getMaxDamage() - l;
                        if (i1 < 0) {
                              i1 = 0;
                        }

                        return new ItemStack(itemstack2.getItem(), 1, i1);
                  }
            }

            return ItemStack.field_190927_a;
      }

      public ItemStack getRecipeOutput() {
            return ItemStack.field_190927_a;
      }

      public NonNullList getRemainingItems(InventoryCrafting inv) {
            NonNullList nonnulllist = NonNullList.func_191197_a(inv.getSizeInventory(), ItemStack.field_190927_a);

            for(int i = 0; i < nonnulllist.size(); ++i) {
                  ItemStack itemstack = inv.getStackInSlot(i);
                  if (itemstack.getItem().hasContainerItem()) {
                        nonnulllist.set(i, new ItemStack(itemstack.getItem().getContainerItem()));
                  }
            }

            return nonnulllist;
      }

      public boolean func_192399_d() {
            return true;
      }

      public boolean func_194133_a(int p_194133_1_, int p_194133_2_) {
            return p_194133_1_ * p_194133_2_ >= 2;
      }
}
