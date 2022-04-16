package wtf.rich.client.features.impl.movement;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.BlockPos;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class WaterLeave extends Feature {
     private final NumberSetting leaveMotion = new NumberSetting("Motion Y", 10.0F, 0.5F, 10.0F, 0.5F, () -> {
          return true;
     });

     public WaterLeave() {
          super("WaterLeave", "Игрок высоко прыгает при погружении в воду", 0, Category.MOVEMENT);
          this.addSettings(new Setting[]{this.leaveMotion});
     }

     @EventTarget
     public void onUpdate(EventUpdate event) {
          BlockPos blockPos = new BlockPos(mc.player.posX, mc.player.posY - 0.1D, mc.player.posZ);
          Block block = mc.world.getBlockState(blockPos).getBlock();
          if (block instanceof BlockLiquid) {
               if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 0.01D, mc.player.posZ)).getBlock() == Block.getBlockById(9) && mc.player.isInsideOfMaterial(Material.AIR)) {
                    mc.player.motionY = 0.08D;
               }

               if (!mc.player.isInLiquid() && mc.player.fallDistance > 0.0F && mc.player.motionY < 0.08D) {
                    EntityPlayerSP var10000 = mc.player;
                    var10000.motionY += (double)this.leaveMotion.getNumberValue();
               }
          }

     }
}
