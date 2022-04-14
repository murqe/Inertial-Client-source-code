package me.rich.helpers.render.cosmetic;

import me.rich.helpers.render.cosmetic.impl.Wings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class Cosmetic {
      public static void renderAccessory(String[] accessorys, EntityPlayer player, float partialTicks) {
            String[] var6 = accessorys;
            int var5 = accessorys.length;
            int var4 = 0;

            while(var4 < var5) {
                  String accessory = var6[var4];
                  switch(accessory.hashCode()) {
                  case -645481:
                        if (accessory.equals("Dragon_wing")) {
                              Wings.render(player, partialTicks);
                        }
                  default:
                        ++var4;
                  }
            }

      }

      public static ResourceLocation getCape(String cape) {
            return new ResourceLocation("toperclient/" + cape + ".png");
      }

      public static ResourceLocation getWing(String wing) {
            return new ResourceLocation("toperclient/" + wing + ".png");
      }
}
