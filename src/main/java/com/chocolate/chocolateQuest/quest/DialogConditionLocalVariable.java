package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogCondition;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class DialogConditionLocalVariable extends DialogCondition {

   public boolean matches(EntityPlayer player, EntityHumanNPC npc) {
      String name = super.name;
      if(name.contains("@sp")) {
         name.replace("@sp", player.getCommandSenderName());
      }

      int var = 0;
      if(npc.npcVariables != null) {
         var = npc.npcVariables.getInteger(name);
      }

      int value = super.value;
      return this.matches(var, value);
   }

   public String getNameForName() {
      return "Variable name";
   }

   public void getSuggestions(List list) {}
}
