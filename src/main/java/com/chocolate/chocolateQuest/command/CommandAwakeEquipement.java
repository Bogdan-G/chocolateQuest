package com.chocolate.chocolateQuest.command;

import com.chocolate.chocolateQuest.magic.Awakements;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class CommandAwakeEquipement extends CommandBase {

   public String getCommandName() {
      return "CQAwakeItem";
   }

   public String getCommandUsage(ICommandSender icommandsender) {
      return "/CQAwakeItem with an item from the mod in the hand";
   }

   public void processCommand(ICommandSender icommandsender, String[] astring) {
      EntityPlayer player = (EntityPlayer)icommandsender;
      ItemStack is = player.getCurrentEquippedItem();
      if(is != null) {
         Awakements[] arr$ = Awakements.awekements;
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Awakements aw = arr$[i$];
            if(aw.canBeUsedOnItem(is)) {
               Awakements.addEnchant(is, aw, aw.getMaxLevel());
            }
         }
      }

   }

   public int compareTo(Object arg0) {
      return 0;
   }

   public List addTabCompletionOptions(ICommandSender par1iCommandSender, String[] parArray) {
      String var10000 = parArray[parArray.length - 1];
      ArrayList list = new ArrayList();
      return list;
   }
}
