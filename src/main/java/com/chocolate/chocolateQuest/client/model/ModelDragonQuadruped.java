package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.entity.boss.EntityWyvern;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelDragonQuadruped extends ModelBase {

   ModelRenderer rightWing;
   ModelRenderer rightWingPart;
   ModelRenderer rightWingArm;
   ModelRenderer rightWingArmPart;
   ModelRenderer leftWing;
   ModelRenderer leftWingPart;
   ModelRenderer leftWingArm;
   ModelRenderer leftWingArmPart;
   ModelRenderer body1;
   ModelRenderer body2;
   ModelRenderer body3;
   ModelRenderer[] portal;
   ModelRenderer neck;
   ModelRenderer tail;
   ModelRenderer tail2;
   ModelRenderer tailEnd;
   ModelRenderer head;
   ModelRenderer mouthUp;
   ModelRenderer mouthDown;
   ModelRenderer nose;
   ModelRenderer hornLeft;
   ModelRenderer hornRight;
   ModelRenderer[] leg;
   ModelRenderer[] leg1;
   ModelRenderer[] foot;


   public ModelDragonQuadruped() {
      short textureSizeX = 128;
      byte textureSizeY = 64;
      this.body1 = (new ModelRenderer(this, 0, 0)).setTextureSize(textureSizeX, textureSizeY);
      this.body1.addBox(-7.0F, -7.0F, -7.0F, 14, 14, 14);
      this.body1.setRotationPoint(0.0F, 0.0F, -10.0F);
      this.body1.rotateAngleX = 45.0F;
      this.body1.rotateAngleY = 0.0F;
      this.body1.rotateAngleZ = 0.0F;
      this.body2 = (new ModelRenderer(this, 25, 40)).setTextureSize(textureSizeX, textureSizeY);
      this.body2.addBox(-5.0F, -4.0F, -8.0F, 10, 8, 16);
      this.body2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.body2.rotateAngleX = 0.0F;
      this.body2.rotateAngleY = 0.0F;
      this.body2.rotateAngleZ = 0.0F;
      this.body3 = (new ModelRenderer(this, 0, 28)).setTextureSize(textureSizeX, textureSizeY);
      this.body3.addBox(-5.0F, -5.0F, -5.0F, 10, 10, 10);
      this.body3.setRotationPoint(0.0F, 0.0F, 10.0F);
      this.body3.rotateAngleX = 45.0F;
      this.body3.rotateAngleY = 0.0F;
      this.body3.rotateAngleZ = 0.0F;
      this.neck = (new ModelRenderer(this, 0, 51)).setTextureSize(textureSizeX, textureSizeY);
      this.neck.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6);
      this.neck.setRotationPoint(0.0F, -6.0F, -14.0F);
      this.neck.rotateAngleX = 0.0F;
      this.neck.rotateAngleY = 0.0F;
      this.neck.rotateAngleZ = 0.0F;
      this.tail = (new ModelRenderer(this, 40, 28)).setTextureSize(textureSizeX, textureSizeY);
      this.tail.addBox(-2.0F, -2.0F, -2.0F, 4, 4, 4);
      this.tail.setRotationPoint(0.0F, -3.0F, 14.0F);
      this.tail.rotateAngleX = 0.0F;
      this.tail.rotateAngleY = 0.0F;
      this.tail.rotateAngleZ = 0.0F;
      this.tail2 = (new ModelRenderer(this, 42, 30)).setTextureSize(textureSizeX, textureSizeY);
      this.tail2.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2);
      this.tail2.setRotationPoint(0.0F, -3.0F, 14.0F);
      this.tailEnd = (new ModelRenderer(this, 44, 0)).setTextureSize(textureSizeX, textureSizeY);
      this.tailEnd.addBox(0.0F, -2.0F, -2.0F, 0, 4, 4);
      this.leg = new ModelRenderer[4];
      this.leg1 = new ModelRenderer[4];
      this.foot = new ModelRenderer[4];

      for(int i = 0; i < 4; ++i) {
         this.leg[i] = (new ModelRenderer(this, 56, 0)).setTextureSize(textureSizeX, textureSizeY);
         this.leg[i].addBox(-1.0F, -4.0F, -2.0F, 4, 14, 4);
         this.leg1[i] = (new ModelRenderer(this, 56, 19)).setTextureSize(textureSizeX, textureSizeY);
         this.leg1[i].addBox(-1.5F, 1.0F, -1.5F, 3, 10, 3);
         this.leg[i].addChild(this.leg1[i]);
         this.leg1[i].setRotationPoint(1.0F, 7.0F, -1.0F);
         this.foot[i] = (new ModelRenderer(this, 52, 32)).setTextureSize(textureSizeX, textureSizeY);
         this.foot[i].addBox(-1.5F, -1.0F, -4.0F, 3, 2, 5);
         this.leg1[i].addChild(this.foot[i]);
         this.foot[i].setRotationPoint(0.0F, 11.0F, 0.0F);
      }

      this.leg[0].setRotationPoint(8.0F, 5.0F, -10.0F);
      this.leg[1].setRotationPoint(-10.0F, 5.0F, -10.0F);
      this.leg[2].setRotationPoint(6.0F, 5.0F, 10.0F);
      this.leg[3].setRotationPoint(-8.0F, 5.0F, 10.0F);
      this.head = (new ModelRenderer(this, 74, 0)).setTextureSize(textureSizeX, textureSizeY);
      this.head.addBox(-2.5F, -3.0F, -4.0F, 5, 5, 8);
      this.mouthUp = (new ModelRenderer(this, 106, 0)).setTextureSize(textureSizeX, textureSizeY);
      this.mouthUp.addBox(-2.0F, -2.0F, -11.0F, 4, 3, 7);
      this.mouthDown = (new ModelRenderer(this, 92, 0)).setTextureSize(textureSizeX, textureSizeY);
      this.mouthDown.addBox(-2.0F, 1.0F, -10.0F, 4, 1, 6);
      this.nose = (new ModelRenderer(this, 107, 11)).setTextureSize(textureSizeX, textureSizeY);
      this.nose.addBox(-2.0F, -3.0F, -11.0F, 4, 1, 1);
      this.hornLeft = (new ModelRenderer(this, 94, 8)).setTextureSize(textureSizeX, textureSizeY);
      this.hornLeft.addBox(1.5F, -2.0F, 3.0F, 1, 1, 8);
      this.hornLeft.rotateAngleX = 0.5F;
      this.hornLeft.rotateAngleY = 0.098F;
      this.hornRight = (new ModelRenderer(this, 94, 8)).setTextureSize(textureSizeX, textureSizeY);
      this.hornRight.addBox(-2.5F, -2.0F, 3.0F, 1, 1, 8);
      this.hornRight.rotateAngleX = 0.5F;
      this.hornRight.rotateAngleY = -0.298F;
      this.head.addChild(this.mouthUp);
      this.head.addChild(this.mouthDown);
      this.head.addChild(this.nose);
      this.head.addChild(this.hornLeft);
      this.head.addChild(this.hornRight);
      this.rightWing = (new ModelRenderer(this, 58, 43)).setTextureSize(textureSizeX, textureSizeY);
      this.rightWing.addBox(0.0F, 0.0F, 0.0F, 14, 0, 21);
      this.rightWing.setRotationPoint(6.0F, -4.0F, -10.0F);
      this.rightWingPart = (new ModelRenderer(this, 46, 21)).setTextureSize(textureSizeX, textureSizeY);
      this.rightWingPart.addBox(0.0F, 0.0F, 0.0F, 30, 0, 22);
      this.rightWingPart.setRotationPoint(14.0F, 0.0F, 0.0F);
      this.rightWingArm = (new ModelRenderer(this, 82, 17)).setTextureSize(textureSizeX, textureSizeY);
      this.rightWingArm.addBox(0.0F, -1.0F, 0.0F, 14, 2, 2);
      this.rightWingArmPart = (new ModelRenderer(this, 64, 18)).setTextureSize(textureSizeX, textureSizeY);
      this.rightWingArmPart.addBox(0.0F, -1.0F, 0.0F, 30, 1, 1);
      this.rightWing.addChild(this.rightWingPart);
      this.rightWing.addChild(this.rightWingArm);
      this.rightWingPart.addChild(this.rightWingArmPart);
      this.leftWing = (new ModelRenderer(this, 58, 43)).setTextureSize(textureSizeX, textureSizeY);
      this.leftWing.addBox(0.0F, 0.0F, 0.0F, 14, 0, 21);
      this.leftWing.setRotationPoint(-6.0F, -4.0F, -10.0F);
      this.leftWingPart = (new ModelRenderer(this, 46, 21)).setTextureSize(textureSizeX, textureSizeY);
      this.leftWingPart.addBox(0.0F, 0.0F, 0.0F, 30, 0, 22);
      this.leftWingPart.setRotationPoint(14.0F, 0.0F, 0.0F);
      this.leftWingArm = (new ModelRenderer(this, 82, 17)).setTextureSize(textureSizeX, textureSizeY);
      this.leftWingArm.addBox(0.0F, -1.0F, 0.0F, 14, 2, 2);
      this.leftWingArmPart = (new ModelRenderer(this, 64, 18)).setTextureSize(textureSizeX, textureSizeY);
      this.leftWingArmPart.addBox(0.0F, -1.0F, 0.0F, 30, 1, 1);
      this.leftWing.addChild(this.leftWingPart);
      this.leftWing.addChild(this.leftWingArm);
      this.leftWingPart.addChild(this.leftWingArmPart);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.rightWing.render(f5);
      this.leftWing.render(f5);
      this.body1.render(f5);
      this.body2.render(f5);
      this.body3.render(f5);

      for(int px = 0; px < 4; ++px) {
         this.leg[px].render(f5);
      }

      float var15 = 0.0F;
      float py = -6.0F;
      float pz = -14.0F;
      float rotX = MathHelper.cos(f / 1.9191077F) * 0.2617994F * f1;

      int i;
      float f10;
      for(i = 0; i < 4; ++i) {
         f10 = (float)Math.cos((double)((float)i * 0.45F)) * 0.15F;
         this.neck.rotateAngleX = (float)i / 4.0F - 0.4F + this.head.rotateAngleX;
         this.neck.rotateAngleY = this.head.rotateAngleY * ((float)i / 1.8F);
         this.neck.rotateAngleZ = this.head.rotateAngleY / 10.0F;
         this.neck.rotationPointX = var15;
         this.neck.rotationPointY = py;
         this.neck.rotationPointZ = pz;
         var15 = (float)((double)var15 - Math.sin((double)this.neck.rotateAngleY) * Math.cos((double)this.neck.rotateAngleX) * 6.0D);
         py = (float)((double)py + Math.sin((double)this.neck.rotateAngleX) * 6.0D);
         pz = (float)((double)pz - Math.cos((double)this.neck.rotateAngleY) * Math.cos((double)this.neck.rotateAngleX) * 6.0D);
         this.neck.render(f5);
      }

      this.head.rotationPointX = var15;
      this.head.rotationPointY = py - 0.4F;
      this.head.rotationPointZ = pz;
      this.head.rotateAngleY = this.neck.rotateAngleY;
      this.head.render(f5);
      var15 = 0.0F;
      py = -3.0F;
      pz = 14.0F;

      float AnimOnTime;
      for(i = 0; i < 10; ++i) {
         f10 = (float)Math.cos(((double)i / 10.0D + (double)(f / 10.0F)) * 3.141592653589793D * 2.0D) * 0.5F;
         AnimOnTime = (float)Math.cos(((double)i / 10.0D + (double)(f2 / 50.0F)) * 3.141592653589793D * 2.0D);
         this.tail.rotateAngleX = 3.16F + f10;
         this.tail.rotateAngleY = AnimOnTime;
         this.tail.rotateAngleZ = 0.0F;
         this.tail.rotationPointX = var15;
         this.tail.rotationPointY = py;
         this.tail.rotationPointZ = pz;
         var15 = (float)((double)var15 - Math.sin((double)this.tail.rotateAngleY) * Math.cos((double)this.tail.rotateAngleX) * 4.0D);
         py = (float)((double)py + Math.sin((double)this.tail.rotateAngleX) * 4.0D);
         pz = (float)((double)pz - Math.cos((double)this.tail.rotateAngleY) * Math.cos((double)this.tail.rotateAngleX) * 4.0D);
         this.tail.render(f5);
      }

      var15 += 2.0F;
      pz -= 2.0F;

      for(i = 0; i < 10; ++i) {
         f10 = (float)Math.cos(((double)i / 10.0D + (double)(f / 10.0F)) * 3.141592653589793D * 2.0D);
         AnimOnTime = (float)Math.cos(((double)i / 10.0D + (double)(f2 / 40.0F)) * 3.141592653589793D * 2.0D);
         this.tail2.rotateAngleX = 3.16F + f10;
         this.tail2.rotateAngleY = AnimOnTime;
         this.tail2.rotateAngleZ = 0.0F;
         this.tail2.rotationPointX = var15;
         this.tail2.rotationPointY = py;
         this.tail2.rotationPointZ = pz;
         var15 = (float)((double)var15 - Math.sin((double)this.tail2.rotateAngleY) * Math.cos((double)this.tail.rotateAngleX) * 2.0D);
         py = (float)((double)py + Math.sin((double)this.tail2.rotateAngleX) * 2.0D);
         pz = (float)((double)pz - Math.cos((double)this.tail2.rotateAngleY) * Math.cos((double)this.tail.rotateAngleX) * 2.0D);
         this.tail2.render(f5);
      }

      this.tailEnd.rotationPointX = var15;
      this.tailEnd.rotationPointY = py;
      this.tailEnd.rotationPointZ = pz;
      this.tailEnd.render(f5);
   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
      this.head.rotateAngleX = par5 / 57.295776F;
      this.head.rotateAngleY = par4 / 57.295776F;
      float rx = MathHelper.cos(par1 * 0.4662F) * 1.4F * par2;

      float e;
      for(int yOffsetSit = 0; yOffsetSit < 4; ++yOffsetSit) {
         e = yOffsetSit % 2 == 0?1.0F:-1.0F;
         this.leg[yOffsetSit].rotateAngleX = rx * e;
         this.leg1[yOffsetSit].rotateAngleX = -0.436F - rx * 2.0F * e;
         this.foot[yOffsetSit].rotateAngleX = 0.576F - rx * -0.3F * e;
      }

      float var12 = 14.0F;
      this.rightWing.rotateAngleZ = -1.3F;
      this.rightWingPart.rotateAngleY = -1.5F;
      this.rightWingPart.rotateAngleZ = 0.0F;
      if(!entity.onGround) {
         for(int var14 = 0; var14 < 4; ++var14) {
            this.leg[var14].rotateAngleX = -0.9F;
            this.leg1[var14].rotateAngleX = 2.4F;
         }

         e = MathHelper.cos((par3 + par1) * 0.1F);
         this.rightWing.rotateAngleZ = e;
         this.rightWingPart.rotateAngleZ = e / 2.0F;
         this.rightWingPart.rotateAngleY = -0.25F;
         this.leftWing.rotateAngleZ = -e;
         this.leftWingPart.rotateAngleZ = -e / 2.0F;
         this.leftWingPart.rotateAngleY = -0.25F;
      }

      if(super.onGround > 0.0F) {
         this.leg[1].rotateAngleX = MathHelper.sin(super.onGround * 3.1415927F) * -1.4F;
         this.leg[1].rotateAngleZ = MathHelper.sin(super.onGround * 3.1415927F) * -1.0F;
         this.leg[0].rotateAngleX = MathHelper.sin(super.onGround * 3.1415927F) * -1.4F;
         this.leg[0].rotateAngleZ = MathHelper.sin(super.onGround * 3.1415927F);
      }

      this.leftWing.rotateAngleZ = 3.1416F - this.rightWing.rotateAngleZ;
      this.leftWingPart.rotateAngleZ = -this.rightWingPart.rotateAngleZ;
      this.leftWingPart.rotateAngleY = this.rightWingPart.rotateAngleY;
      EntityWyvern var13 = (EntityWyvern)entity;
      this.mouthDown.rotateAngleX = 0.0F;
      this.mouthDown.rotateAngleY = 0.0F;
      if(var13.openMouthTime > 0) {
         float var10000 = (float)var13.openMouthTime + (float)(var13.openMouthTime - 1) * par6;
         var13.getClass();
         float animProgress = (float)((double)(var10000 / 10.0F) * 3.141592653589793D);
         this.mouthDown.rotateAngleX += MathHelper.sin(animProgress) * 0.6F;
      }

   }
}
