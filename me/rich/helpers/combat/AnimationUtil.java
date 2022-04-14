package me.rich.helpers.combat;

import net.minecraft.client.Minecraft;

public class AnimationUtil {
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
}
