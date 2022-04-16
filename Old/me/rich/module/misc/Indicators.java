package me.rich.module.misc;

import de.Hero.settings.Setting;
import java.awt.Color;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.Event2D;
import me.rich.font.Fonts;
import me.rich.helpers.render.AnimationHelper;
import me.rich.helpers.render.ColorUtils;
import me.rich.helpers.render.RenderHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.MathHelper;

public class Indicators extends Feature {
      private double cooldownBarWidth;
      private double hurttimeBarWidth;

      public Indicators() {
            super("Indicators", 0, Category.MISC);
            Main.settingsManager.rSetting(new Setting("IndicatorX", this, 100.0D, 0.0D, 480.0D, true));
            Main.settingsManager.rSetting(new Setting("IndicatorY", this, 60.0D, 60.0D, 400.0D, true));
      }

      @EventTarget
      public void goroda(Event2D mamanooma) {
            ScaledResolution sr = new ScaledResolution(mc);
            float scaledWidth = (float)sr.getScaledWidth();
            float scaledHeight = (float)sr.getScaledHeight();
            float x = scaledWidth / 2.0F - Main.settingsManager.getSettingByName(Main.moduleManager.getModule(Indicators.class), "IndicatorX").getValFloat();
            float y = scaledHeight / 2.0F + Main.settingsManager.getSettingByName(Main.moduleManager.getModule(Indicators.class), "IndicatorY").getValFloat();
            double prevZ = Minecraft.player.posZ - Minecraft.player.prevPosZ;
            double prevX = Minecraft.player.posX - Minecraft.player.prevPosX;
            double lastDist = Math.sqrt(prevX * prevX + prevZ * prevZ);
            double currSpeed = lastDist * 15.3571428571D / 4.0D;
            RenderHelper.drawNewRect((double)(x + -3.0F), (double)y + 196.5D - 405.0D, (double)x + 100.5D, (double)y + 232.5D - 408.0D, (new Color(11, 11, 11, 255)).getRGB());
            RenderHelper.drawNewRect((double)(x + -3.0F), (double)(y + 198.0F - 405.0F), (double)(x + 100.0F), (double)(y + 232.0F - 408.0F), (new Color(28, 28, 28, 255)).getRGB());
            RenderHelper.drawNewRect((double)(x + -3.0F), (double)(y + 198.0F - 405.0F), (double)(x + 100.0F), (double)(y + 208.0F - 408.0F), (new Color(21, 19, 20, 255)).getRGB());
            RenderHelper.drawNewRect((double)(x + 44.0F), (double)(y + 210.0F - 406.0F), (double)(x + 95.0F), (double)y + 213.5D - 406.0D, (new Color(41, 41, 41, 255)).getRGB());
            RenderHelper.drawNewRect((double)(x + 44.0F), (double)(y + 219.0F - 406.0F), (double)(x + 95.0F), (double)y + 222.5D - 406.0D, (new Color(41, 41, 41, 255)).getRGB());
            Fonts.futura_14.drawString("INDICATORS", (double)(x + 30.0F), (double)(y + 202.0F - 406.0F), -1);
            double cooldownPercentage = MathHelper.clamp((double)Minecraft.player.getCooledAttackStrength(1.0F), 0.0D, 1.0D);
            double cooldownWidth = 51.0D * cooldownPercentage;
            this.cooldownBarWidth = AnimationHelper.animate(cooldownWidth, this.cooldownBarWidth, 0.0229999852180481D);
            Gui.drawRect((double)(x + 44.0F), (double)(y + 210.0F - 406.0F), (double)(x + 44.0F) + this.cooldownBarWidth, (double)y + 213.5D - 406.0D, ColorUtils.astolfoColors1(0.0F, 0.0F));
            Fonts.futura_14.drawString("COOLDOWN", (double)(x + 0.0F), (double)(y + 211.0F - 406.0F), -1);
            double hurttimePercentage = MathHelper.clamp((double)Minecraft.player.hurtTime, 0.0D, 0.6D);
            double hurttimeWidth = 51.0D * hurttimePercentage;
            this.hurttimeBarWidth = AnimationHelper.animate(hurttimeWidth, this.hurttimeBarWidth, 0.0429999852180481D);
            Gui.drawRect((double)(x + 44.0F), (double)(y + 219.0F - 406.0F), (double)(x + 44.0F) + this.hurttimeBarWidth, (double)y + 222.5D - 406.0D, ColorUtils.astolfoColors1(0.0F, 0.0F));
            Fonts.futura_14.drawString("HURTTIME", (double)(x + 4.0F), (double)(y + 220.0F - 406.0F), -1);
      }

      public void onEnable() {
            super.onEnable();
      }

      public void onDisable() {
            super.onDisable();
      }
}
