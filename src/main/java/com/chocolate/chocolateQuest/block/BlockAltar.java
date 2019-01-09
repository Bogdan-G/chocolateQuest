package com.chocolate.chocolateQuest.block;

import com.chocolate.chocolateQuest.block.BlockAltarTileEntity;
import com.chocolate.chocolateQuest.client.ClientProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAltar extends BlockContainer {

   public BlockAltar() {
      super(Material.wood);
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister iconRegister) {}

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
      ItemStack playerItem = player.getCurrentEquippedItem();
      TileEntity te = world.getTileEntity(x, y, z);
      if(te instanceof BlockAltarTileEntity) {
         BlockAltarTileEntity stand = (BlockAltarTileEntity)te;
         if(stand.item != null) {
            if(!world.isRemote) {
               EntityItem e = new EntityItem(world, (double)x, (double)(y + 1), (double)z, stand.item);
               world.spawnEntityInWorld(e);
            }

            stand.item = null;
            return true;
         }

         if(playerItem != null) {
            stand.item = playerItem.splitStack(1);
            stand.rotation = (int)player.rotationYaw - 180;
            return true;
         }
      } else {
         world.setTileEntity(x, y, z, this.createNewTileEntity(world, world.getBlockMetadata(x, y, z)));
      }

      return super.onBlockActivated(world, x, y + 1, z, player, par6, par7, par8, par9);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
      if(world == null) {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      } else {
         float minX = 0.2F;
         byte xAxis = 0;
         byte zAxis = 0;
         if(world.getBlock(x - 1, y, z) == this) {
            minX = 0.0F;
            ++xAxis;
         }

         float maxX = 0.8F;
         if(world.getBlock(x + 1, y, z) == this) {
            maxX = 1.0F;
            ++xAxis;
         }

         float minZ = 0.2F;
         if(world.getBlock(x, y, z - 1) == this) {
            minZ = 0.0F;
            ++zAxis;
         }

         float maxZ = 0.8F;
         if(world.getBlock(x, y, z + 1) == this) {
            maxZ = 1.0F;
            ++zAxis;
         }

         float minY = 0.0F;
         if(xAxis == 2 || zAxis == 2) {
            minY = 0.9F;
         }

         this.setBlockBounds(minX, minY, minZ, maxX, 1.0F, maxZ);
      }
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
      return ClientProxy.tableRenderID;
   }

   @SideOnly(Side.CLIENT)
   public boolean renderAsNormalBlock() {
      return false;
   }

   public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
      BlockAltarTileEntity stand = (BlockAltarTileEntity)world.getTileEntity(x, y, z);
      if(stand != null && stand.item != null && !world.isRemote) {
         EntityItem e = new EntityItem(world, (double)x + 0.5D, (double)(y + 1), (double)z + 0.5D, stand.item);
         world.spawnEntityInWorld(e);
      }

      super.breakBlock(world, x, y, z, par5, par6);
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new BlockAltarTileEntity();
   }
}
