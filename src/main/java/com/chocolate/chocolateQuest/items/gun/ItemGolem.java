package com.chocolate.chocolateQuest.items.gun;

import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGolem extends Item {

   String icon;


   public ItemGolem(String name) {
      this.icon = name;
      this.setMaxStackSize(1);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:" + this.icon);
   }

   public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int i, int j, int k, int l, float f, float f1, float f2) {
      boolean spawnGolem = entityPlayer.capabilities.isCreativeMode;
      Block block = Blocks.iron_block;
      if(world.getBlock(i, j, k) == block && world.getBlock(i, j - 1, k) == block) {
         if(world.getBlock(i + 1, j, k) == block && world.getBlock(i - 1, j, k) == block) {
            world.setBlockToAir(i + 1, j, k);
            world.setBlockToAir(i - 1, j, k);
            spawnGolem = true;
         } else if(world.getBlock(i, j, k + 1) == block && world.getBlock(i, j, k - 1) == block) {
            world.setBlockToAir(i, j, k + 1);
            world.setBlockToAir(i, j, k - 1);
            spawnGolem = true;
         }
      }

      if(spawnGolem) {
         if(!entityPlayer.capabilities.isCreativeMode) {
            --itemStack.stackSize;
            world.setBlockToAir(i, j, k);
            world.setBlockToAir(i, j - 1, k);
         }

         if(!world.isRemote) {
            EntityGolemMecha golem = this.getGolem(world, entityPlayer);
            golem.setPosition((double)i, (double)(j + 1), (double)k);
            golem.setOwner(entityPlayer);
            world.spawnEntityInWorld(golem);
         }
      }

      return false;
   }

   public EntityGolemMecha getGolem(World world, EntityPlayer entityPlayer) {
      return new EntityGolemMecha(world, entityPlayer);
   }
}
