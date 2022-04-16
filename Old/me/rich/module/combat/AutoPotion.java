package me.rich.module.combat;

import java.util.Iterator;
import java.util.Objects;
import me.rich.event.EventTarget;
import me.rich.event.events.EventPreMotionUpdate;
import me.rich.helpers.world.TimerHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumHand;

public class AutoPotion extends Feature {
      ItemStack held;
      TimerHelper counter = new TimerHelper();

      public AutoPotion() {
            super("AutoPot", 0, Category.COMBAT);
      }

      @EventTarget
      public void onUpdate(EventPreMotionUpdate event) {
            if (this.held == null && this.counter.hasReached(100.0D)) {
                  Minecraft var10001 = mc;
                  this.held = Minecraft.player.getHeldItemMainhand();
            }

            if (this.isPotionOnHotBar()) {
                  Minecraft var10000 = mc;
                  if (Minecraft.player.onGround) {
                        var10000 = mc;
                        if (!Minecraft.player.isPotionActive((Potion)Objects.requireNonNull(Potion.getPotionById(1)))) {
                              event.setPitch(90.0F);
                              if (event.getPitch() == 90.0F) {
                                    this.throwPot(AutoPotion.Potions.SPEED);
                              }
                        }

                        var10000 = mc;
                        if (!Minecraft.player.isPotionActive((Potion)Objects.requireNonNull(Potion.getPotionById(5)))) {
                              event.setPitch(90.0F);
                              if (event.getPitch() == 90.0F) {
                                    this.throwPot(AutoPotion.Potions.STRENGTH);
                              }
                        }

                        var10000 = mc;
                        if (!Minecraft.player.isPotionActive((Potion)Objects.requireNonNull(Potion.getPotionById(12)))) {
                              event.setPitch(90.0F);
                              if (event.getPitch() == 90.0F) {
                                    this.throwPot(AutoPotion.Potions.FIRERES);
                              }
                        }

                        this.counter.reset();
                  }
            }

      }

      void throwPot(AutoPotion.Potions potion) {
            int slot = this.getPotionSlot(potion);
            Minecraft var10000 = mc;
            Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(slot));
            mc.playerController.updateController();
            var10000 = mc;
            Minecraft.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            mc.playerController.updateController();
            var10000 = mc;
            Minecraft var10003 = mc;
            Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(Minecraft.player.inventory.currentItem));
      }

      int getPotionSlot(AutoPotion.Potions potion) {
            for(int i = 0; i < 9; ++i) {
                  Minecraft var10001 = mc;
                  if (this.isStackPotion(Minecraft.player.inventory.getStackInSlot(i), potion)) {
                        return i;
                  }
            }

            return -1;
      }

      boolean isPotionOnHotBar() {
            int i = 0;

            while(true) {
                  if (i >= 9) {
                        return false;
                  }

                  Minecraft var10001 = mc;
                  if (this.isStackPotion(Minecraft.player.inventory.getStackInSlot(i), AutoPotion.Potions.STRENGTH)) {
                        break;
                  }

                  var10001 = mc;
                  if (this.isStackPotion(Minecraft.player.inventory.getStackInSlot(i), AutoPotion.Potions.SPEED)) {
                        break;
                  }

                  var10001 = mc;
                  if (this.isStackPotion(Minecraft.player.inventory.getStackInSlot(i), AutoPotion.Potions.FIRERES)) {
                        break;
                  }

                  ++i;
            }

            return true;
      }

      boolean isStackPotion(ItemStack stack, AutoPotion.Potions potion) {
            if (stack == null) {
                  return false;
            } else {
                  Item item = stack.getItem();
                  if (item == Items.SPLASH_POTION) {
                        int id = 5;
                        switch(potion) {
                        case STRENGTH:
                              id = 5;
                              break;
                        case SPEED:
                              id = 1;
                              break;
                        case FIRERES:
                              id = 12;
                        }

                        Iterator var5 = PotionUtils.getEffectsFromStack(stack).iterator();

                        while(var5.hasNext()) {
                              PotionEffect effect = (PotionEffect)var5.next();
                              if (effect.getPotion() == Potion.getPotionById(id)) {
                                    return true;
                              }
                        }
                  }

                  return false;
            }
      }

      static enum Potions {
            FIRERES,
            SPEED,
            STRENGTH;
      }
}
