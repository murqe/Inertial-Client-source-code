package me.rich.module.combat;

import de.Hero.settings.Setting;
import java.util.Iterator;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventPreMotionUpdate;
import me.rich.event.events.EventUpdate;
import me.rich.helpers.math.MathematicHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;

public class HitBox extends Feature {
      public static Setting expand;

      public HitBox() {
            super("HitBox", 0, Category.COMBAT);
      }

      public void setup() {
            expand = new Setting("Size", this, 0.18D, 0.1D, 1.0D, false);
            Main.settingsManager.rSetting(expand);
      }

      @EventTarget
      public void fsdgsd(EventUpdate event) {
            this.setModuleName("HitBox §7[" + Main.settingsManager.getSettingByName(Main.moduleManager.getModule(HitBox.class), "Size").getValFloat() + "]");
      }

      @EventTarget
      public void onUpdate(EventPreMotionUpdate event) {
            this.setSuffix("" + MathematicHelper.round(expand.getValFloat(), 2));
            Iterator var2 = mc.world.playerEntities.iterator();

            while(var2.hasNext()) {
                  Entity entity = (Entity)var2.next();
                  Minecraft var10001 = mc;
                  if (entity != Minecraft.player) {
                        float width = entity.width;
                        float height = entity.height;
                        float expandValue = expand.getValFloat();
                        entity.setEntityBoundingBox(new AxisAlignedBB(entity.posX - (double)width - (double)expandValue, entity.posY, entity.posZ + (double)width + (double)expandValue, entity.posX + (double)width + (double)expandValue, entity.posY + (double)height + (double)expandValue, entity.posZ - (double)width - (double)expandValue));
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
