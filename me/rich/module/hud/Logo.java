package me.rich.module.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.Hero.settings.Setting;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.Event2D;
import me.rich.font.Fonts;
import me.rich.helpers.render.ColorUtils;
import me.rich.helpers.render.RenderUtil;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetHandlerPlayClient;

public class Logo extends Feature {
      public int x;
      public int y;

      public Logo() {
            super("WaterMark", 0, Category.HUD);
      }

      public void setup() {
            ArrayList options = new ArrayList();
            options.add("Novoline");
            options.add("Inertial");
            options.add("InertialV2");
            options.add("InertialV3");
            options.add("InertialLose");
            options.add("OneTap");
            Main.settingsManager.rSetting(new Setting("Logo Mode", this, "NeverLose", options));
      }

      @EventTarget
      public void voopoo(Event2D eblan) {
            String bambumbim = Main.settingsManager.getSettingByName(Main.moduleManager.getModule(Logo.class), "Logo Mode").getValString();
            String server;
            if (bambumbim.equalsIgnoreCase("Novoline")) {
                  server = (new SimpleDateFormat("HH:mm")).format(Calendar.getInstance().getTime());
                  Fonts.sfui20.drawStringWithShadow(Main.name + ChatFormatting.GRAY + " (" + ChatFormatting.WHITE + server + ChatFormatting.GRAY + ")", 2.0D, 4.0D, ColorUtils.astolfoColors1(0.0F, 0.0F));
            }

            NetHandlerPlayClient var12;
            int ping;
            String text;
            float l;
            double posY;
            if (bambumbim.equalsIgnoreCase("Inertial")) {
                  server = (new SimpleDateFormat("HH:mm")).format(Calendar.getInstance().getTime());
                  Minecraft var10001 = mc;
                  var12 = (NetHandlerPlayClient)Objects.requireNonNull(mc.getConnection());
                  var10001 = mc;
                  ping = var12.getPlayerInfo(Minecraft.player.getUniqueID()).getResponseTime();
                  text = Main.name + " | " + Minecraft.player.getName() + " | " + Minecraft.getDebugFPS() + "fps | " + server + " | " + ping + "ms";
                  RenderUtil.drawNewRect(1.0D, 3.5D, (double)(2 + Fonts.sfui20.getStringWidth(text) + 1), (double)(5.5F + (float)Fonts.sfui20.getHeight() + 1.0F), -1879048192);
                  Fonts.sfui20.drawStringWithShadow(text, 2.0D, 6.5D, 16777215);
                  l = 4.0F;
                  int yTotal = false;
                  posY = 0.0D;
                  double width = (double)(Fonts.sfui20.getStringWidth(text) + 2);
                  RenderUtil.drawNewRect(1.5D, posY + 14.5D, (double)(2 + Fonts.sfui20.getStringWidth(text) + 1), posY + 16.0D, ColorUtils.astolfoColors1(0.0F, 0.0F));
            }

            if (bambumbim.equalsIgnoreCase("InertialV2")) {
                  server = mc.isSingleplayer() ? "local" : mc.getCurrentServerData().serverIP.toLowerCase();
                  NetHandlerPlayClient var12 = (NetHandlerPlayClient)Objects.requireNonNull(mc.getConnection());
                  String text = "InertialClient | " + Minecraft.player.getName() + " | " + Minecraft.getDebugFPS() + " fps | " + server;
                  RenderUtil.drawNewRect(1.0D, 7.0D, (double)(Fonts.sfui20.getStringWidth(text) + 5), 18.0D, (new Color(31, 31, 31, 255)).getRGB());
                  Fonts.sfui20.drawStringWithShadow(text, 2.0D, 9.0D, 16777215);
                  Fonts.sfui20.drawStringWithShadow("InterialClient", 2.0D, 9.0D, (new Color(255, 125, 255)).getRGB());
                  float yDist = 4.0F;
                  int yTotal = false;
                  double posY = 0.0D;
                  posY = (double)(Fonts.sfui20.getStringWidth(text) + 2);
            }

            String time;
            if (bambumbim.equalsIgnoreCase("InertialV3")) {
                  server = mc.isSingleplayer() ? "local" : mc.getCurrentServerData().serverIP.toLowerCase();
                  time = (new SimpleDateFormat("HH:mm:ss")).format(Calendar.getInstance().getTime());
                  var12 = (NetHandlerPlayClient)Objects.requireNonNull(mc.getConnection());
                  ping = var12.getPlayerInfo(Minecraft.player.getUniqueID()).getResponseTime();
                  text = "INERTIAL | " + server + " | " + Minecraft.getDebugFPS() + " fps | " + ping + "ms | " + time;
                  Gui.drawRect(5.0D, 6.0D, (double)(Fonts.sfui16.getStringWidth(text) + 9), 19.0D, (new Color(31, 31, 31, 255)).getRGB());
                  Gui.drawRect(5.0D, 6.5D, (double)(Fonts.sfui16.getStringWidth(text) + 9), 7.0D, (new Color(20, 20, 20, 100)).getRGB());
                  Fonts.sfui16.drawStringWithShadow(text, 7.0D, 10.5D, -1);
            }

            if (bambumbim.equalsIgnoreCase("OneTap")) {
                  server = mc.isSingleplayer() ? "local" : mc.getCurrentServerData().serverIP.toLowerCase();
                  time = (new SimpleDateFormat("HH:mm:ss")).format(Calendar.getInstance().getTime());
                  var12 = (NetHandlerPlayClient)Objects.requireNonNull(mc.getConnection());
                  ping = var12.getPlayerInfo(Minecraft.player.getUniqueID()).getResponseTime();
                  text = "INERTIAL | " + server + " | " + Minecraft.getDebugFPS() + " fps | " + ping + "ms | " + time;
                  Gui.drawRect(5.0D, 6.0D, (double)(Fonts.neverlose500_16.getStringWidth(text) + 9), 18.0D, (new Color(31, 31, 31, 255)).getRGB());

                  for(l = 0.0F; l < (float)(Fonts.neverlose500_16.getStringWidth(text) + 4); ++l) {
                        Gui.drawRect((double)(5.0F + l), 5.0D, (double)(l + 6.0F), 6.0D, RenderUtil.astolfoColors5(l - l + 15.0F, l, 0.5F, 3.0F));
                  }

                  Gui.drawRect(5.0D, 6.0D, (double)(Fonts.neverlose500_16.getStringWidth(text) + 9), 6.5D, (new Color(20, 20, 20, 100)).getRGB());
                  Fonts.neverlose500_16.drawStringWithShadow(text, 7.0D, 10.5D, -1);
            }

            if (bambumbim.equalsIgnoreCase("InertialLose")) {
                  server = mc.isSingleplayer() ? "local" : mc.getCurrentServerData().serverIP.toLowerCase();
                  time = (new SimpleDateFormat("HH:mm:ss")).format(Calendar.getInstance().getTime());
                  var12 = (NetHandlerPlayClient)Objects.requireNonNull(mc.getConnection());
                  ping = var12.getPlayerInfo(Minecraft.player.getUniqueID()).getResponseTime();
                  text = "Inertial";
                  String text = text + ChatFormatting.GRAY + " | " + ChatFormatting.RESET + Minecraft.player.getName() + ChatFormatting.GRAY + " | " + ChatFormatting.RESET + Minecraft.getDebugFPS() + "fps" + ChatFormatting.GRAY + " | " + ChatFormatting.RESET + server;
                  Gui.drawRect(5.0D, 6.0D, (double)(Fonts.neverlosewater15.getStringWidth(text) + 9), 19.0D, (new Color(7, 7, 7)).getRGB());
                  Gui.drawRect(5.0D, 6.5D, (double)(Fonts.neverlosewater15.getStringWidth(text) + 9), 7.0D, (new Color(7, 7, 7, 100)).getRGB());
                  Fonts.neverlosewater15.drawStringWithShadow(text, 6.6D, 9.1D, (new Color(26, 106, 255)).getRGB());
                  Fonts.neverlosewater15.drawStringWithShadow(text, 7.0D, 9.5D, -1);
            }

      }
}
