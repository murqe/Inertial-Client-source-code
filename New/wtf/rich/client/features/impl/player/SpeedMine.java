package wtf.rich.client.features.impl.player;

import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventBlockInteract;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.ListSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class SpeedMine extends Feature {
     public ListSetting mode = new ListSetting("SpeedMine Mode", "Packet", () -> {
          return true;
     }, new String[]{"Packet", "Damage", "Potion"});
     public NumberSetting damageValue = new NumberSetting("Damage Value", 0.8F, 0.7F, 4.0F, 0.1F, () -> {
          return this.mode.currentMode.equals("Damage");
     });

     public SpeedMine() {
          super("SpeedMine", "Ускоряет скорость ломания блоков", 0, Category.PLAYER);
          this.addSettings(new Setting[]{this.mode, this.damageValue});
     }

     @EventTarget
     public void onUpdate(EventUpdate event) {
          this.setSuffix(this.mode.currentMode);
     }

     @EventTarget
     public void onBlockInteract(EventBlockInteract event) {
          String var2 = this.mode.currentMode;
          byte var3 = -1;
          switch(var2.hashCode()) {
          case -1911998296:
               if (var2.equals("Packet")) {
                    var3 = 2;
               }
               break;
          case -1898564173:
               if (var2.equals("Potion")) {
                    var3 = 0;
               }
               break;
          case 2039707535:
               if (var2.equals("Damage")) {
                    var3 = 1;
               }
          }

          switch(var3) {
          case 0:
               mc.player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 817, 1));
               break;
          case 1:
               if ((double)mc.playerController.curBlockDamageMP >= 0.7D) {
                    mc.playerController.curBlockDamageMP = this.damageValue.getNumberValue();
               }
               break;
          case 2:
               mc.player.swingArm(EnumHand.MAIN_HAND);
               mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), event.getFace()));
               mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), event.getFace()));
               event.setCancelled(true);
          }

     }

     public void onDisable() {
          mc.player.removePotionEffect(MobEffects.HASTE);
          super.onDisable();
     }
}
