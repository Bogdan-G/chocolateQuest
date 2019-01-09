package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogAction;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class DialogActionShop extends DialogAction {

   public void execute(EntityPlayer player, EntityHumanNPC npc) {
      if(!npc.worldObj.isRemote) {
         npc.openShop(player);
      }

   }

   public boolean hasName() {
      return false;
   }

   public boolean hasValue() {
      return false;
   }

   public void getSuggestions(List list) {}
}
