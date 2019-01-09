package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileBase;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;

public class ProjectileBulletPistol extends ProjectileBase {

   public static final int BULLET_BASE_DAMAGE = 6;
   public static final int IRON = 0;
   public static final int GOLD = 1;
   public static final int MAGIC = 2;
   public static final int FIRE = 3;
   public static final int CANNON = 4;
   Random rand = new Random();


   public ProjectileBulletPistol(EntityBaseBall entity) {
      super(entity);
   }

   public int getTextureIndex() {
      return super.entity.getlvl() == 1?225:(super.entity.getlvl() == 2?226:(super.entity.getlvl() == 3?227:224));
   }

   public void onImpact(MovingObjectPosition mop) {
      if(!super.entity.worldObj.isRemote) {
         if(mop.entityHit != null) {
            if(mop.entityHit != super.entity.shootingEntity.riddenByEntity && mop.entityHit != super.entity.shootingEntity.ridingEntity) {
               float mat = (float)this.getBulletBaseDamage();
               byte bulletType = super.entity.getlvl();
               mat *= super.entity.damageMultiplier;
               if(bulletType == 1) {
                  mat += mat * 0.66F;
               }

               if(bulletType == 4) {
                  mat += 6.0F;
               }

               DamageSource ds;
               if(bulletType == 3) {
                  ds = Elements.fire.getDamageSourceIndirect(super.entity.getThrower(), super.entity);
               } else if(bulletType == 2) {
                  ds = Elements.magic.getDamageSourceIndirect(super.entity.getThrower(), super.entity);
               } else {
                  ds = Elements.blast.getDamageSourceIndirect(super.entity.getThrower(), super.entity);
               }

               ds.setProjectile();
               if(HelperDamageSource.attackEntityWithoutKnockBack(mop.entityHit, ds, mat)) {
                  if(bulletType != 4) {
                     super.entity.setDead();
                  }

                  if(mop.entityHit instanceof EntityLivingBase) {
                     ((EntityLivingBase)mop.entityHit).hurtResistantTime = 1;
                  }

                  mop.entityHit.motionX = mop.entityHit.motionZ = 0.0D;
               }
            }
         } else {
            Material mat1 = super.entity.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ).getMaterial();
            if(mat1 != Material.air && mat1 != Material.fire && mat1 != Material.plants && mat1 != Material.vine && mat1 != Material.web) {
               super.entity.setDead();
            }
         }
      }

   }

   protected int getBulletBaseDamage() {
      return 6;
   }

   public void onSpawn() {
      super.entity.worldObj.playSoundAtEntity(super.entity, "chocolateQuest:handgun", 0.2F, this.getBulletPitch());
   }

   public float getBulletPitch() {
      return 1.0F;
   }

   public void onUpdateInAir() {
      if(super.entity.worldObj.isRemote) {
         super.entity.worldObj.spawnParticle("smoke", super.entity.posX, super.entity.posY, super.entity.posZ, 0.0D, 0.0D, 0.0D);
      }

   }

   public float getSize() {
      return super.entity.getlvl() >= 4?0.4F:0.1F;
   }

   public boolean canBounce() {
      return false;
   }
}
