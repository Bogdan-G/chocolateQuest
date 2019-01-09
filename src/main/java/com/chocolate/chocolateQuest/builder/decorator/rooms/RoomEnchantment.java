package com.chocolate.chocolateQuest.builder.decorator.rooms;

import com.chocolate.chocolateQuest.builder.decorator.RoomBase;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class RoomEnchantment extends RoomBase {

   public void placeDecorationBlock(Random random, World world, int x, int y, int z, int side) {
      if(side == 5) {
         world.setBlock(x, y, z, Blocks.enchanting_table);
      } else {
         for(int i = 0; i < super.properties.floorHeight; ++i) {
            world.setBlock(x, y + i, z, Blocks.bookshelf);
         }
      }

   }

   public int getType() {
      return 203;
   }
}
