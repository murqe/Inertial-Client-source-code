package me.rich.module.misc;

import me.rich.event.EventTarget;
import me.rich.event.events.EventPreMotionUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.CPacketEntityAction;

public class Disabler extends Feature {
      public Disabler() {
            super("Disabler", 0, Category.MISC);
      }

      @EventTarget
      public void onUpdate(EventPreMotionUpdate event) {
            Minecraft var10000 = mc;
            if (Minecraft.player.ticksExisted % 4 == 0) {
                  NetHandlerPlayClient var2 = mc.getConnection();
                  Minecraft var10003 = mc;
                  var2.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_FALL_FLYING));
            }

      }
}
