package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogCondition;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class DialogConditionItemOnInventory extends DialogCondition {

   public boolean matches(EntityPlayer player, EntityHumanNPC npc) {
      InventoryPlayer inventory = player.inventory;
      ItemStack checkedItem = BDHelper.getStackFromString(super.name);
      if(checkedItem != null) {
         for(int i = 0; i < inventory.getSizeInventory(); ++i) {
            ItemStack item = inventory.getStackInSlot(i);
            if(item != null && item.getItem() == checkedItem.getItem() && item.getItemDamage() == checkedItem.getItemDamage() && item.stackSize >= checkedItem.stackSize) {
               return BDHelper.compareTags(checkedItem.getTagCompound(), item.stackTagCompound);
            }
         }
      }

      return false;
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

   public boolean hasOperator() {
      return false;
   }

   public String getNameForOperator() {
      return "Consume item?";
   }

   public int getSelectorForOperator() {
      return 1;
   }

   public void getSuggestions(List list) {
      list.add("This condition will pass if the selected item is on the player inventory");
      list.add("Sintax: itemName ammount damage {tags}");
   }
}
