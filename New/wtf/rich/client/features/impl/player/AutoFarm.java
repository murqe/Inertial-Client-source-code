package wtf.rich.client.features.impl.player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockCarrot;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketMultiBlockChange;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventPreMotionUpdate;
import wtf.rich.api.event.event.EventReceivePacket;
import wtf.rich.api.utils.combat.EntityHelper;
import wtf.rich.api.utils.combat.RotationHelper;
import wtf.rich.api.utils.world.BlockHelper;
import wtf.rich.api.utils.world.TimerHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class AutoFarm extends Feature {
     private final NumberSetting delay = new NumberSetting("Farm Delay", 2.0F, 0.0F, 10.0F, 0.1F, () -> {
          return true;
     });
     private final NumberSetting radius = new NumberSetting("Farm Radius", 4.0F, 1.0F, 7.0F, 0.1F, () -> {
          return true;
     });
     private final BooleanSetting autoHoe = new BooleanSetting("Auto Hoe", false, () -> {
          return true;
     });
     private final BooleanSetting autoFarm = new BooleanSetting("Auto Farm", true, () -> {
          return true;
     });
     ArrayList crops = new ArrayList();
     ArrayList check = new ArrayList();
     TimerHelper timerHelper = new TimerHelper();
     TimerHelper timerHelper2 = new TimerHelper();

     public AutoFarm() {
          super("Auto Farm", "Автоматически садит и ломает урожай", 0, Category.PLAYER);
          this.addSettings(new Setting[]{this.autoFarm, this.autoHoe, this.delay, this.radius});
     }

     public static boolean doesHaveSeeds() {
          for(int i = 0; i < 9; ++i) {
               mc.player.inventory.getStackInSlot(i);
               if (mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemSeeds) {
                    return true;
               }
          }

          return false;
     }

     public static int searchSeeds() {
          for(int i = 0; i < 45; ++i) {
               ItemStack itemStack = mc.player.inventoryContainer.getSlot(i).getStack();
               if (itemStack.getItem() instanceof ItemSeeds) {
                    return i;
               }
          }

          return -1;
     }

     public static int getSlotWithSeeds() {
          for(int i = 0; i < 9; ++i) {
               mc.player.inventory.getStackInSlot(i);
               if (mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemSeeds) {
                    return i;
               }
          }

          return 0;
     }

     public void onEnable() {
          this.crops.clear();
          this.check.clear();
          super.onEnable();
     }

     private boolean isOnCrops() {
          for(double x = mc.player.boundingBox.minX; x < mc.player.boundingBox.maxX; x += 0.009999999776482582D) {
               for(double z = mc.player.boundingBox.minZ; z < mc.player.boundingBox.maxZ; z += 0.009999999776482582D) {
                    Block block = mc.world.getBlockState(new BlockPos(x, mc.player.posY - 0.1D, z)).getBlock();
                    if (!(block instanceof BlockFarmland) && !(block instanceof BlockCarrot) && !(block instanceof BlockSoulSand) && !(block instanceof BlockSand) && !(block instanceof BlockAir)) {
                         return false;
                    }
               }
          }

          return true;
     }

     private boolean IsValidBlockPos(BlockPos pos) {
          IBlockState state = mc.world.getBlockState(pos);
          if (!(state.getBlock() instanceof BlockFarmland) && !(state.getBlock() instanceof BlockSand) && !(state.getBlock() instanceof BlockSoulSand)) {
               return false;
          } else {
               return mc.world.getBlockState(pos.up()).getBlock() == Blocks.AIR;
          }
     }

     @EventTarget
     public void onPreMotion(EventPreMotionUpdate event) {
          if (mc.player != null || mc.world != null) {
               BlockPos pos = (BlockPos)BlockHelper.getSphere(BlockHelper.getPlayerPos(), this.radius.getNumberValue(), 6, false, true).stream().filter(BlockHelper::IsValidBlockPos).min(Comparator.comparing((blockPos) -> {
                    return EntityHelper.getDistanceOfEntityToBlock(mc.player, blockPos);
               })).orElse((Object)null);
               if (pos != null && mc.player.getHeldItemMainhand().getItem() instanceof ItemHoe) {
                    float[] rots = RotationHelper.getRotationVector(new Vec3d((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F)), true, 2.0F, 2.0F, 360.0F);
                    event.setYaw(rots[0]);
                    event.setPitch(rots[1]);
                    mc.player.renderYawOffset = rots[0];
                    mc.player.rotationYawHead = rots[0];
                    mc.player.rotationPitchHead = rots[1];
                    if (this.timerHelper2.hasReached((double)(this.delay.getNumberValue() * 100.0F))) {
                         mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, EnumFacing.UP, EnumHand.MAIN_HAND, 0.0F, 0.0F, 0.0F));
                         mc.player.swingArm(EnumHand.MAIN_HAND);
                         this.timerHelper2.reset();
                    }
               }

               if (!doesHaveSeeds() && searchSeeds() != -1) {
                    mc.playerController.windowClick(0, searchSeeds(), 1, ClickType.QUICK_MOVE, mc.player);
               }

          }
     }

     @EventTarget
     public void onPre(EventPreMotionUpdate e) {
          if (this.autoFarm.getBoolValue()) {
               ArrayList blockPositions = this.getBlocks(this.radius.getNumberValue(), this.radius.getNumberValue(), this.radius.getNumberValue());
               Iterator var3 = blockPositions.iterator();

               label50:
               while(true) {
                    BlockPos pos;
                    IBlockState state;
                    Block block;
                    BlockPos downPos;
                    do {
                         do {
                              if (!var3.hasNext()) {
                                   break label50;
                              }

                              pos = (BlockPos)var3.next();
                              state = mc.world.getBlockState(pos);
                         } while(!this.isCheck(Block.getIdFromBlock(state.getBlock())));

                         if (!this.isCheck(0)) {
                              this.check.add(pos);
                         }

                         block = mc.world.getBlockState(pos).getBlock();
                         downPos = pos.down(1);
                    } while(!(block instanceof BlockCrops) && !(block instanceof BlockCarrot));

                    BlockCrops crop = (BlockCrops)block;
                    if (!crop.canGrow(mc.world, pos, state, true) && this.timerHelper.hasReached((double)(this.delay.getNumberValue() * 100.0F)) && pos != null) {
                         float[] rots = RotationHelper.getRotationVector(new Vec3d((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F)), true, 2.0F, 2.0F, 360.0F);
                         e.setYaw(rots[0]);
                         e.setPitch(rots[1]);
                         mc.player.renderYawOffset = rots[0];
                         mc.player.rotationYawHead = rots[0];
                         mc.player.rotationPitchHead = rots[1];
                         mc.playerController.onPlayerDamageBlock(pos, mc.player.getHorizontalFacing());
                         mc.player.swingArm(EnumHand.MAIN_HAND);
                         if (doesHaveSeeds()) {
                              mc.player.connection.sendPacket(new CPacketHeldItemChange(getSlotWithSeeds()));
                              mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(downPos, EnumFacing.UP, EnumHand.MAIN_HAND, 0.0F, 0.0F, 0.0F));
                              mc.player.swingArm(EnumHand.MAIN_HAND);
                         }

                         this.timerHelper.reset();
                    }
               }
          }

          BlockPos pos = (BlockPos)BlockHelper.getSphere(BlockHelper.getPlayerPos(), this.radius.getNumberValue(), 6, false, true).stream().filter(this::IsValidBlockPos).min(Comparator.comparing((blockPos) -> {
               return EntityHelper.getDistanceOfEntityToBlock(mc.player, blockPos);
          })).orElse((Object)null);
          Vec3d vec = new Vec3d(0.0D, 0.0D, 0.0D);
          if (this.timerHelper.hasReached((double)(this.delay.getNumberValue() * 100.0F)) && this.isOnCrops() && pos != null && doesHaveSeeds()) {
               float[] rots = RotationHelper.getRotationVector(new Vec3d((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F)), true, 2.0F, 2.0F, 360.0F);
               e.setYaw(rots[0]);
               e.setPitch(rots[1]);
               mc.player.renderYawOffset = rots[0];
               mc.player.rotationYawHead = rots[0];
               mc.player.rotationPitchHead = rots[1];
               mc.player.connection.sendPacket(new CPacketHeldItemChange(getSlotWithSeeds()));
               mc.playerController.processRightClickBlock(mc.player, mc.world, pos, EnumFacing.VALUES[0].getOpposite(), vec, EnumHand.MAIN_HAND);
               this.timerHelper.reset();
          }

     }

     @EventTarget
     public void onReceivePacket(EventReceivePacket e) {
          if (this.autoFarm.getBoolValue()) {
               if (e.getPacket() instanceof SPacketBlockChange) {
                    SPacketBlockChange p = (SPacketBlockChange)e.getPacket();
                    if (this.isEnabled(Block.getIdFromBlock(p.getBlockState().getBlock()))) {
                         this.crops.add(p.getBlockPosition());
                    }
               } else if (e.getPacket() instanceof SPacketMultiBlockChange) {
                    SPacketMultiBlockChange p = (SPacketMultiBlockChange)e.getPacket();
                    SPacketMultiBlockChange.BlockUpdateData[] var3 = p.getChangedBlocks();
                    int var4 = var3.length;

                    for(int var5 = 0; var5 < var4; ++var5) {
                         SPacketMultiBlockChange.BlockUpdateData dat = var3[var5];
                         if (this.isEnabled(Block.getIdFromBlock(dat.getBlockState().getBlock()))) {
                              this.crops.add(dat.getPos());
                         }
                    }
               }
          }

     }

     private boolean isCheck(int id) {
          int check = 0;
          if (id != 0) {
               check = 59;
          }

          if (id == 0) {
               return false;
          } else {
               return id == check;
          }
     }

     private boolean isEnabled(int id) {
          int check = 0;
          if (id != 0) {
               check = 59;
          }

          if (id == 0) {
               return false;
          } else {
               return id == check;
          }
     }

     private ArrayList getBlocks(float x, float y, float z) {
          BlockPos min = new BlockPos(mc.player.posX - (double)x, mc.player.posY - (double)y, mc.player.posZ - (double)z);
          BlockPos max = new BlockPos(mc.player.posX + (double)x, mc.player.posY + (double)y, mc.player.posZ + (double)z);
          return BlockHelper.getAllInBox(min, max);
     }
}
