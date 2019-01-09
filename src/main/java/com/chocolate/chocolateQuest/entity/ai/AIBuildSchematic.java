package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.builder.BuilderHelper;
import com.chocolate.chocolateQuest.builder.schematic.Schematic;
import com.chocolate.chocolateQuest.entity.ai.SchematicStorage;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.utils.Vec4I;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChunkCoordinates;

public class AIBuildSchematic extends EntityAIBase {

   EntityHumanNPC owner;
   Schematic cachedSchematic;
   ChunkCoordinates home;
   public int buildingStep;
   boolean isFinished = false;
   int buildingDelay = 0;
   int buildSpeed = 1;
   List<SchematicStorage> buildingPlans = new ArrayList<SchematicStorage>();


   public AIBuildSchematic(EntityHumanNPC owner) {
      this.owner = owner;
      this.setMutexBits(7);
   }

   public boolean shouldExecute() {
      return this.hasBuildingPlans();
   }

   public boolean continueExecuting() {
      return this.cachedSchematic != null && !this.isFinished;
   }

   public void startExecuting() {
      SchematicStorage currentPlans = this.buildingPlans.get(0);
      this.cachedSchematic = BuilderHelper.getNBTMap(currentPlans.name);
      if(this.cachedSchematic != null) {
         Vec4I v = this.owner.getPositionByName(currentPlans.position);
         this.home = new ChunkCoordinates(v.xCoord, v.yCoord, v.zCoord);
         this.cachedSchematic.setPosition(this.home.posX, this.home.posY, this.home.posZ);
         this.buildSpeed = currentPlans.buildSpeed;
      } else {
         BDHelper.println("Can\'t find schematic " + currentPlans.name);
         this.buildingPlans.remove(0);
      }

   }

   public void resetTask() {
      this.isFinished = false;
      this.cachedSchematic = null;
      super.resetTask();
   }

   public void updateTask() {
      int speed = Math.max(1, this.buildSpeed);
      if(this.cachedSchematic != null) {
         for(int i = 0; i < speed; ++i) {
            if(this.buildingDelay > 0) {
               --this.buildingDelay;
            } else {
               boolean blockPlaced = false;

               while(!blockPlaced) {
                  int step = this.buildingStep;
                  int schematicSize = this.cachedSchematic.width * this.cachedSchematic.length * this.cachedSchematic.height;
                  if(step < schematicSize * 2) {
                     boolean entityIndex = step < schematicSize;
                     if(!entityIndex) {
                        step -= schematicSize;
                     }

                     if(BuilderHelper.builderHelper.buildStep(this.owner.getRNG(), this.owner.worldObj, this.cachedSchematic, step, entityIndex, 0, false, false)) {
                        this.owner.swingItem();
                        this.buildingDelay = 20;
                        blockPlaced = true;
                     }

                     ++this.buildingStep;
                  } else {
                     blockPlaced = true;
                     int var7 = step - schematicSize * 2;
                     if(!BuilderHelper.builderHelper.putEntities(this.owner.worldObj, this.cachedSchematic, var7)) {
                        this.isFinished = true;
                        this.buildingStep = 0;
                        this.buildingPlans.remove(0);
                        this.cachedSchematic = null;
                        return;
                     }

                     ++this.buildingStep;
                     this.buildingDelay = 20;
                  }
               }
            }
         }
      }

   }

   public boolean hasBuildingPlans() {
      return this.buildingPlans.size() > 0;
   }

   public void writeToNBT(NBTTagCompound tag) {
      tag.setInteger("step", this.buildingStep);
      NBTTagList list = new NBTTagList();
      Iterator<SchematicStorage> i$ = this.buildingPlans.iterator();

      while(i$.hasNext()) {
         SchematicStorage schematic = i$.next();
         NBTTagCompound schematicTag = new NBTTagCompound();
         schematicTag.setString("name", schematic.name);
         schematicTag.setString("pos", schematic.position);
         list.appendTag(schematicTag);
      }

      tag.setTag("plans", list);
   }

   public void readFromNBT(NBTTagCompound tag) {
      this.buildingStep = tag.getInteger("step");
      NBTTagList list = tag.getTagList("plans", 10);

      for(int i = 0; i < list.tagCount(); ++i) {
         NBTTagCompound t = list.getCompoundTagAt(i);
         this.buildingPlans.add(new SchematicStorage(t.getString("name"), t.getString("pos"), t.getInteger("speed")));
      }

   }

   public void addBuildingPlans(String schematicName, String position, int speed) {
      this.buildingPlans.add(new SchematicStorage(schematicName, position, speed));
   }
}
