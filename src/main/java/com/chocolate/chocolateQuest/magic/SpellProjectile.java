package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.magic.SpellBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SpellProjectile extends SpellBase {

   public void onShoot(EntityLivingBase shooter, Elements element, ItemStack is, int chargeTime) {
      World world = shooter.worldObj;
      if(!world.isRemote) {
         int type = this.getType();
         EntityBaseBall ball = new EntityBaseBall(world, shooter, type, this.getExpansion(is), element);
         ball.setDamageMultiplier(this.getDamage(is) * this.getDamageMultiplier());
         world.spawnEntityInWorld(ball);
      }

   }

   public int getType() {
      return 100;
   }

   public float getDamageMultiplier() {
      return 0.5F;
   }

   public boolean isProjectile() {
      return true;
   }
}
