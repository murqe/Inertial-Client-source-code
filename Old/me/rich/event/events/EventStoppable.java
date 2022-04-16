package me.rich.event.events;

import me.rich.event.Event;

public class EventStoppable extends Event {
      private boolean stopped;

      protected EventStoppable() {
      }

      public void stop() {
            this.stopped = true;
      }

      public boolean isStopped() {
            return this.stopped;
      }
}
