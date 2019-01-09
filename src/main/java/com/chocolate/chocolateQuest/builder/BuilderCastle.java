package com.chocolate.chocolateQuest.builder;

import com.chocolate.chocolateQuest.API.BuilderBase;
import com.chocolate.chocolateQuest.API.HelperReadConfig;
import com.chocolate.chocolateQuest.builder.BuilderHelper;
import com.chocolate.chocolateQuest.builder.decorator.BuildingProperties;
import com.chocolate.chocolateQuest.builder.decorator.EntranceGenerator;
import com.chocolate.chocolateQuest.builder.decorator.RoomBase;
import com.chocolate.chocolateQuest.builder.decorator.RoomsHelper;
import com.chocolate.chocolateQuest.builder.decorator.TowerRound;
import com.chocolate.chocolateQuest.builder.decorator.TowerSquare;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomBoss;
import java.util.Properties;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BuilderCastle extends BuilderBase {

   final int MIN_ROOM_SIZE = 5;
   int roomSize = 10;
   int maxSize = 60;
   int posY = 64;
   BuildingProperties properties;


   public BuilderBase load(Properties prop) {
      this.maxSize = Math.max(30, HelperReadConfig.getIntegerProperty(prop, "maxSize", 60));
      this.roomSize = Math.max(6, HelperReadConfig.getIntegerProperty(prop, "roomSize", 8));
      this.properties = new BuildingProperties();
      this.properties.load(prop);
      return this;
   }

   public void generate(Random random, World world, int x, int z, int idMob) {
      x -= this.maxSize / 2;
      z -= this.maxSize / 2;
      this.generate(random, world, x, this.posY, z, idMob);
   }

   public void generate(Random random, World world, int x, int y, int z, int idMob) {
      this.properties.initialize(random);
      byte offset = 4;
      BuilderHelper.clearArea(random, world, x - offset, y, z - offset, this.maxSize + offset * 2, this.maxSize + offset * 2);
      this.generateCastleStructure(random, world, x, y, z, this.maxSize, this.maxSize);
   }

   public void generateCastleStructure(Random random, World world, int x, int y, int z, int maxSizeX, int maxSizeZ) {
      int sizeQuartX = maxSizeX / 4;
      int sizeQuartZ = maxSizeZ / 4;
      int sizeX = sizeQuartX + random.nextInt(sizeQuartX * 3);
      int sizeZ = sizeQuartZ + random.nextInt(sizeQuartZ * 3);
      int offsetX = random.nextInt(sizeQuartX);
      int offsetZ = random.nextInt(sizeQuartZ);
      sizeX = Math.max(this.roomSize, sizeX);
      sizeZ = Math.max(this.roomSize, sizeZ);
      x += offsetX;
      z += offsetZ;
      boolean walkableRoof = false;
      if(Math.min(sizeX, sizeZ) > this.roomSize) {
         this.generateCastleStructure(random, world, x, y + (this.properties.floorHeight + 1) * 2, z, sizeX, sizeZ);
         walkableRoof = true;
      }

      this.generateSquaredStructure(random, world, x, y, z, sizeX, sizeZ, 1, walkableRoof, false);
      this.generateStructureAtSide(random, world, x + sizeX, y, z, sizeX, offsetZ, 1, ForgeDirection.NORTH);
      this.generateStructureAtSide(random, world, x, y, z + sizeZ, sizeX, maxSizeZ - sizeZ - offsetZ, 1, ForgeDirection.SOUTH);
      this.generateStructureAtSide(random, world, x, y, z + sizeZ, offsetX, sizeZ, 1, ForgeDirection.WEST);
      this.generateStructureAtSide(random, world, x + sizeX, y, z, maxSizeX - sizeX - offsetX, sizeZ, 1, ForgeDirection.EAST);
      EntranceGenerator entranceGenerator = new EntranceGenerator(this.properties);
      entranceGenerator.generateEntance(world, x + sizeX / 2, y, z, ForgeDirection.NORTH);
      entranceGenerator.generateEntance(world, x + sizeX / 2, y, z + sizeZ, ForgeDirection.SOUTH);
      entranceGenerator.generateEntance(world, x, y, z + sizeZ / 2, ForgeDirection.WEST);
      entranceGenerator.generateEntance(world, x + sizeX, y, z + sizeZ / 2, ForgeDirection.EAST);
   }

   public void generateStructureAtSide(Random random, World world, int x, int y, int z, int maxSizeX, int maxSizeZ, int floors, ForgeDirection side) {
      if(maxSizeX > 0 && maxSizeZ > 0) {
         int sizeX = Math.max(random.nextInt(maxSizeX), this.roomSize);
         int sizeZ = Math.max(random.nextInt(maxSizeZ), this.roomSize);
         boolean addStairs = false;
         int emptyAreaLength = 0;
         if(side.offsetX != 0) {
            emptyAreaLength = maxSizeX;
         } else if(side.offsetZ != 0) {
            emptyAreaLength = maxSizeZ;
         }

         if(emptyAreaLength > this.roomSize + this.roomSize / 2) {
            this.generateSquaredStructure(random, world, x, y, z, sizeX, sizeZ, floors, false, false, addStairs);
            int doorOffsetX = side.offsetZ * sizeZ / 2;
            int doorOffsetZ = side.offsetX * sizeX / 2;

            int offsetX;
            for(offsetX = 0; offsetX <= floors; ++offsetX) {
               this.properties.doors.generate(random, world, x + doorOffsetX, y + 1 + (this.properties.floorHeight + 1) * offsetX, z + doorOffsetZ, side);
            }

            this.properties.doors.generateSquared(random, world, x + doorOffsetX, y + 1 + (this.properties.floorHeight + 1) * (floors + 1), z + doorOffsetZ, side);
            emptyAreaLength = 0;
            if(side.offsetX != 0) {
               emptyAreaLength = maxSizeX - sizeX;
            } else if(side.offsetZ != 0) {
               emptyAreaLength = maxSizeZ - sizeZ;
            }

            if(side.offsetX < 0) {
               x += sizeX;
            }

            if(side.offsetZ < 0) {
               z += sizeZ;
            }

            offsetX = side.offsetX * sizeX;
            int offsetZ = side.offsetZ * sizeZ;
            if(side.offsetX != 0) {
               maxSizeX -= sizeX;
            } else if(side.offsetZ != 0) {
               maxSizeZ -= sizeZ;
            }

            if(emptyAreaLength > this.roomSize) {
               this.generateStructureAtSide(random, world, x + offsetX, y, z + offsetZ, sizeX, sizeZ, floors, side);
            } else if(emptyAreaLength > 5) {
               if(side.offsetX != 0) {
                  maxSizeZ = sizeZ;
               } else if(side.offsetZ != 0) {
                  maxSizeX = sizeX;
               }

               this.generateTowersAtSide(random, world, x + offsetX, y, z + offsetZ, maxSizeX, maxSizeZ, floors, side);
            }
         } else {
            if(emptyAreaLength <= 5) {
               return;
            }

            this.generateTowersAtSide(random, world, x, y, z, maxSizeX, maxSizeZ, floors, side);
         }

      }
   }

   public void generateTowersAtSide(Random random, World world, int x, int y, int z, int maxSizeX, int maxSizeZ, int floors, ForgeDirection side) {
      int emptyAreaLength = 0;
      if(side.offsetX != 0) {
         emptyAreaLength = maxSizeX;
      } else if(side.offsetZ != 0) {
         emptyAreaLength = maxSizeZ;
      }

      int width = 0;
      if(side.offsetZ != 0) {
         width = maxSizeX;
      } else if(side.offsetX != 0) {
         width = maxSizeZ;
      }

      int offsetZ;
      if(width < this.roomSize * 2) {
         width = Math.max(5, Math.min(emptyAreaLength, width));
         Object offsetX = random.nextBoolean()?new TowerSquare(this.properties):new TowerRound(this.properties);
         ((TowerSquare)offsetX).configure(floors, width);
         offsetZ = side.offsetZ * maxSizeX / 2;
         int tower = side.offsetX * maxSizeZ / 2;
         ((TowerSquare)offsetX).buildTower(random, world, x + offsetZ, y, z + tower, side);
      } else {
         width = Math.max(6, Math.min(emptyAreaLength, width));
         int offsetX1 = side.offsetZ * (maxSizeX - 3);
         offsetZ = side.offsetX * (maxSizeZ - 3);
         Object tower1 = random.nextBoolean()?new TowerSquare(this.properties):new TowerRound(this.properties);
         ((TowerSquare)tower1).configure(floors, width);
         ((TowerSquare)tower1).buildTower(random, world, x + offsetX1, y, z + offsetZ, side);
         offsetX1 = side.offsetZ * 3;
         offsetZ = side.offsetX * 3;
         tower1 = random.nextBoolean()?new TowerSquare(this.properties):new TowerRound(this.properties);
         ((TowerSquare)tower1).configure(floors, width);
         ((TowerSquare)tower1).buildTower(random, world, x + offsetX1, y, z + offsetZ, side);
      }

   }

   public void generatePagoda(Random random, World world, int x, int y, int z, int sizeX, int sizeZ) {
      byte stepSize = 4;
      byte stepSize2 = 2;
      byte chapelFloors = 3;
      int xPos = x;
      int yPos = y;
      int zPos = z;
      int floorHeight = this.properties.floorHeight;
      boolean boss = true;

      for(int i = chapelFloors; i >= 0; --i) {
         if(i <= 1 || i * stepSize <= sizeX - this.roomSize && i * stepSize <= sizeZ - this.roomSize) {
            int X = xPos + stepSize2 * i;
            int Y = yPos + (floorHeight + 1) * i;
            int Z = zPos + stepSize2 * i;
            int sX = sizeX - stepSize * i;
            int sZ = sizeZ - stepSize * i;
            this.generateSquaredStructure(random, world, X, Y, Z, sX, sZ, 0, !boss, boss);
            boss = false;
            this.properties.roof.roofPyramid(world, X - stepSize2, Y + floorHeight - 1, Z - stepSize2, sX + stepSize, sZ + stepSize, stepSize2);
         }
      }

   }

   public void generateSquaredStructure(Random random, World world, int x, int y, int z, int sizeX, int sizeZ, int floors, boolean walkableRoof, boolean boss) {
      this.generateSquaredStructure(random, world, x, y, z, sizeX, sizeZ, floors, walkableRoof, boss, true);
   }

   public void generateSquaredStructure(Random random, World world, int x, int y, int z, int sizeX, int sizeZ, int floors, boolean walkableRoof, boolean boss, boolean addStairs) {
      int roomsX = Math.max(1, sizeX / this.roomSize);
      int roomsZ = Math.max(1, sizeZ / this.roomSize);
      int roomSizeX = sizeX / roomsX;
      int roomSizeZ = sizeZ / roomsZ;
      int lastRoomOffsetX = sizeX - roomsX * roomSizeX;
      int lastRoomOffsetZ = sizeZ - roomsZ * roomSizeZ;
      int currentY = y;
      int floorHeight = this.properties.floorHeight;

      int roomsArray;
      int currentfloor;
      for(currentfloor = 0; currentfloor <= sizeX; ++currentfloor) {
         for(roomsArray = 0; roomsArray < sizeZ; ++roomsArray) {
            this.properties.floor.generateFloor(world, x + currentfloor, currentY, z + roomsArray);
         }
      }

      for(currentfloor = 0; currentfloor <= floors; ++currentfloor) {
         int i;
         for(roomsArray = 0; roomsArray <= sizeX; ++roomsArray) {
            for(i = 0; i < sizeZ; ++i) {
               this.properties.wallBlock.placeBlock(world, x + roomsArray, currentY + floorHeight, z + i, random);
               if(currentfloor != floors) {
                  this.properties.floor.generateFloor(world, x + roomsArray, currentY + floorHeight + 1, z + i);
               } else {
                  this.properties.wallBlock.placeBlock(world, x + roomsArray, currentY + floorHeight + 1, z + i, random);
               }
            }
         }

         ++currentY;

         for(roomsArray = 0; roomsArray <= sizeX; ++roomsArray) {
            for(i = -1; i <= floorHeight; ++i) {
               this.properties.wallBlock.placeBlock(world, x + roomsArray, currentY + i, z, random);
               this.properties.wallBlock.placeBlock(world, x + roomsArray, currentY + i, z + sizeZ, random);
            }

            if(roomsArray > 0 && roomsArray < sizeX) {
               this.properties.window.generateWindowX(world, x + roomsArray, currentY, z);
               this.properties.window.generateWindowX(world, x + roomsArray, currentY, z + sizeZ);
            }
         }

         for(roomsArray = 0; roomsArray <= sizeZ; ++roomsArray) {
            for(i = -1; i <= floorHeight; ++i) {
               this.properties.wallBlock.placeBlock(world, x, currentY + i, z + roomsArray, random);
               this.properties.wallBlock.placeBlock(world, x + sizeX, currentY + i, z + roomsArray, random);
            }

            if(roomsArray > 0 && roomsArray < sizeZ) {
               this.properties.window.generateWindowZ(world, x, currentY, z + roomsArray);
               this.properties.window.generateWindowZ(world, x + sizeX, currentY, z + roomsArray);
            }
         }

         RoomBase[][] var24 = new RoomBase[roomsX][roomsZ];
         var24 = RoomsHelper.getRoomsArray(var24, this.properties, random, floorHeight, roomSizeX, roomSizeZ, addStairs);

         RoomBase room;
         for(i = 0; i < var24.length; ++i) {
            room = var24[i][var24[0].length - 1];
            if(room != null) {
               room.sizeZ += lastRoomOffsetZ;
            }
         }

         for(i = 0; i < var24[0].length; ++i) {
            room = var24[var24.length - 1][i];
            if(room != null) {
               room.sizeX += lastRoomOffsetX;
            }
         }

         if(currentfloor == floors && boss) {
            var24[0][0] = (new RoomBoss()).copyDataFrom(var24[0][0]);
         }

         RoomsHelper.buildRooms(world, random, var24, x, currentY, z, this.properties);
         currentY += floorHeight;
      }

      this.properties.roof.generateRoof(world, x, currentY + 1, z, sizeX, sizeZ, walkableRoof);
   }

   public String getName() {
      return "castle";
   }
}
