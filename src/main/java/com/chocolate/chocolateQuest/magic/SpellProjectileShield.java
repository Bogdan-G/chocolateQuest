package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileMagicShield;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.magic.SpellProjectile;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SpellProjectileShield extends SpellProjectile {

   public int getCoolDown() {
      return 300;
   }

   public void onShoot(EntityLivingBase shooter, Elements element, ItemStack is, int chargeTime) {
      World world = shooter.worldObj;
      if(!world.isRemote) {
         int type = this.getType();
         EntityBaseBall ball = new EntityBaseBall(world, shooter, type, this.getExpansion(is), element);
         ball.setBallData(new ProjectileMagicShield(ball, shooter));
         ++ball.posX;
         ball.setDamageMultiplier(this.getDamage(is) * 0.5F);
         world.spawnEntityInWorld(ball);
      }

   }

   public int getType() {
      return 103;
   }

   public int getCastingTime() {
      return 10;
   }

   public float getCost(ItemStack itemstack) {
      return 15.0F;
   }
}
