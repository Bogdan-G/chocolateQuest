package com.chocolate.chocolateQuest.builder;

import com.chocolate.chocolateQuest.API.BuilderBlockData;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BuilderBlockStoneJungle extends BuilderBlockData {

   public BuilderBlockStoneJungle() {
      super(Blocks.stonebrick);
   }

   public void placeBlock(World world, int x, int y, int z, Random random) {
      world.setBlock(x, y, z, super.id, random.nextInt(3) == 0?1:0, 3);
   }
}
