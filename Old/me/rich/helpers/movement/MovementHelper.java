package me.rich.helpers.movement;

import java.util.List;
import me.rich.event.events.EventMove;
import me.rich.module.combat.KillAura;
import net.minecraft.client.Minecraft;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.AxisAlignedBB;

public class MovementHelper {
      public static final double WALK_SPEED = 0.221D;
      public static Minecraft mc = Minecraft.getMinecraft();

      public static int getJumpBoostModifier() {
            PotionEffect effect = Minecraft.player.getActivePotionEffect(MobEffects.JUMP_BOOST);
            return effect != null ? effect.getAmplifier() + 1 : 0;
      }

      public static boolean isBlockAbove() {
            for(double height = 0.0D; height <= 1.0D; height += 0.5D) {
                  List collidingList = mc.world.getCollisionBoxes(Minecraft.player, Minecraft.player.getEntityBoundingBox().offset(0.0D, height, 0.0D));
                  if (!collidingList.isEmpty()) {
                        return true;
                  }
            }

            return false;
      }

      public static float getDirection() {
            Minecraft mc = Minecraft.getMinecraft();
            float var1 = Minecraft.player.rotationYaw;
            if (Minecraft.player.moveForward < 0.0F) {
                  var1 += 180.0F;
            }

            float forward = 1.0F;
            if (Minecraft.player.moveForward < 0.0F) {
                  forward = -0.5F;
            } else if (Minecraft.player.moveForward > 0.0F) {
                  forward = 0.5F;
            }

            if (Minecraft.player.moveStrafing > 0.0F) {
                  var1 -= 90.0F * forward;
            }

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
            if (!mc.gameSettings.keyBindJump.isKeyDown() && Minecraft.player.onGround && Minecraft.player.getDistanceToEntity(KillAura.target) > 1.0F) {
                  e.setX(getXDirAt(angle) * speed);
                  e.setZ(getZDirAt(angle) * speed);
            }

      }

      public static boolean isOnGround() {
            return !Minecraft.player.onGround ? false : Minecraft.player.isCollidedVertically;
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

      public static void setSpeed(double d, float f, double d2, double d3) {
            double d4 = d3;
            double d5 = d2;
            float f2 = f;
            Minecraft var10000;
            if (d3 == 0.0D && d2 == 0.0D) {
                  var10000 = mc;
                  Minecraft.player.motionZ = 0.0D;
                  var10000 = mc;
                  Minecraft.player.motionX = 0.0D;
            } else {
                  if (d3 != 0.0D) {
                        if (d2 > 0.0D) {
                              f2 = f + (float)(d3 > 0.0D ? -45 : 45);
                        } else if (d2 < 0.0D) {
                              f2 = f + (float)(d3 > 0.0D ? 45 : -45);
                        }

                        d5 = 0.0D;
                        if (d3 > 0.0D) {
                              d4 = 1.0D;
                        } else if (d3 < 0.0D) {
                              d4 = -1.0D;
                        }
                  }

                  double d6 = Math.cos(Math.toRadians((double)(f2 + 90.0F)));
                  double d7 = Math.sin(Math.toRadians((double)(f2 + 90.0F)));
                  var10000 = mc;
                  Minecraft.player.motionX = d4 * d * d6 + d5 * d * d7;
                  var10000 = mc;
                  Minecraft.player.motionZ = d4 * d * d7 - d5 * d * d6;
            }

      }

      public static void setSpeed(double speed) {
            double forward = (double)MovementInput.moveForward;
            double strafe = (double)MovementInput.moveStrafe;
            float yaw = Minecraft.player.rotationYaw;
            if (forward == 0.0D && strafe == 0.0D) {
                  Minecraft.player.motionX = 0.0D;
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

                  Minecraft.player.motionX = forward * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F)));
                  Minecraft.player.motionZ = forward * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F))) - strafe * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F)));
            }

      }

      public static void strafe() {
            if (!mc.gameSettings.keyBindBack.isKeyDown()) {
                  strafe(getSpeed());
            }
      }

      public static float getSpeed() {
            return (float)Math.sqrt(Minecraft.player.motionX * Minecraft.player.motionX + Minecraft.player.motionZ * Minecraft.player.motionZ);
      }

      public static boolean isMoving() {
            if (Minecraft.player == null) {
                  return false;
            } else if (MovementInput.moveForward != 0.0F) {
                  return true;
            } else {
                  return MovementInput.moveStrafe != 0.0F;
            }
      }

      public static boolean hasMotion() {
            if (Minecraft.player.motionX == 0.0D) {
                  return false;
            } else if (Minecraft.player.motionZ == 0.0D) {
                  return false;
            } else {
                  return Minecraft.player.motionY != 0.0D;
            }
      }

      public static void strafe(float speed) {
            if (isMoving()) {
                  double yaw = (double)getDirection();
                  Minecraft.player.motionX = -Math.sin(yaw) * (double)speed;
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
            if (MovementInput.moveForward != 0.0F) {
                  return true;
            } else {
                  return MovementInput.moveStrafe != 0.0F;
            }
      }

      public static double getPressedMoveDir() {
            Minecraft mc = Minecraft.getMinecraft();
            double rot = Math.atan2((double)Minecraft.player.moveForward, (double)Minecraft.player.moveStrafing) / 3.141592653589793D * 180.0D;
            if (rot == 0.0D && Minecraft.player.moveStrafing == 0.0F) {
                  rot = 90.0D;
            }

            return rot + (double)Minecraft.player.rotationYaw - 90.0D;
      }

      public static double getPlayerMoveDir() {
            Minecraft mc = Minecraft.getMinecraft();
            double xspeed = Minecraft.player.motionX;
            double zspeed = Minecraft.player.motionZ;
            double direction = Math.atan2(xspeed, zspeed) / 3.141592653589793D * 180.0D;
            return -direction;
      }

      public static boolean isBlockAboveHead() {
            AxisAlignedBB bb = new AxisAlignedBB(Minecraft.player.posX - 0.3D, Minecraft.player.posY + (double)Minecraft.player.getEyeHeight(), Minecraft.player.posZ + 0.3D, Minecraft.player.posX + 0.3D, Minecraft.player.posY + 2.5D, Minecraft.player.posZ - 0.3D);
            return !mc.world.getCollisionBoxes(Minecraft.player, bb).isEmpty();
      }

      public static void setMotionEvent(EventMove event, double speed) {
            double forward = (double)MovementInput.moveForward;
            double strafe = (double)MovementInput.moveStrafe;
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
            Minecraft.player.setPosition(Minecraft.player.posX, Minecraft.player.posY + 0.3D, Minecraft.player.posZ);
            Minecraft.getMinecraft();
            double x = Minecraft.player.posX;
            Minecraft.getMinecraft();
            double y = Minecraft.player.posY;
            Minecraft.getMinecraft();
            double z = Minecraft.player.posZ;

            for(int i = 0; i < 3000; ++i) {
                  Minecraft.getMinecraft().getConnection().sendPacket(new CPacketPlayer.Position(x, y + 0.09999999999999D, z, false));
                  Minecraft.getMinecraft().getConnection().sendPacket(new CPacketPlayer.Position(x, y, z, true));
            }

            Minecraft.getMinecraft();
            Minecraft.player.motionY = 0.0D;
      }

      public static double getBaseMoveSpeed() {
            double baseSpeed = 0.2873D;
            if (Minecraft.player.isPotionActive(Potion.getPotionById(1))) {
                  int amplifier = Minecraft.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
                  baseSpeed *= 1.0D + 0.2D * (double)(amplifier + 1);
            }

            return baseSpeed;
      }

      public static double getDirectionAll() {
            float rotationYaw = Minecraft.player.rotationYaw;
            float forward = 1.0F;
            if (Minecraft.player.moveForward < 0.0F) {
                  rotationYaw += 180.0F;
            }

            if (Minecraft.player.moveForward < 0.0F) {
                  forward = -0.5F;
            } else if (Minecraft.player.moveForward > 0.0F) {
                  forward = 0.5F;
            }

            if (Minecraft.player.moveStrafing > 0.0F) {
                  rotationYaw -= 90.0F * forward;
            }

            if (Minecraft.player.moveStrafing < 0.0F) {
                  rotationYaw += 90.0F * forward;
            }

            return Math.toRadians((double)rotationYaw);
      }

      public static void strafePlayer(float speed) {
            double yaw = getDirectionAll();
            float getSpeed = speed == 0.0F ? getSpeed() : speed;
            Minecraft.player.motionX = -Math.sin(yaw) * (double)getSpeed;
            Minecraft.player.motionZ = Math.cos(yaw) * (double)getSpeed;
      }
}
