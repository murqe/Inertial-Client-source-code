package me.rich.module.movement;

import de.Hero.settings.Setting;
import java.util.ArrayList;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventPreMotionUpdate;
import me.rich.helpers.movement.MovementHelper;
import me.rich.helpers.world.TimerHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;

public class Speed extends Feature {
      private final Setting strafing;
      private int counter;
      TimerHelper timerUtils = new TimerHelper();

      public Speed() {
            super("Speed", 0, Category.MOVEMENT);
            ArrayList speed = new ArrayList();
            speed.add("Teleport");
            speed.add("GuardianHop");
            speed.add("Sunrise");
            speed.add("OnGround");
            speed.add("Matrix");
            Main.settingsManager.rSetting(new Setting("Speed Mode", this, "Teleport", speed));
            this.strafing = new Setting("Strafing", this, false);
            Main.settingsManager.rSetting(this.strafing);
      }

      @EventTarget
      public void onPreMotion(EventPreMotionUpdate event) {
            if (this.isToggled()) {
                  String mode = Main.settingsManager.getSettingByName("Speed Mode").getValString();
                  this.setModuleName("Speed §7Mode : " + Main.settingsManager.getSettingByName("Speed Mode").getValString());
                  this.setSuffix(mode);
                  if (mode.equalsIgnoreCase("Teleport")) {
                        if (!Minecraft.player.isOnLadder() && !Minecraft.player.isInWater() && !Minecraft.player.isInLava()) {
                              mc.gameSettings.keyBindJump.pressed = false;
                              if (this.strafing.getValBoolean()) {
                                    MovementHelper.strafe();
                              }

                              if (Minecraft.player.onGround) {
                                    Minecraft.player.jump();
                                    this.counter = 5;
                              } else if (this.counter < 5) {
                                    if (this.counter == 1) {
                                          mc.timer.timerSpeed = 1.2F;
                                    }

                                    ++this.counter;
                              } else {
                                    this.counter = 0;
                                    Minecraft.player.speedInAir = 0.02079F;
                                    Minecraft.player.jumpMovementFactor = 0.027F;
                              }
                        }

                        mc.timer.timerSpeed = Minecraft.player.motionY == 0.0030162615090425808D ? 1.6F : 1.0F;
                        if (Minecraft.player.ticksExisted % 60 > 39) {
                              mc.timer.timerSpeed = 3.0F;
                        }
                  }

                  if (mode.equalsIgnoreCase("GuardianHop")) {
                        if (Minecraft.player.onGround) {
                              Minecraft.player.jump();
                              this.counter = 5;
                        } else if (this.counter < 5) {
                              if (this.counter == 1) {
                                    mc.timer.timerSpeed = 1.38F;
                              }

                              ++this.counter;
                        } else {
                              this.counter = 0;
                              Minecraft.player.speedInAir = 0.022F;
                              Minecraft.player.jumpMovementFactor = 0.027F;
                        }
                  }

                  if (mode.equalsIgnoreCase("Sunrise")) {
                        if (Minecraft.player.onGround) {
                              Minecraft.player.jump();
                        } else {
                              Minecraft.player.motionY = -20.0D;
                              Minecraft.player.speedInAir = 0.022F;
                        }
                  }

                  if (mode.equalsIgnoreCase("OnGround") && !(Minecraft.player.onGround = false) && (double)Minecraft.player.fallDistance <= 0.1D) {
                        Minecraft.player.motionY = -20.0D;
                        Minecraft.player.speedInAir = 0.2F;
                  }

                  if (mode.equalsIgnoreCase("Matrix")) {
                        if (Minecraft.player.onGround && mc.gameSettings.keyBindForward.pressed) {
                              Minecraft.player.jump();
                              Minecraft.player.speedInAir = 0.018F;
                              Minecraft.player.jumpMovementFactor = 0.027F;
                              Minecraft.player.onGround = false;
                        }

                        if (Minecraft.player.motionY > 0.0D && !Minecraft.player.isInWater()) {
                        }
                  }
            }

      }

      public void onEnable() {
            super.onEnable();
      }

      public void onDisable() {
            mc.timer.timerSpeed = 1.0F;
            Minecraft.player.speedInAir = 0.02F;
            super.onDisable();
      }
}
