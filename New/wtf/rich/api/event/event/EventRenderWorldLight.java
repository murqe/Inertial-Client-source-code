package wtf.rich.api.event.event;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import wtf.rich.api.event.Event;

public class EventRenderWorldLight extends Event {
     private final EnumSkyBlock enumSkyBlock;
     private final BlockPos pos;

     public EventRenderWorldLight(EnumSkyBlock enumSkyBlock, BlockPos pos) {
          this.enumSkyBlock = enumSkyBlock;
          this.pos = pos;
     }

     public EnumSkyBlock getEnumSkyBlock() {
          return this.enumSkyBlock;
     }

     public BlockPos getPos() {
          return this.pos;
     }
}
