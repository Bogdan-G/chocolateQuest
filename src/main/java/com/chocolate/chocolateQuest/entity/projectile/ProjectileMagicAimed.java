package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileMagic;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class ProjectileMagicAimed extends ProjectileMagic {

   protected Entity aimedTo;
   int ticksToStartAim;


   public ProjectileMagicAimed(EntityBaseBall entity) {
      super(entity);
   }

   public ProjectileMagicAimed(EntityBaseBall entity, Entity aimedTo) {
      this(entity, aimedTo, 0);
   }

   public ProjectileMagicAimed(EntityBaseBall entity, Entity aimedTo, int ticksToStartAim) {
      super(entity);
      this.aimedTo = aimedTo;
      this.ticksToStartAim = ticksToStartAim;
   }

   public int getTextureIndex() {
      return 247 + super.type - (super.entity.ticksExisted / 4 % 2 == 0?0:16);
   }

   public void onUpdateInAir() {
      super.onUpdateInAir();
      if(this.aimedTo != null && super.entity.ticksExisted >= this.ticksToStartAim) {
         double x = this.aimedTo.posX - super.entity.posX;
         double y = this.aimedTo.posY + (double)this.aimedTo.height - 0.4D - super.entity.posY;
         double z = this.aimedTo.posZ - super.entity.posZ;
         Vec3 v = Vec3.createVectorHelper(x, y, z);
         v = v.normalize();
         double xM = v.xCoord;
         double yM = v.yCoord;
         double zM = v.zCoord;
         float desp = 0.1F;
         super.entity.motionX += xM * (double)desp;
         super.entity.motionY += yM * (double)desp;
         super.entity.motionZ += zM * (double)desp;
         desp *= 40.0F;
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

   }

   public float getSize() {
      return 0.8F;
   }

   public boolean spawnParticles() {
      return true;
   }
}
