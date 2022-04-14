package net.minecraft.world.gen.layer;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;

public class GenLayerRareBiome extends GenLayer {
      public GenLayerRareBiome(long p_i45478_1_, GenLayer p_i45478_3_) {
            super(p_i45478_1_);
            this.parent = p_i45478_3_;
      }

      public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
            int[] aint = this.parent.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
            int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);

            for(int i = 0; i < areaHeight; ++i) {
                  for(int j = 0; j < areaWidth; ++j) {
                        this.initChunkSeed((long)(j + areaX), (long)(i + areaY));
                        int k = aint[j + 1 + (i + 1) * (areaWidth + 2)];
                        if (this.nextInt(57) == 0) {
                              if (k == Biome.getIdForBiome(Biomes.PLAINS)) {
                                    aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.MUTATED_PLAINS);
                              } else {
                                    aint1[j + i * areaWidth] = k;
                              }
                        } else {
                              aint1[j + i * areaWidth] = k;
                        }
                  }
            }

            return aint1;
      }
}