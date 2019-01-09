package com.chocolate.chocolateQuest.entity.ai.npcai;

import com.chocolate.chocolateQuest.entity.ai.npcai.EnumNpcAI;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;

public class NpcAI {

   public int hour;
   public String position;
   public int type;
   public NpcAI nextAI;


   private NpcAI(NBTTagList tagList, int index) {
      this.readFromList(tagList, index);
   }

   public NpcAI() {
      this.position = "Home";
   }

   public void writeToNBT(NBTTagList tagList) {
      NBTTagCompound tag = new NBTTagCompound();
      tag.setInteger("hour", this.hour);
      tag.setString("pos", this.position);
      tag.setInteger("type", this.type);
      tagList.appendTag(tag);
      if(this.nextAI != null) {
         this.nextAI.writeToNBT(tagList);
      }

   }

   public void readFromNBT(NBTTagList tagList) {
      this.readFromList(tagList, 0);
   }

   private void readFromList(NBTTagList tagList, int index) {
      NBTTagCompound tag = tagList.getCompoundTagAt(index);
      this.hour = tag.getInteger("hour");
      this.type = tag.getInteger("type");
      this.position = tag.getString("pos");
      if(index + 1 < tagList.tagCount()) {
         this.nextAI = new NpcAI(tagList, index + 1);
      }

   }

   public String toString() {
      return StatCollector.translateToLocal(EnumNpcAI.values()[this.type].ainame) + " at " + this.position;
   }
}
