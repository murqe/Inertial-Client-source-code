package me.rich.event.events;

import me.rich.event.Event;
import net.minecraft.network.Packet;

public class EventPacketReceive extends Event {
      Packet packet;

      public EventPacketReceive(Packet packet) {
            this.packet = packet;
      }

      public Packet getPacket() {
            return this.packet;
      }

      public void setPacket(Packet packet) {
            this.packet = packet;
      }
}
