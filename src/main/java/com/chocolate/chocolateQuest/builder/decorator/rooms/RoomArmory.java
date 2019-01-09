package com.chocolate.chocolateQuest.builder.decorator.rooms;

import com.chocolate.chocolateQuest.builder.decorator.RoomBase;
import java.util.Random;
import net.minecraft.world.World;

public class RoomArmory extends RoomBase {

   public void placeDecorationBlock(Random random, World world, int x, int y, int z, int side) {
      if(side == 5) {
         this.placeArmorStand(random, world, x, y, z, side);
      } else if(random.nextInt(20) == 0) {
         this.placeArmorStand(random, world, x, y, z, side);
      } else if(random.nextInt(8) == 0) {
         this.placeShied(random, world, x, y + 1, z, side);
      } else if(random.nextInt(16) == 0) {
         this.addWeaponChest(random, world, x, y, z, side);
      } else {
         this.decorateFullMonsterRoom(random, world, x, y, z, side);
      }

   }

   public int getType() {
      return 202;
   }
}
