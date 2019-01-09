package com.chocolate.chocolateQuest.items.swords;

import com.chocolate.chocolateQuest.items.swords.ItemCQBlade;
import com.chocolate.chocolateQuest.magic.Awakements;
import com.chocolate.chocolateQuest.utils.HelperPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.world.World;

public class ItemBaseSpear extends ItemCQBlade {

   public int cooldown;
   public String texture;
   float cachedDamage;


   public ItemBaseSpear(ToolMaterial mat, float baseDamage) {
      super(mat, baseDamage);
      this.cooldown = 50;
      this.cachedDamage = 0.0F;
      super.elementModifier = 0.7F;
   }

   public ItemBaseSpear(ToolMaterial mat) {
      this(mat, 2.0F);
   }

   public ItemBaseSpear(ToolMaterial mat, String texture) {
      this(mat);
      this.texture = texture;
   }

   public ItemBaseSpear(ToolMaterial mat, String texture, int baseDamage, float elementModifier) {
      this(mat, (float)baseDamage);
      this.texture = texture;
      super.elementModifier = elementModifier;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:" + this.texture);
   }

   public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase target, EntityLivingBase entity) {
      boolean flag = super.hitEntity(par1ItemStack, target, entity);
      if(flag && Item.itemRand.nextInt(5) == 0 && target.ridingEntity != null) {
         target.mountEntity((Entity)null);
      }

      return flag;
   }

   public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5) {
      if(entity instanceof EntityPlayer) {
         EntityPlayer ep = (EntityPlayer)entity;
         if(ep.isSwingInProgress && ep.getCurrentEquippedItem() == itemStack && ep.swingProgressInt == 0) {
            int range = Awakements.getEnchantLevel(itemStack, Awakements.range);
            Entity target = HelperPlayer.getTarget(ep, world, 3.5D + 0.2D * (double)range);
            if(target != null) {
               ep.attackTargetEntityWithCurrentItem(target);
            }
         }
      }

      super.onUpdate(itemStack, world, entity, par4, par5);
   }

   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityPlayer) {
      entityPlayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
      return super.onItemRightClick(itemstack, world, entityPlayer);
   }

   public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityPlayer, int useTime) {
      entityPlayer.swingItem();
      Entity target = HelperPlayer.getTarget(entityPlayer, world, 5.0D);
      int j = this.getMaxItemUseDuration(itemstack) - useTime;
      if(j > this.cooldown) {
         this.doSpecialSkill(itemstack, world, entityPlayer);
      } else {
         this.stopUsingItem(itemstack, world, entityPlayer, target);
      }

      if(target != null) {
         useTime = this.getMaxItemUseDuration(itemstack) - useTime;
         useTime = Math.min(useTime + 1, 90);
         this.cachedDamage = this.getWeaponDamage() * (float)useTime / 30.0F + 1.0F;
         this.attackEntityWithItem(entityPlayer, target);
      }

   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 72000;
   }

   public EnumAction getItemUseAction(ItemStack par1ItemStack) {
      return EnumAction.bow;
   }

   public void doSpecialSkill(ItemStack itemstack, World world, EntityLivingBase entityPlayer) {}

   public void stopUsingItem(ItemStack itemstack, World world, EntityPlayer entityPlayer, Entity target) {}
}
