package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogAction;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;

public class DialogActionSetCurrentPositionHome extends DialogAction {

   final int SET_HOME = 0;
   final int SET_HOME_DIST = 1;
   final int SET_HOME_AND_DIST = 2;


   public void execute(EntityPlayer player, EntityHumanNPC npc) {
      if(super.operator == 2) {
         npc.setHomeArea(MathHelper.floor_double(npc.posX), MathHelper.floor_double(npc.posY), MathHelper.floor_double(npc.posZ), super.value);
      } else if(super.operator == 1) {
         ChunkCoordinates coords = npc.getHomePosition();
         npc.setHomeArea(coords.posX, coords.posY, coords.posZ, super.value);
      } else if(super.operator == 0) {
         npc.setHomeArea(MathHelper.floor_double(npc.posX), MathHelper.floor_double(npc.posY), MathHelper.floor_double(npc.posZ), (int)npc.getHomeDistance());
      }

   }

   public boolean hasName() {
      return true;
   }

   public String getNameForName() {
      return "Position name";
   }

   public boolean hasValue() {
      return true;
   }

   public String getNameForValue() {
      return "Home distance";
   }

   public boolean hasOperator() {
      return true;
   }

   public String getNameForOperator() {
      return "";
   }

   public int getSelectorForOperator() {
      return 2;
   }

   public String[] getOptionsForOperator() {
      return new String[]{"Set home", "Set home distance", "Set home and distance"};
   }

   public void getSuggestions(List list) {
      list.add("Sets the npc home position to the specified AI position");
      list.add("If Position name is undefined sets the current npc position");
      list.add("as home position");
      list.add("If the distance from home is greater than the max distance");
      list.add("the npc will go back to home, a home distance of -1 allows");
      list.add("the npc to move without limits");
   }
}
