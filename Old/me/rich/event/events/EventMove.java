package me.rich.event.events;

import me.rich.event.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;

public class EventMove extends Event {
      private double x;
      private double y;
      private double z;
      public float strafe;
      public float forward;
      public float friction;
      public float yaw;
      public boolean canceled;

      public EventMove(double x, double y, double z) {
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
            if (Minecraft.player.isPotionActive(Potion.getPotionById(1))) {
                  Minecraft.getMinecraft();
                  int amplifier = Minecraft.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
                  baseSpeed *= 1.0D + 0.2D * (double)(amplifier + 1);
            }

            return baseSpeed;
      }

      public double getMovementSpeed(double baseSpeed) {
            Minecraft.getMinecraft();
            if (Minecraft.player.isPotionActive(Potion.getPotionById(1))) {
                  Minecraft.getMinecraft();
                  int amplifier = Minecraft.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
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
            if (Minecraft.player.isPotionActive(Potion.getPotionById(8))) {
                  Minecraft.getMinecraft();
                  mY += (double)(Minecraft.player.getActivePotionEffect(Potion.getPotionById(8)).getAmplifier() + 1) * 0.1D;
            }

            return mY;
      }
}
