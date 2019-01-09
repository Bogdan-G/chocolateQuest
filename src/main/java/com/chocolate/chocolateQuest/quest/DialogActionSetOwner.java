package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogAction;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class DialogActionSetOwner extends DialogAction {

   public void execute(EntityPlayer player, EntityHumanNPC npc) {
      String name = this.operateName(player);
      EntityPlayer targetPlayer = player.worldObj.getPlayerEntityByName(name);
      if(npc.getOwner() == targetPlayer) {
         npc.followTime += super.value;
      } else {
         npc.followTime = super.value;
      }

      npc.setOwner(targetPlayer);
   }

   public String getNameForName() {
      return "Player name ( @sp for speaking player, empty to clear owner)";
   }

   public boolean hasName() {
      return true;
   }

   public boolean hasValue() {
      return true;
   }

   public String getNameForValue() {
      return "Owned ticks, 0 for unlimited time";
   }

   public void getSuggestions(List list) {}
}
