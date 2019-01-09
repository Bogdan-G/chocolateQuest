package com.chocolate.chocolateQuest.items.swords;

import com.chocolate.chocolateQuest.items.swords.ItemBaseBroadSword;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ItemBigSwordArea extends ItemBaseBroadSword {

   final int weaponDamage = 8;


   public ItemBigSwordArea(ToolMaterial mat, String texture, float baseDamage) {
      super(mat, texture, baseDamage);
   }

   public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5) {
      super.onUpdate(itemStack, world, entity, par4, par5);
   }

   public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase entityliving1) {
      float range = 1.5F;
      World world = entityliving.worldObj;
      double mx = entityliving.posX - (double)range;
      double my = entityliving.posY - (double)range;
      double mz = entityliving.posZ - (double)range;
      double max = entityliving.posX + (double)range;
      double may = entityliving.posY + (double)range;
      double maz = entityliving.posZ + (double)range;
      List l = world.getEntitiesWithinAABBExcludingEntity(entityliving, AxisAlignedBB.getBoundingBox(mx, my, mz, max, may, maz));
      Iterator i$ = l.iterator();

      while(i$.hasNext()) {
         Entity e = (Entity)i$.next();
         if(e instanceof EntityLivingBase && e != entityliving1) {
            e.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)entityliving1), 4.0F);
            e.worldObj.spawnParticle("largeexplode", e.posX + (double)Item.itemRand.nextFloat() - 0.5D, e.posY + (double)Item.itemRand.nextFloat(), e.posZ + (double)Item.itemRand.nextFloat() - 0.5D, (double)(Item.itemRand.nextFloat() - 0.5F), (double)(Item.itemRand.nextFloat() - 0.5F), (double)(Item.itemRand.nextFloat() - 0.5F));
         }
      }

      entityliving.worldObj.spawnParticle("largeexplode", entityliving.posX + (double)Item.itemRand.nextFloat() - 0.5D, entityliving.posY + (double)Item.itemRand.nextFloat(), entityliving.posZ + (double)Item.itemRand.nextFloat() - 0.5D, (double)(Item.itemRand.nextFloat() - 0.5F), (double)(Item.itemRand.nextFloat() - 0.5F), (double)(Item.itemRand.nextFloat() - 0.5F));
      return super.hitEntity(itemstack, entityliving, entityliving1);
   }

   public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      return super.onItemRightClick(itemStack, world, entityPlayer);
   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.epic;
   }
}
