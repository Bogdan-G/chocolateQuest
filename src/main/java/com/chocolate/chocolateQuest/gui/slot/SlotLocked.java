package com.chocolate.chocolateQuest.gui.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotLocked extends Slot {

   public SlotLocked(IInventory par1iInventory, int par2, int par3, int par4) {
      super(par1iInventory, par2, par3, par4);
   }

   public boolean isItemValid(ItemStack is) {
      return false;
   }

   public boolean canTakeStack(EntityPlayer p_82869_1_) {
      return false;
   }
}
