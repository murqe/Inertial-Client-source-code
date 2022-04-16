package wtf.rich.api.utils.render;

import net.minecraft.client.Minecraft;

public class AnimationHelper {
     public static int deltaTime;
     public static float speedTarget = 0.125F;
     private int time;
     private AnimationHelper.State previousState;
     private AnimationHelper.State currentState;
     private long currentStateStart;
     private boolean initialState;

     public AnimationHelper(int time, boolean initialState) {
          this.previousState = AnimationHelper.State.STATIC;
          this.currentState = AnimationHelper.State.STATIC;
          this.currentStateStart = 0L;
          this.time = time;
          this.initialState = initialState;
          if (initialState) {
               this.previousState = AnimationHelper.State.EXPANDING;
          }

     }

     public static float animation(float current, float targetAnimation, float speedTarget) {
          float da = (targetAnimation - current) / (float)Minecraft.getDebugFPS() * 15.0F;
          if (da > 0.0F) {
               da = Math.max(speedTarget, da);
               da = Math.min(targetAnimation - current, da);
          } else if (da < 0.0F) {
               da = Math.min(-speedTarget, da);
               da = Math.max(targetAnimation - current, da);
          }

          return current + da;
     }

     public double getAnimationFactor() {
          if (this.currentState == AnimationHelper.State.EXPANDING) {
               return (double)(System.currentTimeMillis() - this.currentStateStart) / (double)this.time;
          } else if (this.currentState == AnimationHelper.State.RETRACTING) {
               return (double)((long)this.time - (System.currentTimeMillis() - this.currentStateStart)) / (double)this.time;
          } else {
               return this.previousState == AnimationHelper.State.EXPANDING ? 1.0D : 0.0D;
          }
     }

     public static double animate(double target, double current, double speed) {
          boolean bl = target > current;
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

          current = bl ? current + factor : current - factor;
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

     public static enum State {
          EXPANDING,
          RETRACTING,
          STATIC;
     }
}
