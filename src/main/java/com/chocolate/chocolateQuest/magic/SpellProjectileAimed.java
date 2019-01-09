package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileMagicAimed;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.magic.SpellProjectile;
import com.chocolate.chocolateQuest.utils.HelperPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class SpellProjectileAimed extends SpellProjectile {

   public int getRange(ItemStack itemstack) {
      return 32;
   }

   public int getCoolDown() {
      return 35;
   }

   public void onShoot(EntityLivingBase shooter, Elements element, ItemStack is, int chargeTime) {
      World world = shooter.worldObj;
      Object target = null;
      if(shooter instanceof EntityPlayer) {
         MovingObjectPosition type = HelperPlayer.getMovingObjectPositionFromPlayer(shooter, world, 60.0D, 2.0D);
         if(type != null) {
            target = type.entityHit;
         }
      } else {
         target = ((EntityLiving)shooter).getAttackTarget();
      }

      if(!world.isRemote && target != null) {
         int type1 = this.getType();
         EntityBaseBall ball = new EntityBaseBall(world, shooter, type1, this.getExpansion(is), element);
         ball.setBallData(new ProjectileMagicAimed(ball, (Entity)target));
         ball.setDamageMultiplier(this.getDamage(is) * 0.5F);
         world.spawnEntityInWorld(ball);
      }

   }

   public int getType() {
      return 101;
   }

   public float getCost(ItemStack itemstack) {
      return 12.0F;
   }
}
