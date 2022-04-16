package wtf.rich.api.utils.combat;

import java.util.Random;

public final class CountHelper {
     public static int nextInt(int startInclusive, int endExclusive) {
          return startInclusive != endExclusive && endExclusive - startInclusive > 0 ? startInclusive + getRandom().nextInt(endExclusive - startInclusive) : startInclusive;
     }

     public static double nextDouble(double startInclusive, double endInclusive) {
          return startInclusive != endInclusive && endInclusive - startInclusive > 0.0D ? startInclusive + (endInclusive - startInclusive) * Math.random() : startInclusive;
     }

     public static float nextFloat(float startInclusive, float endInclusive) {
          return startInclusive != endInclusive && endInclusive - startInclusive > 0.0F ? (float)((double)startInclusive + (double)(endInclusive - startInclusive) * Math.random()) : startInclusive;
     }

     public static String random(int length, String chars) {
          return random(length, chars.toCharArray());
     }

     public static String random(int length, char[] chars) {
          StringBuilder stringBuilder = new StringBuilder();

          for(int i = 0; i < length; ++i) {
               stringBuilder.append(chars[getRandom().nextInt(chars.length)]);
          }

          return stringBuilder.toString();
     }

     public static Random getRandom() {
          return new Random();
     }
}
