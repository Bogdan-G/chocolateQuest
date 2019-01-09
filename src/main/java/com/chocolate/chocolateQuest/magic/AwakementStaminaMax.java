package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.magic.AwakementArmor;
import com.chocolate.chocolateQuest.misc.EnumEnchantType;

public class AwakementStaminaMax extends AwakementArmor {

   public AwakementStaminaMax(String name, int icon) {
      super(name, icon);
   }

   public boolean canBeAddedByNPC(int type) {
      return super.canBeAddedByNPC(type) || type == EnumEnchantType.STAVES.ordinal();
   }

   public int getLevelCost() {
      return 2;
   }

   public int getMaxLevel() {
      return 5;
   }
}
