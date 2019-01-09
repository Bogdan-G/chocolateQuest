package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileBase;
import java.util.Random;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class ProjectileHealBall extends ProjectileBase {

   Random rand = new Random();


   public ProjectileHealBall(EntityBaseBall entity) {
      super(entity);
   }

   public int getTextureIndex() {
      return 229;
   }

   public void onImpact(MovingObjectPosition par1MovingObjectPosition) {
      if(!super.entity.worldObj.isRemote && par1MovingObjectPosition.entityHit != null && par1MovingObjectPosition.entityHit == super.entity.getThrower()) {
         super.entity.setlvl(Math.max(1, (int)super.entity.getDamageMultiplier()));
         super.entity.setDead();
         super.entity.getThrower().heal(super.entity.getDamageMultiplier());
      }

   }

   public void onUpdateInAir() {
      EntityLivingBase shooting = super.entity.getThrower();
      if(shooting != null) {
         Vec3 fc = Vec3.createVectorHelper(shooting.posX - super.entity.posX, shooting.posY + 0.4D - super.entity.posY, shooting.posZ - super.entity.posZ);
         if(fc.lengthVector() < 1.0D) {
            super.entity.setDead();
            super.entity.getThrower().heal((float)super.entity.getlvl());
         }

         fc = fc.normalize();
         double s = 0.2D;
         super.entity.motionX = fc.xCoord * s;
         super.entity.motionY = fc.yCoord * s;
         super.entity.motionZ = fc.zCoord * s;
      } else {
         super.entity.setDead();
      }

   }

   public void onDead() {
      if(super.entity.worldObj.isRemote) {
         for(int i = 0; i < super.entity.getlvl(); ++i) {
            super.entity.worldObj.spawnParticle("heart", super.entity.posX + (double)super.entity.getRNG().nextFloat() - 0.5D, super.entity.posY + 1.0D + (double)super.entity.getRNG().nextFloat(), super.entity.posZ + (double)super.entity.getRNG().nextFloat() - 0.5D, (double)(super.entity.getRNG().nextFloat() - 0.5F), (double)(super.entity.getRNG().nextFloat() - 0.5F), (double)(super.entity.getRNG().nextFloat() - 0.5F));
         }
      }

      super.onDead();
   }

   public float getSize() {
      return 0.4F;
   }

   public boolean canBounce() {
      return false;
   }
}
