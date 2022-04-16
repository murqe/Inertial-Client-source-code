package wtf.rich.client.features.impl.player;

import net.minecraft.network.play.client.CPacketCloseWindow;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventSendPacket;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;

public class XCarry extends Feature {
     public XCarry() {
          super("XCarry", "Позволяет хранить предметы в слотах для крафта", 0, Category.PLAYER);
     }

     @EventTarget
     public void onSendPacket(EventSendPacket event) {
          if (event.getPacket() instanceof CPacketCloseWindow) {
               event.setCancelled(true);
          }

     }
}
