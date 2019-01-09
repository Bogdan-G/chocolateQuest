package com.chocolate.chocolateQuest.items.gun;

import com.chocolate.chocolateQuest.items.gun.ItemGun;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ItemPistol extends ItemGun {

   public ItemPistol() {
      super.canPickAmmoFromInventory = true;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:revolver");
   }

   public boolean isMeleeWeapon(EntityLivingBase shooter, ItemStack is) {
      return false;
   }

   public int getAmmoLoaderStackSize(ItemStack is) {
      return 1;
   }
}
