package com.chocolate.chocolateQuest.builder.decorator;

import com.chocolate.chocolateQuest.builder.decorator.BuildingProperties;
import com.chocolate.chocolateQuest.builder.decorator.TowerSquare;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomStairs;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TowerRound extends TowerSquare {

   public TowerRound(BuildingProperties properties) {
      super(properties);
   }

   public void buildTower(Random random, World world, int x, int y, int z, ForgeDirection direction) {
      int floors = Math.max(1, super.floors - 1 + random.nextInt(5));
      int radioExtended = super.radio + 1;
      int radioDecreased = super.radio - 1;
      RoomStairs stairs = new RoomStairs();
      stairs.configure(super.radio * 2 - 2, super.radio * 2 - 2, super.properties);
      stairs.clearWalls();
      int currentY = y + floors * (super.properties.floorHeight + 1);
      x += super.radio * direction.offsetX;
      z += super.radio * direction.offsetZ;
      this.buildRoof(random, world, x, y, z, floors);

      int h;
      int posY;
      for(h = 0; h <= floors; ++h) {
         for(posY = -radioExtended; posY < radioExtended; ++posY) {
            for(int pz = -radioExtended; pz < radioExtended; ++pz) {
               int dist = posY * posY + pz * pz;
               int blockX = x + posY;
               int blockY = currentY;
               int blockZ = z + pz;
               int i;
               if(dist < super.radio * super.radio) {
                  super.properties.floor.generateFloor(world, blockX, currentY, blockZ);
                  super.properties.wallBlock.placeBlock(world, blockX, currentY + super.properties.floorHeight, blockZ, random);

                  for(i = 1; i < super.properties.floorHeight - 1; ++i) {
                     world.setBlock(blockX, blockY + i, blockZ, Blocks.air);
                  }

                  if(dist >= radioDecreased * radioDecreased) {
                     stairs.placeRandomDecorationBlock(random, world, blockX, blockY + 1, blockZ, 0);
                  }
               } else if(dist < radioExtended * radioExtended) {
                  for(i = 0; i <= super.properties.floorHeight; ++i) {
                     super.properties.wallBlock.placeBlock(world, blockX, blockY + i, blockZ, random);
                  }

                  super.properties.window.generateWindowX(world, blockX, blockY + 1, blockZ);
               }
            }
         }

         stairs.addRoomDecoration(random, world, x - super.radio, currentY + 1, z - super.radio + 1);
         currentY -= super.properties.floorHeight + 1;
      }

      x -= super.radio * direction.offsetX;
      z -= super.radio * direction.offsetZ;

      for(h = 0; h < super.floors + 1; ++h) {
         posY = y + 1 + h * (super.properties.floorHeight + 1);
         super.properties.doors.generate(random, world, x, posY, z, direction);
      }

   }

   public void buildRoof(Random random, World world, int x, int y, int z, int floors) {
      int currentY = y + floors * super.properties.floorHeight + 1;
      int radioExtended = super.radio + 1;
      int var10000 = super.properties.roof.floorType;
      super.properties.roof.getClass();
      int px;
      int pz;
      int blockX;
      int blockY;
      int blockZ;
      if(var10000 != 0) {
         var10000 = super.properties.roof.floorType;
         super.properties.roof.getClass();
         if(var10000 != 5) {
            for(px = -radioExtended; px < radioExtended; ++px) {
               for(pz = -radioExtended; pz < radioExtended; ++pz) {
                  int var19 = px * px + pz * pz;
                  blockX = x + px;
                  blockY = currentY + super.properties.floorHeight + 1;
                  blockZ = z + pz;
                  if(var19 < super.radio * super.radio) {
                     super.properties.wallBlock.placeBlock(world, blockX, blockY, blockZ, random);
                  } else if(var19 < radioExtended * radioExtended) {
                     super.properties.wallBlock.placeBlock(world, blockX, blockY, blockZ, random);
                     super.properties.roof.setRoofBlock(world, blockX, blockY + 1, blockZ);
                  }
               }
            }

            return;
         }
      }

      px = super.radio;
      pz = px + 1;
      byte dist = 2;

      for(blockX = 0; blockX <= super.radio * dist; ++blockX) {
         for(blockY = -pz; blockY < pz; ++blockY) {
            for(blockZ = -pz; blockZ < pz; ++blockZ) {
               int dist1 = blockY * blockY + blockZ * blockZ;
               int blockX1 = x + blockY;
               int blockY1 = currentY + super.properties.floorHeight + 1 + blockX;
               int blockZ1 = z + blockZ;
               if(dist1 >= px * px && dist1 < pz * pz) {
                  super.properties.setWallBlock(world, blockX1, blockY1, blockZ1);
               }
            }
         }

         if(blockX % dist == 0) {
            --px;
            pz = px + 1;
         }
      }

   }
}
