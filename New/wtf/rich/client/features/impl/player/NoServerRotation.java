package wtf.rich.client.features.impl.player;

import net.minecraft.network.play.server.SPacketPlayerPosLook;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventReceivePacket;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;

public class NoServerRotation extends Feature {
     public NoServerRotation() {
          super("NoServerRotation", "Убирает ротацию со стороны сервера", 0, Category.PLAYER);
     }

     @EventTarget
     public void onReceivePacket(EventReceivePacket event) {
          if (event.getPacket() instanceof SPacketPlayerPosLook) {
               SPacketPlayerPosLook packet = (SPacketPlayerPosLook)event.getPacket();
               packet.yaw = mc.player.rotationYaw;
               packet.pitch = mc.player.rotationPitch;
          }

     }
}
