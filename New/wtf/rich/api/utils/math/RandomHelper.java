package wtf.rich.api.utils.math;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomHelper {
     private static Random random;

     public static int randomNumber(int max, int min) {
          return Math.round((float)min + (float)Math.random() * (float)(max - min));
     }

     public static float nextFloat(float startInclusive, float endInclusive) {
          return startInclusive != endInclusive && endInclusive - startInclusive > 0.0F ? (float)((double)startInclusive + (double)(endInclusive - startInclusive) * Math.random()) : startInclusive;
     }

     public static double getRandomDouble(double min, double max) {
          return ThreadLocalRandom.current().nextDouble(min, max + 1.0D);
     }

     public static float randomFloat(float min, float max) {
          return min + random.nextFloat() * (max - min);
     }
}
