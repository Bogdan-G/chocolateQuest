package com.chocolate.chocolateQuest.block;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.block.BlockBannerStandTileEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBannerStand extends BlockContainer {

   public BlockBannerStand() {
      super(Material.wood);
   }

   public void registerBlockIcons(IIconRegister iconRegister) {}

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
      ItemStack playerItem = player.getCurrentEquippedItem();
      TileEntity te = world.getTileEntity(x, y, z);
      if(te instanceof BlockBannerStandTileEntity) {
         BlockBannerStandTileEntity stand = (BlockBannerStandTileEntity)te;
         if(stand.item != null) {
            if(!world.isRemote) {
               EntityItem e = new EntityItem(world, (double)x, (double)(y + 1), (double)z, stand.item);
               world.spawnEntityInWorld(e);
            }

            stand.hasFlag = false;
            stand.item = null;
            return true;
         }

         if(playerItem != null && playerItem.getItem() == ChocolateQuest.banner) {
            stand.item = playerItem.splitStack(1);
            stand.rotation = (int)player.rotationYaw - 180;
            stand.hasFlag = true;
            return true;
         }
      } else {
         world.setTileEntity(x, y, z, this.createNewTileEntity(world, world.getBlockMetadata(x, y, z)));
      }

      return super.onBlockActivated(world, x, y + 1, z, player, par6, par7, par8, par9);
   }

   public void onEntityWalking(World world, int par2, int par3, int par4, Entity par5Entity) {
      super.onEntityWalking(world, par2, par3, par4, par5Entity);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
      this.setBlockBounds(0.4F, 0.0F, 0.4F, 0.6F, 0.2F, 0.6F);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public int getRenderBlockPass() {
      return 0;
   }

   @SideOnly(Side.CLIENT)
   public int getRenderType() {
      return 0;
   }

   @SideOnly(Side.CLIENT)
   public boolean renderAsNormalBlock() {
      return false;
   }

   public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
      TileEntity te = world.getTileEntity(x, y, z);
      if(te instanceof BlockBannerStandTileEntity) {
         BlockBannerStandTileEntity stand = (BlockBannerStandTileEntity)te;
         if(stand != null && stand.hasFlag && !world.isRemote && stand.item != null) {
            EntityItem e = new EntityItem(world, (double)x, (double)y, (double)z, stand.item);
            world.spawnEntityInWorld(e);
         }
      }

      super.breakBlock(world, x, y, z, par5, par6);
   }

   public IIcon getIcon(int side, int metadata) {
      return metadata == 1?Blocks.enchanting_table.getIcon(side, metadata):Blocks.planks.getIcon(side, metadata);
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new BlockBannerStandTileEntity();
   }
}
