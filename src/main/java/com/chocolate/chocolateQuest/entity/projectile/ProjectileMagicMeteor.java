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

public class ProjectileMagicMeteor extends ProjectileMagic {

   public ProjectileMagicMeteor(EntityBaseBall entity) {
      super(entity);
   }

   public int getTextureIndex() {
      return 247 + super.type - (super.entity.ticksExisted % 2 == 0?0:16);
   }

   public float getSize() {
      return 2.0F + (float)super.entity.getlvl();
   }

   public boolean hasPenetration() {
      return false;
   }

   public void onUpdateInAir() {
      if(super.damageCounter > super.entity.getlvl()) {
         super.entity.setDead();
      }

      if(super.entity.worldObj.isRemote) {
         EffectManager.spawnElementParticle(3, super.entity.worldObj, super.entity.prevPosX, super.entity.prevPosY + (double)super.entity.getlvl() * 0.5D, super.entity.prevPosZ, 0.0D, 0.0D, 0.0D, super.entity.getElement());
      }

      super.onUpdateInAir();
   }

   public void onDead() {
      byte expansion = super.entity.getlvl();
      if(super.entity.worldObj.isRemote) {
         Random dist = super.entity.getRNG();
         float desp = 0.5F + (float)(expansion / 3);
         double var3 = super.entity.prevPosX;
         double i$ = super.entity.prevPosY;
         double element = super.entity.prevPosZ;

         for(int damage = 0; damage < 25 + expansion * 15; ++damage) {
            EffectManager.spawnElementParticle(0, super.entity.worldObj, var3, i$, element, (double)((dist.nextFloat() - 0.5F) * desp), (double)((dist.nextFloat() - 0.5F) * desp), (double)((dist.nextFloat() - 0.5F) * desp), super.entity.getElement());
         }

         String var16 = "hugeexplosion";
         super.entity.worldObj.spawnParticle(var16, var3, i$, element, 0.0D, 0.0D, 0.0D);
      } else {
         super.entity.worldObj.playSoundEffect((double)((int)super.entity.posX), (double)((int)super.entity.posY), (double)((int)super.entity.posZ), "random.explode", 4.0F, (1.0F + (super.entity.worldObj.rand.nextFloat() - super.entity.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
         double var11 = (double)(2.0F + (float)expansion * 0.75F);
         AxisAlignedBB var12 = super.entity.boundingBox.expand(var11, var11 / 2.0D, var11);
         List list = super.entity.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, var12);
         Iterator var13 = list.iterator();

         while(var13.hasNext()) {
            Entity e = (Entity)var13.next();
            if(e instanceof EntityLivingBase && e != super.entity.getThrower()) {
               Elements var14 = super.entity.getElement();
               DamageSource ds = var14.getDamageSource();
               float var15 = super.entity.getDamageMultiplier();
               var15 = var14.onHitEntity(super.entity.getThrower(), e, var15);
               HelperDamageSource.attackEntityWithoutKnockBack(e, ds, var15);
            }
         }
      }

   }
}
