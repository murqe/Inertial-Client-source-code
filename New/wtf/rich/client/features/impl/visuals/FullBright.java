package wtf.rich.client.features.impl.visuals;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.ListSetting;

public class FullBright extends Feature {
     public static ListSetting brightMode;

     public FullBright() {
          super("FullBright", "Убирает темноту в игре", 0, Category.VISUALS);
          brightMode = new ListSetting("FullBright Mode", "Gamma", () -> {
               return true;
          }, new String[]{"Gamma", "Potion"});
          this.addSettings(new Setting[]{brightMode});
     }

     @EventTarget
     public void onUpdate(EventUpdate event) {
          if (this.isToggled()) {
               String mode = brightMode.getOptions();
               if (mode.equalsIgnoreCase("Gamma")) {
                    mc.gameSettings.gammaSetting = 1000.0F;
               }

               if (mode.equalsIgnoreCase("Potion")) {
                    mc.player.addPotionEffect(new PotionEffect(Potion.getPotionById(16), 817, 1));
               } else {
                    mc.player.removePotionEffect(Potion.getPotionById(16));
               }
          }

     }

     public void onDisable() {
          super.onDisable();
          mc.gameSettings.gammaSetting = 1.0F;
          mc.player.removePotionEffect(Potion.getPotionById(16));
     }
}
