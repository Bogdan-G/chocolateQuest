package com.chocolate.chocolateQuest.API;

import com.chocolate.chocolateQuest.builder.schematic.Schematic;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBeach;
import net.minecraft.world.biome.BiomeGenDesert;
import net.minecraft.world.biome.BiomeGenHell;
import net.minecraft.world.biome.BiomeGenMushroomIsland;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraft.world.biome.BiomeGenRiver;
import net.minecraft.world.biome.BiomeGenSwamp;

public class BuilderBlockData {

   public Block id;
   public int metadata = 0;


   public BuilderBlockData(Block block) {
      this.id = block;
      this.metadata = 0;
   }

   public BuilderBlockData(Block id, int metadata) {
      this.id = id;
      this.metadata = metadata;
   }

   public void placeBlock(World world, int x, int y, int z, Random random) {
      world.setBlock(x, y, z, this.id, this.metadata, 2);
   }

   public void placeBlock(Schematic world, int x, int y, int z, Random random) {
      world.setBlockAndMetadata(x, y, z, this.id, (byte)this.metadata);
   }

   public static BuilderBlockData getRoadBlockForBiome(BiomeGenBase biome) {
      return !(biome instanceof BiomeGenOcean) && !(biome instanceof BiomeGenRiver) && !(biome instanceof BiomeGenSwamp) && !(biome instanceof BiomeGenBeach)?new BuilderBlockData(Blocks.gravel):new BuilderBlockData(Blocks.planks);
   }

   public static BuilderBlockData getGroundBlockForBiome(BiomeGenBase biome) {
      return biome instanceof BiomeGenDesert?new BuilderBlockData(Blocks.sand):(biome instanceof BiomeGenHell?new BuilderBlockData(Blocks.netherrack):(biome instanceof BiomeGenMushroomIsland?new BuilderBlockData(Blocks.mycelium):new BuilderBlockData(Blocks.grass)));
   }

   public static BuilderBlockData getWallBlockForBiome(BiomeGenBase biome) {
      return biome instanceof BiomeGenDesert?new BuilderBlockData(Blocks.sandstone):(biome instanceof BiomeGenHell?new BuilderBlockData(Blocks.nether_brick):new BuilderBlockData(Blocks.stonebrick));
   }

   public static BuilderBlockData getRandomWallBlock(Random random) {
      Block ret = Blocks.stonebrick;
      if(random.nextInt(500) == 0) {
         ret = Blocks.quartz_block;
      }

      if(random.nextInt(20) == 0) {
         ret = Blocks.nether_brick;
      } else if(random.nextInt(10) == 0) {
         ret = Blocks.sandstone;
      } else if(random.nextInt(8) == 0) {
         ret = Blocks.brick_block;
      }

      if(random.nextInt(10) == 0) {
         ret = Blocks.cobblestone;
      }

      if(random.nextInt(10) == 0) {
         ret = Blocks.log;
      }

      return new BuilderBlockData(ret, 0);
   }
}
