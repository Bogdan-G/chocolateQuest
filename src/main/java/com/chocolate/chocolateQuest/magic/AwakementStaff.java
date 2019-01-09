package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.items.ItemStaffBase;
import com.chocolate.chocolateQuest.magic.Awakements;
import com.chocolate.chocolateQuest.misc.EnumEnchantType;
import net.minecraft.item.ItemStack;

public class AwakementStaff extends Awakements {

   public AwakementStaff(String name, int icon) {
      super(name, icon);
   }

   public boolean canBeUsedOnItem(ItemStack is) {
      return is.getItem() instanceof ItemStaffBase;
   }

   public int getLevelCost() {
      return 2;
   }

   public boolean canBeAddedByNPC(int type) {
      return type == EnumEnchantType.STAVES.ordinal();
   }

   public int getMaxLevel() {
      return 5;
   }
}
