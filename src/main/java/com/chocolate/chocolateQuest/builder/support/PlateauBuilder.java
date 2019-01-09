package com.chocolate.chocolateQuest.builder.support;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.API.BuilderBlockData;
import com.chocolate.chocolateQuest.API.HelperReadConfig;
import com.chocolate.chocolateQuest.builder.Perlin3D;
import com.chocolate.chocolateQuest.builder.schematic.Schematic;
import java.util.Properties;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class PlateauBuilder {

   BuilderBlockData structureBlock;
   BuilderBlockData structureTopBlock;
   public int wallSize;


   public PlateauBuilder() {
      this.structureBlock = new BuilderBlockData(Blocks.dirt);
      this.structureTopBlock = new BuilderBlockData(Blocks.grass);
      this.wallSize = 8;
   }

   public void load(Properties prop) {
      this.structureBlock = HelperReadConfig.getBlock(prop, "structureBlock", new BuilderBlockData(Blocks.dirt));
      this.structureTopBlock = HelperReadConfig.getBlock(prop, "structureTopBlock", new BuilderBlockData(Blocks.grass));
   }

   public void generate(Random random, World world, int i, int j, int k, int sizeX, int sizeZ) {
      Perlin3D p = new Perlin3D(world.getSeed(), 8, random);
      Perlin3D p2 = new Perlin3D(world.getSeed(), 32, random);
      byte wallSize = 8;
      sizeX += wallSize * 2;
      sizeZ += wallSize * 2;
      boolean height = true;
      i -= wallSize;
      k -= wallSize;

      for(int x = 0; x < sizeX; ++x) {
         for(int z = 0; z < sizeZ; ++z) {
            int maxHeight = j - 1 - world.getTopSolidOrLiquidBlock(x + i, z + k);
            int posY = world.getTopSolidOrLiquidBlock(x + i, z + k);

            for(int y = 0; y <= maxHeight; ++y) {
               if(x > wallSize && z > wallSize && x < sizeX - wallSize && z < sizeZ - wallSize) {
                  this.structureBlock.placeBlock(world, i + x, posY + y, k + z, random);
               } else {
                  float noiseVar = (float)(y - maxHeight) / ((float)Math.max(1, maxHeight) * 1.5F);
                  noiseVar += Math.max(0.0F, (float)(wallSize - x) / 8.0F);
                  noiseVar += Math.max(0.0F, (float)(wallSize - (sizeX - x)) / 8.0F);
                  noiseVar += Math.max(0.0F, (float)(wallSize - z) / 8.0F);
                  noiseVar += Math.max(0.0F, (float)(wallSize - (sizeZ - z)) / 8.0F);
                  double value = (p.getNoiseAt(x + i, y, z + k) + p2.getNoiseAt(x + i, y, z + k) + (double)noiseVar) / 3.0D;
                  if(value < 0.5D) {
                     this.structureBlock.placeBlock(world, i + x, posY + y, k + z, random);
                  }
               }
            }

            maxHeight = world.getTopSolidOrLiquidBlock(x + i, z + k);
            if(maxHeight <= j) {
               this.structureTopBlock.placeBlock(world, i + x, maxHeight - 1, k + z, random);
            }
         }
      }

   }

   public Schematic getSchematic(Random random, World world, int i, int j, int k, int sizeX, int sizeZ) {
      byte wallSize = 8;
      sizeX += wallSize * 2;
      sizeZ += wallSize * 2;
      i -= wallSize;
      k -= wallSize;
      int schematicHeight = 0;

      for(int schematic = 0; schematic < sizeX; ++schematic) {
         for(int p = 0; p < sizeZ; ++p) {
            int p2 = j - world.getTopSolidOrLiquidBlock(schematic + i, p + k);
            if(p2 > schematicHeight) {
               schematicHeight = p2;
            }
         }
      }

      schematicHeight = Math.max(1, schematicHeight);
      Schematic var22 = new Schematic(sizeX, schematicHeight, sizeZ, ChocolateQuest.emptyBlock);
      Perlin3D var24 = new Perlin3D(world.getSeed(), 8, random);
      Perlin3D var23 = new Perlin3D(world.getSeed(), 32, random);

      for(int x = 0; x < sizeX; ++x) {
         for(int z = 0; z < sizeZ; ++z) {
            int maxHeight = j - world.getTopSolidOrLiquidBlock(x + i, z + k);
            int maxY = 0;

            for(int y = schematicHeight - maxHeight; y < schematicHeight; ++y) {
               if(x > wallSize && z > wallSize && x < sizeX - wallSize && z < sizeZ - wallSize) {
                  this.structureBlock.placeBlock(var22, x, y, z, random);
                  maxY = y;
               } else {
                  float noiseVar = (float)(y - maxHeight) / ((float)Math.max(1, maxHeight) * 1.5F);
                  noiseVar += Math.max(0.0F, (float)(wallSize - x) / (float)wallSize);
                  noiseVar += Math.max(0.0F, (float)(wallSize - (sizeX - x)) / (float)wallSize);
                  noiseVar += Math.max(0.0F, (float)(wallSize - z) / (float)wallSize);
                  noiseVar += Math.max(0.0F, (float)(wallSize - (sizeZ - z)) / (float)wallSize);
                  double value = (var24.getNoiseAt(x + i, y, z + k) + var23.getNoiseAt(x + i, y, z + k) + (double)noiseVar) / 3.0D + (double)((float)y / (float)schematicHeight) * 0.25D;
                  if(value < 0.5D) {
                     this.structureBlock.placeBlock(var22, x, y, z, random);
                     maxY = y;
                  }
               }
            }

            if(maxHeight <= j) {
               this.structureTopBlock.placeBlock(var22, x, maxY, z, random);
            }
         }
      }

      var22.setPosition(i, j - schematicHeight, k);
      return var22;
   }
}
