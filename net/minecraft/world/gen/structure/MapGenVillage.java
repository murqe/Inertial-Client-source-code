package net.minecraft.world.gen.structure;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.init.Biomes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MapGenVillage extends MapGenStructure {
      public static final List VILLAGE_SPAWN_BIOMES;
      private int size;
      private int distance;
      private final int minTownSeparation;

      public MapGenVillage() {
            this.distance = 32;
            this.minTownSeparation = 8;
      }

      public MapGenVillage(Map map) {
            this();
            Iterator var2 = map.entrySet().iterator();

            while(var2.hasNext()) {
                  Entry entry = (Entry)var2.next();
                  if (((String)entry.getKey()).equals("size")) {
                        this.size = MathHelper.getInt((String)((String)entry.getValue()), this.size, 0);
                  } else if (((String)entry.getKey()).equals("distance")) {
                        this.distance = MathHelper.getInt((String)((String)entry.getValue()), this.distance, 9);
                  }
            }

      }

      public String getStructureName() {
            return "Village";
      }

      protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
            int i = chunkX;
            int j = chunkZ;
            if (chunkX < 0) {
                  chunkX -= this.distance - 1;
            }

            if (chunkZ < 0) {
                  chunkZ -= this.distance - 1;
            }

            int k = chunkX / this.distance;
            int l = chunkZ / this.distance;
            Random random = this.worldObj.setRandomSeed(k, l, 10387312);
            k *= this.distance;
            l *= this.distance;
            k += random.nextInt(this.distance - 8);
            l += random.nextInt(this.distance - 8);
            if (i == k && j == l) {
                  boolean flag = this.worldObj.getBiomeProvider().areBiomesViable(i * 16 + 8, j * 16 + 8, 0, VILLAGE_SPAWN_BIOMES);
                  if (flag) {
                        return true;
                  }
            }

            return false;
      }

      public BlockPos getClosestStrongholdPos(World worldIn, BlockPos pos, boolean p_180706_3_) {
            this.worldObj = worldIn;
            return func_191069_a(worldIn, this, pos, this.distance, 8, 10387312, false, 100, p_180706_3_);
      }

      protected StructureStart getStructureStart(int chunkX, int chunkZ) {
            return new MapGenVillage.Start(this.worldObj, this.rand, chunkX, chunkZ, this.size);
      }

      static {
            VILLAGE_SPAWN_BIOMES = Arrays.asList(Biomes.PLAINS, Biomes.DESERT, Biomes.SAVANNA, Biomes.TAIGA);
      }

      public static class Start extends StructureStart {
            private boolean hasMoreThanTwoComponents;

            public Start() {
            }

            public Start(World worldIn, Random rand, int x, int z, int size) {
                  super(x, z);
                  List list = StructureVillagePieces.getStructureVillageWeightedPieceList(rand, size);
                  StructureVillagePieces.Start structurevillagepieces$start = new StructureVillagePieces.Start(worldIn.getBiomeProvider(), 0, rand, (x << 4) + 2, (z << 4) + 2, list, size);
                  this.components.add(structurevillagepieces$start);
                  structurevillagepieces$start.buildComponent(structurevillagepieces$start, this.components, rand);
                  List list1 = structurevillagepieces$start.pendingRoads;
                  List list2 = structurevillagepieces$start.pendingHouses;

                  int k;
                  while(!list1.isEmpty() || !list2.isEmpty()) {
                        StructureComponent structurecomponent;
                        if (list1.isEmpty()) {
                              k = rand.nextInt(list2.size());
                              structurecomponent = (StructureComponent)list2.remove(k);
                              structurecomponent.buildComponent(structurevillagepieces$start, this.components, rand);
                        } else {
                              k = rand.nextInt(list1.size());
                              structurecomponent = (StructureComponent)list1.remove(k);
                              structurecomponent.buildComponent(structurevillagepieces$start, this.components, rand);
                        }
                  }

                  this.updateBoundingBox();
                  k = 0;
                  Iterator var13 = this.components.iterator();

                  while(var13.hasNext()) {
                        StructureComponent structurecomponent1 = (StructureComponent)var13.next();
                        if (!(structurecomponent1 instanceof StructureVillagePieces.Road)) {
                              ++k;
                        }
                  }

                  this.hasMoreThanTwoComponents = k > 2;
            }

            public boolean isSizeableStructure() {
                  return this.hasMoreThanTwoComponents;
            }

            public void writeToNBT(NBTTagCompound tagCompound) {
                  super.writeToNBT(tagCompound);
                  tagCompound.setBoolean("Valid", this.hasMoreThanTwoComponents);
            }

            public void readFromNBT(NBTTagCompound tagCompound) {
                  super.readFromNBT(tagCompound);
                  this.hasMoreThanTwoComponents = tagCompound.getBoolean("Valid");
            }
      }
}
