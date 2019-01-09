package com.chocolate.chocolateQuest.builder.decorator;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class DecoratorDoor {

   public static final int WEST = 0;
   public static final int SOUTH = 1;
   public static final int EAST = 2;
   public static final int NORTH = 3;
   public int width = 2;
   public int height = 3;
   int type = 0;
   final Block BLOCK_FRAME;


   public DecoratorDoor(int width, int height) {
      this.BLOCK_FRAME = Blocks.brick_block;
      this.width = width;
      this.height = height;
   }

   public void setRandomType(Random random) {
      this.type = random.nextInt(4);
   }

   public void generate(Random random, World world, int x, int y, int z, ForgeDirection side) {
      if(this.width == 1) {
         this.generateSquared(random, world, x, y, z, side);
      } else {
         switch(this.type) {
         case 0:
            this.generateSquared(random, world, x, y, z, side);
            break;
         case 1:
            this.generateArch(random, world, x, y, z, side);
            break;
         case 2:
            this.generateTriangle(random, world, x, y, z, side);
            break;
         case 3:
            this.generateFramed(random, world, x, y, z, side);
            break;
         default:
            this.generateSquared(random, world, x, y, z, side);
         }

      }
   }

   public void generateArch(Random random, World world, int x, int y, int z, ForgeDirection side) {
      int width = this.width / 2;
      int uwidth = width - 1 + this.width % 2;
      int archHeight = Math.max(1, this.height - width);

      for(int k = -width; k <= uwidth; ++k) {
         for(int e = -1; e <= 1; ++e) {
            if(k < 0 && k % 2 != 0) {
               ++archHeight;
            }

            for(int j = 0; j <= this.height; ++j) {
               boolean xPos = false;
               boolean zPos = false;
               int var16;
               int var15;
               if(side != ForgeDirection.WEST && side != ForgeDirection.EAST) {
                  var15 = x + k;
                  var16 = z + e;
               } else {
                  var15 = x + e;
                  var16 = z + k;
               }

               if(j <= archHeight) {
                  this.setBlockToAir(world, var15, y + j, var16);
               }
            }

            if(k > 0 && k % 2 != 0) {
               --archHeight;
            }
         }
      }

   }

   public void generateTriangle(Random random, World world, int x, int y, int z, ForgeDirection side) {
      int width = this.width / 2;
      int uwidth = width + this.width % 2;
      int archHeight = Math.max(1, this.height - width);

      for(int e = -1; e <= 1; ++e) {
         for(int k = -width; k <= uwidth; ++k) {
            for(int j = 0; j <= this.height; ++j) {
               boolean xPos = false;
               boolean zPos = false;
               int var16;
               int var15;
               if(side != ForgeDirection.WEST && side != ForgeDirection.EAST) {
                  var15 = x + k;
                  var16 = z + e;
               } else {
                  var15 = x + e;
                  var16 = z + k;
               }

               if(j <= archHeight) {
                  this.setBlockToAir(world, var15, y + j, var16);
               }
            }

            if(k < 0) {
               ++archHeight;
            } else {
               --archHeight;
            }
         }
      }

   }

   public void generateSquared(Random random, World world, int x, int y, int z, ForgeDirection side) {
      int width = this.width / 2;
      int uwidth = width - 1 + this.width % 2;

      int orientation;
      for(int doorSide = -1; doorSide <= 1; ++doorSide) {
         for(orientation = -width; orientation <= uwidth; ++orientation) {
            for(int open = 0; open <= this.height; ++open) {
               boolean xPos = false;
               boolean zPos = false;
               int var17;
               int var15;
               if(side != ForgeDirection.WEST && side != ForgeDirection.EAST) {
                  var15 = x + orientation;
                  var17 = z + doorSide;
               } else {
                  var15 = x + doorSide;
                  var17 = z + orientation;
               }

               this.setBlockToAir(world, var15, y + open, var17);
            }
         }
      }

      if(this.width == 1) {
         byte var14 = 0;
         if(side == ForgeDirection.SOUTH) {
            var14 = 1;
         } else if(side == ForgeDirection.EAST) {
            var14 = 2;
         } else if(side == ForgeDirection.NORTH) {
            var14 = 3;
         }

         orientation = var14 - 1;
         boolean var16 = false;
         if(var16) {
            world.setBlock(x, y, z, Blocks.wooden_door, orientation, 2);
            world.setBlock(x, y + 1, z, Blocks.wooden_door, orientation ^ 8, 2);
         } else {
            world.setBlock(x, y, z, Blocks.wooden_door, orientation ^ 4, 2);
            world.setBlock(x, y + 1, z, Blocks.wooden_door, orientation ^ 4 ^ 8, 2);
         }
      }

   }

   public void generateFramed(Random random, World world, int x, int y, int z, ForgeDirection side) {
      int width = this.width / 2;
      int uwidth = width + this.width % 2;

      for(int e = -1; e <= 1; ++e) {
         for(int k = -width - 1; k <= uwidth + 1; ++k) {
            for(int j = 0; j <= this.height; ++j) {
               boolean xPos = false;
               boolean zPos = false;
               int var14;
               int var15;
               if(side != ForgeDirection.WEST && side != ForgeDirection.EAST) {
                  var14 = x + k;
                  var15 = z + e;
               } else {
                  var14 = x + e;
                  var15 = z + k;
               }

               if(j != this.height && k >= -width && k <= width) {
                  this.setBlockToAir(world, var14, y + j, var15);
               } else if(e == 0) {
                  world.setBlock(var14, y + j, var15, this.BLOCK_FRAME);
               }
            }
         }
      }

   }

   private void setBlockToAir(World world, int x, int y, int z) {
      world.setBlock(x, y, z, Blocks.air);
   }
}
