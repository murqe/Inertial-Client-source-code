package wtf.rich.api.event.event;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;
import wtf.rich.api.event.Event;
import wtf.rich.api.utils.combat.LPositionHelper;

public class EventPreMotionUpdate extends Event {
     private boolean cancel;
     public float yaw;
     public float pitch;
     public double z;
     public double y;
     public double x;
     public boolean ground;
     private CPacketPlayer.Rotation rotation;
     private LPositionHelper location;

     public EventPreMotionUpdate(float yaw, float pitch, double y, LPositionHelper location) {
          this.yaw = yaw;
          this.pitch = pitch;
          this.y = y;
          this.z = this.z;
          this.x = this.x;
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
          Minecraft.getMinecraft().player.renderYawOffset = yaw;
          Minecraft.getMinecraft().player.rotationYawHead = yaw;
          this.yaw = yaw;
     }

     public float getPitch() {
          return this.pitch;
     }

     public void setPitch(float pitch) {
          Minecraft.getMinecraft().player.rotationPitchHead = pitch;
          this.pitch = pitch;
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

     public void setY(double y) {
          this.y = y;
     }

     public EventPlayerMotionUpdate getLocation() {
          return null;
     }

     public boolean onGround() {
          return this.ground;
     }

     public void setGround(boolean ground) {
          this.ground = ground;
     }
}
