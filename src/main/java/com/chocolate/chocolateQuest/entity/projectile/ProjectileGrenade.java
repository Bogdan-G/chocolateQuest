package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileBase;
import net.minecraft.block.material.Material;
import net.minecraft.util.MovingObjectPosition;

public class ProjectileGrenade extends ProjectileBase {

   int timeTillExplosion = 30;
   boolean ignited = false;


   public ProjectileGrenade(EntityBaseBall entity) {
      super(entity);
   }

   public int getTextureIndex() {
      return this.timeTillExplosion > 15?(super.entity.ticksExisted / 2 % 2 == 0?178:179):(super.entity.ticksExisted / 2 % 2 == 0?180:181);
   }

   public void onImpact(MovingObjectPosition par1MovingObjectPosition) {
      if(par1MovingObjectPosition.entityHit != super.entity.getThrower()) {
         Material mat = super.entity.worldObj.getBlock(par1MovingObjectPosition.blockX, par1MovingObjectPosition.blockY, par1MovingObjectPosition.blockZ).getMaterial();
         if(mat != Material.air && mat != Material.fire && mat != Material.plants && mat != Material.vine && mat != Material.web && mat != Material.snow) {
            super.entity.motionX = 0.0D;
            super.entity.motionZ = 0.0D;
            super.entity.motionY = 0.0D;
            this.ignited = true;
         }
      }

   }

   public void onUpdateInAir() {
      if(!super.entity.onGround) {
         super.entity.motionY -= 0.019999999552965164D;
      }

      if(this.ignited) {
         --this.timeTillExplosion;
         if(this.timeTillExplosion <= 0) {
            boolean breakGround = false;
            float radio = 1.5F + (float)super.entity.getlvl() * 0.5F;
            if(super.entity.getlvl() == 2) {
               breakGround = true;
               radio = 3.0F;
            }

            super.entity.worldObj.createExplosion(super.entity.getThrower(), super.entity.posX, super.entity.posY, super.entity.posZ, radio, breakGround);
            super.entity.setDead();
         }
      }

   }

   public float getSize() {
      return 0.4F + (float)super.entity.getlvl() * 0.2F;
   }

   public boolean longRange() {
      return false;
   }
}
