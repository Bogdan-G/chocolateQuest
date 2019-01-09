package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.client.model.ModelHuman;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMinotaur extends ModelHuman {

   ModelRenderer horn22;
   ModelRenderer horn21;
   ModelRenderer horn2;
   ModelRenderer horn12;
   ModelRenderer horn1;
   ModelRenderer horn11;
   ModelRenderer mouth;


   public ModelMinotaur() {
      this(0.0F);
   }

   public ModelMinotaur(float f) {
      super(f);
      this.horn1 = new ModelRenderer(this, 0, 0);
      this.horn1.addBox(-4.0F, -8.0F, -4.0F, 2, 2, 2);
      this.horn1.setRotationPoint(0.0F, 0.0F, -2.0F);
      this.horn1.mirror = true;
      this.setRotation(this.horn1, -0.0569039F, 0.0F, 0.0F);
      this.horn11 = new ModelRenderer(this, 0, 0);
      this.horn11.addBox(-5.0F, -9.0F, -5.0F, 2, 2, 2);
      this.horn11.setRotationPoint(0.0F, 0.0F, -2.0F);
      this.horn11.setTextureSize(64, 32);
      this.horn11.mirror = true;
      this.setRotation(this.horn11, -0.0569039F, 0.0F, 0.0F);
      this.horn12 = new ModelRenderer(this, 0, 0);
      this.horn12.addBox(-5.0F, -9.0F, -8.0F, 1, 1, 3);
      this.horn12.setRotationPoint(0.0F, 0.0F, -2.0F);
      this.horn12.mirror = true;
      this.setRotation(this.horn12, -0.0569039F, 0.0F, 0.0F);
      this.horn22 = new ModelRenderer(this, 0, 0);
      this.horn22.addBox(4.0F, -9.0F, -8.0F, 1, 1, 3);
      this.horn22.setRotationPoint(0.0F, 0.0F, -2.0F);
      this.horn22.mirror = true;
      this.setRotation(this.horn22, -0.0569039F, 0.0F, 0.0F);
      this.horn21 = new ModelRenderer(this, 0, 0);
      this.horn21.addBox(3.0F, -9.0F, -5.0F, 2, 2, 2);
      this.horn21.setRotationPoint(0.0F, 0.0F, -2.0F);
      this.horn21.mirror = true;
      this.setRotation(this.horn21, -0.0569039F, 0.0F, 0.0F);
      this.horn2 = new ModelRenderer(this, 0, 0);
      this.horn2.addBox(2.0F, -8.0F, -4.0F, 2, 2, 2);
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
      this.mouth.addBox(-2.0F, -3.0F, -6.0F, 4, 3, 3);
      super.bipedHead.addChild(this.mouth);
   }

   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
      super.render(par1Entity, par2, par3, par4, par5, par6, par7);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
   }
}
