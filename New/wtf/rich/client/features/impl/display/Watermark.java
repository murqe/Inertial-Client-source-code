package wtf.rich.client.features.impl.display;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetHandlerPlayClient;
import wtf.rich.Main;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.Event2D;
import wtf.rich.api.utils.render.ClientHelper;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.ListSetting;

public class Watermark extends Feature {
     public static ListSetting waterMarkMode;

     public Watermark() {
          super("Watermark", "Client logo.", 35, Category.DISPLAY);
          waterMarkMode = new ListSetting("WaterMark Mode", "Novoline", () -> {
               return true;
          }, new String[]{"Novoline", "Onetap", "Dev", "OnetapV2", "Neverlose"});
          this.addSettings(new Setting[]{waterMarkMode});
     }

     @EventTarget
     public void ebatkopat(Event2D render) {
          int yTotal = 0;

          for(int i = 0; i < Main.instance.featureDirector.getFeatureList().size(); ++i) {
               yTotal += Minecraft.getMinecraft().sfui16.getFontHeight() + 5;
          }

          String watermark = waterMarkMode.getOptions();
          String name = mc.player.getName();
          this.setModuleName("Watermark §7" + watermark);
          String server;
          if (watermark.equalsIgnoreCase("Novoline")) {
               server = (new SimpleDateFormat("HH:mm")).format(Calendar.getInstance().getTime());
               int offset = Minecraft.getMinecraft().sfui16.getStringWidth(Main.instance.name + ChatFormatting.GRAY + " (" + ChatFormatting.WHITE + server + ChatFormatting.GRAY + ")");
               Minecraft.getMinecraft().sfui18.drawStringWithShadow(Main.instance.name + ChatFormatting.GRAY + " (" + ChatFormatting.WHITE + server + ChatFormatting.GRAY + ")", 3.0D, 4.0D, ClientHelper.getClientColor().getRGB());
          } else {
               String ping;
               String time;
               StringBuilder var10000;
               Minecraft var10001;
               if (watermark.equalsIgnoreCase("OnetapV2")) {
                    server = mc.isSingleplayer() ? "local" : mc.getCurrentServerData().serverIP.toLowerCase();
                    time = (new SimpleDateFormat("HH:mm:ss")).format(Calendar.getInstance().getTime());
                    var10000 = (new StringBuilder()).append("INERTIAL FREE | ").append(name).append(" | ").append(server).append(" | ");
                    var10001 = mc;
                    ping = var10000.append(Minecraft.getDebugFPS()).append(" fps | ").append(((NetHandlerPlayClient)Objects.requireNonNull(mc.getConnection())).getPlayerInfo(mc.player.getUniqueID()).getResponseTime()).append("ms | ").append(time).toString();
                    Gui.drawRect(5.0D, 6.0D, (double)(Minecraft.getMinecraft().neverlose500_15.getStringWidth(ping) + 9), 18.0D, (new Color(31, 31, 31, 255)).getRGB());

                    for(float l = 0.0F; l < (float)(Minecraft.getMinecraft().neverlose500_15.getStringWidth(ping) + 4); ++l) {
                         Gui.drawRect((double)(5.0F + l), 5.0D, (double)(l + 6.0F), 6.0D, ClientHelper.getClientColor(5.0F, l, 5).getRGB());
                    }

                    Gui.drawRect(5.0D, 6.0D, (double)(Minecraft.getMinecraft().neverlose500_15.getStringWidth(ping) + 9), 6.5D, (new Color(20, 20, 20, 100)).getRGB());
                    Minecraft.getMinecraft().neverlose500_15.drawStringWithShadow(ping, 7.0D, 10.5D, -1);
               } else if (watermark.equalsIgnoreCase("Dev")) {
                    if (!ClientFont.minecraftfont.getBoolValue()) {
                         ClientHelper.getFontRender().drawStringWithShadow(Main.instance.name, 3.0D, 4.0D, -1);
                    } else {
                         Minecraft var12 = mc;
                         Minecraft.fontRendererObj.drawStringWithShadow(Main.instance.name, 3.0F, 4.0F, -1);
                    }
               } else if (watermark.equalsIgnoreCase("Onetap")) {
                    server = mc.isSingleplayer() ? "local" : mc.getCurrentServerData().serverIP.toLowerCase();
                    time = (new SimpleDateFormat("HH:mm:ss")).format(Calendar.getInstance().getTime());
                    ping = mc.getConnection().getPlayerInfo(mc.player.getUniqueID()).getResponseTime() + "";
                    DrawHelper.drawGradientRect(5.0D, 185.0D, 95.0D, 198.0D, (new Color(22, 22, 22, 205)).getRGB(), (new Color(21, 21, 21, 0)).getRGB());

                    for(int l = 0; l <= 90; ++l) {
                         Gui.drawRect((double)(l + 5), 184.0D, (double)(l + 6), 185.0D, ClientHelper.getClientColor(5.0F, (float)l, 5).getRGB());
                    }

                    Minecraft.getMinecraft().neverlose500_13.drawStringWithShadow("inertialclient.pub", 28.0D, 190.0D, -1);
                    Minecraft.getMinecraft().neverlose500_13.drawStringWithShadow("user", 9.0D, 200.0D, -1);
                    Minecraft.getMinecraft().neverlose500_13.drawStringWithShadow("[" + mc.player.getName() + "]", (double)(93 - Minecraft.getMinecraft().neverlose500_13.getStringWidth("[" + mc.player.getName() + "]")), 200.0D, -1);
                    Minecraft.getMinecraft().neverlose500_13.drawStringWithShadow("server", 9.0D, 208.0D, -1);
                    Minecraft.getMinecraft().neverlose500_13.drawStringWithShadow("[" + server + "]", (double)(93 - Minecraft.getMinecraft().neverlose500_13.getStringWidth("[" + server + "]")), 208.0D, -1);
                    Minecraft.getMinecraft().neverlose500_13.drawStringWithShadow("time", 9.0D, 216.0D, -1);
                    Minecraft.getMinecraft().neverlose500_13.drawStringWithShadow("[" + time + "]", (double)(93 - Minecraft.getMinecraft().neverlose500_13.getStringWidth("[" + time + "]")), 216.0D, -1);
                    Minecraft.getMinecraft().neverlose500_13.drawStringWithShadow("latency", 9.0D, 224.0D, -1);
                    Minecraft.getMinecraft().neverlose500_13.drawStringWithShadow("[" + ping + "]", (double)(93 - Minecraft.getMinecraft().neverlose500_13.getStringWidth("[" + ping + "]")), 224.0D, -1);
               } else if (watermark.equalsIgnoreCase("Neverlose")) {
                    server = mc.isSingleplayer() ? "local" : mc.getCurrentServerData().serverIP.toLowerCase();
                    var10000 = (new StringBuilder()).append(name).append(" | ");
                    var10001 = mc;
                    time = var10000.append(Minecraft.getDebugFPS()).append(" fps | ").append(server).toString();
                    var10000 = (new StringBuilder()).append("INERTIAL FREE | ").append(name).append(" | ");
                    var10001 = mc;
                    ping = var10000.append(Minecraft.getDebugFPS()).append(" fps | ").append(server).toString();
                    DrawHelper.drawGlowRoundedRect(2.0F, 4.0F, (float)(Minecraft.getMinecraft().neverlose500_15.getStringWidth(ping) + 15), 20.0F, (new Color(10, 10, 10, 200)).getRGB(), 5.0F, 5.0F);
                    DrawHelper.drawSmoothRect(5.0F, 6.0F, (float)(Minecraft.getMinecraft().neverlose500_15.getStringWidth(ping) + 12), 18.0F, (new Color(10, 10, 10, 255)).getRGB());
                    Minecraft.getMinecraft().lato15.drawStringWithShadow("INERTIAL FREE", 7.5D, 10.0D, ClientHelper.getClientColor().getRGB());
                    Minecraft.getMinecraft().lato15.drawStringWithShadow("INERTIAL FREE", 8.0D, 10.5D, -1);
                    Minecraft.getMinecraft().neverlose500_15.drawStringWithShadow("| " + time, (double)(7 + Minecraft.getMinecraft().neverlose500_15.getStringWidth("INERTIAL FREE | ")), 10.5D, -1);
               }
          }

     }
}
