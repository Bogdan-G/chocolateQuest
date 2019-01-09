package com.chocolate.chocolateQuest.entity.handHelper;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.handHelper.HandRanged;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class HandSnowBall extends HandRanged {

   public HandSnowBall(EntityHumanBase owner, ItemStack itemStack) {
      super(owner, itemStack);
      super.range = 4096.0F;
      super.aimDelay = 4;
      super.isMeleWeapon = false;
   }

   public void doRangeAttack(Entity target, float f) {
      EntitySnowball arrow = new EntitySnowball(super.owner.worldObj, super.owner);
      arrow.setPosition(super.owner.posX, super.owner.posY + (double)super.owner.getEyeHeight(), super.owner.posZ);
      if(!super.owner.worldObj.isRemote) {
         float distFactor = f / 10.0F;
         double arrowMotionX = target.posX - super.owner.posX + target.motionX * (double)distFactor;
         double arrowMotionZ = target.posZ - super.owner.posZ + target.motionZ * (double)distFactor;
         double d2 = target.posY + (double)target.getEyeHeight() - 0.699999988079071D - arrow.posY;
         float dist = MathHelper.sqrt_float(f);
         float hFact = dist;
         if(dist < 16.0F) {
            hFact = dist * 0.28F;
         }

         super.owner.worldObj.playSoundAtEntity(super.owner, "random.bow", 1.0F, 1.0F / (super.owner.getRNG().nextFloat() * 0.4F + 0.8F));
         float damage = (float)super.owner.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue() * 2.0F;
         arrow.setThrowableHeading(arrowMotionX, d2 + (double)hFact, arrowMotionZ, 1.0F, super.owner.accuracy);
         arrow.motionX *= (double)Math.max(1.0F, hFact / 18.0F);
         arrow.motionZ *= (double)Math.max(1.0F, hFact / 18.0F);
         super.owner.worldObj.spawnEntityInWorld(arrow);
      }

   }

   public double getDistanceToStopAdvancing() {
      return this.getRange();
   }

   public double getRange() {
      return (double)super.range;
   }

   public int getAimTime(Entity target) {
      return 10;
   }
}
