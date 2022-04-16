package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderSnowball extends Render {
     protected final Item item;
     private final RenderItem itemRenderer;

     public RenderSnowball(RenderManager renderManagerIn, Item itemIn, RenderItem itemRendererIn) {
          super(renderManagerIn);
          this.item = itemIn;
          this.itemRenderer = itemRendererIn;
     }

     public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
          GlStateManager.pushMatrix();
          GlStateManager.translate((float)x, (float)y, (float)z);
          GlStateManager.enableRescaleNormal();
          RenderManager var10000 = this.renderManager;
          GlStateManager.rotate(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
          float var10 = (float)(this.renderManager.options.thirdPersonView == 2 ? -1 : 1);
          RenderManager var10001 = this.renderManager;
          GlStateManager.rotate(var10 * RenderManager.playerViewX, 1.0F, 0.0F, 0.0F);
          GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
          this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
          if (this.renderOutlines) {
               GlStateManager.enableColorMaterial();
               GlStateManager.enableOutlineMode(this.getTeamColor(entity));
          }

          this.itemRenderer.renderItem(this.getStackToRender(entity), ItemCameraTransforms.TransformType.GROUND);
          if (this.renderOutlines) {
               GlStateManager.disableOutlineMode();
               GlStateManager.disableColorMaterial();
          }

          GlStateManager.disableRescaleNormal();
          GlStateManager.popMatrix();
          super.doRender(entity, x, y, z, entityYaw, partialTicks);
     }

     public ItemStack getStackToRender(Entity entityIn) {
          return new ItemStack(this.item);
     }

     protected ResourceLocation getEntityTexture(Entity entity) {
          return TextureMap.LOCATION_BLOCKS_TEXTURE;
     }
}
