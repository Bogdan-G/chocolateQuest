package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileMagic;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.particles.EffectManager;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;

public class ProjectileMagicStormProjectile extends ProjectileMagic {

   static final float DAMAGE = 0.75F;
   public double x;
   public double y;
   public double z = 0.0D;
   boolean isFirstTick = true;


   public ProjectileMagicStormProjectile(EntityBaseBall entity) {
      super(entity);
   }

   public int getTextureIndex() {
      return super.type == 2?-2:super.getTextureIndex();
   }

   public void onUpdateInAir() {
      if(this.isFirstTick) {
         this.isFirstTick = false;
         this.x = super.entity.posX;
         this.y = super.entity.posY;
         this.z = super.entity.posZ;
      }

      super.onUpdateInAir();
      super.entity.motionY -= 0.01D;
   }

   public void onDead() {
      if(super.entity.worldObj.isRemote) {
         Random dist = super.entity.getRNG();
         float desp = 0.3F;

         for(int var3 = 0; var3 < 5; ++var3) {
            EffectManager.spawnElementParticle(0, super.entity.worldObj, super.entity.posX, super.entity.posY + (double)dist.nextFloat() - 0.5D, super.entity.posZ, (double)((dist.nextFloat() - 0.5F) * desp), 0.10000000149011612D, (double)((dist.nextFloat() - 0.5F) * desp), super.entity.getElement());
         }
      } else {
         double var9 = 2.0D;
         AxisAlignedBB var10 = super.entity.boundingBox.expand(var9, 0.0D, var9);
         List list = super.entity.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, var10);
         Iterator i$ = list.iterator();

         while(i$.hasNext()) {
            Entity e = (Entity)i$.next();
            if(e instanceof EntityLivingBase && e != super.entity.getThrower()) {
               Elements element = super.entity.getElement();
               float damage = 0.75F * super.entity.getDamageMultiplier();
               element.attackWithElementProjectile((EntityLivingBase)e, super.entity.getThrower(), super.entity, damage, false);
            }
         }
      }

   }

   public float getSize() {
      return 0.8F;
   }

   public void onSpawn() {}

   public boolean hasPenetration() {
      return false;
   }
}
