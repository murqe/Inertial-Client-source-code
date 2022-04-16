package wtf.rich.client.features.impl.combat;

import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class FastBow extends Feature {
     private final NumberSetting ticks = new NumberSetting("Bow Ticks", 1.5F, 1.5F, 10.0F, 0.5F, () -> {
          return true;
     });

     public FastBow() {
          super("FastBow", "При зажатии на ПКМ игрок быстро стреляет из лука", 0, Category.COMBAT);
          this.addSettings(new Setting[]{this.ticks});
     }

     @EventTarget
     public void onUpdate(EventUpdate e) {
          if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow && mc.player.isBowing() && (float)mc.player.getItemInUseMaxCount() >= this.ticks.getNumberValue()) {
               mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
               mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
               mc.player.stopActiveHand();
          }

     }
}
