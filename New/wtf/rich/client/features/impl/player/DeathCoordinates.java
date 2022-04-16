package wtf.rich.client.features.impl.player;

import net.minecraft.client.gui.GuiGameOver;
import wtf.rich.Main;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.api.utils.notifications.NotificationPublisher;
import wtf.rich.api.utils.notifications.NotificationType;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;

public class DeathCoordinates extends Feature {
     public DeathCoordinates() {
          super("DeathCoordinates", "После смерти пишит ваши координаты в чат", 0, Category.PLAYER);
     }

     @EventTarget
     public void onUpdate(EventUpdate event) {
          if (mc.player.getHealth() < 1.0F && mc.currentScreen instanceof GuiGameOver) {
               int x = mc.player.getPosition().getX();
               int y = mc.player.getPosition().getY();
               int z = mc.player.getPosition().getZ();
               if (mc.player.ticksExisted % 20 == 0) {
                    NotificationPublisher.queue("Death Coordinates", "X: " + x + " Y: " + y + " Z: " + z, NotificationType.INFO);
                    Main.msg("Death Coordinates: X: " + x + " Y: " + y + " Z: " + z, true);
               }
          }

     }
}
