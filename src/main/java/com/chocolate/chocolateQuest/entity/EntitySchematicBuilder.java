package com.chocolate.chocolateQuest.entity;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.builder.BuilderHelper;
import com.chocolate.chocolateQuest.builder.schematic.Schematic;
import com.chocolate.chocolateQuest.entity.SchematicStorage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntitySchematicBuilder extends Entity {

   int mobID;
   boolean replaceBanner;
   int buildingStep;
   Schematic cachedSchematic;
   int schematicSize;
   boolean firstTick = true;
   List buildingPlans = new ArrayList();


   public EntitySchematicBuilder(World world) {
      super(world);
      this.setSize(0.1F, 0.1F);
   }

   protected void entityInit() {
      super.dataWatcher.addObject(16, Integer.valueOf(0));
      super.dataWatcher.addObject(17, Integer.valueOf(0));
   }

   protected void setSchematicSize(int size) {
      super.dataWatcher.updateObject(16, Integer.valueOf(size));
   }

   protected int getSchematicSize() {
      return super.dataWatcher.getWatchableObjectInt(16);
   }

   protected void setSchematicStep(int size) {
      super.dataWatcher.updateObject(17, Integer.valueOf(size));
   }

   protected int getSchematicStep() {
      return super.dataWatcher.getWatchableObjectInt(17);
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

   public void onUpdate() {
      if(super.worldObj.isRemote) {
         if(this.getSchematicSize() > 0) {
            BuilderHelper.builderHelper.setStructureGenerationAmmount(this.getSchematicStep() * 100 / this.getSchematicSize());
         }
      } else if(this.firstTick) {
         this.setSchematicSize(this.schematicSize);
         this.firstTick = false;
         return;
      }

      if(super.worldObj.getClosestPlayerToEntity(this, 128.0D) != null) {
         int speed = ChocolateQuest.config.builderSpeed;
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

                  BuilderHelper.builderHelper.buildStep(super.rand, super.worldObj, this.cachedSchematic, step, entityIndex, this.mobID, true, this.replaceBanner);
                  ++this.buildingStep;
                  this.setSchematicStep(this.getSchematicStep() + 1);
               } else {
                  int var6 = step - schematicSize * 2;
                  if(!BuilderHelper.builderHelper.putEntities(super.worldObj, this.cachedSchematic, var6, this.mobID, this.replaceBanner)) {
                     this.nextSchematic();
                     return;
                  }

                  ++this.buildingStep;
               }
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
      } else if(!super.worldObj.isRemote) {
         this.setDead();
      }

   }

   protected void readEntityFromNBT(NBTTagCompound tag) {
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

   protected void writeEntityToNBT(NBTTagCompound tag) {
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

   public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
      return false;
   }

   public boolean canBeCollidedWith() {
      return false;
   }

   public boolean isInvisible() {
      return true;
   }
}
