package com.chocolate.chocolateQuest.items.gun;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.items.gun.ItemGolemWeapon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGolemMachineGun extends ItemGolemWeapon {

   public ItemGolemMachineGun() {
      super(0, 20.0F, 200.0F, 0.6F, 1);
      super.canPickAmmoFromLoader = true;
   }

   public void shootFromEntity(EntityLivingBase shooter, ItemStack is, int angle, Entity target) {}

   public boolean shouldUpdate(EntityLivingBase shooter) {
      return true;
   }

   public void onUpdate(ItemStack is, World world, Entity entity, int par4, boolean par5) {
      boolean shoot = false;
      if(entity instanceof EntityHumanBase && world.getWorldTime() % 3L == 0L) {
         super.shootFromEntity((EntityLivingBase)entity, is, par4, (Entity)null);
      }

      if(entity instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entity;
         if(world.getWorldTime() % 2L == 0L && player.isUsingItem() && player.getCurrentEquippedItem() == is) {
            this.shoot(is, world, (EntityPlayer)entity);
         }
      }

   }

   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityPlayer) {
      entityPlayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
      return itemstack;
   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 72000;
   }

   public EnumAction getItemUseAction(ItemStack par1ItemStack) {
      return EnumAction.bow;
   }

   public EntityBaseBall getBall(World world, EntityLivingBase shooter, ItemStack is, double x, double y, double z) {
      EntityBaseBall ball = super.getBall(world, shooter, is, x, y, z);
      ball.setDamageMultiplier(0.7F);
      return ball;
   }
}
