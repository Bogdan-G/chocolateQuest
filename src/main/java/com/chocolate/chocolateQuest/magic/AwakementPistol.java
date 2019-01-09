package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.items.gun.ItemGun;
import com.chocolate.chocolateQuest.magic.Awakements;
import com.chocolate.chocolateQuest.misc.EnumEnchantType;
import net.minecraft.item.ItemStack;

public class AwakementPistol extends Awakements {

   int maxLevel;


   public AwakementPistol(String name, int icon) {
      this(name, icon, 4);
   }

   public int getMaxLevel() {
      return this.maxLevel;
   }

   public AwakementPistol(String name, int icon, int maxLevel) {
      super(name, icon);
      this.maxLevel = 4;
      this.maxLevel = maxLevel;
   }

   public boolean canBeUsedOnItem(ItemStack is) {
      return is.getItem() instanceof ItemGun;
   }

   public boolean canBeAddedByNPC(int type) {
      return type == EnumEnchantType.GUNSMITH.ordinal();
   }
}
