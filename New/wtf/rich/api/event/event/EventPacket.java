package wtf.rich.api.event.event;

import net.minecraft.network.Packet;
import wtf.rich.api.event.Event;

public class EventPacket extends Event {
     private Packet packet;
     private boolean outgoing;
     private boolean pre;

     public EventPacket(Packet packet, boolean outgoing) {
          this.packet = packet;
          this.outgoing = outgoing;
          this.pre = true;
     }

     public EventPacket(Packet packet) {
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
