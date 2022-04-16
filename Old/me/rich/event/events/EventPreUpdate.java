package me.rich.event.events;

import me.rich.event.Event;

public class EventPreUpdate extends Event {
      double x;
      double y;
      double minY;
      double z;
      float yaw;
      float pitch;
      boolean onGround;

      public EventPreUpdate(double x, double y, double z, float yaw, float pitch, boolean onGround, double minY) {
            this.x = x;
            this.onGround = onGround;
            this.pitch = pitch;
            this.yaw = yaw;
            this.y = y;
            this.z = z;
            this.minY = minY;
      }

      public double getMinY() {
            return this.minY;
      }

      public void setMinY(double minY) {
            this.minY = minY;
      }

      public double getX() {
            return this.x;
      }

      public void setX(double x) {
            this.x = x;
      }

      public boolean isOnGround() {
            return this.onGround;
      }

      public void setOnGround(boolean onGround) {
            this.onGround = onGround;
      }

      public float getPitch() {
            return this.pitch;
      }

      public void setPitch(float pitch) {
            this.pitch = pitch;
      }

      public float getYaw() {
            return this.yaw;
      }

      public void setYaw(float yaw) {
            this.yaw = yaw;
      }

      public double getZ() {
            return this.z;
      }

      public void setZ(double z) {
            this.z = z;
      }

      public double getY() {
            return this.y;
      }

      public void setY(double y) {
            this.y = y;
      }
}
