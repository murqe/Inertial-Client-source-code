package wtf.rich.client.features.impl.visuals;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHandSide;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventTransformSideFirstPerson;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class ViewModel extends Feature {
     public static NumberSetting rightx;
     public static NumberSetting righty;
     public static NumberSetting rightz;
     public static NumberSetting leftx;
     public static NumberSetting lefty;
     public static NumberSetting leftz;

     public ViewModel() {
          super("ViewModel", "Позволяет редактировать позицию предметов в руке", 0, Category.VISUALS);
          rightx = new NumberSetting("RightX", 0.0F, -2.0F, 2.0F, 0.1F, () -> {
               return true;
          });
          righty = new NumberSetting("RightY", 0.2F, -2.0F, 2.0F, 0.1F, () -> {
               return true;
          });
          rightz = new NumberSetting("RightZ", 0.2F, -2.0F, 2.0F, 0.1F, () -> {
               return true;
          });
          leftx = new NumberSetting("LeftX", 0.0F, -2.0F, 2.0F, 0.1F, () -> {
               return true;
          });
          lefty = new NumberSetting("LeftY", 0.2F, -2.0F, 2.0F, 0.1F, () -> {
               return true;
          });
          leftz = new NumberSetting("LeftZ", 0.2F, -2.0F, 2.0F, 0.1F, () -> {
               return true;
          });
          this.addSettings(new Setting[]{rightx, righty, rightz, leftx, lefty, leftz});
     }

     @EventTarget
     public void onSidePerson(EventTransformSideFirstPerson event) {
          if (event.getEnumHandSide() == EnumHandSide.RIGHT) {
               GlStateManager.translate(rightx.getNumberValue(), righty.getNumberValue(), rightz.getNumberValue());
          }

          if (event.getEnumHandSide() == EnumHandSide.LEFT) {
               GlStateManager.translate(-leftx.getNumberValue(), lefty.getNumberValue(), leftz.getNumberValue());
          }

     }
}
