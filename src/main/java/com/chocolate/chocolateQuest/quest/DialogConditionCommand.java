package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogCondition;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class DialogConditionCommand extends DialogCondition {

   public boolean matches(EntityPlayer player, EntityHumanNPC npc) {
      int var = npc.executeCommand(super.name);
      int value = super.value;
      return this.matches(var, value);
   }

   public String getNameForName() {
      return "Command";
   }

   public void getSuggestions(List list) {
      list.add("Executes a testfor command and compares the result with the value");
   }
}
