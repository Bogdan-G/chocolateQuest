package com.chocolate.chocolateQuest.gui.guinpc;

import com.chocolate.chocolateQuest.gui.ContainerBDChest;
import com.chocolate.chocolateQuest.gui.guinpc.InventoryShop;
import com.chocolate.chocolateQuest.gui.slot.SlotShop;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerShop extends ContainerBDChest {

   static final int COLUMS = 9;
   static final int ICON_DESP = 17;
   static final int MARGIN = 10;
   static final int SHOP_SLOTS = 18;
   boolean isCreative = true;
   public InventoryShop shopInventory;


   public ContainerShop(IInventory playerInventory, InventoryShop chestInventory) {
      super(playerInventory, chestInventory);
      this.shopInventory = chestInventory;
   }

   public void layoutInventory(IInventory chestInventory) {
      int posY = 0;

      int i;
      int x;
      for(i = 0; i < 18; ++i) {
         x = 10 + i * 17 - posY * 9;
         int y = 10 + posY;
         this.addSlotToContainer(new SlotShop((InventoryShop)chestInventory, i, x, y));
         if(i % 9 == 8) {
            posY += 17;
         }
      }

      if(super.player.capabilities.isCreativeMode) {
         for(i = 0; i < 4; ++i) {
            x = 10 + i * 17;
            byte var6 = 54;
            if(i > 0) {
               x += 16;
            }

            this.addSlotToContainer(new Slot(chestInventory, 18 + i, x, var6));
         }
      }

   }

   public void layoutInventory2(IInventory chestInventory) {}

   public ItemStack slotClick(int slotID, int par2, int par3, EntityPlayer player) {
      if(par3 == 6) {
         return null;
      } else if(par3 == 1) {
         return null;
      } else if(slotID >= 36 && slotID < 54) {
         SlotShop slot = (SlotShop)super.inventorySlots.get(slotID);
         if(slot.hasRecipe(player, false)) {
            if(player.inventory.getItemStack() != null) {
               ItemStack heldStack = player.inventory.getItemStack();
               if(!heldStack.isItemEqual(slot.getStack())) {
                  return null;
               }

               if(heldStack.stackSize + slot.getStack().stackSize > heldStack.getMaxStackSize()) {
                  return null;
               }
            }

            slot.hasRecipe(player, true);
            return super.slotClick(slotID, par2, par3, player);
         } else {
            return null;
         }
      } else {
         return super.slotClick(slotID, par2, par3, player);
      }
   }

   public ItemStack transferStackInSlot(EntityPlayer player, int index) {
      return super.transferStackInSlot(player, index);
   }

   public int getPlayerInventoryY() {
      return super.getPlayerInventoryY();
   }
}
