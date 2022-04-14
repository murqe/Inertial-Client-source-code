package me.rich.module.movement;

import de.Hero.settings.Setting;
import java.util.ArrayList;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.helpers.movement.MovementHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import org.lwjgl.input.Keyboard;

public class NoSlowDown extends Feature {
      public void setup() {
            ArrayList options = new ArrayList();
            options.add("Vanilla");
            options.add("Matrix");
            options.add("Matrix2");
            Main.settingsManager.rSetting(new Setting("NoSlowdown Mode", this, "Vanilla", options));
            Main.settingsManager.rSetting(new Setting("CustomSpeed", this, 70.0D, 10.0D, 100.0D, true));
            Main.settingsManager.rSetting(new Setting("AutoJumping", this, false));
      }

      public NoSlowDown() {
            super("NoSlowdown", 0, Category.MOVEMENT);
      }

      @EventTarget
      public void onUpd(EventUpdate event) {
            double spd = Main.settingsManager.getSettingByName("CustomSpeed").getValDouble();
            String mode = Main.settingsManager.getSettingByName("NoSlowdown Mode").getValString();
            (new StringBuilder()).append(Character.toUpperCase(mode.charAt(0))).append(mode.substring(1)).toString();
            if (mode.equalsIgnoreCase("Matrix") && Minecraft.player.isEating() && MovementHelper.isMoving() && (double)Minecraft.player.fallDistance > 0.7D) {
                  EntityPlayerSP var10000 = Minecraft.player;
                  var10000.motionX *= 0.9700000286102295D;
                  var10000 = Minecraft.player;
                  var10000.motionZ *= 0.9700000286102295D;
            }

            if (mode.equalsIgnoreCase("Matrix2")) {
                  if (!Minecraft.player.isMoving()) {
                        return;
                  }

                  if (Minecraft.player.isEating() || Minecraft.player.isBlocking() || Minecraft.player.isBowing() || Minecraft.player.isDrinking()) {
                        if (Minecraft.player.ticksExisted % 2 == 0) {
                              mc.gameSettings.keyBindSneak.pressed = true;
                        } else {
                              mc.gameSettings.keyBindSneak.pressed = false;
                        }
                  }

                  if (!Minecraft.player.isEating() && !Minecraft.player.isBlocking() && !Minecraft.player.isBowing() && !Minecraft.player.isDrinking() && !Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
                        mc.gameSettings.keyBindSneak.pressed = false;
                  }
            }

            if (Main.settingsManager.getSettingByName("AutoJumping").getValBoolean() && (Minecraft.player.isEating() || Minecraft.player.isBlocking() || Minecraft.player.isBowing() || Minecraft.player.isDrinking())) {
                  if (!Minecraft.player.onGround) {
                        return;
                  }

                  Minecraft.player.jump();
            }

      }
}
