package com.chocolate.chocolateQuest.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class ItemMulti extends Item {

   public String[] names = new String[]{"???"};
   public IIcon[] icon;
   String textureName;


   public ItemMulti(String[] names, String texture) {
      this.names = names;
      this.textureName = texture;
   }

   public boolean getHasSubtypes() {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      this.icon = new IIcon[this.names.length];

      for(int i = 0; i < this.icon.length; ++i) {
         this.icon[i] = iconRegister.registerIcon("chocolatequest:" + this.textureName + i);
      }

   }

   public String getItemStackDisplayName(ItemStack itemstack) {
      int i = itemstack.getItemDamage();
      return i < this.names.length?("" + StatCollector.translateToLocal("item." + this.names[i] + ".name")).trim():"????";
   }

   public IIcon getIconFromDamage(int par1) {
      return par1 < this.icon.length?this.icon[par1]:this.icon[0];
   }

   @SideOnly(Side.CLIENT)
   public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List par3List) {
      for(int i = 0; i < this.names.length; ++i) {
         par3List.add(new ItemStack(this, 1, i));
      }

   }
}
