package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogCondition;
import com.chocolate.chocolateQuest.quest.worldManager.ReputationManager;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class DialogConditionKillCounter extends DialogCondition {

   public boolean matches(EntityPlayer player, EntityHumanNPC npc) {
      int var = ReputationManager.instance.getKillAmmount(super.name, player.getCommandSenderName());
      int value = super.value;
      return this.matches(var, value);
   }

   public String getNameForName() {
      return "KillCounter";
   }

   public void getSuggestions(List list) {}
}
