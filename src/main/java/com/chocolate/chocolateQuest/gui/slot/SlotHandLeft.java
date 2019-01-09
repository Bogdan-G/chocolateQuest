package com.chocolate.chocolateQuest.gui.slot;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.API.ITwoHandedItem;
import com.chocolate.chocolateQuest.items.swords.ItemSwordAndShieldBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotHandLeft extends Slot {

   Slot rightHand;


   public SlotHandLeft(IInventory arg0, int id, int x, int y, Slot rightHand) {
      super(arg0, id, x, y);
      this.rightHand = rightHand;
   }

   public boolean isItemValid(ItemStack par1ItemStack) {
      return this.isItemTwoHanded(par1ItemStack)?false:(this.rightHand.getHasStack() && this.isItemTwoHanded(this.rightHand.getStack())?false:super.isItemValid(par1ItemStack));
   }

   public boolean isItemTwoHanded(ItemStack is) {
      return is.getItem() instanceof ITwoHandedItem || is.getItem() instanceof ItemSwordAndShieldBase;
   }

   public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
      return this.getStack() != null && this.getStack().getItem() == ChocolateQuest.shield?(this.rightHand.getStack() == null?true:!(this.rightHand.getStack().getItem() instanceof ItemSwordAndShieldBase)):super.canTakeStack(par1EntityPlayer);
   }
}
