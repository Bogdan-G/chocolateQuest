package com.chocolate.chocolateQuest.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelSkeletonSummoned extends ModelBiped {

   public ModelSkeletonSummoned() {
      this(0.0F);
   }

   public ModelSkeletonSummoned(float f) {
      this(f, 0.0F);
   }

   public ModelSkeletonSummoned(float f, float f1) {
      super.heldItemLeft = 0;
      super.heldItemRight = 0;
      super.isSneak = false;
      super.aimedBow = false;
      super.bipedCloak = new ModelRenderer(this, 0, 0);
      super.bipedCloak.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, f);
      super.bipedEars = new ModelRenderer(this, 24, 0);
      super.bipedEars.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, f);
      super.bipedHead = new ModelRenderer(this, 0, 0);
      super.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
      super.bipedHead.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
      super.bipedHeadwear = new ModelRenderer(this, 32, 0);
      super.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, f + 0.5F);
      super.bipedHeadwear.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
      super.bipedBody = new ModelRenderer(this, 16, 16);
      super.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, f);
      super.bipedBody.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
      super.bipedRightArm = new ModelRenderer(this, 40, 16);
      super.bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, f);
      super.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
      super.bipedLeftArm = new ModelRenderer(this, 40, 16);
      super.bipedLeftArm.mirror = true;
      super.bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, f);
      super.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
      super.bipedRightLeg = new ModelRenderer(this, 0, 16);
      super.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, f);
      super.bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
      super.bipedLeftLeg = new ModelRenderer(this, 0, 16);
      super.bipedLeftLeg.mirror = true;
      super.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, f);
      super.bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
      super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
      float f6 = MathHelper.sin(super.onGround * 3.1415927F);
      float f7 = MathHelper.sin((1.0F - (1.0F - super.onGround) * (1.0F - super.onGround)) * 3.1415927F);
      super.bipedRightArm.rotateAngleZ = 0.0F;
      super.bipedLeftArm.rotateAngleZ = 0.0F;
      super.bipedRightArm.rotateAngleY = -(0.1F - f6 * 0.6F);
      super.bipedLeftArm.rotateAngleY = 0.1F - f6 * 0.6F;
      super.bipedRightArm.rotateAngleX = -1.5707964F;
      super.bipedLeftArm.rotateAngleX = -1.5707964F;
      super.bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
      super.bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
      super.bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
      super.bipedLeftArm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
      super.bipedRightArm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
      super.bipedLeftArm.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;
   }
}
