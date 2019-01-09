package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogAction;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

public class DialogActionSetNBT extends DialogAction {

   public DialogActionSetNBT() {
      super.name = "{}";
   }

   public void execute(EntityPlayer player, EntityHumanNPC npc) {
      try {
         NBTBase base = JsonToNBT.func_150315_a(super.name);
         NBTTagCompound e = (NBTTagCompound)base;
         npc.loadFromTag(e);
      } catch (NBTException var5) {
         var5.printStackTrace();
      }

   }

   public boolean hasName() {
      return true;
   }

   public String getNameForName() {
      return "NBTData";
   }

   public boolean hasValue() {
      return false;
   }

   public void getSuggestions(List list) {}
}
