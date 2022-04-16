package wtf.rich.client.features.impl.visuals;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.text.TextFormatting;
import wtf.rich.Main;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventTransformSideFirstPerson;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.features.impl.combat.KillAura;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.ListSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class SwingAnimations extends Feature {
     public static boolean blocking;
     public static ListSetting swordAnim;
     public static BooleanSetting auraOnly;
     public static BooleanSetting item360;
     public static NumberSetting item360Speed;
     public static ListSetting item360Mode;
     public static ListSetting item360Hand;
     public static NumberSetting swingSpeed;
     public static NumberSetting spinSpeed;
     public static NumberSetting fapSmooth;

     public SwingAnimations() {
          super("SwingAnimations", "Добавляет анимацию на меч", 0, Category.VISUALS);
          swordAnim = new ListSetting("Blocking Animation Mode", "Astolfo", () -> {
               return true;
          }, new String[]{"Astolfo", "Self", "Spin", "Fap", "Big"});
          auraOnly = new BooleanSetting("Aura Only", false, () -> {
               return true;
          });
          swingSpeed = new NumberSetting("Swing Speed", "Скорость удара меча", 8.0F, 1.0F, 20.0F, 1.0F, () -> {
               return true;
          });
          spinSpeed = new NumberSetting("Spin Speed", 4.0F, 1.0F, 10.0F, 1.0F, () -> {
               return swordAnim.currentMode.equals("Astolfo") || swordAnim.currentMode.equals("Spin");
          });
          fapSmooth = new NumberSetting("Fap Smooth", 4.0F, 0.5F, 10.0F, 0.5F, () -> {
               return swordAnim.currentMode.equals("Fap");
          });
          item360 = new BooleanSetting("Item360", false, () -> {
               return true;
          });
          item360Mode = new ListSetting("Item360 Mode", "Horizontal", () -> {
               return item360.getBoolValue();
          }, new String[]{"Horizontal", "Vertical", "Zoom"});
          item360Hand = new ListSetting("Item360 Hand", "All", () -> {
               return item360.getBoolValue();
          }, new String[]{"All", "Left", "Right"});
          item360Speed = new NumberSetting("Item360 Speed", 8.0F, 1.0F, 15.0F, 1.0F, () -> {
               return item360.getBoolValue();
          });
          this.addSettings(new Setting[]{auraOnly, swordAnim, spinSpeed, fapSmooth, swingSpeed, item360, item360Mode, item360Hand, item360Speed});
     }

     @EventTarget
     public void onUpdate(EventUpdate event) {
          String anim = swordAnim.getOptions();
          blocking = Main.instance.featureDirector.getFeatureByClass(KillAura.class).isToggled() && KillAura.target != null;
          this.setModuleName("SwingAnimations " + TextFormatting.GRAY + anim);
     }

     @EventTarget
     public void onSidePerson(EventTransformSideFirstPerson event) {
          if (blocking && event.getEnumHandSide() == EnumHandSide.RIGHT) {
               GlStateManager.translate(0.29D, 0.1D, -0.31D);
          }

     }
}
