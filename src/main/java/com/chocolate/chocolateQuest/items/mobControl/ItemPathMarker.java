package com.chocolate.chocolateQuest.items.mobControl;

import com.chocolate.chocolateQuest.entity.EntityCursor;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.utils.HelperPlayer;
import com.chocolate.chocolateQuest.utils.Vec4I;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemPathMarker extends Item {

   ArrayList cursors = new ArrayList();
   ItemStack currentItem;


   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityPlayer) {
      if(entityPlayer.isSneaking() && itemstack.stackTagCompound != null) {
         this.spawnCursors(world, itemstack);
         this.removePoint(itemstack, entityPlayer);
      } else {
         HelperPlayer.getTarget(entityPlayer, world, 6.0D);
         MovingObjectPosition mop = HelperPlayer.getMovingObjectPositionFromPlayer(entityPlayer, world, 50.0D);
         if(mop != null && mop.entityHit == null) {
            if(itemstack.stackTagCompound == null) {
               itemstack.stackTagCompound = new NBTTagCompound();
               itemstack.setItemDamage(Item.itemRand.nextInt(16));
            }

            if(this.addPoint(itemstack, entityPlayer, mop.blockX, mop.blockY, mop.blockZ) && world.isRemote) {
               this.spawnCursors(world, itemstack);
            }
         }
      }

      return super.onItemRightClick(itemstack, world, entityPlayer);
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
            this.spawnCursors(world, this.currentItem);
         }
      }

      super.onUpdate(itemstack, world, par3Entity, par4, par5);
   }

   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
      if(entity instanceof EntityHumanBase && stack.stackTagCompound != null && player.capabilities.isCreativeMode) {
         int points = stack.stackTagCompound.getInteger("pos");
         if(points < 2) {
            return false;
         }

         Vec4I[] path = new Vec4I[points];

         for(int p = 0; p < points; ++p) {
            path[p] = new Vec4I(stack.stackTagCompound.getInteger("x" + p), stack.stackTagCompound.getInteger("y" + p), stack.stackTagCompound.getInteger("z" + p), 0);
         }

         ((EntityHumanBase)entity).path = path;
         if(player.worldObj.isRemote) {
            player.addChatMessage(new ChatComponentText("Assigned path for " + EnumChatFormatting.DARK_GREEN + entity.getCommandSenderName()));
         }
      }

      return true;
   }

   public boolean hasEffect(ItemStack par1ItemStack) {
      return par1ItemStack.stackTagCompound != null;
   }

   public boolean addPoint(ItemStack itemstack, EntityPlayer entityPlayer, int blockX, int blockY, int blockZ) {
      int point = itemstack.stackTagCompound.getInteger("pos");
      if(point >= this.getMaxPoints(itemstack)) {
         entityPlayer.addChatMessage(new ChatComponentText("Can\'t add more points"));
         return false;
      } else {
         if(point > 0) {
            int x = itemstack.stackTagCompound.getInteger("x" + (point - 1));
            int y = itemstack.stackTagCompound.getInteger("y" + (point - 1));
            int z = itemstack.stackTagCompound.getInteger("z" + (point - 1));
            x -= blockX;
            y -= blockY;
            z -= blockZ;
            double dist = Math.sqrt((double)(x * x + y * y + z * z));
            if(dist > 30.0D) {
               entityPlayer.addChatMessage(new ChatComponentText("Too far from previous point"));
               return false;
            }
         }

         itemstack.stackTagCompound.setInteger("x" + point, blockX);
         itemstack.stackTagCompound.setInteger("y" + point, blockY);
         itemstack.stackTagCompound.setInteger("z" + point, blockZ);
         itemstack.stackTagCompound.setInteger("rot" + point, (int)entityPlayer.rotationYaw);
         itemstack.stackTagCompound.setInteger("pos", point + 1);
         return true;
      }
   }

   public int getMaxPoints(ItemStack is) {
      return 32;
   }

   public boolean removePoint(ItemStack itemstack, EntityPlayer entityPlayer) {
      if(itemstack.stackTagCompound != null) {
         int currentPoint = Math.max(0, itemstack.stackTagCompound.getInteger("pos") - 1);
         itemstack.stackTagCompound.setInteger("pos", currentPoint);
         if(currentPoint > 0 && entityPlayer.worldObj.isRemote) {
            EntityCursor e = (EntityCursor)this.cursors.get(currentPoint);
            if(e != null) {
               e.item = null;
               e.setDead();
            }
         }

         if(currentPoint == 0) {
            itemstack.stackTagCompound = null;
         }
      }

      return true;
   }

   public void spawnCursors(World world, ItemStack itemstack) {
      if(world.isRemote && itemstack.stackTagCompound != null) {
         Iterator points = this.cursors.iterator();

         while(points.hasNext()) {
            EntityCursor p = (EntityCursor)points.next();
            p.item = null;
            p.setDead();
         }

         this.cursors.clear();
         int var6 = itemstack.stackTagCompound.getInteger("pos");

         for(int var7 = 0; var7 < var6; ++var7) {
            EntityCursor c = new EntityCursor(world, (double)itemstack.stackTagCompound.getInteger("x" + var7) + 0.5D, (double)(itemstack.stackTagCompound.getInteger("y" + var7) + 1), (double)itemstack.stackTagCompound.getInteger("z" + var7) + 0.5D, (float)itemstack.stackTagCompound.getInteger("rot" + var7), itemstack);
            if(var7 >= 1) {
               ((EntityCursor)this.cursors.get(var7 - 1)).next = c;
            }

            this.cursors.add(c);
            world.spawnEntityInWorld(c);
         }
      }

   }
}
