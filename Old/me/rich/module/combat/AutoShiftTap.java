package me.rich.module.combat;

import de.Hero.settings.Setting;
import java.util.ArrayList;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;

public class AutoShiftTap extends Feature {
      public AutoShiftTap() {
            super("AutoShiftTap", 0, Category.COMBAT);
      }

      public void setup() {
            ArrayList options = new ArrayList();
            options.add("OnMouse");
            options.add("OnJump");
            Main var10000 = Main.instance;
            Main.settingsManager.rSetting(new Setting("Tap Mode", this, "OnMouse", options));
      }

      @EventTarget
      public void onUpdate(EventUpdate event) {
            Main var10000 = Main.instance;
            String mode = Main.settingsManager.getSettingByName("Tap Mode").getValString();
            (new StringBuilder()).append(Character.toUpperCase(mode.charAt(0))).append(mode.substring(1)).toString();
            if (mode.equalsIgnoreCase("OnMouse")) {
                  if (mc.gameSettings.keyBindAttack.pressed) {
                        mc.gameSettings.keyBindSneak.pressed = true;
                  } else {
                        mc.gameSettings.keyBindSneak.pressed = false;
                  }
            }

            if (mode.equalsIgnoreCase("OnJump")) {
                  Minecraft var4 = mc;
                  if ((double)Minecraft.player.fallDistance >= 0.08D) {
                        mc.gameSettings.keyBindSneak.pressed = true;
                  } else {
                        var4 = mc;
                        if (Minecraft.player.onGround) {
                              mc.gameSettings.keyBindSneak.pressed = false;
                        }
                  }
            }

      }
}
