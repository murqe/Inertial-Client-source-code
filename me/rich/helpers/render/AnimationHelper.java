package me.rich.helpers.render;

import net.minecraft.client.Minecraft;

public class AnimationHelper {
      public static float speedTarget = 0.125F;

      public static float animation(float current, float targetAnimation, float speed) {
            return animation(current, targetAnimation, speedTarget, speed);
      }

      public static float animation(float animation, float target, float poxyi, float speedTarget) {
            float da = (target - animation) / Math.max((float)Minecraft.getDebugFPS(), 5.0F) * 15.0F;
            if (da > 0.0F) {
                  da = Math.max(speedTarget, da);
                  da = Math.min(target - animation, da);
            } else if (da < 0.0F) {
                  da = Math.min(-speedTarget, da);
                  da = Math.max(target - animation, da);
            }

            return animation + da;
      }

      public static double animate(double target, double current, double speed) {
            boolean larger = target > current;
            if (speed < 0.0D) {
                  speed = 0.0D;
            } else if (speed > 1.0D) {
                  speed = 1.0D;
            }

            double dif = Math.max(target, current) - Math.min(target, current);
            double factor = dif * speed;
            if (factor < 0.1D) {
                  factor = 0.1D;
            }

            current = larger ? current + factor : current - factor;
            return current;
      }

      public static float calculateCompensation(float target, float current, long delta, double speed) {
            float diff = current - target;
            if (delta < 1L) {
                  delta = 1L;
            }

            if (delta > 1000L) {
                  delta = 16L;
            }

            double xD;
            if ((double)diff > speed) {
                  xD = speed * (double)delta / 16.0D < 0.5D ? 0.5D : speed * (double)delta / 16.0D;
                  current = (float)((double)current - xD);
                  if (current < target) {
                        current = target;
                  }
            } else if ((double)diff < -speed) {
                  xD = speed * (double)delta / 16.0D < 0.5D ? 0.5D : speed * (double)delta / 16.0D;
                  current = (float)((double)current + xD);
                  if (current > target) {
                        current = target;
                  }
            } else {
                  current = target;
            }

            return current;
      }
}
