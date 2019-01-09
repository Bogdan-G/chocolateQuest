package com.chocolate.chocolateQuest.entity.handHelper;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.handHelper.HandRanged;
import com.chocolate.chocolateQuest.entity.projectile.EntityHookShoot;
import com.chocolate.chocolateQuest.items.ItemHookShoot;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class HandHook extends HandRanged {

   EntityHookShoot web;
   ItemHookShoot hook;


   public HandHook(EntityHumanBase owner, ItemStack itemStack) {
      super(owner, itemStack);
      if(itemStack.getItem() == ChocolateQuest.hookSword) {
         this.hook = (ItemHookShoot)ChocolateQuest.manualShoot;
      } else {
         this.hook = (ItemHookShoot)itemStack.getItem();
      }

      super.isMeleWeapon = true;
   }

   public void doRangeAttack(Entity target, float f) {
      if(this.web == null) {
         boolean pos = false;
         if(this.web != null) {
            this.web.setDead();
            this.web = null;
         }

         this.web = new EntityHookShoot(super.owner.worldObj, super.owner, this.hook.getHookType());
         this.web.setThrowableHeading(target.posX - super.owner.posX, target.posY - super.owner.posY, target.posZ - super.owner.posZ, 1.0F, 0.0F);
         super.owner.worldObj.spawnEntityInWorld(this.web);
      }

   }

   public void onUpdate() {
      if(this.web != null) {
         if(this.web.isDead) {
            this.web = null;
         } else if(this.web.isReeling()) {
            boolean setHookDead = false;
            double webDist = super.owner.getDistanceSqToEntity(this.web);
            float width = super.owner.width + 1.0F;
            if(this.web.hookedEntity == null && super.owner.getAttackTarget() != null && webDist < super.owner.getDistanceSqToEntity(super.owner.getAttackTarget())) {
               setHookDead = true;
            }

            if(webDist < (double)(width * width)) {
               setHookDead = true;
            }

            if(this.web.ticksExisted > 40 || setHookDead) {
               this.web.setDead();
               this.web = null;
            }
         }
      }

      super.onUpdate();
   }

   public double getDistanceToStopAdvancing() {
      return super.getDistanceToStopAdvancing();
   }
}
