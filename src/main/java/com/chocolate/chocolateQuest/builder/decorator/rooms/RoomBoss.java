package com.chocolate.chocolateQuest.builder.decorator.rooms;

import com.chocolate.chocolateQuest.builder.BuilderHelper;
import com.chocolate.chocolateQuest.builder.decorator.RoomBase;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import com.chocolate.chocolateQuest.entity.mob.registry.RegisterDungeonMobs;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class RoomBoss extends RoomBase {

   public void placeDecorationBlock(Random random, World world, int x, int y, int z, int side) {
      if(side == 5) {
         DungeonMonstersBase mob = (DungeonMonstersBase)RegisterDungeonMobs.mobList.get(super.properties.mobID);
         if(mob != null) {
            Entity e = mob.getBoss(world, x, y, z);
            if(e != null) {
               e.setPosition((double)x, (double)y, (double)z);
               world.spawnEntityInWorld(e);
               if(e.ridingEntity != null) {
                  world.spawnEntityInWorld(e.ridingEntity);
               }
            }
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
