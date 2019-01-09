package com.chocolate.chocolateQuest.entity;

import com.chocolate.chocolateQuest.gui.guiParty.GuiParty;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityCursor extends Entity {

   int lifeTime;
   public Entity followEntity;
   public ItemStack item;
   public Entity next;
   public int type;
   public static final int flag = 0;
   public static final int selector = 1;
   public int color;
   GuiParty guiParty;


   public EntityCursor(World par1World) {
      super(par1World);
      this.lifeTime = 40;
      this.type = 0;
      this.color = '\uff00';
   }

   public EntityCursor(World par1World, double x, double y, double z, float rotationYaw, ItemStack item) {
      this(par1World, x, y, z, rotationYaw);
      this.item = item;
      this.setPositionAndRotation(x, y + 1.0D, z, rotationYaw, 0.0F);
   }

   public EntityCursor(World par1World, double x, double y, double z, float rotationYaw) {
      super(par1World);
      this.lifeTime = 40;
      this.type = 0;
      this.color = '\uff00';
      this.setPositionAndRotation(x, y + 1.0D, z, rotationYaw, 0.0F);
   }

   public EntityCursor(World par1World, Entity entity, int color, GuiParty guiParty) {
      super(par1World);
      this.lifeTime = 40;
      this.type = 0;
      this.color = '\uff00';
      this.followEntity = entity;
      this.guiParty = guiParty;
      this.color = color;
      this.setPositionAndRotation(entity.posX, entity.posY + 1.0D, entity.posZ, super.rotationYaw, 0.0F);
      this.type = 1;
   }

   public EntityCursor(World par1World, Entity entity, ItemStack item) {
      super(par1World);
      this.lifeTime = 40;
      this.type = 0;
      this.color = '\uff00';
      this.followEntity = entity;
      this.item = item;
      this.setPositionAndRotation(entity.posX, entity.posY + 1.0D, entity.posZ, super.rotationYaw, 0.0F);
   }

   public EntityCursor(World par1World, Entity entity, ItemStack item, int type) {
      this(par1World, entity, item);
      this.type = type;
   }

   public EntityCursor(World par1World, Entity entity, ItemStack item, int type, Entity entityPointingTo) {
      this(par1World, entity, item, type);
      this.next = entityPointingTo;
   }

   protected void entityInit() {
      this.setSize(0.1F, 2.0F);
   }

   public void onUpdate() {
      super.onUpdate();
      if(super.ticksExisted < 5) {
         super.posY -= 0.2D;
      }

      if(super.ticksExisted > this.lifeTime) {
         this.setDead();
      }

      if(this.followEntity != null) {
         super.posX = this.followEntity.posX;
         super.posZ = this.followEntity.posZ;
         if(super.ticksExisted > 5) {
            super.posY = this.followEntity.posY + 0.01D;
         }

         super.rotationYaw = this.followEntity.rotationYaw;
         if(super.ticksExisted > this.lifeTime) {
            this.setDead();
         }
      }

      this.setPosition(super.posX, super.posY, super.posZ);
      if(this.next != null) {
         for(int i = 0; i < 4; ++i) {
            super.worldObj.spawnParticle("enchantmenttable", this.next.posX + (double)((super.rand.nextFloat() - 0.5F) / 2.0F), this.next.posY + (double)(super.rand.nextFloat() / 2.0F) + 1.5D, this.next.posZ + (double)((super.rand.nextFloat() - 0.5F) / 2.0F), super.posX - this.next.posX, super.posY - this.next.posY - 1.0D, super.posZ - this.next.posZ);
         }
      }

   }

   public void setDead() {
      if(this.followEntity != null) {
         this.lifeTime = super.ticksExisted + 20;
         if(this.followEntity.isDead) {
            super.setDead();
            return;
         }
      }

      if(this.guiParty == null || Minecraft.getMinecraft().currentScreen != this.guiParty) {
         if(this.item == null || Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() == null || !this.item.isItemEqual(Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem())) {
            super.setDead();
         }
      }
   }

   public void forceDead() {
      super.setDead();
   }

   protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {}

   protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {}
}
