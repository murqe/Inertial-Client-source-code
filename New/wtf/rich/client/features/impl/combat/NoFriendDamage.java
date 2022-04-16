package wtf.rich.client.features.impl.combat;

import net.minecraft.network.play.client.CPacketUseEntity;
import wtf.rich.Main;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventSendPacket;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;

public class NoFriendDamage extends Feature {
     public NoFriendDamage() {
          super("NoFriendDamage", "Не даёт ударить друга", 0, Category.COMBAT);
     }

     @EventTarget
     public void onSendPacket(EventSendPacket event) {
          if (event.getPacket() instanceof CPacketUseEntity) {
               CPacketUseEntity cpacketUseEntity = (CPacketUseEntity)event.getPacket();
               if (cpacketUseEntity.getAction().equals(CPacketUseEntity.Action.ATTACK) && Main.instance.friendManager.isFriend(mc.objectMouseOver.entityHit.getName())) {
                    event.setCancelled(true);
               }
          }

     }
}
