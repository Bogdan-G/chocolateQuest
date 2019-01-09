package com.chocolate.chocolateQuest.items.gun;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.items.gun.ItemGun;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAmmoLoader extends ItemGun {

   public int getAmmoLoaderStackSize(ItemStack is) {
      return 64;
   }

   public int getAmmoLoaderAmmount(ItemStack is) {
      return 8;
   }

   public boolean canBeUsedByEntity(Entity entity) {
      return false;
   }

   public void shootFromEntity(EntityLivingBase shooter, ItemStack is, int angle, Entity target) {}

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:ammoLoader");
   }

   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityPlayer) {
      entityPlayer.openGui(ChocolateQuest.instance, 3, entityPlayer.worldObj, 0, 0, 0);
      return itemstack;
   }
}
