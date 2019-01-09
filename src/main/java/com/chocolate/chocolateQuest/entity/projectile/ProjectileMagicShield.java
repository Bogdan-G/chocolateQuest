package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileMagicAimed;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.particles.EffectManager;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;

public class ProjectileMagicShield extends ProjectileMagicAimed {

   final float DAMAGE;
   int deathTime;
   final int MAX_HEALTH;
   final int HEALTH_PER_LEVEL;


   public ProjectileMagicShield(EntityBaseBall entity) {
      super(entity);
      this.DAMAGE = 0.33F;
      this.deathTime = 0;
      this.MAX_HEALTH = 600;
      this.HEALTH_PER_LEVEL = 200;
   }

   public ProjectileMagicShield(EntityBaseBall entity, Entity aimedTo) {
      this(entity, aimedTo, 0);
   }

   public ProjectileMagicShield(EntityBaseBall entity, Entity aimedTo, int ticksToStartAim) {
      super(entity, aimedTo);
      this.DAMAGE = 0.33F;
      this.deathTime = 0;
      this.MAX_HEALTH = 600;
      this.HEALTH_PER_LEVEL = 200;
   }

   public void onUpdateInAir() {
      ++this.deathTime;
      super.entity.motionX = super.entity.motionZ = super.entity.motionY = 0.0D;
      if(super.aimedTo != null && super.entity.ticksExisted > super.ticksToStartAim && super.aimedTo != null && !super.entity.worldObj.isRemote) {
         int rand = super.entity.ticksExisted;
         double x = super.aimedTo.posX + Math.cos((double)((float)rand / 20.0F)) * 2.0D;
         double z = super.aimedTo.posZ + Math.sin((double)((float)rand / 20.0F)) * 2.0D;
         super.entity.setPosition(x, super.aimedTo.posY, z);
         double dist = 0.3D;
         AxisAlignedBB var3 = super.entity.boundingBox.expand(dist, dist, dist);
         List list = super.entity.worldObj.getEntitiesWithinAABB(Entity.class, var3);
         Iterator i$ = list.iterator();

         while(i$.hasNext()) {
            Entity et = (Entity)i$.next();
            if(et instanceof EntityLivingBase) {
               EntityLivingBase e = (EntityLivingBase)et;
               if(e != super.entity.getThrower()) {
                  if(super.entity.getThrower() != null && super.entity.getThrower().getTeam() != null && super.entity.getThrower().isOnSameTeam(e)) {
                     return;
                  }

                  Elements element = super.entity.getElement();
                  float damage = 0.33F * super.entity.getDamageMultiplier();
                  element.attackWithElementProjectile(e, super.entity.getThrower(), super.entity, damage);
                  this.deathTime += 30;
               }
            }
         }
      }

      if(this.deathTime > this.getMaxLifeTime()) {
         super.entity.setDead();
      }

      if(super.entity.worldObj.isRemote) {
         Random rand1 = super.entity.worldObj.rand;
         EffectManager.spawnElementParticle(0, super.entity.worldObj, super.entity.posX + (double)rand1.nextFloat() - 0.5D, super.entity.posY + (double)rand1.nextFloat() - 0.5D, super.entity.posZ + (double)rand1.nextFloat() - 0.5D, 0.0D, 0.0D, 0.0D, super.entity.getElement());
      }

   }

   public int getMaxLifeTime() {
      return 600 + 200 * super.entity.getlvl();
   }

   public void onImpact(MovingObjectPosition par1MovingObjectPosition) {}
}
