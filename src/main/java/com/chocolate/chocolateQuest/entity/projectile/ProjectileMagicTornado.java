package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileBase;
import com.chocolate.chocolateQuest.particles.EffectManager;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class ProjectileMagicTornado extends ProjectileBase {

   static final float DAMAGE = 0.25F;
   int maxLifeTime = 80;
   Random rand = new Random();


   public ProjectileMagicTornado(EntityBaseBall entity) {
      super(entity);
   }

   public int getTextureIndex() {
      return super.entity.ticksExisted % 2 == 0?235:236;
   }

   public void onImpact(MovingObjectPosition par1MovingObjectPosition) {
      if(!super.entity.worldObj.isRemote && par1MovingObjectPosition.entityHit instanceof EntityLivingBase) {
         ;
      }

   }

   public void onUpdateInAir() {
      if(super.entity.ticksExisted % 30 == 0) {
         super.entity.worldObj.playSoundAtEntity(super.entity, "chocolatequest:wind", 1.0F, 1.0F);
      }

      if(super.entity.ticksExisted >= this.maxLifeTime) {
         super.entity.setDead();
      }

      super.entity.motionX *= 0.9D;
      super.entity.motionY *= 0.9D;
      super.entity.motionZ *= 0.9D;
      double dist = (double)(5 + super.entity.getlvl());
      AxisAlignedBB var3 = super.entity.boundingBox.expand(dist - 1.0D, 2.0D + dist / 10.0D, dist - 1.0D);
      List list = super.entity.worldObj.getEntitiesWithinAABB(Entity.class, var3);
      Iterator id = list.iterator();

      while(id.hasNext()) {
         Entity i = (Entity)id.next();
         if(i != super.entity && i != super.entity.getThrower()) {
            Vec3 d = Vec3.createVectorHelper(super.entity.posX - i.posX, super.entity.posY - i.posY, super.entity.posZ - i.posZ);
            double distToEntity = Math.max(15.0D, (d.xCoord * d.xCoord + d.zCoord * d.zCoord) * 10.0D);
            d.normalize();
            double x = d.xCoord / distToEntity * dist / 5.0D;
            double y = d.yCoord / Math.max(15.0D, d.yCoord * d.yCoord * 10.0D);
            double z = d.zCoord / distToEntity * dist / 5.0D;
            if(this.rand.nextInt(10) == 0) {
               y += (dist - distToEntity) / 40.0D;
            }

            if(i instanceof EntityLivingBase) {
               EntityLivingBase var22 = (EntityLivingBase)i;
               ItemStack boots = var22.getEquipmentInSlot(1);
               boolean canBePushed = true;
               if(boots != null && boots.getItem() != null) {
                  ;
               }

               if(i.canBePushed() && canBePushed) {
                  i.addVelocity(x, y, z);
               }

               if(super.entity.getDistanceSqToEntity(i) <= 3.0D && !super.entity.worldObj.isRemote) {
                  float damage = 0.25F * super.entity.getDamageMultiplier();
                  super.entity.getElement().attackWithElementProjectile((EntityLivingBase)i, super.entity.getThrower(), super.entity, damage);
               }

               i.fallDistance = 0.0F;
            } else if(i instanceof EntityBaseBall && i != super.entity && ((EntityBaseBall)i).getType() == 10) {
               if(((EntityBaseBall)i).getThrower() != super.entity.getThrower()) {
                  if(!super.entity.worldObj.isRemote && this.rand.nextInt(30) == 0) {
                     ;
                  }

                  EntityLightningBolt lightning = new EntityLightningBolt(super.entity.worldObj, super.entity.posX, super.entity.posY - 1.0D, super.entity.posZ);
                  super.entity.worldObj.spawnEntityInWorld(lightning);
                  i.addVelocity(x, y, z);
               } else if(super.entity.ticksExisted > i.ticksExisted) {
                  super.entity.setDead();
               } else {
                  super.entity.ticksExisted -= i.ticksExisted / 2;
               }
            } else {
               i.addVelocity(x, y, z);
            }
         }
      }

      if(super.entity.worldObj.isRemote) {
         Block var20 = super.entity.worldObj.getBlock((int)super.entity.posX, (int)super.entity.posY - 2, (int)super.entity.posZ);
         if(var20 == Blocks.air) {
            var20 = Blocks.glass;
         }

         for(int var21 = 0; var21 < 4; ++var21) {
            EffectManager.spawnElementParticle(1, super.entity.worldObj, super.entity.posX + (double)this.rand.nextFloat() - 0.5D, super.entity.posY + (double)this.rand.nextFloat() - 0.5D, super.entity.posZ + (double)this.rand.nextFloat() - 0.5D, dist, 0.0D, 0.0D, super.entity.getElement());
            super.entity.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(var20) + "_" + 0, super.entity.posX + (double)this.rand.nextFloat() - 0.5D, super.entity.posY + (double)this.rand.nextFloat() - 0.5D, super.entity.posZ + (double)this.rand.nextFloat() - 0.5D, (double)(this.rand.nextFloat() - 0.5F), (double)(2.0F + this.rand.nextFloat() * 2.0F), (double)(this.rand.nextFloat() - 0.5F));
         }
      }

   }

   public float getSize() {
      return 0.6F;
   }

   public float getSizeBB() {
      return 0.01F;
   }

   public boolean canBounce() {
      return false;
   }

   public void onSpawn() {
      super.entity.setInmuneToFire(false);
   }

   public boolean longRange() {
      return false;
   }
}
