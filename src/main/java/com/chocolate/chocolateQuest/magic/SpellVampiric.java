package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.magic.SpellProjectile;
import net.minecraft.item.ItemStack;

public class SpellVampiric extends SpellProjectile {

   public int getType() {
      return 8;
   }

   public int getCoolDown() {
      return 55;
   }

   public int getRange(ItemStack itemstack) {
      return 16;
   }
}
