package com.chocolate.chocolateQuest.block;

import com.chocolate.chocolateQuest.block.BlockDungeonChestTileEntity;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDungeonChest extends BlockContainer {

   public BlockDungeonChest() {
      super(Material.wood);
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new BlockDungeonChestTileEntity();
   }

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float posX, float posY, float posZ) {
      player.openGui("chocolateQuest", 4, world, x, y, z);
      return super.onBlockActivated(world, x, y, z, player, side, posX, posY, posZ);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
      this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 1.0F, 0.9F);
   }

   public boolean isOpaqueCube() {
      return false;
   }
}
