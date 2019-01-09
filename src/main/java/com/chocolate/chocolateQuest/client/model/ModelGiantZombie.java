package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.entity.boss.AttackKick;
import com.chocolate.chocolateQuest.entity.boss.EntityGiantZombie;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class ModelGiantZombie extends ModelBiped {

   boolean attacking;
   int attackAnim;
   int maxAttackAnimTime;
   int attackType;


   public ModelGiantZombie() {
      this(0.0F);
   }

   public ModelGiantZombie(float s) {
      super(s, 0.0F, 64, 64);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
   }

   public void setLivingAnimations(EntityLivingBase entityliving, float f, float f1, float f2) {
      super.setLivingAnimations(entityliving, f, f1, f2);
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      super.bipedRightLeg.rotationPointY = 12.0F;
      super.bipedLeftLeg.rotationPointY = 12.0F;
      EntityGiantZombie e = (EntityGiantZombie)entity;
      AttackKick kick = e.kickHelper;
      if(kick.kickTime > 0) {
         super.bipedRightLeg.rotateAngleX = 0.0F;
         super.bipedLeftLeg.rotateAngleX = 0.0F;
         int f6 = kick.kickSpeed;
         int f7 = f6 - kick.kickTime - f6 / 10;
         float animProgress = ((float)(f7 + 1) + (float)f7 * f5) / (float)f6 * 6.283184F;
         float dir = kick.kickType != 2 && kick.kickType != 4?1.0F:-1.0F;
         if(kick.kickType != 2 && kick.kickType != 1) {
            super.bipedRightLeg.rotateAngleX = dir * MathHelper.sin(animProgress) * 1.2F - dir * 0.2F;
            super.bipedRightLeg.rotateAngleY = dir * -MathHelper.cos(animProgress) * 0.4F;
         } else {
            super.bipedLeftLeg.rotateAngleX = dir * MathHelper.sin(animProgress) * 1.2F - dir * 0.2F;
            super.bipedLeftLeg.rotateAngleY = dir * MathHelper.cos(animProgress) * 0.4F;
         }
      }

      float f61 = MathHelper.sin(super.onGround * 3.1415927F);
      float f71 = MathHelper.sin((1.0F - (1.0F - super.onGround) * (1.0F - super.onGround)) * 3.1415927F);
      super.bipedRightArm.rotateAngleZ = 0.0F;
      super.bipedLeftArm.rotateAngleZ = 0.0F;
      super.bipedRightArm.rotateAngleY = 0.1F - f61 * 0.6F;
      super.bipedRightArm.rotateAngleX = -1.5707964F;
      super.bipedLeftArm.rotateAngleX = -1.5707964F;
      super.bipedRightArm.rotateAngleX += f61 * 1.2F - f71 * 0.4F;
      super.bipedRightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
      super.bipedLeftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
      super.bipedRightArm.rotateAngleX += MathHelper.sin(f2 * 0.067F) * 0.05F;
      super.bipedLeftArm.rotateAngleX -= MathHelper.sin(f2 * 0.067F) * 0.05F;
   }
}
