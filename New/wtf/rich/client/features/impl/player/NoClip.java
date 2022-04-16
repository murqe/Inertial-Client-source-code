package wtf.rich.client.features.impl.player;

import net.minecraft.entity.Entity;
import wtf.rich.Main;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.api.utils.movement.MovementHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class NoClip extends Feature {
     public static NumberSetting speed;
     public static BooleanSetting customSpeed;

     public NoClip() {
          super("NoClip", "Позволяет ходить сквозь стены", 0, Category.PLAYER);
          customSpeed = new BooleanSetting("Custom Speed", false, () -> {
               return true;
          });
          speed = new NumberSetting("Speed", 0.02F, 0.0F, 2.0F, 0.01F, () -> {
               return customSpeed.getBoolValue();
          });
          this.addSettings(new Setting[]{customSpeed, speed});
     }

     @EventTarget
     public void onUpdate(EventUpdate event) {
          mc.player.noClip = true;
          mc.player.motionY = 1.0E-5D;
          if (customSpeed.getBoolValue()) {
               MovementHelper.setSpeed(speed.getNumberValue() == 0.0F ? MovementHelper.getBaseMoveSpeed() : (double)speed.getNumberValue());
          }

          if (mc.gameSettings.keyBindJump.isKeyDown()) {
               mc.player.motionY = 0.4D;
          }

          if (mc.gameSettings.keyBindSneak.isKeyDown()) {
               mc.player.motionY = -0.4D;
          }

     }

     public static boolean isNoClip(Entity entity) {
          return !Main.instance.featureDirector.getFeatureByClass(NoClip.class).isToggled() || mc.player == null || mc.player.ridingEntity != null && entity != mc.player.ridingEntity ? entity.noClip : true;
     }

     public void onDisable() {
          mc.player.noClip = false;
          super.onDisable();
     }
}
