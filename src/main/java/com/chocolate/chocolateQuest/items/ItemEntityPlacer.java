package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.entity.EntityDungeonCrystal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEntityPlacer extends Item {

   public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float fx, float f3, float fz) {
      if(player.isSneaking()) {
         fz = 0.5F;
         fx = 0.5F;
      }

      float width = 0.1F;
      switch(side) {
      case 0:
         --y;
      case 1:
      default:
         break;
      case 2:
         fz -= width;
         break;
      case 3:
         fz += width;
         break;
      case 4:
         fx -= width;
         break;
      case 5:
         fx += width;
      }

      if(!world.isRemote) {
         Entity e = this.getEntity(stack, player, world, x, y, z);
         e.setPosition((double)((float)x + fx), (double)((float)y + f3), (double)((float)z + fz));
         world.spawnEntityInWorld(e);
      }

      if(!player.capabilities.isCreativeMode) {
         player.inventory.decrStackSize(player.inventory.currentItem, 1);
      }

      return true;
   }

   public Entity getEntity(ItemStack stack, EntityPlayer player, World world, int x, int y, int z) {
      return new EntityDungeonCrystal(world);
   }
}
