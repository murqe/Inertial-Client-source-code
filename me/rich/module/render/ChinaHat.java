package me.rich.module.render;

import de.Hero.settings.Setting;
import java.util.ArrayList;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.Event3D;
import me.rich.helpers.DrawHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class ChinaHat extends Feature {
      public ChinaHat() {
            super("ChinaHat", 0, Category.RENDER);
            ArrayList Mode = new ArrayList();
            Mode.add("Tringle");
            Mode.add("Circle");
            Main.settingsManager.rSetting(new Setting("UnRenderFirstPerson", this, true));
            Main.settingsManager.rSetting(new Setting("Mode", this, "Tringle", Mode));
      }

      @EventTarget
      public void asf(Event3D event) {
            ItemStack stack = Minecraft.player.getEquipmentInSlot(4);
            double height = stack.getItem() instanceof ItemArmor ? (Minecraft.player.isSneaking() ? -0.1D : 0.12D) : (Minecraft.player.isSneaking() ? -0.22D : 0.0D);
            if ((mc.gameSettings.thirdPersonView == 1 || mc.gameSettings.thirdPersonView == 2) && Main.settingsManager.getSettingByName(Main.moduleManager.getModule(ChinaHat.class), "UnRenderFirstPerson").getValBoolean()) {
                  GL11.glPushMatrix();
                  GL11.glBlendFunc(770, 771);
                  GL11.glEnable(3042);
                  GL11.glDisable(3553);
                  GL11.glTranslatef(0.0F, (float)((double)(Minecraft.player.height + 0.0F) + height), 0.0F);
                  GL11.glDisable(2884);
                  GL11.glRotatef(-Minecraft.player.rotationYaw, 0.0F, 1.0F, 0.0F);
                  GL11.glBegin(6);
                  GL11.glVertex3d(0.0D, 0.3D, 0.0D);
                  double radius = 0.5D;
                  GlStateManager.resetColor();
                  String mode = Main.settingsManager.getSettingByName(Main.moduleManager.getModule(ChinaHat.class), "Mode").getValString();
                  if (mode.equalsIgnoreCase("Tringle")) {
                        for(int i = 0; i < 361; i += 12) {
                              DrawHelper.setColor(DrawHelper.setAlpha(DrawHelper.Rainbow1337(1.0F, (float)i, 0.5F, 16.0F), 195).getRGB());
                              GL11.glVertex3d(Math.cos((double)i * 3.141592653589793D / 180.0D) * radius, 0.0D, Math.sin((double)i * 3.141592653589793D / 180.0D) * radius);
                              GL11.glVertex3d(Math.cos(Math.toRadians((double)i)) * radius, -0.1D, Math.sin(Math.toRadians((double)i)) * radius);
                        }
                  } else if (mode.equalsIgnoreCase("Circle")) {
                        double radius1 = 0.6D;

                        for(int i = 0; i < 361; i += 3) {
                              DrawHelper.setColor(DrawHelper.setAlpha(DrawHelper.Rainbow1337(0.0F, (float)i, 0.5F, 6.0F), 180).getRGB());
                              GL11.glVertex3d(Math.cos((double)i * 3.141592653589793D / 180.0D) * radius1, 0.0D, Math.sin((double)i * 3.141592653589793D / 180.0D) * radius1);
                              GL11.glVertex3d(Math.cos(Math.toRadians((double)i)) * radius1, 0.0D, Math.sin(Math.toRadians((double)i)) * radius1);
                        }
                  }

                  GL11.glVertex3d(0.0D, 0.3D, 0.0D);
                  GL11.glEnd();
                  GL11.glEnable(2884);
                  GlStateManager.resetColor();
                  GL11.glEnable(3553);
                  GL11.glDisable(3042);
                  GL11.glPopMatrix();
            }

      }

      public void onEnable() {
            super.onEnable();
      }

      public void onDisable() {
            super.onDisable();
      }
}
