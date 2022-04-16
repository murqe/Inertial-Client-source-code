package me.rich.module.movement;

import me.rich.event.EventTarget;
import me.rich.event.events.EventSendPacket;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class LongJump extends Feature {
      int air;

      public LongJump() {
            super("LongJump", 0, Category.MOVEMENT);
      }

      @EventTarget
      public void onSendPacket(EventSendPacket event) {
      }

      @EventTarget
      public void onUpdate(EventUpdate event) {
            if (!Minecraft.player.onGround) {
                  ++this.air;
            } else if (this.air > 3) {
                  this.setEnabled(false);
            }

            if (Minecraft.player.onGround || this.air == 0) {
                  Minecraft.player.jump();
            }

            Minecraft.player.jumpMovementFactor = 0.15F;
            EntityPlayerSP var10000 = Minecraft.player;
            var10000.motionY += 0.05D;
      }
}
