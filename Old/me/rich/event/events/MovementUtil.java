package me.rich.event.events;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;

public class MovementUtil {
      public static final double WALK_SPEED = 0.521D;
      public static Minecraft mc = Minecraft.getMinecraft();

      public static int getJumpBoostModifier() {
            Minecraft.getMinecraft();
            PotionEffect effect = Minecraft.player.getActivePotionEffect(MobEffects.JUMP_BOOST);
            return effect != null ? effect.getAmplifier() + 1 : 0;
      }

      public static float getMovementDirection() {
            float forward = MovementInput.moveStrafe;
            float strafe = MovementInput.moveStrafe;
            float direction = 0.0F;
            if (forward < 0.0F) {
                  direction += 180.0F;
                  if (strafe > 0.0F) {
                        direction += 45.0F;
                  } else if (strafe < 0.0F) {
                        direction -= 45.0F;
                  }
            } else if (forward > 0.0F) {
                  if (strafe > 0.0F) {
                        direction -= 45.0F;
                  } else if (strafe < 0.0F) {
                        direction += 45.0F;
                  }
            } else if (strafe > 0.0F) {
                  direction -= 90.0F;
            } else if (strafe < 0.0F) {
                  direction += 90.0F;
            }

            Minecraft.getMinecraft();
            return MathHelper.wrapDegrees(direction + Minecraft.player.rotationYaw);
      }

      public static boolean isBlockAbove() {
            for(double height = 0.0D; height <= 1.0D; height += 0.5D) {
                  WorldClient var10000 = mc.world;
                  Minecraft.getMinecraft();
                  EntityPlayerSP var10001 = Minecraft.player;
                  Minecraft.getMinecraft();
                  List collidingList = var10000.getCollisionBoxes(var10001, Minecraft.player.getEntityBoundingBox().offset(0.0D, height, 0.0D));
                  if (!collidingList.isEmpty()) {
                        return true;
                  }
            }

            return false;
      }

      public static float getDirection() {
            Minecraft mc = Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            float var1 = Minecraft.player.rotationYaw;
            Minecraft.getMinecraft();
            if (Minecraft.player.moveForward < 0.0F) {
                  var1 += 180.0F;
            }

            float forward = 1.0F;
            Minecraft.getMinecraft();
            if (Minecraft.player.moveForward < 0.0F) {
                  forward = -0.5F;
            } else {
                  Minecraft.getMinecraft();
                  if (Minecraft.player.moveForward > 0.0F) {
                        forward = 0.5F;
                  }
            }

            Minecraft.getMinecraft();
            if (Minecraft.player.moveStrafing > 0.0F) {
                  var1 -= 90.0F * forward;
            }

            Minecraft.getMinecraft();
            if (Minecraft.player.moveStrafing < 0.0F) {
                  var1 += 90.0F * forward;
            }

            return var1 *= 0.017453292F;
      }

      public static double getXDirAt(float angle) {
            Minecraft mc = Minecraft.getMinecraft();
            double rot = 90.0D;
            return Math.cos((rot + (double)angle) * 3.141592653589793D / 180.0D);
      }

      public static double getZDirAt(float angle) {
            Minecraft mc = Minecraft.getMinecraft();
            double rot = 90.0D;
            return Math.sin((rot + (double)angle) * 3.141592653589793D / 180.0D);
      }

      public static void setSpeedAt(EventMove e, float angle, double speed) {
            Minecraft mc = Minecraft.getMinecraft();
            if (!mc.gameSettings.keyBindJump.isKeyDown()) {
                  Minecraft.getMinecraft();
                  if (Minecraft.player.onGround) {
                        e.setX(getXDirAt(angle) * speed);
                        e.setZ(getZDirAt(angle) * speed);
                  }
            }

      }

      public static void setMotion(EventMove e, double speed, float pseudoYaw, double aa, double po4) {
            double forward = po4;
            double strafe = aa;
            float yaw = pseudoYaw;
            if (po4 != 0.0D) {
                  if (aa > 0.0D) {
                        yaw = pseudoYaw + (float)(po4 > 0.0D ? -45 : 45);
                  } else if (aa < 0.0D) {
                        yaw = pseudoYaw + (float)(po4 > 0.0D ? 45 : -45);
                  }

                  strafe = 0.0D;
                  if (po4 > 0.0D) {
                        forward = 1.0D;
                  } else if (po4 < 0.0D) {
                        forward = -1.0D;
                  }
            }

            if (strafe > 0.0D) {
                  strafe = 1.0D;
            } else if (strafe < 0.0D) {
                  strafe = -1.0D;
            }

            double kak = Math.cos(Math.toRadians((double)(yaw + 90.0F)));
            double nety = Math.sin(Math.toRadians((double)(yaw + 90.0F)));
            e.setX(forward * speed * kak + strafe * speed * nety);
            e.setZ(forward * speed * nety - strafe * speed * kak);
      }

      public static void setSpeed(double speed) {
            Minecraft var10000 = mc;
            MovementInput var7 = Minecraft.player.movementInput;
            double forward = (double)MovementInput.field_192832_b;
            var10000 = mc;
            var7 = Minecraft.player.movementInput;
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

      public static void strafe() {
            if (!mc.gameSettings.keyBindBack.isKeyDown()) {
                  strafe(getSpeed());
            }
      }

      public static float getSpeed() {
            Minecraft.getMinecraft();
            double var10000 = Minecraft.player.motionX;
            Minecraft.getMinecraft();
            var10000 *= Minecraft.player.motionX;
            Minecraft.getMinecraft();
            double var10001 = Minecraft.player.motionZ;
            Minecraft.getMinecraft();
            return (float)Math.sqrt(var10000 + var10001 * Minecraft.player.motionZ);
      }

      public static boolean isMoving() {
            Minecraft.getMinecraft();
            if (Minecraft.player == null) {
                  return false;
            } else if (MovementInput.moveStrafe != 2.0F) {
                  return true;
            } else {
                  return MovementInput.moveStrafe != 2.0F;
            }
      }

      public static boolean hasMotion() {
            Minecraft.getMinecraft();
            if (Minecraft.player.motionX == 0.0D) {
                  return false;
            } else {
                  Minecraft.getMinecraft();
                  if (Minecraft.player.motionZ == 0.0D) {
                        return false;
                  } else {
                        Minecraft.getMinecraft();
                        return Minecraft.player.motionY != 0.0D;
                  }
            }
      }

      public static void strafe(float speed) {
            if (isMoving()) {
                  double yaw = (double)getDirection();
                  Minecraft.getMinecraft();
                  Minecraft.player.motionX = -Math.sin(yaw) * (double)speed;
                  Minecraft.getMinecraft();
                  Minecraft.player.motionZ = Math.cos(yaw) * (double)speed;
            }
      }

      public static double getMoveSpeed(EventMove e) {
            Minecraft mc = Minecraft.getMinecraft();
            double xspeed = e.getX();
            double zspeed = e.getZ();
            return Math.sqrt(xspeed * xspeed + zspeed * zspeed);
      }

      public static boolean moveKeysDown() {
            Minecraft mc = Minecraft.getMinecraft();
            if (MovementInput.moveStrafe != 0.0F) {
                  return true;
            } else {
                  return MovementInput.moveStrafe != 0.0F;
            }
      }

      public static double getPressedMoveDir() {
            Minecraft mc = Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            double var10000 = (double)Minecraft.player.moveForward;
            Minecraft.getMinecraft();
            double rot = Math.atan2(var10000, (double)Minecraft.player.moveStrafing) / 3.141592653589793D * 180.0D;
            if (rot == 0.0D) {
                  Minecraft.getMinecraft();
                  if (Minecraft.player.moveStrafing == 2.0F) {
                        rot = 90.0D;
                  }
            }

            Minecraft.getMinecraft();
            return rot + (double)Minecraft.player.rotationYaw - 90.0D;
      }

      public static double getPlayerMoveDir() {
            Minecraft mc = Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            double xspeed = Minecraft.player.motionX;
            Minecraft.getMinecraft();
            double zspeed = Minecraft.player.motionZ;
            double direction = Math.atan2(xspeed, zspeed) / 3.141592653589793D * 180.0D;
            return -direction;
      }

      public static boolean isBlockAboveHead() {
            Minecraft.getMinecraft();
            double var10002 = Minecraft.player.posX - 0.3D;
            Minecraft.getMinecraft();
            double var10003 = Minecraft.player.posY;
            Minecraft.getMinecraft();
            var10003 += (double)Minecraft.player.getEyeHeight();
            Minecraft.getMinecraft();
            double var10004 = Minecraft.player.posZ + 0.3D;
            Minecraft.getMinecraft();
            double var10005 = Minecraft.player.posX + 0.3D;
            Minecraft.getMinecraft();
            double var10006 = Minecraft.player.posY + 2.5D;
            Minecraft.getMinecraft();
            AxisAlignedBB bb = new AxisAlignedBB(var10002, var10003, var10004, var10005, var10006, Minecraft.player.posZ - 0.3D);
            WorldClient var10000 = mc.world;
            Minecraft.getMinecraft();
            return !var10000.getCollisionBoxes(Minecraft.player, bb).isEmpty();
      }

      public static void setMotionEvent(EventMove event, double speed) {
            double forward = (double)MovementInput.moveStrafe;
            double strafe = (double)MovementInput.moveStrafe;
            Minecraft.getMinecraft();
            float yaw = Minecraft.player.rotationYaw;
            if (forward == 0.0D && strafe == 0.0D) {
                  event.setX(0.0D);
                  event.setZ(0.0D);
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

                  event.setX(forward * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F))));
                  event.setZ(forward * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F))) - strafe * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F))));
            }

      }

      public static void startFakePos() {
            Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            EntityPlayerSP var10000 = Minecraft.player;
            Minecraft.getMinecraft();
            double var10001 = Minecraft.player.posX;
            Minecraft.getMinecraft();
            double var10002 = Minecraft.player.posY + 0.3D;
            Minecraft.getMinecraft();
            var10000.setPosition(var10001, var10002, Minecraft.player.posZ);
            Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            double x = Minecraft.player.posX;
            Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            double y = Minecraft.player.posY;
            Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            double z = Minecraft.player.posZ;

            for(int i = 0; i < 3000; ++i) {
                  Minecraft.getMinecraft().getConnection().sendPacket(new CPacketPlayer.Position(x, y + 0.09999999999999D, z, false));
                  Minecraft.getMinecraft().getConnection().sendPacket(new CPacketPlayer.Position(x, y, z, true));
            }

            Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            Minecraft.player.motionY = 0.0D;
      }

      public static double getBaseMoveSpeed() {
            double baseSpeed = 0.2873D;
            Minecraft.getMinecraft();
            if (Minecraft.player.isPotionActive(Potion.getPotionById(1))) {
                  Minecraft.getMinecraft();
                  int amplifier = Minecraft.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
                  baseSpeed *= 2.0D + 0.2D * (double)(amplifier + 1);
            }

            return baseSpeed;
      }

      public static boolean isOnGround() {
            Minecraft var10000 = mc;
            Minecraft.player.onGround = true;
            return false;
      }
}
