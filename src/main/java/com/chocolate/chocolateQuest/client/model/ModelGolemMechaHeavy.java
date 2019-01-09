package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.client.model.ModelGolemMecha;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGolemMechaHeavy extends ModelGolemMecha {

   public ModelGolemMechaHeavy() {
      this(0.0F);
   }

   public ModelGolemMechaHeavy(float f) {
      super(f);
      super.textureHeight = 64;
      super.textureWidth = 64;
      super.bipedHead = new ModelRenderer(this, 0, 0);
      super.bipedHead.addBox(-9.0F, -7.0F, -7.0F, 18, 17, 14, f);
      super.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
      super.bipedBody = new ModelRenderer(this, 0, 38);
      super.bipedBody.addBox(-4.0F, 10.01F, -2.0F, 8, 6, 4, f);
      super.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
      super.bipedHeadwear = new ModelRenderer(this, 0, 0);
      super.bipedHeadwear.addBox(-3.0F, 5.0F, 7.0F, 6, 2, 2, f);
      super.bipedHeadwear.setRotationPoint(0.0F, 2.0F, 0.0F);
      super.bipedRightArm = new ModelRenderer(this, 24, 42);
      super.bipedRightArm.addBox(-6.0F, -2.0F, -2.0F, 4, 12, 4, f);
      super.bipedRightArm.setRotationPoint(-8.0F, 2.0F, 0.0F);
      super.bipedLeftArm = new ModelRenderer(this, 24, 42);
      super.bipedLeftArm.addBox(2.0F, -2.0F, -2.0F, 4, 12, 4, f);
      super.bipedLeftArm.setRotationPoint(8.0F, 2.0F, 0.0F);
      super.bipedLeftArm.mirror = true;
      super.bipedRightLeg = new ModelRenderer(this, 0, 50);
      super.bipedRightLeg.addBox(-4.0F, 2.0F, -2.0F, 4, 10, 4, f);
      super.bipedRightLeg.setRotationPoint(-4.0F, 12.0F + f, 0.0F);
      super.bipedRightLeg.setTextureSize(64, 64);
      super.bipedLeftLeg = new ModelRenderer(this, 0, 50);
      super.bipedLeftLeg.mirror = true;
      super.bipedLeftLeg.addBox(0.0F, 2.0F, -2.0F, 4, 10, 4, f);
      super.bipedLeftLeg.setRotationPoint(4.0F, 12.0F + f, 0.0F);
      super.bipedLeftLeg.setTextureSize(64, 64);
      super.antenna = new ModelRenderer(this, 0, 4);
      super.antenna.addBox(-1.0F, -3.0F, 0.0F, 3, 3, 1, f);
      super.antenna.setRotationPoint(0.0F, 0.0F, 0.0F);
      super.antennaStick = new ModelRenderer(this, 36, 0);
      super.antennaStick.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, f);
      super.antennaStick.setRotationPoint(8.0F, -8.0F, 6.0F);
      super.antennaStick.setTextureSize(64, 64);
      super.antennaStick.addChild(super.antenna);
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      super.bipedRightArm.setRotationPoint(-7.0F, 5.0F, 0.0F);
      super.bipedLeftArm.setRotationPoint(7.0F, 5.0F, 0.0F);
   }
}
