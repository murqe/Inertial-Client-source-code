package wtf.rich.api.utils.render;

import net.minecraft.client.Minecraft;

public class Translate {
     private float x;
     private float y;
     private long lastMS;

     public Translate(float x, float y) {
          this.x = x;
          this.y = y;
          this.lastMS = System.currentTimeMillis();
     }

     public void interpolate(float targetX, float targetY, int xSpeed, int ySpeed) {
          long currentMS = System.currentTimeMillis();
          long delta = currentMS - this.lastMS;
          this.lastMS = currentMS;
          int deltaX = (int)(Math.abs(targetX - this.x) * 0.51F);
          int deltaY = (int)(Math.abs(targetY - this.y) * 0.51F);
          this.x = AnimationHelper.calculateCompensation(targetX, this.x, delta, (double)deltaX);
          this.y = AnimationHelper.calculateCompensation(targetY, this.y, delta, (double)deltaY);
     }

     public final void interpolate(float targetX, float targetY, float smoothing) {
          this.x = (float)AnimationHelper.animate((double)targetX, (double)this.x, (double)smoothing);
          this.y = (float)AnimationHelper.animate((double)targetY, (double)this.y, (double)smoothing);
     }

     public void interpolate(float targetX, float targetY, double speedX, double speedY) {
          long currentMS = System.currentTimeMillis();
          long delta = currentMS - this.lastMS;
          this.lastMS = currentMS;
          double deltaX = 0.0D;
          double deltaY = 0.0D;
          if (speedX != 0.0D || speedY != 0.0D) {
               deltaX = (double)(Math.abs(targetX - this.x) * 0.35F) / (10.0D / speedX);
               deltaY = (double)(Math.abs(targetY - this.y) * 0.35F) / (10.0D / speedY);
          }

          this.x = AnimationHelper.calculateCompensation(targetX, this.x, delta, deltaX);
          this.y = AnimationHelper.calculateCompensation(targetY, this.y, delta, deltaY);
     }

     public void arrayListAnim(float targetX, float targetY, float xSpeed, float ySpeed) {
          int deltaX = (int)(Math.abs(targetX - this.x) * xSpeed);
          int deltaY = (int)(Math.abs(targetY - this.y) * ySpeed);
          this.x = AnimationHelper.calculateCompensation(targetX, this.x, (long)Minecraft.frameTime, (double)deltaX);
          this.y = AnimationHelper.calculateCompensation(targetY, this.y, (long)Minecraft.frameTime, (double)deltaY);
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
