package com.chocolate.chocolateQuest.block;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.block.BlockEditorTileEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockEditor extends BlockContainer {

   public IIcon icon1;
   public IIcon icon2;
   public IIcon icon3;


   public BlockEditor() {
      super(Material.cake);
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister iconRegister) {
      super.blockIcon = iconRegister.registerIcon("chocolatequest:e0");
      this.icon1 = iconRegister.registerIcon("chocolatequest:e1");
      this.icon2 = iconRegister.registerIcon("chocolatequest:e2");
      this.icon3 = iconRegister.registerIcon("chocolatequest:e3");
   }

   public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
      BlockEditorTileEntity block = (BlockEditorTileEntity)par1World.getTileEntity(par2, par3, par4);
      par5EntityPlayer.openGui(ChocolateQuest.instance, 1, par1World, par2, par3, par4);
      return true;
   }

   public IIcon getIcon(int i, int j) {
      switch(i) {
      case 0:
         return this.icon3;
      case 1:
      case 3:
      default:
         return this.icon2;
      case 2:
         return this.icon1;
      case 4:
         return super.blockIcon;
      }
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public int getRenderType() {
      return 0;
   }

   public TileEntity createNewTileEntity(World world, int var2) {
      return new BlockEditorTileEntity();
   }
}
