package wtf.rich.client.ui.clickgui;

import net.minecraft.client.Minecraft;
import wtf.rich.api.utils.math.MathematicHelper;
import wtf.rich.api.utils.render.AnimationHelper;

public class ScreenHelper {
     private float x;
     private float y;
     private long lastMS;

     public ScreenHelper(float x, float y) {
          this.x = x;
          this.y = y;
          this.lastMS = System.currentTimeMillis();
     }

     public float bebra(float start, float end, float step) {
          return start + step * (end - start);
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

          if (larger) {
               current += factor;
          } else {
               current -= factor;
          }

          return current;
     }

     public static double progressiveAnimation(double now, double desired, double speed) {
          double dif = Math.abs(now - desired);
          int fps = Minecraft.getDebugFPS();
          if (dif > 0.0D) {
               double animationSpeed = MathematicHelper.round(Math.min(10.0D, Math.max(0.05D, 144.0D / (double)fps * (dif / 10.0D) * speed)), 0.05D);
               if (dif < animationSpeed) {
                    animationSpeed = dif;
               }

               if (now < desired) {
                    return now + animationSpeed;
               }

               if (now > desired) {
                    return now - animationSpeed;
               }
          }

          return now;
     }

     public static double linearAnimation(double now, double desired, double speed) {
          double dif = Math.abs(now - desired);
          int fps = Minecraft.getDebugFPS();
          if (dif > 0.0D) {
               double animationSpeed = MathematicHelper.round(Math.min(10.0D, Math.max(0.005D, 144.0D / (double)fps * speed)), 0.005D);
               if (dif != 0.0D && dif < animationSpeed) {
                    animationSpeed = dif;
               }

               if (now < desired) {
                    return now + animationSpeed;
               }

               if (now > desired) {
                    return now - animationSpeed;
               }
          }

          return now;
     }

     public void interpolate(float targetX, float targetY, double speed) {
          long currentMS = System.currentTimeMillis();
          long delta = currentMS - this.lastMS;
          this.lastMS = currentMS;
          double deltaX = 0.0D;
          double deltaY = 0.0D;
          if (speed != 0.0D) {
               deltaX = (double)(Math.abs(targetX - this.x) * 0.35F) / (10.0D / speed);
               deltaY = (double)(Math.abs(targetY - this.y) * 0.35F) / (10.0D / speed);
          }

          this.x = AnimationHelper.calculateCompensation(targetX, this.x, delta, deltaX);
          this.y = AnimationHelper.calculateCompensation(targetY, this.y, delta, deltaY);
     }

     public float getX() {
          return this.x;
     }

     public void setX(float x) {
          this.x = x;
     }

     public float getY() {
          return this.y;
     }

     public void setY(float y) {
          this.y = y;
     }
}
