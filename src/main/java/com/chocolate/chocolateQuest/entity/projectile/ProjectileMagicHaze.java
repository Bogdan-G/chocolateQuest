package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileMagic;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.particles.EffectManager;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;

public class ProjectileMagicHaze extends ProjectileMagic {

   final float DAMAGE = 0.25F;
   int lifeTime = 0;
   int deathTime = 200;
   Random rand = new Random();
   private int timeToStart = 30;


   public ProjectileMagicHaze(EntityBaseBall entity) {
      super(entity);
   }

   public int getTextureIndex() {
      return -3;
   }

   public void onImpact(MovingObjectPosition par1MovingObjectPosition) {}

   public void onUpdateInAir() {
      ++this.lifeTime;
      if(this.lifeTime >= this.deathTime) {
         super.entity.setDead();
      }

      double dist = 1.0D + (double)super.entity.getlvl() * 0.5D;
      AxisAlignedBB var3 = super.entity.boundingBox.expand(dist, 0.1D, dist);
      List list = super.entity.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, var3);
      Iterator width = list.iterator();

      while(width.hasNext()) {
         Entity e = (Entity)width.next();
         if(e instanceof EntityLivingBase && e != super.entity.getThrower() && this.lifeTime % 20 == 0 && e.onGround && !super.entity.worldObj.isRemote) {
            Elements iterations = super.entity.getElement();
            float i = 0.25F * super.entity.getDamageMultiplier();
            iterations.attackWithElementProjectile((EntityLivingBase)e, super.entity.getThrower(), super.entity, i);
         }
      }

      if(super.entity.worldObj.isRemote) {
         double var9 = dist * 0.6D;
         if(this.lifeTime > this.timeToStart) {
            int var10 = 3 + super.entity.getlvl();

            for(int var11 = 0; var11 < var10; ++var11) {
               EffectManager.spawnElementParticle(2, super.entity.worldObj, super.entity.posX + this.rand.nextGaussian() * var9, super.entity.posY + 0.3D, super.entity.posZ + this.rand.nextGaussian() * var9, 0.2D + (double)(this.rand.nextFloat() / 8.0F), 0.6D + (double)(this.rand.nextFloat() / 4.0F), 0.2D + (double)(this.rand.nextFloat() / 8.0F), super.entity.getElement());
            }
         }

         EffectManager.spawnElementParticle(0, super.entity.worldObj, super.entity.posX + this.rand.nextGaussian() * var9, super.entity.posY + 0.2D, super.entity.posZ + this.rand.nextGaussian() * var9, 0.0D, 0.1D + (double)(this.rand.nextFloat() / 8.0F), 0.0D, super.entity.getElement());
      }

      super.entity.motionX = super.entity.motionZ = super.entity.motionY = 0.0D;
      if(!super.entity.onGround) {
         if(super.entity.worldObj.getBlock((int)super.entity.posX, (int)super.entity.posY, (int)super.entity.posZ) != Blocks.air) {
            super.entity.onGround = true;
         }

         super.entity.motionY -= 0.1D;
      }

   }

   public float getSize() {
      return 0.1F;
   }

   public boolean canBounce() {
      return false;
   }

   public void onSpawn() {
      super.entity.posY = super.entity.shootingEntity.boundingBox.minY + 0.5D;
      super.entity.setInmuneToFire(true);
      super.entity.motionX = super.entity.motionZ = super.entity.motionY = 0.0D;
   }

   public boolean longRange() {
      return false;
   }
}
