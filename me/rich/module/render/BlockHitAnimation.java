package me.rich.module.render;

import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventTransformSideFirstPerson;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.module.combat.KillAura;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHandSide;

public class BlockHitAnimation extends Feature {
      public static boolean blocking;

      public BlockHitAnimation() {
            super("BlockHitAnimation", 0, Category.MISC);
      }

      @EventTarget
      public void onUpdate(EventUpdate event) {
            blocking = Main.moduleManager.getModule(KillAura.class).isToggled() && KillAura.target != null;
      }

      @EventTarget
      public void onSidePerson(EventTransformSideFirstPerson event) {
            if (blocking && event.getEnumHandSide() == EnumHandSide.RIGHT) {
                  GlStateManager.rotate(110.0F, -5.0F, 5.0F, 10.0F);
            }

      }

      public void onEnable() {
            super.onEnable();
      }

      public void onDisable() {
            super.onDisable();
      }
}
