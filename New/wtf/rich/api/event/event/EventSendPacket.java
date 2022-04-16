package wtf.rich.api.event.event;

import net.minecraft.network.Packet;
import wtf.rich.api.event.Event;

public class EventSendPacket extends Event {
     private Packet packet;
     private boolean sending;

     public EventSendPacket(Packet packet, boolean sending) {
          this.packet = packet;
          this.sending = sending;
     }

     public boolean isSending() {
          return this.sending;
     }

     public boolean isRecieving() {
          return !this.sending;
     }

     public Packet getPacket() {
          return this.packet;
     }
}
