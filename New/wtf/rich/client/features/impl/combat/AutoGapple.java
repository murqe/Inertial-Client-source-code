package wtf.rich.client.features.impl.combat;

import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class AutoGapple extends Feature {
     public static NumberSetting health;
     private boolean isActive;

     public AutoGapple() {
          super("AutoGApple", "Автоматически ест яблоко при опредленном здоровье", 0, Category.COMBAT);
          health = new NumberSetting("Health Amount", 15.0F, 1.0F, 20.0F, 1.0F, () -> {
               return true;
          });
          this.addSettings(new Setting[]{health});
     }

     @EventTarget
     public void onUpdate(EventUpdate e) {
          this.setSuffix("" + (int)health.getNumberValue());
          if (mc.player != null && mc.world != null) {
               if (this.isGoldenApple(mc.player.getHeldItemOffhand()) && mc.player.getHealth() <= health.getNumberValue()) {
                    this.isActive = true;
                    mc.gameSettings.keyBindUseItem.pressed = true;
               } else if (this.isActive) {
                    mc.gameSettings.keyBindUseItem.pressed = false;
                    this.isActive = false;
               }

          }
     }

     private boolean isGoldenApple(ItemStack itemStack) {
          return itemStack != null && !itemStack.isEmpty() && itemStack.getItem() instanceof ItemAppleGold;
     }
}
