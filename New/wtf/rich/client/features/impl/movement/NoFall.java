package wtf.rich.client.features.impl.movement;

import net.minecraft.network.play.client.CPacketPlayer;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventPreMotionUpdate;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.ListSetting;

public class NoFall extends Feature {
     private final ListSetting noFallMode = new ListSetting("NoFall Mode", "Matrix", () -> {
          return true;
     }, new String[]{"Vanilla", "Matrix"});

     public NoFall() {
          super("NoFall", "Вы не получаете дамаг от падения", 0, Category.MOVEMENT);
          this.addSettings(new Setting[]{this.noFallMode});
     }

     @EventTarget
     public void onPreMotion(EventPreMotionUpdate event) {
          String mode = this.noFallMode.getOptions();
          this.setSuffix(mode);
          if (mode.equalsIgnoreCase("Vanilla")) {
               if (mc.player.fallDistance > 3.0F) {
                    event.setGround(true);
                    mc.player.connection.sendPacket(new CPacketPlayer(true));
               }
          } else if (mode.equalsIgnoreCase("Matrix") && mc.player.fallDistance > 3.0F) {
               mc.player.fallDistance = (float)(Math.random() * 1.0E-12D);
               mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(mc.player.posX, mc.player.posY, mc.player.posZ, mc.player.rotationYaw, mc.player.rotationPitch, true));
               mc.player.fallDistance = 0.0F;
          }

     }
}
