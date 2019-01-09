package com.chocolate.chocolateQuest.block;

import com.chocolate.chocolateQuest.block.BlockMobSpawnerTileEntity;
import java.util.Random;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockMobSpawner extends BlockContainer {

   public BlockMobSpawner() {
      super(Material.rock);
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return 0;
   }

   public int quantityDropped(Random par1Random) {
      return 0;
   }

   public void registerBlockIcons(IIconRegister iconRegister) {}

   public boolean isOpaqueCube() {
      return false;
   }

   public IIcon getIcon(int i, int j) {
      return Blocks.mob_spawner.getIcon(i, j);
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new BlockMobSpawnerTileEntity();
   }
}
