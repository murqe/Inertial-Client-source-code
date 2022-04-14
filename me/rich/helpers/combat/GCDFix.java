package me.rich.helpers.combat;

import net.minecraft.client.Minecraft;

public class GCDFix {
      private float yaw;
      private float pitch;
      static Minecraft mc = Minecraft.getMinecraft();

      public GCDFix(float yaw, float pitch) {
            this.yaw = yaw;
            this.pitch = pitch;
      }

      public static float getFixedRotation(float rot) {
            return getDeltaMouse(rot) * getGCDValue();
      }

      public static float getGCDValue() {
            return (float)((double)getGCD() * 0.15D);
      }

      public static float getGCD() {
            float f1;
            return (f1 = (float)((double)mc.gameSettings.mouseSensitivity * 0.6D + 0.2D)) * f1 * f1 * 8.0F;
      }

      public static float getDeltaMouse(float delta) {
            return (float)Math.round(delta / getGCDValue());
      }

      public final float getYaw() {
            return this.yaw;
      }

      public final void setYaw(float var1) {
            this.yaw = var1;
      }

      public final float getPitch() {
            return this.pitch;
      }

      public final void setPitch(float var1) {
            this.pitch = var1;
      }

      public String toString() {
            return "Rotation(yaw=" + this.yaw + ", pitch=" + this.pitch + ")";
      }

      public int hashCode() {
            return Float.hashCode(this.yaw) * 31 + Float.hashCode(this.pitch);
      }

      public boolean equals(Object var1) {
            if (this != var1) {
                  if (!(var1 instanceof GCDFix)) {
                        return false;
                  } else {
                        GCDFix var2 = (GCDFix)var1;
                        return Float.compare(this.yaw, var2.yaw) == 0 && Float.compare(this.pitch, var2.pitch) == 0;
                  }
            } else {
                  return true;
            }
      }
}
