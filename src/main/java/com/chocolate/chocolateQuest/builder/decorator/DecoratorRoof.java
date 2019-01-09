package com.chocolate.chocolateQuest.builder.decorator;

import com.chocolate.chocolateQuest.API.BuilderBlockData;
import com.chocolate.chocolateQuest.builder.decorator.BuildingProperties;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DecoratorRoof {

   public final int PYRAMID = 0;
   public final int FENCE_IRON_FLOOR = 2;
   public final int TRIANGLE = 5;
   public int floorType = 0;
   public BuilderBlockData fence;
   public Block stairs;
   int texasType;
   int walkableType;
   Random random;
   BuildingProperties properties;


   public DecoratorRoof(Random random, BuildingProperties properties) {
      this.fence = new BuilderBlockData(Blocks.fence);
      this.stairs = Blocks.birch_stairs;
      this.texasType = 0;
      this.walkableType = 0;
      this.random = random;
      this.floorType = random.nextInt(6);
      this.texasType = random.nextInt(2);
      this.walkableType = random.nextInt(4);
      this.properties = properties;
      int i = random.nextInt(9);
      switch(i) {
      case 0:
         this.stairs = Blocks.birch_stairs;
         break;
      case 1:
         this.stairs = Blocks.jungle_stairs;
         break;
      case 2:
         this.stairs = Blocks.oak_stairs;
         break;
      case 3:
         this.stairs = Blocks.birch_stairs;
         break;
      case 4:
         this.stairs = Blocks.spruce_stairs;
         break;
      case 5:
         this.stairs = Blocks.stone_stairs;
         break;
      case 6:
         this.stairs = Blocks.brick_stairs;
         break;
      case 7:
         this.stairs = Blocks.sandstone_stairs;
         break;
      case 8:
         this.stairs = Blocks.stone_brick_stairs;
      }

      if(random.nextInt(100) == 0) {
         this.stairs = Blocks.quartz_stairs;
      }

      if(this.floorType == 2) {
         this.fence = new BuilderBlockData(Blocks.iron_bars);
      }

   }

   public void generateRoof(World world, int x, int y, int z, int sizeX, int sizeZ, boolean walkable) {
      if(!walkable && this.random.nextBoolean()) {
         if(this.texasType == 0) {
            this.roofPyramid(world, x, y, z, sizeX, sizeZ, 1000);
         } else {
            this.roofTriangle(world, x, y, z, sizeX, sizeZ);
         }

      } else {
         switch(this.floorType) {
         case 1:
            this.fence(world, x, y, z, sizeX, sizeZ, Blocks.fence);
            break;
         case 2:
            this.fence(world, x, y, z, sizeX, sizeZ, Blocks.iron_bars);
            break;
         case 3:
            this.roofDoubleSlabsDecorator(world, x, y, z, sizeX, sizeZ, 2);
            break;
         case 4:
            this.roofDoubleSlabsDecorator(world, x, y, z, sizeX, sizeZ, 1);
            break;
         case 5:
            if(!walkable) {
               this.roofTriangle(world, x, y, z, sizeX, sizeZ);
            } else {
               this.addClearRoof(world, x, y, z, sizeX, sizeZ);
            }
            break;
         default:
            if(!walkable) {
               this.roofPyramid(world, x, y, z, sizeX, sizeZ, 1000);
            } else {
               this.addClearRoof(world, x, y, z, sizeX, sizeZ);
            }
         }

      }
   }

   public void setRoofBlock(World world, int x, int y, int z) {
      if(this.floorType != 1 && this.floorType != 2) {
         int ax = Math.abs(x);
         int az = Math.abs(z);
         if(ax % 2 == az % 2) {
            world.setBlock(x, y, z, this.properties.wallBlock.id);
         }
      } else {
         world.setBlock(x, y, z, this.fence.id);
      }

   }

   private void addClearRoof(World world, int x, int y, int z, int sizeX, int sizeZ) {
      switch(this.walkableType) {
      case 1:
         this.fence(world, x, y, z, sizeX, sizeZ, Blocks.fence);
         break;
      case 2:
         this.fence(world, x, y, z, sizeX, sizeZ, Blocks.iron_bars);
         break;
      case 3:
         this.roofDoubleSlabsDecorator(world, x, y, z, sizeX, sizeZ, 2);
         break;
      default:
         this.roofDoubleSlabsDecorator(world, x, y, z, sizeX, sizeZ, 1);
      }

   }

   private void fence(World world, int x, int y, int z, int sizeX, int sizeZ, Block block) {
      int k;
      for(k = 0; k <= sizeX; ++k) {
         world.setBlock(x + k, y, z, block);
         world.setBlock(x + k, y, z + sizeZ, block);
      }

      for(k = 0; k <= sizeZ; ++k) {
         world.setBlock(x, y, z + k, block);
         world.setBlock(x + sizeX, y, z + k, block);
      }

   }

   public void roofPyramid(World world, int x, int y, int z, int sizeX, int sizeZ, int height) {
      int minSize = Math.min(sizeX, sizeZ) / 2 + 1;
      minSize = Math.min(height, minSize);
      minSize -= 1 - minSize % 2;

      for(int j = 0; j <= minSize; ++j) {
         world.setBlock(x + j - 1, y + j, z + j - 1, this.stairs, 2, 3);
         world.setBlock(x + sizeX - j + 1, y + j, z + j - 1, this.stairs, 2, 3);
         world.setBlock(x + j - 1, y + j, z + sizeZ - j + 1, this.stairs, 3, 3);
         world.setBlock(x + sizeX - j + 1, y + j, z + sizeZ - j + 1, this.stairs, 3, 3);

         int k;
         for(k = j; k <= sizeX - j; ++k) {
            this.properties.setWallBlock(world, x + k, y + j, z + j);
            world.setBlock(x + k, y + j, z + j - 1, this.stairs, 2, 3);
            this.properties.setWallBlock(world, x + k, y + j, z + sizeZ - j);
            world.setBlock(x + k, y + j, z + sizeZ - j + 1, this.stairs, 3, 3);
         }

         for(k = j; k <= sizeZ - j; ++k) {
            this.properties.setWallBlock(world, x + j, y + j, z + k);
            world.setBlock(x + j - 1, y + j, z + k, this.stairs);
            this.properties.setWallBlock(world, x + sizeX - j, y + j, z + k);
            world.setBlock(x + sizeX - j + 1, y + j, z + k, this.stairs, 1, 3);
         }
      }

   }

   private void roofTriangle(World world, int x, int y, int z, int sizeX, int sizeZ) {
      int minSize;
      int j;
      int k;
      if(sizeX < sizeZ) {
         minSize = sizeX / 2;

         for(j = 0; j <= minSize; ++j) {
            for(k = 0; k <= sizeZ; ++k) {
               world.setBlock(x + j, y + j, z + k, this.stairs, 0, 3);
               world.setBlock(x + sizeX - j, y + j, z + k, this.stairs, 1, 3);
            }

            for(k = j + 1; k < sizeX - j; ++k) {
               this.properties.setWallBlock(world, x + k, y + j, z);
               this.properties.setWallBlock(world, x + k, y + j, z + sizeZ);
            }
         }
      } else {
         minSize = sizeZ / 2;

         for(j = 0; j <= minSize; ++j) {
            for(k = 0; k <= sizeX; ++k) {
               world.setBlock(x + k, y + j, z + j, this.stairs, 2, 3);
               world.setBlock(x + k, y + j, z + sizeZ - j, this.stairs, 3, 3);
            }

            for(k = j + 1; k < sizeZ - j; ++k) {
               this.properties.setWallBlock(world, x, y + j, z + k);
               this.properties.setWallBlock(world, x + sizeX, y + j, z + k);
            }
         }
      }

   }

   private void roofDoubleSlabsDecorator(World world, int x, int y, int z, int sizeX, int sizeZ, int width) {
      int k;
      int w;
      for(k = 0; k < sizeX; k += width * 2) {
         for(w = 0; w < width; ++w) {
            this.properties.setWallBlock(world, x + k + w, y, z);
            this.properties.setWallBlock(world, x + k + w, y, z + sizeZ);
         }
      }

      for(k = 0; k <= sizeZ; k += width * 2) {
         for(w = 0; w < width; ++w) {
            this.properties.setWallBlock(world, x, y, z + k + w);
            this.properties.setWallBlock(world, x + sizeX, y, z + k + w);
         }
      }

   }
}
