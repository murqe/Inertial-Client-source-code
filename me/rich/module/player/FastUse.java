package me.rich.module.player;

import me.rich.event.EventTarget;
import me.rich.event.events.EventPreMotionUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class FastUse extends Feature {
      public FastUse() {
            super("FastUse", 0, Category.PLAYER);
      }

      @EventTarget
      public void onUpdate(EventPreMotionUpdate event) {
            if (Minecraft.player.getItemInUseCount() == 2) {
                  for(int i = 0; i < 30; ++i) {
                        Minecraft.player.connection.sendPacket(new CPacketPlayer(true));
                  }

                  mc.getConnection().sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                  Minecraft.player.stopActiveHand();
            }

      }
}
