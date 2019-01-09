package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.magic.SpellBase;
import com.chocolate.chocolateQuest.utils.HelperPlayer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class SpellStorm extends SpellBase {

   public void onShoot(EntityLivingBase shooter, Elements element, ItemStack is, int chargeTime) {
      World world = shooter.worldObj;
      if(!world.isRemote) {
         if(shooter instanceof EntityPlayer) {
            MovingObjectPosition look = HelperPlayer.getBlockMovingObjectPositionFromPlayer(shooter.worldObj, shooter, 60.0D, true);
            if(look != null) {
               this.shootBallAt(shooter, element, (double)look.blockX, (double)look.blockY, (double)look.blockZ, is);
            } else {
               Vec3 dist = shooter.getLookVec();
               double dist1 = 10.0D;
               this.shootBallAt(shooter, element, shooter.posX + dist.xCoord * dist1, shooter.posY, shooter.posZ + dist.zCoord * dist1, is);
            }
         } else {
            Vec3 look1 = shooter.getLookVec();
            double dist2 = 10.0D;
            EntityLivingBase target = ((EntityLiving)shooter).getAttackTarget();
            if(target != null) {
               dist2 = (double)shooter.getDistanceToEntity(target);
            }

            this.shootBallAt(shooter, element, shooter.posX + look1.xCoord * dist2, shooter.posY, shooter.posZ + look1.zCoord * dist2, is);
         }
      }

   }

   public void shootBallAt(EntityLivingBase shooter, Elements element, double x, double y, double z, ItemStack is) {
      World world = shooter.worldObj;
      int type = this.getType();
      EntityBaseBall ball = new EntityBaseBall(world, shooter, type, this.getExpansion(is), element);
      ball.posX = x;
      ball.posY = y;
      ball.posZ = z;
      ball.setDamageMultiplier(this.getDamage(is) * 0.66F);
      world.spawnEntityInWorld(ball);
   }

   public int getCoolDown() {
      return 1000;
   }

   public int getRange(ItemStack itemstack) {
      return 32;
   }

   public int getCastingTime() {
      return 40;
   }

   public float getCost(ItemStack itemstack) {
      return 200.0F;
   }

   public int getType() {
      return 104;
   }

   public boolean isProjectile() {
      return true;
   }
}
