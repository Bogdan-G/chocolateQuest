package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.items.swords.ItemBaseSpear;
import com.chocolate.chocolateQuest.magic.Awakements;
import net.minecraft.item.ItemStack;

public class AwakementSpear extends Awakements {

   public AwakementSpear(String name, int icon) {
      super(name, icon);
   }

   public boolean canBeUsedOnItem(ItemStack is) {
      return is.getItem() instanceof ItemBaseSpear;
   }
}
