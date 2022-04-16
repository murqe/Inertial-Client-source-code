package wtf.rich.api.utils.world;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import wtf.rich.api.utils.Helper;

public class BlockHelper implements Helper {
     public static Block getBlock(BlockPos pos) {
          return mc.world.getBlockState(pos).getBlock();
     }

     public static BlockPos getPlayerPos() {
          return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
     }

     public static boolean IsValidBlockPos(BlockPos pos) {
          IBlockState state = mc.world.getBlockState(pos);
          if (state.getBlock() instanceof BlockDirt || state.getBlock() instanceof BlockGrass && !(state.getBlock() instanceof BlockFarmland)) {
               return mc.world.getBlockState(pos.up()).getBlock() == Blocks.AIR;
          } else {
               return false;
          }
     }

     public static List getSphere(BlockPos blockPos, float offset, int range, boolean hollow, boolean sphere) {
          ArrayList blockPosList = new ArrayList();
          int blockPosX = blockPos.getX();
          int blockPosY = blockPos.getY();
          int blockPosZ = blockPos.getZ();

          for(float x = (float)blockPosX - offset; x <= (float)blockPosX + offset; ++x) {
               for(float z = (float)blockPosZ - offset; z <= (float)blockPosZ + offset; ++z) {
                    float y = sphere ? (float)blockPosY - offset : (float)blockPosY;

                    while(true) {
                         float f = sphere ? (float)blockPosY + offset : (float)(blockPosY + range);
                         if (y >= f) {
                              break;
                         }

                         float dist = ((float)blockPosX - x) * ((float)blockPosX - x) + ((float)blockPosZ - z) * ((float)blockPosZ - z) + (sphere ? ((float)blockPosY - y) * ((float)blockPosY - y) : 0.0F);
                         if (dist < offset * offset && (!hollow || dist >= offset - 1.0F * (offset - 1.0F))) {
                              BlockPos pos = new BlockPos((double)x, (double)y, (double)z);
                              blockPosList.add(pos);
                         }

                         ++y;
                    }
               }
          }

          return blockPosList;
     }

     public static ArrayList getBlocks(int x, int y, int z) {
          BlockPos min = new BlockPos(mc.player.posX - (double)x, mc.player.posY - (double)y, mc.player.posZ - (double)z);
          BlockPos max = new BlockPos(mc.player.posX + (double)x, mc.player.posY + (double)y, mc.player.posZ + (double)z);
          return getAllInBox(min, max);
     }

     public static ArrayList getAllInBox(BlockPos from, BlockPos to) {
          ArrayList blocks = new ArrayList();
          BlockPos min = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
          BlockPos max = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));

          for(int x = min.getX(); x <= max.getX(); ++x) {
               for(int y = min.getY(); y <= max.getY(); ++y) {
                    for(int z = min.getZ(); z <= max.getZ(); ++z) {
                         blocks.add(new BlockPos(x, y, z));
                    }
               }
          }

          return blocks;
     }
}
