package me.rich.module.misc;

import me.rich.event.EventTarget;
import me.rich.event.events.EventReceivePacket;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

public class NoSRotations extends Feature {
      public NoSRotations() {
            super("NoSRotations", 0, Category.MISC);
      }

      @EventTarget
      public void onPacket(EventReceivePacket event) {
            if (event.getPacket() instanceof SPacketPlayerPosLook) {
                  Packet packet = event.getPacket();
                  ((SPacketPlayerPosLook)packet).yaw = Minecraft.player.rotationYaw;
                  ((SPacketPlayerPosLook)packet).pitch = Minecraft.player.rotationPitch;
            }

      }

      public void onEnable() {
            super.onEnable();
      }

      public void onDisable() {
            super.onDisable();
      }
}
