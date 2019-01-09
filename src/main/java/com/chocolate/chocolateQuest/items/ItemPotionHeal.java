package com.chocolate.chocolateQuest.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPotionHeal extends Item {

   public ItemPotionHeal() {
      this.setHasSubtypes(true);
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player) {
      player.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
      return par1ItemStack;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:potion");
   }

   public String getItemStackDisplayName(ItemStack itemstack) {
      int i = itemstack.getItemDamage();
      String num = " ";
      switch(i) {
      case 1:
         num = " I";
         break;
      case 2:
         num = " II";
         break;
      case 3:
         num = " III";
         break;
      case 4:
         num = " IV";
         break;
      case 5:
         num = " V";
      }

      return super.getItemStackDisplayName(itemstack) + num;
   }

   public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i) {
      int var6 = this.getMaxItemUseDuration(itemstack) - i;
   }

   public int getMaxItemUseDuration(ItemStack itemStack) {
      return itemStack.getItemDamage() * 10 + 35;
   }

   public EnumAction getItemUseAction(ItemStack itemstack) {
      return EnumAction.drink;
   }

   public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      if(entityplayer.getMaxHealth() > entityplayer.getHealth() && !entityplayer.capabilities.isCreativeMode) {
         entityplayer.heal((float)(4 + itemstack.getItemDamage() * 2));
         entityplayer.inventory.decrStackSize(entityplayer.inventory.currentItem, 1);
      }

      return itemstack;
   }
}
