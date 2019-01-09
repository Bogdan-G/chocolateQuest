package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileBase;
import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;

public class ProjectileFireBall extends ProjectileBase {

   Random rand = new Random();


   public ProjectileFireBall(EntityBaseBall entity) {
      super(entity);
   }

   public int getTextureIndex() {
      return (double)super.entity.ticksExisted * 0.33D % 2.0D == 0.0D?234:250;
   }

   public void onImpact(MovingObjectPosition mop) {
      if(!super.entity.worldObj.isRemote) {
         Entity e = mop.entityHit;
         if(e != null) {
            if(e instanceof EntityLivingBase && !e.isEntityEqual(super.entity.shootingEntity) && mop.entityHit != super.entity.shootingEntity.riddenByEntity && mop.entityHit != super.entity.shootingEntity.ridingEntity) {
               e.attackEntityFrom(HelperDamageSource.causeFireProjectileDamage(super.entity, super.entity.shootingEntity), 3.0F);
               e.setFire(8);
               super.entity.worldObj.playSoundEffect((double)((int)e.posX), (double)((int)e.posY), (double)((int)e.posZ), "fire.fire", 4.0F, (1.0F + (super.entity.worldObj.rand.nextFloat() - super.entity.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
               super.entity.setDead();
            }
         } else {
            super.entity.setDead();
         }
      }

   }

   public void onUpdateInAir() {
      if(super.entity.worldObj.isRemote) {
         for(int i = 0; i < 1 + super.entity.getlvl(); ++i) {
            super.entity.worldObj.spawnParticle("fire", super.entity.posX + (double)this.rand.nextFloat() - 0.5D, super.entity.posY + (double)this.rand.nextFloat() - 0.5D, super.entity.posZ + (double)this.rand.nextFloat() - 0.5D, (double)(this.rand.nextFloat() - 0.5F), (double)(this.rand.nextFloat() - 0.5F), (double)(this.rand.nextFloat() - 0.5F));
         }
      }

   }

   public float getSize() {
      return 0.5F;
   }

   public boolean canBounce() {
      return false;
   }
}
