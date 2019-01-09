package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.items.mobControl.ItemMobToSpawner;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemSoulBottle extends Item {

   public static final String ENTITY_NAMETAG = "entity";
   public static final String NAME_NAMETAG = "itemName";


   public ItemSoulBottle() {
      this.setMaxStackSize(1);
   }

   public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
      return super.onItemRightClick(stack, world, player);
   }

   public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int f1, float f2, float f3, float f4) {
      if(!world.isRemote && stack.stackTagCompound != null) {
         NBTTagCompound tag = (NBTTagCompound)stack.getTagCompound().getTag("entity");
         Entity entity = createEntityFromNBT(tag, world, x, y + 1, z);
         if(entity != null) {
            world.spawnEntityInWorld(entity);
         }

         if(!player.capabilities.isCreativeMode) {
            stack.stackTagCompound = null;
            stack.stackSize = 0;
         }
      }

      return super.onItemUse(stack, player, world, x, y, z, f1, f2, f3, f4);
   }

   public static Entity createEntityFromNBT(NBTTagCompound tag, World world, int x, int y, int z) {
      Entity entity = EntityList.createEntityFromNBT(tag, world);
      if(entity instanceof EntityHumanBase) {
         EntityHumanBase human = (EntityHumanBase)entity;
         human.readEntityFromSpawnerNBT(tag, x, y, z);
         if(tag.getTag("Riding") != null) {
            NBTTagCompound ridingNBT = (NBTTagCompound)tag.getTag("Riding");
            Entity riding = EntityList.createEntityFromNBT(ridingNBT, world);
            if(riding != null) {
               riding.setPosition((double)x + 0.5D, (double)(y + 1), (double)z + 0.5D);
               world.spawnEntityInWorld(riding);
               human.mountEntity(riding);
               if(riding instanceof EntityHumanBase) {
                  ((EntityHumanBase)riding).entityTeam = human.entityTeam;
               }
            }
         }
      } else {
         entity.readFromNBT(tag);
      }

      if(entity != null) {
         entity.posX = (double)x + 0.5D;
         entity.posY = (double)(y + 1);
         entity.posZ = (double)z + 0.5D;
         entity.setPosition(entity.posX, entity.posY, entity.posZ);
      }

      return entity;
   }

   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
      if(stack.stackTagCompound == null) {
         NBTTagCompound tag = new NBTTagCompound();
         tag.setString("itemName", entity.getCommandSenderName());
         if(entity instanceof EntityHumanBase) {
            int entityTag = MathHelper.floor_double(entity.posX);
            int y = MathHelper.floor_double(entity.posY);
            int z = MathHelper.floor_double(entity.posZ);
            tag.setTag("entity", ItemMobToSpawner.getHumanSaveTagAndKillIt(entityTag, y, z, (EntityHumanBase)entity));
         } else {
            NBTTagCompound entityTag1 = new NBTTagCompound();
            if(entity.writeToNBTOptional(entityTag1)) {
               tag.setTag("entity", entityTag1);
               entity.setDead();
            }
         }

         if(player.worldObj.isRemote) {
            BDHelper.println(tag.getTag("entity").toString());
         }

         stack.stackTagCompound = tag;
      }

      return true;
   }

   public String getItemStackDisplayName(ItemStack itemstack) {
      return itemstack.stackTagCompound != null?super.getItemStackDisplayName(itemstack) + ": " + itemstack.stackTagCompound.getString("itemName"):super.getItemStackDisplayName(itemstack);
   }
}
