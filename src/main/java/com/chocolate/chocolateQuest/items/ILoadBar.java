package com.chocolate.chocolateQuest.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ILoadBar {

   int getMaxCharge();

   boolean shouldBarShine(EntityPlayer var1, ItemStack var2);
}
