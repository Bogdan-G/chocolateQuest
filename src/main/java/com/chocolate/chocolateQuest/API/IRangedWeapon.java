package com.chocolate.chocolateQuest.API;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IRangedWeapon {

   float getRange(EntityLivingBase var1, ItemStack var2);

   int getCooldown(EntityLivingBase var1, ItemStack var2);

   void shootFromEntity(EntityLivingBase var1, ItemStack var2, int var3, Entity var4);

   boolean canBeUsedByEntity(Entity var1);

   boolean isMeleeWeapon(EntityLivingBase var1, ItemStack var2);

   boolean shouldUpdate(EntityLivingBase var1);

   int startAiming(ItemStack var1, EntityLivingBase var2, Entity var3);
}
