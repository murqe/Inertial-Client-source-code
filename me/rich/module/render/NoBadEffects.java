package me.rich.module.render;

import me.rich.helpers.Wrapper;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.potion.Potion;

public class NoBadEffects extends Feature {
      public NoBadEffects() {
            super("NoBadEffects", 0, Category.RENDER);
      }

      public void onRender2D() {
            if (Wrapper.getPlayer().isPotionActive(Potion.getPotionById(25))) {
                  Wrapper.getPlayer().removeActivePotionEffect(Potion.getPotionById(25));
            }

            if (Wrapper.getPlayer().isPotionActive(Potion.getPotionById(2))) {
                  Wrapper.getPlayer().removeActivePotionEffect(Potion.getPotionById(2));
            }

            if (Wrapper.getPlayer().isPotionActive(Potion.getPotionById(4))) {
                  Wrapper.getPlayer().removeActivePotionEffect(Potion.getPotionById(4));
            }

            if (Wrapper.getPlayer().isPotionActive(Potion.getPotionById(9))) {
                  Wrapper.getPlayer().removeActivePotionEffect(Potion.getPotionById(9));
            }

            if (Wrapper.getPlayer().isPotionActive(Potion.getPotionById(15))) {
                  Wrapper.getPlayer().removeActivePotionEffect(Potion.getPotionById(15));
            }

            if (Wrapper.getPlayer().isPotionActive(Potion.getPotionById(17))) {
                  Wrapper.getPlayer().removeActivePotionEffect(Potion.getPotionById(17));
            }

            if (Wrapper.getPlayer().isPotionActive(Potion.getPotionById(18))) {
                  Wrapper.getPlayer().removeActivePotionEffect(Potion.getPotionById(18));
            }

            if (Wrapper.getPlayer().isPotionActive(Potion.getPotionById(27))) {
                  Wrapper.getPlayer().removeActivePotionEffect(Potion.getPotionById(27));
            }

            if (Wrapper.getPlayer().isPotionActive(Potion.getPotionById(20))) {
                  Wrapper.getPlayer().removeActivePotionEffect(Potion.getPotionById(20));
            }

      }

      public void onEnable() {
            super.onEnable();
      }

      public void onDisable() {
            super.onDisable();
      }
}
