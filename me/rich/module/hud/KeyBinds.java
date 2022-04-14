package me.rich.module.hud;

import de.Hero.settings.Setting;
import java.awt.Color;
import java.util.Iterator;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.Event2D;
import me.rich.font.Fonts;
import me.rich.helpers.render.RenderUtil;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.module.FeatureDirector;

public class KeyBinds extends Feature {
      public KeyBinds() {
            super("KeyBinds", 0, Category.HUD);
            Main var10000 = Main.instance;
            Main.settingsManager.rSetting(new Setting("PositionX", this, 360.0D, 0.0D, 500.0D, true));
            var10000 = Main.instance;
            Main.settingsManager.rSetting(new Setting("PositionY", this, 150.0D, 0.0D, 170.0D, true));
      }

      @EventTarget
      public void biba(Event2D event) {
            float x = Main.settingsManager.getSettingByName(Main.moduleManager.getModule(KeyBinds.class), "PositionX").getValFloat();
            float y = Main.settingsManager.getSettingByName(Main.moduleManager.getModule(KeyBinds.class), "PositionY").getValFloat();
            RenderUtil.drawGradientRect((double)x, (double)y, (double)(x + 95.0F), (double)(y + 10.0F), (new Color(11, 11, 11, 255)).getRGB(), (new Color(11, 11, 11, 255)).getRGB());
            RenderUtil.drawGradientSideways((double)x, (double)y, (double)(x + 95.0F), (double)(y + 1.0F), -1, (new Color(255, 255, 255, 220)).getRGB());
            Fonts.sfui14.drawStringWithShadow("binds", (double)(x + 40.0F), (double)(y + 4.0F), -1);
            double offsetY = (double)(y + 3.0F);
            FeatureDirector var10000 = Main.moduleManager;
            Iterator var6 = FeatureDirector.modules.iterator();

            while(var6.hasNext()) {
                  Feature f = (Feature)var6.next();
                  if (f.isToggled() && f.getKey() != 0) {
                        Fonts.sfui14.drawStringWithShadow(f.getName().toLowerCase(), (double)(x + 2.0F), offsetY + 10.0D, -1);
                        Fonts.sfui14.drawStringWithShadow("toggled", (double)(x + 92.0F - (float)Fonts.sfui14.getStringWidth("toggled")), offsetY + 10.0D, -1);
                        offsetY += 8.0D;
                  }
            }

      }
}
