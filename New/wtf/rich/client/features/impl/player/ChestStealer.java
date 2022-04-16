package wtf.rich.client.features.impl.player;

import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemCompass;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventPreMotionUpdate;
import wtf.rich.api.utils.world.TimerHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class ChestStealer extends Feature {
     private final NumberSetting delay = new NumberSetting("Stealer Speed", 10.0F, 0.0F, 100.0F, 1.0F, () -> {
          return true;
     });
     public TimerHelper timer = new TimerHelper();

     public ChestStealer() {
          super("ChestStealer", "Забирает содержимое сундука ", 0, Category.PLAYER);
          this.addSettings(new Setting[]{this.delay});
     }

     @EventTarget
     public void onUpdate(EventPreMotionUpdate event) {
          this.setSuffix("" + (int)this.delay.getNumberValue());
          float delay = this.delay.getNumberValue() * 10.0F;
          if (mc.player.openContainer instanceof ContainerChest) {
               ContainerChest container = (ContainerChest)mc.player.openContainer;

               for(int index = 0; index < container.inventorySlots.size(); ++index) {
                    if (container.getLowerChestInventory().getStackInSlot(index).getItem() != Item.getItemById(0) && this.timer.hasReached((double)delay)) {
                         mc.playerController.windowClick(container.windowId, index, 0, ClickType.QUICK_MOVE, mc.player);
                         this.timer.reset();
                    } else if (this.isEmpty(container)) {
                         mc.player.closeScreen();
                    }
               }
          }

     }

     public boolean isWhiteItem(ItemStack itemStack) {
          return itemStack.getItem() instanceof ItemArmor || itemStack.getItem() instanceof ItemEnderPearl || itemStack.getItem() instanceof ItemSword || itemStack.getItem() instanceof ItemTool || itemStack.getItem() instanceof ItemFood || itemStack.getItem() instanceof ItemPotion || itemStack.getItem() instanceof ItemBlock || itemStack.getItem() instanceof ItemArrow || itemStack.getItem() instanceof ItemCompass;
     }

     private boolean isEmpty(Container container) {
          for(int index = 0; index < container.inventorySlots.size(); ++index) {
               if (this.isWhiteItem(container.getSlot(index).getStack())) {
                    return false;
               }
          }

          return true;
     }
}
