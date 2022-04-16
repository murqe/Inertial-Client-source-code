package wtf.rich.client.features.impl.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import wtf.rich.Main;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventPreMotionUpdate;
import wtf.rich.api.event.event.EventReceivePacket;
import wtf.rich.api.utils.movement.MovementHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.ListSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class Speed extends Feature {
     private float ticks = 35.0F;
     private final ListSetting speedMode = new ListSetting("Speed Mode", "Matrix", () -> {
          return true;
     }, new String[]{"Matrix", "Matrix New", "Matrix Long", "NexusGrief", "SunriseDamage", "StormDisabler"});
     private final NumberSetting speed = new NumberSetting("Speed", 2.0F, 0.1F, 10.0F, 0.1F, () -> {
          return this.speedMode.currentMode.equalsIgnoreCase("StormDisabler");
     });
     private final BooleanSetting lagbackCheck = new BooleanSetting("Lagback Check", "Отключает модуль если вас флагнуло на сервере", false, () -> {
          return true;
     });

     public Speed() {
          super("Speed", "Увеличивает вашу скорость", 0, Category.MOVEMENT);
          this.addSettings(new Setting[]{this.speedMode, this.speed, this.lagbackCheck});
     }

     @EventTarget
     public void onLagbackSpeed(EventReceivePacket e) {
          if (e.getPacket() instanceof SPacketPlayerPosLook && this.lagbackCheck.getBoolValue()) {
               this.toggle();
               Main.msg("Anti-cheat discovered speedhack", true);
          }

     }

     @EventTarget
     public void onPreMotion(EventPreMotionUpdate event) {
          if (this.isToggled()) {
               String mode = this.speedMode.getOptions();
               EntityPlayerSP var10000;
               double x;
               double y;
               double z;
               double yaw;
               if (mode.equalsIgnoreCase("Matrix")) {
                    mc.gameSettings.keyBindJump.pressed = false;
                    if (mc.player.isInWeb || mc.player.isOnLadder() || mc.player.isInLiquid()) {
                         return;
                    }

                    var10000 = mc.player;
                    var10000.jumpMovementFactor *= 1.04F;
                    x = mc.player.posX;
                    y = mc.player.posY;
                    z = mc.player.posZ;
                    yaw = (double)mc.player.rotationYaw * 0.017453292D;
                    if (mc.player.onGround) {
                         mc.timer.timerSpeed = 1.3F;
                         this.ticks = 11.0F;
                         MovementHelper.strafe();
                         mc.player.jump();
                    } else if (this.ticks < 11.0F) {
                         ++this.ticks;
                    } else {
                         if (timerHelper.hasReached(260.0D)) {
                              Minecraft.getMinecraft().player.onGround = false;
                              var10000 = Minecraft.getMinecraft().player;
                              var10000.motionX *= 1.7000000000000002D;
                              var10000 = Minecraft.getMinecraft().player;
                              var10000.motionZ *= 1.7000000000000002D;
                              Minecraft.getMinecraft().player.setPosition(x - Math.sin(yaw) * 0.003D, y, z + Math.cos(yaw) * 0.003D);
                              timerHelper.reset();
                         }

                         this.ticks = 0.0F;
                    }
               } else if (mode.equalsIgnoreCase("Matrix New")) {
                    if (mc.player.isInWeb || mc.player.isOnLadder() || mc.player.isInLiquid()) {
                         return;
                    }

                    x = mc.player.posX;
                    y = mc.player.posY;
                    z = mc.player.posZ;
                    yaw = (double)mc.player.rotationYaw * 0.017453292D;
                    if (mc.player.onGround) {
                         mc.player.jump();
                         mc.timer.timerSpeed = 1.3F;
                         this.ticks = 11.0F;
                    } else if (this.ticks < 11.0F) {
                         ++this.ticks;
                    }

                    if (mc.player.motionY == -0.4448259643949201D) {
                         var10000 = mc.player;
                         var10000.motionX *= 2.0D;
                         var10000 = mc.player;
                         var10000.motionZ *= 2.0D;
                         Minecraft.getMinecraft().player.setPosition(x - Math.sin(yaw) * 0.003D, y, z + Math.cos(yaw) * 0.003D);
                    }

                    this.ticks = 0.0F;
               } else if (mode.equalsIgnoreCase("Matrix Long")) {
                    if (mc.player.isInWeb || mc.player.isOnLadder() || mc.player.isInLiquid()) {
                         return;
                    }

                    x = mc.player.posX;
                    y = mc.player.posY;
                    z = mc.player.posZ;
                    yaw = (double)mc.player.rotationYaw * 0.017453292D;
                    if (mc.player.onGround) {
                         this.ticks = 11.0F;
                    } else if (this.ticks < 11.0F) {
                         ++this.ticks;
                    }

                    if (mc.player.motionY == -0.4448259643949201D) {
                         var10000 = mc.player;
                         var10000.motionX *= 1.7999999523162842D;
                         var10000 = mc.player;
                         var10000.motionZ *= 1.7999999523162842D;
                         Minecraft.getMinecraft().player.setPosition(x - Math.sin(yaw) * 0.003D, y, z + Math.cos(yaw) * 0.003D);
                    }

                    this.ticks = 0.0F;
               } else if (mode.equalsIgnoreCase("NexusGrief")) {
                    if (MovementHelper.isMoving() && mc.player.hurtTime > 0) {
                         MovementHelper.strafe();
                         if (mc.player.onGround) {
                              mc.player.addVelocity(-Math.sin((double)MovementHelper.getDirection()) * 6.8D / 24.5D, 0.0D, Math.cos((double)MovementHelper.getDirection()) * 6.8D / 24.5D);
                              MovementHelper.strafe();
                         } else if (mc.player.isInWater()) {
                              mc.player.addVelocity(-Math.sin((double)MovementHelper.getDirection()) * 7.5D / 24.5D, 0.0D, Math.cos((double)MovementHelper.getDirection()) * 7.5D / 24.5D);
                              MovementHelper.strafe();
                         } else if (!mc.player.onGround) {
                              mc.player.addVelocity(-Math.sin((double)MovementHelper.getDirection2()) * 0.11D / 24.5D, 0.0D, Math.cos((double)MovementHelper.getDirection2()) * 0.11D / 24.5D);
                              MovementHelper.strafe();
                         } else {
                              mc.player.addVelocity(-Math.sin((double)MovementHelper.getDirection()) * 0.005D * (double)MovementHelper.getSpeed(), 0.0D, Math.cos((double)MovementHelper.getDirection()) * 0.005D * (double)MovementHelper.getSpeed());
                              MovementHelper.strafe();
                         }
                    }
               } else if (mode.equalsIgnoreCase("SunriseDamage")) {
                    if (MovementHelper.isMoving()) {
                         if (mc.player.onGround) {
                              mc.player.addVelocity(-Math.sin((double)MovementHelper.getDirection()) * 9.8D / 24.5D, 0.0D, Math.cos((double)MovementHelper.getDirection()) * 9.8D / 24.5D);
                              MovementHelper.strafe();
                         } else if (mc.player.isInWater()) {
                              mc.player.addVelocity(-Math.sin((double)MovementHelper.getDirection()) * 8.5D / 24.5D, 0.0D, Math.cos((double)MovementHelper.getDirection()) * 9.5D / 24.5D);
                              MovementHelper.strafe();
                         } else if (!mc.player.onGround) {
                              mc.player.addVelocity(-Math.sin((double)MovementHelper.getDirection2()) * 0.11D / 24.5D, 0.0D, Math.cos((double)MovementHelper.getDirection2()) * 0.11D / 24.5D);
                              MovementHelper.strafe();
                         } else {
                              mc.player.addVelocity(-Math.sin((double)MovementHelper.getDirection()) * 0.005D * (double)MovementHelper.getSpeed(), 0.0D, Math.cos((double)MovementHelper.getDirection()) * 0.005D * (double)MovementHelper.getSpeed());
                              MovementHelper.strafe();
                         }
                    }
               } else if (mode.equalsIgnoreCase("StormDisabler")) {
                    if (mc.player.onGround) {
                         mc.player.jump();
                    } else {
                         MovementHelper.setSpeed((double)this.speed.getNumberValue());
                    }
               }

               this.setSuffix(mode);
          }

     }

     public void onDisable() {
          mc.timer.timerSpeed = 1.0F;
          mc.player.speedInAir = 0.02F;
          super.onDisable();
     }
}
