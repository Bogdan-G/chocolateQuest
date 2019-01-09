package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogAction;
import com.chocolate.chocolateQuest.quest.worldManager.ReputationManager;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class DialogActionCreateCounter extends DialogAction {

   final String[] typeNames = new String[]{"Create kill counter", "Delete kill counter"};
   static final int CREATE = 0;
   static final int DELETE = 1;


   public void execute(EntityPlayer player, EntityHumanNPC npc) {
      String[] strings = super.name.split(" ");
      String counterName = null;
      String entityName = null;
      String tags = null;
      if(strings.length > 0) {
         counterName = strings[0];
      }

      if(strings.length > 1) {
         entityName = strings[1];
      }

      if(strings.length > 2) {
         tags = strings[2];
      }

      if(counterName != null) {
         if(super.operator == 0 && entityName != null) {
            ReputationManager.instance.addKillCounter(counterName, player.getCommandSenderName(), entityName, tags);
         }

         if(super.operator == 1) {
            ReputationManager.instance.removeKillCounter(counterName, player.getCommandSenderName());
         }
      }

   }

   public boolean hasName() {
      return true;
   }

   public String getNameForName() {
      return "Counter";
   }

   public boolean hasValue() {
      return false;
   }

   public boolean hasOperator() {
      return true;
   }

   public String getNameForOperator() {
      return "Action: ";
   }

   public String[] getOptionsForOperator() {
      return this.typeNames;
   }

   public void getSuggestions(List list) {
      list.add("Create or delete a kill counters related to the player");
      list.add("To delete put the counter name");
      list.add("The formating to create is \"COUNTER_NAME ENTITY_ID ENTITY_TAGS\"");
      list.add("COUNTER_NAME is the name of the counter");
      list.add("ENTITY_ID is the id used to summon it with commands");
      list.add("ENTITY_TAGS are data to check from the entity for example:");
      list.add("KILL_BULL_COUNTER chocolateQuest.bull {scale:1f}");
      list.add("to increase the counter each time a bull with size 1 is killed by the player");
   }
}
