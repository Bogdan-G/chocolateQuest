package com.chocolate.chocolateQuest.builder.decorator.rooms;

import com.chocolate.chocolateQuest.builder.decorator.RoomBase;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class RoomEndPortal extends RoomBase {

   public void addRoomDecoration(Random random, World world, int posX, int posY, int posZ) {
      int x = posX + super.sizeX / 2;
      int z = posZ + super.sizeZ / 2;
      x -= 3;
      z -= 3;
      int y = posY + 1;
      Block block = Blocks.end_portal_frame;

      for(int i = 1; i < 4; ++i) {
         world.setBlock(x + i, y, z + 4, block, 0, 3);
         world.setBlock(x + i, y, z, block, 2, 3);
         world.setBlock(x, y, z + i, block, 3, 3);
         world.setBlock(x + 4, y, z + i, block, 1, 3);
      }

   }

   public int getType() {
      return 208;
   }
}
