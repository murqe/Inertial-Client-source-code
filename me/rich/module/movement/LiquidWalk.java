package me.rich.module.movement;

import de.Hero.settings.Setting;
import java.util.ArrayList;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.BlockPos;

public class LiquidWalk extends Feature {
      public LiquidWalk() {
            super("Jesus", 0, Category.MOVEMENT);
            ArrayList jesus = new ArrayList();
            jesus.add("Default");
            jesus.add("Solid");
            jesus.add("Zoom");
            Main.settingsManager.rSetting(new Setting("Jesus Mode", this, "Default", jesus));
            Main.settingsManager.rSetting(new Setting("UseTimer", this, false));
            Main.settingsManager.rSetting(new Setting("MotionVal", this, 1.2D, 0.2D, 3.0D, false));
            Main.settingsManager.rSetting(new Setting("ZoomMotionVal", this, 1.0D, 1.0D, 15.0D, false));
            Main.settingsManager.rSetting(new Setting("JumpVal", this, 0.45D, 0.2D, 0.6D, false));
      }

      @EventTarget
      public void Update(EventUpdate event) {
            String jesus = Main.settingsManager.getSettingByName("Jesus Mode").getValString();
            Minecraft var10000;
            if (jesus.equalsIgnoreCase("Default")) {
                  label37: {
                        double ff = (double)Main.settingsManager.getSettingByName("MotionVal").getValFloat();
                        var10000 = mc;
                        if (!Minecraft.player.isInWater()) {
                              var10000 = mc;
                              if (!Minecraft.player.isInLava()) {
                                    mc.timer.timerSpeed = 1.0F;
                                    break label37;
                              }
                        }

                        var10000 = mc;
                        Minecraft.player.motionY = (double)Main.settingsManager.getSettingByName("JumpVal").getValFloat();
                        setSpeed(ff);
                        if (Main.settingsManager.getSettingByName("UseTimer").getValBoolean()) {
                              mc.timer.timerSpeed = 2.0F;
                        }
                  }
            }

            Minecraft var10002;
            Minecraft var10003;
            Minecraft var10004;
            Block block;
            BlockPos blockPos;
            Minecraft var10005;
            double var6;
            double var7;
            if (jesus.equalsIgnoreCase("Zoom")) {
                  var10002 = mc;
                  var10003 = mc;
                  var6 = Minecraft.player.posY - 0.1D;
                  var10004 = mc;
                  blockPos = new BlockPos(Minecraft.player.posX, var6, Minecraft.player.posZ);
                  block = mc.world.getBlockState(blockPos).getBlock();
                  if (Block.getIdFromBlock(block) == 9) {
                        var10000 = mc;
                        if (!Minecraft.player.onGround) {
                              setSpeed((double)Main.settingsManager.getSettingByName("ZoomMotionVal").getValFloat());
                              var10003 = mc;
                              var10004 = mc;
                              var7 = Minecraft.player.posY + 1.0E-7D;
                              var10005 = mc;
                              if (mc.world.getBlockState(new BlockPos(Minecraft.player.posX, var7, Minecraft.player.posZ)).getBlock() == Block.getBlockById(9)) {
                                    var10000 = mc;
                                    Minecraft.player.fallDistance = 0.0F;
                                    var10000 = mc;
                                    Minecraft.player.motionX = 0.0D;
                                    var10000 = mc;
                                    Minecraft.player.motionY = 0.05999999865889549D;
                                    var10000 = mc;
                                    Minecraft.player.jumpMovementFactor = 0.01F;
                                    var10000 = mc;
                                    Minecraft.player.motionZ = 0.0D;
                              }
                        }
                  }
            }

            if (jesus.equalsIgnoreCase("Solid")) {
                  var10002 = mc;
                  var10003 = mc;
                  var6 = Minecraft.player.posY - 0.1D;
                  var10004 = mc;
                  blockPos = new BlockPos(Minecraft.player.posX, var6, Minecraft.player.posZ);
                  block = mc.world.getBlockState(blockPos).getBlock();
                  if (Block.getIdFromBlock(block) == 9) {
                        var10000 = mc;
                        if (!Minecraft.player.onGround) {
                              setSpeed((double)Main.settingsManager.getSettingByName("MotionVal").getValFloat());
                              var10000 = mc;
                              Minecraft.player.motionY = 0.0D;
                        }
                  }

                  var10003 = mc;
                  var10004 = mc;
                  var7 = Minecraft.player.posY + 1.0E-7D;
                  var10005 = mc;
                  if (mc.world.getBlockState(new BlockPos(Minecraft.player.posX, var7, Minecraft.player.posZ)).getBlock() == Block.getBlockById(9)) {
                        var10000 = mc;
                        Minecraft.player.motionY = 0.06D;
                  }
            }

            this.setModuleName("Jesus §7Mode: " + Main.settingsManager.getSettingByName("Jesus Mode").getValString());
      }

      public void onEnable() {
            super.onEnable();
            NotificationPublisher.queue(this.getName(), "Was enabled.", NotificationType.INFO);
      }

      public void onDisable() {
            mc.timer.timerSpeed = 1.0F;
            NotificationPublisher.queue(this.getName(), "Was disabled.", NotificationType.INFO);
            Minecraft var10000 = mc;
            Minecraft.player.capabilities.isFlying = false;
            super.onDisable();
      }

      public static void setSpeed(double speed) {
            double forward = (double)MovementInput.field_192832_b;
            double strafe = (double)MovementInput.moveStrafe;
            Minecraft var10000 = mc;
            float yaw = Minecraft.player.rotationYaw;
            if (forward == 0.0D && strafe == 0.0D) {
                  var10000 = mc;
                  Minecraft.player.motionX = 0.0D;
                  var10000 = mc;
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

                  var10000 = mc;
                  Minecraft.player.motionX = forward * speed * Math.cos(Math.toRadians((double)yaw + 90.0D)) + strafe * speed * Math.sin(Math.toRadians((double)yaw + 90.0D));
                  var10000 = mc;
                  Minecraft.player.motionZ = forward * speed * Math.sin(Math.toRadians((double)yaw + 90.0D)) - strafe * speed * Math.cos(Math.toRadians((double)yaw + 90.0D));
            }

      }
}
