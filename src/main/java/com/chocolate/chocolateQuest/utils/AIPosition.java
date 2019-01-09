package com.chocolate.chocolateQuest.utils;

import net.minecraft.nbt.NBTTagCompound;

public class AIPosition {

   public int xCoord;
   public int yCoord;
   public int zCoord;
   public int rot;
   public String name;


   public AIPosition() {}

   public AIPosition(int xCoord, int yCoord, int zCoord, int rot, String name) {
      this.xCoord = xCoord;
      this.yCoord = yCoord;
      this.zCoord = zCoord;
      this.rot = rot;
      this.name = name;
   }

   public AIPosition(NBTTagCompound tag) {
      this.xCoord = tag.getInteger("x");
      this.yCoord = tag.getInteger("y");
      this.zCoord = tag.getInteger("z");
      this.rot = tag.getInteger("r");
      this.name = tag.getString("name");
   }

   public void saveToNBT(NBTTagCompound tag) {
      tag.setInteger("x", this.xCoord);
      tag.setInteger("y", this.yCoord);
      tag.setInteger("z", this.zCoord);
      tag.setInteger("r", this.rot);
      tag.setString("name", this.name);
   }
}
