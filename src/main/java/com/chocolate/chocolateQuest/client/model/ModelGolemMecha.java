package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.client.model.ModelHuman;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGolemMecha extends ModelHuman {

   public ModelRenderer antennaStick;
   public ModelRenderer antenna;


   public ModelGolemMecha() {
      this(0.0F);
   }

   public ModelGolemMecha(float f) {
      super(f);
      super.bipedBody = new ModelRenderer(this, 16, 22);
      super.bipedBody.addBox(-3.0F, 6.0F, -2.0F, 6, 6, 4, f);
      super.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
      super.bipedHead = new ModelRenderer(this, 0, 0);
      super.bipedHead.addBox(-6.0F, -8.0F, -7.0F, 12, 14, 12, 1.0F + f);
      super.bipedHead.setRotationPoint(0.0F, 2.0F, 0.0F);
      super.bipedHeadwear = new ModelRenderer(this, 0, 0);
      super.bipedHeadwear.addBox(-3.0F, 6.0F, 1.0F, 6, 2, 2, f);
      super.bipedHeadwear.setRotationPoint(0.0F, 2.0F, 0.0F);
      super.bipedRightArm = new ModelRenderer(this, 48, 16);
      super.bipedRightArm.addBox(-6.0F, -2.0F, -2.0F, 4, 12, 4, f);
      super.bipedRightArm.setRotationPoint(-8.0F, 2.0F, 0.0F);
      super.bipedLeftArm = new ModelRenderer(this, 48, 16);
      super.bipedLeftArm.mirror = true;
      super.bipedLeftArm.addBox(2.0F, -2.0F, -2.0F, 4, 12, 4, f);
      super.bipedLeftArm.setRotationPoint(8.0F, 2.0F, 0.0F);
      super.bipedRightLeg = new ModelRenderer(this, 48, 0);
      super.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, f);
      super.bipedRightLeg.setRotationPoint(-3.0F, 12.0F + f, 0.0F);
      super.bipedLeftLeg = new ModelRenderer(this, 48, 0);
      super.bipedLeftLeg.mirror = true;
      super.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, f);
      super.bipedLeftLeg.setRotationPoint(3.0F, 12.0F + f, 0.0F);
      this.antenna = new ModelRenderer(this, 0, 4);
      this.antenna.addBox(-1.0F, -3.0F, 0.0F, 3, 3, 1, f);
      this.antenna.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.antennaStick = new ModelRenderer(this, 36, 0);
      this.antennaStick.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1, f);
      this.antennaStick.setRotationPoint(6.0F, -12.0F, 5.0F);
      this.antennaStick.addChild(this.antenna);
   }

   public void render(Entity z, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(z, f, f1, f2, f3, f4, f5);
      this.antennaStick.render(f5);
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      super.bipedHeadwear.rotateAngleX = -1.5707964F;
      super.bipedHeadwear.rotateAngleY = 0.0F;
      this.antenna.rotateAngleX = super.bipedHead.rotateAngleX;
      this.antenna.rotateAngleY = super.bipedHead.rotateAngleY;
      super.bipedHead.rotateAngleX = 0.0F;
      super.bipedHead.rotateAngleY = 0.0F;
   }

   public boolean useTinyArms() {
      return false;
   }
}
