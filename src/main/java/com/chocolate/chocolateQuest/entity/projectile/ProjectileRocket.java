package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileBulletPistol;
import com.chocolate.chocolateQuest.particles.EffectManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class ProjectileRocket extends ProjectileBulletPistol {

   protected Entity aimedTo;
   int ticksToStartAim;


   public ProjectileRocket(EntityBaseBall entity) {
      super(entity);
   }

   public ProjectileRocket(EntityBaseBall entity, Entity aimedTo) {
      this(entity, aimedTo, 0);
   }

   public ProjectileRocket(EntityBaseBall entity, Entity aimedTo, int ticksToStartAim) {
      super(entity);
      this.aimedTo = aimedTo;
      this.ticksToStartAim = ticksToStartAim;
   }

   public void onUpdateInAir() {
      if(this.aimedTo != null && super.entity.ticksExisted > this.ticksToStartAim) {
         double s = this.aimedTo.posX - super.entity.posX;
         double maxCount = this.aimedTo.posY - super.entity.posY;
         double z = this.aimedTo.posZ - super.entity.posZ;
         Vec3 v = Vec3.createVectorHelper(s, maxCount, z);
         v = v.normalize();
         double xM = v.xCoord;
         double yM = v.yCoord;
         double zM = v.zCoord;
         float desp = 0.2F;
         super.entity.motionX += xM * (double)desp;
         super.entity.motionY += yM * (double)desp;
         super.entity.motionZ += zM * (double)desp;
         desp *= 20.0F;
         if(yM > 0.0D) {
            super.entity.motionY = Math.min(super.entity.motionY, yM * (double)desp);
         } else {
            super.entity.motionY = Math.max(super.entity.motionY, yM * (double)desp);
         }

         if(xM > 0.0D) {
            super.entity.motionX = Math.min(super.entity.motionX, xM * (double)desp);
         } else {
            super.entity.motionX = Math.max(super.entity.motionX, xM * (double)desp);
         }

         if(zM > 0.0D) {
            super.entity.motionZ = Math.min(super.entity.motionZ, zM * (double)desp);
         } else {
            super.entity.motionZ = Math.max(super.entity.motionZ, zM * (double)desp);
         }
      }

      if(super.entity.worldObj.isRemote) {
         float var15 = 0.2F;
         float m = 0.0F;
         byte var16 = 2;

         for(int i = 0; i <= var16; ++i) {
            m = (float)i / (float)var16;
            EffectManager.spawnParticle(6, super.entity.worldObj, super.entity.posX + (double)((super.rand.nextFloat() - 0.5F) * var15) - super.entity.motionX * (double)m, super.entity.posY + (double)((super.rand.nextFloat() - 0.5F) * var15) - super.entity.motionY * (double)m, super.entity.posZ + (double)((super.rand.nextFloat() - 0.5F) * var15) - super.entity.motionZ * (double)m, 1.0D, 1.0D, 1.0D);
         }
      }

   }

   protected int getBulletBaseDamage() {
      return 10;
   }

   public float getBulletPitch() {
      return 0.4F;
   }

   public float getSize() {
      return 0.2F;
   }
}
