package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileBase;
import com.chocolate.chocolateQuest.particles.EffectManager;
import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;

public class ProjectileFireFalling extends ProjectileBase {

   Random rand = new Random();


   public ProjectileFireFalling(EntityBaseBall entity) {
      super(entity);
   }

   public int getTextureIndex() {
      int i = (int)((double)super.entity.ticksExisted * 0.2D % 8.0D);
      if(i >= 4) {
         i = 7 - i;
      }

      return (double)super.entity.ticksExisted * 0.2D % 2.0D == 0.0D?242:243;
   }

   public void onImpact(MovingObjectPosition mop) {
      if(!super.entity.worldObj.isRemote) {
         Entity e = mop.entityHit;
         if(e != null) {
            if(e instanceof EntityLivingBase) {
               ;
            }
         } else {
            if(super.entity.getlvl() >= 1 && super.entity.worldObj.isAirBlock(mop.blockX, mop.blockY + 1, mop.blockZ) && super.entity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
               super.entity.worldObj.setBlock(mop.blockX, mop.blockY + 1, mop.blockZ, Blocks.fire);
            }

            super.entity.setDead();
         }
      }

   }

   public float getGravityVelocity() {
      return 0.03F;
   }

   public void onUpdateInAir() {
      super.entity.motionX *= 0.8D;
      super.entity.motionZ *= 0.8D;
      float x = (float)Math.sin(Math.toRadians((double)super.entity.rotationYaw));
      float z = (float)Math.cos(Math.toRadians((double)super.entity.rotationYaw));
      double y = -Math.sin(Math.toRadians((double)super.entity.rotationPitch));
      if(super.entity.shootingEntity != null) {
         byte dist = 2;
         List list = super.entity.worldObj.getEntitiesWithinAABBExcludingEntity(super.entity.shootingEntity, super.entity.boundingBox.expand((double)dist, (double)dist, (double)dist));
         Iterator i$ = list.iterator();

         while(i$.hasNext()) {
            Entity e = (Entity)i$.next();
            if(e instanceof EntityLivingBase) {
               e.setFire(4);
               e.attackEntityFrom(HelperDamageSource.causeFireProjectileDamage(super.entity, super.entity.shootingEntity), 1.0F);
            }
         }
      }

      if(super.entity.worldObj.isRemote) {
         EffectManager.spawnParticle(3, super.entity.worldObj, super.entity.posX, super.entity.posY + 1.0D, super.entity.posZ, super.entity.motionX + ((double)this.rand.nextFloat() - 0.5D) / 8.0D, super.entity.motionY + ((double)this.rand.nextFloat() - 0.5D) / 8.0D + 0.4D, super.entity.motionZ + ((double)this.rand.nextFloat() - 0.5D) / 8.0D);
      }

   }

   public float getSize() {
      return 1.2F;
   }

   public boolean canBounce() {
      return false;
   }

   public boolean longRange() {
      return false;
   }
}
