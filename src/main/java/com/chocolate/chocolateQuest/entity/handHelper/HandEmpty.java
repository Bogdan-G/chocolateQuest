package com.chocolate.chocolateQuest.entity.handHelper;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.handHelper.HandHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class HandEmpty extends HandHelper {

   public HandEmpty(EntityHumanBase owner, ItemStack itemStack) {
      super(owner, itemStack);
   }

   public void onUpdate() {}

   public void attackEntity(Entity entity) {}

   public boolean attackWithRange(Entity target, float f) {
      return false;
   }
}
