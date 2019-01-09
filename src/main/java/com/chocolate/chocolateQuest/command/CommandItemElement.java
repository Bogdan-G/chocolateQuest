package com.chocolate.chocolateQuest.command;

import com.chocolate.chocolateQuest.magic.Elements;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class CommandItemElement extends CommandBase {

   public String getCommandName() {
      return "CQAddElement";
   }

   public String getCommandUsage(ICommandSender icommandsender) {
      return "/CQAddElement \"element\" \"ammount\" with an blade from the mod in the hand \n Default ma";
   }

   public void processCommand(ICommandSender icommandsender, String[] astring) {
      int value = 4;
      if(astring.length >= 1) {
         value = Integer.parseInt(astring[1]);
      }

      if(astring.length >= 0) {
         String element = astring[0];
         EntityPlayer player = (EntityPlayer)icommandsender;
         ItemStack is = player.getCurrentEquippedItem();
         if(is != null) {
            if(element.equals("blast")) {
               Elements.setElementValue(is, Elements.blast, value);
            }

            if(element.equals("fire")) {
               Elements.setElementValue(is, Elements.fire, value);
            }

            if(element.equals("physic")) {
               Elements.setElementValue(is, Elements.physic, value);
            }

            if(element.equals("magic")) {
               Elements.setElementValue(is, Elements.magic, value);
            }

            if(element.equals("light")) {
               Elements.setElementValue(is, Elements.light, value);
            }

            if(element.equals("dark")) {
               Elements.setElementValue(is, Elements.darkness, value);
            }
         }
      }

   }

   public int compareTo(Object arg0) {
      return 0;
   }

   public List addTabCompletionOptions(ICommandSender par1iCommandSender, String[] parArray) {
      ArrayList list = new ArrayList();
      if(parArray.length > 1) {
         for(int i = 1; i < 7; ++i) {
            list.add("" + i);
         }
      } else {
         list.add("physic");
         list.add("magic");
         list.add("blast");
         list.add("fire");
         list.add("light");
      }

      return list;
   }
}
