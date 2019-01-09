package com.chocolate.chocolateQuest.items.swords;

import com.chocolate.chocolateQuest.items.swords.ItemSwordAndShieldBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

public class ItemSwordAndShield extends ItemSwordAndShieldBase {

   final boolean isIron;


   public ItemSwordAndShield(ToolMaterial mat) {
      super(mat, "");
      if(mat == ToolMaterial.IRON) {
         this.isIron = true;
      } else {
         this.isIron = false;
      }

   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {}

   @SideOnly(Side.CLIENT)
   public void getSubItems(Item itemId, CreativeTabs table, List list) {
      for(int i = 0; i < 15; ++i) {
         ItemStack is = new ItemStack(itemId);
         is.stackTagCompound = new NBTTagCompound();
         is.stackTagCompound.setShort("Shield", (short)i);
         list.add(is);
      }

   }

   public int getShieldID(ItemStack is) {
      return is.stackTagCompound != null?is.stackTagCompound.getShort("Shield"):super.getShieldID(is);
   }

   public IIcon getIconFromDamage(int par1) {
      return this.isIron?Items.iron_sword.getIconFromDamage(par1):Items.diamond_sword.getIconFromDamage(par1);
   }
}
