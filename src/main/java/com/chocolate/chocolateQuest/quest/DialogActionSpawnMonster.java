package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.mob.EntityHumanMob;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.items.ItemSoulBottle;
import com.chocolate.chocolateQuest.quest.DialogAction;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;

public class DialogActionSpawnMonster extends DialogAction {

   static final int NORTH = 0;
   static final int SOUTH = 1;
   static final int EAST = 2;
   static final int WEST = 3;
   static final String[] directions = new String[]{"North", "South", "East", "West"};


   public void execute(EntityPlayer player, EntityHumanNPC npc) {
      NBTTagCompound tag = super.actionTag;
      if(tag != null) {
         NBTTagCompound entitytag = tag.getCompoundTag("entity");
         if(entitytag != null) {
            ChunkCoordinates coords = npc.getHomePosition();
            Entity entity = ItemSoulBottle.createEntityFromNBT(entitytag, npc.worldObj, coords.posX, coords.posY, coords.posZ);
            int x;
            int y;
            int z;
            if(super.value > 0) {
               x = MathHelper.floor_double((double)coords.posX);
               y = MathHelper.floor_double((double)coords.posY);
               z = MathHelper.floor_double((double)coords.posZ);
               if(super.operator == 0) {
                  z -= super.value;
               }

               if(super.operator == 1) {
                  z += super.value;
               }

               if(super.operator == 2) {
                  x += super.value;
               }

               if(super.operator == 3) {
                  x -= super.value;
               }

               while(npc.worldObj.getBlock(x, y, z).getMaterial() != Material.air && y < 256) {
                  ++y;
               }

               entity.setPosition((double)x, (double)y, (double)z);
            }

            if(entity instanceof EntityHumanMob) {
               x = MathHelper.floor_double(entity.posX);
               y = MathHelper.floor_double(entity.posY);
               z = MathHelper.floor_double(entity.posZ);
               ((EntityHumanMob)entity).setHomeArea(x, y, z, -1);
               ((EntityHumanMob)entity).saveToSpawner = false;
            }

            if(entity != null) {
               npc.worldObj.spawnEntityInWorld(entity);
            }
         }
      }

   }

   public boolean hasName() {
      return true;
   }

   public int getSelectorForName() {
      return 4;
   }

   public boolean hasValue() {
      return true;
   }

   public String getNameForValue() {
      return "Distance";
   }

   public boolean hasOperator() {
      return true;
   }

   public String getNameForOperator() {
      return "Direction";
   }

   public String[] getOptionsForOperator() {
      return directions;
   }

   public boolean hasTag() {
      return true;
   }

   public String getNameString() {
      return super.name;
   }

   public void getSuggestions(List list) {
      list.add("Spawns an entity in the npc home position.");
      list.add("To select an entity you need to have it in you inventory");
      list.add("stored inside a Soul Bottle.");
   }

}
