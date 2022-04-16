package wtf.rich.client.features.impl.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.EntityLivingBase;
import wtf.rich.Main;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventMouseKey;
import wtf.rich.api.utils.friend.Friend;
import wtf.rich.api.utils.notifications.NotificationPublisher;
import wtf.rich.api.utils.notifications.NotificationType;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;

public class MCF extends Feature {
     EntityLivingBase friend;

     public MCF() {
          super("MiddleClickFriend", "Добавляет игрока в френд лист при нажатии на кнопку мыши", 0, Category.MISC);
     }

     @EventTarget
     public void onMouseEvent(EventMouseKey event) {
          if (event.getKey() == 2 && mc.pointedEntity instanceof EntityLivingBase) {
               if (Main.instance.friendManager.getFriends().stream().anyMatch((friend) -> {
                    return friend.getName().equals(mc.pointedEntity.getName());
               })) {
                    Main.instance.friendManager.getFriends().remove(Main.instance.friendManager.getFriend(mc.pointedEntity.getName()));
                    Main.msg(ChatFormatting.RED + "Removed " + ChatFormatting.RESET + "'" + mc.pointedEntity.getName() + "' as Friend!", true);
                    NotificationPublisher.queue("MCF", "Removed '" + mc.pointedEntity.getName() + "' as Friend!", NotificationType.INFO);
               } else {
                    Main.instance.friendManager.addFriend(new Friend(mc.pointedEntity.getName()));
                    Main.msg(ChatFormatting.GREEN + "Added " + ChatFormatting.RESET + "'" + mc.pointedEntity.getName() + "' as Friend!", true);
                    NotificationPublisher.queue("MCF", "Added " + mc.pointedEntity.getName() + " as Friend!", NotificationType.SUCCESS);
               }
          }

     }
}
