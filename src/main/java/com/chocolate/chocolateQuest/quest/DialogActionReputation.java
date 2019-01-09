package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogAction;
import com.chocolate.chocolateQuest.quest.worldManager.ReputationManager;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class DialogActionReputation extends DialogAction {

   public void execute(EntityPlayer player, EntityHumanNPC npc) {
      ReputationManager.instance.addReputation(player, super.name, super.value);
   }

   public boolean hasName() {
      return true;
   }

   public String getNameForName() {
      return "Faction name";
   }

   public boolean hasValue() {
      return true;
   }

   public void getSuggestions(List list) {}
}
