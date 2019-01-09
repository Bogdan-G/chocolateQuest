package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogAction;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class DialogActionBuildSchematic extends DialogAction {

   public void execute(EntityPlayer player, EntityHumanNPC npc) {
      npc.startBuildingSchematic(super.name, super.surname, super.value);
   }

   public boolean hasName() {
      return true;
   }

   public boolean hasSurname() {
      return true;
   }

   public boolean hasValue() {
      return true;
   }

   public String getNameForName() {
      return "Schematic";
   }

   public String getNameForSurname() {
      return "Position";
   }

   public String getNameForValue() {
      return "Build speed";
   }

   public String toString() {
      String s = DialogAction.actions[this.getType()].toString();
      s = s + " " + super.name + " at " + super.surname + " ";
      return s;
   }

   public void getSuggestions(List list) {
      list.add("You can specify a position from the AI positions list");
      list.add("If no position is found the npc will build at home position");
   }
}
