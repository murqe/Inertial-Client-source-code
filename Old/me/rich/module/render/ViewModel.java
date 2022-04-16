package me.rich.module.render;

import de.Hero.settings.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventTransformSideFirstPerson;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHandSide;

public class ViewModel extends Feature {
      public static Setting rightx;
      public static Setting righty;
      public static Setting rightz;
      public static Setting leftx;
      public static Setting lefty;
      public static Setting leftz;

      public ViewModel() {
            super("ViewModel", 0, Category.RENDER);
            rightx = new Setting("RightX", this, 0.0D, -2.0D, 2.0D, false);
            Main var10000 = Main.instance;
            Main.settingsManager.rSetting(rightx);
            righty = new Setting("RightY", this, 0.2D, -2.0D, 2.0D, false);
            var10000 = Main.instance;
            Main.settingsManager.rSetting(righty);
            rightz = new Setting("RightZ", this, 0.2D, -2.0D, 2.0D, false);
            var10000 = Main.instance;
            Main.settingsManager.rSetting(rightz);
            leftx = new Setting("LeftX", this, 0.0D, -2.0D, 2.0D, false);
            var10000 = Main.instance;
            Main.settingsManager.rSetting(leftx);
            lefty = new Setting("LeftY", this, 0.2D, -2.0D, 2.0D, false);
            var10000 = Main.instance;
            Main.settingsManager.rSetting(lefty);
            leftz = new Setting("LeftZ", this, 0.2D, -2.0D, 2.0D, false);
            var10000 = Main.instance;
            Main.settingsManager.rSetting(leftz);
      }

      @EventTarget
      public void onSidePerson(EventTransformSideFirstPerson event) {
            if (event.getEnumHandSide() == EnumHandSide.RIGHT) {
                  GlStateManager.translate(rightx.getValDouble(), righty.getValDouble(), rightz.getValDouble());
            }

            if (event.getEnumHandSide() == EnumHandSide.LEFT) {
                  GlStateManager.translate(-leftx.getValDouble(), lefty.getValDouble(), leftz.getValDouble());
            }

      }
}
