package com.chocolate.chocolateQuest.items.mobControl;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.items.mobControl.ItemPathMarker;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.utils.Vec4I;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

public class ItemTeamEditor extends ItemPathMarker {

   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
      if(stack.stackTagCompound != null && entity instanceof EntityHumanBase) {
         EntityHumanBase human = (EntityHumanBase)entity;
         human.AIPosition = new Vec4I(stack.stackTagCompound.getInteger("x0"), stack.stackTagCompound.getInteger("y0"), stack.stackTagCompound.getInteger("z0"), stack.stackTagCompound.getInteger("rot0"));
         if(!player.worldObj.isRemote) {
            player.addChatMessage(new ChatComponentText("Assigned ward position for " + BDHelper.StringColor("2") + entity.getCommandSenderName()));
         }
      }

      return true;
   }

   public int getMaxPoints(ItemStack is) {
      return 1;
   }
}
