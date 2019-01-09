package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.client.model.ModelHuman;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGremlin extends ModelHuman {

   public ModelRenderer wingl;
   public ModelRenderer wingr;
   public ModelRenderer tail;


   public ModelGremlin() {
      this(0.0F);
   }

   public ModelGremlin(float f) {
      super(f);
      super.bipedRightLeg = new ModelRenderer(this, 0, 22);
      super.bipedRightLeg.addBox(0.0F, 0.0F, -2.0F, 4, 6, 4, f);
      super.bipedRightLeg.setRotationPoint(0.0F, 0.0F, -2.0F);
      super.bipedLeftLeg = new ModelRenderer(this, 0, 22);
      super.bipedLeftLeg.mirror = true;
      super.bipedLeftLeg.addBox(-8.0F, 0.0F, -2.0F, 4, 6, 4, f);
      super.bipedLeftLeg.setRotationPoint(2.9F, 12.0F, 6.0F);
      super.bipedHead = new ModelRenderer(this, 0, 0);
      super.bipedHead.addBox(-4.0F, -5.0F, -7.0F, 8, 8, 8, f);
      super.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
      super.bipedHeadwear = new ModelRenderer(this, 32, 0);
      super.bipedHeadwear.addBox(-4.0F, -5.0F, -7.0F, 8, 8, 8, f + 0.5F);
      super.bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.wingl = new ModelRenderer(this, 0, 21);
      this.wingl.addBox(0.0F, -2.0F, 0.0F, 0, 5, 12, f);
      this.wingl.setRotationPoint(-2.0F, 0.0F, 3.0F);
      this.wingr = new ModelRenderer(this, 0, 21);
      this.wingr.addBox(0.0F, -2.0F, 0.0F, 0, 5, 12, f);
      this.wingr.setRotationPoint(2.0F, 0.0F, 3.0F);
      this.tail = new ModelRenderer(this, 1, 17);
      this.tail.addBox(-4.0F, 0.0F, -2.0F, 4, 3, 3);
      this.tail.setRotationPoint(0.0F, -3.0F, 14.0F);
   }

   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
      super.render(par1Entity, par2, par3, par4, par5, par6, par7);
      float rot = (float)(Math.sin((double)(System.nanoTime() / 4L)) * 0.3D);
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      EntityHumanBase e = (EntityHumanBase)entity;
      if(e.isSitting()) {
         ++super.bipedRightArm.rotateAngleX;
         ++super.bipedLeftArm.rotateAngleX;
      }

      if(e.isTwoHanded()) {
         super.bipedRightArm.rotateAngleX += 0.5F;
         super.bipedLeftArm.rotateAngleX += 0.5F;
      } else if(!e.isAiming()) {
         super.bipedRightArm.rotateAngleX -= 0.7F;
         super.bipedLeftArm.rotateAngleX -= 0.7F;
      }

      if(e.isDefending()) {
         this.setShiedRotation(f2);
      }

      super.bipedRightLeg.setRotationPoint(2.0F, 7.0F, 8.0F);
      super.bipedLeftLeg.setRotationPoint(2.0F, 7.0F, 8.0F);
      super.bipedBody.rotateAngleX = 0.7F;
   }

   public boolean useTinyArms() {
      return false;
   }
}
