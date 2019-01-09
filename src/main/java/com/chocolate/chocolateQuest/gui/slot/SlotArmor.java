package com.chocolate.chocolateQuest.gui.slot;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class SlotArmor extends Slot {

   int armorSlot;


   public SlotArmor(IInventory par1iInventory, int par2, int par3, int par4, int armorSlot) {
      super(par1iInventory, par2, par3, par4);
      this.armorSlot = armorSlot;
   }

   public boolean isItemValid(ItemStack par1ItemStack) {
      return this.armorSlot != 0?(par1ItemStack.getItem() instanceof ItemArmor?((ItemArmor)par1ItemStack.getItem()).armorType == this.armorSlot:false):par1ItemStack.getItem().isValidArmor(par1ItemStack, this.armorSlot, (Entity)null) || par1ItemStack.getItem() instanceof ItemBlock;
   }
}
