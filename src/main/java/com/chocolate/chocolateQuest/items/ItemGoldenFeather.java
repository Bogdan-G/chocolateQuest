package com.chocolate.chocolateQuest.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGoldenFeather extends Item {

   public ItemGoldenFeather() {
      this.setMaxStackSize(1);
      this.setMaxDamage(385);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:goldenFeather");
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entity, int par4, boolean par5) {
      if(entity.fallDistance >= 3.0F) {
         itemstack.damageItem(1, (EntityLivingBase)entity);
         entity.fallDistance = 0.0F;

         for(int i = 0; i < 3; ++i) {
            entity.worldObj.spawnParticle("cloud", entity.posX, entity.posY - 2.0D, entity.posZ, (double)((Item.itemRand.nextFloat() - 0.5F) / 2.0F), -0.5D, (double)((Item.itemRand.nextFloat() - 0.5F) / 2.0F));
         }
      }

      super.onUpdate(itemstack, world, entity, par4, par5);
   }

   public boolean isDamageable() {
      return true;
   }

   public String getTextureFile() {
      return "/bdimg/items.png";
   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.uncommon;
   }
}
