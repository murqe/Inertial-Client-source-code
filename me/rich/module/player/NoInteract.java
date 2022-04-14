package me.rich.module.player;

import de.Hero.settings.Setting;
import me.rich.event.EventTarget;
import me.rich.event.events.EventPacket;
import me.rich.event.events.EventPreMotionUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;

public class NoInteract extends Feature {
      public Setting craft = new Setting("Crafting table", this, true);
      public Setting furnace = new Setting("Furnace", this, true);
      public Setting armorStand = new Setting("Armor Stand", this, true);

      public NoInteract() {
            super("NoInteract", 0, Category.PLAYER);
      }

      @EventTarget
      public void onUpdate(EventPreMotionUpdate event) {
      }

      @EventTarget
      public void onPacket(EventPacket event) {
            if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
                  Block block = mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock();
                  if (block == Blocks.CRAFTING_TABLE && this.craft.getValBoolean() || block == Blocks.FURNACE && this.furnace.getValBoolean() || mc.objectMouseOver.entityHit != null && mc.objectMouseOver.entityHit instanceof EntityArmorStand && this.armorStand.getValBoolean()) {
                        event.setCancelled(true);
                  }
            }

      }
}
