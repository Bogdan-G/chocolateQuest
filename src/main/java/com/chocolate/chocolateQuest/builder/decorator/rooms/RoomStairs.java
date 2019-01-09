package com.chocolate.chocolateQuest.builder.decorator.rooms;

import com.chocolate.chocolateQuest.builder.decorator.RoomBase;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class RoomStairs extends RoomBase {

   public void placeDecorationBlock(Random random, World world, int x, int y, int z, int side) {
      int widthStairs = Math.max(2, Math.min(super.sizeX, super.sizeZ) / 4);
      int roomHeight = super.properties.floorHeight;
      if(side == 5) {
         int j;
         int step;
         for(int stairBlock = 0; stairBlock <= roomHeight; ++stairBlock) {
            for(j = -widthStairs + 1; j < widthStairs; ++j) {
               for(step = -widthStairs + 1; step < widthStairs; ++step) {
                  world.setBlockToAir(x + j, y + stairBlock, z + step);
               }
            }

            super.properties.wallBlock.placeBlock(world, x, y + stairBlock, z, random);
         }

         Block var14 = Blocks.stone_brick_stairs;

         for(j = 0; j <= roomHeight; ++j) {
            boolean var15 = true;
            int direction = (y + j) % 4;
            int step1;
            if(direction == 0) {
               for(step = 1; step < widthStairs; ++step) {
                  world.setBlock(x, y + j, z + step, var14, 0, 3);

                  for(step1 = 1; step1 < widthStairs; ++step1) {
                     super.properties.wallBlock.placeBlock(world, x + step, y + j, z + step1, random);
                  }
               }
            }

            if(direction == 1) {
               for(step = 1; step < widthStairs; ++step) {
                  world.setBlock(x + step, y + j, z, var14, 3, 3);

                  for(step1 = 1; step1 < widthStairs; ++step1) {
                     super.properties.wallBlock.placeBlock(world, x + step1, y + j, z - step, random);
                  }
               }
            }

            if(direction == 2) {
               for(step = 1; step < widthStairs; ++step) {
                  world.setBlock(x, y + j, z - step, var14, 1, 3);

                  for(step1 = 1; step1 < widthStairs; ++step1) {
                     super.properties.wallBlock.placeBlock(world, x - step, y + j, z - step1, random);
                  }
               }
            }

            if(direction == 3) {
               for(step = 1; step < widthStairs; ++step) {
                  world.setBlock(x - step, y + j, z, var14, 2, 3);

                  for(step1 = 1; step1 < widthStairs; ++step1) {
                     super.properties.wallBlock.placeBlock(world, x - step1, y + j, z + step, random);
                  }
               }
            }
         }
      } else {
         this.decorateFullMonsterRoom(random, world, x, y, z, side);
      }

   }

   public int getType() {
      return 202;
   }
}
