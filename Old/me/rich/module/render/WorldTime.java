package me.rich.module.render;

import de.Hero.settings.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventPacket;
import me.rich.event.events.EventUpdate;
import me.rich.helpers.world.TimerHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.network.play.server.SPacketTimeUpdate;

public class WorldTime extends Feature {
      TimerHelper timer = new TimerHelper();

      public void setup() {
            Main.settingsManager.rSetting(new Setting("WorldTime", this, 5000.0D, 0.1D, 16000.0D, true));
            Main.settingsManager.rSetting(new Setting("SmoothTime", this, false));
            Main.settingsManager.rSetting(new Setting("SmoothTimeSpeed", this, 2.0D, 1.0D, 10.0D, false));
      }

      public WorldTime() {
            super("WorldTime", 0, Category.RENDER);
      }

      @EventTarget
      public void onPacket(EventPacket event) {
            if (event.getPacket() instanceof SPacketTimeUpdate) {
                  event.setCancelled(true);
            }

      }

      @EventTarget
      public void onUpdate(EventUpdate event) {
            boolean nosmooth = Main.settingsManager.getSettingByName("SmoothTime").getValBoolean();
            long time = (long)Main.settingsManager.getSettingByName("WorldTime").getValDouble();
            long speed = (long)Main.settingsManager.getSettingByName("SmoothTimeSpeed").getValDouble();
            if (!nosmooth) {
                  mc.world.setWorldTime(time);
            } else {
                  mc.world.setWorldTime(this.timer.getCurrentMS() * speed);
            }

            if (this.timer.getCurrentMS() > 30000L && this.isToggled()) {
                  this.timer.reset();
            }

      }
}
