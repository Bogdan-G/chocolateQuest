package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogAction;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class DialogActionEquipement extends DialogAction {

   public void execute(EntityPlayer player, EntityHumanNPC npc) {
      if(super.operator == 1) {
         player.openGui(ChocolateQuest.instance, 10, player.worldObj, npc.getEntityId(), 0, 0);
      } else {
         npc.openEquipement(player);
      }

   }

   public boolean hasName() {
      return false;
   }

   public boolean hasValue() {
      return false;
   }

   public boolean hasOperator() {
      return true;
   }

   public String getNameForOperator() {
      return "Equipement/inventory";
   }

   public int getSelectorForOperator() {
      return 2;
   }

   public String[] getOptionsForOperator() {
      return new String[]{"Equipement", "Inventory"};
   }

   public void getSuggestions(List list) {}
}
