package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogConditionCommand;
import com.chocolate.chocolateQuest.quest.DialogConditionGlobalVariable;
import com.chocolate.chocolateQuest.quest.DialogConditionItemOnInventory;
import com.chocolate.chocolateQuest.quest.DialogConditionKillCounter;
import com.chocolate.chocolateQuest.quest.DialogConditionList;
import com.chocolate.chocolateQuest.quest.DialogConditionLocalVariable;
import com.chocolate.chocolateQuest.quest.DialogConditionNPCTimer;
import com.chocolate.chocolateQuest.quest.DialogConditionNearbyEntity;
import com.chocolate.chocolateQuest.quest.DialogConditionOnTeam;
import com.chocolate.chocolateQuest.quest.DialogConditionReputation;
import com.chocolate.chocolateQuest.quest.DialogConditionScoreValue;
import com.chocolate.chocolateQuest.quest.DialogConditionTime;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public abstract class DialogCondition {

   public String name = "";
   public int value;
   public int operator;
   public static final int EQUALS = 0;
   public static final int DIFFERENT = 1;
   public static final int GREATER = 2;
   public static final int LESSER = 3;
   public static final String[] OPERATORS_MATHEMATICAL = new String[]{"EQUALS", "DIFFERENT", "GREATER", "LESSER"};
   public static final String[] OPERATORS_YES_NO = new String[]{"No", "Yes"};
   static final int MATHEMATICAL = 0;
   static final int YES_NO = 1;
   private byte id;
   public static DialogConditionList[] conditions = new DialogConditionList[]{new DialogConditionList(DialogConditionLocalVariable.class, "npc variable"), new DialogConditionList(DialogConditionGlobalVariable.class, "Global variable"), new DialogConditionList(DialogConditionScoreValue.class, "Score value"), new DialogConditionList(DialogConditionReputation.class, "Reputation"), new DialogConditionList(DialogConditionItemOnInventory.class, "Item on inventory"), new DialogConditionList(DialogConditionOnTeam.class, "On team"), new DialogConditionList(DialogConditionTime.class, "Time"), new DialogConditionList(DialogConditionCommand.class, "Command"), new DialogConditionList(DialogConditionKillCounter.class, "Kill Counter"), new DialogConditionList(DialogConditionNearbyEntity.class, "Nearby entity"), new DialogConditionList(DialogConditionNPCTimer.class, "Timer")};


   public boolean matches(EntityPlayer player, EntityHumanNPC npc) {
      return true;
   }

   public boolean matches(int var, int value) {
      switch(this.operator) {
      case 1:
         return var != value;
      case 2:
         return var > value;
      case 3:
         return var < value;
      default:
         return var == value;
      }
   }

   public boolean matches(long var, long value) {
      switch(this.operator) {
      case 1:
         return var != value;
      case 2:
         return var > value;
      case 3:
         return var < value;
      default:
         return var == value;
      }
   }

   public void readFromNBT(NBTTagCompound tag) {
      if(this.hasName()) {
         this.name = tag.getString("Name");
      }

      if(this.hasValue()) {
         this.value = tag.getInteger("Value");
      }

      if(this.hasOperator()) {
         this.operator = tag.getInteger("Operator");
      }

   }

   public void writeToNBT(NBTTagCompound tag) {
      tag.setByte("Type", this.getType());
      if(this.hasName()) {
         tag.setString("Name", this.name);
      }

      if(this.hasValue()) {
         tag.setInteger("Value", this.value);
      }

      if(this.hasOperator()) {
         tag.setInteger("Operator", this.operator);
      }

   }

   public static DialogCondition getFromNBT(NBTTagCompound tag) {
      byte type = tag.getByte("Type");

      try {
         DialogCondition e = conditions[type].getNewInstance();
         e.readFromNBT(tag);
         return e;
      } catch (Exception var3) {
         var3.printStackTrace();
         return null;
      }
   }

   public static int getIDByName(String name) {
      for(int i = 0; i < conditions.length; ++i) {
         if(conditions[i].name.equals(name)) {
            return i;
         }
      }

      return 0;
   }

   public void setType(int type) {
      this.id = (byte)type;
   }

   public byte getType() {
      return this.id;
   }

   public boolean hasName() {
      return true;
   }

   public String getNameForName() {
      return "Name";
   }

   public int getSelectorForName() {
      return 0;
   }

   public boolean hasValue() {
      return true;
   }

   public String getNameForValue() {
      return "Value";
   }

   public int getSelectorForValue() {
      return 1;
   }

   public boolean hasOperator() {
      return true;
   }

   public String getNameForOperator() {
      return "Operator";
   }

   public int getSelectorForOperator() {
      return 0;
   }

   public String[] getOptionsForOperator() {
      return this.getSelectorForOperator() == 0?OPERATORS_MATHEMATICAL:(this.getSelectorForOperator() == 1?OPERATORS_YES_NO:new String[]{"undefined"});
   }

   public String getOperatorString(int operator) {
      return this.getSelectorForOperator() == 0?OPERATORS_MATHEMATICAL[operator]:(this.getSelectorForOperator() == 1?OPERATORS_YES_NO[operator]:"undefined");
   }

   public String toString() {
      String s = conditions[this.getType()].toString();
      if(this.hasName()) {
         s = s + " | " + this.name;
      }

      if(this.hasOperator()) {
         s = s + " | " + this.getOperatorString(this.operator);
      }

      if(this.hasValue()) {
         s = s + " | " + this.value;
      }

      return s;
   }

   public void getSuggestions(List list) {}

   public DialogCondition copy() {
      NBTTagCompound tag = new NBTTagCompound();
      this.writeToNBT(tag);
      return getFromNBT(tag);
   }

}
