package me.rich.module.player;

import de.Hero.settings.Setting;
import java.util.ArrayList;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.helpers.world.TimerHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;

public class NoFall extends Feature {
      TimerHelper timer;

      public NoFall() {
            super("NoFall", 0, Category.PLAYER);
      }

      public void setup() {
            ArrayList options = new ArrayList();
            options.add("Vanilla");
            options.add("MatrixNew");
            options.add("OldMatrix");
            options.add("AAC");
            Main.settingsManager.rSetting(new Setting("NoFall Mode", this, "MatrixNew", options));
      }

      @EventTarget
      public void onUpdate(EventUpdate event) {
            String mode = Main.settingsManager.getSettingByName("NoFall Mode").getValString();
            (new StringBuilder(String.valueOf(Character.toUpperCase(mode.charAt(0))))).append(mode.substring(1)).toString();
            Minecraft var10000;
            if (mode.equalsIgnoreCase("Vanilla")) {
                  var10000 = mc;
                  if (Minecraft.player.fallDistance > 2.0F) {
                        var10000 = mc;
                        Minecraft.player.connection.sendPacket(new CPacketPlayer(true));
                  }
            }

            if (mode.equalsIgnoreCase("MatrixNew")) {
                  var10000 = mc;
                  if (Minecraft.player.fallDistance > 5.0F) {
                        var10000 = mc;
                        Minecraft.player.capabilities.isFlying = true;
                        mc.getConnection().sendPacket(new CPacketPlayer(true));
                        var10000 = mc;
                        Minecraft.player.isInWeb = true;
                  } else {
                        var10000 = mc;
                        Minecraft.player.capabilities.isFlying = false;
                  }
            }

            if (mode.equalsIgnoreCase("OldMatrix")) {
                  var10000 = mc;
                  if (Minecraft.player.fallDistance >= 2.2F) {
                        var10000 = mc;
                        Minecraft.player.motionX = 0.03999999910593033D;
                        var10000 = mc;
                        Minecraft.player.motionY = -55.0D;
                        var10000 = mc;
                        Minecraft.player.connection.sendPacket(new CPacketPlayer(true));
                        var10000 = mc;
                        Minecraft.player.connection.sendPacket(new CPacketPlayer(true));
                        var10000 = mc;
                        Minecraft.player.connection.sendPacket(new CPacketPlayer(true));
                  }
            }

            if (mode.equalsIgnoreCase("AAC")) {
                  var10000 = mc;
                  if (Minecraft.player.fallDistance >= 2.0F) {
                        var10000 = mc;
                        Minecraft.player.motionX = 0.01D;
                        var10000 = mc;
                        Minecraft.player.motionY = -0.0010101001323131D;
                        var10000 = mc;
                        Minecraft.player.motionX = 0.05D;
                        var10000 = mc;
                        Minecraft.player.motionY = -0.0010101001323131D;
                        var10000 = mc;
                        Minecraft.player.motionX = 0.0D;
                        var10000 = mc;
                        Minecraft.player.motionY = -0.0010101001323131D;
                        var10000 = mc;
                        Minecraft.player.connection.sendPacket(new CPacketPlayer(true));
                        var10000 = mc;
                        Minecraft.player.connection.sendPacket(new CPacketPlayer(false));
                        var10000 = mc;
                        Minecraft.player.connection.sendPacket(new CPacketPlayer(true));
                        var10000 = mc;
                        Minecraft.player.connection.sendPacket(new CPacketPlayer(false));
                        var10000 = mc;
                        Minecraft.player.connection.sendPacket(new CPacketPlayer(true));
                  }
            }

      }
}
