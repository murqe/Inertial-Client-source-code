package wtf.rich.client.features.impl.visuals;

import net.minecraft.init.MobEffects;
import net.minecraft.world.EnumSkyBlock;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventRenderWorldLight;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;

public class NoRender extends Feature {
     public static BooleanSetting hurtcam;
     public static BooleanSetting cameraClip;
     public static BooleanSetting antiTotem;
     public static BooleanSetting noFire;
     public static BooleanSetting noPotion;
     public static BooleanSetting noExp;
     public static BooleanSetting noPumpkin;
     public static BooleanSetting chatRect;
     public static BooleanSetting rain;
     public static BooleanSetting fog;
     public static BooleanSetting noBoss;
     public static BooleanSetting light;
     public static BooleanSetting blindness;
     public static BooleanSetting noArrow;
     public static BooleanSetting noArmor;
     public static BooleanSetting glintEffect;

     public NoRender() {
          super("NoRender", "Убирает опредленные элементы рендера в игре", 0, Category.VISUALS);
          rain = new BooleanSetting("Rain", true, () -> {
               return true;
          });
          hurtcam = new BooleanSetting("HurtCam", true, () -> {
               return true;
          });
          cameraClip = new BooleanSetting("Camera Clip", true, () -> {
               return true;
          });
          antiTotem = new BooleanSetting("AntiTotemAnimation", false, () -> {
               return true;
          });
          noFire = new BooleanSetting("NoFireOverlay", false, () -> {
               return true;
          });
          noPotion = new BooleanSetting("NoPotionDebug", false, () -> {
               return true;
          });
          noExp = new BooleanSetting("NoExpBar", false, () -> {
               return true;
          });
          fog = new BooleanSetting("Fog", false, () -> {
               return true;
          });
          noPumpkin = new BooleanSetting("NoPumpkinOverlay", false, () -> {
               return true;
          });
          chatRect = new BooleanSetting("Chat Rect", false, () -> {
               return true;
          });
          light = new BooleanSetting("Light", false, () -> {
               return true;
          });
          blindness = new BooleanSetting("Blindness", true, () -> {
               return true;
          });
          noBoss = new BooleanSetting("NoBossBar", false, () -> {
               return true;
          });
          glintEffect = new BooleanSetting("Glint Effect", false, () -> {
               return true;
          });
          noArrow = new BooleanSetting("NoArrowInPlayer", false, () -> {
               return true;
          });
          noArmor = new BooleanSetting("NoArmor", false, () -> {
               return true;
          });
          this.addSettings(new Setting[]{rain, fog, hurtcam, cameraClip, antiTotem, noArmor, noFire, chatRect, blindness, light, noPotion, noExp, noPumpkin, noBoss, glintEffect, noArrow});
     }

     @EventTarget
     public void onUpdate(EventUpdate event) {
          if (blindness.getBoolValue() && mc.player.isPotionActive(MobEffects.BLINDNESS) || mc.player.isPotionActive(MobEffects.NAUSEA)) {
               mc.player.removePotionEffect(MobEffects.NAUSEA);
               mc.player.removePotionEffect(MobEffects.BLINDNESS);
          }

     }

     @EventTarget
     public void onWorldLight(EventRenderWorldLight event) {
          if (this.isToggled()) {
               if (light.getBoolValue() && event.getEnumSkyBlock() == EnumSkyBlock.SKY) {
                    event.setCancelled(true);
               }

          }
     }
}
