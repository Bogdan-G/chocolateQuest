package com.chocolate.chocolateQuest.gui.slot;

import com.chocolate.chocolateQuest.items.gun.ILoadableGun;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotAmmo extends Slot {

   ILoadableGun gun;


   public SlotAmmo(IInventory par1iInventory, int par2, int par3, int par4, ILoadableGun gun) {
      super(par1iInventory, par2, par3, par4);
      this.gun = gun;
   }

   public boolean isItemValid(ItemStack is) {
      return this.gun.isValidAmmo(is);
   }
}
