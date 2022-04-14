package me.rich.event.events;

import me.rich.event.Event;
import net.minecraft.network.Packet;

public class EventReceivePacket extends Event {
      private Packet packet;
      private boolean outgoing;
      private boolean pre;

      public EventReceivePacket(Packet packet, boolean outgoing) {
            this.packet = packet;
            this.outgoing = outgoing;
            this.pre = true;
      }

      public EventReceivePacket(Packet packet) {
            this.packet = packet;
            this.pre = false;
      }

      public boolean isPre() {
            return this.pre;
      }

      public boolean isPost() {
            return !this.pre;
      }

      public Packet getPacket() {
            return this.packet;
      }

      public void setPacket(Packet packet) {
            this.packet = packet;
      }

      public boolean isOutgoing() {
            return this.outgoing;
      }

      public boolean isIncoming() {
            return !this.outgoing;
      }
}
