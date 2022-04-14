package net.minecraft.block;

import java.util.Iterator;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPressurePlate extends BlockBasePressurePlate {
      public static final PropertyBool POWERED = PropertyBool.create("powered");
      private final BlockPressurePlate.Sensitivity sensitivity;

      protected BlockPressurePlate(Material materialIn, BlockPressurePlate.Sensitivity sensitivityIn) {
            super(materialIn);
            this.setDefaultState(this.blockState.getBaseState().withProperty(POWERED, false));
            this.sensitivity = sensitivityIn;
      }

      protected int getRedstoneStrength(IBlockState state) {
            return (Boolean)state.getValue(POWERED) ? 15 : 0;
      }

      protected IBlockState setRedstoneStrength(IBlockState state, int strength) {
            return state.withProperty(POWERED, strength > 0);
      }

      protected void playClickOnSound(World worldIn, BlockPos color) {
            if (this.blockMaterial == Material.WOOD) {
                  worldIn.playSound((EntityPlayer)null, color, SoundEvents.BLOCK_WOOD_PRESSPLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.8F);
            } else {
                  worldIn.playSound((EntityPlayer)null, color, SoundEvents.BLOCK_STONE_PRESSPLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
            }

      }

      protected void playClickOffSound(World worldIn, BlockPos pos) {
            if (this.blockMaterial == Material.WOOD) {
                  worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_WOOD_PRESSPLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.7F);
            } else {
                  worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_STONE_PRESSPLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.5F);
            }

      }

      protected int computeRedstoneStrength(World worldIn, BlockPos pos) {
            AxisAlignedBB axisalignedbb = PRESSURE_AABB.offset(pos);
            List list;
            switch(this.sensitivity) {
            case EVERYTHING:
                  list = worldIn.getEntitiesWithinAABBExcludingEntity((Entity)null, axisalignedbb);
                  break;
            case MOBS:
                  list = worldIn.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
                  break;
            default:
                  return 0;
            }

            if (!list.isEmpty()) {
                  Iterator var5 = list.iterator();

                  while(var5.hasNext()) {
                        Entity entity = (Entity)var5.next();
                        if (!entity.doesEntityNotTriggerPressurePlate()) {
                              return 15;
                        }
                  }
            }

            return 0;
      }

      public IBlockState getStateFromMeta(int meta) {
            return this.getDefaultState().withProperty(POWERED, meta == 1);
      }

      public int getMetaFromState(IBlockState state) {
            return (Boolean)state.getValue(POWERED) ? 1 : 0;
      }

      protected BlockStateContainer createBlockState() {
            return new BlockStateContainer(this, new IProperty[]{POWERED});
      }

      public static enum Sensitivity {
            EVERYTHING,
            MOBS;
      }
}
