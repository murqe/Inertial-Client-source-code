package me.rich.event.events;

import me.rich.event.Event;
import net.minecraft.network.Packet;

public class EventPacketSend extends Event {
      Packet packet;

      public EventPacketSend(Packet packet) {
            this.packet = packet;
      }

      public Packet getPacket() {
            return this.packet;
      }

      public void setPacket(Packet packet) {
            this.packet = packet;
      }
}
