package wtf.rich.api.utils.render;

import net.minecraft.util.math.Vec3d;

public class BreadcrumbHelper {
     Vec3d vector;
     BreadcrumbHelper.TimeHelper timer = new BreadcrumbHelper.TimeHelper();

     public BreadcrumbHelper(Vec3d vector) {
          this.vector = vector;
     }

     public BreadcrumbHelper.TimeHelper getTimer() {
          return this.timer;
     }

     public Vec3d getVector() {
          return this.vector;
     }

     public class TimeHelper {
          long ms;

          public TimeHelper() {
               this.reset();
          }

          public boolean hasReached(long mils) {
               return System.currentTimeMillis() - this.ms >= mils;
          }

          public long getMS() {
               return this.ms;
          }

          public void reset() {
               this.ms = System.currentTimeMillis();
          }
     }
}
