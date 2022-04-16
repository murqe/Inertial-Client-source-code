package wtf.rich.api.event.event;

import wtf.rich.api.event.Event;

public class Event3D extends Event {
     private float partialTicks;

     public Event3D(float partialTicks) {
          this.partialTicks = partialTicks;
     }

     public float getPartialTicks() {
          return this.partialTicks;
     }
}
