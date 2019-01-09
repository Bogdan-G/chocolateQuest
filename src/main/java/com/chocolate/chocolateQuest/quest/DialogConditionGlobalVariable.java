package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogCondition;
import com.chocolate.chocolateQuest.quest.worldManager.ReputationManager;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class DialogConditionGlobalVariable extends DialogCondition {

   public boolean matches(EntityPlayer player, EntityHumanNPC npc) {
      String name = super.name;
      if(name.contains("@sp")) {
         name.replace("@sp", player.getCommandSenderName());
      }

      int var = ReputationManager.instance.getGlobal(name);
      int value = super.value;
      return this.matches(var, value);
   }

   public String getNameForName() {
      return "Variable name";
   }

   public void getSuggestions(List list) {}
}
