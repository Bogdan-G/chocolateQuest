package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelHuman extends ModelBiped {

   public boolean isFemale;
   public boolean renderTinyArms;
   ModelRenderer bipedRightArmTiny;
   ModelRenderer bipedLeftArmTiny;


   public ModelHuman() {
      this(0.0F);
   }

   public ModelHuman(float f) {
      this(f, 0.0F);
   }

   public ModelHuman(float f, boolean renderTinyArms) {
      this(f, 0.0F);
      this.renderTinyArms = renderTinyArms;
   }

   public ModelHuman(float f, float f1) {
      this.renderTinyArms = true;
      super.heldItemLeft = 0;
      super.heldItemRight = 0;
      super.aimedBow = false;
      super.bipedCloak = new ModelRenderer(this, 0, 0);
      super.bipedCloak.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, f);
      super.bipedEars = new ModelRenderer(this, 25, 0);
      super.bipedEars.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
      super.bipedEars.addBox(-7.0F, -7.0F, -1.0F, 3, 4, 0, f);
      super.bipedHead = new ModelRenderer(this, 0, 0);
      super.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
      super.bipedHead.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
      super.bipedHeadwear = new ModelRenderer(this, 32, 0);
      super.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, f + 0.5F);
      super.bipedHeadwear.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
      super.bipedBody = new ModelRenderer(this, 16, 16);
      super.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, f);
      super.bipedBody.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
      super.bipedRightArm = new ModelRenderer(this, 40, 16);
      super.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, f);
      super.bipedRightArm.setRotationPoint(-5.0F, 2.0F + f1, 0.0F);
      super.bipedLeftArm = new ModelRenderer(this, 40, 16);
      super.bipedLeftArm.mirror = true;
      super.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, f);
      super.bipedLeftArm.setRotationPoint(5.0F, 2.0F + f1, 0.0F);
      super.bipedRightLeg = new ModelRenderer(this, 0, 16);
      super.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, f);
      super.bipedRightLeg.setRotationPoint(-2.0F, 12.0F + f1, 0.0F);
      super.bipedLeftLeg = new ModelRenderer(this, 0, 16);
      super.bipedLeftLeg.mirror = true;
      super.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, f);
      super.bipedLeftLeg.setRotationPoint(2.0F, 12.0F + f1, 0.0F);
      super.bipedEars = new ModelRenderer(this, 25, 1);
      super.bipedEars.setRotationPoint(0.0F, 0.0F, 0.0F);
      super.bipedEars.addBox(-7.0F, -7.0F, -1.0F, 3, 4, 0, 0.0F);
      this.bipedRightArmTiny = new ModelRenderer(this, 41, 17);
      this.bipedRightArmTiny.addBox(-3.0F, -2.0F, -2.0F, 3, 12, 3, f);
      this.bipedRightArmTiny.setRotationPoint(-4.0F, 2.0F + f1, 0.0F);
      this.bipedLeftArmTiny = new ModelRenderer(this, 41, 17);
      this.bipedLeftArmTiny.mirror = true;
      this.bipedLeftArmTiny.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 3, f);
      this.bipedLeftArmTiny.setRotationPoint(5.0F, 2.0F + f1, 0.0F);
   }

   public ModelHuman(float par1, float par2, int par3, int par4) {
      super(par1, par2, par3, par4);
      this.renderTinyArms = true;
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      if(entity != null) {
         super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
         EntityHumanBase human = (EntityHumanBase)entity;
         this.setHumanRotationAngles(f, f1, f2, f3, f4, f5, human);
      }
   }

   public void setHumanRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, EntityHumanBase e) {
      this.isFemale = !e.isMale;
      if(e.isSitting()) {
         super.bipedRightArm.rotateAngleX += -0.62831855F;
         super.bipedLeftArm.rotateAngleX += -0.62831855F;
         super.bipedRightLeg.rotateAngleX = -1.5707964F;
         super.bipedLeftLeg.rotateAngleX = -1.5707964F;
         super.bipedRightLeg.rotateAngleY = 0.31415927F;
         super.bipedLeftLeg.rotateAngleY = -0.31415927F;
      } else {
         if(e.isSneaking()) {
            super.bipedBody.rotateAngleX = 0.5F;
            super.bipedRightLeg.rotateAngleX -= 0.0F;
            super.bipedLeftLeg.rotateAngleX -= 0.0F;
            super.bipedRightArm.rotateAngleX += 0.4F;
            super.bipedLeftArm.rotateAngleX += 0.4F;
            super.bipedRightLeg.rotationPointZ = 4.0F;
            super.bipedLeftLeg.rotationPointZ = 4.0F;
            super.bipedRightLeg.rotationPointY = 9.0F;
            super.bipedLeftLeg.rotationPointY = 9.0F;
            super.bipedHead.rotationPointY = 1.0F;
         }

         if(e.isTwoHanded()) {
            this.setTwoHandedAngles(f2);
         }

         if(e.isAiming()) {
            float attackAnim = super.bipedRightArm.rotateAngleX;
            float maxAttackAnimTime = super.bipedRightArm.rotateAngleY;
            if(e.rightHand.isTwoHanded()) {
               if(e.rightHand.isAiming()) {
                  this.setAimingAngles(f2);
               }
            } else {
               if(e.rightHand.isAiming()) {
                  this.setAimingAnglesRight(f2);
               }

               if(e.leftHand.isAiming()) {
                  this.setAimingAnglesLeft(f2);
               } else if(!e.rightHand.isAiming()) {
                  this.setAimingAngles(f2);
               }
            }

            if(e.rightHand.isAiming() && super.onGround > 0.0F) {
               super.bipedRightArm.rotateAngleY += maxAttackAnimTime;
               super.bipedRightArm.rotateAngleX += attackAnim + 0.3F;
            }
         }

         if(e.leftHandSwing > 0) {
            int attackAnim1 = e.leftHandSwing;
            byte maxAttackAnimTime1 = 10;
            float animProgress;
            if(e.haveShied()) {
               this.setShiedRotation(f2);
               animProgress = ((float)attackAnim1 + (float)(attackAnim1 - 1) * f5) / (float)maxAttackAnimTime1 * 4.82F;
               super.bipedLeftArm.rotateAngleY += MathHelper.cos(animProgress) * 1.8F - 0.8F;
               super.bipedLeftArm.rotateAngleX -= MathHelper.cos(animProgress) * 0.6F;
            } else {
               animProgress = (float)((double)(((float)attackAnim1 + (float)(attackAnim1 - 1) * f5) / (float)maxAttackAnimTime1) * 3.141592653589793D);
               super.bipedLeftArm.rotateAngleY += MathHelper.cos(animProgress) * 0.5F;
               super.bipedLeftArm.rotateAngleX -= MathHelper.sin(animProgress) * 1.2F;
            }
         } else if(e.isDefending()) {
            this.setShiedRotation(f2);
         }
      }

   }

   public void render(Entity z, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotationAngles(f, f1, f2, f3, f4, f5, z);
      super.bipedHead.render(f5);
      super.bipedBody.render(f5);
      super.bipedRightLeg.render(f5);
      super.bipedLeftLeg.render(f5);
      super.bipedHeadwear.render(f5);
      this.renderArms(f5);
   }

   public void renderArms(float f) {
      if(this.isFemale && this.useTinyArms() && this.renderTinyArms) {
         this.bipedRightArmTiny.rotateAngleX = super.bipedRightArm.rotateAngleX;
         this.bipedRightArmTiny.rotateAngleY = super.bipedRightArm.rotateAngleY;
         this.bipedRightArmTiny.rotateAngleZ = super.bipedRightArm.rotateAngleZ;
         this.bipedLeftArmTiny.rotateAngleX = super.bipedLeftArm.rotateAngleX;
         this.bipedLeftArmTiny.rotateAngleY = super.bipedLeftArm.rotateAngleY;
         this.bipedLeftArmTiny.rotateAngleZ = super.bipedLeftArm.rotateAngleZ;
         this.bipedRightArmTiny.render(f);
         this.bipedLeftArmTiny.render(f);
      } else {
         super.bipedRightArm.render(f);
         super.bipedLeftArm.render(f);
      }

   }

   public void renderEars(float f) {
      super.bipedEars.rotationPointX = 0.0F;
      super.bipedEars.rotationPointZ = super.bipedHead.rotationPointZ + 1.0F;
      super.bipedEars.rotateAngleY = super.bipedHead.rotateAngleY;
      super.bipedEars.rotateAngleX = super.bipedHead.rotateAngleX;
      super.bipedEars.render(f);
      super.bipedEars.rotateAngleX = -super.bipedHead.rotateAngleX;
      super.bipedEars.rotateAngleY = (float)((double)super.bipedHead.rotateAngleY - 3.141592653589793D);
      super.bipedEars.rotationPointY = super.bipedHead.rotationPointY;
      super.bipedEars.rotationPointZ = super.bipedHead.rotationPointZ - 1.0F;
      super.bipedEars.render(f);
   }

   public void renderCloak(float f) {
      super.bipedCloak.render(f);
   }

   public void setTwoHandedAngles(float time) {
      float f7 = 0.0F;
      float rotationYaw = 0.0F;
      float swing = MathHelper.sin(super.onGround * 3.1415927F);
      super.bipedRightArm.rotateAngleZ = 0.0F;
      super.bipedLeftArm.rotateAngleZ = -0.3F;
      super.bipedRightArm.rotateAngleY = rotationYaw - 0.6F;
      super.bipedLeftArm.rotateAngleY = rotationYaw + 0.6F;
      super.bipedRightArm.rotateAngleX = -0.8F + swing;
      super.bipedLeftArm.rotateAngleX = -0.8F + swing;
      super.bipedRightArm.rotateAngleZ += MathHelper.cos(time * 0.09F) * 0.05F + 0.05F;
      super.bipedLeftArm.rotateAngleZ -= MathHelper.cos(time * 0.09F) * 0.05F + 0.05F;
      super.bipedRightArm.rotateAngleX += MathHelper.sin(time * 0.067F) * 0.05F;
      super.bipedLeftArm.rotateAngleX -= MathHelper.sin(time * 0.067F) * 0.05F;
      float f6 = 1.0F - super.onGround;
      f6 *= f6;
      f6 *= f6;
      f6 = 1.0F - f6;
      f7 = MathHelper.sin(f6 * 3.1415927F);
      float f8 = MathHelper.sin(super.onGround * 3.1415927F) * -(super.bipedHead.rotateAngleX - 0.7F) * 0.75F;
      super.bipedRightArm.rotateAngleX = (float)((double)super.bipedRightArm.rotateAngleX - ((double)f7 * 1.2D + (double)f8));
      super.bipedRightArm.rotateAngleY += super.bipedBody.rotateAngleY * 2.0F;
      super.bipedRightArm.rotateAngleZ = MathHelper.sin(super.onGround * 3.1415927F) * -0.4F;
      super.bipedLeftArm.rotateAngleX = (float)((double)super.bipedRightArm.rotateAngleX - ((double)f7 * 1.2D + (double)f8));
      super.bipedLeftArm.rotateAngleY += super.bipedBody.rotateAngleY * 2.0F;
      super.bipedLeftArm.rotateAngleZ = MathHelper.sin(super.onGround * 3.1415927F) * -0.4F;
   }

   public void setAimingAngles(float time) {
      float f7 = 0.0F;
      float f9 = 0.0F;
      super.bipedRightArm.rotateAngleZ = 0.0F;
      super.bipedLeftArm.rotateAngleZ = 0.0F;
      super.bipedRightArm.rotateAngleY = -(0.1F - f7 * 0.6F) + super.bipedHead.rotateAngleY;
      super.bipedLeftArm.rotateAngleY = 0.1F - f7 * 0.6F + super.bipedHead.rotateAngleY + 0.4F;
      super.bipedRightArm.rotateAngleX = -1.5707964F + super.bipedHead.rotateAngleX;
      super.bipedLeftArm.rotateAngleX = -1.5707964F + super.bipedHead.rotateAngleX;
      super.bipedRightArm.rotateAngleX -= f7 * 1.2F - f9 * 0.4F;
      super.bipedLeftArm.rotateAngleX -= f7 * 1.2F - f9 * 0.4F;
      super.bipedRightArm.rotateAngleZ += MathHelper.cos(time * 0.09F) * 0.05F + 0.05F;
      super.bipedLeftArm.rotateAngleZ -= MathHelper.cos(time * 0.09F) * 0.05F + 0.05F;
      super.bipedRightArm.rotateAngleX += MathHelper.sin(time * 0.067F) * 0.05F;
      super.bipedLeftArm.rotateAngleX -= MathHelper.sin(time * 0.067F) * 0.05F;
   }

   public void setAimingAnglesRight(float time) {
      float f7 = 0.0F;
      float f9 = 0.0F;
      super.bipedRightArm.rotateAngleX = -1.5707964F + super.bipedHead.rotateAngleX;
      super.bipedRightArm.rotateAngleX -= f7 * 1.2F - f9 * 0.4F;
      super.bipedRightArm.rotateAngleX -= MathHelper.sin(time * 0.067F) * 0.05F;
      super.bipedRightArm.rotateAngleY = -0.060000002F + super.bipedHead.rotateAngleY;
      super.bipedRightArm.rotateAngleZ = MathHelper.cos(time * 0.09F) * 0.05F + 0.05F;
   }

   public void setAimingAnglesLeft(float time) {
      float f7 = 0.0F;
      float f9 = 0.0F;
      super.bipedLeftArm.rotateAngleX = -1.5707964F + super.bipedHead.rotateAngleX;
      super.bipedLeftArm.rotateAngleX -= f7 * 1.2F - f9 * 0.4F;
      super.bipedLeftArm.rotateAngleX -= MathHelper.sin(time * 0.067F) * 0.05F;
      super.bipedLeftArm.rotateAngleY = 0.1F - f7 * 0.6F + super.bipedHead.rotateAngleY;
      super.bipedLeftArm.rotateAngleZ = -MathHelper.cos(time * 0.09F) * 0.05F + 0.05F;
   }

   public void setShiedRotation(float time) {
      float f7 = 0.0F;
      float f9 = 0.0F;
      super.bipedLeftArm.rotateAngleZ = -0.7F;
      super.bipedLeftArm.rotateAngleY = 1.2F;
      super.bipedLeftArm.rotateAngleX = -1.5707964F + super.bipedHead.rotateAngleX;
      super.bipedLeftArm.rotateAngleX -= f7 * 1.2F - f9 * 0.4F;
      super.bipedLeftArm.rotateAngleZ -= MathHelper.cos(time * 0.09F) * 0.05F + 0.05F;
      super.bipedLeftArm.rotateAngleX -= MathHelper.sin(time * 0.067F) * 0.05F;
   }

   public boolean useTinyArms() {
      return true;
   }
}
