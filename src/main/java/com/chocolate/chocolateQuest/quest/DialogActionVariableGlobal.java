package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogAction;
import com.chocolate.chocolateQuest.quest.worldManager.ReputationManager;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class DialogActionVariableGlobal extends DialogAction {

   public void execute(EntityPlayer player, EntityHumanNPC npc) {
      if(super.name.contains("@sp")) {
         super.name.replace("@sp", player.getCommandSenderName());
      }

      int newValue = this.operateValue(ReputationManager.instance.getGlobal(super.name));
      ReputationManager.instance.setGlobal(super.name, newValue);
   }

   public boolean hasName() {
      return true;
   }

   public boolean hasValue() {
      return true;
   }

   public boolean hasOperator() {
      return true;
   }

   public String getNameForName() {
      return "Variable name";
   }

   public void getSuggestions(List list) {
      list.add("All npcs can read global variables");
      list.add("@sp in the name will be replaced by the name of the player speaking to.");
      list.add("So you can use @sp in the name to create player related variables.");
      list.add("");
      list.add("Don\'t use special chars(+,-,/,*,<,>,=) as variable names.");
   }
}
