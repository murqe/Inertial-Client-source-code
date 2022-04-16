package wtf.rich.api.utils.movement;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import wtf.rich.api.event.event.EventStrafe;
import wtf.rich.api.event.event.MoveEvent;
import wtf.rich.client.features.impl.combat.KillAura;

public class MovementHelper {
     public static double WALK_SPEED = 0.221D;
     public static Minecraft mc = Minecraft.getMinecraft();

     public static int getJumpBoostModifier() {
          PotionEffect effect = mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST);
          return effect != null ? effect.getAmplifier() + 1 : 0;
     }

     public static void setEventSpeed(MoveEvent event, double speed) {
          MovementInput var10000 = mc.player.movementInput;
          double forward = (double)MovementInput.moveForward;
          var10000 = mc.player.movementInput;
          double strafe = (double)MovementInput.moveStrafe;
          float yaw = mc.player.rotationYaw;
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

     public static void calculateSilentMove(EventStrafe event, float yaw) {
          float strafe = event.getStrafe();
          float forward = event.getForward();
          float friction = event.getFriction();
          int difference = (int)((MathHelper.wrapDegrees(mc.player.rotationYaw - yaw - 23.5F - 135.0F) + 180.0F) / 45.0F);
          float calcForward = 0.0F;
          float calcStrafe = 0.0F;
          switch(difference) {
          case 0:
               calcForward = forward;
               calcStrafe = strafe;
               break;
          case 1:
               calcForward += forward;
               calcStrafe -= forward;
               calcForward += strafe;
               calcStrafe += strafe;
               break;
          case 2:
               calcForward = strafe;
               calcStrafe = -forward;
               break;
          case 3:
               calcForward -= forward;
               calcStrafe -= forward;
               calcForward += strafe;
               calcStrafe -= strafe;
               break;
          case 4:
               calcForward = -forward;
               calcStrafe = -strafe;
               break;
          case 5:
               calcForward -= forward;
               calcStrafe += forward;
               calcForward -= strafe;
               calcStrafe -= strafe;
               break;
          case 6:
               calcForward = -strafe;
               calcStrafe = forward;
               break;
          case 7:
               calcForward += forward;
               calcStrafe += forward;
               calcForward -= strafe;
               calcStrafe += strafe;
          }

          if (calcForward > 1.0F || calcForward < 0.9F && calcForward > 0.3F || calcForward < -1.0F || calcForward > -0.9F && calcForward < -0.3F) {
               calcForward *= 0.5F;
          }

          if (calcStrafe > 1.0F || calcStrafe < 0.9F && calcStrafe > 0.3F || calcStrafe < -1.0F || calcStrafe > -0.9F && calcStrafe < -0.3F) {
               calcStrafe *= 0.5F;
          }

          float dist = calcStrafe * calcStrafe + calcForward * calcForward;
          if (dist >= 1.0E-4F) {
               dist = (float)((double)friction / Math.max(1.0D, Math.sqrt((double)dist)));
               calcStrafe *= dist;
               calcForward *= dist;
               float yawSin = MathHelper.sin((float)((double)yaw * 3.141592653589793D / 180.0D));
               float yawCos = MathHelper.cos((float)((double)yaw * 3.141592653589793D / 180.0D));
               EntityPlayerSP var10000 = mc.player;
               var10000.motionX += (double)(calcStrafe * yawCos - calcForward * yawSin);
               var10000 = mc.player;
               var10000.motionZ += (double)(calcForward * yawCos + calcStrafe * yawSin);
          }

     }

     public static boolean isBlockAbove() {
          for(double height = 0.0D; height <= 1.0D; height += 0.5D) {
               List collidingList = mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(0.0D, height, 0.0D));
               if (!collidingList.isEmpty()) {
                    return true;
               }
          }

          return false;
     }

     public static float getDirection() {
          float rotationYaw = mc.player.rotationYaw;
          float factor = 0.0F;
          MovementInput var10000 = mc.player.movementInput;
          if (MovementInput.moveForward > 0.0F) {
               factor = 1.0F;
          }

          var10000 = mc.player.movementInput;
          if (MovementInput.moveForward < 0.0F) {
               factor = -1.0F;
          }

          if (factor == 0.0F) {
               var10000 = mc.player.movementInput;
               if (MovementInput.moveStrafe > 0.0F) {
                    rotationYaw -= 90.0F;
               }

               var10000 = mc.player.movementInput;
               if (MovementInput.moveStrafe < 0.0F) {
                    rotationYaw += 90.0F;
               }
          } else {
               var10000 = mc.player.movementInput;
               if (MovementInput.moveStrafe > 0.0F) {
                    rotationYaw -= 45.0F * factor;
               }

               var10000 = mc.player.movementInput;
               if (MovementInput.moveStrafe < 0.0F) {
                    rotationYaw += 45.0F * factor;
               }
          }

          if (factor < 0.0F) {
               rotationYaw -= 180.0F;
          }

          return (float)Math.toRadians((double)rotationYaw);
     }

     public static float getDirection2() {
          Minecraft mc = Minecraft.getMinecraft();
          float var1 = mc.player.rotationYaw;
          if (mc.player.moveForward < 0.0F) {
               var1 += 180.0F;
          }

          float forward = 1.0F;
          if (mc.player.moveForward < 0.0F) {
               forward = -50.5F;
          } else if (mc.player.moveForward > 0.0F) {
               forward = 50.5F;
          }

          if (mc.player.moveStrafing > 0.0F) {
               var1 -= 22.0F * forward;
          }

          if (mc.player.moveStrafing < 0.0F) {
               var1 += 22.0F * forward;
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

     public static void setSpeedAt(MoveEvent e, float angle, double speed) {
          Minecraft mc = Minecraft.getMinecraft();
          if (!mc.gameSettings.keyBindJump.isKeyDown() && mc.player.onGround && mc.player.getDistanceToEntity(KillAura.target) > 1.0F) {
               e.setX(getXDirAt(angle) * speed);
               e.setZ(getZDirAt(angle) * speed);
          }

     }

     public static boolean isOnGround() {
          return !mc.player.onGround ? false : mc.player.isCollidedVertically;
     }

     public static void setMotion(MoveEvent e, double speed, float pseudoYaw, double aa, double po4) {
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
          if (d3 == 0.0D && d2 == 0.0D) {
               mc.player.motionZ = 0.0D;
               mc.player.motionX = 0.0D;
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
               mc.player.motionX = d4 * d * d6 + d5 * d * d7;
               mc.player.motionZ = d4 * d * d7 - d5 * d * d6;
          }

     }

     public static void setSpeed(double speed) {
          double forward = (double)MovementInput.moveForward;
          double strafe = (double)MovementInput.moveStrafe;
          float yaw = mc.player.rotationYaw;
          if (forward == 0.0D && strafe == 0.0D) {
               mc.player.motionX = 0.0D;
               mc.player.motionZ = 0.0D;
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

               mc.player.motionX = forward * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F)));
               mc.player.motionZ = forward * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F))) - strafe * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F)));
          }

     }

     public static void strafe() {
          if (!mc.gameSettings.keyBindBack.isKeyDown()) {
               strafe(getSpeed());
          }
     }

     public static float getSpeed() {
          return (float)Math.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ);
     }

     public static boolean isMoving() {
          if (mc.player == null) {
               return false;
          } else if (MovementInput.moveForward != 0.0F) {
               return true;
          } else {
               return MovementInput.moveStrafe != 0.0F;
          }
     }

     public static boolean hasMotion() {
          if (mc.player.motionX == 0.0D) {
               return false;
          } else if (mc.player.motionZ == 0.0D) {
               return false;
          } else {
               return mc.player.motionY != 0.0D;
          }
     }

     public static void strafe(float speed) {
          if (isMoving()) {
               double yaw = (double)getDirection();
               mc.player.motionX = -Math.sin(yaw) * (double)speed;
               mc.player.motionZ = Math.cos(yaw) * (double)speed;
          }
     }

     public static double getMoveSpeed(MoveEvent e) {
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
          double rot = Math.atan2((double)mc.player.moveForward, (double)mc.player.moveStrafing) / 3.141592653589793D * 180.0D;
          if (rot == 0.0D && mc.player.moveStrafing == 0.0F) {
               rot = 90.0D;
          }

          return rot + (double)mc.player.rotationYaw - 90.0D;
     }

     public static double getPlayerMoveDir() {
          Minecraft mc = Minecraft.getMinecraft();
          double xspeed = mc.player.motionX;
          double zspeed = mc.player.motionZ;
          double direction = Math.atan2(xspeed, zspeed) / 3.141592653589793D * 180.0D;
          return -direction;
     }

     public static boolean isBlockAboveHead() {
          AxisAlignedBB bb = new AxisAlignedBB(mc.player.posX - 0.3D, mc.player.posY + (double)mc.player.getEyeHeight(), mc.player.posZ + 0.3D, mc.player.posX + 0.3D, mc.player.posY + 2.5D, mc.player.posZ - 0.3D);
          return !mc.world.getCollisionBoxes(mc.player, bb).isEmpty();
     }

     public static void setMotionEvent(MoveEvent event, double speed) {
          double forward = (double)MovementInput.moveForward;
          double strafe = (double)MovementInput.moveStrafe;
          float yaw = mc.player.rotationYaw;
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
          mc.player.setPosition(mc.player.posX, mc.player.posY + 0.3D, mc.player.posZ);
          Minecraft.getMinecraft();
          double x = mc.player.posX;
          Minecraft.getMinecraft();
          double y = mc.player.posY;
          Minecraft.getMinecraft();
          double z = mc.player.posZ;

          for(int i = 0; i < 3000; ++i) {
               Minecraft.getMinecraft().getConnection().sendPacket(new CPacketPlayer.Position(x, y + 0.09999999999999D, z, false));
               Minecraft.getMinecraft().getConnection().sendPacket(new CPacketPlayer.Position(x, y, z, true));
          }

          Minecraft.getMinecraft();
          mc.player.motionY = 0.0D;
     }

     public static double getBaseMoveSpeed() {
          double baseSpeed = 0.2873D;
          if (mc.player.isPotionActive(Potion.getPotionById(1))) {
               int amplifier = mc.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
               baseSpeed *= 1.0D + 0.2D * (double)(amplifier + 1);
          }

          return baseSpeed;
     }
}
