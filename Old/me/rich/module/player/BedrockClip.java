package me.rich.module.player;

import de.Hero.settings.Setting;
import java.util.ArrayList;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;

public class BedrockClip extends Feature {
      public BedrockClip() {
            super("BedrockClip", 0, Category.PLAYER);
            ArrayList clip = new ArrayList();
            clip.add("OnWater");
            clip.add("OnGround");
            Main.settingsManager.rSetting(new Setting("Clip mode", this, "OnWater", clip));
      }

      @EventTarget
      public void shlyxa(EventUpdate event) {
            String mode = Main.settingsManager.getSettingByName("Clip mode").getValString();
            Minecraft var10000;
            Minecraft var10001;
            Minecraft var10002;
            Minecraft var10003;
            double var3;
            if (mode.equalsIgnoreCase("OnWater")) {
                  var10000 = mc;
                  if (Minecraft.player.isInWater()) {
                        var10000 = mc;
                        var10001 = mc;
                        var10002 = mc;
                        var10003 = mc;
                        var3 = Minecraft.player.posY - (Minecraft.player.posY + 2.0D);
                        var10003 = mc;
                        Minecraft.player.setPosition(Minecraft.player.posX, var3, Minecraft.player.posZ);
                        this.toggle();
                  }
            }

            if (mode.equalsIgnoreCase("OnGround") && Minecraft.player.onGround) {
                  var10000 = mc;
                  var10001 = mc;
                  var10002 = mc;
                  var10003 = mc;
                  var3 = Minecraft.player.posY - (Minecraft.player.posY + 2.0D);
                  var10003 = mc;
                  Minecraft.player.setPosition(Minecraft.player.posX, var3, Minecraft.player.posZ);
                  this.toggle();
            }

      }
}
