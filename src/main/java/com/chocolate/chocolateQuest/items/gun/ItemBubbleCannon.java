package com.chocolate.chocolateQuest.items.gun;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.items.gun.ItemGolemWeapon;
import com.chocolate.chocolateQuest.magic.Awakements;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBubbleCannon extends ItemGolemWeapon {

   public ItemBubbleCannon(int cooldown, float range, float accuracy) {
      super(cooldown, range, accuracy);
      super.usesStats = false;
   }

   public EntityBaseBall getBall(World world, EntityLivingBase shooter, ItemStack is, double x, double y, double z) {
      return new EntityBaseBall(shooter.worldObj, shooter, x, y, z, 7, 0, this.getAccuracy(is));
   }

   public boolean freeAmmo() {
      return true;
   }

   public boolean shoot(ItemStack itemstack, World world, EntityPlayer entityPlayer) {
      if(!world.isRemote) {
         EntityBaseBall ball = new EntityBaseBall(world, entityPlayer, 7, 0);
         float accuracy = this.getAccuracy(itemstack) * 0.01F;
         ball.motionX += Item.itemRand.nextGaussian() * (double)accuracy;
         ball.motionY += Item.itemRand.nextGaussian() * (double)accuracy;
         ball.motionZ += Item.itemRand.nextGaussian() * (double)accuracy;
         int power = Awakements.getEnchantLevel(itemstack, Awakements.power);
         ball.setDamageMultiplier(1.0F + (float)power / 10.0F);
         world.spawnEntityInWorld(ball);
      }

      return true;
   }
}
