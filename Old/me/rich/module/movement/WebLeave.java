package me.rich.module.movement;

import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;

public class WebLeave extends Feature {
      public WebLeave() {
            super("WebLeave", 0, Category.MOVEMENT);
      }

      @EventTarget
      public void onUpdate(EventUpdate event) {
            Minecraft var10003 = mc;
            Minecraft var10004 = mc;
            double var2 = Minecraft.player.posY + 1.0D;
            Minecraft var10005 = mc;
            Minecraft var10000;
            if (mc.world.getBlockState(new BlockPos(Minecraft.player.posX, var2, Minecraft.player.posZ)).getBlock() == Block.getBlockById(30)) {
                  var10000 = mc;
                  Minecraft.player.motionY = 0.18000000715255737D;
            } else {
                  var10003 = mc;
                  var10004 = mc;
                  var10005 = mc;
                  if (mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ)).getBlock() == Block.getBlockById(30)) {
                        var10000 = mc;
                        Minecraft.player.fallDistance = 0.0F;
                        var10000 = mc;
                        Minecraft.player.motionX = 0.0D;
                        var10000 = mc;
                        Minecraft.player.motionY = 10.0D;
                  }
            }

      }
}
