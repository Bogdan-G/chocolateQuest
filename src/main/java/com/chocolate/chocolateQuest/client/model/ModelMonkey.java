package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.client.model.ModelHuman;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelMonkey extends ModelHuman {

   ModelRenderer mouth = new ModelRenderer(this, 25, 0);
   ModelRenderer tail;


   public ModelMonkey() {
      this.mouth.addBox(-2.0F, -3.0F, -6.0F, 4, 3, 3);
      super.bipedHead.addChild(this.mouth);
      this.tail = new ModelRenderer(this, 0, 20);
      this.tail.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.tail.setRotationPoint(0.0F, -3.0F, 14.0F);
   }

   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
      super.render(par1Entity, par2, par3, par4, par5, par6, par7);
      float px = -0.5F;
      float py = 8.0F;
      float pz = 8.0F;
      float dist = 1.1F;
      if(!par1Entity.isSneaking()) {
         pz = 2.0F;
         py = 11.0F;
      }

      for(int i = 0; i < 8; ++i) {
         float f10 = (float)Math.cos((15.0D - (double)i / 15.0D + (double)(par2 / 10.0F)) * 3.141592653589793D * 2.0D);
         float AnimOnTime = (float)Math.cos(((double)i / 20.0D + (double)(par4 / 50.0F)) * 3.141592653589793D * 2.0D);
         this.tail.rotateAngleX = 3.56F + f10 / 10.0F;
         this.tail.rotateAngleY = AnimOnTime;
         this.tail.rotateAngleZ = 0.0F;
         this.tail.rotationPointX = px;
         this.tail.rotationPointY = py;
         this.tail.rotationPointZ = pz;
         px = (float)((double)px - Math.sin((double)this.tail.rotateAngleY) * Math.cos((double)this.tail.rotateAngleX) * (double)dist);
         py = (float)((double)py + Math.sin((double)this.tail.rotateAngleX) * 0.8D);
         pz = (float)((double)pz - Math.cos((double)this.tail.rotateAngleY) * Math.cos((double)this.tail.rotateAngleX) * (double)dist);
         this.tail.render(par7);
      }

   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
   }

   public void setAimingAngles(float time) {
      super.setAimingAngles(time);
   }

   public void setAimingAnglesLeft(float time) {
      super.setAimingAnglesLeft(time);
      super.bipedLeftArm.rotateAngleX += 0.7F;
   }

   public void setHumanRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, EntityHumanBase e) {
      super.setHumanRotationAngles(f, f1, f2, f3, f4, f5, e);
      if(e.isSneaking()) {
         if(e.isTwoHanded()) {
            super.bipedRightArm.rotateAngleX += 0.5F;
            super.bipedLeftArm.rotateAngleX += 0.5F;
         } else {
            super.bipedRightArm.rotateAngleX -= 0.7F;
            super.bipedLeftArm.rotateAngleX -= 0.7F;
         }
      }

   }

   public void setShiedRotation(float time) {
      float f7 = 0.0F;
      float f9 = 2.0F;
      super.bipedLeftArm.rotateAngleZ = -0.7F;
      super.bipedLeftArm.rotateAngleY = 1.2F;
      super.bipedLeftArm.rotateAngleX = -1.5707964F + super.bipedHead.rotateAngleX;
      super.bipedLeftArm.rotateAngleX -= f7 * 1.2F - f9 * 0.4F;
      super.bipedLeftArm.rotateAngleZ -= MathHelper.cos(time * 0.09F) * 0.05F + 0.05F;
      super.bipedLeftArm.rotateAngleX -= MathHelper.sin(time * 0.067F) * 0.05F;
   }
}
