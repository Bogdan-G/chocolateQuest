package com.chocolate.chocolateQuest.items.gun;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.items.gun.ItemGolemWeapon;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGolemCannon extends ItemGolemWeapon {

   public ItemGolemCannon(int cooldown, float range, float accuracy) {
      super(cooldown, range, accuracy, 1.0F, 1);
   }

   public EntityBaseBall getBall(World world, EntityLivingBase shooter, ItemStack is, double x, double y, double z) {
      float accuracy = this.getAccuracy(is);
      byte projectile = 1;
      if(shooter instanceof EntityHumanBase) {
         accuracy += ((EntityHumanBase)shooter).accuracy;
         if(shooter instanceof EntityGolemMecha) {
            projectile = 2;
         }
      }

      return new EntityBaseBall(shooter.worldObj, shooter, x, y, z, projectile, 4, accuracy);
   }

   public boolean isValidAmmo(ItemStack is) {
      return super.isValidAmmo(is) && is.getItemDamage() == 4;
   }

   protected int getExtraBulletDamage(int bullet) {
      return 6;
   }

   public int getDefaultAmmo() {
      return 4;
   }
}
