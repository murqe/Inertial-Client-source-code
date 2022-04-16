package me.rich.module.movement;

import de.Hero.settings.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventPreMotionUpdate;
import me.rich.event.events.MovementUtil;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;

public class WaterSpeed extends Feature {
      public int ticks = 0;
      public Setting speed = new Setting("Speed", this, 1.0D, 0.10000000149011612D, 5.0D, false);

      public WaterSpeed() {
            super("WaterSpeed", 0, Category.MOVEMENT);
            Main.settingsManager.rSetting(this.speed);
      }

      @EventTarget
      public void onPreMotion(EventPreMotionUpdate event) {
            if (Minecraft.player.isInWater()) {
                  ++this.ticks;
                  if (this.ticks == 4) {
                        MovementUtil.setSpeed(this.speed.getValDouble());
                  }

                  if (this.ticks >= 5) {
                        MovementUtil.setSpeed(this.speed.getValDouble());
                        this.ticks = 0;
                  }

                  MovementUtil.setSpeed(this.speed.getValDouble());
            }

      }
}
