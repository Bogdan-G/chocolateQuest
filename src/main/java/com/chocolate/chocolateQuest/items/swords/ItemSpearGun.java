package com.chocolate.chocolateQuest.items.swords;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.API.IRangedWeapon;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemBase;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.items.ILoadBar;
import com.chocolate.chocolateQuest.items.gun.ILoadableGun;
import com.chocolate.chocolateQuest.items.gun.ItemGun;
import com.chocolate.chocolateQuest.items.swords.ItemBaseSpear;
import com.chocolate.chocolateQuest.magic.Awakements;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.world.World;

public class ItemSpearGun extends ItemBaseSpear implements IRangedWeapon, ILoadableGun, ILoadBar {

   public ItemSpearGun() {
      super(ToolMaterial.IRON, 3.0F);
      this.setMaxDamage(2048);
      super.cooldown = 50;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:spearGun");
      RenderItemBase.registerIcons(iconRegister);
   }

   public void doSpecialSkill(ItemStack itemstack, World world, EntityLivingBase entityPlayer) {
      if(!world.isRemote) {
         world.spawnEntityInWorld(new EntityBaseBall(world, entityPlayer, 1, 4));
      }

      itemstack.damageItem(1, entityPlayer);
   }

   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityPlayer) {
      if(entityPlayer.isSneaking()) {
         entityPlayer.openGui(ChocolateQuest.instance, 3, entityPlayer.worldObj, 0, 0, 0);
         return itemstack;
      } else {
         return super.onItemRightClick(itemstack, world, entityPlayer);
      }
   }

   public void stopUsingItem(ItemStack itemstack, World world, EntityPlayer entityPlayer, Entity target) {
      if(!world.isRemote && target == null) {
         ((ItemGun)ChocolateQuest.revolver).shoot(itemstack, world, entityPlayer);
         itemstack.damageItem(1, entityPlayer);
      }

   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.epic;
   }

   public int getEntityLifespan(ItemStack itemStack, World world) {
      return 24000;
   }

   public void shootFromEntity(EntityLivingBase shooter, ItemStack is, int angle, Entity target) {
      ((ItemGun)ChocolateQuest.revolver).shootFromEntity(shooter, is, angle, target);
   }

   public float getRange(EntityLivingBase shooter, ItemStack is) {
      return 100.0F;
   }

   public int getCooldown(EntityLivingBase shooter, ItemStack is) {
      return 10;
   }

   public boolean canBeUsedByEntity(Entity entity) {
      return true;
   }

   public boolean isMeleeWeapon(EntityLivingBase shooter, ItemStack is) {
      return true;
   }

   public boolean shouldUpdate(EntityLivingBase shooter) {
      return false;
   }

   public int getAmmoLoaderStackSize(ItemStack is) {
      return 12;
   }

   public int getAmmoLoaderAmmount(ItemStack is) {
      int loaders = Awakements.getEnchantLevel(is, Awakements.ammoCapacity);
      return 1 + loaders;
   }

   public boolean isValidAmmo(ItemStack is) {
      return is == null?false:is.getItem() == ChocolateQuest.bullet;
   }

   public int getStackIcon(ItemStack is) {
      return 85;
   }

   public int startAiming(ItemStack is, EntityLivingBase shooter, Entity target) {
      return 30;
   }

   public int getMaxCharge() {
      return super.cooldown;
   }

   public boolean shouldBarShine(EntityPlayer player, ItemStack is) {
      return player.getItemInUseDuration() > super.cooldown;
   }
}
