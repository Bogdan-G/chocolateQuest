package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.magic.SpellProjectile;
import net.minecraft.item.ItemStack;

public class SpellMagicPrison extends SpellProjectile {

   public int getType() {
      return 108;
   }

   public int getCoolDown() {
      return 80;
   }

   public int getCastingTime() {
      return 20;
   }

   public int getRange(ItemStack itemstack) {
      return 26;
   }

   public float getCost(ItemStack itemstack) {
      return 25.0F;
   }
}
