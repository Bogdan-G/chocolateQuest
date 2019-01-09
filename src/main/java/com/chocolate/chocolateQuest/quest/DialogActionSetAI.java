package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.ai.EnumAiState;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogAction;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class DialogActionSetAI extends DialogAction {

   String[] names = EnumAiState.getNames();


   public void execute(EntityPlayer player, EntityHumanNPC npc) {
      npc.AIMode = super.operator;
      npc.setAIForCurrentMode();
   }

   public boolean hasName() {
      return false;
   }

   public boolean hasValue() {
      return false;
   }

   public boolean hasOperator() {
      return true;
   }

   public String[] getOptionsForOperator() {
      return this.names;
   }

   public void getSuggestions(List list) {}
}
