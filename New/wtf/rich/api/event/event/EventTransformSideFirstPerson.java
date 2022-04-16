package wtf.rich.api.event.event;

import net.minecraft.util.EnumHandSide;
import wtf.rich.api.event.Event;

public class EventTransformSideFirstPerson extends Event {
     private final EnumHandSide enumHandSide;

     public EventTransformSideFirstPerson(EnumHandSide enumHandSide) {
          this.enumHandSide = enumHandSide;
     }

     public EnumHandSide getEnumHandSide() {
          return this.enumHandSide;
     }
}
