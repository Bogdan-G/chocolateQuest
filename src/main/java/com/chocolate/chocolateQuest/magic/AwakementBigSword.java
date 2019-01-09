package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.items.swords.ItemBaseBroadSword;
import com.chocolate.chocolateQuest.magic.Awakements;
import net.minecraft.item.ItemStack;

public class AwakementBigSword extends Awakements {

   public AwakementBigSword(String name, int icon) {
      super(name, icon);
   }

   public boolean canBeUsedOnItem(ItemStack is) {
      return is.getItem() instanceof ItemBaseBroadSword;
   }
}
