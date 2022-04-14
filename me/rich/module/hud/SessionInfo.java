package me.rich.module.hud;

import java.awt.Color;
import me.rich.event.EventTarget;
import me.rich.event.events.EventRender2D;
import me.rich.font.Fonts;
import me.rich.helpers.render.ColorUtils;
import me.rich.helpers.render.RenderUtil;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;

public class SessionInfo extends Feature {
      public SessionInfo() {
            super("SessionInfo", 0, Category.HUD);
      }

      @EventTarget
      public void onRender2D(EventRender2D e) {
            double prevX = Minecraft.player.posX - Minecraft.player.prevPosX;
            double prevZ = Minecraft.player.posZ - Minecraft.player.prevPosZ;
            double lastDist = Math.sqrt(prevX * prevX + prevZ * prevZ);
            double currSpeed = lastDist * 15.3571428571D;
            String speed = String.format("%.2f blocks/sec", currSpeed);
            String server = mc.isSingleplayer() ? "local" : mc.getCurrentServerData().serverIP.toLowerCase();
            StringBuilder var10000 = (new StringBuilder()).append("Name: ");
            Minecraft var10001 = mc;
            String name = var10000.append(Minecraft.player.getName()).toString();
            float str1 = (float)Fonts.sfui15.getStringWidth(name);
            float str2 = (float)Fonts.sfui15.getStringWidth("IP: " + server);
            RenderUtil.drawNewRect(5.0D, 50.0D, (double)(str1 > str2 ? str1 : str2) + 35.5D, 94.0D, (new Color(31, 31, 30, 255)).getRGB());
            RenderUtil.drawNewRect(5.0D, 63.0D, (double)(str1 > str2 ? str1 : str2) + 35.5D, 63.5D, (new Color(88, 88, 88, 255)).getRGB());
            RenderUtil.drawNewRect(5.0D, 50.0D, (double)(str1 > str2 ? str1 : str2) + 35.5D, 51.0D, ColorUtils.astolfoColors1(0.0F, 0.0F));
            Fonts.sfui20.drawStringWithShadow("Session Info", 20.5D, 54.5D, -1);
            Fonts.sfui15.drawStringWithShadow("Speed: " + speed, 8.5D, 68.0D, -1);
            Fonts.sfui15.drawStringWithShadow(name, 8.5D, 77.0D, -1);
            Fonts.sfui15.drawStringWithShadow("IP: " + server, 8.5D, 86.0D, -1);
      }
}
