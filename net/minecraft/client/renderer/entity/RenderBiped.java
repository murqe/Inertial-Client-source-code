package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderBiped extends RenderLiving {
      private static final ResourceLocation DEFAULT_RES_LOC = new ResourceLocation("textures/entity/steve.png");

      public RenderBiped(RenderManager renderManagerIn, ModelBiped modelBipedIn, float shadowSize) {
            super(renderManagerIn, modelBipedIn, shadowSize);
            this.addLayer(new LayerCustomHead(modelBipedIn.bipedHead));
            this.addLayer(new LayerElytra(this));
            this.addLayer(new LayerHeldItem(this));
      }

      protected ResourceLocation getEntityTexture(EntityLiving entity) {
            return DEFAULT_RES_LOC;
      }

      public void transformHeldFull3DItemLayer() {
            GlStateManager.translate(0.0F, 0.1875F, 0.0F);
      }
}
