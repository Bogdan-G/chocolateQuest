package com.chocolate.chocolateQuest.builder.decorator;

import com.chocolate.chocolateQuest.builder.decorator.BuildingProperties;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DecoratorWindow {

   int floorType = 0;
   Block window;
   public int height;
   public int width;
   int separation;
   BuildingProperties properties;


   public DecoratorWindow(Random random, BuildingProperties properties) {
      this.window = Blocks.glass_pane;
      this.height = 1;
      this.width = 1;
      this.separation = 3;
      this.floorType = random.nextInt(4);
      if(random.nextInt(4) == 0) {
         this.window = Blocks.glass;
      } else if(random.nextInt(4) == 0) {
         this.window = Blocks.iron_bars;
      }

      this.width = 1 + random.nextInt(4);
      this.separation = 1 + random.nextInt(5);
      this.height = 1 + random.nextInt(3);
      this.properties = properties;
   }

   public void generateWindowX(World world, int x, int y, int z) {
      this.generateWindow(world, x, y, z, true);
   }

   public void generateWindowZ(World world, int x, int y, int z) {
      this.generateWindow(world, x, y, z, false);
   }

   public void generateWindow(World world, int x, int y, int z, boolean aroundX) {
      switch(this.floorType) {
      case 1:
         if(this.width < 2) {
            this.simple(world, x, y, z, aroundX);
         } else {
            this.framed(world, x, y, z, aroundX);
         }
         break;
      case 2:
         this.open(world, x, y, z, aroundX);
         break;
      default:
         this.simple(world, x, y, z, aroundX);
      }

   }

   public void simple(World world, int x, int y, int z, boolean aroundX) {
      int pos = Math.abs(aroundX?x:z);
      boolean posX = false;
      boolean posZ = false;
      pos %= this.separation + this.width;

      for(int i = 1; i < 1 + this.height; ++i) {
         if(pos < this.width) {
            world.setBlock(x, y + i, z, this.window);
         }
      }

   }

   public void open(World world, int x, int y, int z, boolean aroundX) {
      int pos = Math.abs(aroundX?x:z);
      boolean posX = false;
      boolean posZ = false;
      pos %= this.separation + this.width;
      if(pos < this.width) {
         world.setBlockToAir(x, y + 1, z);
         world.setBlock(x, y + 2, z, this.window);
      }

   }

   public void framed(World world, int x, int y, int z, boolean aroundX) {
      int pos = Math.abs(aroundX?x:z);
      boolean posX = false;
      boolean posZ = false;
      pos %= this.separation + this.width;

      for(int i = 1; i < 1 + this.height; ++i) {
         if(pos != this.width && pos != 0) {
            if(pos < this.width) {
               world.setBlock(x, y + i, z, this.window);
            }
         } else {
            this.properties.setWallBlock(world, x, y + i, z);
         }
      }

   }
}
