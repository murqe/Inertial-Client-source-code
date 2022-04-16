package wtf.rich.api.event.event;

import net.minecraft.entity.Entity;
import wtf.rich.api.event.Event;

public class EventAttackClient extends Event {
     private final Entity entity;

     public EventAttackClient(Entity targetEntity) {
          this.entity = targetEntity;
     }

     public Entity getEntity() {
          return this.entity;
     }
}
