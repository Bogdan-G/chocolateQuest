package com.chocolate.chocolateQuest.gui.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotLockedToClass extends Slot {

   Item item;
   int damage;
   boolean checkDamage;


   public SlotLockedToClass(IInventory par1iInventory, int par2, int par3, int par4, Item item) {
      super(par1iInventory, par2, par3, par4);
      this.damage = 0;
      this.checkDamage = false;
      this.item = item;
   }

   public SlotLockedToClass(IInventory par1iInventory, int par2, int par3, int par4, Item item, int metadata) {
      this(par1iInventory, par2, par3, par4, item);
      this.damage = metadata;
      this.checkDamage = true;
   }

   public boolean isItemValid(ItemStack is) {
      return !this.checkDamage?is.getItem() == this.item:is.getItem() == this.item && is.getItemDamage() == this.damage;
   }
}
