package com.chocolate.chocolateQuest.items.gun;

import com.chocolate.chocolateQuest.items.gun.ItemGolemWeapon;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ItemShotGun extends ItemGolemWeapon {

   public ItemShotGun(int cooldown, float range, float accuracy, float damage, int capacity, int rounds) {
      super(cooldown, range * range, accuracy, damage, capacity, rounds);
   }

   public boolean isMeleeWeapon(EntityLivingBase shooter, ItemStack is) {
      return true;
   }

   public int getAmmoLoaderStackSize(ItemStack is) {
      return 1;
   }
}
