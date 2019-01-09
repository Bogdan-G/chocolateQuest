package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogAction;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class DialogActionSetItem extends DialogAction {

   String[] slots = new String[]{"Right hand", "Boots", "Leggings", "Plate", "Helmet", "Left hand"};


   public void execute(EntityPlayer player, EntityHumanNPC npc) {
      ItemStack checkedItem = BDHelper.getStackFromString(super.name);
      npc.setCurrentItemOrArmor(super.operator, checkedItem);
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
      return true;
   }

   public String[] getOptionsForOperator() {
      return this.slots;
   }

   public void getSuggestions(List list) {
      list.add("Select an item to equip in the selected slot");
   }
}
