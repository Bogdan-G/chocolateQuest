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

public class ProjectileMagicArea extends ProjectileMagic {

   final float DAMAGE = 0.7F;
   int lifeTime = 10;
   int deathTime = 100;
   Random rand = new Random();


   public ProjectileMagicArea(EntityBaseBall entity) {
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

      double dist = (double)((float)this.lifeTime / 10.0F);
      AxisAlignedBB var3 = super.entity.boundingBox.expand(dist, 0.1D, dist);
      List list = super.entity.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, var3);
      Iterator x = list.iterator();

      while(x.hasNext()) {
         Entity e = (Entity)x.next();
         if(e instanceof EntityLivingBase && e != super.entity.getThrower() && (int)e.getDistanceToEntity(super.entity) == this.lifeTime / 10 && e.onGround && !super.entity.worldObj.isRemote) {
            Elements z = super.entity.getElement();
            float damage = 0.7F * super.entity.getDamageMultiplier();
            z.attackWithElementProjectile((EntityLivingBase)e, super.entity.getThrower(), super.entity, damage);
         }
      }

      if(super.entity.worldObj.isRemote) {
         double var12 = Math.sin((double)((float)this.lifeTime / 10.0F));
         double var13 = Math.cos((double)((float)this.lifeTime / 10.0F));
         double step = 0.39269908169872414D;

         for(int i = 0; i < 16; ++i) {
            var12 = Math.sin((double)((float)this.lifeTime / 10.0F) + step * (double)i) * (double)((float)this.lifeTime / 10.0F);
            var13 = Math.cos((double)((float)this.lifeTime / 10.0F) + step * (double)i) * (double)((float)this.lifeTime / 10.0F);
            EffectManager.spawnElementParticle(0, super.entity.worldObj, super.entity.posX + var12, super.entity.posY - 0.5D + (double)this.rand.nextFloat() - 0.5D, super.entity.posZ + var13, 0.0D, 0.1D, 0.0D, super.entity.getElement());
         }
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
      this.deathTime = 40 + super.entity.getlvl() * 20;
      super.entity.posY = super.entity.shootingEntity.boundingBox.minY + 0.5D;
      super.entity.setInmuneToFire(true);
      super.entity.motionX = super.entity.motionZ = super.entity.motionY = 0.0D;
   }

   public boolean longRange() {
      return false;
   }
}
