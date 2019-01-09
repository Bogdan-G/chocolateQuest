package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.entity.boss.AttackKick;
import com.chocolate.chocolateQuest.entity.boss.AttackPunch;
import com.chocolate.chocolateQuest.entity.boss.EntityGiantBoxer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class ModelGiantBoxer extends ModelBiped {

   ModelRenderer rightarm1;
   ModelRenderer leftarm1;
   ModelRenderer earR;
   ModelRenderer earL;
   ModelRenderer mouth;
   ModelRenderer tail;
   boolean attacking;
   int attackAnim;
   int maxAttackAnimTime;
   int attackType;


   public ModelGiantBoxer() {
      this(0.0F);
   }

   public ModelGiantBoxer(float s) {
      super.textureWidth = 64;
      super.textureHeight = 32;
      super.bipedHead = new ModelRenderer(this, 0, 0);
      super.bipedHead.addBox(-4.0F, -5.0F, -6.0F, 8, 8, 8, s);
      super.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
      super.bipedHead.setTextureSize(64, 32);
      super.bipedBody = new ModelRenderer(this, 16, 16);
      super.bipedBody.addBox(-4.0F, 3.0F, -5.0F, 8, 12, 4, s);
      super.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
      super.bipedBody.setTextureSize(64, 32);
      super.bipedRightLeg = new ModelRenderer(this, 0, 16);
      super.bipedRightLeg.addBox(-2.0F, 0.0F, -3.0F, 4, 12, 4, s);
      super.bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
      super.bipedRightLeg.setTextureSize(64, 32);
      super.bipedLeftLeg = new ModelRenderer(this, 0, 16);
      super.bipedLeftLeg.addBox(-2.0F, 0.0F, -3.0F, 4, 12, 4, s);
      super.bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
      super.bipedLeftLeg.setTextureSize(64, 32);
      super.bipedLeftLeg.mirror = true;
      super.bipedRightArm = new ModelRenderer(this, 40, 16);
      super.bipedRightArm.addBox(-3.0F, -1.0F, -4.0F, 4, 12, 4, s);
      super.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
      super.bipedRightArm.setTextureSize(64, 32);
      this.rightarm1 = new ModelRenderer(this, 40, 16);
      this.rightarm1.addBox(-2.0F, 1.0F, -4.0F, 4, 10, 4, s);
      this.rightarm1.setRotationPoint(-1.0F, 10.0F, 0.0F);
      this.rightarm1.setTextureSize(64, 32);
      super.bipedRightArm.addChild(this.rightarm1);
      super.bipedLeftArm = new ModelRenderer(this, 40, 16);
      super.bipedLeftArm.addBox(-1.0F, -1.0F, -4.0F, 4, 12, 4, s);
      super.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
      super.bipedLeftArm.setTextureSize(64, 32);
      super.bipedLeftArm.mirror = true;
      this.leftarm1 = new ModelRenderer(this, 40, 16);
      this.leftarm1.addBox(-2.0F, 1.0F, -4.0F, 4, 10, 4);
      this.leftarm1.setRotationPoint(1.0F, 10.0F, 0.0F);
      this.leftarm1.setTextureSize(64, 32);
      this.leftarm1.mirror = true;
      super.bipedLeftArm.addChild(this.leftarm1);
      this.earR = new ModelRenderer(this, 0, 0);
      this.earR.addBox(4.0F, -2.0F, -3.0F, 2, 2, 1);
      this.earR.mirror = true;
      this.earL = new ModelRenderer(this, 0, 0);
      this.earL.addBox(-6.0F, -2.0F, -3.0F, 2, 2, 1);
      this.mouth = new ModelRenderer(this, 25, 0);
      this.mouth.addBox(-2.0F, 0.0F, -8.0F, 4, 3, 3);
      super.bipedHead.addChild(this.earR);
      super.bipedHead.addChild(this.earL);
      super.bipedHead.addChild(this.mouth);
      this.tail = new ModelRenderer(this, 0, 20);
      this.tail.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.tail.setRotationPoint(0.0F, -3.0F, 14.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.isSneak = true;
      super.render(entity, f, f1, f2, f3, f4, f5);
      float px = -0.5F;
      float py = 13.0F;
      float pz = 5.5F;

      for(int i = 0; i < 15; ++i) {
         float f10 = (float)Math.cos((15.0D - (double)i / 15.0D + (double)(f / 10.0F)) * 3.141592653589793D * 2.0D);
         float AnimOnTime = (float)Math.cos(((double)i / 20.0D + (double)(f2 / 50.0F)) * 3.141592653589793D * 2.0D);
         this.tail.rotateAngleX = 3.86F + f10 / 10.0F;
         this.tail.rotateAngleY = AnimOnTime;
         this.tail.rotateAngleZ = 0.0F;
         this.tail.rotationPointX = px;
         this.tail.rotationPointY = py;
         this.tail.rotationPointZ = pz;
         px = (float)((double)px - Math.sin((double)this.tail.rotateAngleY) * Math.cos((double)this.tail.rotateAngleX) * 0.8D);
         py = (float)((double)py + Math.sin((double)this.tail.rotateAngleX) * 0.8D);
         pz = (float)((double)pz - Math.cos((double)this.tail.rotateAngleY) * Math.cos((double)this.tail.rotateAngleX) * 0.8D);
         this.tail.render(f5);
      }

   }

   public void setLivingAnimations(EntityLivingBase entityliving, float f, float f1, float f2) {
      super.setLivingAnimations(entityliving, f, f1, f2);
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      super.bipedRightLeg.rotationPointY = 13.0F;
      super.bipedLeftLeg.rotationPointY = 13.0F;
      EntityGiantBoxer e = (EntityGiantBoxer)entity;
      super.bipedRightArm.rotateAngleZ = 0.0F;
      super.bipedRightArm.rotateAngleX = 1.570796F;
      if(e.rightHand != null) {
         double kick = e.posX;
         double attackAnim = e.posY + (double)e.rightHand.getShoulderHeight();
         double dir = e.posZ;
         float dist = (float)e.getDistance(e.rightHand.posX + kick, e.rightHand.posY - (double)e.getScaleSize() + attackAnim, e.rightHand.posZ + dir) / 2.0F;
         float yDist = (float)(-((double)e.rightHand.getShoulderHeight() + e.rightHand.posY));
         float armLength = (float)(e.getArmLength() / 2.0D);
         float armAngle = 1.570796F;
         float AH = dist / armLength;
         float entityRot = e.rotationYaw * 3.141592F / 180.0F + 0.2F;
         if(AH <= 1.0F) {
            armAngle = (float)Math.asin((double)AH);
         }

         float shoulderAngle = (float)this.getAngleBetweenEntities(e, e.rightHand, f5) - entityRot;
         super.bipedRightArm.rotateAngleY = shoulderAngle + (float)(1.5707963267948966D - (double)armAngle);
         this.rightarm1.rotateAngleZ = 3.141592F - armAngle * 2.0F;
         super.bipedRightArm.rotateAngleX = -(yDist - yDist / e.getScaleSize()) / armLength;
         kick = e.posX;
         attackAnim = e.posY + (double)e.leftHand.getShoulderHeight();
         dir = e.posZ;
         dist = (float)e.getDistance(e.leftHand.posX + kick, e.leftHand.posY - (double)e.getScaleSize() + attackAnim, e.leftHand.posZ + dir) / 2.0F;
         AH = dist / armLength;
         entityRot = e.rotationYaw * 3.141592F / 180.0F - 0.2F;
         if(AH <= 1.0F) {
            armAngle = (float)Math.asin((double)AH);
         }

         yDist = (float)(-((double)e.leftHand.getShoulderHeight() + e.leftHand.posY));
         shoulderAngle = (float)this.getAngleBetweenEntities(e, e.leftHand, f5) - entityRot;
         super.bipedLeftArm.rotateAngleY = shoulderAngle + (float)(-1.5707963267948966D + (double)armAngle);
         this.leftarm1.rotateAngleZ = 3.141592F + armAngle * 2.0F;
         super.bipedLeftArm.rotateAngleX = -(yDist - yDist / e.getScaleSize()) / armLength;
      }

      AttackKick kick1 = e.kickHelper;
      if(kick1.kickTime > 0) {
         super.bipedRightLeg.rotateAngleX = 0.0F;
         super.bipedLeftLeg.rotateAngleX = 0.0F;
         int maxAttackAnimTime = kick1.kickSpeed;
         int attackAnim1 = maxAttackAnimTime - kick1.kickTime - maxAttackAnimTime / 10;
         float animProgress = ((float)(attackAnim1 + 1) + (float)attackAnim1 * f5) / (float)maxAttackAnimTime * 6.283184F;
         float dir1 = kick1.kickType != 2 && kick1.kickType != 4?1.0F:-1.0F;
         if(kick1.kickType != 2 && kick1.kickType != 1) {
            super.bipedRightLeg.rotateAngleX = dir1 * MathHelper.sin(animProgress) * 1.2F - dir1 * 0.2F;
            super.bipedRightLeg.rotateAngleY = dir1 * -MathHelper.cos(animProgress) * 0.4F;
         } else {
            super.bipedLeftLeg.rotateAngleX = dir1 * MathHelper.sin(animProgress) * 1.2F - dir1 * 0.2F;
            super.bipedLeftLeg.rotateAngleY = dir1 * MathHelper.cos(animProgress) * 0.4F;
         }
      }

      if(e.airSmashInProgress) {
         super.bipedBody.rotateAngleX -= 0.2F;
         super.bipedRightLeg.rotationPointY += 2.0F;
         super.bipedLeftLeg.rotationPointY += 2.0F;
         super.bipedRightLeg.rotateAngleX = -0.9424778F;
         super.bipedLeftLeg.rotateAngleX = -0.9424778F;
         super.bipedRightLeg.rotateAngleY = 0.31415927F;
         super.bipedLeftLeg.rotateAngleY = -0.31415927F;
      }

   }

   public double getAngleBetweenEntities(Entity entity, AttackPunch part, float tickTime) {
      double d = -part.posX;
      double d2 = -part.posZ;
      double angle = Math.atan2(d, d2);
      return -angle;
   }
}
