package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.entity.projectile.EntityProjectileBeam;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.magic.SpellBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class SpellBeam extends SpellBase {

   public void onUpdate(EntityLivingBase shooter, Elements element, ItemStack is, int angle) {}

   public void onCastStart(EntityLivingBase shooter, Elements element, ItemStack is) {
      if(!shooter.worldObj.isRemote) {
         float dist = 0.5F;
         float height = -0.1F;
         byte angle = 40;
         int angle1 = shooter.getEquipmentInSlot(0) == is?angle:-angle;
         if(!(shooter instanceof EntityPlayer)) {
            height = shooter.height - 0.5F;
         }

         EntityProjectileBeam e = new EntityProjectileBeam(shooter.worldObj, shooter, (float)angle1, dist, height, element);
         e.setDamage(this.getDamage(is));
         e.setMaxRange(6 + this.getExpansion(is) * 2);
         shooter.worldObj.spawnEntityInWorld(e);
      }

   }

   public void onShoot(EntityLivingBase shooter, Elements element, ItemStack is, int chargeTime) {}

   public boolean isProjectile() {
      return true;
   }

   public int getRange(ItemStack itemstack) {
      return 8 + this.getExpansion(itemstack);
   }

   public boolean shouldUpdate() {
      return true;
   }

   public int getCastingTime() {
      return 40;
   }

   public int getCoolDown() {
      return 80;
   }

   public float getCost(ItemStack itemstack) {
      return 1.0F;
   }
}
