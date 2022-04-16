package wtf.rich.api.event.event;

import net.minecraft.block.BlockLiquid;
import net.minecraft.util.math.BlockPos;
import wtf.rich.api.event.Event;

public class EventLiquidSolid extends Event {
     private final BlockLiquid blockLiquid;
     private final BlockPos pos;

     public EventLiquidSolid(BlockLiquid blockLiquid, BlockPos pos) {
          this.blockLiquid = blockLiquid;
          this.pos = pos;
     }

     public BlockLiquid getBlock() {
          return this.blockLiquid;
     }

     public BlockPos getPos() {
          return this.pos;
     }
}
