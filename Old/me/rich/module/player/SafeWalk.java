package me.rich.module.player;

import me.rich.event.EventTarget;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Vector3d;

public class SafeWalk extends Feature {
      private final Vector3d vec = new Vector3d();

      public SafeWalk() {
            super("SafeWalk", 0, Category.PLAYER);
      }

      @EventTarget
      public void onSafeWalk(EventSafeWalk event) {
            if (Minecraft.player != null && mc.world != null) {
                  event.setCancelled(Minecraft.player.onGround);
            }
      }

      public void onEnable() {
            super.onEnable();
      }

      public void onDisable() {
            super.onDisable();
      }
}
