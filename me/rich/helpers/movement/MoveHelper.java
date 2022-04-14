package me.rich.helpers.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MovementInput;

public class MoveHelper {
      public static void setSpeed(double speed) {
            Minecraft.getMinecraft();
            MovementInput var10000 = Minecraft.player.movementInput;
            double forward = (double)MovementInput.field_192832_b;
            Minecraft.getMinecraft();
            var10000 = Minecraft.player.movementInput;
            double strafe = (double)MovementInput.moveStrafe;
            Minecraft.getMinecraft();
            float yaw = Minecraft.player.rotationYaw;
            if (forward == 0.0D && strafe == 0.0D) {
                  Minecraft.getMinecraft();
                  Minecraft.player.motionX = 0.0D;
                  Minecraft.getMinecraft();
                  Minecraft.player.motionZ = 0.0D;
            } else {
                  if (forward != 0.0D) {
                        if (strafe > 0.0D) {
                              yaw += (float)(forward > 0.0D ? -45 : 45);
                        } else if (strafe < 0.0D) {
                              yaw += (float)(forward > 0.0D ? 45 : -45);
                        }

                        strafe = 0.0D;
                        if (forward > 0.0D) {
                              forward = 1.0D;
                        } else if (forward < 0.0D) {
                              forward = -1.0D;
                        }
                  }

                  Minecraft.getMinecraft();
                  Minecraft.player.motionX = forward * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F)));
                  Minecraft.getMinecraft();
                  Minecraft.player.motionZ = forward * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F))) - strafe * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F)));
            }

      }
}
