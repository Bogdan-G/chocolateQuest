package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.entity.projectile.EntityHookShoot;
import com.chocolate.chocolateQuest.items.IHookLauncher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemHookShoot extends Item implements IHookLauncher {

   public static IIcon hook;
   int lvl;
   String iconTexture;


   public ItemHookShoot(int lvl, String iconTexture) {
      this.lvl = lvl;
      this.iconTexture = iconTexture;
      this.setMaxStackSize(1);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      hook = iconRegister.registerIcon("chocolatequest:hook");
      super.itemIcon = iconRegister.registerIcon(this.iconTexture);
   }

   public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
      return par2 == 0 && par1 == 0?hook:super.itemIcon;
   }

   @SideOnly(Side.CLIENT)
   public boolean requiresMultipleRenderPasses() {
      return true;
   }

   public int getHookType() {
      return this.lvl;
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entity, int par4, boolean flag) {
      if(itemstack.getItemDamage() != 0) {
         if(world.getEntityByID(itemstack.getItemDamage()) != null) {
            if(world.getEntityByID(itemstack.getItemDamage()).isDead) {
               itemstack.setItemDamage(0);
            }
         } else {
            itemstack.setItemDamage(0);
         }
      }

      super.onUpdate(itemstack, world, entity, par4, flag);
   }

   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityPlayer) {
      if(itemstack.getItemDamage() == 0) {
         if(!world.isRemote) {
            EntityHookShoot e = new EntityHookShoot(world, entityPlayer, this.lvl, itemstack);
            world.spawnEntityInWorld(e);
            itemstack.setItemDamage(e.getEntityId());
         }
      } else {
         Entity e1 = world.getEntityByID(itemstack.getItemDamage());
         if(e1 instanceof EntityHookShoot) {
            e1.setDead();
         }

         itemstack.setItemDamage(0);
      }

      return super.onItemRightClick(itemstack, world, entityPlayer);
   }

   public boolean shouldRotateAroundWhenRendering() {
      return true;
   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.rare;
   }

   public int getHookID(ItemStack is) {
      return is.getItemDamage();
   }

   public void setHookID(ItemStack is, int id) {
      is.setItemDamage(id);
   }
}
