package wtf.rich.api.event.event;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import wtf.rich.api.event.Event;

public class EventBlockInteract extends Event {
     private BlockPos pos;
     private EnumFacing face;

     public EventBlockInteract(BlockPos pos, EnumFacing face) {
          this.pos = pos;
          this.face = face;
     }

     public BlockPos getPos() {
          return this.pos;
     }

     public void setPos(BlockPos pos) {
          this.pos = pos;
     }

     public EnumFacing getFace() {
          return this.face;
     }

     public void setFace(EnumFacing face) {
          this.face = face;
     }
}
