package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogActionBuildSchematic;
import com.chocolate.chocolateQuest.quest.DialogActionCommand;
import com.chocolate.chocolateQuest.quest.DialogActionConsumeItem;
import com.chocolate.chocolateQuest.quest.DialogActionCreateCounter;
import com.chocolate.chocolateQuest.quest.DialogActionEnchant;
import com.chocolate.chocolateQuest.quest.DialogActionEquipement;
import com.chocolate.chocolateQuest.quest.DialogActionGiveItem;
import com.chocolate.chocolateQuest.quest.DialogActionJoinTeam;
import com.chocolate.chocolateQuest.quest.DialogActionList;
import com.chocolate.chocolateQuest.quest.DialogActionReputation;
import com.chocolate.chocolateQuest.quest.DialogActionSetAI;
import com.chocolate.chocolateQuest.quest.DialogActionSetCurrentPositionHome;
import com.chocolate.chocolateQuest.quest.DialogActionSetItem;
import com.chocolate.chocolateQuest.quest.DialogActionSetNBT;
import com.chocolate.chocolateQuest.quest.DialogActionSetOwner;
import com.chocolate.chocolateQuest.quest.DialogActionSetTimer;
import com.chocolate.chocolateQuest.quest.DialogActionShop;
import com.chocolate.chocolateQuest.quest.DialogActionSounInABottle;
import com.chocolate.chocolateQuest.quest.DialogActionSpawnMonster;
import com.chocolate.chocolateQuest.quest.DialogActionVariableGlobal;
import com.chocolate.chocolateQuest.quest.DialogActionVariableNPC;
import com.chocolate.chocolateQuest.quest.DialogCondition;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class DialogAction {

   public String name = "";
   public String surname = "";
   public int value = 0;
   public int operator = 0;
   public NBTTagCompound actionTag;
   public List conditions = null;
   public static DialogActionList[] actions = new DialogActionList[]{new DialogActionList(DialogActionShop.class, "Open shop"), new DialogActionList(DialogActionEnchant.class, "Open item upgrade"), new DialogActionList(DialogActionEquipement.class, "Open inventory"), new DialogActionList(DialogActionJoinTeam.class, "Join team"), new DialogActionList(DialogActionVariableNPC.class, "NPC variable"), new DialogActionList(DialogActionVariableGlobal.class, "Global variable"), new DialogActionList(DialogActionReputation.class, "Reputation"), new DialogActionList(DialogActionGiveItem.class, "Give item"), new DialogActionList(DialogActionConsumeItem.class, "Consume item"), new DialogActionList(DialogActionCommand.class, "Command"), new DialogActionList(DialogActionSetOwner.class, "Set owner"), new DialogActionList(DialogActionSetItem.class, "Set equipement"), new DialogActionList(DialogActionSetAI.class, "Set AI"), new DialogActionList(DialogActionSetNBT.class, "Load data from NBT"), new DialogActionList(DialogActionSpawnMonster.class, "Spawn monster"), new DialogActionList(DialogActionCreateCounter.class, "Kill counter"), new DialogActionList(DialogActionSetTimer.class, "Timer"), new DialogActionList(DialogActionSetCurrentPositionHome.class, "Set home"), new DialogActionList(DialogActionBuildSchematic.class, "Build schematic"), new DialogActionList(DialogActionSounInABottle.class, "Put in a bottle")};
   static final String[] operations = new String[]{"SET", "ADD", "SUBSTRACT"};
   byte id;


   public static int getIDByName(String name) {
      for(int i = 0; i < actions.length; ++i) {
         if(actions[i].name.equals(name)) {
            return i;
         }
      }

      return 0;
   }

   public void execute(EntityPlayer player, EntityHumanNPC npc) {}

   public int operateValue(int originalValue) {
      switch(this.operator) {
      case 0:
         return this.value;
      case 1:
         return originalValue + this.value;
      case 2:
         return originalValue - this.value;
      default:
         return this.value;
      }
   }

   public String operateName(EntityPlayer player) {
      if(this.name.contains("@sp")) {
         this.name = this.name.replace("@sp", player.getCommandSenderName());
      }

      return this.name;
   }

   public void setType(int type) {
      this.id = (byte)type;
   }

   public byte getType() {
      return this.id;
   }

   public static DialogAction getFromNBT(NBTTagCompound tag) {
      byte type = tag.getByte("Type");

      try {
         DialogAction e = actions[type].getNewInstance();
         e.readFromNBT(tag);
         return e;
      } catch (Exception var3) {
         var3.printStackTrace();
         return null;
      }
   }

   public void writeToNBT(NBTTagCompound tag) {
      tag.setByte("Type", this.getType());
      if(this.hasName()) {
         tag.setString("Name", this.name);
      }

      if(this.hasSurname()) {
         tag.setString("Surname", this.surname);
      }

      if(this.hasValue()) {
         tag.setInteger("Value", this.value);
      }

      if(this.hasOperator()) {
         tag.setInteger("Operator", this.operator);
      }

      if(this.hasTag() && this.actionTag != null) {
         tag.setTag("ActionTag", this.actionTag);
      }

      if(this.conditions != null) {
         NBTTagList list = new NBTTagList();
         Iterator i$ = this.conditions.iterator();

         while(i$.hasNext()) {
            DialogCondition condition = (DialogCondition)i$.next();
            NBTTagCompound conditionTag = new NBTTagCompound();
            condition.writeToNBT(conditionTag);
            list.appendTag(conditionTag);
         }

         tag.setTag("Conditions", list);
      }

   }

   public void readFromNBT(NBTTagCompound tag) {
      if(this.hasName()) {
         this.name = tag.getString("Name");
      }

      if(this.hasSurname()) {
         this.surname = tag.getString("Surname");
      }

      if(this.hasValue()) {
         this.value = tag.getInteger("Value");
      }

      if(this.hasOperator()) {
         this.operator = tag.getInteger("Operator");
      }

      if(this.hasTag() && tag.hasKey("ActionTag")) {
         this.actionTag = (NBTTagCompound)tag.getTag("ActionTag");
      }

      if(tag.hasKey("Conditions")) {
         NBTTagList list = (NBTTagList)tag.getTag("Conditions");
         int optionCount = list.tagCount();

         for(int i = 0; i < optionCount; ++i) {
            this.addCondition(DialogCondition.getFromNBT(list.getCompoundTagAt(i)));
         }
      }

   }

   public void addCondition(DialogCondition newDialog) {
      if(this.conditions == null) {
         this.conditions = new ArrayList();
      }

      this.conditions.add(newDialog);
   }

   public void removeCondition(DialogCondition condition) {
      this.conditions.remove(condition);
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

   public boolean hasSurname() {
      return false;
   }

   public String getNameForSurname() {
      return "Surname";
   }

   public int getSelectorForSurname() {
      return 0;
   }

   public boolean hasValue() {
      return false;
   }

   public String getNameForValue() {
      return "Value";
   }

   public int getSelectorForValue() {
      return 1;
   }

   public boolean hasOperator() {
      return false;
   }

   public String getNameForOperator() {
      return "Operation";
   }

   public int getSelectorForOperator() {
      return 2;
   }

   public String[] getOptionsForOperator() {
      return operations;
   }

   public boolean hasTag() {
      return false;
   }

   public String toString() {
      String s = actions[this.getType()].toString();
      if(this.hasName()) {
         s = s + " | " + this.getNameString();
      }

      if(this.hasOperator()) {
         s = s + " | " + this.getOperatorString(this.operator);
      }

      if(this.hasValue()) {
         s = s + " | " + this.value;
      }

      return s;
   }

   public String getNameString() {
      return this.name;
   }

   public String getOperatorString(int operator) {
      return this.getOptionsForOperator()[operator];
   }

   public String getValueString(int operator) {
      return this.getOptionsForOperator()[operator];
   }

   public void getSuggestions(List list) {}

   public DialogAction copy() {
      NBTTagCompound tag = new NBTTagCompound();
      this.writeToNBT(tag);
      return getFromNBT(tag);
   }

}
