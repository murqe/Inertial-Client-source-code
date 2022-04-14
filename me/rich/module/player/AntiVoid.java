package me.rich.module.player;

import me.rich.event.EventTarget;
import me.rich.event.events.EventChatMessage;
import me.rich.event.events.EventPreMotionUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;

public class AntiVoid extends Feature {
      double fall = 0.0D;

      public AntiVoid() {
            super("AntiVoid", 0, Category.PLAYER);
      }

      @EventTarget
      public void onChatDisable(EventChatMessage event) {
            if (event.getMessage().contains("ïðîâåðêà,")) {
            }

      }

      @EventTarget
      public void onUpdate(EventPreMotionUpdate event) {
            boolean blockUnderneath = false;
            int checkedY = 0;
            int i = 0;

            while(true) {
                  double var10000 = (double)i;
                  Minecraft var10001 = mc;
                  BlockPos pos;
                  Minecraft var10002;
                  double var10003;
                  Minecraft var10004;
                  if (var10000 >= Minecraft.player.posY + 2.0D) {
                        if (blockUnderneath) {
                              var10002 = mc;
                              var10003 = (double)checkedY;
                              var10004 = mc;
                              pos = new BlockPos(Minecraft.player.posX, var10003, Minecraft.player.posZ);
                              if (!(mc.world.getBlockState(pos).getBlock() instanceof BlockLiquid) && !(mc.world.getBlockState(pos).getBlock() instanceof BlockDynamicLiquid)) {
                                    return;
                              }

                              int antiLiquid = 0;

                              while(true) {
                                    var10000 = (double)antiLiquid;
                                    var10001 = mc;
                                    if (var10000 >= Minecraft.player.posY) {
                                          break;
                                    }

                                    var10002 = mc;
                                    Minecraft var10 = mc;
                                    var10003 = Minecraft.player.posY - (double)antiLiquid;
                                    var10004 = mc;
                                    BlockPos posTest = new BlockPos(Minecraft.player.posX, var10003, Minecraft.player.posZ);
                                    Block block = mc.world.getBlockState(posTest).getBlock();
                                    if (!(block instanceof BlockLiquid) && !(block instanceof BlockDynamicLiquid) && !(block instanceof BlockAir)) {
                                          return;
                                    }

                                    ++antiLiquid;
                              }
                        }

                        Minecraft var9 = mc;
                        if (!Minecraft.player.onGround) {
                              var9 = mc;
                              if (!Minecraft.player.isCollidedVertically) {
                                    var9 = mc;
                                    if (Minecraft.player.motionY < -0.08D) {
                                          var10002 = mc;
                                          this.fall -= Minecraft.player.motionY;
                                    }

                                    if (this.fall > 7.0D) {
                                          this.fall = 0.0D;
                                          var9 = mc;
                                          Minecraft.player.jump();
                                          var9 = mc;
                                          Minecraft.player.fallDistance = 0.0F;
                                    }

                                    return;
                              }
                        }

                        this.fall = 0.0D;
                        return;
                  }

                  var10002 = mc;
                  var10003 = (double)i;
                  var10004 = mc;
                  pos = new BlockPos(Minecraft.player.posX, var10003, Minecraft.player.posZ);
                  if (!(mc.world.getBlockState(pos).getBlock() instanceof BlockAir)) {
                        blockUnderneath = true;
                        checkedY = i;
                  }

                  ++i;
            }
      }
}
