package com.chocolate.chocolateQuest.quest.worldManager;

import com.chocolate.chocolateQuest.builder.BuilderHelper;
import com.chocolate.chocolateQuest.builder.schematic.Schematic;
import com.chocolate.chocolateQuest.quest.worldManager.SchematicStorage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class OnTickBuilder {

   int mobID;
   boolean replaceBanner;
   int buildingStep;
   Schematic cachedSchematic;
   int schematicSize;
   int schematicStep;
   List buildingPlans = new ArrayList();
   Random rand = new Random();


   protected void setSchematicSize(int size) {
      this.schematicSize = size;
   }

   protected int getSchematicSize() {
      return this.schematicSize;
   }

   protected void setSchematicStep(int size) {
      this.schematicStep = size;
   }

   protected int getSchematicStep() {
      return this.schematicStep;
   }

   public void setMobID(int idMob, boolean replaceBanners) {
      this.mobID = idMob;
      this.replaceBanner = replaceBanners;
   }

   public void addBuildingPlans(Schematic schematic, int posX, int posY, int posZ) {
      SchematicStorage storage = new SchematicStorage(posX, posY, posZ);
      storage.schematic = schematic;
      this.buildingPlans.add(storage);
      this.schematicSize += schematic.width * schematic.length * schematic.height * 2;
   }

   public void onUpdate(World worldObj) {
      if(worldObj.isRemote && this.getSchematicSize() > 0) {
         BuilderHelper.builderHelper.setStructureGenerationAmmount(this.getSchematicStep() * 100 / this.getSchematicSize());
      }

      short speed = 2000;
      if(this.cachedSchematic == null) {
         this.startBuilding();
      } else {
         int schematicSize = this.cachedSchematic.width * this.cachedSchematic.length * this.cachedSchematic.height;

         for(int i = 0; i < speed; ++i) {
            int step = this.buildingStep;
            if(step < schematicSize * 2) {
               boolean entityIndex = step < schematicSize;
               if(!entityIndex) {
                  step -= schematicSize;
               }

               if(BuilderHelper.builderHelper.buildStep(this.rand, worldObj, this.cachedSchematic, step, entityIndex, this.mobID, true, this.replaceBanner)) {
                  ;
               }

               ++this.buildingStep;
               this.setSchematicStep(this.getSchematicStep() + 1);
            } else {
               int var7 = step - schematicSize * 2;
               if(!BuilderHelper.builderHelper.putEntities(worldObj, this.cachedSchematic, var7)) {
                  this.nextSchematic();
                  return;
               }

               ++this.buildingStep;
            }
         }

      }
   }

   protected void nextSchematic() {
      this.buildingStep = 0;
      this.buildingPlans.remove(0);
      this.cachedSchematic = null;
   }

   protected void startBuilding() {
      if(this.buildingPlans.size() > 0) {
         SchematicStorage currentPlans = (SchematicStorage)this.buildingPlans.get(0);
         this.cachedSchematic = currentPlans.schematic;
         this.cachedSchematic.setPosition(currentPlans.posX, currentPlans.posY, currentPlans.posZ);
      } else {
         this.reset();
      }

   }

   public void reset() {}

   protected void readFromNBT(NBTTagCompound tag) {
      this.buildingStep = tag.getInteger("step");
      this.mobID = tag.getInteger("mobID");
      this.replaceBanner = tag.getBoolean("banner");
      NBTTagList list = tag.getTagList("plans", 10);

      for(int i = 0; i < list.tagCount(); ++i) {
         NBTTagCompound t = list.getCompoundTagAt(i);
         SchematicStorage storage = new SchematicStorage(t.getInteger("x"), t.getInteger("y"), t.getInteger("z"));
         storage.schematic = new Schematic(t.getCompoundTag("schematic"));
         this.buildingPlans.add(storage);
      }

   }

   protected void writeToNBT(NBTTagCompound tag) {
      tag.setInteger("step", this.buildingStep);
      tag.setInteger("mobID", this.mobID);
      tag.setBoolean("banner", this.replaceBanner);
      NBTTagList list = new NBTTagList();
      Iterator i$ = this.buildingPlans.iterator();

      while(i$.hasNext()) {
         SchematicStorage buildingPlan = (SchematicStorage)i$.next();
         NBTTagCompound buildingPlanTag = new NBTTagCompound();
         NBTTagCompound schematicTag = new NBTTagCompound();
         buildingPlan.schematic.saveToNBT(schematicTag);
         buildingPlanTag.setTag("schematic", schematicTag);
         buildingPlanTag.setInteger("x", buildingPlan.posX);
         buildingPlanTag.setInteger("y", buildingPlan.posY);
         buildingPlanTag.setInteger("z", buildingPlan.posZ);
         list.appendTag(buildingPlanTag);
      }

      tag.setTag("plans", list);
   }
}
