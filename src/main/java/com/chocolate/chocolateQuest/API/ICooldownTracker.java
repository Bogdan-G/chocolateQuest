package com.chocolate.chocolateQuest.API;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface ICooldownTracker {

   Object getCooldownTracker(ItemStack var1, Entity var2);

   boolean shouldStartCasting(ItemStack var1, EntityLivingBase var2, boolean var3);

   void startTick(Object var1);
}
