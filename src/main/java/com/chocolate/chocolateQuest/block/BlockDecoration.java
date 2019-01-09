package com.chocolate.chocolateQuest.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockDecoration extends Block {

   public IIcon[] icon;
   public String[] names;
   public String field_149768_d;


   public BlockDecoration(Material p_i45394_1_, String textureName, String[] names) {
      super(p_i45394_1_);
      this.field_149768_d = textureName;
      this.names = names;
      this.icon = new IIcon[names.length];
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister iconRegister) {
      for(int i = 0; i < this.icon.length; ++i) {
         this.icon[i] = iconRegister.registerIcon("chocolatequest:" + this.field_149768_d + i);
      }

   }

   public IIcon getIcon(int i, int j) {
      return j < this.icon.length?this.icon[j]:this.icon[0];
   }

   public int damageDropped(int metadata) {
      return metadata;
   }

   public String getBlockName(int i) {
      return i < this.names.length?this.names[i]:this.names[0];
   }

   @SideOnly(Side.CLIENT)
   public void getSubBlocks(Item item, CreativeTabs par2CreativeTabs, List list) {
      for(int i = 0; i < this.names.length; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }
}
