package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.entity.EntitySummonedUndead;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.magic.SpellBase;
import com.chocolate.chocolateQuest.utils.HelperPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class SpellSummonElemental extends SpellBase {

   final byte summonType;
   final int summonCost;


   public SpellSummonElemental(byte summonType, int summonCost) {
      this.summonType = summonType;
      this.summonCost = summonCost;
   }

   public void onShoot(EntityLivingBase shooter, Elements element, ItemStack is, int chargeTime) {
      World world = shooter.worldObj;
      double x;
      double y;
      double z;
      if(shooter instanceof EntityPlayer) {
         int e = 8 + 10 * this.getExpansion(is) + (int)this.getDamage(is);
         MovingObjectPosition mop = HelperPlayer.getBlockMovingObjectPositionFromPlayerWithSideOffset(shooter.worldObj, shooter, (double)e, true);
         if(mop != null) {
            x = (double)mop.blockX;
            y = (double)mop.blockY;
            z = (double)mop.blockZ;
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

      if(!world.isRemote) {
         EntitySummonedUndead e1 = new EntitySummonedUndead(world, shooter, this.getExpansion(is), this.getDamage(is) / 2.0F, element, this.summonType);
         e1.setPosition(x, y, z);
         world.spawnEntityInWorld(e1);
      }

   }

   public int getCoolDown() {
      return 600;
   }

   public float getCost(ItemStack itemstack) {
      return (float)this.summonCost;
   }

   public int getRange(ItemStack itemstack) {
      return 32;
   }

   public boolean isProjectile() {
      return true;
   }
}
