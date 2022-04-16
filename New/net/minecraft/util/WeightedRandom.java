package net.minecraft.util;

import java.util.List;
import java.util.Random;

public class WeightedRandom {
     public static int getTotalWeight(List collection) {
          int i = 0;
          int j = 0;

          for(int k = collection.size(); j < k; ++j) {
               WeightedRandom.Item weightedrandom$item = (WeightedRandom.Item)collection.get(j);
               i += weightedrandom$item.itemWeight;
          }

          return i;
     }

     public static WeightedRandom.Item getRandomItem(Random random, List collection, int totalWeight) {
          if (totalWeight <= 0) {
               throw new IllegalArgumentException();
          } else {
               int i = random.nextInt(totalWeight);
               return getRandomItem(collection, i);
          }
     }

     public static WeightedRandom.Item getRandomItem(List collection, int weight) {
          int i = 0;

          for(int j = collection.size(); i < j; ++i) {
               WeightedRandom.Item t = (WeightedRandom.Item)collection.get(i);
               weight -= t.itemWeight;
               if (weight < 0) {
                    return t;
               }
          }

          return (WeightedRandom.Item)null;
     }

     public static WeightedRandom.Item getRandomItem(Random random, List collection) {
          return getRandomItem(random, collection, getTotalWeight(collection));
     }

     public static class Item {
          protected int itemWeight;

          public Item(int itemWeightIn) {
               this.itemWeight = itemWeightIn;
          }
     }
}
