package me.rich.module.misc;

import me.rich.event.EventTarget;
import me.rich.event.events.EventPreUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;

public class TeleportBack extends Feature {
      public TeleportBack() {
            super("TeleportBack", 0, Category.MISC);
      }

      @EventTarget
      public void onUpdate(EventPreUpdate event) {
            if (event.isOnGround()) {
                  Minecraft var10000 = mc;
                  Minecraft.player.setSprinting(false);
                  event.setOnGround(false);
            }

      }

      public void onDisable() {
            mc.getConnection().sendPacket(new CPacketPlayer.Position(0.0D, 0.0D, 0.0D, false));
      }
}
