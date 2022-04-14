package me.rich.module.player;

import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;

public class ParticleTrails extends Feature {
      public ParticleTrails() {
            super("ParticleTrails", 0, Category.RENDER);
      }

      @EventTarget
      public void onUpdate(EventUpdate e) {
            Minecraft var10002 = mc;
            Minecraft var10003 = mc;
            Minecraft var10004 = mc;
            Minecraft var10005 = mc;
            Minecraft var10006 = mc;
            Minecraft var10007 = mc;
            mc.world.spawnParticle(EnumParticleTypes.DRIP_LAVA, Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ, Minecraft.player.motionX, Minecraft.player.motionY, Minecraft.player.motionZ, new int[]{2});
      }
}
