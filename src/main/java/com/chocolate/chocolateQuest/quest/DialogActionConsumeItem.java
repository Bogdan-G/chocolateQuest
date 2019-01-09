package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogAction;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class DialogActionConsumeItem extends DialogAction {

   public void execute(EntityPlayer player, EntityHumanNPC npc) {
      InventoryPlayer inventory = player.inventory;
      ItemStack checkedItem = BDHelper.getStackFromString(super.name);
      if(checkedItem != null) {
         for(int i = 0; i < inventory.getSizeInventory(); ++i) {
            ItemStack item = inventory.getStackInSlot(i);
            if(item != null && item.getItem() == checkedItem.getItem() && item.getItemDamage() == checkedItem.getItemDamage() && item.stackSize >= checkedItem.stackSize && BDHelper.compareTags(checkedItem.getTagCompound(), item.stackTagCompound)) {
               int stacksRemoved = item.stackSize;
               item.stackSize -= checkedItem.stackSize;
               checkedItem.stackSize -= stacksRemoved;
               if(item.stackSize <= 0) {
                  item = null;
               }

               inventory.setInventorySlotContents(i, item);
               if(checkedItem.stackSize <= 0) {
                  break;
               }
            }
         }
      }

   }

   public boolean hasName() {
      return true;
   }

   public int getSelectorForName() {
      return 3;
   }

   public boolean hasValue() {
      return false;
   }

   public void getSuggestions(List list) {
      list.add("Consumes an item from the player inventory");
   }
}
