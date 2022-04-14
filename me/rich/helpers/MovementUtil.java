package me.rich.helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovementInput;

public class MovementUtil {
      public static final double WALK_SPEED = 0.221D;
      public static Minecraft mc = Minecraft.getMinecraft();

      public static int getJumpBoostModifier() {
            PotionEffect effect = Minecraft.player.getActivePotionEffect(MobEffects.JUMP_BOOST);
            return effect != null ? effect.getAmplifier() + 1 : 0;
      }

      public static void setSpeed(double speed) {
            Minecraft.getMinecraft();
            MovementInput var10000 = Minecraft.player.movementInput;
            double forward = (double)MovementInput.moveForward;
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
