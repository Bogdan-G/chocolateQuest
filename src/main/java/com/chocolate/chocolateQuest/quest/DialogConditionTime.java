package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogCondition;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class DialogConditionTime extends DialogCondition {

   public boolean matches(EntityPlayer player, EntityHumanNPC npc) {
      long var = npc.worldObj.getWorldTime() % 24000L;
      int value = super.value;
      return this.matches(var, (long)value);
   }

   public boolean hasName() {
      return false;
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

   public void getSuggestions(List list) {}
}
