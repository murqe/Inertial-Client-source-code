package me.rich.module.player;

import de.Hero.settings.Setting;
import java.util.ArrayList;
import java.util.Random;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventPreMotionUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;

public class AntiAFK extends Feature {
      public void setup() {
            ArrayList options = new ArrayList();
            options.add("Sunrise");
            options.add("Jetmine");
            Main var10000 = Main.instance;
            Main.settingsManager.rSetting(new Setting("AntiAFK Mode", this, "Sunrise", options));
      }

      public AntiAFK() {
            super("AntiAFK", 0, Category.PLAYER);
      }

      @EventTarget
      public void onPreUpdate(EventPreMotionUpdate event) {
            String mode = Main.settingsManager.getSettingByName("AntiAFK Mode").getValString();
            Minecraft var10000;
            Random random;
            float yaw;
            Minecraft var10004;
            float pitch;
            if (mode.equalsIgnoreCase("Jetmine")) {
                  random = new Random();
                  yaw = random.nextFloat() * 90.0F;
                  pitch = random.nextFloat() * 90.0F;
                  var10000 = mc;
                  if (Minecraft.player.ticksExisted % 7 == 0) {
                        var10000 = mc;
                        var10004 = mc;
                        Minecraft.player.connection.sendPacket(new CPacketPlayer.Rotation(yaw, Minecraft.player.rotationPitch, false));
                        var10000 = mc;
                        Minecraft.player.rotationYawHead = yaw;
                        var10000 = mc;
                        Minecraft.player.renderYawOffset = yaw;
                  }
            }

            if (mode.equalsIgnoreCase("Sunrise")) {
                  var10000 = mc;
                  if (Minecraft.player.ticksExisted % 10 == 0) {
                        mc.gameSettings.keyBindForward.pressed = true;
                  } else {
                        mc.gameSettings.keyBindForward.pressed = false;
                  }

                  var10000 = mc;
                  if (Minecraft.player.ticksExisted % 15 == 0) {
                        mc.gameSettings.keyBindBack.pressed = true;
                  } else {
                        mc.gameSettings.keyBindBack.pressed = false;
                  }

                  var10000 = mc;
                  if (Minecraft.player.ticksExisted % 20 == 0) {
                        mc.gameSettings.keyBindLeft.pressed = true;
                  } else {
                        mc.gameSettings.keyBindLeft.pressed = false;
                  }

                  var10000 = mc;
                  if (Minecraft.player.ticksExisted % 25 == 0) {
                        mc.gameSettings.keyBindRight.pressed = true;
                  } else {
                        mc.gameSettings.keyBindRight.pressed = false;
                  }

                  random = new Random();
                  yaw = random.nextFloat() * 90.0F;
                  pitch = random.nextFloat() * 90.0F;
                  var10000 = mc;
                  if (Minecraft.player.ticksExisted % 15 == 0) {
                        var10000 = mc;
                        var10004 = mc;
                        Minecraft.player.connection.sendPacket(new CPacketPlayer.Rotation(yaw, Minecraft.player.rotationPitch, false));
                        var10000 = mc;
                        Minecraft.player.rotationYawHead = yaw;
                        var10000 = mc;
                        Minecraft.player.renderYawOffset = yaw;
                  }

                  var10000 = mc;
                  if (Minecraft.player.ticksExisted % 300 == 0) {
                        var10000 = mc;
                        Minecraft.player.sendChatMessage("kykareky!111");
                  }

                  var10000 = mc;
                  if (Minecraft.player.ticksExisted % 500 == 0) {
                        mc.clickMouse();
                  }
            }

      }
}
