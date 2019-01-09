package com.chocolate.chocolateQuest.magic;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

public class EnchantmentMagicDefense extends EnchantmentProtection {

   public EnchantmentMagicDefense(int par1, int par2) {
      super(par1, par2, 0);
      this.setName("magicResist");
   }

   public int calcModifierDamage(int level, DamageSource ds) {
      if(!ds.isMagicDamage()) {
         return 0;
      } else {
         float TYPE_MODIFIER = 1.5F;
         if(level == 0) {
            return 0;
         } else {
            float damageReduction = (float)MathHelper.floor_float((float)(6 + level * level) * TYPE_MODIFIER / 3.0F);
            return (int)damageReduction;
         }
      }
   }

   public boolean canApply(ItemStack stack) {
      return false;
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack) {
      return false;
   }

   public boolean canApplyTogether(Enchantment enchantment) {
      return enchantment != Enchantment.protection && enchantment != Enchantment.projectileProtection;
   }

   public boolean isAllowedOnBooks() {
      return false;
   }

   public String getTranslatedName(int par1) {
      return StatCollector.translateToLocal("enchantment.magicDefense.name") + " " + StatCollector.translateToLocal("enchantment.level." + par1);
   }

   public int getMaxLevel() {
      return 4;
   }
}
