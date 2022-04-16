package wtf.rich.client.features.impl.combat;

import net.minecraft.entity.player.EntityPlayer;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventAttackSilent;
import wtf.rich.api.utils.other.SoundHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.ListSetting;

public class HitSounds extends Feature {
     public static ListSetting soundMode;

     public HitSounds() {
          super("HitSounds", "Издаёт звук при ударе ", 0, Category.COMBAT);
          soundMode = new ListSetting("Sound Mode", "Neverlose", () -> {
               return true;
          }, new String[]{"Neverlose", "Metallic"});
          this.addSettings(new Setting[]{soundMode});
     }

     @EventTarget
     public void onAttackSilent(EventAttackSilent eventAttackSilent) {
          if (eventAttackSilent.getTargetEntity() instanceof EntityPlayer) {
               String mode = soundMode.getOptions();
               if (mode.equalsIgnoreCase("Neverlose")) {
                    SoundHelper.playSound("neverlose.wav");
               } else if (mode.equalsIgnoreCase("Metallic")) {
                    SoundHelper.playSound("metallic.wav");
               }
          }

     }
}
