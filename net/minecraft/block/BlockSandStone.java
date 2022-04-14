package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockSandStone extends Block {
      public static final PropertyEnum TYPE = PropertyEnum.create("type", BlockSandStone.EnumType.class);

      public BlockSandStone() {
            super(Material.ROCK);
            this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, BlockSandStone.EnumType.DEFAULT));
            this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      }

      public int damageDropped(IBlockState state) {
            return ((BlockSandStone.EnumType)state.getValue(TYPE)).getMetadata();
      }

      public void getSubBlocks(CreativeTabs itemIn, NonNullList tab) {
            BlockSandStone.EnumType[] var3 = BlockSandStone.EnumType.values();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                  BlockSandStone.EnumType blocksandstone$enumtype = var3[var5];
                  tab.add(new ItemStack(this, 1, blocksandstone$enumtype.getMetadata()));
            }

      }

      public MapColor getMapColor(IBlockState state, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
            return MapColor.SAND;
      }

      public IBlockState getStateFromMeta(int meta) {
            return this.getDefaultState().withProperty(TYPE, BlockSandStone.EnumType.byMetadata(meta));
      }

      public int getMetaFromState(IBlockState state) {
            return ((BlockSandStone.EnumType)state.getValue(TYPE)).getMetadata();
      }

      protected BlockStateContainer createBlockState() {
            return new BlockStateContainer(this, new IProperty[]{TYPE});
      }

      public static enum EnumType implements IStringSerializable {
            DEFAULT(0, "sandstone", "default"),
            CHISELED(1, "chiseled_sandstone", "chiseled"),
            SMOOTH(2, "smooth_sandstone", "smooth");

            private static final BlockSandStone.EnumType[] META_LOOKUP = new BlockSandStone.EnumType[values().length];
            private final int metadata;
            private final String name;
            private final String unlocalizedName;

            private EnumType(int meta, String name, String unlocalizedName) {
                  this.metadata = meta;
                  this.name = name;
                  this.unlocalizedName = unlocalizedName;
            }

            public int getMetadata() {
                  return this.metadata;
            }

            public String toString() {
                  return this.name;
            }

            public static BlockSandStone.EnumType byMetadata(int meta) {
                  if (meta < 0 || meta >= META_LOOKUP.length) {
                        meta = 0;
                  }

                  return META_LOOKUP[meta];
            }

            public String getName() {
                  return this.name;
            }

            public String getUnlocalizedName() {
                  return this.unlocalizedName;
            }

            static {
                  BlockSandStone.EnumType[] var0 = values();
                  int var1 = var0.length;

                  for(int var2 = 0; var2 < var1; ++var2) {
                        BlockSandStone.EnumType blocksandstone$enumtype = var0[var2];
                        META_LOOKUP[blocksandstone$enumtype.getMetadata()] = blocksandstone$enumtype;
                  }

            }
      }
}
