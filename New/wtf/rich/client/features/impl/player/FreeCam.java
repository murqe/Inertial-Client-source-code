package wtf.rich.client.features.impl.player;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import wtf.rich.Main;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventReceivePacket;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.api.utils.movement.MovementHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class FreeCam extends Feature {
     public NumberSetting speed = new NumberSetting("Flying Speed", 0.5F, 0.1F, 1.0F, 0.1F, () -> {
          return true;
     });
     public BooleanSetting AntiAction = new BooleanSetting("ReallyWorld", false, () -> {
          return true;
     });
     public BooleanSetting autoDamageDisable = new BooleanSetting("Auto Damage Disable", false, () -> {
          return true;
     });
     double x;
     double y;
     double z;

     public FreeCam() {
          super("FreeCam", "Позволяет летать в свободной камере", 0, Category.PLAYER);
          this.addSettings(new Setting[]{this.speed, this.autoDamageDisable, this.AntiAction});
     }

     @EventTarget
     public void onReceivePacket(EventReceivePacket event) {
          if (this.AntiAction.getBoolValue() && event.getPacket() instanceof SPacketPlayerPosLook) {
               event.setCancelled(true);
          }

     }

     public void onEnable() {
          super.onEnable();
          this.x = mc.player.posX;
          this.y = mc.player.posY;
          this.z = mc.player.posZ;
          EntityOtherPlayerMP ent = new EntityOtherPlayerMP(mc.world, mc.player.getGameProfile());
          ent.inventory = mc.player.inventory;
          ent.inventoryContainer = mc.player.inventoryContainer;
          ent.setHealth(mc.player.getHealth());
          ent.setPositionAndRotation(this.x, mc.player.getEntityBoundingBox().minY, this.z, mc.player.rotationYaw, mc.player.rotationPitch);
          ent.rotationYawHead = mc.player.rotationYawHead;
          mc.world.addEntityToWorld(-1, ent);
     }

     @EventTarget
     public void onPreMotion(EventUpdate e) {
          if (this.autoDamageDisable.getBoolValue() && mc.player.hurtTime > 0 && Main.instance.featureDirector.getFeatureByClass(FreeCam.class).isToggled()) {
               Main.instance.featureDirector.getFeatureByClass(FreeCam.class).toggle();
          }

          mc.player.motionY = 0.0D;
          if (mc.gameSettings.keyBindJump.pressed) {
               mc.player.motionY = (double)this.speed.getNumberValue();
          }

          if (mc.gameSettings.keyBindSneak.pressed) {
               mc.player.motionY = (double)(-this.speed.getNumberValue());
          }

          mc.player.noClip = true;
          MovementHelper.setSpeed((double)this.speed.getNumberValue());
          e.setCancelled(true);
     }

     public void onDisable() {
          super.onDisable();
          mc.player.setPosition(this.x, this.y, this.z);
          mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.01D, mc.player.posZ, mc.player.onGround));
          mc.player.capabilities.isFlying = false;
          mc.player.noClip = false;
          mc.world.removeEntityFromWorld(-1);
     }
}
