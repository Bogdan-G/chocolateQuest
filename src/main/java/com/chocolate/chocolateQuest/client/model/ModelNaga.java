package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.client.model.ModelHuman;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelNaga extends ModelHuman {

   ModelRenderer[] mouth = new ModelRenderer[4];
   ModelRenderer[] tail;


   public ModelNaga() {
      for(int i = 0; i < this.mouth.length; ++i) {
         this.mouth[i] = new ModelRenderer(this, 24, 0);
         this.mouth[i].addBox(-2.0F + (float)i, -3.0F, -7.0F, 1, 1, 4);
         super.bipedHead.addChild(this.mouth[i]);
      }

      this.tail = new ModelRenderer[4];
      this.tail[0] = new ModelRenderer(this, 34, 0);
      this.tail[0].addBox(-6.0F, 0.0F, -2.0F, 8, 4, 4);
      this.tail[0].setRotationPoint(0.0F, -3.0F, 14.0F);
      this.tail[1] = new ModelRenderer(this, 34, 8);
      this.tail[1].addBox(-5.0F, 0.0F, -2.0F, 6, 4, 4);
      this.tail[1].setRotationPoint(0.0F, -3.0F, 14.0F);
      this.tail[2] = new ModelRenderer(this, 1, 17);
      this.tail[2].addBox(-4.0F, 0.0F, -2.0F, 4, 3, 3);
      this.tail[2].setRotationPoint(0.0F, -3.0F, 14.0F);
   }

   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
      this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
      super.bipedHead.render(par7);
      super.bipedBody.render(par7);
      this.renderArms(par7);

      for(int px = 0; px < this.mouth.length; ++px) {
         this.mouth[px].rotateAngleX = (float)Math.sin((double)(par4 / 10.0F + (float)(px * 20))) / 6.0F + 0.5F;
      }

      float var14 = 1.5F;
      float py = 10.0F;
      float pz = 2.0F;
      float dist = 3.0F;

      int i;
      for(i = 0; i < 5; ++i) {
         py += (float)i;
         pz -= (float)Math.sin((double)(i + 1));
         var14 += MathHelper.cos(par2 * 0.6662F) * 1.4F * par3;
         byte tailPos = 0;
         if(i >= 4) {
            tailPos = 2;
         }

         if(i > 2) {
            tailPos = 1;
         }

         this.tail[tailPos].rotationPointX = var14;
         this.tail[tailPos].rotationPointY = py;
         this.tail[tailPos].rotationPointZ = pz;
         this.tail[tailPos].render(par7);
      }

      for(i = 0; i < 3; ++i) {
         var14 += MathHelper.cos(par2 * 0.6662F) * 1.4F * par3;
         this.tail[2].rotationPointX = var14;
         this.tail[2].rotationPointY = py;
         this.tail[2].rotationPointZ = pz + (float)i * dist;
         this.tail[2].render(par7);
      }

   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
   }
}
