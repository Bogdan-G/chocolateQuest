package com.chocolate.chocolateQuest.entity.handHelper;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.handHelper.HandHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class HandShield extends HandHelper {

   int blockElapsedTime = 0;


   public HandShield(EntityHumanBase owner, ItemStack itemStack) {
      super(owner, itemStack);
   }

   public boolean canBlock() {
      return true;
   }

   public void onUpdate() {
      if(super.owner.isDefending() && !super.owner.worldObj.isRemote && super.owner.getAttackTarget() == null) {
         if(this.blockElapsedTime > 200) {
            super.owner.setDefending(false);
            this.blockElapsedTime = 0;
         } else {
            ++this.blockElapsedTime;
         }
      }

   }

   public void attackEntity(Entity entity) {}

   public boolean attackWithRange(Entity target, float f) {
      return false;
   }
}
