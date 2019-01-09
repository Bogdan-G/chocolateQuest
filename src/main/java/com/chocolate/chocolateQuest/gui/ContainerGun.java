package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.gui.ContainerBDChest;
import com.chocolate.chocolateQuest.gui.slot.SlotAmmo;
import com.chocolate.chocolateQuest.items.gun.ILoadableGun;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerGun extends ContainerBDChest {

   int rows;
   ItemStack itemstackGun;


   public ContainerGun(IInventory playerInventory, IInventory chestInventory, ItemStack is) {
      super(playerInventory, chestInventory);
      this.itemstackGun = is;
      this.layoutInventory(chestInventory);
   }

   protected void layoutContainer(IInventory playerInventory, IInventory chestInventory) {
      this.rows = (chestInventory.getSizeInventory() - 1) / 9;
      super.layoutContainer(playerInventory, chestInventory);
   }

   public void layoutInventory(IInventory chestInventory) {
      if(this.itemstackGun != null) {
         ILoadableGun gun = (ILoadableGun)this.itemstackGun.getItem();
         int posY = 0;

         for(int i = 0; i < chestInventory.getSizeInventory(); ++i) {
            int x = 10 + i * 17 - posY * 9;
            int y = 10 + posY;
            this.addSlotToContainer(new SlotAmmo(chestInventory, i, x, y, gun));
            if(i % 9 == 8) {
               posY += 17;
            }
         }

      }
   }

   public ItemStack transferStackInSlot(EntityPlayer player, int index) {
      Slot slot = (Slot)super.inventorySlots.get(index);
      if(slot != null && slot.getHasStack()) {
         ItemStack slotItemStack = slot.getStack();
         if(slotItemStack == this.itemstackGun) {
            return null;
         }
      }

      return super.transferStackInSlot(player, index);
   }

   public int getPlayerInventoryY() {
      return 60 + this.rows * 17;
   }
}
