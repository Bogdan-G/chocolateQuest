package com.chocolate.chocolateQuest.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;

public class ItemQuestBook extends ItemEditableBook {

   public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5) {
      super.onUpdate(itemStack, world, entity, par4, par5);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:book");
   }

   public boolean onEntityItemUpdate(EntityItem entityItem) {
      return super.onEntityItemUpdate(entityItem);
   }

   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
      if(itemstack.stackTagCompound == null) {
         itemstack.stackTagCompound = new NBTTagCompound();
      }

      itemstack.setTagInfo("title", new NBTTagString("Arrr"));
      itemstack.setTagInfo("author", new NBTTagString("Chocolatin, the pirate captain"));
      new NBTTagList();
      return itemstack;
   }
}
