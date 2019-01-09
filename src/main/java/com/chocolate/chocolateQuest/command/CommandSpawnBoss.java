package com.chocolate.chocolateQuest.command;

import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import com.chocolate.chocolateQuest.entity.boss.EntityBull;
import com.chocolate.chocolateQuest.entity.boss.EntityGiantBoxer;
import com.chocolate.chocolateQuest.entity.boss.EntityGiantZombie;
import com.chocolate.chocolateQuest.entity.boss.EntitySlimeBoss;
import com.chocolate.chocolateQuest.entity.boss.EntitySpiderBoss;
import com.chocolate.chocolateQuest.entity.boss.EntityTurtle;
import com.chocolate.chocolateQuest.entity.boss.EntityWyvern;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.world.World;

public class CommandSpawnBoss extends CommandBase {

   final int NAME_POSITION = 0;
   final int LVL_POSITION = 1;
   final int POS_X_POSITION = 2;
   final int POS_Y_POSITION = 3;
   final int POS_Z_POSITION = 4;
   String[] NAMES = new String[]{"bull", "turtle", "spider", "monking", "slime", "giantZombie"};
   String[] NUMBERS_1_TO_10 = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};


   public String getCommandName() {
      return "CQSpawnBoss";
   }

   public String getCommandUsage(ICommandSender icommandsender) {
      return "/CQSpawnBoss Boss_Name Boss_Size(1-10) positionX position_Y position_Z";
   }

   public void processCommand(ICommandSender icommandsender, String[] astring) {
      if(astring.length > 0) {
         World world = icommandsender.getEntityWorld();
         double lvl = 1.0D;
         if(astring.length > 1) {
            lvl = Double.parseDouble(astring[1]);
         }

         String name = astring[0];
         EntityBaseBoss boss = getBossByName(name, world);
         int posX = icommandsender.getPlayerCoordinates().posX;
         int posY = icommandsender.getPlayerCoordinates().posY;
         int posZ = icommandsender.getPlayerCoordinates().posZ;
         String s;
         if(astring.length > 2) {
            s = astring[2];
            posX = this.getCoordinate(s, posX);
         }

         if(astring.length > 3) {
            s = astring[3];
            posY = this.getCoordinate(s, posY);
         }

         if(astring.length > 4) {
            s = astring[4];
            posZ = this.getCoordinate(s, posZ);
         }

         if(boss != null) {
            boss.setPosition((double)posX - 0.5D, (double)posY + 0.5D, (double)posZ - 0.5D);
            boss.setMonsterScale((float)lvl);
            boss.heal(boss.getMaxHealth());
            world.spawnEntityInWorld(boss);
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
      String[] arr$;
      int len$;
      int i$;
      String s;
      if(parArray.length - 1 == 0) {
         last = parArray[parArray.length - 1];
         arr$ = this.NAMES;
         len$ = arr$.length;

         for(i$ = 0; i$ < len$; ++i$) {
            s = arr$[i$];
            if(s.startsWith(last)) {
               list.add(s);
            }
         }
      } else if(parArray.length - 1 == 1) {
         arr$ = this.NUMBERS_1_TO_10;
         len$ = arr$.length;

         for(i$ = 0; i$ < len$; ++i$) {
            s = arr$[i$];
            list.add(s);
         }
      } else if(parArray.length <= 5) {
         list.add("~");
      }

      return list;
   }

   public int getCoordinate(String s, int pos) {
      if(s.startsWith("~")) {
         s = s.substring(1);
         if(s.length() == 0) {
            s = "0";
         }
      } else {
         pos = 0;
      }

      pos += Integer.parseInt(s);
      return pos;
   }

   public static EntityBaseBoss getBossByName(String name, World world) {
      Object boss = null;
      if(name.equals("monking")) {
         boss = new EntityGiantBoxer(world);
      } else if(name.equals("slime")) {
         boss = new EntitySlimeBoss(world);
      } else if(name.equals("spider")) {
         boss = new EntitySpiderBoss(world);
      } else if(name.equals("turtle")) {
         boss = new EntityTurtle(world);
      } else if(name.equals("bull")) {
         boss = new EntityBull(world);
      } else if(name.equals("giantZombie")) {
         boss = new EntityGiantZombie(world);
      } else if(name.equals("gotterfunken")) {
         boss = new EntityWyvern(world);
      }

      return (EntityBaseBoss)boss;
   }
}
