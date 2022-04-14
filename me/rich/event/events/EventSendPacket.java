package me.rich.event.events;

import me.rich.event.Event;
import net.minecraft.network.Packet;

public class EventSendPacket extends Event {
      public Packet packet;

      public EventSendPacket(Packet packet) {
            this.setPacket(packet);
      }

      public Packet getPacket() {
            return this.packet;
      }

      public void setPacket(Packet packet) {
            this.packet = packet;
      }
}
