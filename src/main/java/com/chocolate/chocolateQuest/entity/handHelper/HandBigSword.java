package com.chocolate.chocolateQuest.entity.handHelper;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.handHelper.HandCQBlade;
import net.minecraft.item.ItemStack;

public class HandBigSword extends HandCQBlade {

   public HandBigSword(EntityHumanBase owner, ItemStack itemStack) {
      super(owner, itemStack);
   }

   public double getAttackRangeBonus() {
      return 0.2D;
   }
}
