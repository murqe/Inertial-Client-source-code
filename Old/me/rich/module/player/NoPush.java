package me.rich.module.player;

import de.Hero.settings.Setting;
import me.rich.Main;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;

public class NoPush extends Feature {
      public NoPush() {
            super("NoPush", 0, Category.PLAYER);
            Main.settingsManager.rSetting(new Setting("Players", this, true));
            Main.settingsManager.rSetting(new Setting("Blocks", this, true));
            Main.settingsManager.rSetting(new Setting("Water", this, true));
      }

      public void onEnable() {
            super.onEnable();
            NotificationPublisher.queue(this.getName(), "Was enabled.", NotificationType.INFO);
      }

      public void onDisable() {
            NotificationPublisher.queue(this.getName(), "Was disabled.", NotificationType.INFO);
            super.onDisable();
      }
}
