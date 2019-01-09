package com.chocolate.chocolateQuest.builder;

import com.chocolate.chocolateQuest.API.BuilderBlockData;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BuilderBlockDataBiomeTop extends BuilderBlockData {

   public BuilderBlockDataBiomeTop() {
      super(Blocks.grass);
   }

   public void placeBlock(World world, int x, int y, int z, Random random) {
      BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
      world.setBlock(x, y, z, biome.topBlock, biome.field_150604_aj, 3);
   }
}
