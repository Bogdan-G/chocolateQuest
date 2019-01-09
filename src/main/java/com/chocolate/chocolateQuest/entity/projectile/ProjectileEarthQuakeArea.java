package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileBase;
import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;

public class ProjectileEarthQuakeArea extends ProjectileBase {

   int lifeTime = 10;
   int deathTime = 100;
   Random rand = new Random();


   public ProjectileEarthQuakeArea(EntityBaseBall entity) {
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
      AxisAlignedBB var3 = super.entity.boundingBox.expand(dist, 1.0D, dist);
      List list = super.entity.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, var3);
      Iterator x = list.iterator();

      while(x.hasNext()) {
         Entity e = (Entity)x.next();
         if(e instanceof EntityLivingBase && e != super.entity.getThrower() && (int)e.getDistanceToEntity(super.entity) == this.lifeTime / 10 && e.onGround && !super.entity.worldObj.isRemote) {
            e.attackEntityFrom(HelperDamageSource.causeProjectilePhysicalDamage(super.entity, super.entity.getThrower()), 1.0F);
         }
      }

      if(super.entity.worldObj.isRemote) {
         double var18 = Math.sin((double)((float)this.lifeTime / 10.0F));
         double z = Math.cos((double)((float)this.lifeTime / 10.0F));
         double step = 0.39269908169872414D;

         for(int i = 0; i < 16; ++i) {
            var18 = Math.sin((double)((float)this.lifeTime / 10.0F) + step * (double)i) * (double)((float)this.lifeTime / 10.0F);
            z = Math.cos((double)((float)this.lifeTime / 10.0F) + step * (double)i) * (double)((float)this.lifeTime / 10.0F);
            int blockX = MathHelper.floor_double(super.entity.posX + var18);
            int blockY = MathHelper.floor_double(super.entity.posY) - 1;
            int blockZ = MathHelper.floor_double(super.entity.posZ + z);
            Block id = super.entity.worldObj.getBlock(blockX, blockY, blockZ);
            int md = super.entity.worldObj.getBlockMetadata(blockX, blockY, blockZ);
            if(id == null || id == Blocks.air) {
               id = Blocks.glass;
            }

            for(int a = 0; a < 5; ++a) {
               super.entity.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(id) + "_" + md, super.entity.posX + var18, super.entity.posY - 0.5D + (double)this.rand.nextFloat() - 0.5D, super.entity.posZ + z, 0.0D, 1.0D, 0.0D);
            }
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
      this.deathTime = 50 + super.entity.getlvl() * 50;
      super.entity.posY = super.entity.shootingEntity.boundingBox.minY + 0.5D;
      super.entity.setInmuneToFire(true);
   }

   public boolean longRange() {
      return false;
   }
}
