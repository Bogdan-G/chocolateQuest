package com.chocolate.chocolateQuest.builder;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.API.BuilderBase;
import com.chocolate.chocolateQuest.API.BuilderBlockData;
import com.chocolate.chocolateQuest.API.HelperReadConfig;
import com.chocolate.chocolateQuest.builder.BuilderHelper;
import com.chocolate.chocolateQuest.builder.Perlin3D;
import com.chocolate.chocolateQuest.builder.schematic.Schematic;
import com.chocolate.chocolateQuest.entity.EntitySchematicBuilder;
import java.util.Properties;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BuilderNether extends BuilderBase {

   String folder = "pigcity";
   int rows = 4;
   int posY = 31;
   BuilderBlockData floorBlock;
   BuilderBlockData lavaBlock;
   boolean replaceBanners;


   public BuilderNether() {
      this.lavaBlock = new BuilderBlockData(Blocks.lava);
      this.replaceBanners = true;
   }

   public BuilderBase load(Properties prop) {
      this.rows = HelperReadConfig.getIntegerProperty(prop, "rows", 4);
      if(this.rows < 1) {
         this.rows = 1;
      }

      if(this.rows > 20) {
         this.rows = 20;
      }

      this.posY = HelperReadConfig.getIntegerProperty(prop, "posY", 31);
      this.floorBlock = HelperReadConfig.getBlock(prop, "floorBlock", new BuilderBlockData(Blocks.nether_brick));
      this.lavaBlock = HelperReadConfig.getBlock(prop, "lavaBlock", this.lavaBlock);
      this.folder = prop.getProperty("folder");
      if(this.folder == null) {
         this.folder = "pigcity";
      }

      this.replaceBanners = HelperReadConfig.getBooleanProperty(prop, "replaceBanners", this.replaceBanners);
      return super.load(prop);
   }

   public String getName() {
      return "lavaCity";
   }

   public void generate(Random random, World world, int x, int z, int mob) {
      this.generate(random, world, x, this.posY, z, mob);
   }

   public void generate(Random random, World world, int i, int j, int k, int mob) {
      Perlin3D p = new Perlin3D(world.getSeed(), 8, random);
      Perlin3D p2 = new Perlin3D(world.getSeed(), 32, random);
      byte rowSize = 21;
      int size = this.rows * rowSize;
      byte height = 32;
      byte wallSize = 4;

      int builder;
      for(int b = 0; b < size; ++b) {
         for(int s = 0; s < height; ++s) {
            for(builder = 0; builder < size; ++builder) {
               float x = Math.max(0.0F, 2.0F - (float)(height - s) / 4.0F);
               x += Math.max(0.0F, (float)wallSize - (float)b / 2.0F);
               x += Math.max(0.0F, (float)wallSize - (float)(size - b) / 2.0F);
               x += Math.max(0.0F, (float)wallSize - (float)builder / 2.0F);
               x += Math.max(0.0F, (float)wallSize - (float)(size - builder) / 2.0F);
               if(p.getNoiseAt(b + i, s + j, builder + k) * p2.getNoiseAt(b + i, s + j, builder + k) * (double)x < 0.5D) {
                  world.setBlockToAir(i + b, j + s, k + builder);
               }
            }
         }
      }

      BuilderHelper var18 = BuilderHelper.builderHelper;
      Schematic var19 = new Schematic(size, 1, size);

      int var20;
      for(builder = 0; builder < var19.width; ++builder) {
         for(var20 = 0; var20 < var19.length; ++var20) {
            if((builder % 21 <= 2 || builder % 21 >= 18 || var20 % 21 <= 2 || var20 % 21 >= 18) && (builder % 21 <= 8 || builder % 21 >= 12) && (var20 % 21 <= 8 || var20 % 21 >= 12)) {
               this.lavaBlock.placeBlock(var19, builder, 0, var20, random);
            } else {
               this.floorBlock.placeBlock(var19, builder, 0, var20, random);
            }
         }
      }

      if(ChocolateQuest.config.useInstantDungeonBuilder) {
         var18.putSchematicInWorld(random, world, var19, i, j, k, mob, this.replaceBanners);

         for(builder = 0; builder < var19.width; builder += 21) {
            for(var20 = 0; var20 < var19.length; var20 += 21) {
               var18.putSchematicInWorld(random, world, BuilderHelper.getRandomNBTMap(this.folder, random, this.getDungeonName()), builder + 3 + i, j + 1, var20 + 3 + k, mob, this.replaceBanners);
            }
         }
      } else {
         EntitySchematicBuilder var21 = new EntitySchematicBuilder(world);
         var21.setMobID(mob, this.replaceBanners);
         var21.setPosition((double)i, (double)j, (double)k);
         var21.addBuildingPlans(var19, i, j, k);

         for(var20 = 0; var20 < var19.width; var20 += 21) {
            for(int z = 0; z < var19.length; z += 21) {
               var21.addBuildingPlans(BuilderHelper.getRandomNBTMap(this.folder, random, this.getDungeonName()), var20 + 3 + i, j + 1, z + 3 + k);
            }
         }

         world.spawnEntityInWorld(var21);
      }

   }
}
