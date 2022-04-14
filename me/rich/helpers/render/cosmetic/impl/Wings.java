package me.rich.helpers.render.cosmetic.impl;

import me.rich.helpers.render.cosmetic.Cosmetic;
import me.rich.module.render.Cosmetics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class Wings extends ModelBase {
      private static ModelRenderer mr1;
      private static ModelRenderer mr2;

      public Wings() {
            this.setTextureOffset("wing.bone", 0, 0);
            this.setTextureOffset("wing.skin", -10, 8);
            this.setTextureOffset("wingtip.bone", 0, 5);
            this.setTextureOffset("wingtip.skin", -10, 18);
            (mr1 = new ModelRenderer(this, "wing")).setTextureSize(30, 30);
            mr1.setRotationPoint(-2.0F, 0.0F, 0.0F);
            mr1.addBox("bone", -10.0F, -1.0F, -1.0F, 10, 2, 2);
            mr1.addBox("skin", -10.0F, 0.0F, 0.5F, 10, 0, 10);
            (mr2 = new ModelRenderer(this, "wingtip")).setTextureSize(30, 30);
            mr2.setRotationPoint(-10.0F, 0.0F, 0.0F);
            mr2.addBox("bone", -10.0F, -0.5F, -0.5F, 10, 1, 1);
            mr2.addBox("skin", -10.0F, 0.0F, 0.5F, 10, 0, 10);
            mr1.addChild(mr2);
      }

      public static void render(EntityPlayer player, float partialTicks) {
            double rotate = (double)interpolate(player.prevRenderYawOffset, player.renderYawOffset, partialTicks);
            GL11.glPushMatrix();
            GL11.glScaled(Cosmetics.scale, Cosmetics.scale, Cosmetics.scale);
            GL11.glRotated(Math.toRadians(rotate) - 4.0D, 0.0D, 1.0D, 0.0D);
            GL11.glTranslated(0.0D, 0.1D, 0.095D);
            if (player.isSneaking()) {
                  GL11.glTranslated(0.0D, 0.2D, 0.05D);
            }

            if (!((ItemStack)player.inventory.armorInventory.get(2)).func_190926_b()) {
                  GL11.glTranslated(0.0D, 0.0D, 0.05D);
            }

            ResourceLocation rl = Cosmetic.getWing(Cosmetics.wingpng);
            Minecraft.getMinecraft().getTextureManager().bindTexture(rl);

            for(int i = 0; i < 2; ++i) {
                  float f11 = (float)(System.currentTimeMillis() % 1000L) / 1000.0F * 3.1415927F * 2.0F;
                  mr1.rotateAngleX = (float)(Math.toRadians(-80.0D) - Math.cos((double)f11) * 0.20000000298023224D);
                  mr1.rotateAngleY = (float)(Math.toRadians(30.0D) + Math.sin((double)f11) * 0.4000000059604645D);
                  mr1.rotateAngleZ = (float)Math.toRadians(20.0D);
                  mr2.rotateAngleZ = (float)(-(Math.sin((double)(f11 + 2.0F)) + 0.5D) * 0.75D);
                  mr1.render(0.0625F);
                  GL11.glScalef(-1.0F, 1.0F, 1.0F);
            }

            GL11.glCullFace(1029);
            GL11.glPopMatrix();
      }

      private static float calRotateHeadNowX(float yaw1, float yaw2, float t, EntityPlayer player) {
            Minecraft.getMinecraft();
            if (!player.equals(Minecraft.player) && (0.0F > yaw1 && 0.0F < yaw2 || 0.0F < yaw1 && 0.0F > yaw2)) {
                  return yaw2;
            } else {
                  float f = (yaw1 + (yaw2 - yaw1) * t) % 360.0F;
                  return f;
            }
      }

      private static float calRotateBodyNowX(float yaw1, float yaw2, float t) {
            float f = (yaw1 + (yaw2 - yaw1) * t) % 360.0F;
            return f;
      }

      private static float calRotateHeadNowY(float yaw1, float yaw2, float t) {
            float f = (yaw1 + (yaw2 - yaw1) * t) % 180.0F;
            return f;
      }

      private static float interpolate(float yaw1, float yaw2, float percent) {
            float f = (yaw1 + (yaw2 - yaw1) * percent) % 360.0F;
            if (f < 0.0F) {
                  f += 360.0F;
            }

            return f;
      }
}
