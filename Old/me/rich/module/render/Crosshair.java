package me.rich.module.render;

import de.Hero.settings.Setting;
import java.awt.Color;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.Event2D;
import me.rich.helpers.render.RenderHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MovementInput;

public class Crosshair extends Feature {
      private final Setting dynamic;
      private final Setting colorRed = new Setting("Red", this, 249.0D, 0.0D, 255.0D, true);
      private final Setting colorGreen;
      private final Setting colorBlue;
      private final Setting width;
      private final Setting gap;
      private final Setting length;
      private final Setting dynamicGap;

      public Crosshair() {
            super("Crosshair", 0, Category.RENDER);
            Main.settingsManager.rSetting(this.colorRed);
            this.colorGreen = new Setting("Green", this, 255.0D, 0.0D, 255.0D, true);
            Main.settingsManager.rSetting(this.colorGreen);
            this.colorBlue = new Setting("Blue", this, 0.0D, 0.0D, 255.0D, true);
            Main.settingsManager.rSetting(this.colorBlue);
            this.width = new Setting("Width", this, 1.0D, 0.5D, 8.0D, true);
            Main.settingsManager.rSetting(this.width);
            this.gap = new Setting("Gap", this, 2.0D, 0.5D, 10.0D, true);
            Main.settingsManager.rSetting(this.gap);
            this.length = new Setting("Length", this, 3.0D, 0.5D, 30.0D, true);
            Main.settingsManager.rSetting(this.length);
            this.dynamic = new Setting("Dynamic", this, false);
            Main.settingsManager.rSetting(this.dynamic);
            this.dynamicGap = new Setting("Dynamic Gap", this, 3.0D, 1.0D, 20.0D, true);
            Main.settingsManager.rSetting(this.dynamicGap);
      }

      @EventTarget
      public void on2D(Event2D event) {
            int color = (new Color(this.colorRed.getValFloat() / 255.0F, this.colorGreen.getValFloat() / 255.0F, this.colorBlue.getValFloat() / 255.0F)).getRGB();
            int screenWidth = (int)event.getWidth();
            int screenHeight = (int)event.getHeight();
            int wMiddle = screenWidth / 2;
            int hMiddle = screenHeight / 2;
            boolean dyn = this.dynamic.getValBoolean();
            double dyngap = this.dynamicGap.getValDouble();
            double wid = this.width.getValDouble();
            double len = this.length.getValDouble();
            boolean wider = dyn && this.isMoving();
            double gaps = wider ? dyngap : this.gap.getValDouble();
            RenderHelper.drawBorderedRect((double)wMiddle - gaps - len, (double)hMiddle - wid / 2.0D, (double)wMiddle - gaps, (double)hMiddle + wid / 2.0D, 0.5D, Color.black.getRGB(), color, false);
            RenderHelper.drawBorderedRect((double)wMiddle + gaps, (double)hMiddle - wid / 2.0D, (double)wMiddle + gaps + len, (double)hMiddle + wid / 2.0D, 0.5D, Color.black.getRGB(), color, false);
            RenderHelper.drawBorderedRect((double)wMiddle - wid / 2.0D, (double)hMiddle - gaps - len, (double)wMiddle + wid / 2.0D, (double)hMiddle - gaps, 0.5D, Color.black.getRGB(), color, false);
            RenderHelper.drawBorderedRect((double)wMiddle - wid / 2.0D, (double)hMiddle + gaps, (double)wMiddle + wid / 2.0D, (double)hMiddle + gaps + len, 0.5D, Color.black.getRGB(), color, false);
      }

      public boolean isMoving() {
            if (MovementInput.field_192832_b != 0.0F) {
                  return true;
            } else {
                  return Minecraft.player.moveStrafing != 0.0F;
            }
      }
}
