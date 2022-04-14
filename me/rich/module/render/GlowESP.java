package me.rich.module.render;

import java.util.Iterator;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class GlowESP extends Feature {
      public GlowESP() {
            super("GlowESP", 0, Category.RENDER);
      }

      public void onDisable() {
            Iterator var1 = mc.world.playerEntities.iterator();

            while(var1.hasNext()) {
                  EntityPlayer player = (EntityPlayer)var1.next();
                  if (player.isGlowing()) {
                        player.setGlowing(false);
                  }
            }

            super.onDisable();
      }

      @EventTarget
      public void onEvent(EventUpdate e) {
            if (Minecraft.player != null) {
                  Iterator var2 = mc.world.loadedEntityList.iterator();

                  while(var2.hasNext()) {
                        Entity player = (Entity)var2.next();
                        if (player instanceof EntityPlayer) {
                              player.setGlowing(true);
                        }
                  }

            }
      }
}
