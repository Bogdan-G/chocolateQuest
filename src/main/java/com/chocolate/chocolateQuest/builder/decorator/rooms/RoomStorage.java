package com.chocolate.chocolateQuest.builder.decorator.rooms;

import com.chocolate.chocolateQuest.builder.decorator.RoomBase;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class RoomStorage extends RoomBase {

   public void placeDecorationBlock(Random random, World world, int x, int y, int z, int side) {
      if(side != 5 && random.nextInt(8) != 0) {
         Block id = Blocks.pumpkin;
         byte md = 0;
         if(side == 1) {
            id = Blocks.melon_block;
         }

         if(side == 2) {
            id = Blocks.hay_block;
         }

         if(side == 3) {
            id = Blocks.red_mushroom_block;
            md = 15;
         }

         int roomHeight = random.nextInt(super.properties.floorHeight);

         for(int i = 0; i < roomHeight; ++i) {
            world.setBlock(x, y + i, z, id, md, 3);
         }
      } else {
         this.decorateFullMonsterRoom(random, world, x, y, z, side);
      }

   }

   public int getType() {
      return 206;
   }
}
