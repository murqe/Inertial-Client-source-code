package wtf.rich.client.features.impl.combat;

import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.util.text.TextFormatting;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventReceivePacket;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.ListSetting;

public class Velocity extends Feature {
     public static BooleanSetting cancelOtherDamage;
     public static ListSetting velocityMode;

     public Velocity() {
          super("Velocity", "Вы не будете откидываться", 0, Category.COMBAT);
          velocityMode = new ListSetting("Velocity Mode", "Packet", () -> {
               return true;
          }, new String[]{"Packet", "Matrix"});
          cancelOtherDamage = new BooleanSetting("Cancel Other Damage", true, () -> {
               return true;
          });
          this.addSettings(new Setting[]{velocityMode, cancelOtherDamage});
     }

     @EventTarget
     public void onReceivePacket(EventReceivePacket event) {
          String mode = velocityMode.getOptions();
          this.setModuleName("Velocity " + TextFormatting.GRAY + mode);
          if (cancelOtherDamage.getBoolValue() && mc.player.hurtTime > 0 && event.getPacket() instanceof SPacketEntityVelocity && (mc.player.isPotionActive(MobEffects.POISON) || mc.player.isPotionActive(MobEffects.WITHER) || mc.player.isBurning())) {
               event.setCancelled(true);
          }

          if (mode.equalsIgnoreCase("Packet")) {
               if ((event.getPacket() instanceof SPacketEntityVelocity || event.getPacket() instanceof SPacketExplosion) && ((SPacketEntityVelocity)event.getPacket()).getEntityID() == mc.player.getEntityId()) {
                    event.setCancelled(true);
               }
          } else if (mode.equals("Matrix") && mc.player.hurtTime > 8) {
               mc.player.onGround = true;
          }

     }
}
