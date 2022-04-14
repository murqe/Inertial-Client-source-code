package me.rich.module.player;

import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;

public class ParkourHelper extends Feature {
      public ParkourHelper() {
            super("ParkourHelper", 0, Category.PLAYER);
      }

      @EventTarget
      public void onLocalPlayerUpdate(EventUpdate e) {
            if (Minecraft.player.onGround && !Minecraft.player.isSneaking() && !mc.gameSettings.keyBindJump.isPressed() && mc.world.getCollisionBoxes(Minecraft.player, Minecraft.player.getEntityBoundingBox().offset(0.0D, -0.5D, 0.0D).expand(-0.001D, 0.0D, -0.001D)).isEmpty()) {
                  Minecraft.player.jump();
            }

      }
}
