package wtf.rich.client.features.impl.combat;

import net.minecraft.util.text.TextFormatting;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class HitBox extends Feature {
     public static NumberSetting hitboxsize;

     public HitBox() {
          super("HitBox", "Увеличивает хитбокс у ентити", 0, Category.COMBAT);
          hitboxsize = new NumberSetting("Size", "Размер хитбокса", 0.2F, 0.1F, 1.0F, 0.1F, () -> {
               return true;
          });
          this.addSettings(new Setting[]{hitboxsize});
     }

     @EventTarget
     public void fsdgsd(EventUpdate event) {
          this.setModuleName("HitBox " + TextFormatting.GRAY + "[" + hitboxsize.getNumberValue() + "]");
     }
}
