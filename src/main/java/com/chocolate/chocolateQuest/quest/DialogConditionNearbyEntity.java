package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogCondition;
import com.chocolate.chocolateQuest.quest.worldManager.KillCounter;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class DialogConditionNearbyEntity extends DialogCondition {

   public boolean matches(EntityPlayer player, EntityHumanNPC npc) {
      List list = npc.worldObj.getEntitiesWithinAABBExcludingEntity(npc, AxisAlignedBB.getBoundingBox(npc.posX - (double)super.value, npc.posY - (double)super.value, npc.posZ - (double)super.value, npc.posX + (double)super.value, npc.posY + (double)super.value, npc.posZ + (double)super.value));
      Iterator i$ = list.iterator();

      while(i$.hasNext()) {
         Entity e = (Entity)i$.next();
         if(e instanceof EntityLivingBase) {
            String[] vars = super.name.split(" ");
            String name = super.name;
            String sTags = null;
            if(vars.length > 0) {
               name = vars[0];
            }

            if(vars.length > 1) {
               sTags = vars[1];
            }

            if(name != null) {
               NBTTagCompound tag = null;
               if(sTags != null) {
                  try {
                     tag = (NBTTagCompound)BDHelper.JSONToNBT(sTags);
                  } catch (NBTException var11) {
                     var11.printStackTrace();
                  }
               }

               boolean matches = KillCounter.entityMatchesNameAndTags((EntityLivingBase)e, name, tag);
               if(matches) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public boolean hasName() {
      return true;
   }

   public boolean hasValue() {
      return true;
   }

   public String getNameForValue() {
      return "Radio";
   }

   public boolean hasOperator() {
      return false;
   }

   public void getSuggestions(List list) {
      list.add("This condition will pass if there are any nearby entities with the");
      list.add("specified id and data, sintax is ENTITY_ID {ENTITY_DATA}, use:");
      list.add("chocolateQuest.CQ_npc {nameID:\"innkeeper\"}");
      list.add("to search a nearby NPC tagged as innkeeper");
   }
}
