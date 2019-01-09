package com.chocolate.chocolateQuest.entity.handHelper;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.handHelper.HandRanged;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

public class HandFireChange extends HandRanged {

   public HandFireChange(EntityHumanBase owner, ItemStack itemStack) {
      super(owner, itemStack);
      super.range = 4096.0F;
      super.aimDelay = 3;
      super.isMeleWeapon = false;
   }

   public void doRangeAttack(Entity target, float f) {
      Vec3 vec = super.owner.getLookVec();
      EntitySmallFireball arrow = new EntitySmallFireball(super.owner.worldObj, super.owner, vec.xCoord, vec.yCoord, vec.zCoord);
      arrow.setPosition(super.owner.posX, super.owner.posY + (double)super.owner.getEyeHeight(), super.owner.posZ);
      arrow.motionX = vec.xCoord;
      arrow.motionY = vec.yCoord;
      arrow.motionZ = vec.zCoord;
      arrow.accelerationX = vec.xCoord * 0.01D;
      arrow.accelerationY = vec.yCoord * 0.01D;
      arrow.accelerationZ = vec.zCoord * 0.01D;
      super.owner.worldObj.spawnEntityInWorld(arrow);
   }

   public double getDistanceToStopAdvancing() {
      return this.getRange();
   }

   public double getRange() {
      return (double)super.range;
   }

   public int getAimTime(Entity target) {
      return super.rangedWeapon != null?super.rangedWeapon.startAiming(super.currentItem, super.owner, target):super.owner.getAttackSpeed();
   }
}
