package wtf.rich.client.features.impl.player;

import com.mojang.realmsclient.gui.ChatFormatting;
import wtf.rich.Main;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventReceiveMessage;
import wtf.rich.api.utils.notifications.NotificationPublisher;
import wtf.rich.api.utils.notifications.NotificationType;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;

public class AutoAuth extends Feature {
     public static String password = "qwerty123";

     public AutoAuth() {
          super("AutoAuth", "Автоматически регестрируется и логинится на серверах", 0, Category.PLAYER);
     }

     @EventTarget
     public void onReceiveMessage(EventReceiveMessage event) {
          if (!event.getMessage().contains("/reg") && !event.getMessage().contains("/register") && !event.getMessage().contains("Зарегистрируйтесь")) {
               if (event.getMessage().contains("Авторизуйтесь") || event.getMessage().contains("/l")) {
                    mc.player.sendChatMessage("/login " + password);
                    NotificationPublisher.queue("AutoAuth", "You are successfully login!", NotificationType.SUCCESS);
               }
          } else {
               mc.player.sendChatMessage("/reg " + password + " " + password);
               Main.msg("Your password: " + ChatFormatting.RED + password, true);
               NotificationPublisher.queue("AutoAuth", "You are successfully registered!", NotificationType.SUCCESS);
          }

     }
}
