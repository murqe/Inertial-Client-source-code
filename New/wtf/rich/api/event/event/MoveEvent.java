package wtf.rich.api.event.event;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import wtf.rich.api.event.Event;

public class MoveEvent extends Event {
     private double x;
     private double y;
     private double z;
     public float strafe;
     public float forward;
     public float friction;
     public float yaw;
     public boolean canceled;

     public MoveEvent(double x, double y, double z) {
          this.x = x;
          this.y = y;
          this.z = z;
     }

     public double getX() {
          return this.x;
     }

     public double getY() {
          return this.y;
     }

     public double getZ() {
          return this.z;
     }

     public void setX(double x) {
          this.x = x;
     }

     public void setY(double y) {
          this.y = y;
     }

     public double getMovementSpeed() {
          double baseSpeed = 0.2873D;
          Minecraft.getMinecraft();
          if (Minecraft.getMinecraft().player.isPotionActive(Potion.getPotionById(1))) {
               Minecraft.getMinecraft();
               int amplifier = Minecraft.getMinecraft().player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
               baseSpeed *= 1.0D + 0.2D * (double)(amplifier + 1);
          }

          return baseSpeed;
     }

     public double getMovementSpeed(double baseSpeed) {
          Minecraft.getMinecraft();
          if (Minecraft.getMinecraft().player.isPotionActive(Potion.getPotionById(1))) {
               Minecraft.getMinecraft();
               int amplifier = Minecraft.getMinecraft().player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
               return baseSpeed * (1.0D + 0.2D * (double)(amplifier + 1));
          } else {
               return baseSpeed;
          }
     }

     public void setZ(double z) {
          this.z = z;
     }

     public double getLegitMotion() {
          return 0.41999998688697815D;
     }

     public double getMotionY(double mY) {
          Minecraft.getMinecraft();
          if (Minecraft.getMinecraft().player.isPotionActive(Potion.getPotionById(8))) {
               Minecraft.getMinecraft();
               mY += (double)(Minecraft.getMinecraft().player.getActivePotionEffect(Potion.getPotionById(8)).getAmplifier() + 1) * 0.1D;
          }

          return mY;
     }
}
