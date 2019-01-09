package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileBase;
import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;

public class ProjectileBubble extends ProjectileBase {

   Random rand = new Random();
   int mountTime = 0;


   public ProjectileBubble(EntityBaseBall entity) {
      super(entity);
   }

   public int getTextureIndex() {
      return 240;
   }

   public void onImpact(MovingObjectPosition par1MovingObjectPosition) {
      super.entity.worldObj.playSoundEffect((double)((int)super.entity.posX), (double)((int)super.entity.posY), (double)((int)super.entity.posZ), "liquid:swim", 4.0F, (1.0F + (super.entity.worldObj.rand.nextFloat() - super.entity.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
      if(!super.entity.worldObj.isRemote) {
         if(par1MovingObjectPosition.entityHit instanceof EntityLivingBase) {
            if(par1MovingObjectPosition.entityHit.ridingEntity == null) {
               par1MovingObjectPosition.entityHit.attackEntityFrom(HelperDamageSource.causeProjectilePhysicalDamage(super.entity, super.entity.getThrower()), 4.0F);
               par1MovingObjectPosition.entityHit.mountEntity(super.entity);
            }
         } else if(!super.entity.getThrower().isEntityEqual(par1MovingObjectPosition.entityHit)) {
            super.entity.setDead();
         }
      }

   }

   public void onUpdateInAir() {
      Material i;
      if(super.entity.riddenByEntity != null) {
         super.entity.motionX = 0.0D;
         super.entity.motionY = 0.0D;
         super.entity.motionZ = 0.0D;
         ++this.mountTime;
         i = super.entity.worldObj.getBlock(MathHelper.floor_double(super.entity.posX), MathHelper.floor_double(super.entity.posY + (double)super.entity.riddenByEntity.height), MathHelper.floor_double(super.entity.posZ)).getMaterial();
         if(i == Material.air || i.isLiquid()) {
            super.entity.motionY = 0.05D;
         }

         if(this.mountTime >= 70 || super.entity.riddenByEntity.isCollidedVertically && !super.entity.riddenByEntity.onGround) {
            super.entity.setDead();
         }
      } else {
         i = super.entity.worldObj.getBlock(MathHelper.floor_double(super.entity.posX), MathHelper.floor_double(super.entity.posY), MathHelper.floor_double(super.entity.posZ)).getMaterial();
         if(i == Material.fire) {
            super.entity.worldObj.setBlockToAir(MathHelper.floor_double(super.entity.posX), MathHelper.floor_double(super.entity.posY), MathHelper.floor_double(super.entity.posZ));
            super.entity.setDead();
         }
      }

      for(int var2 = 0; var2 < 1 + super.entity.getlvl(); ++var2) {
         super.entity.worldObj.spawnParticle("splash", super.entity.posX + (double)this.rand.nextFloat() - 0.5D, super.entity.posY + (double)this.rand.nextFloat() - 0.5D, super.entity.posZ + (double)this.rand.nextFloat() - 0.5D, 0.0D, -1.0D, 0.0D);
      }

   }

   public float getSize() {
      return super.entity.riddenByEntity != null?super.entity.riddenByEntity.width + super.entity.riddenByEntity.height:1.5F;
   }

   public boolean canBounce() {
      return false;
   }

   public void onSpawn() {
      super.entity.worldObj.playSoundEffect((double)((int)super.entity.posX), (double)((int)super.entity.posY), (double)((int)super.entity.posZ), "chocolatequest:bubble", 4.0F, (1.0F + (super.entity.worldObj.rand.nextFloat() - super.entity.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
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
