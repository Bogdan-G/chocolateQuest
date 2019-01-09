package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.items.ItemMulti;
import com.chocolate.chocolateQuest.magic.Elements;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

public class ItemMonterMaterial extends ItemMulti {

   public static final String TAG_MAX_LEVEL = "maxLevel";


   public ItemMonterMaterial() {
      super(new String[]{"elementStone", "elementStone", "elementStone", "elementStone", "elementStone"}, "stone");
   }

   public String getItemStackDisplayName(ItemStack itemstack) {
      String elementName = "";
      Elements element = this.getElement(itemstack);
      if(element != null) {
         elementName = ": " + element.getTranslatedName();
      }

      return super.getItemStackDisplayName(itemstack) + elementName;
   }

   @SideOnly(Side.CLIENT)
   public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List par3List) {
      for(int i = 0; i < super.names.length; ++i) {
         ItemStack is = new ItemStack(this, 1, i);
         is.stackTagCompound = new NBTTagCompound();
         is.stackTagCompound.setInteger("maxLevel", 5);
         par3List.add(is);
      }

   }

   public WeightedRandomChestContent getChestGenBase(ChestGenHooks chest, Random rnd, WeightedRandomChestContent original) {
      ItemStack is = original.theItemId;
      is.stackTagCompound = new NBTTagCompound();
      if(rnd.nextBoolean()) {
         if(rnd.nextBoolean()) {
            if(rnd.nextInt(3) == 0) {
               if(rnd.nextInt(3) == 0) {
                  is.stackTagCompound.setInteger("maxLevel", 5);
               } else {
                  is.stackTagCompound.setInteger("maxLevel", 4);
               }
            } else {
               is.stackTagCompound.setInteger("maxLevel", 3);
            }
         } else {
            is.stackTagCompound.setInteger("maxLevel", 2);
         }
      } else {
         is.stackTagCompound.setInteger("maxLevel", 1);
      }

      return original;
   }

   public void addInformation(ItemStack is, EntityPlayer player, List list, boolean b) {
      list.add("Max Level: " + this.getMaxLevel(is));
      if(player.capabilities.isCreativeMode) {
         list.add("Tags: maxLevel(Integer)");
      }

   }

   public Elements getElement(ItemStack is) {
      return is.getItemDamage() < 5?Elements.values()[is.getItemDamage()]:Elements.physic;
   }

   public int getMaxLevel(ItemStack is) {
      return is.stackTagCompound != null?is.stackTagCompound.getInteger("maxLevel"):1;
   }
}
