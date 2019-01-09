package com.chocolate.chocolateQuest.command;

import com.chocolate.chocolateQuest.quest.worldManager.ReputationManager;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class CommandKillCounter extends CommandBase {

   final int NAME = 0;
   final int PLAYER_NAME = 1;
   final int ENTITY_NAME = 2;
   final int ENTITY_DATA = 3;


   public String getCommandName() {
      return "CQAddKillCounter";
   }

   public String getCommandUsage(ICommandSender icommandsender) {
      return "/CQAddKillCounter counter_name player_name entity_id entity_tags";
   }

   public void processCommand(ICommandSender icommandsender, String[] astring) {
      if(astring.length > 0) {
         World world = icommandsender.getEntityWorld();
         String counterName = astring[0];
         String playerName = null;
         if(astring.length > 1) {
            playerName = astring[1];
         }

         String entityName = null;
         if(astring.length > 2) {
            entityName = astring[2];
         }

         String entityData = null;
         if(astring.length > 3) {
            entityData = astring[3];
         }

         if(counterName != null && playerName != null && entityName != null) {
            ReputationManager.instance.addKillCounter(counterName, playerName, entityName, entityData);
         }
      }

   }

   public int compareTo(Object arg0) {
      return 0;
   }

   public boolean canCommandSenderUseCommand(ICommandSender arg0) {
      return super.canCommandSenderUseCommand(arg0);
   }

   public int getRequiredPermissionLevel() {
      return super.getRequiredPermissionLevel();
   }

   public List addTabCompletionOptions(ICommandSender par1iCommandSender, String[] parArray) {
      String last = "";
      ArrayList list = new ArrayList();
      if(parArray.length - 1 == 0) {
         if("UNIQUE_IDENTIFIER".contains(parArray[0])) {
            list.add("UNIQUE_IDENTIFIER");
         }
      } else {
         if(parArray.length - 1 == 1) {
            return getListOfStringsMatchingLastWord(parArray, MinecraftServer.getServer().getAllUsernames());
         }

         if(parArray.length - 1 == 2 && "ENTITY_ID".contains(parArray[2])) {
            list.add("ENTITY_ID");
         }
      }

      return list;
   }
}
