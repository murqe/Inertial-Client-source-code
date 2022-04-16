package me.rich.module.player;

import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityBoat;

public class BoatFly extends Feature {
      public BoatFly() {
            super("BoatFly", 0, Category.PLAYER);
      }

      @EventTarget
      public void onPreMotion(EventUpdate event) {
            if (Minecraft.player.ridingEntity != null && Minecraft.player.ridingEntity instanceof EntityBoat) {
                  Minecraft.player.ridingEntity.motionY = mc.gameSettings.keyBindJump.pressed ? 0.5D : 0.0D;
            }

      }
}
