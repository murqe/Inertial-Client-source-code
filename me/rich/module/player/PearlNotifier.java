package me.rich.module.player;

import java.util.Iterator;
import me.rich.event.EventTarget;
import me.rich.event.events.Event3D;
import me.rich.helpers.other.ChatUtils;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.module.combat.KillAura;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderPearl;

public class PearlNotifier extends Feature {
      EntityLivingBase target;

      public PearlNotifier() {
            super("PearlESP", 0, Category.PLAYER);
            this.target = KillAura.target;
      }

      @EventTarget
      public void onRender(Event3D event) {
            Iterator var2 = mc.world.loadedEntityList.iterator();

            while(var2.hasNext()) {
                  Entity e = (Entity)var2.next();
                  if (e instanceof EntityEnderPearl && e != null) {
                        float pTicks = mc.timer.renderPartialTicks;
                        double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double)pTicks - mc.getRenderManager().viewerPosX;
                        double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double)pTicks - mc.getRenderManager().viewerPosY;
                        double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double)pTicks - mc.getRenderManager().viewerPosZ;
                        ChatUtils.addChatMessage("Pearl Coord: X: " + e.getPosition().getX() + " Y: " + e.getPosition().getY() + " Z: " + e.getPosition().getZ());
                  }
            }

      }
}
