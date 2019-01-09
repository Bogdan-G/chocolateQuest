package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.items.ItemArmorBase;
import com.chocolate.chocolateQuest.magic.Awakements;
import com.chocolate.chocolateQuest.misc.EnumEnchantType;
import net.minecraft.item.ItemStack;

public class AwakementArmor extends Awakements {

   public AwakementArmor(String name, int icon) {
      super(name, icon);
   }

   public boolean canBeUsedOnItem(ItemStack is) {
      return is.getItem() instanceof ItemArmorBase?((ItemArmorBase)is.getItem()).isEpic():false;
   }

   public boolean canBeAddedByNPC(int type) {
      return type == EnumEnchantType.TAILOR.ordinal();
   }

   public int getLevelCost() {
      return 5;
   }

   public int getMaxLevel() {
      return 1;
   }
}
