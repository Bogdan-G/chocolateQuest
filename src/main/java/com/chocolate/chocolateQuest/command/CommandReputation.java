package com.chocolate.chocolateQuest.command;

import com.chocolate.chocolateQuest.quest.worldManager.ReputationManager;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class CommandReputation extends CommandBase {

   final int ACTION_TYPE = 0;
   final int PLAYER_NAME = 1;
   final int TEAM_NAME = 2;
   final int AMMOUNT_POSITION = 3;
   String[] NAMES = new String[]{"set", "show"};
   String[] NUMBERS_1_TO_10 = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};


   public String getCommandName() {
      return "CQReputation";
   }

   public String getCommandUsage(ICommandSender icommandsender) {
      return "/CQReputation set team ammount /CQReputation show team";
   }

   public void processCommand(ICommandSender icommandsender, String[] astring) {
      if(astring.length > 0) {
         World world = icommandsender.getEntityWorld();
         boolean type = false;
         if(astring[0].equals(this.NAMES[0])) {
            type = true;
         }

         String playerName = null;
         if(astring.length > 1) {
            playerName = astring[1];
         }

         String teamName = null;
         if(astring.length > 2) {
            teamName = astring[2];
         }

         if(playerName != null && teamName != null) {
            int ammount;
            if(!type) {
               ammount = ReputationManager.instance.getPlayerReputation(playerName, teamName);
               String color = "+" + BDHelper.StringColor("2");
               if(ammount < 0) {
                  color = BDHelper.StringColor("4");
               }

               icommandsender.addChatMessage(new ChatComponentText(playerName + " reputation for team " + teamName + " = " + color + ammount));
            } else if(astring.length > 3) {
               ammount = Integer.parseInt(astring[3]);
               ReputationManager.instance.setReputation(playerName, teamName, ammount);
            }
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
         last = parArray[parArray.length - 1];
         String[] playerName = this.NAMES;
         int len$ = playerName.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String s = playerName[i$];
            if(s.startsWith(last)) {
               list.add(s);
            }
         }
      } else {
         if(parArray.length - 1 == 1) {
            return getListOfStringsMatchingLastWord(parArray, MinecraftServer.getServer().getAllUsernames());
         }

         if(parArray.length - 1 == 2) {
            String var9 = parArray[1];
         }
      }

      return list;
   }
}
