package wtf.rich.client.features.impl.combat;

import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketUseEntity;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventSendPacket;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;

public class SuperKnockBack extends Feature {
     public SuperKnockBack() {
          super("ExtendedKnockBack", "Вы откидываете противника дальше", 0, Category.COMBAT);
     }

     @EventTarget
     public void onSendPacket(EventSendPacket event) {
          if (event.getPacket() instanceof CPacketUseEntity) {
               CPacketUseEntity packet = (CPacketUseEntity)event.getPacket();
               if (packet.getAction() == CPacketUseEntity.Action.ATTACK) {
                    mc.player.setSprinting(false);
                    mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                    mc.player.setSprinting(true);
                    mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SPRINTING));
               }
          }

     }
}
