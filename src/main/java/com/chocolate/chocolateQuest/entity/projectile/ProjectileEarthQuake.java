package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileBase;
import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;

public class ProjectileEarthQuake extends ProjectileBase {

   int lifeTime = 60;
   Random rand = new Random();


   public ProjectileEarthQuake(EntityBaseBall entity) {
      super(entity);
   }

   public int getTextureIndex() {
      return -3;
   }

   public void onImpact(MovingObjectPosition par1MovingObjectPosition) {
      if(!super.entity.worldObj.isRemote && !(par1MovingObjectPosition.entityHit instanceof EntityLiving)) {
         super.entity.motionY = 0.0D;
      }

   }

   public void onUpdateInAir() {
      --this.lifeTime;
      if(this.lifeTime <= 0) {
         super.entity.setDead();
      }

      Block id = super.entity.worldObj.getBlock((int)super.entity.posX, (int)super.entity.posY - 1, (int)super.entity.posZ);
      if(id == null || id == Blocks.air) {
         id = Blocks.glass;
      }

      double dist = 1.0D;
      AxisAlignedBB var3 = super.entity.boundingBox.expand(dist, 2.0D, dist);
      List list = super.entity.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, var3);
      Iterator i = list.iterator();

      while(i.hasNext()) {
         Entity e = (Entity)i.next();
         if(e instanceof EntityLivingBase && e != super.entity.getThrower() && !super.entity.worldObj.isRemote && e.onGround) {
            e.motionY = 0.3D;
            e.attackEntityFrom(HelperDamageSource.causeProjectileMagicDamage(super.entity, super.entity.getThrower()), 1.0F);
         }
      }

      if(super.entity.worldObj.isRemote) {
         for(int var8 = 0; var8 < 8; ++var8) {
            super.entity.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(id) + "_" + 0, super.entity.posX + (double)this.rand.nextFloat() - 0.5D, super.entity.posY + (double)this.rand.nextFloat() - 0.5D, super.entity.posZ + (double)this.rand.nextFloat() - 0.5D, (double)(this.rand.nextFloat() - 0.5F), (double)this.rand.nextFloat(), (double)(this.rand.nextFloat() - 0.5F));
         }
      }

   }

   public float getSize() {
      return 1.5F;
   }

   public boolean canBounce() {
      return false;
   }

   public void onSpawn() {
      --super.entity.posY;
      super.entity.motionX /= 2.5D;
      super.entity.motionY = -1.0D;
      super.entity.motionZ /= 2.5D;
      super.entity.setInmuneToFire(true);
   }

   public boolean longRange() {
      return false;
   }
}
