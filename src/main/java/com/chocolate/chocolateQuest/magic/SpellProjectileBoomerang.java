package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileMagicAimed;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.magic.SpellProjectile;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SpellProjectileBoomerang extends SpellProjectile {

   public int getCoolDown() {
      return 100;
   }

   public void onShoot(EntityLivingBase shooter, Elements element, ItemStack is, int chargeTime) {
      World world = shooter.worldObj;
      if(!world.isRemote) {
         int type = this.getType();
         EntityBaseBall ball = new EntityBaseBall(world, shooter, type, this.getExpansion(is), element);
         ball.setBallData(new ProjectileMagicAimed(ball, shooter, 10));
         ball.setDamageMultiplier(this.getDamage(is) * 0.5F);
         world.spawnEntityInWorld(ball);
      }

   }

   public int getType() {
      return 101;
   }

   public float getCost(ItemStack itemstack) {
      return 10.0F;
   }
}
