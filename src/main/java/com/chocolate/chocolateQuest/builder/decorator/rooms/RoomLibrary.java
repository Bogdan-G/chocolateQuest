package com.chocolate.chocolateQuest.builder.decorator.rooms;

import com.chocolate.chocolateQuest.builder.decorator.RoomBase;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RoomLibrary extends RoomBase {

   public void placeDecorationBlock(Random random, World world, int x, int y, int z, int side) {
      if(side != 5 && random.nextInt(3) != 0) {
         if(random.nextInt(50) == 0) {
            this.placeFrame(random, world, x, y + 1, z, side, new ItemStack(Items.clock));
         } else if(random.nextInt(100) == 0) {
            this.placePainting(random, world, x, y + 1, z, side);
         } else {
            this.decorateFullMonsterRoom(random, world, x, y, z, side);
         }
      } else {
         world.setBlock(x, y, z, Blocks.bookshelf);
      }

   }

   public int getType() {
      return 2;
   }
}
