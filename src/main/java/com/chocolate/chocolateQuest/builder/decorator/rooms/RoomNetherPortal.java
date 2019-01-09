package com.chocolate.chocolateQuest.builder.decorator.rooms;

import com.chocolate.chocolateQuest.builder.decorator.RoomBase;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class RoomNetherPortal extends RoomBase {

   public void addRoomDecoration(Random random, World world, int posX, int posY, int posZ) {
      int x = posX + super.sizeX / 2;
      int z = posZ + super.sizeZ / 2;
      --z;
      int y = posY - 1;
      Block block = Blocks.obsidian;
      world.setBlock(x, y, z, block);
      world.setBlock(x + 1, y, z, block);
      world.setBlock(x, y + 4, z, block);
      world.setBlock(x + 1, y + 4, z, block);

      int i;
      for(i = 0; i <= 4; ++i) {
         world.setBlock(x - 1, y + i, z, block);
         world.setBlock(x + 2, y + i, z, block);
      }

      if(random.nextInt(3) == 0) {
         for(i = 1; i <= 3; ++i) {
            world.setBlock(x, y + i, z, Blocks.portal);
            world.setBlock(x + 1, y + i, z, Blocks.portal);
         }
      }

   }

   public int getType() {
      return 208;
   }
}
