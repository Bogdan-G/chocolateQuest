package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileBase;
import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;

public class ProjectileMagicPrison extends ProjectileBase {

   int mountTime = 0;


   public ProjectileMagicPrison(EntityBaseBall entity) {
      super(entity);
   }

   public int getTextureIndex() {
      return 240;
   }

   public void onImpact(MovingObjectPosition par1MovingObjectPosition) {
      super.entity.worldObj.playSoundEffect((double)((int)super.entity.posX), (double)((int)super.entity.posY), (double)((int)super.entity.posZ), "sounds.bubble", 4.0F, (1.0F + (super.entity.worldObj.rand.nextFloat() - super.entity.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
      if(!super.entity.worldObj.isRemote) {
         if(par1MovingObjectPosition.entityHit instanceof EntityLivingBase) {
            if(par1MovingObjectPosition.entityHit.ridingEntity == null) {
               par1MovingObjectPosition.entityHit.attackEntityFrom(HelperDamageSource.causeProjectilePhysicalDamage(super.entity, super.entity.getThrower()), super.entity.getDamageMultiplier() * 0.5F);
               if(par1MovingObjectPosition.entityHit.width * 2.0F + par1MovingObjectPosition.entityHit.height <= 4.0F) {
                  par1MovingObjectPosition.entityHit.mountEntity(super.entity);
               } else {
                  super.entity.setDead();
               }
            }
         } else {
            super.entity.setDead();
         }
      }

   }

   public void onUpdateInAir() {
      if(super.entity.riddenByEntity != null) {
         super.entity.motionX = 0.0D;
         super.entity.motionY = 0.0D;
         super.entity.motionZ = 0.0D;
         ++this.mountTime;
         Material mat = super.entity.worldObj.getBlock(MathHelper.floor_double(super.entity.posX), MathHelper.floor_double(super.entity.posY + (double)super.entity.riddenByEntity.height), MathHelper.floor_double(super.entity.posZ)).getMaterial();
         if(mat == Material.air || mat.isLiquid()) {
            super.entity.motionY = 0.02D * (double)super.entity.getDamageMultiplier();
         }

         if(this.mountTime >= 40 + super.entity.getlvl() * 10) {
            super.entity.setDead();
         }
      }

   }

   public float getSize() {
      return super.entity.riddenByEntity != null?super.entity.riddenByEntity.width + super.entity.riddenByEntity.height:1.5F;
   }

   public boolean canBounce() {
      return false;
   }

   public void onSpawn() {
      super.entity.worldObj.playSoundEffect((double)((int)super.entity.posX), (double)((int)super.entity.posY), (double)((int)super.entity.posZ), "random.swim", 4.0F, (1.0F + (super.entity.worldObj.rand.nextFloat() - super.entity.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
   }

   public void attackFrom(DamageSource d, float damage) {
      this.mountTime += 10;
   }

   public boolean longRange() {
      return false;
   }

   public int getMaxLifeTime() {
      return 150;
   }
}
