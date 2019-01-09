package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.items.swords.ItemBaseDagger;
import com.chocolate.chocolateQuest.magic.Awakements;
import net.minecraft.item.ItemStack;

public class AwakementDagger extends Awakements {

   public AwakementDagger(String name, int icon) {
      super(name, icon);
   }

   public boolean canBeUsedOnItem(ItemStack is) {
      return is.getItem() instanceof ItemBaseDagger;
   }
}
