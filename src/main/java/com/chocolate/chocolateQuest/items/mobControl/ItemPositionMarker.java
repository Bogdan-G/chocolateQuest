package com.chocolate.chocolateQuest.items.mobControl;

import com.chocolate.chocolateQuest.entity.EntityCursor;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.utils.AIPosition;
import com.chocolate.chocolateQuest.utils.HelperPlayer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemPositionMarker extends Item {

   static final String TAG_NON_EMPTY = "has_position";
   EntityCursor cursor;
   ItemStack currentItem;


   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityPlayer) {
      HelperPlayer.getTarget(entityPlayer, world, 6.0D);
      MovingObjectPosition mop = HelperPlayer.getMovingObjectPositionFromPlayer(entityPlayer, world, 50.0D);
      if(mop != null && mop.entityHit == null) {
         if(itemstack.stackTagCompound == null) {
            itemstack.stackTagCompound = new NBTTagCompound();
         }

         if(this.addPoint(itemstack, mop.blockX, mop.blockY, mop.blockZ, (int)entityPlayer.rotationYawHead)) {
            itemstack.setItemDamage(Item.itemRand.nextInt(16));
            if(world.isRemote) {
               this.spawnCursor(world, itemstack);
            }
         }
      }

      return super.onItemRightClick(itemstack, world, entityPlayer);
   }

   public void addInformation(ItemStack is, EntityPlayer player, List list, boolean par4) {
      super.addInformation(is, player, list, par4);
      if(this.hasPosition(is)) {
         list.add("X: " + is.stackTagCompound.getInteger("x"));
         list.add("Y: " + is.stackTagCompound.getInteger("y"));
         list.add("Z: " + is.stackTagCompound.getInteger("z"));
         list.add("Rotation: " + is.stackTagCompound.getInteger("rot"));
      }

   }

   private void removePoint(ItemStack itemstack, EntityPlayer entityPlayer) {}

   public boolean addPoint(ItemStack itemstack, int blockX, int blockY, int blockZ, int rot) {
      if(itemstack.stackTagCompound == null) {
         itemstack.stackTagCompound = new NBTTagCompound();
      }

      itemstack.stackTagCompound.setInteger("x", blockX);
      itemstack.stackTagCompound.setInteger("y", blockY);
      itemstack.stackTagCompound.setInteger("z", blockZ);
      itemstack.stackTagCompound.setInteger("rot", rot);
      itemstack.stackTagCompound.setBoolean("has_position", true);
      return false;
   }

   public void addPoint(ItemStack is, AIPosition position) {
      this.addPoint(is, position.xCoord, position.yCoord, position.zCoord, position.rot);
      NBTTagCompound tagDisplay = new NBTTagCompound();
      tagDisplay.setString("Name", position.name);
      is.stackTagCompound.setTag("display", tagDisplay);
   }

   public void onUpdate(ItemStack itemstack, World world, Entity par3Entity, int par4, boolean par5) {
      if(world.isRemote && par3Entity instanceof EntityPlayer) {
         boolean itemChanged = false;
         EntityPlayer player = (EntityPlayer)par3Entity;
         if(player.inventory.getCurrentItem() != null) {
            ItemStack playerCurrentItem = player.inventory.getCurrentItem();
            if(playerCurrentItem.getItem() == this) {
               if(this.currentItem == null || !playerCurrentItem.isItemEqual(this.currentItem)) {
                  this.currentItem = playerCurrentItem;
                  itemChanged = true;
               }
            } else {
               itemChanged = true;
               this.currentItem = null;
            }
         } else {
            this.currentItem = null;
         }

         if(itemChanged && this.currentItem != null) {
            this.spawnCursor(world, this.currentItem);
         }
      }

      super.onUpdate(itemstack, world, par3Entity, par4, par5);
   }

   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
      if(entity instanceof EntityHumanNPC && stack.stackTagCompound != null) {
         NBTTagCompound tag = stack.stackTagCompound;
         EntityHumanNPC npc = (EntityHumanNPC)entity;
         if(npc.AIPositions == null) {
            npc.AIPositions = new ArrayList();
         }

         String name = "position" + npc.AIPositions.size();
         if(tag.hasKey("display")) {
            NBTTagCompound position = tag.getCompoundTag("display");
            if(position.hasKey("Name")) {
               name = position.getString("Name");
            }
         }

         AIPosition var9 = new AIPosition(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"), tag.getInteger("rot"), name);

         for(int i = 0; i < npc.AIPositions.size(); ++i) {
            if(((AIPosition)npc.AIPositions.get(i)).name.equals(name)) {
               npc.AIPositions.set(i, var9);
               if(player.worldObj.isRemote) {
                  player.addChatMessage(new ChatComponentText("Replaced position " + name + " to " + EnumChatFormatting.DARK_GREEN + entity.getCommandSenderName()));
               }

               return true;
            }
         }

         npc.AIPositions.add(var9);
         if(player.worldObj.isRemote) {
            player.addChatMessage(new ChatComponentText("Assigned position to " + EnumChatFormatting.DARK_GREEN + entity.getCommandSenderName()));
         }
      }

      return true;
   }

   public boolean hasPosition(ItemStack par1ItemStack) {
      return par1ItemStack.stackTagCompound == null?false:par1ItemStack.stackTagCompound.hasKey("has_position");
   }

   public boolean hasEffect(ItemStack par1ItemStack) {
      return par1ItemStack.stackTagCompound != null;
   }

   public void spawnCursor(World world, ItemStack itemstack) {
      if(world.isRemote && this.hasPosition(itemstack)) {
         if(this.cursor != null) {
            this.cursor.item = null;
            this.cursor.setDead();
         }

         this.cursor = new EntityCursor(world, (double)itemstack.stackTagCompound.getInteger("x") + 0.5D, (double)(itemstack.stackTagCompound.getInteger("y") + 1), (double)itemstack.stackTagCompound.getInteger("z") + 0.5D, (float)itemstack.stackTagCompound.getInteger("rot"), itemstack);
         world.spawnEntityInWorld(this.cursor);
      }

   }
}
