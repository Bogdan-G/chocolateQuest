package com.chocolate.chocolateQuest.quest.worldManager;

import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

public class KillCounter {

   public String playerName;
   public String name;
   private String monster;
   private NBTTagCompound tags;
   public int killAmmount;


   public KillCounter() {}

   public KillCounter(String playerName, String name, String monster, String tags) {
      this.playerName = playerName;
      this.name = name;
      this.monster = monster;
      if(tags != null) {
         try {
            this.tags = (NBTTagCompound)BDHelper.JSONToNBT(tags);
         } catch (NBTException var6) {
            var6.printStackTrace();
         }
      }

   }

   public boolean entityMatchesCounter(EntityLivingBase entity) {
      return entityMatchesNameAndTags(entity, this.monster, this.tags);
   }

   public static boolean entityMatchesNameAndTags(EntityLivingBase entity, String monsterName, NBTTagCompound tags) {
      String entityName = EntityList.getEntityString(entity);
      if(entityName == null) {
         return false;
      } else if(entityName.equals(monsterName)) {
         if(tags != null) {
            NBTTagCompound tagEntity = new NBTTagCompound();
            entity.writeEntityToNBT(tagEntity);
            if(!BDHelper.compareTags(tags, tagEntity)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public void writeToNBT(NBTTagCompound tag) {
      tag.setString("name", this.name);
      tag.setString("playerName", this.playerName);
      tag.setString("monster", this.monster);
      if(this.tags != null) {
         tag.setTag("data", this.tags);
      }

   }

   public void readFromNBT(NBTTagCompound tag) {
      this.name = tag.getString("name");
      this.playerName = tag.getString("playerName");
      this.monster = tag.getString("monster");
      if(tag.hasKey("data")) {
         this.tags = (NBTTagCompound)tag.getTag("data");
      }

   }
}
