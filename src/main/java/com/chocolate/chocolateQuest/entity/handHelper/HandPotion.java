package com.chocolate.chocolateQuest.entity.handHelper;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.handHelper.HandRanged;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.ItemStack;

public class HandPotion extends HandRanged {

   public HandPotion(EntityHumanBase owner, ItemStack itemStack) {
      super(owner, itemStack);
      super.range = 64.0F;
      super.aimDelay = 50;
      super.aimingTime = 30;
   }

   public void onUpdate() {
      super.onUpdate();
   }

   public void doRangeAttack(Entity target, float f) {
      if(!super.owner.worldObj.isRemote) {
         EntityPotion e = new EntityPotion(super.owner.worldObj, super.owner, super.currentItem);
         e.motionX *= 1.2D;
         e.motionZ *= 1.2D;
         super.owner.worldObj.spawnEntityInWorld(e);
         super.owner.swingHand(this);
      }

   }

   public double getRange() {
      return (double)super.range;
   }

   public ItemStack getItem() {
      return !super.owner.isAiming()?null:super.getItem();
   }
}
