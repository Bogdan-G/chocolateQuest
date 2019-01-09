package com.chocolate.chocolateQuest.items.gun;

import net.minecraft.item.ItemStack;

public interface ILoadableGun {

   int getAmmoLoaderStackSize(ItemStack var1);

   int getAmmoLoaderAmmount(ItemStack var1);

   boolean isValidAmmo(ItemStack var1);

   int getStackIcon(ItemStack var1);
}
