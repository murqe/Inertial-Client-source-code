package wtf.rich.api.event.event;

import wtf.rich.api.event.Event;

public class EventKey extends Event {
     private int key;

     public EventKey(int key) {
          this.key = key;
     }

     public int getKey() {
          return this.key;
     }
}
