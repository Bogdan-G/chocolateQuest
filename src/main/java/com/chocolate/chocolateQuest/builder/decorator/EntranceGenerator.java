package com.chocolate.chocolateQuest.builder.decorator;

import com.chocolate.chocolateQuest.builder.decorator.BuildingProperties;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EntranceGenerator {

   private BuildingProperties properties;


   public EntranceGenerator(BuildingProperties properties) {
      this.properties = properties;
   }

   public void generateEntance(World world, int xCenter, int yCenter, int zCenter, ForgeDirection direction) {
      int dirX = direction.offsetX;
      int dirZ = direction.offsetZ;
      byte width = 6;
      byte height = 5;
      byte length = 2;
      xCenter -= width / 2 * dirZ;
      zCenter -= width / 2 * dirX;

      for(int w = 0; w < width; ++w) {
         for(int l = -2; l < length; ++l) {
            for(int h = 0; h < height; ++h) {
               int x = xCenter + w * dirZ + l * dirX;
               int y = yCenter + h;
               int z = zCenter + w * dirX + l * dirZ;
               if(h == 0) {
                  this.properties.floor.generateFloor(world, x, y, z);
               } else if(l < 0) {
                  world.setBlockToAir(x, y, z);
               } else if(h == height - 1) {
                  this.properties.setWallBlock(world, x, y, z);
               } else if(w != 0 && w != width - 1) {
                  world.setBlockToAir(x, y, z);
               } else {
                  world.setBlock(x, y, z, Blocks.fence);
               }
            }
         }
      }

   }
}
