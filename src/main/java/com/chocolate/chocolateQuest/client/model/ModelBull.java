package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.entity.boss.AttackKickQuadruped;
import com.chocolate.chocolateQuest.entity.boss.EntityBull;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelBull extends ModelQuadruped {

   ModelRenderer horn22;
   ModelRenderer horn21;
   ModelRenderer horn2;
   ModelRenderer horn12;
   ModelRenderer horn1;
   ModelRenderer horn11;
   ModelRenderer sideLeft;
   ModelRenderer sideRight;
   ModelRenderer sideLeftBleeding;
   ModelRenderer sideRightBleeding;
   ModelRenderer mouth;
   ModelRenderer tail;
   ModelRenderer tailEnd;
   ModelRenderer tailEndM;


   public ModelBull() {
      super(12, 0.0F);
      super.textureWidth = 64;
      super.textureHeight = 64;
      float ho = 3.0F;
      super.head = new ModelRenderer(this, 0, 0);
      super.head.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 6);
      super.head.setRotationPoint(0.0F, 6.0F, -6.0F);
      super.body = new ModelRenderer(this, 0, 36);
      super.body.addBox(-6.0F, -10.0F, -18.0F, 12, 10, 18);
      super.body.setRotationPoint(0.0F, 12.0F, 9.0F);
      super.body.setTextureSize(super.textureWidth, super.textureHeight);
      super.leg1 = new ModelRenderer(this, 48, 0);
      super.leg1.addBox(-3.0F, 0.0F + ho, -2.0F, 4, 12, 4);
      super.leg1.setRotationPoint(-3.0F, 8.0F, 7.0F);
      super.leg1.setTextureSize(super.textureWidth, super.textureHeight);
      super.leg1.mirror = true;
      super.leg2 = new ModelRenderer(this, 48, 0);
      super.leg2.addBox(-1.0F, 0.0F + ho, -2.0F, 4, 12, 4);
      super.leg2.setRotationPoint(3.0F, 8.0F, 7.0F);
      super.leg2.setTextureSize(super.textureWidth, super.textureHeight);
      super.leg2.mirror = true;
      super.leg3 = new ModelRenderer(this, 48, 0);
      super.leg3.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4);
      super.leg3.setRotationPoint(-5.0F, 1.0F, -16.0F);
      super.leg3.setTextureSize(super.textureWidth, super.textureHeight);
      super.leg3.mirror = true;
      super.leg4 = new ModelRenderer(this, 48, 0);
      super.leg4.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4);
      super.leg4.setRotationPoint(3.0F, 1.0F, -16.0F);
      super.leg4.setTextureSize(super.textureWidth, super.textureHeight);
      super.leg4.mirror = true;
      super.body.addChild(super.leg3);
      super.body.addChild(super.leg4);
      this.horn1 = new ModelRenderer(this, 22, 0);
      this.horn1.addBox(-4.0F, -3.0F, -5.0F, 2, 2, 2);
      this.horn1.setRotationPoint(0.0F, -1.0F, -3.0F);
      this.horn1.setTextureSize(super.textureWidth, super.textureHeight);
      this.horn1.mirror = true;
      this.setRotation(this.horn1, -0.0569039F, 0.0F, 0.0F);
      this.horn11 = new ModelRenderer(this, 22, 0);
      this.horn11.addBox(-5.0F, -4.0F, -6.0F, 2, 2, 2);
      this.horn11.setRotationPoint(0.0F, -1.0F, -3.0F);
      this.horn11.setTextureSize(super.textureWidth, super.textureHeight);
      this.horn11.mirror = true;
      this.setRotation(this.horn11, -0.0569039F, 0.0F, 0.0F);
      this.horn12 = new ModelRenderer(this, 22, 0);
      this.horn12.addBox(-5.0F, -4.0F, -9.0F, 1, 1, 3);
      this.horn12.setRotationPoint(0.0F, -1.0F, -3.0F);
      this.horn12.setTextureSize(super.textureWidth, super.textureHeight);
      this.horn12.mirror = true;
      this.setRotation(this.horn12, -0.0569039F, 0.0F, 0.0F);
      this.horn22 = new ModelRenderer(this, 22, 0);
      this.horn22.addBox(4.0F, -4.0F, -9.0F, 1, 1, 3);
      this.horn22.setRotationPoint(0.0F, -1.0F, -3.0F);
      this.horn22.setTextureSize(super.textureWidth, super.textureHeight);
      this.horn22.mirror = true;
      this.setRotation(this.horn22, -0.0569039F, 0.0F, 0.0F);
      this.horn21 = new ModelRenderer(this, 22, 0);
      this.horn21.addBox(3.0F, -4.0F, -6.0F, 2, 2, 2);
      this.horn21.setRotationPoint(0.0F, -1.0F, -3.0F);
      this.horn21.setTextureSize(super.textureWidth, super.textureHeight);
      this.horn21.mirror = true;
      this.setRotation(this.horn21, -0.0569039F, 0.0F, 0.0F);
      this.horn2 = new ModelRenderer(this, 22, 0);
      this.horn2.addBox(2.0F, -3.0F, -5.0F, 2, 2, 2);
      this.horn2.setRotationPoint(0.0F, -1.0F, -3.0F);
      this.horn2.setTextureSize(super.textureWidth, super.textureHeight);
      this.horn2.mirror = true;
      this.setRotation(this.horn2, -0.0569039F, 0.0F, 0.0F);
      super.head.addChild(this.horn1);
      super.head.addChild(this.horn11);
      super.head.addChild(this.horn12);
      super.head.addChild(this.horn2);
      super.head.addChild(this.horn21);
      super.head.addChild(this.horn22);
      super.head.setRotationPoint(0.0F, -6.0F, -16.0F);
      super.body.addChild(super.head);
      this.sideLeft = new ModelRenderer(this, 30, 0);
      this.sideLeft.addBox(0.0F, 0.0F, 0.0F, 1, 9, 16);
      this.sideLeft.setRotationPoint(6.0F, -9.0F, -17.0F);
      this.sideRight = new ModelRenderer(this, 30, 0);
      this.sideRight.addBox(0.0F, 0.0F, 0.0F, 1, 9, 16);
      this.sideRight.setRotationPoint(-7.0F, -9.0F, -17.0F);
      super.body.addChild(this.sideLeft);
      super.body.addChild(this.sideRight);
      this.sideLeftBleeding = new ModelRenderer(this, 12, 11);
      this.sideLeftBleeding.addBox(0.0F, 0.0F, 0.0F, 1, 9, 16);
      this.sideLeftBleeding.setRotationPoint(6.0F, -9.0F, -17.0F);
      this.sideRightBleeding = new ModelRenderer(this, 12, 11);
      this.sideRightBleeding.addBox(0.0F, 0.0F, 0.0F, 1, 9, 16);
      this.sideRightBleeding.setRotationPoint(-7.0F, -9.0F, -17.0F);
      super.body.addChild(this.sideLeftBleeding);
      super.body.addChild(this.sideRightBleeding);
      this.mouth = new ModelRenderer(this, 30, 0);
      this.mouth.addBox(-2.0F, 1.0F, -9.0F, 4, 3, 2);
      super.head.addChild(this.mouth);
      this.tail = new ModelRenderer(this, 0, 14);
      this.tail.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 6);
      this.tail.setRotationPoint(0.0F, -9.0F, 0.0F);
      super.body.addChild(this.tail);
      this.tailEnd = new ModelRenderer(this, 46, 24);
      this.tailEnd.addBox(0.0F, -1.5F, 0.0F, 0, 4, 8);
      this.tailEnd.setRotationPoint(0.0F, 0.0F, 6.0F);
      this.tailEndM = new ModelRenderer(this, 46, 24);
      this.tailEndM.addBox(0.5F, -2.0F, 0.0F, 0, 4, 8);
      this.tailEndM.setRotationPoint(0.0F, 0.0F, 6.0F);
      this.tailEndM.rotateAngleZ = 1.5708F;
      this.tail.addChild(this.tailEnd);
      this.tail.addChild(this.tailEndM);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      super.body.render(f5);
      super.leg1.render(f5);
      super.leg2.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      super.body.rotateAngleX = 0.0F;
      super.leg1.rotateAngleY = super.leg2.rotateAngleY = super.leg3.rotateAngleY = super.leg4.rotateAngleY = 0.0F;
      this.tail.rotateAngleX = -1.4F;
      EntityBull e = (EntityBull)entity;
      if(e.charge) {
         super.head.rotateAngleX = (float)((double)super.head.rotateAngleX + (0.3D - (double)((float)Math.cos((double)((float)e.chargeTime / (float)e.chargeTimeMax * 24.0F)) * 0.4F)));
         this.tail.rotateAngleX += (float)e.chargeTime / (float)e.chargeTimeMax * 1.4F;
      } else {
         super.head.offsetY = 0.0F;
      }

      AttackKickQuadruped kick = e.kickHelper;
      int attackAnim;
      float animProgress;
      if(kick.kickTime > 0 || kick.kickTimeBack > 0) {
         super.leg4.rotateAngleX = super.leg3.rotateAngleX = super.leg2.rotateAngleX = super.leg1.rotateAngleX = 0.0F;
         int b = kick.kickSpeed;
         attackAnim = b - kick.kickTime - b / 10;
         animProgress = ((float)(attackAnim + 1) + (float)attackAnim * f5) / (float)b * 6.283184F;
         if(kick.kickType == 1) {
            super.leg4.rotateAngleX = MathHelper.sin(animProgress) * 1.4F - 0.2F;
            super.leg4.rotateAngleY = MathHelper.cos(animProgress) * 0.4F;
         } else if(kick.kickType == 3) {
            super.leg3.rotateAngleX = MathHelper.sin(animProgress) * 1.4F - 0.2F;
            super.leg3.rotateAngleY = -MathHelper.cos(animProgress) * 0.4F;
         }

         attackAnim = b - kick.kickTimeBack - b / 10;
         animProgress = ((float)(attackAnim + 1) + (float)attackAnim * f5) / (float)b * 6.283184F;
         if(kick.kickTypeBack == 2) {
            super.leg2.rotateAngleX = -MathHelper.sin(animProgress) * 1.4F + 0.2F;
            super.leg2.rotateAngleY = -MathHelper.cos(animProgress) * 0.4F;
         } else if(kick.kickTypeBack == 4) {
            super.leg1.rotateAngleX = -MathHelper.sin(animProgress) * 1.4F + 0.2F;
            super.leg1.rotateAngleY = MathHelper.cos(animProgress) * 0.4F;
         }
      }

      if(e.smashTime > 0) {
         e.getClass();
         byte b1 = 30;
         attackAnim = b1 - e.smashTime - b1 / 10;
         animProgress = ((float)(attackAnim + 1) + (float)attackAnim * f5) / (float)b1 * 6.283184F;
         float rot = -MathHelper.sin(animProgress / 2.0F) * 0.5F;
         super.body.rotateAngleX = rot;
         super.leg4.rotateAngleX = rot;
         super.leg3.rotateAngleX = rot;
      }

      this.sideLeft.isHidden = e.isHurtLeft();
      this.sideLeftBleeding.isHidden = !e.isHurtLeft();
      this.sideRight.isHidden = e.isHurtRight();
      this.sideRightBleeding.isHidden = !e.isHurtRight();
      boolean b2 = !e.isHornBroken();
      this.horn11.showModel = b2;
      this.horn12.showModel = b2;
      this.horn22.showModel = b2;
   }
}
