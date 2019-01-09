package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileBase;
import com.chocolate.chocolateQuest.particles.EffectManager;
import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition;

public class ProjectilePoisonBall extends ProjectileBase {

   int particleEffect = 4;


   public ProjectilePoisonBall(EntityBaseBall entity) {
      super(entity);
   }

   public int getTextureIndex() {
      return 246;
   }

   public void onImpact(MovingObjectPosition par1MovingObjectPosition) {
      if(!super.entity.worldObj.isRemote) {
         Entity e = par1MovingObjectPosition.entityHit;
         if(e != null) {
            if(e instanceof EntityLivingBase && e != super.entity.shootingEntity) {
               if(e instanceof EntityPlayer && ((EntityPlayer)e).capabilities.disableDamage) {
                  return;
               }

               e.attackEntityFrom(HelperDamageSource.causeProjectilePhysicalDamage(super.entity, super.entity.getThrower()), 4.0F);
               ((EntityLivingBase)e).addPotionEffect(new PotionEffect(Potion.poison.id, 120, 1));
            }
         } else {
            super.entity.setDead();
         }
      }

   }

   public void onUpdateInAir() {
      if(super.entity.worldObj.isRemote) {
         EffectManager.spawnParticle(2, super.entity.worldObj, super.entity.posX, super.entity.posY + 1.0D, super.entity.posZ, 0.0D, 0.4D, 0.0D);
      }

      super.onUpdateInAir();
   }

   public float getGravityVelocity() {
      return 0.0F;
   }

   public float getSize() {
      return 0.8F;
   }

   public boolean longRange() {
      return false;
   }
}
