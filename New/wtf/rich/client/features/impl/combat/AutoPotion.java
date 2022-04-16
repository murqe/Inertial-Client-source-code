package wtf.rich.client.features.impl.combat;

import java.util.Iterator;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumHand;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventPreMotionUpdate;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class AutoPotion extends Feature {
     public NumberSetting delay = new NumberSetting("Throw Delay", 500.0F, 0.0F, 600.0F, 10.0F, () -> {
          return true;
     });
     public BooleanSetting onlyGround = new BooleanSetting("Only Ground", true, () -> {
          return true;
     });

     public AutoPotion() {
          super("AutoPotion", "Автоматически бросает зелья находящиеся в хотбаре(быстрые слоты)", 0, Category.COMBAT);
          this.addSettings(new Setting[]{this.delay, this.onlyGround});
     }

     public void onDisable() {
          super.onDisable();
     }

     @EventTarget
     public void onPreMotion(EventPreMotionUpdate event) {
          if (!this.onlyGround.getBoolValue() || mc.player.onGround) {
               if (!mc.player.isPotionActive(Potion.getPotionById(1)) && this.isPotion(AutoPotion.Potions.SPEED)) {
                    event.setPitch(90.0F);
                    mc.player.rotationPitchHead = 90.0F;
                    if (event.getPitch() == 90.0F) {
                         this.throwPot(AutoPotion.Potions.SPEED);
                    }
               } else if (!mc.player.isPotionActive(Potion.getPotionById(5)) && this.isPotion(AutoPotion.Potions.STRENGTH)) {
                    event.setPitch(90.0F);
                    mc.player.rotationPitchHead = 90.0F;
                    if (event.getPitch() == 90.0F) {
                         this.throwPot(AutoPotion.Potions.STRENGTH);
                    }
               } else if (!mc.player.isPotionActive(Potion.getPotionById(12)) && this.isPotion(AutoPotion.Potions.FIRERES)) {
                    event.setPitch(90.0F);
                    mc.player.rotationPitchHead = 90.0F;
                    if (event.getPitch() == 90.0F) {
                         this.throwPot(AutoPotion.Potions.FIRERES);
                    }
               }

          }
     }

     public void throwPot(AutoPotion.Potions potion) {
          int slot = this.getPotionSlot(potion);
          if (timerHelper.hasReached((double)this.delay.getNumberValue())) {
               mc.player.connection.sendPacket(new CPacketHeldItemChange(slot));
               mc.playerController.updateController();
               mc.player.connection.sendPacket(new CPacketPlayer.Rotation(mc.player.rotationYaw, 90.0F, mc.player.onGround));
               mc.playerController.updateController();
               mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
               mc.playerController.updateController();
               mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
               timerHelper.reset();
          }

     }

     public int getPotionSlot(AutoPotion.Potions potion) {
          for(int i = 0; i < 9; ++i) {
               if (this.isStackPotion(mc.player.inventory.getStackInSlot(i), potion)) {
                    return i;
               }
          }

          return -1;
     }

     public boolean isPotion(AutoPotion.Potions potions) {
          for(int i = 0; i < 9; ++i) {
               if (this.isStackPotion(mc.player.inventory.getStackInSlot(i), potions)) {
                    return true;
               }
          }

          return false;
     }

     public boolean isStackPotion(ItemStack stack, AutoPotion.Potions potion) {
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

     public static enum Potions {
          STRENGTH,
          SPEED,
          FIRERES;
     }
}
