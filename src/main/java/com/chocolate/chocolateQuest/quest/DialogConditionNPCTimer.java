package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogCondition;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class DialogConditionNPCTimer extends DialogCondition {

   public boolean matches(EntityPlayer player, EntityHumanNPC npc) {
      long var = (long)npc.getTimeCounter(super.name);
      int value = super.value;
      return this.matches(var, (long)value);
   }

   public boolean hasName() {
      return true;
   }

   public boolean hasValue() {
      return true;
   }

   public String getNameForValue() {
      return "Time";
   }

   public boolean hasOperator() {
      return true;
   }

   public void getSuggestions(List list) {
      list.add("Checks if the timer with selected name has (operation) value than the value specified");
   }
}
