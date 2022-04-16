package me.rich.module.player;

import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdateLiving;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayer;

public class NoClip extends Feature {
      private float gamma;

      public NoClip() {
            super("NoClip", 0, Category.PLAYER);
      }

      @EventTarget
      public void onLivingUpdate(EventUpdateLiving event) {
            EntityPlayerSP var10000;
            if (Minecraft.player != null) {
                  Minecraft.player.noClip = true;
                  Minecraft.player.motionY = 0.0D;
                  Minecraft.player.onGround = false;
                  Minecraft.player.capabilities.isFlying = false;
                  if (mc.gameSettings.keyBindJump.isKeyDown()) {
                        var10000 = Minecraft.player;
                        var10000.motionY += 0.5D;
                  }

                  if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                        var10000 = Minecraft.player;
                        var10000.motionY -= 0.5D;
                  }
            } else {
                  double d = (double)Minecraft.player.getHorizontalFacing().getDirectionVec().getX() * 1.273197475E-15D;
                  double d2 = (double)Minecraft.player.getHorizontalFacing().getDirectionVec().getZ() * 1.273197475E-15D;
                  Minecraft.player.motionY = 0.0D;
                  if (mc.gameSettings.keyBindJump.isKeyDown()) {
                        var10000 = Minecraft.player;
                        var10000.motionY += 4.24399158E-15D;
                  }

                  if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                        var10000 = Minecraft.player;
                        var10000.motionY -= 4.24399158E-15D;
                  }

                  if (Minecraft.player.isCollidedVertically) {
                        Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY + 1.273197475E-14D, Minecraft.player.posZ, true));
                        Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX + d * 0.0D, Minecraft.player.posY, Minecraft.player.posZ + d2 * 0.0D, true));
                        Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ, true));
                  }
            }

      }

      public void onDisable() {
            super.onDisable();
            Minecraft.player.onGround = false;
      }
}
