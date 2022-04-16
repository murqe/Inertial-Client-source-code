package wtf.rich.client.features.impl.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextFormatting;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.ListSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class NoSlowDown extends Feature {
     private boolean isNotJump;
     public static NumberSetting percentage;
     public static ListSetting noSlowMode;
     public static int usingTicks;

     public NoSlowDown() {
          super("NoSlowDown", "Убирает замедление при использовании еды и других предметов", 0, Category.MOVEMENT);
          percentage = new NumberSetting("Percentage", 100.0F, 0.0F, 100.0F, 1.0F, () -> {
               return true;
          });
          noSlowMode = new ListSetting("NoSlow Mode", "Default", () -> {
               return true;
          }, new String[]{"Default", "Matrix"});
          this.addSettings(new Setting[]{noSlowMode, percentage});
     }

     public void onDisable() {
          mc.timer.timerSpeed = 1.0F;
          super.onDisable();
     }

     @EventTarget
     public void onUpdate(EventUpdate event) {
          this.setModuleName("NoSlowDown " + TextFormatting.GRAY + noSlowMode.getCurrentMode());
          usingTicks = mc.player.isUsingItem() ? ++usingTicks : 0;
          if (this.isToggled() && mc.player.isUsingItem()) {
               if (noSlowMode.currentMode.equals("Matrix") && mc.player.isUsingItem()) {
                    EntityPlayerSP var10000;
                    if (mc.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) {
                         if (mc.player.ticksExisted % 2 == 0) {
                              var10000 = mc.player;
                              var10000.motionX *= 0.46D;
                              var10000 = mc.player;
                              var10000.motionZ *= 0.46D;
                         }
                    } else if ((double)mc.player.fallDistance > 0.2D) {
                         var10000 = mc.player;
                         var10000.motionX *= 0.9100000262260437D;
                         var10000 = mc.player;
                         var10000.motionZ *= 0.9100000262260437D;
                    }
               }

          }
     }
}
