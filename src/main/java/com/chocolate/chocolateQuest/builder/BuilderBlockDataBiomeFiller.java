package com.chocolate.chocolateQuest.builder;

import com.chocolate.chocolateQuest.API.BuilderBlockData;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BuilderBlockDataBiomeFiller extends BuilderBlockData {

   public BuilderBlockDataBiomeFiller() {
      super(Blocks.grass);
   }

   public void placeBlock(World world, int x, int y, int z, Random random) {
      BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
      world.setBlock(x, y, z, biome.fillerBlock, biome.field_76754_C, 3);
   }
}
