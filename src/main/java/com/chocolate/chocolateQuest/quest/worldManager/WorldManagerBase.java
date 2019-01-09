package com.chocolate.chocolateQuest.quest.worldManager;

import java.io.File;
import java.io.IOException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class WorldManagerBase {

   public void read(World world, String fileName) {
      File file = new File(world.getSaveHandler().getWorldDirectory(), fileName);
      if(file.exists()) {
         try {
            NBTTagCompound tag = CompressedStreamTools.read(file);
            this.readFromNBT(tag);
         } catch (IOException var6) {
            var6.printStackTrace();
         }
      }

   }

   public void save(World world, String filePath, String fileName) {
      File saveFile = world.getSaveHandler().getWorldDirectory();
      File file = new File(saveFile, filePath);
      if(!file.exists()) {
         file.mkdir();
      }

      File managerFile = new File(file, fileName);
      NBTTagCompound tag = new NBTTagCompound();
      this.writeToNBT(tag);

      try {
         CompressedStreamTools.safeWrite(tag, managerFile);
      } catch (IOException var9) {
         var9.printStackTrace();
      }

   }

   protected void readFromNBT(NBTTagCompound tag) {}

   protected void writeToNBT(NBTTagCompound tag) {}
}
