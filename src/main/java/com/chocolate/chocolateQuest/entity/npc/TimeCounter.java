package com.chocolate.chocolateQuest.entity.npc;

import net.minecraft.nbt.NBTTagCompound;

class TimeCounter {

   public String name;
   public int time;


   public TimeCounter(String name, int time) {
      this.name = name;
      this.time = time;
   }

   public TimeCounter(NBTTagCompound tag) {
      this.name = tag.getString("name");
      this.time = tag.getInteger("t");
   }

   public void saveToNBT(NBTTagCompound tag) {
      tag.setString("name", this.name);
      tag.setInteger("t", this.time);
   }

   public void update() {
      if(this.time > 0) {
         --this.time;
      }

   }
}
