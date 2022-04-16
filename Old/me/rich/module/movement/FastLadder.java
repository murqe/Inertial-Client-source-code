package me.rich.module.movement;

import de.Hero.settings.Setting;
import me.rich.Main;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;

public class FastLadder extends Feature {
      public void setup() {
            Main var10000 = Main.instance;
            Main.settingsManager.rSetting(new Setting("LadderSpeed", this, 5.0D, 1.1D, 10.0D, true));
      }

      public FastLadder() {
            super("FastLadder", 0, Category.MOVEMENT);
      }

      public void onUpdate() {
            Minecraft var10000 = mc;
            if (Minecraft.player.isOnLadder()) {
                  var10000 = mc;
                  if (Minecraft.player.isCollidedHorizontally) {
                        var10000 = mc;
                        if (Minecraft.player.ticksExisted % 15 == 0) {
                              var10000 = mc;
                              Minecraft.player.connection.sendPacket(new CPacketPlayer(true));
                        }

                        var10000 = mc;
                        if (Minecraft.player.ticksExisted % 2 == 0) {
                              var10000 = mc;
                              Minecraft var10001 = mc;
                              Minecraft var10002 = mc;
                              double var1 = Minecraft.player.posY + 0.1D;
                              Minecraft var10003 = mc;
                              Minecraft.player.setPosition(Minecraft.player.posX, var1, Minecraft.player.posZ);
                        }
                  }
            }

      }
}
