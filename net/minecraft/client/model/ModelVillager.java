package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelVillager extends ModelBase {
      public ModelRenderer villagerHead;
      public ModelRenderer villagerBody;
      public ModelRenderer villagerArms;
      public ModelRenderer rightVillagerLeg;
      public ModelRenderer leftVillagerLeg;
      public ModelRenderer villagerNose;

      public ModelVillager(float scale) {
            this(scale, 0.0F, 64, 64);
      }

      public ModelVillager(float scale, float p_i1164_2_, int width, int height) {
            this.villagerHead = (new ModelRenderer(this)).setTextureSize(width, height);
            this.villagerHead.setRotationPoint(0.0F, 0.0F + p_i1164_2_, 0.0F);
            this.villagerHead.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, scale);
            this.villagerNose = (new ModelRenderer(this)).setTextureSize(width, height);
            this.villagerNose.setRotationPoint(0.0F, p_i1164_2_ - 2.0F, 0.0F);
            this.villagerNose.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, scale);
            this.villagerHead.addChild(this.villagerNose);
            this.villagerBody = (new ModelRenderer(this)).setTextureSize(width, height);
            this.villagerBody.setRotationPoint(0.0F, 0.0F + p_i1164_2_, 0.0F);
            this.villagerBody.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, scale);
            this.villagerBody.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, scale + 0.5F);
            this.villagerArms = (new ModelRenderer(this)).setTextureSize(width, height);
            this.villagerArms.setRotationPoint(0.0F, 0.0F + p_i1164_2_ + 2.0F, 0.0F);
            this.villagerArms.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, scale);
            this.villagerArms.setTextureOffset(44, 22).addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, scale);
            this.villagerArms.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, scale);
            this.rightVillagerLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(width, height);
            this.rightVillagerLeg.setRotationPoint(-2.0F, 12.0F + p_i1164_2_, 0.0F);
            this.rightVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale);
            this.leftVillagerLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(width, height);
            this.leftVillagerLeg.mirror = true;
            this.leftVillagerLeg.setRotationPoint(2.0F, 12.0F + p_i1164_2_, 0.0F);
            this.leftVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale);
      }

      public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
            this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
            this.villagerHead.render(scale);
            this.villagerBody.render(scale);
            this.rightVillagerLeg.render(scale);
            this.leftVillagerLeg.render(scale);
            this.villagerArms.render(scale);
      }

      public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
            this.villagerHead.rotateAngleY = netHeadYaw * 0.017453292F;
            this.villagerHead.rotateAngleX = headPitch * 0.017453292F;
            this.villagerArms.rotationPointY = 3.0F;
            this.villagerArms.rotationPointZ = -1.0F;
            this.villagerArms.rotateAngleX = -0.75F;
            this.rightVillagerLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
            this.leftVillagerLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount * 0.5F;
            this.rightVillagerLeg.rotateAngleY = 0.0F;
            this.leftVillagerLeg.rotateAngleY = 0.0F;
      }
}
