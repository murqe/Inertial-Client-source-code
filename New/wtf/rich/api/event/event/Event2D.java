package wtf.rich.api.event.event;

import net.minecraft.client.gui.ScaledResolution;
import wtf.rich.api.event.Event;

public class Event2D extends Event {
     private float width;
     private float height;
     private ScaledResolution resolution;

     public Event2D(float width, float height) {
          this.width = width;
          this.height = height;
     }

     public float getWidth() {
          return this.width;
     }

     public float getHeight() {
          return this.height;
     }

     public ScaledResolution getResolution() {
          return this.resolution;
     }
}
