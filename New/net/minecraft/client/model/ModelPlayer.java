package net.minecraft.client.model;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHandSide;
import wtf.rich.Main;
import wtf.rich.client.features.impl.visuals.CustomModel;

public class ModelPlayer extends ModelBiped {
     private final ModelRenderer RightLeg;
     private final ModelRenderer LeftLeg;
     private final ModelRenderer Body;
     private final ModelRenderer RightArm;
     private final ModelRenderer Head;
     private final ModelRenderer LeftArm;
     public ModelRenderer bipedLeftArmwear;
     public ModelRenderer bipedRightArmwear;
     public ModelRenderer bipedLeftLegwear;
     public ModelRenderer bipedRightLegwear;
     public ModelRenderer bipedBodyWear;
     public final boolean smallArms;
     private final ModelRenderer bipedCape;
     private final ModelRenderer bipedDeadmau5Head;

     public ModelPlayer(float modelSize, boolean smallArms) {
          super(modelSize, 0.0F, 64, 64);
          this.smallArms = smallArms;
          this.textureWidth = 64;
          this.textureHeight = 64;
          this.bipedDeadmau5Head = new ModelRenderer(this, 24, 0);
          this.bipedDeadmau5Head.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, modelSize);
          this.bipedCape = new ModelRenderer(this, 0, 0);
          this.bipedCape.setTextureSize(64, 32);
          this.bipedCape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, modelSize);
          if (smallArms) {
               this.bipedLeftArm = new ModelRenderer(this, 32, 48);
               this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, modelSize);
               this.bipedLeftArm.setRotationPoint(5.0F, 2.5F, 0.0F);
               this.bipedRightArm = new ModelRenderer(this, 40, 16);
               this.bipedRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, modelSize);
               this.bipedRightArm.setRotationPoint(-5.0F, 2.5F, 0.0F);
               this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
               this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, modelSize + 0.25F);
               this.bipedLeftArmwear.setRotationPoint(5.0F, 2.5F, 0.0F);
               this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
               this.bipedRightArmwear.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, modelSize + 0.25F);
               this.bipedRightArmwear.setRotationPoint(-5.0F, 2.5F, 10.0F);
          } else {
               this.bipedLeftArm = new ModelRenderer(this, 32, 48);
               this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
               this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
               this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
               this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
               this.bipedLeftArmwear.setRotationPoint(5.0F, 2.0F, 0.0F);
               this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
               this.bipedRightArmwear.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
               this.bipedRightArmwear.setRotationPoint(-5.0F, 2.0F, 10.0F);
          }

          this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
          this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
          this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
          this.bipedLeftLegwear = new ModelRenderer(this, 0, 48);
          this.bipedLeftLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
          this.bipedLeftLegwear.setRotationPoint(1.9F, 12.0F, 0.0F);
          this.bipedRightLegwear = new ModelRenderer(this, 0, 32);
          this.bipedRightLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
          this.bipedRightLegwear.setRotationPoint(-1.9F, 12.0F, 0.0F);
          this.bipedBodyWear = new ModelRenderer(this, 16, 32);
          this.bipedBodyWear.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, modelSize + 0.25F);
          this.bipedBodyWear.setRotationPoint(0.0F, 0.0F, 0.0F);
          this.RightLeg = new ModelRenderer(this);
          this.RightLeg.setRotationPoint(-2.0F, 14.0F, 0.0F);
          this.RightLeg.cubeList.add(new ModelBox(this.RightLeg, 0, 36, -2.0F, 0.0F, -2.0F, 4, 10, 4, 0.0F, false));
          this.LeftLeg = new ModelRenderer(this);
          this.LeftLeg.setRotationPoint(2.0F, 14.0F, 0.0F);
          this.LeftLeg.cubeList.add(new ModelBox(this.LeftLeg, 24, 24, -2.0F, 0.0F, -2.0F, 4, 10, 4, 0.0F, false));
          this.Body = new ModelRenderer(this);
          this.Body.setRotationPoint(0.0F, 24.0F, 0.0F);
          this.setRotationAngle(this.Body, 0.2618F, 0.0F, 0.0F);
          this.Body.cubeList.add(new ModelBox(this.Body, 0, 18, -4.0F, -23.1486F, 0.5266F, 8, 14, 4, 0.0F, false));
          this.RightArm = new ModelRenderer(this);
          this.RightArm.setRotationPoint(0.0F, 24.0F, 0.0F);
          this.setRotationAngle(this.RightArm, -1.309F, 0.0F, 0.0F);
          this.RightArm.cubeList.add(new ModelBox(this.RightArm, 36, 0, -7.0F, -4.5F, -23.25F, 3, 12, 3, 0.0F, false));
          this.RightArm.cubeList.add(new ModelBox(this.RightArm, 16, 36, -6.0F, 5.75F, -25.25F, 1, 2, 5, 0.0F, false));
          this.RightArm.cubeList.add(new ModelBox(this.RightArm, 31, 15, -6.0F, 5.75F, -30.25F, 1, 2, 5, 0.0F, false));
          this.RightArm.cubeList.add(new ModelBox(this.RightArm, 0, 0, -6.0F, 8.75F, -28.25F, 1, 1, 3, 0.0F, false));
          this.RightArm.cubeList.add(new ModelBox(this.RightArm, 24, 18, -6.0F, 7.75F, -29.25F, 1, 1, 5, 0.0F, false));
          this.Head = new ModelRenderer(this);
          this.Head.setRotationPoint(0.0F, 1.0F, -3.0F);
          this.Head.cubeList.add(new ModelBox(this.Head, 0, 0, -5.0F, -9.75F, -5.0F, 10, 10, 8, 0.0F, false));
          this.LeftArm = new ModelRenderer(this);
          this.LeftArm.setRotationPoint(4.0F, 3.0F, -3.0F);
          this.LeftArm.cubeList.add(new ModelBox(this.LeftArm, 37, 37, 0.0F, -1.75F, -1.5F, 3, 12, 3, 0.0F, false));
     }

     public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
          boolean flag = Main.instance.featureDirector.getFeatureByClass(CustomModel.class).isToggled();
          if (entityIn instanceof EntityPlayerSP) {
               if (flag) {
                    this.RightLeg.render(scale);
                    this.LeftLeg.render(scale);
                    this.Body.render(scale);
                    this.RightArm.render(scale);
                    this.Head.render(scale);
                    this.LeftArm.render(scale);
                    this.Head.rotateAngleX = this.bipedHead.rotateAngleX;
                    this.Head.rotateAngleY = this.bipedHead.rotateAngleY;
                    this.Head.rotateAngleZ = this.bipedHead.rotateAngleZ;
                    this.LeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX;
                    this.LeftArm.rotateAngleY = this.bipedLeftArm.rotateAngleY;
                    this.LeftArm.rotateAngleZ = this.bipedLeftArm.rotateAngleZ;
                    this.RightLeg.rotateAngleX = this.bipedRightLeg.rotateAngleX;
                    this.RightLeg.rotateAngleY = this.bipedRightLeg.rotateAngleY;
                    this.RightLeg.rotateAngleZ = this.bipedRightLeg.rotateAngleZ;
                    this.LeftLeg.rotateAngleX = this.bipedLeftLeg.rotateAngleX;
                    this.LeftLeg.rotateAngleY = this.bipedLeftLeg.rotateAngleY;
                    this.LeftLeg.rotateAngleZ = this.bipedLeftLeg.rotateAngleZ;
               } else {
                    super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
               }
          } else {
               super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
               super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
               GlStateManager.pushMatrix();
               if (this.isChild) {
                    float f = 2.0F;
                    GlStateManager.scale(0.5F, 0.5F, 0.5F);
                    GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
                    this.bipedLeftLegwear.render(scale);
                    this.bipedRightLegwear.render(scale);
                    this.bipedLeftArmwear.render(scale);
                    this.bipedRightArmwear.render(scale);
                    this.bipedBodyWear.render(scale);
               } else {
                    if (entityIn.isSneaking()) {
                         GlStateManager.translate(0.0F, 0.2F, 0.0F);
                    }

                    this.bipedLeftLegwear.render(scale);
                    this.bipedRightLegwear.render(scale);
                    this.bipedLeftArmwear.render(scale);
                    this.bipedRightArmwear.render(scale);
                    this.bipedBodyWear.render(scale);
               }

               GlStateManager.popMatrix();
          }

     }

     public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
          super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
          copyModelAngles(this.bipedLeftLeg, this.bipedLeftLegwear);
          copyModelAngles(this.bipedRightLeg, this.bipedRightLegwear);
          copyModelAngles(this.bipedLeftArm, this.bipedLeftArmwear);
          copyModelAngles(this.bipedRightArm, this.bipedRightArmwear);
          copyModelAngles(this.bipedBody, this.bipedBodyWear);
     }

     public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
          modelRenderer.rotateAngleX = x;
          modelRenderer.rotateAngleY = y;
          modelRenderer.rotateAngleZ = z;
     }

     public void renderDeadmau5Head(float scale) {
          copyModelAngles(this.bipedHead, this.bipedDeadmau5Head);
          this.bipedDeadmau5Head.rotationPointX = 0.0F;
          this.bipedDeadmau5Head.rotationPointY = 0.0F;
          this.bipedDeadmau5Head.render(scale);
     }

     public void setInvisible(boolean invisible) {
          super.setInvisible(invisible);
          this.bipedLeftArmwear.showModel = invisible;
          this.bipedRightArmwear.showModel = invisible;
          this.bipedLeftLegwear.showModel = invisible;
          this.bipedRightLegwear.showModel = invisible;
          this.bipedBodyWear.showModel = invisible;
          this.bipedCape.showModel = invisible;
          this.bipedDeadmau5Head.showModel = invisible;
     }

     public void renderCape(float scale) {
          this.bipedCape.render(scale);
     }

     public void postRenderArm(float scale, EnumHandSide side) {
          ModelRenderer modelrenderer = this.getArmForSide(side);
          if (this.smallArms) {
               float f = 0.5F * (float)(side == EnumHandSide.RIGHT ? 1 : -1);
               modelrenderer.rotationPointX += f;
               modelrenderer.postRender(scale);
               modelrenderer.rotationPointX -= f;
          } else {
               modelrenderer.postRender(scale);
          }

     }
}
