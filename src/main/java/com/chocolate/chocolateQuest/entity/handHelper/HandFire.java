package com.chocolate.chocolateQuest.entity.handHelper;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.handHelper.HandHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class HandFire extends HandHelper {

   public HandFire(EntityHumanBase owner, ItemStack itemStack) {
      super(owner, itemStack);
   }

   public void onUpdate() {
      super.onUpdate();
   }

   public void attackEntity(Entity entity) {
      entity.setFire(8);
      super.attackEntity(entity);
   }
}
