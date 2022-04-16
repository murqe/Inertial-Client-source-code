package wtf.rich.client.features.impl.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventPreMotionUpdate;
import wtf.rich.api.event.event.EventReceivePacket;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.api.utils.movement.MovementHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.ListSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class Flight extends Feature {
     public final ListSetting flyMode = new ListSetting("Flight Mode", "Vanilla", () -> {
          return true;
     }, new String[]{"Vanilla", "WellMore", "Matrix Glide", "ReallyWorld"});
     public final NumberSetting speed = new NumberSetting("Flight Speed", 5.0F, 0.1F, 15.0F, 0.1F, () -> {
          return this.flyMode.currentMode.equals("Vanilla") || this.flyMode.currentMode.equalsIgnoreCase("Matrix Glide") || this.flyMode.currentMode.equals("WellMore");
     });
     private final BooleanSetting lagbackCheck = new BooleanSetting("Lagback Check", "Отключает модуль если вас флагнуло на сервере", false, () -> {
          return true;
     });

     public Flight() {
          super("Flight", "Позволяет вам летать без креатив режима", 0, Category.MOVEMENT);
          this.addSettings(new Setting[]{this.flyMode, this.speed, this.lagbackCheck});
     }

     @EventTarget
     public void onLagbackFlight(EventReceivePacket e) {
          if (e.getPacket() instanceof SPacketPlayerPosLook && this.lagbackCheck.getBoolValue()) {
               this.toggle();
          }

     }

     @EventTarget
     public void onPacket(EventReceivePacket event) {
          String modes = this.flyMode.getOptions();
          Packet p = event.getPacket();
          if (modes.equalsIgnoreCase("WellMore") && event.isIncoming() && timerHelper.hasReached(1000.0D) && p instanceof SPacketPlayerPosLook && mc.player != null) {
               event.setCancelled(true);
               timerHelper.reset();
          }

     }

     public void onDisable() {
          super.onDisable();
          mc.player.speedInAir = 0.02F;
          mc.timer.timerSpeed = 1.0F;
          mc.player.capabilities.isFlying = false;
          if (this.flyMode.getOptions().equalsIgnoreCase("WellMore")) {
               mc.player.motionY = 0.0D;
               mc.player.motionX = 0.0D;
               mc.player.motionZ = 0.0D;
          }

     }

     @EventTarget
     public void onUpdate(EventUpdate eventUpdate) {
          String mode = this.flyMode.getCurrentMode();
          if (mode.equalsIgnoreCase("Matrix Glide")) {
               if (mc.player.onGround) {
                    mc.player.jump();
                    timerHelper.reset();
               } else if (!mc.player.onGround && timerHelper.hasReached(100.0D)) {
                    mc.player.motionX = 0.0D;
                    mc.player.motionZ = 0.0D;
                    mc.player.motionY = -0.01D;
                    mc.player.capabilities.isFlying = true;
                    mc.player.capabilities.setFlySpeed(this.speed.getNumberValue());
                    mc.player.speedInAir = 0.3F;
                    EntityPlayerSP var10000;
                    if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                         var10000 = mc.player;
                         var10000.motionY -= 0.6D;
                    } else if (mc.gameSettings.keyBindJump.isKeyDown()) {
                         var10000 = mc.player;
                         var10000.motionY += 0.6D;
                    }
               }
          } else if (mode.equalsIgnoreCase("ReallyWorld")) {
               if (mc.player.ticksExisted % 3 == 0) {
                    mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
               }

               if (mc.gameSettings.keyBindJump.isKeyDown()) {
                    mc.player.jumpMovementFactor = 0.0F;
                    mc.player.jump();
               }
          }

     }

     @EventTarget
     public void onPreMotion(EventPreMotionUpdate event) {
          String mode = this.flyMode.getCurrentMode();
          this.setSuffix(mode);
          EntityPlayerSP var10000;
          if (mode.equalsIgnoreCase("WellMore")) {
               if (mc.player.onGround && timerHelper.hasReached(100.0D)) {
                    mc.player.jump();
                    timerHelper.reset();
               } else {
                    mc.player.motionX = 0.0D;
                    mc.player.motionZ = 0.0D;
                    mc.player.motionY = -0.01D;
                    MovementHelper.setSpeed((double)this.speed.getNumberValue());
                    mc.player.speedInAir = 0.3F;
                    if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                         var10000 = mc.player;
                         var10000.motionY -= (double)this.speed.getNumberValue();
                    } else if (mc.gameSettings.keyBindJump.isKeyDown()) {
                         var10000 = mc.player;
                         var10000.motionY += (double)this.speed.getNumberValue();
                    }
               }
          } else if (mode.equalsIgnoreCase("Vanilla")) {
               mc.player.capabilities.isFlying = true;
               MovementHelper.setSpeed((double)this.speed.getNumberValue());
               if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                    var10000 = mc.player;
                    var10000.motionY -= (double)this.speed.getNumberValue();
               } else if (mc.gameSettings.keyBindJump.isKeyDown()) {
                    var10000 = mc.player;
                    var10000.motionY += (double)this.speed.getNumberValue();
               }
          }

     }
}
