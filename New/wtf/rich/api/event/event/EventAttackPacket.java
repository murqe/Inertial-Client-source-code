package wtf.rich.api.event.event;

import net.minecraft.entity.Entity;
import wtf.rich.api.event.Event;

public class EventAttackPacket extends Event {
     private final Entity targetEntity;

     public EventAttackPacket(Entity targetEntity) {
          this.targetEntity = targetEntity;
     }

     public Entity getTargetEntity() {
          return this.targetEntity;
     }
}
