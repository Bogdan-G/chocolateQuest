package com.chocolate.chocolateQuest.builder.decorator.rooms;

import com.chocolate.chocolateQuest.builder.BuilderHelper;
import com.chocolate.chocolateQuest.builder.decorator.RoomBase;
import java.util.Random;
import net.minecraft.world.World;

public class RoomLoot extends RoomBase {

   public void placeDecorationBlock(Random random, World world, int x, int y, int z, int side) {
      if(side == 5) {
         if(random.nextInt(10) == 0) {
            BuilderHelper.addTreasure(random, world, x, y, z, 2);
         } else if(random.nextInt(3) == 0) {
            BuilderHelper.addWeaponChest(random, world, x, y, z, 2);
         } else if(random.nextInt(3) == 0) {
            BuilderHelper.addChest(random, world, x, y, z, 2);
         } else {
            BuilderHelper.addMineralChest(random, world, x, y, z, 2);
         }
      } else if(random.nextInt(30) == 0) {
         BuilderHelper.addTreasure(random, world, x, y, z, 2);
         BuilderHelper.addWeaponChest(random, world, x, y, z, 2);
         BuilderHelper.addChest(random, world, x, y, z, 2);
         BuilderHelper.addMineralChest(random, world, x, y, z, 2);
      } else {
         this.decorateFullMonsterRoom(random, world, x, y, z, side);
      }

   }

   public int getType() {
      return 403;
   }
}
