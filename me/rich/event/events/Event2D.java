package me.rich.event.events;

import me.rich.event.Event;

public class Event2D extends Event {
      private float width;
      private float height;

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
}
