package wtf.rich.api.event.event;

import wtf.rich.api.event.Event;

public class EventMessage extends Event {
     public String message;

     public EventMessage(String message) {
          this.message = message;
     }

     public String getMessage() {
          return this.message;
     }
}
