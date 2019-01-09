package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.magic.SpellProjectile;
import com.chocolate.chocolateQuest.utils.HelperPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class SpellMeteor extends SpellProjectile {

   public int getRange(ItemStack itemstack) {
      return 32;
   }

   public int getCoolDown() {
      return 160;
   }

   public float getCost(ItemStack itemstack) {
      return 100.0F;
   }

   public void onShoot(EntityLivingBase shooter, Elements element, ItemStack is, int chargeTime) {
      World world = shooter.worldObj;
      if(!world.isRemote) {
         byte type = 107;
         int dist = 40 + 20 * this.getExpansion(is) + (int)this.getDamage(is);
         double x = 0.0D;
         double y = 0.0D;
         double z = 0.0D;
         if(shooter instanceof EntityPlayer) {
            MovingObjectPosition hx = HelperPlayer.getBlockMovingObjectPositionFromPlayerWithSideOffset(shooter.worldObj, shooter, (double)dist, true);
            if(hx != null) {
               x = (double)hx.blockX;
               y = (double)hx.blockY;
               z = (double)hx.blockZ;
            } else {
               x = shooter.posX;
               y = shooter.posY;
               z = shooter.posZ;
            }
         } else {
            x = shooter.posX;
            y = shooter.posY;
            z = shooter.posZ;
         }

         double hx1 = x;
         double hy = y;
         double hz = z;
         x += (double)(shooter.getRNG().nextInt(200) - 100);
         y += 100.0D;
         z += (double)(shooter.getRNG().nextInt(200) - 100);
         hx1 -= x;
         hy -= y;
         hz -= z;
         EntityBaseBall ball = new EntityBaseBall(world, shooter, type, this.getExpansion(is), element);
         ball.setPosition(x, y, z);
         ball.setThrowableHeading(hx1, hy, hz, 1.0F, 0.0F);
         ball.setElement(element);
         ball.setDamageMultiplier(this.getDamage(is) * 3.0F);
         world.spawnEntityInWorld(ball);
      }

   }

   public int getType() {
      return 101;
   }
}
