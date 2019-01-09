package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileBase;
import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import java.util.Random;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;

public class ProjectileVapiricBall extends ProjectileBase {

   static final float DAMAGE = 0.8F;
   Random rand = new Random();


   public ProjectileVapiricBall(EntityBaseBall entity) {
      super(entity);
   }

   public int getTextureIndex() {
      return 228;
   }

   public void onImpact(MovingObjectPosition mop) {
      if(!super.entity.worldObj.isRemote) {
         if(mop.entityHit != null) {
            if(mop.entityHit instanceof EntityLivingBase && !mop.entityHit.isEntityEqual(super.entity.shootingEntity) && mop.entityHit != super.entity.shootingEntity.riddenByEntity && mop.entityHit != super.entity.shootingEntity.ridingEntity) {
               float damage = 0.8F * super.entity.getDamageMultiplier();
               damage = super.entity.getElement().onHitEntity(super.entity.getThrower(), mop.entityHit, damage);
               if(mop.entityHit.attackEntityFrom(HelperDamageSource.causeProjectileMagicDamage(super.entity, super.entity.getThrower()), damage)) {
                  if(super.entity.getThrower() != null && !super.entity.worldObj.isRemote) {
                     EntityBaseBall ball = new EntityBaseBall(super.entity.worldObj, super.entity.getThrower(), 9, 1 + super.entity.getlvl() / 2);
                     ball.setPosition(super.entity.posX, super.entity.posY, super.entity.posZ);
                     super.entity.worldObj.spawnEntityInWorld(ball);
                  }

                  super.entity.setDead();
               }
            }
         } else {
            super.entity.setDead();
         }
      }

   }

   public void onUpdateInAir() {
      for(int i = 0; i < 1 + super.entity.getlvl(); ++i) {
         super.entity.worldObj.spawnParticle("portal", super.entity.posX + (double)this.rand.nextFloat() - 0.5D, super.entity.posY + (double)this.rand.nextFloat() - 0.5D, super.entity.posZ + (double)this.rand.nextFloat() - 0.5D, (double)(this.rand.nextFloat() - 0.5F), (double)(this.rand.nextFloat() - 0.5F), (double)(this.rand.nextFloat() - 0.5F));
      }

   }

   public float getSize() {
      return 0.5F;
   }

   public boolean canBounce() {
      return false;
   }

   public void onSpawn() {
      super.entity.worldObj.playSoundEffect((double)((int)super.entity.posX), (double)((int)super.entity.posY), (double)((int)super.entity.posZ), "random.bow", 4.0F, (1.0F + (super.entity.worldObj.rand.nextFloat() - super.entity.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
   }
}
