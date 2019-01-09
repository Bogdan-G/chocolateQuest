package com.chocolate.chocolateQuest.entity.handHelper;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.handHelper.HandRanged;
import com.chocolate.chocolateQuest.items.gun.ItemGolemWeapon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class HandGolem extends HandRanged {

   public HandGolem(EntityHumanBase owner, ItemStack itemStack) {
      super(owner, itemStack);
   }

   public void doRangeAttack(Entity target, float f) {
      if(super.currentItem.getItem() instanceof ItemGolemWeapon) {
         ((ItemGolemWeapon)super.currentItem.getItem()).shootFromEntity(super.owner, super.currentItem, super.owner.getHandAngle(this), target);
      }

   }

   public void onUpdate() {
      if(this.isAiming() && !super.owner.worldObj.isRemote && super.owner.getAttackTarget() == null && super.owner.riddenByEntity == null) {
         super.owner.setAiming(this, false);
      }

      super.onUpdate();
   }

   public boolean attackWithRange(Entity target, float f) {
      return !(super.owner.riddenByEntity instanceof EntityPlayer)?super.attackWithRange(target, f):true;
   }

   public void attackEntity(Entity entity) {
      super.attackTime = 10;
   }

   public boolean isRanged() {
      return true;
   }

   public void onClick() {
      if(super.aimDelayTime <= 0) {
         if(super.currentItem.getItem() instanceof ItemGolemWeapon) {
            ((ItemGolemWeapon)super.currentItem.getItem()).shootFromEntity(super.owner, super.currentItem, super.owner.getHandAngle(this), (Entity)null);
         }

         super.aiming = true;
         super.aimingTime = super.owner.getAttackSpeed() + 20;
         super.aimDelayTime = ((ItemGolemWeapon)super.currentItem.getItem()).getCooldown(super.currentItem);
      }

   }

   public void onRelease() {}
}
