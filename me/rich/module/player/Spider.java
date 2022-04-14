package me.rich.module.player;

import de.Hero.settings.Setting;
import java.util.ArrayList;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventPacket;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;

public class Spider extends Feature {
      public Spider() {
            super("Spider", 0, Category.PLAYER);
            ArrayList spider = new ArrayList();
            spider.add("Vanilla");
            spider.add("Matrix");
            Main.settingsManager.rSetting(new Setting("Spider Mode", this, "Vanilla", spider));
      }

      @EventTarget
      public void EventSendPacket(EventPacket event) {
            String mode = Main.settingsManager.getSettingByName("Spider Mode").getValString();
            if (mode.equalsIgnoreCase("Vanilla") && Minecraft.player.isCollidedHorizontally) {
                  Minecraft.player.motionY = 0.4000000059604645D;
            }

            if (mode.equalsIgnoreCase("Matrix") && Minecraft.player.isCollidedHorizontally && timerHelper.hasReached(150.0D)) {
                  timerHelper.reset();
                  Minecraft.player.jump();
                  Minecraft.player.motionY = 0.30000001192092896D;
            }

      }

      public void onEnable() {
            super.onEnable();
      }

      public void onDisable() {
            super.onDisable();
      }
}
