package me.rich.helpers.render;

import net.minecraft.util.math.Vec3d;

public class TrailsHelper {
      Vec3d vector;
      TrailsHelper.TimeHelper timer = new TrailsHelper.TimeHelper();

      public TrailsHelper(Vec3d vector) {
            this.vector = vector;
      }

      public TrailsHelper.TimeHelper getTimer() {
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
