package com.chocolate.chocolateQuest.gui.slot;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.API.ITwoHandedItem;
import com.chocolate.chocolateQuest.items.swords.ItemSwordAndShieldBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotHandRight extends Slot {

   Slot oppositeHand;


   public SlotHandRight(IInventory arg0, int id, int x, int y) {
      super(arg0, id, x, y);
   }

   public void setOpossedHandSlot(Slot slot) {
      this.oppositeHand = slot;
   }

   public boolean isItemValid(ItemStack is) {
      return this.oppositeHand.getHasStack() && (is.getItem() instanceof ITwoHandedItem || is.getItem() instanceof ItemSwordAndShieldBase)?false:super.isItemValid(is);
   }

   public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack is) {
      if(is.getItem() instanceof ItemSwordAndShieldBase) {
         this.oppositeHand.putStack((ItemStack)null);
      }

      super.onPickupFromSlot(par1EntityPlayer, is);
   }

   public void putStack(ItemStack is) {
      if(is != null && is.getItem() instanceof ItemSwordAndShieldBase) {
         this.oppositeHand.putStack(new ItemStack(ChocolateQuest.shield, 0, ((ItemSwordAndShieldBase)is.getItem()).getShieldID(is)));
      }

      super.putStack(is);
   }
}
