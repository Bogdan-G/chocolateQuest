package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileMagic;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.particles.EffectManager;
import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;

public class ProjectileMagicExplosive extends ProjectileMagic {

   final float DAMAGE = 1.0F;


   public ProjectileMagicExplosive(EntityBaseBall entity) {
      super(entity);
   }

   public int getTextureIndex() {
      return 247 + super.type - (super.entity.ticksExisted / 4 % 2 == 0?0:16);
   }

   public float getSize() {
      return 0.9F;
   }

   public float getGravityVelocity() {
      return 0.12F;
   }

   public boolean spawnParticles() {
      return true;
   }

   public boolean hasPenetration() {
      return false;
   }

   public void onDead() {
      byte expansion = super.entity.getlvl();
      if(super.entity.worldObj.isRemote) {
         Random dist = super.entity.getRNG();
         float desp = 0.5F + (float)(expansion / 4);
         double var3 = super.entity.prevPosX;
         double i$ = super.entity.prevPosY;
         double element = super.entity.prevPosZ;

         for(int damage = 0; damage < 25 + expansion * 5; ++damage) {
            EffectManager.spawnElementParticle(0, super.entity.worldObj, var3, i$, element, (double)((dist.nextFloat() - 0.5F) * desp), (double)((dist.nextFloat() - 0.5F) * desp), (double)((dist.nextFloat() - 0.5F) * desp), super.entity.getElement());
         }

         String var15 = "largeexplode";
         if(expansion > 2) {
            var15 = "hugeexplosion";
         }

         super.entity.worldObj.spawnParticle(var15, var3, i$, element, 0.0D, 0.0D, 0.0D);
      } else {
         double var11 = (double)(1.0F + (float)(expansion / 2));
         AxisAlignedBB var12 = super.entity.boundingBox.expand(var11, var11 / 2.0D, var11);
         List list = super.entity.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, var12);
         Iterator var13 = list.iterator();

         while(var13.hasNext()) {
            Entity e = (Entity)var13.next();
            if(e instanceof EntityLivingBase && e != super.entity.getThrower()) {
               Elements var14 = super.entity.getElement();
               DamageSource ds = var14.getDamageSource();
               float var16 = 1.0F * super.entity.getDamageMultiplier();
               var16 = var14.onHitEntity(super.entity.getThrower(), e, var16);
               HelperDamageSource.attackEntityWithoutKnockBack(e, ds, var16);
            }
         }
      }

   }
}
