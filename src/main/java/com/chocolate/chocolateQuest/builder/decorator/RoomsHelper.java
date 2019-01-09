package com.chocolate.chocolateQuest.builder.decorator;

import com.chocolate.chocolateQuest.builder.decorator.BuildingProperties;
import com.chocolate.chocolateQuest.builder.decorator.RoomBase;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomAlchemy;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomArmory;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomBedRoom;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomBlackSmith;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomDinning;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomEnchantment;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomEndPortal;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomFlag;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomJail;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomKitchen;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomLibrary;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomLibraryBig;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomNetherPortal;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomStairs;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomStorage;
import java.util.Random;
import net.minecraft.world.World;

public class RoomsHelper {

   static final int NORTH = 0;
   static final int EAST = 2;
   static final int SOUTH = 1;
   static final int WEST = 3;


   public static RoomBase getRoom(Random random, int sizeX, int sizeZ, BuildingProperties data) {
      RoomBase room = getRandomRoomType(random);
      room.configure(sizeX, sizeZ, data);
      return room;
   }

   public static RoomBase getRandomRoomType(Random random) {
      if(random.nextInt(400) == 0) {
         return new RoomEndPortal();
      } else if(random.nextInt(100) == 0) {
         return new RoomNetherPortal();
      } else if(random.nextInt(60) == 0) {
         return new RoomLibraryBig();
      } else if(random.nextInt(60) == 0) {
         return new RoomAlchemy();
      } else if(random.nextInt(60) == 0) {
         return new RoomBlackSmith();
      } else if(random.nextInt(60) == 0) {
         return new RoomEnchantment();
      } else if(random.nextInt(60) == 0) {
         return new RoomFlag();
      } else if(random.nextInt(60) == 0) {
         return new RoomArmory();
      } else if(random.nextInt(60) == 0) {
         return new RoomStorage();
      } else if(random.nextInt(60) == 0) {
         return new RoomJail();
      } else if(random.nextInt(60) == 0) {
         return new RoomDinning();
      } else {
         switch(random.nextInt(5)) {
         case 0:
            return new RoomBedRoom();
         case 1:
            return new RoomKitchen();
         case 2:
            return new RoomLibrary();
         default:
            return new RoomBase();
         }
      }
   }

   public static void buildRooms(World world, Random random, RoomBase[][] rooms, int x, int y, int z, BuildingProperties props) {
      int roomsX = rooms.length;
      int roomsZ = rooms[0].length;
      int roomSizeX = rooms[0][0].sizeX;
      int roomSizeZ = rooms[0][0].sizeZ;
      int floorHeight = props.floorHeight;

      for(int currentRoomX = 0; currentRoomX < roomsX; ++currentRoomX) {
         for(int currentRoomZ = 0; currentRoomZ < roomsZ; ++currentRoomZ) {
            int posX = currentRoomX * roomSizeX + x;
            int posZ = currentRoomZ * roomSizeZ + z;
            RoomBase room = rooms[currentRoomX][currentRoomZ];
            if(room != null) {
               room.decorate(random, world, posX, y, posZ);
            }
         }
      }

   }

   public static RoomBase[][] getRoomsArray(RoomBase[][] rooms, BuildingProperties data, Random random, int height, int sizeX, int sizeZ, boolean addStairs) {
      int roomsX = rooms.length;
      int roomsZ = rooms[0].length;

      int x;
      int z;
      for(x = 0; x < roomsX; ++x) {
         for(z = 0; z < roomsZ; ++z) {
            rooms[x][z] = getRoom(random, sizeX, sizeZ, data);
            if(x == 0) {
               rooms[x][z].wallEast = false;
            }

            if(x == roomsX - 1) {
               rooms[x][z].wallWest = false;
            }

            if(z == 0) {
               rooms[x][z].wallNorth = false;
            }

            if(z == roomsZ - 1) {
               rooms[x][z].wallSouth = false;
            }
         }
      }

      for(x = 0; x < roomsX; ++x) {
         for(z = 0; z < roomsZ; ++z) {
            if(x > 0 && rooms[x][z].getType() == rooms[x - 1][z].getType()) {
               rooms[x][z].decorateEast = false;
               rooms[x][z].wallEast = false;
            }

            if(x < roomsX - 1 && rooms[x][z].getType() == rooms[x + 1][z].getType()) {
               rooms[x][z].decorateWest = false;
               rooms[x][z].wallWest = false;
            }

            if(z > 0 && rooms[x][z].getType() == rooms[x][z - 1].getType()) {
               rooms[x][z].decorateNorth = false;
               rooms[x][z].wallNorth = false;
            }

            if(z < roomsZ - 1 && rooms[x][z].getType() == rooms[x][z + 1].getType()) {
               rooms[x][z].decorateSouth = false;
               rooms[x][z].wallSouth = false;
            }
         }
      }

      addDoorToRoom(rooms, 0, 0, random);
      x = random.nextInt(roomsX);
      z = random.nextInt(roomsZ);
      if(addStairs) {
         rooms[x][z] = (new RoomStairs()).copyDataFrom(rooms[x][z]);
      }

      return rooms;
   }

   public static boolean addDoorToRoom(RoomBase[][] rooms, int x, int z, Random random) {
      int side = random.nextInt(4);
      int rotation = random.nextBoolean()?1:-1;
      rooms[x][z].doorSet = true;

      for(int cont = 0; cont < 4; ++cont) {
         if(addDoorToSide(rooms, x, z, side)) {
            int nx = x;
            int nz = z;
            if(side == 0) {
               nz = z - 1;
               rooms[x][z].doorNorth = true;
               rooms[x][z - 1].doorSouth = true;
            } else if(side == 1) {
               nz = z + 1;
               rooms[x][z].doorSouth = true;
               rooms[x][z + 1].doorNorth = true;
            } else if(side == 2) {
               nx = x - 1;
               rooms[x][z].doorEast = true;
               rooms[x - 1][z].doorWest = true;
            } else if(side == 3) {
               nx = x + 1;
               rooms[x][z].doorWest = true;
               rooms[x + 1][z].doorEast = true;
            }

            addDoorToRoom(rooms, nx, nz, random);
         }

         side += rotation;
         if(side < 0) {
            side = 3;
         }

         if(side > 3) {
            side = 0;
         }
      }

      return true;
   }

   public static boolean addDoorToSide(RoomBase[][] rooms, int x, int z, int side) {
      if((z != 0 || side != 0) && (z != rooms[0].length - 1 || side != 1) && (x != 0 || side != 2) && (x != rooms.length - 1 || side != 3)) {
         int nx = x;
         int nz = z;
         if(side == 0) {
            nz = z - 1;
         } else if(side == 1) {
            nz = z + 1;
         } else if(side == 2) {
            nx = x - 1;
         } else if(side == 3) {
            nx = x + 1;
         }

         return !rooms[nx][nz].doorSet;
      } else {
         return false;
      }
   }
}
