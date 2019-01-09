package com.chocolate.chocolateQuest.entity.handHelper;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.handHelper.HandCQBlade;
import net.minecraft.item.ItemStack;

public class HandSpear extends HandCQBlade {

   public HandSpear(EntityHumanBase owner, ItemStack itemStack) {
      super(owner, itemStack);
   }

   public double getAttackRangeBonus() {
      return 1.0D;
   }
}
