package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogAction;
import java.util.List;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class DialogActionSounInABottle extends DialogAction {

   public void execute(EntityPlayer player, EntityHumanNPC npc) {
      ItemStack is = new ItemStack(ChocolateQuest.soulBottle);
      if(ChocolateQuest.soulBottle.onLeftClickEntity(is, player, npc)) {
         npc.worldObj.spawnEntityInWorld(new EntityItem(npc.worldObj, npc.posX, npc.posY, npc.posZ, is));
      }

   }

   public boolean hasName() {
      return false;
   }

   public boolean hasValue() {
      return false;
   }

   public void getSuggestions(List list) {
      super.getSuggestions(list);
      list.add("Puts this npc into a soul in a bottle item and drops it");
   }
}
