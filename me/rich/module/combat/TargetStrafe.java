package me.rich.module.combat;

import de.Hero.settings.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventMove;
import me.rich.event.events.EventPreMotionUpdate;
import me.rich.event.events.EventUpdate;
import me.rich.event.events.MovementUtil;
import me.rich.helpers.combat.RotationHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;

public class TargetStrafe extends Feature {
      public static Minecraft mc = Minecraft.getMinecraft();
      private static int direction = -1;
      private final Setting range;

      public TargetStrafe() {
            super("TargetStrafe", 0, Category.COMBAT);
            Main.settingsManager.rSetting(this.range = new Setting("Radius", this, 3.0D, 1.0D, 6.0D, false));
            Main.settingsManager.rSetting(new Setting("Speed", this, 0.23D, 0.1D, 4.0D, false));
            Main.settingsManager.rSetting(new Setting("AutoJump", this, true));
            Main.settingsManager.rSetting(new Setting("KeepDistance", this, true));
      }

      public boolean onMotionUpdate(EventMove e) {
            EntityLivingBase entity = KillAura.target;
            float[] rotations = RotationHelper.getRotations(entity);
            double spd = (double)Main.settingsManager.getSettingByName("Speed").getValFloat();
            if ((double)Minecraft.player.getDistanceToEntity(entity) <= this.range.getValDouble()) {
                  MovementUtil.setMotion(e, Main.settingsManager.getSettingByName(this, "Speed").getValDouble(), rotations[0], (double)direction, 0.0D);
            } else {
                  MovementUtil.setMotion(e, Main.settingsManager.getSettingByName(this, "Speed").getValDouble(), rotations[0], (double)direction, 1.0D);
            }

            return true;
      }

      @EventTarget
      public void onUpdate(EventPreMotionUpdate event) {
            EntityLivingBase entity = KillAura.target;
            if (Minecraft.player.isCollidedHorizontally) {
                  this.switchDirection();
            }

            if (mc.gameSettings.keyBindLeft.isPressed()) {
                  direction = 1;
            }

            if (mc.gameSettings.keyBindRight.isPressed()) {
                  direction = -1;
            }

            if (KillAura.target.getHealth() > 0.0F && Main.settingsManager.getSettingByName("AutoJump").getValBoolean() && Main.moduleManager.getModule(KillAura.class).isToggled() && Main.moduleManager.getModule(TargetStrafe.class).isToggled() && Minecraft.player.onGround) {
                  Minecraft.player.jump();
            }

      }

      @EventTarget
      public void onSwitchDir(EventUpdate event) {
            if (Minecraft.player.isCollidedHorizontally) {
                  this.switchDirection();
            }

            if (mc.gameSettings.keyBindLeft.isKeyDown()) {
                  direction = 1;
            }

            if (mc.gameSettings.keyBindRight.isKeyDown()) {
                  direction = -1;
            }

      }

      private void switchDirection() {
            direction = direction == 1 ? -1 : 1;
      }

      @EventTarget
      public void onMove(EventMove e) {
            if (Minecraft.player.ticksExisted <= 1) {
                  this.toggle();
                  NotificationPublisher.queue(this.getName(), "toggled off", NotificationType.INFO);
            } else {
                  if (Main.moduleManager.getModule(KillAura.class).isToggled() && KillAura.target != null) {
                        if (Minecraft.player.isCollidedHorizontally) {
                              this.switchDirection();
                        }

                        if (KillAura.target.getHealth() > 0.0F) {
                              this.onMotionUpdate(e);
                        }
                  }

            }
      }

      public void onEnable() {
            super.onEnable();
      }

      public void onDisable() {
            super.onDisable();
      }
}
