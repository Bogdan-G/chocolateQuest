package com.chocolate.chocolateQuest.entity.handHelper;

import com.chocolate.chocolateQuest.API.IRangedWeapon;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.handHelper.HandHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class HandRanged extends HandHelper {

   boolean aiming = false;
   boolean isMeleWeapon;
   protected float range;
   protected int aimDelay;
   protected int aimingTime;
   protected int aimDelayTime;
   IRangedWeapon rangedWeapon;


   public HandRanged(EntityHumanBase owner, ItemStack itemStack) {
      super(owner, itemStack);
      if(super.currentItem.getItem() instanceof IRangedWeapon) {
         this.rangedWeapon = (IRangedWeapon)super.currentItem.getItem();
         this.range = this.rangedWeapon.getRange(owner, itemStack);
         this.aimDelay = this.rangedWeapon.getCooldown(owner, itemStack);
         this.isMeleWeapon = this.rangedWeapon.isMeleeWeapon(owner, itemStack);
      } else {
         this.range = 4096.0F;
         this.aimDelay = 10;
         this.isMeleWeapon = false;
      }

   }

   public boolean attackWithRange(Entity target, float f) {
      if((double)f < this.getRange()) {
         if(this.isAiming()) {
            if(this.aimingTime <= 0) {
               this.doRangeAttack(target, f);
               super.owner.setAiming(this, false);
               this.aimDelayTime = this.aimDelay;
            }
         } else if(this.aimDelayTime <= 0) {
            int aimTime = this.getAimTime(target);
            if(aimTime >= 0) {
               super.owner.setAiming(this, true);
               this.aimingTime = aimTime;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public int getAimTime(Entity target) {
      return this.rangedWeapon != null?this.rangedWeapon.startAiming(super.currentItem, super.owner, target):super.owner.getAttackSpeed();
   }

   public void doRangeAttack(Entity target, float f) {
      if(super.currentItem.getItem() instanceof IRangedWeapon) {
         ((IRangedWeapon)super.currentItem.getItem()).shootFromEntity(super.owner, super.currentItem, super.owner.getHandAngle(this), target);
      } else {
         EntityArrow arrow = new EntityArrow(super.owner.worldObj, super.owner, 0.0F);
         arrow.setPosition(super.owner.posX, super.owner.posY + (double)super.owner.getEyeHeight(), super.owner.posZ);
         if(!super.owner.worldObj.isRemote) {
            if(target.posY < super.owner.posY) {
               arrow.setIsCritical(true);
            }

            float distFactor = f / 10.0F;
            double arrowMotionX = target.posX - super.owner.posX + target.motionX * (double)distFactor;
            double arrowMotionZ = target.posZ - super.owner.posZ + target.motionZ * (double)distFactor;
            double d2 = target.posY + (double)target.getEyeHeight() - 0.699999988079071D - arrow.posY;
            float dist = MathHelper.sqrt_float(f);
            float hFact = dist;
            if(dist < 16.0F) {
               hFact = dist * 0.38F;
            }

            super.owner.worldObj.playSoundAtEntity(super.owner, "random.bow", 1.0F, 1.0F / (super.owner.getRNG().nextFloat() * 0.4F + 0.8F));
            float damage = (float)super.owner.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue() * 2.0F;
            arrow.setThrowableHeading(arrowMotionX, d2 + (double)hFact, arrowMotionZ, 1.0F, super.owner.accuracy);
            arrow.setDamage((double)damage);
            arrow.motionX *= (double)Math.max(1.0F, hFact / 18.0F);
            arrow.motionZ *= (double)Math.max(1.0F, hFact / 18.0F);
            super.owner.worldObj.spawnEntityInWorld(arrow);
         }
      }

   }

   public void onUpdate() {
      if(this.isAiming() && this.rangedWeapon != null && this.rangedWeapon.shouldUpdate(super.owner)) {
         super.currentItem.getItem().onUpdate(super.currentItem, super.owner.worldObj, super.owner, super.owner.getHandAngle(this), false);
      }

      if(this.aimDelayTime > 0) {
         --this.aimDelayTime;
      }

      if(this.aimingTime >= -20) {
         --this.aimingTime;
      } else if(this.isAiming() && !super.owner.worldObj.isRemote) {
         super.owner.setAiming(this, false);
      }

      super.onUpdate();
   }

   public void setAiming(boolean aim) {
      this.aiming = aim;
      this.aimingTime = 0;
   }

   public boolean isAiming() {
      return this.aiming;
   }

   public boolean isRanged() {
      return true;
   }

   public double getDistanceToStopAdvancing() {
      return this.isMeleWeapon?super.getDistanceToStopAdvancing():this.getRange();
   }

   public double getMaxRangeForAttack() {
      return this.getRange();
   }

   public double getRange() {
      return this.rangedWeapon != null?(double)this.rangedWeapon.getRange(super.owner, super.currentItem):(double)this.range;
   }
}
