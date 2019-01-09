package com.chocolate.chocolateQuest.entity;

import com.chocolate.chocolateQuest.ChocolateQuest;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class EntityReferee extends EntityCreature {

   List<EntityLivingBase> trackingPlayers;
   List<EntityLivingBase> trackingMobs;
   int lifeTime;


   public EntityReferee(World par1World) {
      super(par1World);
      this.lifeTime = 0;
      super.experienceValue = 0;
      this.initTasks();

      for(int i = 0; i < super.equipmentDropChances.length; ++i) {
         super.equipmentDropChances[i] = 0.0F;
      }

      this.setCurrentItemOrArmor(0, new ItemStack(ChocolateQuest.egg, 1, 499));
   }

   public EntityReferee(World world, List<EntityLivingBase> trackingPlayers, List<EntityLivingBase> trackingMobs) {
      this(world);
      this.trackingMobs = trackingMobs;
      this.trackingPlayers = trackingPlayers;
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
      this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.33D);
   }

   protected void initTasks() {}

   public void onLivingUpdate() {
      boolean lost = false;
      boolean win = false;
      ListIterator<EntityLivingBase> i$;
      Entity e;
      EntityPlayer player;
      if(this.trackingPlayers != null) {
         i$ = this.trackingPlayers.listIterator();

         while(i$.hasNext()) {
            e = (Entity)i$.next();
            if(e.isDead) {
               i$.remove();
               player = (EntityPlayer)e;
               player.addChatMessage(new ChatComponentText("Game Over. Try again!"));
            }
         }

         if(this.trackingPlayers.isEmpty()) {
            lost = true;
         }
      }

      if(this.trackingMobs != null) {
         i$ = this.trackingMobs.listIterator();

         while(i$.hasNext()) {
            e = (Entity)i$.next();
            if(e.isDead) {
               i$.remove();
            }
         }

         if(this.trackingMobs.isEmpty()) {
            win = true;
         }
      }

      if(!super.worldObj.isRemote && this.trackingMobs == null && this.trackingPlayers == null) {
         this.setDead();
      }

      Iterator i$1;
      if(lost) {
         this.setDead();

         for(i$1 = this.trackingPlayers.iterator(); i$1.hasNext(); e = (Entity)i$1.next()) {
            ;
         }

         i$1 = this.trackingMobs.iterator();

         while(i$1.hasNext()) {
            e = (Entity)i$1.next();
            e.setDead();
         }
      }

      if(win) {
         this.setDead();
         i$1 = this.trackingPlayers.iterator();

         while(i$1.hasNext()) {
            e = (Entity)i$1.next();
            player = (EntityPlayer)e;
            boolean MINUTE = true;
            float TICK_PER_SEC = 20.0F;
            float timef = (float)super.ticksExisted / 20.0F;
            String time = (int)(timef / 60.0F) + "m " + (int)(timef % 60.0F) + "s ";
            player.addChatMessage(new ChatComponentText("Winner!!! " + time));
         }
      }

      super.onLivingUpdate();
   }

   public void setDead() {
      if(super.worldObj.isRemote) {
         for(int r = 0; r < 30; ++r) {
            super.worldObj.spawnParticle("smoke", super.posX + (double)super.rand.nextFloat() - 0.5D, super.posY + (double)(super.rand.nextFloat() * 2.0F), super.posZ + (double)super.rand.nextFloat() - 0.5D, 0.0D, 0.0D, 0.0D);
         }
      }

      super.setDead();
   }

   protected void attackEntity(Entity par1Entity, float par2) {
      if(par1Entity instanceof EntityPlayer) {
         super.attackEntity(par1Entity, par2);
      }

   }

   public boolean isAIEnabled() {
      return true;
   }
}
