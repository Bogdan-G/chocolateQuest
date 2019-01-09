package com.chocolate.chocolateQuest.items.gun;

import com.chocolate.chocolateQuest.entity.projectile.EntityProjectileBeam;
import com.chocolate.chocolateQuest.items.gun.ItemGolemWeapon;
import com.chocolate.chocolateQuest.magic.Elements;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWaterPump extends ItemGolemWeapon {

   public ItemWaterPump(int cooldown, float range, float accuracy) {
      super(cooldown, range, accuracy);
      super.usesStats = false;
   }

   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityPlayer) {
      this.shootBeam(entityPlayer, itemstack, 40);
      if(entityPlayer.dimension == -1) {
         return itemstack;
      } else {
         entityPlayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
         return itemstack;
      }
   }

   public void shootBeam(EntityLivingBase shooter, ItemStack is, int angle) {
      if(shooter.dimension != -1) {
         if(!shooter.worldObj.isRemote) {
            float rot = 0.5F;
            float height = -0.4F;
            if(!(shooter instanceof EntityPlayer)) {
               height = shooter.height - 0.5F;
            }

            EntityProjectileBeam x = new EntityProjectileBeam(shooter.worldObj, shooter, (float)angle, rot, height, Elements.water);
            x.setDamage(6.0F);
            x.setMaxRange(16);
            shooter.worldObj.spawnEntityInWorld(x);
         }
      } else {
         double var14 = Math.toRadians((double)shooter.rotationYawHead);
         double var15 = shooter.posX - Math.sin(var14);
         double y = shooter.posY;
         double z = shooter.posZ + Math.cos(var14);
         float f = 0.3F;

         for(int i = 0; i < 3; ++i) {
            shooter.worldObj.spawnParticle("smoke", var15 + (double)((Item.itemRand.nextFloat() - 0.5F) * f), y + (double)((Item.itemRand.nextFloat() - 0.5F) * f), z + (double)((Item.itemRand.nextFloat() - 0.5F) * f), 0.0D, 0.0D, 0.0D);
         }
      }

   }

   public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityPlayer, int useTime) {}

   public int startAiming(ItemStack is, EntityLivingBase shooter, Entity target) {
      byte angle = 40;
      if(shooter.getEquipmentInSlot(0) != is) {
         int var10000 = -angle;
      }

      this.shootBeam(shooter, is, 40);
      return 40;
   }

   public float getRange(EntityLivingBase shooter, ItemStack is) {
      return 36.0F;
   }

   public int getCooldown(EntityLivingBase shooter, ItemStack is) {
      return 60;
   }

   public void shootFromEntity(EntityLivingBase shooter, ItemStack is, int angle, Entity target) {}
}
