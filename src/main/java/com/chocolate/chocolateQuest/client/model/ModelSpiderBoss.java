package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.entity.boss.AttackPunch;
import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import com.chocolate.chocolateQuest.entity.boss.EntitySpiderBoss;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelSpiderBoss extends ModelBase {

   public ModelRenderer spiderHead;
   public ModelRenderer spiderNeck;
   public ModelRenderer spiderBody;
   public ModelRenderer[] spiderLeg;
   public ModelRenderer[] spiderLegPart;
   public ModelRenderer spiderMouthLeft;
   public ModelRenderer spiderMouthRight;
   ModelRenderer rightarm1;
   ModelRenderer leftarm1;
   ModelRenderer bipedRightArm;
   ModelRenderer bipedLeftArm;
   ModelRenderer leftNeedle;
   ModelRenderer rightNeedle;


   public ModelSpiderBoss() {
      float f = 0.0F;
      byte i = 15;
      super.textureWidth = 64;
      super.textureHeight = 64;
      this.spiderHead = new ModelRenderer(this, 32, 4);
      this.spiderHead.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, f);
      this.spiderHead.setRotationPoint(0.0F, (float)(0 + i), -3.0F);
      this.spiderHead.setTextureSize(super.textureWidth, super.textureHeight);
      this.spiderNeck = new ModelRenderer(this, 0, 0);
      this.spiderNeck.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, f);
      this.spiderNeck.setRotationPoint(0.0F, (float)i, 0.0F);
      this.spiderNeck.setTextureSize(super.textureWidth, super.textureHeight);
      this.spiderBody = new ModelRenderer(this, 0, 12);
      this.spiderBody.addBox(-5.0F, -4.0F, -6.0F, 10, 8, 12, f);
      this.spiderBody.setRotationPoint(0.0F, (float)(0 + i), 9.0F);
      this.spiderBody.setTextureSize(super.textureWidth, super.textureHeight);
      this.spiderLeg = new ModelRenderer[8];
      this.spiderLeg[0] = new ModelRenderer(this, 18, 0);
      this.spiderLeg[0].addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, f);
      this.spiderLeg[0].setRotationPoint(-4.0F, (float)(0 + i), 2.0F);
      this.spiderLeg[1] = new ModelRenderer(this, 18, 0);
      this.spiderLeg[1].addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, f);
      this.spiderLeg[1].setRotationPoint(4.0F, (float)(0 + i), 2.0F);
      this.spiderLeg[2] = new ModelRenderer(this, 18, 0);
      this.spiderLeg[2].addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, f);
      this.spiderLeg[2].setRotationPoint(-4.0F, (float)(0 + i), 1.0F);
      this.spiderLeg[3] = new ModelRenderer(this, 18, 0);
      this.spiderLeg[3].addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, f);
      this.spiderLeg[3].setRotationPoint(4.0F, (float)(0 + i), 1.0F);
      this.spiderLeg[4] = new ModelRenderer(this, 18, 0);
      this.spiderLeg[4].addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, f);
      this.spiderLeg[4].setRotationPoint(-4.0F, (float)(0 + i), 0.0F);
      this.spiderLeg[5] = new ModelRenderer(this, 18, 0);
      this.spiderLeg[5].addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, f);
      this.spiderLeg[5].setRotationPoint(4.0F, (float)(0 + i), 0.0F);
      this.spiderLeg[6] = new ModelRenderer(this, 18, 0);
      this.spiderLeg[6].addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, f);
      this.spiderLeg[6].setRotationPoint(-4.0F, (float)(0 + i), -1.0F);
      this.spiderLeg[7] = new ModelRenderer(this, 18, 0);
      this.spiderLeg[7].addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, f);
      this.spiderLeg[7].setRotationPoint(4.0F, (float)(0 + i), -1.0F);

      int h;
      for(h = 0; h < this.spiderLeg.length; ++h) {
         this.spiderLeg[h].setTextureSize(super.textureWidth, super.textureHeight);
      }

      this.spiderLegPart = new ModelRenderer[8];

      for(h = 0; h < this.spiderLegPart.length; ++h) {
         this.spiderLegPart[h] = new ModelRenderer(this, 18, 32);
         this.spiderLegPart[h].addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, f);
         this.spiderLegPart[h].setRotationPoint(h % 2 == 0?-15.0F:15.0F, 0.0F, 0.0F);
         this.setRotation(this.spiderLegPart[h], 0.0F, 0.0F, 1.6F);
         this.spiderLegPart[h].setTextureSize(super.textureWidth, super.textureHeight);
      }

      this.spiderLeg[0].addChild(this.spiderLegPart[0]);
      this.spiderLeg[1].addChild(this.spiderLegPart[1]);
      this.spiderLeg[2].addChild(this.spiderLegPart[2]);
      this.spiderLeg[3].addChild(this.spiderLegPart[3]);
      this.spiderLeg[4].addChild(this.spiderLegPart[4]);
      this.spiderLeg[5].addChild(this.spiderLegPart[5]);
      this.spiderLeg[6].addChild(this.spiderLegPart[6]);
      this.spiderLeg[7].addChild(this.spiderLegPart[7]);
      this.spiderMouthLeft = new ModelRenderer(this, 46, 20);
      this.spiderMouthLeft.addBox(0.0F, -1.0F, -3.0F, 2, 2, 4, f);
      this.spiderMouthLeft.setRotationPoint(-3.0F, 3.0F, -8.0F);
      this.spiderMouthLeft.setTextureSize(super.textureWidth, super.textureHeight);
      this.spiderHead.addChild(this.spiderMouthLeft);
      this.spiderMouthRight = new ModelRenderer(this, 46, 20);
      this.spiderMouthRight.addBox(0.0F, -1.0F, -3.0F, 2, 2, 4, f);
      this.spiderMouthRight.setRotationPoint(1.0F, 3.0F, -8.0F);
      this.spiderMouthRight.setTextureSize(super.textureWidth, super.textureHeight);
      this.spiderHead.addChild(this.spiderMouthRight);
      this.bipedRightArm = new ModelRenderer(this, 0, 32);
      this.bipedRightArm.addBox(-1.0F, -1.0F, -4.0F, 2, 16, 2);
      this.bipedRightArm.setRotationPoint(-4.0F, 16.0F, -4.0F);
      this.bipedRightArm.setTextureSize(super.textureWidth, super.textureHeight);
      this.rightarm1 = new ModelRenderer(this, 8, 36);
      this.rightarm1.addBox(-0.5F, 0.0F, -0.5F, 3, 16, 3);
      this.rightarm1.setRotationPoint(0.0F, 16.0F, -2.0F);
      this.rightarm1.setTextureSize(super.textureWidth, super.textureHeight);
      this.bipedRightArm.addChild(this.rightarm1);
      this.bipedLeftArm = new ModelRenderer(this, 0, 32);
      this.bipedLeftArm.addBox(-1.0F, -1.0F, -4.0F, 2, 16, 2);
      this.bipedLeftArm.setRotationPoint(4.0F, 16.0F, -4.0F);
      this.bipedLeftArm.setTextureSize(super.textureWidth, super.textureHeight);
      this.bipedLeftArm.mirror = true;
      this.leftarm1 = new ModelRenderer(this, 8, 36);
      this.leftarm1.addBox(-0.5F, 0.0F, -0.5F, 3, 16, 3);
      this.leftarm1.setRotationPoint(-1.0F, 16.0F, -2.0F);
      this.leftarm1.setTextureSize(super.textureWidth, super.textureHeight);
      this.leftarm1.mirror = true;
      this.bipedLeftArm.addChild(this.leftarm1);
      this.leftNeedle = new ModelRenderer(this, 8, 32);
      this.leftNeedle.addBox(0.5F, 0.0F, 0.5F, 1, 3, 1);
      this.leftNeedle.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.leftNeedle.setTextureSize(super.textureWidth, super.textureHeight);
      this.leftarm1.addChild(this.leftNeedle);
      this.rightNeedle = new ModelRenderer(this, 8, 32);
      this.rightNeedle.addBox(0.5F, 0.0F, 0.5F, 1, 3, 1);
      this.rightNeedle.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.rightNeedle.setTextureSize(super.textureWidth, super.textureHeight);
      this.rightarm1.addChild(this.rightNeedle);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.textureHeight = 64;
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.leftarm1.setRotationPoint(-1.0F, 14.0F, -2.0F);
      this.spiderLeg[2].render(f5);
      this.spiderLeg[3].render(f5);
      this.spiderLeg[4].render(f5);
      this.spiderLeg[5].render(f5);
      this.spiderLeg[6].render(f5);
      this.spiderLeg[7].render(f5);
      this.spiderBody.render(f5);
      this.spiderHead.render(f5);
      this.spiderNeck.render(f5);
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      this.spiderHead.rotateAngleY = f3 / 57.295776F;
      this.spiderHead.rotateAngleX = f4 / 57.295776F;
      float f6 = 2.66F;
      this.spiderLeg[2].rotateAngleZ = -f6;
      this.spiderLeg[3].rotateAngleZ = f6;
      this.spiderLeg[4].rotateAngleZ = -f6;
      this.spiderLeg[5].rotateAngleZ = f6;
      this.spiderLeg[6].rotateAngleZ = -f6;
      this.spiderLeg[7].rotateAngleZ = f6;
      float f7 = 3.0F;
      float f8 = 0.3926991F;
      this.spiderLeg[2].rotateAngleY = f8 * 1.0F + f7;
      this.spiderLeg[3].rotateAngleY = -f8 * 1.0F - f7;
      this.spiderLeg[4].rotateAngleY = -f8 * 1.0F + f7;
      this.spiderLeg[5].rotateAngleY = f8 * 1.0F - f7;
      this.spiderLeg[6].rotateAngleY = -f8 * 2.0F + f7;
      this.spiderLeg[7].rotateAngleY = f8 * 2.0F - f7;
      float f10 = -(MathHelper.cos(f * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * f1;
      float f11 = -(MathHelper.cos(f * 0.6662F * 2.0F + 1.5707964F) * 0.4F) * f1;
      float f12 = -(MathHelper.cos(f * 0.6662F * 2.0F + 4.712389F) * 0.4F) * f1;
      float f13 = Math.abs(MathHelper.sin(f * 0.6662F + 0.0F) * 0.4F) * f1;
      float f14 = Math.abs(MathHelper.sin(f * 0.6662F + 3.1415927F) * 0.4F) * f1;
      float f15 = Math.abs(MathHelper.sin(f * 0.6662F + 1.5707964F) * 0.4F) * f1;
      float f16 = Math.abs(MathHelper.sin(f * 0.6662F + 4.712389F) * 0.4F) * f1;
      this.spiderLeg[2].rotateAngleY += f10;
      this.spiderLeg[3].rotateAngleY += -f10;
      this.spiderLeg[4].rotateAngleY += f11;
      this.spiderLeg[5].rotateAngleY += -f11;
      this.spiderLeg[6].rotateAngleY += f12;
      this.spiderLeg[7].rotateAngleY += -f12;
      this.spiderLeg[2].rotateAngleZ += f14;
      this.spiderLeg[3].rotateAngleZ += -f14;
      this.spiderLeg[4].rotateAngleZ += f15;
      this.spiderLeg[5].rotateAngleZ += -f15;
      this.spiderLeg[6].rotateAngleZ += f16;
      this.spiderLeg[7].rotateAngleZ += -f16;
      float animTime = ((EntityBaseBoss)entity).swingProgress;
      this.spiderMouthLeft.rotateAngleY = animTime;
      this.spiderMouthRight.rotateAngleY = -animTime;
      EntitySpiderBoss e = (EntitySpiderBoss)entity;
      if(e.rightHand != null) {
         this.bipedRightArm.rotateAngleX = 1.15F;
         this.bipedRightArm.rotateAngleZ = 3.141592F;
         this.bipedLeftArm.rotateAngleX = 1.15F;
         this.bipedLeftArm.rotateAngleZ = 3.141592F;
         float dist = (float)e.getDistance(e.posX + e.rightHand.posX, e.rightHand.posY - (double)(e.getScaleSize() / 2.0F) + e.posY + (double)e.rightHand.getShoulderHeight(), e.rightHand.posZ + e.posZ);
         float yDist = (float)(-((double)e.rightHand.getShoulderHeight() + e.rightHand.posY));
         float HA = (float)((double)dist / e.rightHand.getArmLength());
         float pitch = (float)((double)yDist / e.rightHand.getArmLength() * 3.14159D);
         this.bipedRightArm.rotateAngleX += 1.0F + pitch / 2.0F - MathHelper.cos(HA);
         this.rightarm1.rotateAngleX = 3.1416F - MathHelper.sin(HA) * 3.14159F + Math.max(pitch / 8.0F, 0.0F);
         float entityRot = e.rotationYaw * 3.141592F / 180.0F + 0.2F;
         float shoulderAngle = (float)this.getAngleBetweenEntities(e, e.rightHand, f5) - entityRot;
         this.bipedRightArm.rotateAngleY = -shoulderAngle;
         dist = (float)e.getDistance(e.posX + e.leftHand.posX, e.leftHand.posY - (double)(e.getScaleSize() / 2.0F) + e.posY + (double)e.leftHand.getShoulderHeight(), e.leftHand.posZ + e.posZ);
         yDist = (float)(-((double)e.leftHand.getShoulderHeight() + e.leftHand.posY));
         HA = (float)((double)dist / e.leftHand.getArmLength());
         pitch = (float)((double)yDist / e.leftHand.getArmLength() * 3.14159D);
         this.bipedLeftArm.rotateAngleX += 1.0F + pitch / 2.0F - MathHelper.cos(HA);
         this.leftarm1.rotateAngleX = 3.1416F - MathHelper.sin(HA) * 3.14159F + Math.max(pitch / 8.0F, 0.0F);
         entityRot = e.rotationYaw * 3.141592F / 180.0F;
         shoulderAngle = (float)this.getAngleBetweenEntities(e, e.leftHand, f5) - entityRot;
         this.bipedLeftArm.rotateAngleY = -shoulderAngle;
      }

      this.bipedLeftArm.render(f5);
      this.bipedRightArm.render(f5);
   }

   public double getAngleBetweenEntities(Entity entity, AttackPunch part, float tickTime) {
      double d = -part.posX;
      double d2 = -part.posZ;
      double angle = Math.atan2(d, d2);
      return -angle;
   }
}
