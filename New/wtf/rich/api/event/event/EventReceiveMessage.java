package wtf.rich.api.event.event;

import wtf.rich.api.event.Event;

public class EventReceiveMessage extends Event {
     public String message;
     public boolean cancelled;

     public EventReceiveMessage(String chat) {
          this.message = chat;
     }

     public String getMessage() {
          return this.message;
     }

     public void setCancelled(boolean b) {
          this.cancelled = b;
     }
}
