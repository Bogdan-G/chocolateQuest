package com.chocolate.chocolateQuest.builder;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.API.BuilderBase;
import com.chocolate.chocolateQuest.API.BuilderBlockData;
import com.chocolate.chocolateQuest.API.HelperReadConfig;
import com.chocolate.chocolateQuest.builder.Perlin3D;
import com.chocolate.chocolateQuest.builder.schematic.Schematic;
import com.chocolate.chocolateQuest.command.CommandSpawnBoss;
import com.chocolate.chocolateQuest.entity.EntitySchematicBuilder;
import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import java.util.Properties;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BuilderEmptyCave extends BuilderBase {

   BuilderBlockData caveBlock;
   int size;
   int height;
   int posY;
   int boss;
   String spawnBoss;
   boolean inverted;
   int borderWidth;


   public BuilderEmptyCave() {
      this.caveBlock = new BuilderBlockData(Blocks.air);
      this.size = 48;
      this.height = 28;
      this.boss = 0;
      this.spawnBoss = "no";
      this.inverted = false;
      this.borderWidth = 0;
   }

   public BuilderBase load(Properties prop) {
      this.caveBlock = HelperReadConfig.getBlock(prop, "caveBlock", this.caveBlock);
      this.size = HelperReadConfig.getIntegerProperty(prop, "caveSize", 32);
      this.height = HelperReadConfig.getIntegerProperty(prop, "caveHeight", 28);
      this.posY = HelperReadConfig.getIntegerProperty(prop, "caveY", 20);
      this.inverted = HelperReadConfig.getBooleanProperty(prop, "inverted", this.inverted);
      this.spawnBoss = HelperReadConfig.getStringProperty(prop, "spawnBoss", this.spawnBoss);
      return this;
   }

   public void generate(Random random, World world, int i, int j, int mob) {
      this.generate(random, world, i, this.posY, j, mob);
   }

   public String getName() {
      return "emptyCave";
   }

   public void generate(Random random, World world, int i, int j, int k, int idMob) {
      this.generate(random, world, i, j, k, this.size, this.size, this.height);
   }

   public void generate(Random random, World world, int i, int j, int k, int sizeX, int sizeZ, int height) {
      if(!ChocolateQuest.config.useInstantDungeonBuilder && this.caveBlock.id != ChocolateQuest.emptyBlock) {
         EntitySchematicBuilder builder = new EntitySchematicBuilder(world);
         builder.setPosition((double)i, (double)j, (double)k);
         builder.addBuildingPlans(this.getSchematic(random, world, i, j, k, this.size, this.size, height), i, j, k);
         world.spawnEntityInWorld(builder);
      } else {
         this.generateCave(random, world, i, j, k, this.size, this.size, height);
      }

   }

   public void generateCave(Random random, World world, int i, int j, int k, int sizeX, int sizeZ, int height) {
      i -= this.borderWidth;
      k -= this.borderWidth;
      sizeX += this.borderWidth * 2;
      height += this.borderWidth * 2;
      sizeZ += this.borderWidth * 2;
      Perlin3D p = new Perlin3D(world.getSeed(), 4, random);
      Perlin3D p2 = new Perlin3D(world.getSeed(), 32, random);
      int centerX = sizeX / 2;
      int centerY = height / 2;
      int centerZ = sizeZ / 2;
      int centerDistsq = centerX * centerX + centerY * centerY + centerZ * centerZ;
      float scaleX = 1.0F;
      float scaleY = 1.0F;
      float scaleZ = 1.0F;
      float maxSize = (float)Math.max(sizeX, Math.max(height, sizeZ));
      scaleX = 1.0F + (maxSize - (float)sizeX) / maxSize;
      scaleY = 1.0F + (maxSize - (float)height) / maxSize;
      scaleZ = 1.0F + (maxSize - (float)sizeZ) / maxSize;

      for(int boss = 0; boss < sizeX; ++boss) {
         for(int y = 0; y < height; ++y) {
            for(int z = 0; z < sizeZ; ++z) {
               float noiseVar = (float)((boss - centerX) * (boss - centerX)) * scaleX * scaleX + (float)((y - centerY) * (y - centerY)) * scaleY * scaleY + (float)((z - centerZ) * (z - centerZ)) * scaleZ * scaleZ;
               noiseVar /= (float)centerDistsq;
               double noiseValue = (p.getNoiseAt(boss + i, y + j, z + k) + p2.getNoiseAt(boss + i, y + j, z + k)) / 2.0D * (double)noiseVar * 2.5D;
               if(this.inverted) {
                  if(noiseValue > 0.5D) {
                     this.caveBlock.placeBlock(world, i + boss, j + y, k + z, random);
                  }
               } else if(noiseValue < 0.5D) {
                  this.caveBlock.placeBlock(world, i + boss, j + y, k + z, random);
               }
            }
         }
      }

      EntityBaseBoss var25 = CommandSpawnBoss.getBossByName(this.spawnBoss, world);
      if(var25 != null) {
         var25.setMonsterScale(3.0F + random.nextFloat() * 4.0F);
         var25.setPosition((double)(i + sizeX / 2), (double)(j + height / 2), (double)(k + sizeZ / 2));
         world.spawnEntityInWorld(var25);
      }

   }

   public Schematic getSchematic(Random random, World world, int i, int j, int k, int sizeX, int sizeZ, int height) {
      i -= this.borderWidth;
      if(j > this.borderWidth + 2) {
         j -= this.borderWidth;
      } else {
         j = 3;
      }

      k -= this.borderWidth;
      sizeX += this.borderWidth * 2;
      height += this.borderWidth * 2;
      sizeZ += this.borderWidth * 2;
      Schematic schematic = new Schematic(sizeX, height, sizeZ, ChocolateQuest.emptyBlock);
      Perlin3D p = new Perlin3D(world.getSeed(), 4, random);
      Perlin3D p2 = new Perlin3D(world.getSeed(), 32, random);
      int centerX = sizeX / 2;
      int centerY = height / 2;
      int centerZ = sizeZ / 2;
      int centerDistsq = centerX * centerX + centerY * centerY + centerZ * centerZ;
      float scaleX = 1.0F;
      float scaleY = 1.0F;
      float scaleZ = 1.0F;
      float maxSize = (float)Math.max(sizeX, Math.max(height, sizeZ));
      scaleX = 1.0F + (maxSize - (float)sizeX) / maxSize;
      scaleY = 1.0F + (maxSize - (float)height) / maxSize;

      for(int boss = 0; boss < sizeX; ++boss) {
         for(int y = 0; y < height; ++y) {
            for(int z = 0; z < sizeZ; ++z) {
               float noiseVar = (float)((boss - centerX) * (boss - centerX)) * scaleX * scaleX + (float)((y - centerY) * (y - centerY)) * scaleY * scaleY + (float)((z - centerZ) * (z - centerZ)) * scaleZ * scaleZ;
               noiseVar /= (float)centerDistsq;
               double noiseValue = (p.getNoiseAt(boss + i, y + j, z + k) + p2.getNoiseAt(boss + i, y + j, z + k)) / 2.0D * (double)noiseVar * 2.5D;
               if(this.inverted) {
                  if(noiseValue > 0.5D) {
                     this.caveBlock.placeBlock(schematic, boss, y, z, random);
                  }
               } else if(noiseValue < 0.5D) {
                  this.caveBlock.placeBlock(schematic, boss, y, z, random);
               }
            }
         }
      }

      EntityBaseBoss var26 = CommandSpawnBoss.getBossByName(this.spawnBoss, world);
      if(var26 != null) {
         var26.setMonsterScale(3.0F + random.nextFloat() * 4.0F);
         var26.setPosition((double)(sizeX / 2), (double)(height / 2), (double)(sizeZ / 2));
         schematic.addEntity(var26);
      }

      return schematic;
   }
}
