package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogAction;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class DialogActionSetTimer extends DialogAction {

   public void execute(EntityPlayer player, EntityHumanNPC npc) {
      if(!super.name.isEmpty()) {
         npc.setTimeCounter(super.name, super.value);
      }

   }

   public boolean hasName() {
      return true;
   }

   public boolean hasValue() {
      return true;
   }

   public boolean hasOperator() {
      return false;
   }

   public void getSuggestions(List list) {
      list.add("Creates a timer with the specified name for this npc");
      list.add("counting down every tick and starting from the specified value");
   }
}
