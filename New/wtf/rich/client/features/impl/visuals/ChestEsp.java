package wtf.rich.client.features.impl.visuals;

import java.awt.Color;
import java.util.Iterator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.Event3D;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.ColorSetting;

public class ChestEsp extends Feature {
     public static BooleanSetting espOutline;
     public static ColorSetting chestColor;

     public ChestEsp() {
          super("ChestEsp", "Показывает сундуки через стены", 0, Category.VISUALS);
          chestColor = new ColorSetting("Chest Color", (new Color(16777215)).getRGB(), () -> {
               return true;
          });
          espOutline = new BooleanSetting("ESP Outline", false, () -> {
               return true;
          });
          this.addSettings(new Setting[]{espOutline, chestColor});
     }

     @EventTarget
     public void onRender3D(Event3D event) {
          if (mc.player != null || mc.world != null) {
               Iterator var2 = mc.world.loadedTileEntityList.iterator();

               while(var2.hasNext()) {
                    TileEntity entity = (TileEntity)var2.next();
                    BlockPos pos = entity.getPos();
                    if (entity instanceof TileEntityChest) {
                         DrawHelper.blockEsp(pos, new Color(chestColor.getColorValue()), espOutline.getBoolValue());
                    }
               }
          }

     }
}
