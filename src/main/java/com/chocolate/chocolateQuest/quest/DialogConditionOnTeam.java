package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogCondition;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;

public class DialogConditionOnTeam extends DialogCondition {

   public boolean matches(EntityPlayer player, EntityHumanNPC npc) {
      boolean var = false;
      ScorePlayerTeam team = player.worldObj.getScoreboard().getPlayersTeam(player.getCommandSenderName());
      return team != null?team.getRegisteredName().equals(super.name):false;
   }

   public String getNameForName() {
      return "Team name";
   }

   public boolean hasValue() {
      return false;
   }

   public boolean hasOperator() {
      return false;
   }

   public void getSuggestions(List list) {}
}
