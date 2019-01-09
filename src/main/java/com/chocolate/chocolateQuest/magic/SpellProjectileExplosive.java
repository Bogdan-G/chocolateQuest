package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.magic.SpellProjectile;
import net.minecraft.item.ItemStack;

public class SpellProjectileExplosive extends SpellProjectile {

   public int getCoolDown() {
      return 100;
   }

   public int getType() {
      return 106;
   }

   public float getCost(ItemStack itemstack) {
      return 20.0F;
   }
}
