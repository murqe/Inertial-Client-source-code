package me.rich.module.player;

import de.Hero.settings.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventSendPacket;
import me.rich.event.events.EventUpdate;
import me.rich.helpers.other.ChatUtils;
import me.rich.helpers.world.TimerHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

public class FakeLag extends Feature {
      TimerHelper timer;

      public void setup() {
            Main.settingsManager.rSetting(new Setting("LagPulse", this, 500.0D, 100.0D, 10000.0D, true));
      }

      public FakeLag() {
            super("FakeLag", 0, Category.PLAYER);
      }

      @EventTarget
      public void sendPacket(EventSendPacket event) {
            if (event.getPacket() instanceof SPacketPlayerPosLook) {
                  event.setCancelled(true);
                  ChatUtils.addChatMessage("Laggy!");
            }

      }

      @EventTarget
      public void onUpdate(EventUpdate event) {
            double tk = Main.settingsManager.getSettingByName("LagPulse").getValDouble();
      }
}
