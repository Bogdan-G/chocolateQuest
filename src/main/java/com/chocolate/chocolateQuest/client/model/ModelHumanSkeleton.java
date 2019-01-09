package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.client.model.ModelHuman;
import net.minecraft.client.model.ModelRenderer;

public class ModelHumanSkeleton extends ModelHuman {

   public ModelHumanSkeleton() {
      this(0.0F);
   }

   public ModelHumanSkeleton(float f) {
      this(f, 0.0F);
   }

   public ModelHumanSkeleton(float f, float f1) {
      super.heldItemLeft = 0;
      super.heldItemRight = 0;
      super.isSneak = false;
      super.aimedBow = false;
      super.bipedCloak = new ModelRenderer(this, 0, 0);
      super.bipedCloak.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, f);
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

   public boolean useTinyArms() {
      return false;
   }
}
