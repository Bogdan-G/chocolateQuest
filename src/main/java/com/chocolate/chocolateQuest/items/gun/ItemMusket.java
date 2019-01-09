package com.chocolate.chocolateQuest.items.gun;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.items.ItemStaffHeal;
import com.chocolate.chocolateQuest.items.gun.ItemGun;
import com.chocolate.chocolateQuest.utils.BDHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

public class ItemMusket extends ItemGun {

   public static final String bayonet = "bayonet";


   public ItemMusket() {
      super(60, 400.0F, 10.0F);
      super.canPickAmmoFromInventory = true;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:musket");
   }

   public boolean isMeleeWeapon(EntityLivingBase shooter, ItemStack is) {
      return false;
   }

   public int getAmmoLoaderStackSize(ItemStack is) {
      return 1;
   }

   public int getBayonet(ItemStack is) {
      return is.stackTagCompound == null?0:is.stackTagCompound.getInteger("bayonet");
   }

   public void addInformation(ItemStack is, EntityPlayer player, List list, boolean par4) {
      ItemStaffHeal.addPotionEffectInformation(is, list, true);
      super.addInformation(is, player, list, par4);
   }

   @SideOnly(Side.CLIENT)
   public void getSubItems(Item itemId, CreativeTabs table, List list) {
      list.add(new ItemStack(ChocolateQuest.musket));

      for(int i = 0; i < 4; ++i) {
         ItemStack is = new ItemStack(ChocolateQuest.musket);
         int damage = 4 + i;
         BDHelper.addAttribute(is, SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(Item.field_111210_e, "Damage", (double)damage, 0));
         is.stackTagCompound.setInteger("bayonet", 1 + i);
         list.add(is);
      }

   }

   public WeightedRandomChestContent getChestGenBase(ChestGenHooks chest, Random rnd, WeightedRandomChestContent original) {
      original = super.getChestGenBase(chest, rnd, original);
      if(rnd.nextBoolean()) {
         ItemStack is = original.theItemId;
         if(is.stackTagCompound == null) {
            is.stackTagCompound = new NBTTagCompound();
         }

         int damage = 4;
         int bayonet = 1;
         if(rnd.nextBoolean()) {
            ++damage;
            ++bayonet;
            if(rnd.nextBoolean()) {
               ++damage;
               ++bayonet;
               if(rnd.nextBoolean()) {
                  ++damage;
               }

               if(rnd.nextBoolean()) {
                  ++damage;
                  ++bayonet;
                  if(rnd.nextBoolean()) {
                     ++damage;
                  }
               }
            }
         }

         is.stackTagCompound.setInteger("bayonet", bayonet);
         BDHelper.addAttribute(is, SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(Item.field_111210_e, "Damage", (double)damage, 0));
      }

      return original;
   }
}
