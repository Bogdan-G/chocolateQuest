package com.chocolate.chocolateQuest.builder.decorator.rooms;

import com.chocolate.chocolateQuest.builder.decorator.RoomBase;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class RoomAlchemy extends RoomBase {

   public void placeDecorationBlock(Random random, World world, int x, int y, int z, int side) {
      if(side != 5 && random.nextInt(5) != 0) {
         if(random.nextInt(5) == 0) {
            world.setBlock(x, y, z, Blocks.cauldron, random.nextInt(3), 3);
         } else {
            this.decorateFullMonsterRoom(random, world, x, y, z, side);
         }
      } else {
         world.setBlock(x, y, z, Blocks.brewing_stand, 0, 3);
      }

   }

   public int getType() {
      return 201;
   }
}
