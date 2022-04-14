package me.rich.module.misc;

import de.Hero.settings.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventPreMotionUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;

public class VClip extends Feature {
      public VClip() {
            super("VClip", 0, Category.MISC);
            Main.settingsManager.rSetting(new Setting("ClipRange", this, 50.0D, 1.0D, 200.0D, false));
      }

      @EventTarget
      public void onUpdate(EventPreMotionUpdate event) {
            if (Minecraft.player != null || mc.world != null) {
                  float tp = Main.settingsManager.getSettingByName("ClipRange").getValFloat();

                  for(int i = 0; i < 1; ++i) {
                        Minecraft.player.setEntityBoundingBox(Minecraft.player.getEntityBoundingBox().offset(0.0D, (double)(-tp), 0.0D));
                        this.toggle();
                  }
            }

      }
}
