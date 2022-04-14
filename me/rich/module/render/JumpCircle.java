package me.rich.module.render;

import de.Hero.settings.Setting;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.Event3D;
import me.rich.event.events.EventUpdate;
import me.rich.helpers.DrawHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;

public class JumpCircle extends Feature {
      static List circles = new ArrayList();

      public JumpCircle() {
            super("JumpCircles", 0, Category.RENDER);
            Main.settingsManager.rSetting(new Setting("Speed", this, 1.0D, 0.1D, 0.5D, false));
            Main.settingsManager.rSetting(new Setting("Max Radius", this, 2.0D, 0.5D, 2.0D, false));
            Main.settingsManager.rSetting(new Setting("Line Width", this, 1.0D, 0.1D, 3.0D, false));
      }

      @EventTarget
      public void onJump(EventUpdate event) {
            if (Minecraft.player.jumpTicks >= 10) {
                  circles.add(new JumpCircle.Circle((float)Minecraft.player.posX, (float)(Minecraft.player.posY - 0.38D), (float)Minecraft.player.posZ));
            }

      }

      @EventTarget
      public void onRender(Event3D event) {
            if (circles.size() > 0) {
                  circles.removeIf((circlex) -> {
                        return circlex.factor > Main.settingsManager.getSettingByName(Main.moduleManager.getModule(JumpCircle.class), "Max Radius").getValFloat();
                  });
                  GlStateManager.resetColor();
                  Iterator var2 = circles.iterator();

                  while(var2.hasNext()) {
                        JumpCircle.Circle circle = (JumpCircle.Circle)var2.next();
                        circle.factor = MathHelper.lerp(circle.factor, (float)((double)Main.settingsManager.getSettingByName(Main.moduleManager.getModule(JumpCircle.class), "Max Radius").getValFloat() + 0.3D), (float)((double)(Main.settingsManager.getSettingByName(Main.moduleManager.getModule(JumpCircle.class), "Speed").getValFloat() * 2.0F) * Feature.deltaTime()));
                        DrawHelper.draw3DCircle(circle, mc.getRenderPartialTicks(), (double)circle.factor, Main.settingsManager.getSettingByName(Main.moduleManager.getModule(JumpCircle.class), "Line Width").getValFloat(), new Color(255, 255, 255));
                        DrawHelper.draw3DCircle(circle, mc.getRenderPartialTicks(), (double)circle.factor - 0.005D, Main.settingsManager.getSettingByName(Main.moduleManager.getModule(JumpCircle.class), "Line Width").getValFloat() + 0.5F, DrawHelper.setAlpha(new Color(199, 198, 198), 100));
                        DrawHelper.draw3DCircle(circle, mc.getRenderPartialTicks(), (double)circle.factor - 0.01D, Main.settingsManager.getSettingByName(Main.moduleManager.getModule(JumpCircle.class), "Line Width").getValFloat() + 0.5F, DrawHelper.setAlpha(new Color(180, 180, 180), 90));
                  }

                  GlStateManager.resetColor();
            }
      }

      public void onEnable() {
            super.onEnable();
      }

      public void onDisable() {
            super.onDisable();
      }

      public static class Circle {
            public final float spawnX;
            public final float spawnY;
            public final float spawnZ;
            public float factor = 0.0F;

            public Circle(float spawnX, float spawnY, float spawnZ) {
                  this.spawnX = spawnX;
                  this.spawnY = spawnY;
                  this.spawnZ = spawnZ;
            }
      }
}
