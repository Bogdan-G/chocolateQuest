package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.gui.InventoryBag;
import com.chocolate.chocolateQuest.magic.SpellBase;
import net.minecraft.item.ItemStack;

class CoolDownTracker {

   public SpellBase castingSpell;
   SpellBase[] spells;
   int[] cooldowns;


   public CoolDownTracker(ItemStack is) {
      ItemStack[] ammo = InventoryBag.getCargo(is);
      this.spells = new SpellBase[ammo.length];
      this.cooldowns = new int[ammo.length];

      for(int i = 0; i < ammo.length; ++i) {
         if(ammo[i] != null) {
            this.spells[i] = SpellBase.getSpellByID(ammo[i].getItemDamage());
         }
      }

   }

   public void onUpdate() {
      for(int i = 0; i < this.cooldowns.length; ++i) {
         if(this.cooldowns[i] > 0) {
            --this.cooldowns[i];
         }
      }

   }

   public void increaseAllCooldowns(int ammount) {
      for(int i = 0; i < this.cooldowns.length; ++i) {
         this.cooldowns[i] += ammount;
      }

   }
}
