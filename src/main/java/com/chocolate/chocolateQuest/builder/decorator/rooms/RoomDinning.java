package com.chocolate.chocolateQuest.builder.decorator.rooms;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.builder.decorator.RoomBase;
import java.util.Random;
import net.minecraft.world.World;

public class RoomDinning extends RoomBase {

   public void addRoomDecoration(Random random, World world, int posX, int posY, int posZ) {
      int sx = Math.max(1, super.sizeX - 3);
      int sz = Math.max(1, super.sizeZ - 3);

      for(int i = 2; i < sx; ++i) {
         for(int k = 2; k < sz; ++k) {
            world.setBlock(posX + i, posY, posZ + k, ChocolateQuest.table);
         }
      }

   }

   public int getType() {
      return 400;
   }
}
