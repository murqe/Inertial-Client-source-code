package wtf.rich.client.features.impl.player;

import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class ItemScroller extends Feature {
     public static NumberSetting scrollerDelay;

     public ItemScroller() {
          super("ItemScroller", "Позволяет быстро лутать сундуки при нажатии на шифт и ЛКМ", 0, Category.PLAYER);
          scrollerDelay = new NumberSetting("Scroller Delay", 0.0F, 0.0F, 1000.0F, 50.0F, () -> {
               return true;
          });
          this.addSettings(new Setting[]{scrollerDelay});
     }
}
