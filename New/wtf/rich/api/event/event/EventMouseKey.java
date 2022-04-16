package wtf.rich.api.event.event;

import wtf.rich.api.event.Event;

public class EventMouseKey extends Event {
     private int key;

     public EventMouseKey(int key) {
          this.key = key;
     }

     public int getKey() {
          return this.key;
     }

     public void setKey(int key) {
          this.key = key;
     }
}
