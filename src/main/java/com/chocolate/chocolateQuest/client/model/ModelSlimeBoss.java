package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.entity.boss.AttackKick;
import com.chocolate.chocolateQuest.entity.boss.EntitySlimeBoss;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelSlimeBoss extends ModelQuadruped {

   ModelRenderer foot2;
   ModelRenderer foot1;
   ModelRenderer headBT;
   ModelRenderer core;
   ModelRenderer eyer;
   ModelRenderer eyel;
   ModelSlimeBoss goomy;
   boolean isMain;


   public ModelSlimeBoss() {
      this(0.0F, 0, true);
   }

   public ModelSlimeBoss(float s, int to, boolean isMain) {
      super(12, s);
      this.isMain = isMain;
      super.textureWidth = 64;
      super.textureHeight = 64;
      super.body = new ModelRenderer(this, 0, to);
      super.body.addBox(-5.0F, -6.0F, -2.0F, 10, 6, 12, s);
      super.body.setRotationPoint(0.0F, 19.0F, -4.0F);
      super.body.setTextureSize(super.textureWidth, super.textureHeight);
      super.leg1 = new ModelRenderer(this, 0, to);
      super.leg1.addBox(0.0F, -1.0F, -1.0F, 3, 9, 3, s);
      super.leg1.setRotationPoint(5.0F, -3.0F, 8.0F);
      super.leg1.setTextureSize(super.textureWidth, super.textureHeight);
      this.foot1 = new ModelRenderer(this, 56, to);
      this.foot1.addBox(-1.0F, -10.0F, -2.0F, 2, 10, 2, s);
      this.foot1.setRotationPoint(1.0F, 8.0F, 0.0F);
      this.foot1.setTextureSize(super.textureWidth, super.textureHeight);
      super.body.addChild(super.leg1);
      super.leg1.addChild(this.foot1);
      this.setRotation(super.leg1, -1.047198F, 0.0F, 0.0F);
      this.setRotation(this.foot1, -1.047198F, 0.0F, 0.0F);
      super.leg2 = new ModelRenderer(this, 0, to);
      super.leg2.addBox(-0.0F, -1.0F, -1.0F, 3, 9, 3, s);
      super.leg2.setRotationPoint(-8.0F, -3.0F, 8.0F);
      super.leg2.setTextureSize(super.textureWidth, super.textureHeight);
      this.foot2 = new ModelRenderer(this, 56, to);
      this.foot2.addBox(-1.0F, -10.0F, -1.0F, 2, 10, 2, s);
      this.foot2.setRotationPoint(1.0F, 8.0F, 0.0F);
      this.foot2.setTextureSize(super.textureWidth, super.textureHeight);
      super.body.addChild(super.leg2);
      super.leg2.addChild(this.foot2);
      this.setRotation(super.leg2, -1.047198F, 0.0F, 0.0F);
      this.setRotation(this.foot2, -1.047198F, 0.0F, 0.0F);
      super.leg4 = new ModelRenderer(this, 56, to + 12);
      super.leg4.addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2, s);
      super.leg4.setRotationPoint(4.0F, 0.0F, -1.0F);
      super.leg4.setTextureSize(super.textureWidth, super.textureHeight);
      super.leg3 = new ModelRenderer(this, 56, to + 12);
      super.leg3.addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2, s);
      super.leg3.setRotationPoint(-4.0F, 0.0F, -1.0F);
      super.leg3.setTextureSize(super.textureWidth, super.textureHeight);
      super.body.addChild(super.leg4);
      super.body.addChild(super.leg3);
      super.head = new ModelRenderer(this, 0, to + 18);
      super.head.addBox(-4.0F, -4.0F, -6.0F, 8, 6, 8, s);
      super.head.setRotationPoint(0.0F, -6.0F, -1.0F);
      super.head.setTextureSize(super.textureWidth, super.textureHeight);
      super.body.addChild(super.head);
      this.headBT = new ModelRenderer(this, 32, to + 20);
      this.headBT.addBox(-4.0F, 2.0F, -10.0F, 8, 4, 8, s);
      this.headBT.setRotationPoint(0.0F, -2.0F, 4.0F);
      this.headBT.setTextureSize(super.textureWidth, super.textureHeight);
      super.head.addChild(this.headBT);
      if(isMain) {
         this.core = new ModelRenderer(this, 32, 0);
         this.core.addBox(-3.0F, -1.0F, -2.0F, 6, 6, 6);
         this.core.setRotationPoint(0.0F, -1.5F, -3.0F);
         this.core.setTextureSize(super.textureWidth, super.textureHeight);
         this.setRotation(this.core, 0.4F, 0.0F, 0.0F);
         super.head.addChild(this.core);
         this.eyer = new ModelRenderer(this, 0, 18);
         this.eyer.addBox(0.0F, -1.0F, -1.0F, 2, 2, 2);
         this.eyer.setRotationPoint(3.0F, -1.0F, -1.0F);
         this.eyer.setTextureSize(super.textureWidth, super.textureHeight);
         this.eyel = new ModelRenderer(this, 0, 18);
         this.eyel.addBox(-2.0F, -1.0F, -1.0F, 2, 2, 2);
         this.eyel.setRotationPoint(-3.0F, -1.0F, -1.0F);
         this.eyel.setTextureSize(super.textureWidth, super.textureHeight);
         super.head.addChild(this.eyer);
         super.head.addChild(this.eyel);
         this.goomy = new ModelSlimeBoss(1.2F, 32, false);
      }

   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      super.body.render(f5);
      this.goomy.copyRotations(this);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      this.goomy.body.render(f5);
      GL11.glDisable(3042);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }

   public void copyRotations(ModelSlimeBoss model) {
      this.setRotation(super.body, model.body.rotateAngleX, model.body.rotateAngleY, model.body.rotateAngleZ);
      this.setRotation(super.leg1, model.leg1.rotateAngleX, model.leg1.rotateAngleY, model.leg1.rotateAngleZ);
      this.setRotation(super.leg2, model.leg2.rotateAngleX, model.leg2.rotateAngleY, model.leg2.rotateAngleZ);
      this.setRotation(super.leg3, model.leg3.rotateAngleX, model.leg3.rotateAngleY, model.leg3.rotateAngleZ);
      this.setRotation(super.leg4, model.leg4.rotateAngleX, model.leg4.rotateAngleY, model.leg4.rotateAngleZ);
      this.setRotation(this.foot1, model.foot1.rotateAngleX, model.foot1.rotateAngleY, model.foot1.rotateAngleZ);
      this.setRotation(this.foot2, model.foot2.rotateAngleX, model.foot2.rotateAngleY, model.foot2.rotateAngleZ);
      this.setRotation(super.head, model.head.rotateAngleX, model.head.rotateAngleY, model.head.rotateAngleZ);
      this.setRotation(this.headBT, model.headBT.rotateAngleX, model.headBT.rotateAngleY, model.headBT.rotateAngleZ);
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.setRotation(super.body, 0.1919862F, 0.0F, 0.0F);
      float ankleRot = -1.047198F;
      float ankleRotY = 0.7F;
      super.leg2.rotateAngleX += ankleRot;
      super.leg1.rotateAngleX += ankleRot;
      super.leg1.rotateAngleY = -ankleRotY;
      super.leg2.rotateAngleY = ankleRotY;
      this.headBT.rotateAngleX = 0.0F;
      this.setRotation(this.foot2, -1.047198F, 0.0F, 0.0F);
      this.setRotation(this.foot1, -1.047198F, 0.0F, 0.0F);
      EntitySlimeBoss e = (EntitySlimeBoss)entity;
      AttackKick kick = e.kickHelper;
      int slimePoolAttackTimeMax;
      if(kick.kickTime > 0) {
         super.leg4.rotateAngleX = super.leg3.rotateAngleX = 0.0F;
         slimePoolAttackTimeMax = kick.kickSpeed;
         int i = slimePoolAttackTimeMax - kick.kickTime - slimePoolAttackTimeMax / 10;
         float animProgress = ((float)(i + 1) + (float)i * f5) / (float)slimePoolAttackTimeMax * 6.283184F;
         if(kick.kickType == 1) {
            super.leg1.rotateAngleX = MathHelper.sin(animProgress) * 1.4F - 0.2F;
            super.leg1.rotateAngleY += MathHelper.cos(animProgress) * 0.4F;
            this.foot1.rotateAngleX = -MathHelper.sin(animProgress / 2.0F) * 3.0F - 1.0F;
         } else if(kick.kickType == 3) {
            super.leg2.rotateAngleX = MathHelper.sin(animProgress) * 1.4F - 0.2F;
            super.leg2.rotateAngleY -= MathHelper.cos(animProgress) * 0.4F;
            this.foot2.rotateAngleX = -MathHelper.sin(animProgress / 2.0F) * 3.0F - 1.0F;
         } else if(kick.kickType == 2) {
            super.leg2.rotateAngleX += -MathHelper.sin(animProgress) * 1.4F + 0.2F;
            super.leg2.rotateAngleY += -MathHelper.cos(animProgress) * 0.4F;
         } else if(kick.kickType == 4) {
            super.leg1.rotateAngleX += -MathHelper.sin(animProgress) * 1.4F + 0.2F;
            super.leg1.rotateAngleY += MathHelper.cos(animProgress) * 0.4F;
         }
      }

      if(e.isAttacking()) {
         super.head.rotateAngleX = -0.8F;
         this.headBT.rotateAngleX = 0.8F;
      }

      if(super.onGround > 0.0F) {
         super.head.rotateAngleX = MathHelper.sin(super.onGround * 3.1415927F) * -0.4F;
         this.headBT.rotateAngleX = MathHelper.sin(super.onGround * 3.1415927F) * 0.4F;
      }

      if(e.slimePoolAttackTime > 0) {
         slimePoolAttackTimeMax = e.slimePoolAttackTimeMax;
         float i1 = (float)(e.slimePoolAttackTime - slimePoolAttackTimeMax + e.slimePoolChargeTime);
         super.head.rotateAngleX = (1.0F - Math.max(0.0F, i1 / (float)slimePoolAttackTimeMax)) * 2.0F;
      }

   }
}
