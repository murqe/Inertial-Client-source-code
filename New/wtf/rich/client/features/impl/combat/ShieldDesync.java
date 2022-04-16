package wtf.rich.client.features.impl.combat;

import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import wtf.rich.Main;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventPreMotionUpdate;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;

public class ShieldDesync extends Feature {
     public ShieldDesync() {
          super("ShieldDesync", "Десинкает шилд-брейкеры противников(вам не смогут сломать щит)", 0, Category.COMBAT);
     }

     @EventTarget
     public void onEventPreMotion(EventPreMotionUpdate event) {
          if (Main.instance.featureDirector.getFeatureByClass(KillAura.class).isToggled() && mc.player.isBlocking() && KillAura.target != null && mc.player.ticksExisted % 2 == 0) {
               mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(900, 900, 900), EnumFacing.NORTH));
               mc.playerController.processRightClick(mc.player, mc.world, EnumHand.OFF_HAND);
          }

     }
}
