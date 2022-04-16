package me.rich.event.events;

import me.rich.event.Event;
import net.minecraft.entity.Entity;

public class EventAttackPacket extends Event {
      private final Entity targetEntity;

      public EventAttackPacket(Entity targetEntity) {
            this.targetEntity = targetEntity;
      }

      public Entity getTargetEntity() {
            return this.targetEntity;
      }
}
