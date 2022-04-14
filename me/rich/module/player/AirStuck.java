package me.rich.module.player;

import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;

public class AirStuck extends Feature {
      public AirStuck() {
            super("AirStuck", 0, Category.PLAYER);
      }

      @EventTarget
      public void onUpdate(EventUpdate event) {
            Minecraft var10000 = mc;
            if (!Minecraft.player.isDead) {
                  var10000 = mc;
                  Minecraft.player.isDead = true;
            }

      }

      public void onDisable() {
            Minecraft var10000 = mc;
            Minecraft.player.isDead = false;
            super.onDisable();
      }
}
