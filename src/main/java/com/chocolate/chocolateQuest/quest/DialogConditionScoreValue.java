package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogCondition;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;

public class DialogConditionScoreValue extends DialogCondition {

   public boolean matches(EntityPlayer player, EntityHumanNPC npc) {
      int var = 0;
      ScoreObjective objective = player.worldObj.getScoreboard().getObjective(super.name);
      if(objective != null) {
         Score score = player.worldObj.getScoreboard().func_96529_a(player.getCommandSenderName(), objective);
         var = score.getScorePoints();
      }

      return this.matches(var, super.value);
   }

   public String getNameForName() {
      return "Score name";
   }

   public void getSuggestions(List list) {}
}
