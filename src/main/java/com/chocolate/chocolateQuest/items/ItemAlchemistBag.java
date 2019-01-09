package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.gui.InventoryBag;
import com.chocolate.chocolateQuest.items.gun.ILoadableGun;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

public class ItemAlchemistBag extends Item implements ILoadableGun {

   public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer player) {
      ItemStack[] cargo = InventoryBag.getCargo(itemstack);
      int itemPos = 0;

      for(int potionStack = 0; potionStack < cargo.length; ++potionStack) {
         if(cargo[potionStack] != null) {
            itemPos = potionStack;
            break;
         }
      }

      ItemStack var8 = cargo[itemPos];
      if(var8 != null) {
         ItemPotion potion = (ItemPotion)var8.getItem();
         if(!ItemPotion.isSplash(var8.getItemDamage())) {
            potion.onEaten(var8, world, player);
            if(!player.capabilities.isCreativeMode) {
               --var8.stackSize;
               if(var8.stackSize <= 0) {
                  cargo[itemPos] = null;
               }

               InventoryBag.saveCargo(itemstack, cargo);
            }
         }
      }

      return itemstack;
   }

   public void addInformation(ItemStack is, EntityPlayer player, List list, boolean par4) {
      super.addInformation(is, player, list, par4);
      list.add(StatCollector.translateToLocal("strings.slots").trim() + ": " + this.getAmmoLoaderAmmount(is));
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:bag");
   }

   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityPlayer) {
      if(entityPlayer.isSneaking()) {
         entityPlayer.openGui(ChocolateQuest.instance, 3, entityPlayer.worldObj, 0, 0, 0);
         return itemstack;
      } else {
         ItemStack[] cargo = InventoryBag.getCargo(itemstack);
         int itemPos = 0;

         for(int potionStack = 0; potionStack < cargo.length; ++potionStack) {
            if(cargo[potionStack] != null) {
               itemPos = potionStack;
               break;
            }
         }

         ItemStack var9 = cargo[itemPos];
         if(var9 != null) {
            ItemPotion potion = (ItemPotion)var9.getItem();
            if(ItemPotion.isSplash(var9.getItemDamage())) {
               if(!world.isRemote) {
                  EntityPotion e = new EntityPotion(world, entityPlayer, var9);
                  world.spawnEntityInWorld(e);
                  if(!entityPlayer.capabilities.isCreativeMode) {
                     --var9.stackSize;
                     if(var9.stackSize <= 0) {
                        cargo[itemPos] = null;
                     }

                     InventoryBag.saveCargo(itemstack, cargo);
                  }
               }
            } else {
               entityPlayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
            }
         }

         return itemstack;
      }
   }

   public int getMaxItemUseDuration(ItemStack itemStack) {
      return 30;
   }

   public EnumAction getItemUseAction(ItemStack itemstack) {
      return EnumAction.drink;
   }

   public boolean isValidAmmo(ItemStack is) {
      return is.getItem() instanceof ItemPotion;
   }

   public int getAmmoLoaderStackSize(ItemStack is) {
      return 64;
   }

   public int getAmmoLoaderAmmount(ItemStack is) {
      return 3 + is.getItemDamage();
   }

   public int getStackIcon(ItemStack is) {
      return 84;
   }

   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
      if(entity instanceof EntityLiving && !entity.worldObj.isRemote && player.capabilities.isCreativeMode) {
         ItemStack[] cargo = InventoryBag.getCargo(stack);
         ItemStack potion = cargo[0];
         if(potion != null && potion.getItem() == Items.potionitem) {
            List list = PotionHelper.getPotionEffects(potion.getItemDamage(), true);
            if(list != null) {
               Iterator i$ = list.iterator();

               while(i$.hasNext()) {
                  PotionEffect effect = (PotionEffect)i$.next();
                  ((EntityLiving)entity).addPotionEffect(new PotionEffect(effect.getPotionID(), Integer.MAX_VALUE, effect.getAmplifier()));
               }
            }

            return true;
         }
      }

      return false;
   }

   public WeightedRandomChestContent getChestGenBase(ChestGenHooks chest, Random rnd, WeightedRandomChestContent original) {
      ItemStack is = original.theItemId;
      is.stackTagCompound = new NBTTagCompound();
      if(rnd.nextBoolean()) {
         if(rnd.nextBoolean()) {
            if(rnd.nextInt(3) == 0) {
               if(rnd.nextInt(3) == 0) {
                  is.setItemDamage(5);
               } else {
                  is.setItemDamage(4);
               }
            } else {
               is.setItemDamage(3);
            }
         } else {
            is.setItemDamage(2);
         }
      } else {
         is.setItemDamage(1);
      }

      return original;
   }
}
