package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.magic.SpellBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

public class SpellLeap extends SpellBase {

   public void onShoot(EntityLivingBase shooter, Elements element, ItemStack is, int chargeTime) {
      Vec3 vec = shooter.getLookVec();
      float dist = ((float)(10 + 6 * this.getExpansion(is)) + this.getDamage(is)) * 0.1F;
      if(shooter instanceof EntityPlayer) {
         shooter.motionX = vec.xCoord * (double)dist;
         shooter.motionY = vec.yCoord * (double)dist * 0.6D;
         shooter.motionZ = vec.zCoord * (double)dist;
      } else {
         byte dir = -1;
         shooter.motionY += 0.2D;
         if(shooter.isCollidedHorizontally) {
            dir = 1;
         }

         shooter.motionX = (double)dir * vec.xCoord * (double)dist;
         shooter.motionY = (double)dir * vec.yCoord * (double)dist * 0.6D;
         shooter.motionZ = (double)dir * vec.zCoord * (double)dist;
      }

      shooter.worldObj.playSoundEffect((double)((int)shooter.posX), (double)((int)shooter.posY), (double)((int)shooter.posZ), element.sound, 4.0F, (1.0F + (shooter.worldObj.rand.nextFloat() - shooter.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
      shooter.fallDistance = 0.0F;
   }

   public int getRange(ItemStack itemstack) {
      return 5;
   }

   public boolean isProjectile() {
      return true;
   }

   public int getCastingTime() {
      return 2;
   }

   public int getCoolDown() {
      return 40;
   }

   public float getCost(ItemStack itemstack) {
      return 40.0F;
   }
}
