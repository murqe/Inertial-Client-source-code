package me.rich.module.combat;

import me.rich.event.EventTarget;
import me.rich.event.events.EventPacket;
import me.rich.helpers.friend.FriendManager;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.network.play.client.CPacketUseEntity;

public class NoFriendDamage extends Feature {
      public NoFriendDamage() {
            super("NoFriendDamage", 0, Category.COMBAT);
      }

      @EventTarget
      public void onPacket(EventPacket e) {
            if (e.getPacket() instanceof CPacketUseEntity) {
                  CPacketUseEntity packet = (CPacketUseEntity)e.getPacket();
                  if (packet.getAction() == CPacketUseEntity.Action.ATTACK) {
                        FriendManager.getFriends();
                        if (FriendManager.isFriend(mc.objectMouseOver.entityHit.getName())) {
                              e.setCancelled(true);
                        }
                  }
            }

      }

      public void onEnable() {
            super.onEnable();
      }

      public void onDisable() {
            super.onDisable();
      }
}
