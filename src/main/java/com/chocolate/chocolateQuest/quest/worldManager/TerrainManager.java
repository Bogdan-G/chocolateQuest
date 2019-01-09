package com.chocolate.chocolateQuest.quest.worldManager;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.quest.worldManager.NamedCoordinates;
import com.chocolate.chocolateQuest.quest.worldManager.WorldManagerBase;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChunkCoordinates;

public class TerrainManager extends WorldManagerBase {

   public static TerrainManager instance;
   int territorySeparation;
   List uniqueDungeonsSpawned;
   List teleportPoints;


   public TerrainManager(int territorySeparation) {
      this.territorySeparation = territorySeparation;
      this.uniqueDungeonsSpawned = new ArrayList();
      this.teleportPoints = new LinkedList();
   }

   public static TerrainManager getInstance() {
      if(instance == null) {
         instance = new TerrainManager(ChocolateQuest.config.dungeonSeparation);
      }

      return instance;
   }

   public static int getTerritorySeparation() {
      return instance == null?ChocolateQuest.config.dungeonSeparation:instance.territorySeparation;
   }

   public boolean isDungeonSpawned(String name) {
      return this.uniqueDungeonsSpawned.contains(name);
   }

   public void dungeonSpawned(String name) {
      this.uniqueDungeonsSpawned.add(name);
   }

   public void addTeleportPoint(String name, ChunkCoordinates coords) {
      Iterator i$ = this.teleportPoints.iterator();

      NamedCoordinates coord;
      do {
         if(!i$.hasNext()) {
            this.teleportPoints.add(new NamedCoordinates(coords.posX, coords.posY, coords.posZ, name));
            return;
         }

         coord = (NamedCoordinates)i$.next();
      } while(!coord.name.equals(name));

      coord.x = coords.posX;
      coord.y = coords.posY;
      coord.z = coords.posZ;
   }

   public ChunkCoordinates getTeleportPoint(String name) {
      Iterator i$ = this.teleportPoints.iterator();

      NamedCoordinates coords;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         coords = (NamedCoordinates)i$.next();
      } while(!coords.name.equals(name));

      return new ChunkCoordinates(coords.x, coords.y, coords.z);
   }

   public void readFromNBT(NBTTagCompound tag) {
      this.territorySeparation = tag.getInteger("Separation");
      NBTTagList list = (NBTTagList)tag.getTag("dungeons");
      int tags;
      int i;
      if(list != null) {
         tags = list.tagCount();
         if(tags > 0) {
            for(i = 0; i < tags; ++i) {
               this.uniqueDungeonsSpawned.add(list.getCompoundTagAt(i).getString("name"));
            }
         }
      }

      if(tag.hasKey("teleportPoints")) {
         list = (NBTTagList)tag.getTag("teleportPoints");
         if(list != null) {
            tags = list.tagCount();
            if(tags > 0) {
               for(i = 0; i < tags; ++i) {
                  NBTTagCompound tempTag = list.getCompoundTagAt(i);
                  int x = tempTag.getInteger("x");
                  int y = tempTag.getInteger("y");
                  int z = tempTag.getInteger("z");
                  String name = tempTag.getString("name");
                  NamedCoordinates coords = new NamedCoordinates(x, y, z, name);
                  this.teleportPoints.add(coords);
               }
            }
         }
      }

   }

   public void writeToNBT(NBTTagCompound tag) {
      tag.setInteger("Separation", this.territorySeparation);
      NBTTagList list = new NBTTagList();

      int i;
      NBTTagCompound tempTag;
      for(i = 0; i < this.uniqueDungeonsSpawned.size(); ++i) {
         tempTag = new NBTTagCompound();
         tempTag.setString("name", (String)this.uniqueDungeonsSpawned.get(i));
         list.appendTag(tempTag);
      }

      tag.setTag("dungeons", list);
      if(!this.teleportPoints.isEmpty()) {
         list = new NBTTagList();

         for(i = 0; i < this.teleportPoints.size(); ++i) {
            tempTag = new NBTTagCompound();
            NamedCoordinates coords = (NamedCoordinates)this.teleportPoints.get(i);
            tempTag.setString("name", coords.name);
            tempTag.setInteger("x", coords.x);
            tempTag.setInteger("y", coords.y);
            tempTag.setInteger("z", coords.z);
            list.appendTag(tempTag);
         }

         tag.setTag("teleportPoints", list);
      }

   }
}
