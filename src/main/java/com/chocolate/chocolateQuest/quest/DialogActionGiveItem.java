package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogAction;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class DialogActionGiveItem extends DialogAction {

   public void execute(EntityPlayer player, EntityHumanNPC npc) {
      ItemStack item = BDHelper.getStackFromString(super.name);
      if(item != null && !player.inventory.addItemStackToInventory(item)) {
         player.dropPlayerItemWithRandomChoice(item, true);
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

   public void getSuggestions(List list) {}
}
