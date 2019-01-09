package com.chocolate.chocolateQuest.builder.decorator;

import com.chocolate.chocolateQuest.builder.decorator.BuildingProperties;
import com.chocolate.chocolateQuest.builder.decorator.rooms.RoomStairs;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TowerSquare {

   BuildingProperties properties;
   int radio = 6;
   int floors = 4;


   public TowerSquare(BuildingProperties properties) {
      this.properties = properties;
   }

   public void configure(int floors, int width) {
      this.floors = floors;
      this.radio = Math.max(3, width / 2);
   }

   public void buildTower(Random random, World world, int x, int y, int z, ForgeDirection direction) {
      int floors = Math.max(1, this.floors - 1 + random.nextInt(5));
      int width = this.radio * 2;
      int originX = x;
      int originZ = z;
      x -= Math.abs(this.radio * direction.offsetZ);
      z -= Math.abs(this.radio * direction.offsetX);
      if(direction.offsetX < 0) {
         x -= width;
      }

      if(direction.offsetZ < 0) {
         z -= width;
      }

      RoomStairs stairs = new RoomStairs();
      stairs.configure(this.radio * 2, this.radio * 2, this.properties);

      int h;
      int posY;
      for(h = floors - 1; h >= 0; --h) {
         posY = y + h * (this.properties.floorHeight + 1);

         int sx;
         int posX;
         int posZ;
         for(sx = 1; sx < width; ++sx) {
            posX = x + sx;

            for(posZ = 1; posZ < width; ++posZ) {
               int posZ1 = z + posZ;
               this.properties.floor.generateFloor(world, posX, posY, posZ1);

               for(int h1 = 1; h1 < this.properties.floorHeight; ++h1) {
                  world.setBlockToAir(posX, posY + h1, posZ1);
               }

               this.properties.setWallBlock(world, posX, posY + this.properties.floorHeight, posZ1);
            }
         }

         stairs.decorate(random, world, x, posY + 1, z);

         for(sx = 0; sx <= width; ++sx) {
            posX = x + sx;
            this.properties.setWallBlock(world, posX, posY, z);
            this.properties.window.generateWindowX(world, posX, posY + 1, z);
            posZ = z + width;
            this.properties.setWallBlock(world, posX, posY, posZ);
            this.properties.window.generateWindowX(world, posX, posY + 1, posZ);
            posZ = z + sx;
            this.properties.setWallBlock(world, x, posY, posZ);
            this.properties.window.generateWindowZ(world, x, posY + 1, posZ);
            posX = x + width;
            this.properties.setWallBlock(world, posX, posY, posZ);
            this.properties.window.generateWindowZ(world, posX, posY + 1, posZ);
         }
      }

      this.properties.roof.generateRoof(world, x, y + floors * (this.properties.floorHeight + 1), z, width, width, false);

      for(h = 0; h < this.floors + 1; ++h) {
         posY = y + 1 + h * (this.properties.floorHeight + 1);
         this.properties.doors.generate(random, world, originX, posY, originZ, direction);
      }

   }
}
