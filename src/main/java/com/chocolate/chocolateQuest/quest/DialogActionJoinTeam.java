package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogAction;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;

public class DialogActionJoinTeam extends DialogAction {

   public void execute(EntityPlayer player, EntityHumanNPC npc) {
      Scoreboard scoreboard = player.worldObj.getScoreboard();
      ScorePlayerTeam team = scoreboard.getTeam(super.name);
      if(team == null) {
         scoreboard.createTeam(super.name);
      }

      scoreboard.func_151392_a(player.getCommandSenderName(), super.name);
   }

   public boolean hasName() {
      return true;
   }

   public boolean hasValue() {
      return false;
   }

   public String getNameForName() {
      return "Team name";
   }

   public void getSuggestions(List list) {
      list.add("Adds the player to the team with the specified name");
   }
}
