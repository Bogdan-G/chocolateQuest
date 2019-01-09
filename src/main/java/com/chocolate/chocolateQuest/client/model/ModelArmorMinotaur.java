package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.client.model.ModelArmorSpider;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelArmorMinotaur extends ModelArmorSpider {

   ModelRenderer horn22;
   ModelRenderer horn21;
   ModelRenderer horn2;
   ModelRenderer horn12;
   ModelRenderer horn1;
   ModelRenderer horn11;
   ModelRenderer mouth;
   ModelRenderer hornLeftArm;
   ModelRenderer hornRightArm;


   public ModelArmorMinotaur() {
      this(0.0F, 0);
   }

   public ModelArmorMinotaur(float f, int type) {
      super(0.6F, type);
      float size = 0.0F;
      this.horn1 = new ModelRenderer(this, 0, 0);
      this.horn1.addBox(-4.0F, -9.0F, -4.0F, 2, 2, 2, size);
      this.horn1.setRotationPoint(0.0F, 0.0F, -2.0F);
      this.horn1.mirror = true;
      this.setRotation(this.horn1, -0.0569039F, 0.0F, 0.0F);
      this.horn11 = new ModelRenderer(this, 0, 0);
      this.horn11.addBox(-5.0F, -10.0F, -5.0F, 2, 2, 2, size);
      this.horn11.setRotationPoint(0.0F, 0.0F, -2.0F);
      this.horn11.setTextureSize(64, 32);
      this.horn11.mirror = true;
      this.setRotation(this.horn11, -0.0569039F, 0.0F, 0.0F);
      this.horn12 = new ModelRenderer(this, 0, 0);
      this.horn12.addBox(-5.0F, -10.0F, -8.0F, 1, 1, 3, size);
      this.horn12.setRotationPoint(0.0F, 0.0F, -2.0F);
      this.horn12.mirror = true;
      this.setRotation(this.horn12, -0.0569039F, 0.0F, 0.0F);
      this.horn22 = new ModelRenderer(this, 0, 0);
      this.horn22.addBox(4.0F, -10.0F, -8.0F, 1, 1, 3, size);
      this.horn22.setRotationPoint(0.0F, 0.0F, -2.0F);
      this.horn22.mirror = true;
      this.setRotation(this.horn22, -0.0569039F, 0.0F, 0.0F);
      this.horn21 = new ModelRenderer(this, 0, 0);
      this.horn21.addBox(3.0F, -10.0F, -5.0F, 2, 2, 2, size);
      this.horn21.setRotationPoint(0.0F, 0.0F, -2.0F);
      this.horn21.mirror = true;
      this.setRotation(this.horn21, -0.0569039F, 0.0F, 0.0F);
      this.horn2 = new ModelRenderer(this, 0, 0);
      this.horn2.addBox(2.0F, -9.0F, -4.0F, 2, 2, 2, size);
      this.horn2.setRotationPoint(0.0F, 0.0F, -2.0F);
      this.horn2.mirror = true;
      this.setRotation(this.horn2, -0.0569039F, 0.0F, 0.0F);
      super.bipedHead.addChild(this.horn1);
      super.bipedHead.addChild(this.horn11);
      super.bipedHead.addChild(this.horn12);
      super.bipedHead.addChild(this.horn2);
      super.bipedHead.addChild(this.horn21);
      super.bipedHead.addChild(this.horn22);
      this.mouth = new ModelRenderer(this, 25, 0);
      this.mouth.addBox(-2.0F, -3.0F, -6.0F, 4, 3, 3, 0.5F);
      super.bipedHead.addChild(this.mouth);
      this.hornLeftArm = this.getHorn();
      this.hornLeftArm.setRotationPoint(5.0F, -4.0F, 1.0F);
      this.hornLeftArm.rotateAngleX = -1.5707964F;
      this.hornLeftArm.rotateAngleY = 1.5707964F;
      super.bipedLeftArm.addChild(this.hornLeftArm);
      this.hornRightArm = this.getHorn();
      this.hornRightArm.setRotationPoint(-5.0F, -4.0F, -1.0F);
      this.hornRightArm.rotateAngleX = -1.5707964F;
      this.hornRightArm.rotateAngleY = -1.5707964F;
      super.bipedRightArm.addChild(this.hornRightArm);
   }

   public ModelRenderer getHorn() {
      float size = 0.3F;
      ModelRenderer horn1 = new ModelRenderer(this, 0, 0);
      horn1.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, size);
      ModelRenderer horn11 = new ModelRenderer(this, 0, 0);
      horn11.addBox(0.0F, -1.0F, -1.0F, 2, 2, 2, size - 0.1F);
      horn1.addChild(horn11);
      ModelRenderer horn12 = new ModelRenderer(this, 0, 0);
      horn12.addBox(0.5F, -1.0F, -4.0F, 1, 1, 3, size - 0.1F);
      horn1.addChild(horn12);
      return horn1;
   }

   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
      super.render(par1Entity, par2, par3, par4, par5, par6, par7);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
