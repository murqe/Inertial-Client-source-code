package me.rich.event.events;

import me.rich.event.Event;
import me.rich.helpers.combat.LPositionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;

public class EventPreMotionUpdate extends Event {
      private boolean cancel;
      public float yaw;
      public float pitch;
      public double y;
      public boolean ground;
      private CPacketPlayer.Rotation rotation;
      private LPositionHelper location;
      private boolean onGround;

      public EventPreMotionUpdate(float yaw, float pitch, double y, LPositionHelper location) {
            this.yaw = yaw;
            this.pitch = pitch;
            this.y = y;
            this.location = location;
      }

      public boolean isCancel() {
            return this.cancel;
      }

      public void setCancel(boolean cancel) {
            this.cancel = cancel;
      }

      public CPacketPlayer.Rotation getRotation() {
            return this.rotation;
      }

      public float getYaw() {
            return this.yaw;
      }

      public void setYaw(float yaw) {
            Minecraft.getMinecraft();
            Minecraft.player.renderYawOffset = yaw;
            Minecraft.getMinecraft();
            Minecraft.player.rotationYawHead = yaw;
            this.yaw = yaw;
      }

      public float getPitch() {
            return this.pitch;
      }

      public void setPitch(float pitch) {
            Minecraft.getMinecraft();
            Minecraft.player.rotationPitchHead = pitch;
            this.pitch = pitch;
      }

      public double getY() {
            return this.y;
      }

      public void setY(double y) {
            this.y = y;
      }

      public EventPlayerMotionUpdate getLocation() {
            return null;
      }

      public double getLegitMotion() {
            return 0.41999998688697815D;
      }

      public boolean onGround() {
            return this.ground;
      }

      public void setOnGround(boolean onGround) {
            this.onGround = onGround;
      }
}
