package wtf.rich.client.features.impl.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventPreMotionUpdate;
import wtf.rich.api.utils.combat.EntityHelper;
import wtf.rich.api.utils.combat.RotationHelper;
import wtf.rich.api.utils.world.BlockHelper;
import wtf.rich.api.utils.world.InventoryHelper;
import wtf.rich.api.utils.world.TimerHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class AntiCrystal extends Feature {
     private final NumberSetting rangeToBlock = new NumberSetting("Range To Block", 5.0F, 3.0F, 6.0F, 0.1F, () -> {
          return true;
     });
     private final NumberSetting delay = new NumberSetting("Place Delay", 0.0F, 0.0F, 2000.0F, 100.0F, () -> {
          return true;
     });
     private final BooleanSetting throughWalls = new BooleanSetting("Through Walls", true, () -> {
          return true;
     });
     private final BooleanSetting bedrockCheck = new BooleanSetting("Bedrock Check", false, () -> {
          return true;
     });
     private final BooleanSetting obsidianCheck = new BooleanSetting("Obsidian Check", true, () -> {
          return true;
     });
     private final TimerHelper timerHelper = new TimerHelper();
     private final ArrayList invalidPositions = new ArrayList();

     public AntiCrystal() {
          super("AntiCrystal", "Автоматически ставит блок на обсидиан/бедрок", 0, Category.COMBAT);
          this.addSettings(new Setting[]{this.obsidianCheck, this.bedrockCheck, this.throughWalls, this.rangeToBlock, this.delay});
     }

     public static int getSlotWithBlock() {
          for(int i = 0; i < 9; ++i) {
               mc.player.inventory.getStackInSlot(i);
               if (mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemBlock) {
                    return i;
               }
          }

          return -1;
     }

     private boolean IsValidBlockPos(BlockPos pos) {
          IBlockState state = mc.world.getBlockState(pos);
          if (state.getBlock() instanceof BlockObsidian && this.obsidianCheck.getBoolValue() || state.getBlock() == Block.getBlockById(7) && this.bedrockCheck.getBoolValue()) {
               return mc.world.getBlockState(pos.up()).getBlock() == Blocks.AIR;
          } else {
               return false;
          }
     }

     @EventTarget
     public void onPreMotion(EventPreMotionUpdate event) {
          this.setSuffix("" + (int)this.rangeToBlock.getNumberValue());
          int oldSlot = mc.player.inventory.currentItem;
          BlockPos pos = (BlockPos)BlockHelper.getSphere(BlockHelper.getPlayerPos(), this.rangeToBlock.getNumberValue(), 6, false, true).stream().filter(this::IsValidBlockPos).min(Comparator.comparing((blockPos) -> {
               return EntityHelper.getDistanceOfEntityToBlock(mc.player, blockPos);
          })).orElse((Object)null);
          if (InventoryHelper.doesHotbarHaveBlock() && pos != null && this.timerHelper.hasReached((double)this.delay.getNumberValue()) && getSlotWithBlock() != -1) {
               if (!mc.world.isAirBlock(pos.up(1))) {
                    this.invalidPositions.add(pos);
               }

               Iterator var4 = mc.world.loadedEntityList.iterator();

               while(var4.hasNext()) {
                    Entity e = (Entity)var4.next();
                    if (e instanceof EntityEnderCrystal && e.getPosition().getX() == pos.getX() && e.getPosition().getZ() == pos.getZ()) {
                         return;
                    }
               }

               if (!this.invalidPositions.contains(pos)) {
                    if (mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + (double)mc.player.getEyeHeight(), mc.player.posZ), new Vec3d((double)pos.getX(), (double)pos.getY(), (double)pos.getZ()), false, true, false) != null && !this.throughWalls.getBoolValue()) {
                         return;
                    }

                    float[] rots = RotationHelper.getRotationVector(new Vec3d((double)pos.getX() + 0.5D, (double)pos.getY() + 1.4D, (double)pos.getZ() + 0.5D), true, 2.0F, 2.0F, 360.0F);
                    event.setYaw(rots[0]);
                    event.setPitch(rots[1]);
                    mc.player.renderYawOffset = rots[0];
                    mc.player.rotationYawHead = rots[0];
                    mc.player.rotationPitchHead = rots[1];
                    mc.player.inventory.currentItem = getSlotWithBlock();
                    mc.playerController.processRightClickBlock(mc.player, mc.world, pos, EnumFacing.UP, new Vec3d((double)pos.getX(), (double)pos.getY(), (double)pos.getZ()), EnumHand.MAIN_HAND);
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    mc.player.inventory.currentItem = oldSlot;
                    this.timerHelper.reset();
               }
          }

     }
}
