package wtf.rich.client.features.impl.movement;

import net.minecraft.network.play.client.CPacketPlayer;
import wtf.rich.Main;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventPreMotionUpdate;
import wtf.rich.api.event.event.EventStep;
import wtf.rich.api.utils.world.TimerHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.features.impl.player.NoClip;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.ListSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class Step extends Feature {
     public static TimerHelper time = new TimerHelper();
     public static NumberSetting delay;
     public static NumberSetting heightStep;
     public static ListSetting stepMode;
     public BooleanSetting reverseStep;
     public boolean jump;
     boolean resetTimer;

     public Step() {
          super("Step", "Автоматически взбирается на блоки", 0, Category.MOVEMENT);
          stepMode = new ListSetting("Step Mode", "Motion", () -> {
               return true;
          }, new String[]{"Motion", "Vanilla"});
          delay = new NumberSetting("Step Delay", 0.0F, 0.0F, 1.0F, 0.1F, () -> {
               return true;
          });
          heightStep = new NumberSetting("Height", 1.0F, 1.0F, 10.0F, 0.5F, () -> {
               return true;
          });
          this.reverseStep = new BooleanSetting("Reverse Step", false, () -> {
               return true;
          });
          this.addSettings(new Setting[]{stepMode, heightStep, delay, this.reverseStep});
     }

     @EventTarget
     public void onStep(EventStep step) {
          String mode = stepMode.getOptions();
          float delayValue = delay.getNumberValue() * 1000.0F;
          float stepValue = heightStep.getNumberValue();
          if (!Main.instance.featureDirector.getFeatureByClass(NoClip.class).isToggled()) {
               double height = mc.player.getEntityBoundingBox().minY - mc.player.posY;
               boolean canStep = height >= 0.625D;
               if (canStep) {
                    time.reset();
               }

               if (this.resetTimer) {
                    this.resetTimer = false;
                    mc.timer.timerSpeed = 1.0F;
               }

               if (mode.equalsIgnoreCase("Motion")) {
                    if (mc.player.isCollidedVertically && !mc.gameSettings.keyBindJump.isPressed() && time.hasReached((double)delayValue)) {
                         step.setStepHeight((double)stepValue);
                    }

                    if (canStep) {
                         mc.timer.timerSpeed = height > 1.0D ? 0.12F : 0.4F;
                         this.resetTimer = true;
                         this.ncpStep(height);
                    }
               } else if (mode.equalsIgnoreCase("Vanilla")) {
                    mc.player.stepHeight = heightStep.getNumberValue();
               }

          }
     }

     private void ncpStep(double height) {
          double[] offset = new double[]{0.42D, 0.333D, 0.248D, 0.083D, -0.078D};
          double posX = mc.player.posX;
          double posZ = mc.player.posZ;
          double y = mc.player.posY;
          if (height < 1.1D) {
               double first = 0.42D;
               double second = 0.75D;
               mc.player.connection.sendPacket(new CPacketPlayer.Position(posX, y + first, posZ, false));
               if (y + second < y + height) {
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(posX, y + second, posZ, true));
               }
          } else {
               double[] heights;
               int var18;
               if (height < 1.6D) {
                    heights = offset;
                    int var11 = offset.length;

                    for(var18 = 0; var18 < var11; ++var18) {
                         double off = heights[var18];
                         y += off;
                         mc.player.connection.sendPacket(new CPacketPlayer.Position(posX, y, posZ, true));
                    }
               } else {
                    double off;
                    double[] var17;
                    int var19;
                    if (height < 2.1D) {
                         heights = new double[]{0.425D, 0.821D, 0.699D, 0.599D, 1.022D, 1.372D, 1.652D, 1.869D};
                         var17 = heights;
                         var18 = heights.length;

                         for(var19 = 0; var19 < var18; ++var19) {
                              off = var17[var19];
                              mc.player.connection.sendPacket(new CPacketPlayer.Position(posX, y + off, posZ, true));
                         }
                    } else {
                         heights = new double[]{0.425D, 0.821D, 0.699D, 0.599D, 1.022D, 1.372D, 1.652D, 1.869D, 2.019D, 1.907D};
                         var17 = heights;
                         var18 = heights.length;

                         for(var19 = 0; var19 < var18; ++var19) {
                              off = var17[var19];
                              mc.player.connection.sendPacket(new CPacketPlayer.Position(posX, y + off, posZ, true));
                         }
                    }
               }
          }

     }

     @EventTarget
     public void onPreMotion(EventPreMotionUpdate event) {
          String mode = stepMode.getOptions();
          this.setSuffix(mode);
          if (mc.player.motionY > 0.0D) {
               this.jump = true;
          } else if (mc.player.onGround) {
               this.jump = false;
          }

          if (this.reverseStep.getBoolValue() && !mc.gameSettings.keyBindJump.isKeyDown() && !mc.player.onGround && mc.player.motionY < 0.0D && mc.player.fallDistance < 1.0F && !this.jump) {
               mc.player.motionY = -1.0D;
          }

     }

     public void onDisable() {
          mc.player.stepHeight = 0.625F;
          mc.timer.timerSpeed = 1.0F;
          super.onDisable();
     }
}
