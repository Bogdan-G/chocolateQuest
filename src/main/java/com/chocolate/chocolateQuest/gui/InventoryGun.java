package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.gui.InventoryBag;
import com.chocolate.chocolateQuest.items.gun.ILoadableGun;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class InventoryGun extends InventoryBag {

   ILoadableGun gun;


   public InventoryGun(ItemStack item, EntityPlayer player) {
      super(item, player);
      this.gun = (ILoadableGun)item.getItem();
   }

   public int getSizeInventory() {
      return this.gun == null?super.getSizeInventory():this.gun.getAmmoLoaderAmmount(super.container);
   }

   public String getInventoryName() {
      return "Gun";
   }

   public ItemStack getStackInSlotOnClosing(int i) {
      return null;
   }

   public int getInventoryStackLimit() {
      return this.gun.getAmmoLoaderStackSize(super.container);
   }
}
