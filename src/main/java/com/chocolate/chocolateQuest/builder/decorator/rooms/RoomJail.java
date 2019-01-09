package com.chocolate.chocolateQuest.builder.decorator.rooms;

import com.chocolate.chocolateQuest.builder.decorator.RoomBase;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class RoomJail extends RoomBase {

   final Block BARS_BLOCK;


   public RoomJail() {
      this.BARS_BLOCK = Blocks.iron_bars;
   }

   public int getType() {
      return 401;
   }

   public void addRoomDecoration(Random random, World world, int posX, int posY, int posZ) {
      int roomCenterX = posX + super.sizeX / 2;
      int roomCenterZ = posZ + super.sizeZ / 2;
      this.decorateFullMonsterRoom(random, world, roomCenterX, posY, roomCenterZ, 5);
      int villagers;
      int v;
      int villagerOffset;
      if(super.sizeX > 1) {
         if(!super.doorNorth && super.decorateNorth) {
            for(villagers = 1; villagers < super.sizeX; ++villagers) {
               for(v = 0; v < super.properties.floorHeight - 1; ++v) {
                  world.setBlock(posX + villagers, posY + v, posZ + 3, this.BARS_BLOCK);
               }
            }

            villagers = Math.max(1, super.sizeX / 6);

            for(v = 0; v < villagers; ++v) {
               if(random.nextBoolean()) {
                  villagerOffset = random.nextInt(super.sizeX - 1) + 1;
                  this.spawnPrisoner(world, random, posX + villagerOffset, posY, posZ + 1);
               }
            }
         }

         if(!super.doorSouth && super.decorateSouth) {
            for(villagers = 1; villagers < super.sizeX; ++villagers) {
               for(v = 0; v < super.properties.floorHeight - 1; ++v) {
                  world.setBlock(posX + villagers, posY + v, posZ + super.sizeZ - 3, this.BARS_BLOCK);
               }
            }

            villagers = Math.max(1, super.sizeX / 6);

            for(v = 0; v < villagers; ++v) {
               if(random.nextBoolean()) {
                  villagerOffset = random.nextInt(super.sizeX - 1) + 1;
                  this.spawnPrisoner(world, random, posX + villagerOffset, posY, posZ + super.sizeZ - 1);
               }
            }
         }
      }

      if(super.sizeZ > 1) {
         if(!super.doorEast && super.decorateEast) {
            for(villagers = 1; villagers < super.sizeZ; ++villagers) {
               for(v = 0; v < super.properties.floorHeight - 1; ++v) {
                  world.setBlock(posX + 3, posY + v, posZ + villagers, this.BARS_BLOCK);
               }
            }

            villagers = Math.max(1, super.sizeZ / 3);

            for(v = 0; v < villagers; ++v) {
               if(random.nextBoolean()) {
                  villagerOffset = random.nextInt(super.sizeZ - 1) + 1;
                  this.spawnPrisoner(world, random, posX + 1, posY, posZ + villagerOffset);
               }
            }
         }

         if(!super.doorWest && super.decorateWest) {
            for(villagers = 1; villagers < super.sizeZ; ++villagers) {
               for(v = 0; v < super.properties.floorHeight - 1; ++v) {
                  world.setBlock(posX + super.sizeX - 3, posY + v, posZ + villagers, this.BARS_BLOCK);
               }
            }

            villagers = Math.max(1, super.sizeZ / 3);

            for(v = 0; v < villagers; ++v) {
               if(random.nextBoolean()) {
                  villagerOffset = random.nextInt(super.sizeZ - 1) + 1;
                  this.spawnPrisoner(world, random, posX + super.sizeX - 1, posY, posZ + villagerOffset);
               }
            }
         }
      }

   }

   public void spawnPrisoner(World world, Random random, int posX, int posY, int posZ) {}
}
