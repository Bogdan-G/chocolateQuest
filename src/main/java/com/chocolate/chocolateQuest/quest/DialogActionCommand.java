package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogAction;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class DialogActionCommand extends DialogAction {

   public void execute(EntityPlayer player, EntityHumanNPC npc) {
      npc.executeCommand(super.name);
   }

   public boolean hasName() {
      return true;
   }

   public String getNameForName() {
      return "Command";
   }

   public boolean hasValue() {
      return false;
   }

   public void getSuggestions(List list) {}
}
