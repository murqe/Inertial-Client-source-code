package wtf.rich.client.features.impl.movement;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventLiquidSolid;
import wtf.rich.api.event.event.EventPreMotionUpdate;
import wtf.rich.api.utils.movement.MovementHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.ListSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class Jesus extends Feature {
     public static ListSetting mode = new ListSetting("Jesus Mode", "Matrix", () -> {
          return true;
     }, new String[]{"Matrix", "ReallyWorld", "Matrix Zoom", "NCP"});
     public static NumberSetting speed = new NumberSetting("Speed", 0.65F, 0.1F, 10.0F, 0.01F, () -> {
          return !mode.currentMode.equals("NCP");
     });
     public static NumberSetting NCPSpeed = new NumberSetting("NCP Speed", 0.25F, 0.01F, 0.5F, 0.01F, () -> {
          return mode.currentMode.equals("NCP");
     });
     public static NumberSetting motionUp = new NumberSetting("Motion Up", 0.42F, 0.1F, 2.0F, 0.01F, () -> {
          return mode.currentMode.equals("Matrix");
     });
     public static BooleanSetting useTimer = new BooleanSetting("Use Timer", false, () -> {
          return true;
     });
     private final NumberSetting timerSpeed = new NumberSetting("Timer Speed", 1.05F, 1.01F, 1.5F, 0.01F, () -> {
          return useTimer.getBoolValue();
     });
     private final BooleanSetting speedCheck = new BooleanSetting("Speed Potion Check", false, () -> {
          return true;
     });
     private final BooleanSetting autoMotionStop = new BooleanSetting("Auto Motion Stop", true, () -> {
          return mode.currentMode.equals("ReallyWorld");
     });
     private final BooleanSetting autoWaterDown = new BooleanSetting("Auto Water Down", false, () -> {
          return mode.currentMode.equals("ReallyWorld");
     });
     private int waterTicks = 0;

     public Jesus() {
          super("Jesus", "Позволяет прыгать на воде", 0, Category.MOVEMENT);
          this.addSettings(new Setting[]{mode, speed, NCPSpeed, useTimer, this.timerSpeed, motionUp, this.speedCheck, this.autoWaterDown, this.autoMotionStop});
     }

     public void onDisable() {
          mc.timer.timerSpeed = 1.0F;
          if (mode.currentMode.equals("ReallyWorld") && this.autoWaterDown.getBoolValue()) {
               EntityPlayerSP var10000 = mc.player;
               var10000.motionY -= 500.0D;
          }

          this.waterTicks = 0;
          super.onDisable();
     }

     @EventTarget
     public void onLiquidBB(EventLiquidSolid event) {
          if (mode.currentMode.equals("ReallyWorld") || mode.currentMode.equals("NCP")) {
               event.setCancelled(true);
          }

     }

     private boolean isWater() {
          BlockPos bp1 = new BlockPos(mc.player.posX - 0.5D, mc.player.posY - 0.5D, mc.player.posZ - 0.5D);
          BlockPos bp2 = new BlockPos(mc.player.posX - 0.5D, mc.player.posY - 0.5D, mc.player.posZ + 0.5D);
          BlockPos bp3 = new BlockPos(mc.player.posX + 0.5D, mc.player.posY - 0.5D, mc.player.posZ + 0.5D);
          BlockPos bp4 = new BlockPos(mc.player.posX + 0.5D, mc.player.posY - 0.5D, mc.player.posZ - 0.5D);
          return mc.player.world.getBlockState(bp1).getBlock() == Blocks.WATER && mc.player.world.getBlockState(bp2).getBlock() == Blocks.WATER && mc.player.world.getBlockState(bp3).getBlock() == Blocks.WATER && mc.player.world.getBlockState(bp4).getBlock() == Blocks.WATER || mc.player.world.getBlockState(bp1).getBlock() == Blocks.LAVA && mc.player.world.getBlockState(bp2).getBlock() == Blocks.LAVA && mc.player.world.getBlockState(bp3).getBlock() == Blocks.LAVA && mc.player.world.getBlockState(bp4).getBlock() == Blocks.LAVA;
     }

     @EventTarget
     public void onPreMotion(EventPreMotionUpdate event) {
          this.setSuffix(mode.getCurrentMode());
          if (mc.player.isPotionActive(MobEffects.SPEED) || !this.speedCheck.getBoolValue()) {
               BlockPos blockPos = new BlockPos(mc.player.posX, mc.player.posY - 0.1D, mc.player.posZ);
               Block block = mc.world.getBlockState(blockPos).getBlock();
               if (useTimer.getBoolValue()) {
                    mc.timer.timerSpeed = this.timerSpeed.getNumberValue();
               }

               if (mode.currentMode.equalsIgnoreCase("Matrix")) {
                    if (mc.player.isInLiquid() && mc.player.motionY < 0.0D) {
                         mc.player.motionY = (double)motionUp.getNumberValue();
                         MovementHelper.setSpeed((double)speed.getNumberValue());
                    } else if (mode.currentMode.equalsIgnoreCase("NCP") && this.isWater() && block instanceof BlockLiquid) {
                         mc.player.motionY = 0.0D;
                         mc.player.onGround = false;
                         mc.player.isAirBorne = true;
                         MovementHelper.setSpeed((double)NCPSpeed.getNumberValue());
                         event.setY(mc.player.ticksExisted % 2 == 0 ? event.getY() + 0.02D : event.getY() - 0.02D);
                         event.setGround(false);
                    }
               } else if (mode.currentMode.equalsIgnoreCase("ReallyWorld")) {
                    if (this.isWater() && block instanceof BlockLiquid) {
                         ++this.waterTicks;
                         mc.player.motionY = 0.0D;
                         event.setGround(false);
                         mc.player.onGround = false;
                         event.setY(mc.player.ticksExisted % 2 == 0 ? event.getY() + 0.02D : event.getY() - 0.02D);
                         if (mc.player.ticksExisted % 2 == 0) {
                              MovementHelper.setSpeed((double)(speed.getNumberValue() * 5.2F));
                         } else {
                              MovementHelper.setSpeed(0.07000000029802322D);
                         }
                    } else {
                         this.waterTicks = MathHelper.clamp(this.waterTicks, 0, this.waterTicks);
                         this.waterTicks -= 10;
                         if (this.autoMotionStop.getBoolValue() && this.waterTicks >= 5 && !mc.player.onGround) {
                              MovementHelper.setSpeed(0.0D);
                         }
                    }
               } else if (mode.currentMode.equalsIgnoreCase("Matrix Zoom")) {
                    if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 0.20000000298023224D, mc.player.posZ)).getBlock() instanceof BlockLiquid && !mc.player.onGround) {
                         MovementHelper.setSpeed((double)speed.getNumberValue());
                    }

                    if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 9.999999747378752E-5D, mc.player.posZ)).getBlock() instanceof BlockLiquid) {
                         mc.player.motionX = 0.0D;
                         mc.player.motionZ = 0.0D;
                         mc.player.motionY = 0.05000000074505806D;
                    }
               }
          }

     }
}
