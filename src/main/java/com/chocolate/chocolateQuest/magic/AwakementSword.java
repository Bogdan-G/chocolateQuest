package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.items.swords.ItemSwordAndShieldBase;
import com.chocolate.chocolateQuest.magic.Awakements;
import net.minecraft.item.ItemStack;

public class AwakementSword extends Awakements {

   public AwakementSword(String name, int icon) {
      super(name, icon);
   }

   public boolean canBeUsedOnItem(ItemStack is) {
      return is.getItem() instanceof ItemSwordAndShieldBase;
   }
}
