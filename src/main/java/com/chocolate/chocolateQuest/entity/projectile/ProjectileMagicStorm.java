package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileBase;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.particles.EffectManager;
import java.util.Random;
import net.minecraft.util.MovingObjectPosition;

public class ProjectileMagicStorm extends ProjectileBase {

   int deathTime = 300;
   Random rand = new Random();


   public ProjectileMagicStorm(EntityBaseBall entity) {
      super(entity);
   }

   public int getTextureIndex() {
      return -3;
   }

   public void onImpact(MovingObjectPosition par1MovingObjectPosition) {}

   public void onUpdateInAir() {
      super.entity.motionX = super.entity.motionZ = super.entity.motionY = 0.0D;
      int level = super.entity.getlvl() + 1;
      int dist = 10 + 5 * level;
      int height = 5 + dist / 2;
      double posX = super.entity.posX;
      double posY = super.entity.posY + (double)height;
      double posZ = super.entity.posZ;
      if(super.entity.worldObj.isRemote) {
         Elements i = super.entity.getElement();

         for(int ball = 0; ball < level; ++ball) {
            EffectManager.spawnParticle(5, super.entity.worldObj, posX + (double)((this.rand.nextFloat() - 0.5F) * (float)dist), posY + (double)((this.rand.nextFloat() - 0.5F) * 2.0F), posZ + (double)((this.rand.nextFloat() - 0.5F) * (float)dist), (double)i.getColorX(), (double)i.getColorY(), (double)i.getColorZ());
         }
      } else if(super.entity.ticksExisted >= this.deathTime) {
         super.entity.setDead();
      }

      for(int var12 = 0; var12 < level; ++var12) {
         if(!super.entity.worldObj.isRemote && super.entity.ticksExisted % 3 == 0) {
            EntityBaseBall var13 = new EntityBaseBall(super.entity.worldObj, super.entity.getThrower(), 105, 0, super.entity.getElement());
            var13.setDamageMultiplier(super.entity.getDamageMultiplier());
            var13.setPosition(posX + (double)((this.rand.nextFloat() - 0.5F) * (float)dist), posY, posZ + (double)((this.rand.nextFloat() - 0.5F) * (float)dist));
            var13.setThrowableHeading((double)(this.rand.nextFloat() / 10.0F), -1.0D, (double)(this.rand.nextFloat() / 10.0F), 1.0F, 1.0F);
            super.entity.worldObj.spawnEntityInWorld(var13);
         }
      }

   }

   public float getSize() {
      return 0.1F;
   }

   public void onSpawn() {
      super.entity.motionX = super.entity.motionZ = super.entity.motionY = 0.0D;
      this.deathTime = 300 + super.entity.getlvl() * 100;
      super.entity.posY = super.entity.shootingEntity.boundingBox.minY + 0.5D;
      super.entity.setInmuneToFire(true);
   }
}
