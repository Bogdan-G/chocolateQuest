package com.chocolate.chocolateQuest.entity.handHelper;

import com.chocolate.chocolateQuest.API.ICooldownTracker;
import com.chocolate.chocolateQuest.API.IRangedWeapon;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.handHelper.HandRanged;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class HandMagicCaster extends HandRanged {

   ICooldownTracker itemTracker;
   public Object cooldownTracker = null;


   public HandMagicCaster(EntityHumanBase owner, ItemStack itemStack) {
      super(owner, itemStack);
      if(itemStack.getItem() instanceof ICooldownTracker) {
         this.itemTracker = (ICooldownTracker)itemStack.getItem();
         this.cooldownTracker = this.itemTracker.getCooldownTracker(itemStack, owner);
      }

   }

   public void onUpdate() {
      if(!super.owner.worldObj.isRemote) {
         this.itemTracker.startTick(this.cooldownTracker);
      }

      super.onUpdate();
      if(this.cooldownTracker != null && !super.owner.worldObj.isRemote) {
         this.itemTracker.startTick(this.cooldownTracker);
         boolean aimTime = this.itemTracker.shouldStartCasting(super.currentItem, super.owner, this.isAiming());
         if(!this.isAiming()) {
            if(super.aimDelayTime <= 0 && aimTime) {
               super.owner.setAiming(this, true);
               super.aimingTime = this.getAimTime((Entity)null);
            }
         } else if(super.aimingTime < 0) {
            ((IRangedWeapon)super.currentItem.getItem()).shootFromEntity(super.owner, super.currentItem, super.owner.getHandAngle(this), (Entity)null);
            super.owner.setAiming(this, false);
            super.aimDelayTime = super.aimDelay;
         }
      }

   }
}
