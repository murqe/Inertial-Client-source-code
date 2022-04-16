package me.rich.module.misc;

import me.rich.event.EventTarget;
import me.rich.event.events.EventReceivePacket;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.network.play.client.CPacketConfirmTeleport;

public class PortalGodMode extends Feature {
      public PortalGodMode() {
            super("PortalGodMode", 0, Category.MISC);
      }

      @EventTarget
      public void receivePacket(EventReceivePacket event) {
            if (event.getPacket() instanceof CPacketConfirmTeleport) {
                  CPacketConfirmTeleport packet = (CPacketConfirmTeleport)event.getPacket();
                  event.setCancelled(true);
            }

      }
}
