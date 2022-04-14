package net.minecraft.block.state;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MapPopulator;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Cartesian;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IUnlistedProperty;
import optifine.BlockModelUtils;
import optifine.Reflector;

public class BlockStateContainer {
      private static final Pattern NAME_PATTERN = Pattern.compile("^[a-z0-9_]+$");
      private static final Function GET_NAME_FUNC = new Function() {
            @Nullable
            public String apply(@Nullable IProperty p_apply_1_) {
                  return p_apply_1_ == null ? "<NULL>" : p_apply_1_.getName();
            }
      };
      private final Block block;
      private final ImmutableSortedMap properties;
      private final ImmutableList validStates;

      public BlockStateContainer(Block blockIn, IProperty... properties) {
            this(blockIn, properties, (ImmutableMap)null);
      }

      protected BlockStateContainer.StateImplementation createState(Block p_createState_1_, ImmutableMap p_createState_2_, @Nullable ImmutableMap p_createState_3_) {
            return new BlockStateContainer.StateImplementation(p_createState_1_, p_createState_2_);
      }

      protected BlockStateContainer(Block p_i9_1_, IProperty[] p_i9_2_, ImmutableMap p_i9_3_) {
            this.block = p_i9_1_;
            Map map = Maps.newHashMap();
            IProperty[] var5 = p_i9_2_;
            int var6 = p_i9_2_.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                  IProperty iproperty = var5[var7];
                  validateProperty(p_i9_1_, iproperty);
                  map.put(iproperty.getName(), iproperty);
            }

            this.properties = ImmutableSortedMap.copyOf(map);
            Map map2 = Maps.newLinkedHashMap();
            List list = Lists.newArrayList();
            Iterator var13 = Cartesian.cartesianProduct(this.getAllowedValues()).iterator();

            while(var13.hasNext()) {
                  List list1 = (List)var13.next();
                  Map map1 = MapPopulator.createMap(this.properties.values(), list1);
                  BlockStateContainer.StateImplementation blockstatecontainer$stateimplementation = this.createState(p_i9_1_, ImmutableMap.copyOf(map1), p_i9_3_);
                  map2.put(map1, blockstatecontainer$stateimplementation);
                  list.add(blockstatecontainer$stateimplementation);
            }

            var13 = list.iterator();

            while(var13.hasNext()) {
                  BlockStateContainer.StateImplementation blockstatecontainer$stateimplementation1 = (BlockStateContainer.StateImplementation)var13.next();
                  blockstatecontainer$stateimplementation1.buildPropertyValueTable(map2);
            }

            this.validStates = ImmutableList.copyOf(list);
      }

      public static String validateProperty(Block block, IProperty property) {
            String s = property.getName();
            if (!NAME_PATTERN.matcher(s).matches()) {
                  throw new IllegalArgumentException("Block: " + block.getClass() + " has invalidly named property: " + s);
            } else {
                  Iterator var3 = property.getAllowedValues().iterator();

                  String s1;
                  do {
                        if (!var3.hasNext()) {
                              return s;
                        }

                        Comparable t = (Comparable)var3.next();
                        s1 = property.getName(t);
                  } while(NAME_PATTERN.matcher(s1).matches());

                  throw new IllegalArgumentException("Block: " + block.getClass() + " has property: " + s + " with invalidly named value: " + s1);
            }
      }

      public ImmutableList getValidStates() {
            return this.validStates;
      }

      private List getAllowedValues() {
            List list = Lists.newArrayList();
            ImmutableCollection immutablecollection = this.properties.values();
            UnmodifiableIterator unmodifiableiterator = immutablecollection.iterator();

            while(unmodifiableiterator.hasNext()) {
                  IProperty iproperty = (IProperty)unmodifiableiterator.next();
                  list.add(iproperty.getAllowedValues());
            }

            return list;
      }

      public IBlockState getBaseState() {
            return (IBlockState)this.validStates.get(0);
      }

      public Block getBlock() {
            return this.block;
      }

      public Collection getProperties() {
            return this.properties.values();
      }

      public String toString() {
            return MoreObjects.toStringHelper(this).add("block", Block.REGISTRY.getNameForObject(this.block)).add("properties", Iterables.transform(this.properties.values(), GET_NAME_FUNC)).toString();
      }

      @Nullable
      public IProperty getProperty(String propertyName) {
            return (IProperty)this.properties.get(propertyName);
      }

      static class StateImplementation extends BlockStateBase {
            private final Block block;
            private final ImmutableMap properties;
            private ImmutableTable propertyValueTable;

            private StateImplementation(Block blockIn, ImmutableMap propertiesIn) {
                  this.block = blockIn;
                  this.properties = propertiesIn;
            }

            protected StateImplementation(Block p_i8_1_, ImmutableMap p_i8_2_, ImmutableTable p_i8_3_) {
                  this.block = p_i8_1_;
                  this.properties = p_i8_2_;
                  this.propertyValueTable = p_i8_3_;
            }

            public Collection getPropertyNames() {
                  return Collections.unmodifiableCollection(this.properties.keySet());
            }

            public Comparable getValue(IProperty property) {
                  Comparable comparable = (Comparable)this.properties.get(property);
                  if (comparable == null) {
                        throw new IllegalArgumentException("Cannot get property " + property + " as it does not exist in " + this.block.getBlockState());
                  } else {
                        return (Comparable)property.getValueClass().cast(comparable);
                  }
            }

            public IBlockState withProperty(IProperty property, Comparable value) {
                  Comparable comparable = (Comparable)this.properties.get(property);
                  if (comparable == null) {
                        throw new IllegalArgumentException("Cannot set property " + property + " as it does not exist in " + this.block.getBlockState());
                  } else if (comparable == value) {
                        return this;
                  } else {
                        IBlockState iblockstate = (IBlockState)this.propertyValueTable.get(property, value);
                        if (iblockstate == null) {
                              throw new IllegalArgumentException("Cannot set property " + property + " to " + value + " on block " + Block.REGISTRY.getNameForObject(this.block) + ", it is not an allowed value");
                        } else {
                              return iblockstate;
                        }
                  }
            }

            public ImmutableMap getProperties() {
                  return this.properties;
            }

            public Block getBlock() {
                  return this.block;
            }

            public boolean equals(Object p_equals_1_) {
                  return this == p_equals_1_;
            }

            public int hashCode() {
                  return this.properties.hashCode();
            }

            public void buildPropertyValueTable(Map map) {
                  if (this.propertyValueTable != null) {
                        throw new IllegalStateException();
                  } else {
                        Table table = HashBasedTable.create();
                        UnmodifiableIterator unmodifiableiterator = this.properties.entrySet().iterator();

                        while(unmodifiableiterator.hasNext()) {
                              Entry entry = (Entry)unmodifiableiterator.next();
                              IProperty iproperty = (IProperty)entry.getKey();
                              Iterator var6 = iproperty.getAllowedValues().iterator();

                              while(var6.hasNext()) {
                                    Comparable comparable = (Comparable)var6.next();
                                    if (comparable != entry.getValue()) {
                                          table.put(iproperty, comparable, map.get(this.getPropertiesWithValue(iproperty, comparable)));
                                    }
                              }
                        }

                        this.propertyValueTable = ImmutableTable.copyOf(table);
                  }
            }

            private Map getPropertiesWithValue(IProperty property, Comparable value) {
                  Map map = Maps.newHashMap(this.properties);
                  map.put(property, value);
                  return map;
            }

            public Material getMaterial() {
                  return this.block.getMaterial(this);
            }

            public boolean isFullBlock() {
                  return this.block.isFullBlock(this);
            }

            public boolean canEntitySpawn(Entity entityIn) {
                  return this.block.canEntitySpawn(this, entityIn);
            }

            public int getLightOpacity() {
                  return this.block.getLightOpacity(this);
            }

            public int getLightValue() {
                  return this.block.getLightValue(this);
            }

            public boolean isTranslucent() {
                  return this.block.isTranslucent(this);
            }

            public boolean useNeighborBrightness() {
                  return this.block.getUseNeighborBrightness(this);
            }

            public MapColor getMapColor(IBlockAccess p_185909_1_, BlockPos p_185909_2_) {
                  return this.block.getMapColor(this, p_185909_1_, p_185909_2_);
            }

            public IBlockState withRotation(Rotation rot) {
                  return this.block.withRotation(this, rot);
            }

            public IBlockState withMirror(Mirror mirrorIn) {
                  return this.block.withMirror(this, mirrorIn);
            }

            public boolean isFullCube() {
                  return this.block.isFullCube(this);
            }

            public boolean func_191057_i() {
                  return this.block.func_190946_v(this);
            }

            public EnumBlockRenderType getRenderType() {
                  return this.block.getRenderType(this);
            }

            public int getPackedLightmapCoords(IBlockAccess source, BlockPos pos) {
                  return this.block.getPackedLightmapCoords(this, source, pos);
            }

            public float getAmbientOcclusionLightValue() {
                  return this.block.getAmbientOcclusionLightValue(this);
            }

            public boolean isBlockNormalCube() {
                  return this.block.isBlockNormalCube(this);
            }

            public boolean isNormalCube() {
                  return this.block.isNormalCube(this);
            }

            public boolean canProvidePower() {
                  return this.block.canProvidePower(this);
            }

            public int getWeakPower(IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
                  return this.block.getWeakPower(this, blockAccess, pos, side);
            }

            public boolean hasComparatorInputOverride() {
                  return this.block.hasComparatorInputOverride(this);
            }

            public int getComparatorInputOverride(World worldIn, BlockPos pos) {
                  return this.block.getComparatorInputOverride(this, worldIn, pos);
            }

            public float getBlockHardness(World worldIn, BlockPos pos) {
                  return this.block.getBlockHardness(this, worldIn, pos);
            }

            public float getPlayerRelativeBlockHardness(EntityPlayer player, World worldIn, BlockPos pos) {
                  return this.block.getPlayerRelativeBlockHardness(this, player, worldIn, pos);
            }

            public int getStrongPower(IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
                  return this.block.getStrongPower(this, blockAccess, pos, side);
            }

            public EnumPushReaction getMobilityFlag() {
                  return this.block.getMobilityFlag(this);
            }

            public IBlockState getActualState(IBlockAccess blockAccess, BlockPos pos) {
                  return this.block.getActualState(this, blockAccess, pos);
            }

            public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
                  return this.block.getSelectedBoundingBox(this, worldIn, pos);
            }

            public boolean shouldSideBeRendered(IBlockAccess blockAccess, BlockPos pos, EnumFacing facing) {
                  return this.block.shouldSideBeRendered(this, blockAccess, pos, facing);
            }

            public boolean isOpaqueCube() {
                  return this.block.isOpaqueCube(this);
            }

            @Nullable
            public AxisAlignedBB getCollisionBoundingBox(IBlockAccess worldIn, BlockPos pos) {
                  return this.block.getCollisionBoundingBox(this, worldIn, pos);
            }

            public void addCollisionBoxToList(World worldIn, BlockPos pos, AxisAlignedBB p_185908_3_, List p_185908_4_, @Nullable Entity p_185908_5_, boolean p_185908_6_) {
                  this.block.addCollisionBoxToList(this, worldIn, pos, p_185908_3_, p_185908_4_, p_185908_5_, p_185908_6_);
            }

            public AxisAlignedBB getBoundingBox(IBlockAccess blockAccess, BlockPos pos) {
                  Block.EnumOffsetType block$enumoffsettype = this.block.getOffsetType();
                  if (block$enumoffsettype != Block.EnumOffsetType.NONE && !(this.block instanceof BlockFlower)) {
                        AxisAlignedBB axisalignedbb = this.block.getBoundingBox(this, blockAccess, pos);
                        axisalignedbb = BlockModelUtils.getOffsetBoundingBox(axisalignedbb, block$enumoffsettype, pos);
                        return axisalignedbb;
                  } else {
                        return this.block.getBoundingBox(this, blockAccess, pos);
                  }
            }

            public RayTraceResult collisionRayTrace(World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
                  return this.block.collisionRayTrace(this, worldIn, pos, start, end);
            }

            public boolean isFullyOpaque() {
                  return this.block.isFullyOpaque(this);
            }

            public Vec3d func_191059_e(IBlockAccess p_191059_1_, BlockPos p_191059_2_) {
                  return this.block.func_190949_e(this, p_191059_1_, p_191059_2_);
            }

            public boolean onBlockEventReceived(World worldIn, BlockPos pos, int id, int param) {
                  return this.block.eventReceived(this, worldIn, pos, id, param);
            }

            public void neighborChanged(World worldIn, BlockPos pos, Block blockIn, BlockPos p_189546_4_) {
                  this.block.neighborChanged(this, worldIn, pos, blockIn, p_189546_4_);
            }

            public boolean func_191058_s() {
                  return this.block.causesSuffocation(this);
            }

            public ImmutableTable getPropertyValueTable() {
                  return this.propertyValueTable;
            }

            public int getLightOpacity(IBlockAccess p_getLightOpacity_1_, BlockPos p_getLightOpacity_2_) {
                  return Reflector.callInt(this.block, Reflector.ForgeBlock_getLightOpacity, this, p_getLightOpacity_1_, p_getLightOpacity_2_);
            }

            public int getLightValue(IBlockAccess p_getLightValue_1_, BlockPos p_getLightValue_2_) {
                  return Reflector.callInt(this.block, Reflector.ForgeBlock_getLightValue, this, p_getLightValue_1_, p_getLightValue_2_);
            }

            public boolean isSideSolid(IBlockAccess p_isSideSolid_1_, BlockPos p_isSideSolid_2_, EnumFacing p_isSideSolid_3_) {
                  return Reflector.callBoolean(this.block, Reflector.ForgeBlock_isSideSolid, this, p_isSideSolid_1_, p_isSideSolid_2_, p_isSideSolid_3_);
            }

            public boolean doesSideBlockRendering(IBlockAccess p_doesSideBlockRendering_1_, BlockPos p_doesSideBlockRendering_2_, EnumFacing p_doesSideBlockRendering_3_) {
                  return Reflector.callBoolean(this.block, Reflector.ForgeBlock_doesSideBlockRendering, this, p_doesSideBlockRendering_1_, p_doesSideBlockRendering_2_, p_doesSideBlockRendering_3_);
            }

            public BlockFaceShape func_193401_d(IBlockAccess p_193401_1_, BlockPos p_193401_2_, EnumFacing p_193401_3_) {
                  return this.block.func_193383_a(p_193401_1_, this, p_193401_2_, p_193401_3_);
            }

            // $FF: synthetic method
            StateImplementation(Block x0, ImmutableMap x1, Object x2) {
                  this(x0, x1);
            }
      }

      public static class Builder {
            private final Block block;
            private final List listed = Lists.newArrayList();
            private final List unlisted = Lists.newArrayList();

            public Builder(Block p_i11_1_) {
                  this.block = p_i11_1_;
            }

            public BlockStateContainer.Builder add(IProperty... p_add_1_) {
                  IProperty[] var2 = p_add_1_;
                  int var3 = p_add_1_.length;

                  for(int var4 = 0; var4 < var3; ++var4) {
                        IProperty iproperty = var2[var4];
                        this.listed.add(iproperty);
                  }

                  return this;
            }

            public BlockStateContainer.Builder add(IUnlistedProperty... p_add_1_) {
                  IUnlistedProperty[] var2 = p_add_1_;
                  int var3 = p_add_1_.length;

                  for(int var4 = 0; var4 < var3; ++var4) {
                        IUnlistedProperty iunlistedproperty = var2[var4];
                        this.unlisted.add(iunlistedproperty);
                  }

                  return this;
            }

            public BlockStateContainer build() {
                  IProperty[] iproperty = new IProperty[this.listed.size()];
                  iproperty = (IProperty[])((IProperty[])this.listed.toArray(iproperty));
                  if (this.unlisted.size() == 0) {
                        return new BlockStateContainer(this.block, iproperty);
                  } else {
                        IUnlistedProperty[] iunlistedproperty = new IUnlistedProperty[this.unlisted.size()];
                        iunlistedproperty = (IUnlistedProperty[])((IUnlistedProperty[])this.unlisted.toArray(iunlistedproperty));
                        return (BlockStateContainer)Reflector.newInstance(Reflector.ExtendedBlockState_Constructor, this.block, iproperty, iunlistedproperty);
                  }
            }
      }
}
