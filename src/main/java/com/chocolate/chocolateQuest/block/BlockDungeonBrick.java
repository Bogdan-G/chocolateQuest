package com.chocolate.chocolateQuest.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockDungeonBrick extends Block {

   public IIcon[] icon;


   public BlockDungeonBrick() {
      super(Material.rock);
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister iconRegister) {
      this.icon = new IIcon[16];

      for(int i = 0; i < 16; ++i) {
         this.icon[i] = iconRegister.registerIcon("chocolatequest:w" + i);
      }

   }

   public IIcon getIcon(int i, int j) {
      return this.icon[j];
   }

   public int damageDropped(int metadata) {
      return metadata;
   }

   @SideOnly(Side.CLIENT)
   public void getSubBlocks(Item item, CreativeTabs par2CreativeTabs, List list) {
      for(int i = 0; i < 16; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }

   public void onBlockHarvested(World par1World, int x, int y, int z, int par5, EntityPlayer par6EntityPlayer) {
      super.onBlockHarvested(par1World, x, y, z, par5, par6EntityPlayer);
   }

   public MapColor getMapColor(int data) {
      switch(data) {
      case 3:
         return MapColor.lightBlueColor;
      case 4:
         return MapColor.limeColor;
      case 5:
         return MapColor.greenColor;
      case 6:
         return MapColor.pinkColor;
      case 7:
         return MapColor.brownColor;
      case 8:
         return MapColor.dirtColor;
      case 9:
         return MapColor.cyanColor;
      case 10:
         return MapColor.purpleColor;
      case 11:
         return MapColor.blueColor;
      case 12:
         return MapColor.adobeColor;
      case 13:
         return MapColor.foliageColor;
      case 14:
         return MapColor.redColor;
      case 15:
         return MapColor.blackColor;
      default:
         return super.getMapColor(data);
      }
   }
}
