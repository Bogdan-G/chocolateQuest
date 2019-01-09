package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileBase;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.particles.EffectManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;

public class ProjectileMagic extends ProjectileBase {

   public static final int PHYSIC = 0;
   public static final int MAGIC = 1;
   public static final int BLAST = 2;
   public static final int FIRE = 3;
   int type;
   int damageCounter = 0;


   public ProjectileMagic(EntityBaseBall entity) {
      super(entity);
      this.type = entity.getElement().ordinal();
   }

   public int getTextureIndex() {
      return this.type == 2?-1:247 + this.type - (super.entity.ticksExisted / 4 % 2 == 0?0:16);
   }

   public void onImpact(MovingObjectPosition par1MovingObjectPosition) {
      if(!super.entity.worldObj.isRemote) {
         Entity e = par1MovingObjectPosition.entityHit;
         if(e != null) {
            if(e instanceof EntityLivingBase && e != super.entity.shootingEntity) {
               if(e instanceof EntityPlayer && ((EntityPlayer)e).capabilities.disableDamage) {
                  return;
               }

               Elements element = super.entity.getElement();
               float damage = super.entity.getDamageMultiplier();
               if(element.attackWithElementProjectile((EntityLivingBase)e, super.entity.getThrower(), super.entity, damage)) {
                  ++this.damageCounter;
               }
            }
         } else {
            super.entity.setDead();
         }
      }

   }

   public void onUpdateInAir() {
      if(this.damageCounter > super.entity.getlvl() || this.damageCounter > 0 && !this.hasPenetration()) {
         super.entity.setDead();
      }

      if(this.spawnParticles() && super.entity.worldObj.isRemote) {
         EffectManager.spawnElementParticle(2, super.entity.worldObj, super.entity.posX, super.entity.posY, super.entity.posZ, 0.0D, 0.4D, 0.0D, super.entity.getElement());
      }

      super.onUpdateInAir();
   }

   public float getGravityVelocity() {
      return 0.0F;
   }

   public float getSize() {
      return 0.4F;
   }

   public void onSpawn() {
      super.entity.worldObj.playSoundEffect((double)((int)super.entity.posX), (double)((int)super.entity.posY), (double)((int)super.entity.posZ), super.entity.getElement().sound, 4.0F, (1.0F + (super.entity.worldObj.rand.nextFloat() - super.entity.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
   }

   public boolean spawnParticles() {
      return false;
   }

   public boolean hasPenetration() {
      return true;
   }
}
