package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.misc.EnumEnchantType;
import com.chocolate.chocolateQuest.quest.DialogAction;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class DialogActionEnchant extends DialogAction {

   public void execute(EntityPlayer player, EntityHumanNPC npc) {
      npc.openEnchantment(player, super.operator, super.value);
   }

   public boolean hasName() {
      return false;
   }

   public boolean hasValue() {
      return true;
   }

   public String getNameForValue() {
      return "Max level";
   }

   public String[] getOptionsForOperator() {
      return EnumEnchantType.getNames();
   }

   public String getNameForOperator() {
      return "Upgrade type";
   }

   public boolean hasOperator() {
      return true;
   }

   public void getSuggestions(List list) {
      list.add("Opens a GUI to enchant items with different enchantments depending on the type");
   }
}
