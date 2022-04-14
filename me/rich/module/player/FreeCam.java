package me.rich.module.player;

import de.Hero.settings.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdateLiving;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.settings.GameSettings;

public class FreeCam extends Feature {
      private float yaw;
      private float pitch;
      private float yawHead;
      private float gamma;
      private EntityOtherPlayerMP other;
      private float old;
      private EntityOtherPlayerMP fakePlayer = null;
      private double oldX;
      private double oldY;
      private double oldZ;
      public Setting speed = new Setting("Speed", this, 0.1D, 0.01D, 10.0D, false);

      public FreeCam() {
            super("FreeCam", 0, Category.RENDER);
            Main.settingsManager.rSetting(this.speed);
      }

      public void onDisable() {
            Minecraft.player.capabilities.isFlying = false;
            Minecraft.player.capabilities.setFlySpeed(this.old);
            Minecraft.player.rotationPitch = this.pitch;
            Minecraft.player.rotationYaw = this.yaw;
            mc.world.removeEntityFromWorld(-1);
            Minecraft.player.noClip = false;
            mc.renderGlobal.loadRenderers();
            Minecraft.getMinecraft();
            Minecraft.player.noClip = false;
            Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            Minecraft.player.setPositionAndRotation(this.oldX, this.oldY, this.oldZ, Minecraft.player.rotationYaw, Minecraft.player.rotationPitch);
            Minecraft.getMinecraft().world.removeEntityFromWorld(-69);
            this.fakePlayer = null;
            super.onDisable();
      }

      public void onEnable() {
            Minecraft.getMinecraft();
            this.oldX = Minecraft.player.posX;
            Minecraft.getMinecraft();
            this.oldY = Minecraft.player.posY;
            Minecraft.getMinecraft();
            this.oldZ = Minecraft.player.posZ;
            Minecraft.getMinecraft();
            Minecraft.player.noClip = true;
            Minecraft.getMinecraft();
            EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP(Minecraft.getMinecraft().world, Minecraft.player.getGameProfile());
            Minecraft.getMinecraft();
            fakePlayer.copyLocationAndAnglesFrom(Minecraft.player);
            fakePlayer.posY -= 0.0D;
            Minecraft.getMinecraft();
            fakePlayer.rotationYawHead = Minecraft.player.rotationYawHead;
            Minecraft.getMinecraft().world.addEntityToWorld(-69, fakePlayer);
            super.onEnable();
      }

      @EventTarget
      public void g(EventUpdateLiving e) {
            Minecraft.player.noClip = true;
            Minecraft.player.onGround = false;
            Minecraft.player.capabilities.setFlySpeed((float)this.speed.getValDouble());
            Minecraft.player.capabilities.isFlying = true;
            if (!Minecraft.player.isInsideOfMaterial(Material.AIR) && !Minecraft.player.isInsideOfMaterial(Material.LAVA) && !Minecraft.player.isInsideOfMaterial(Material.WATER)) {
                  if (mc.gameSettings.gammaSetting < 100.0F) {
                        GameSettings var10000 = mc.gameSettings;
                        var10000.gammaSetting += 0.08F;
                  }
            } else {
                  mc.gameSettings.gammaSetting = this.gamma;
            }
      }
}
