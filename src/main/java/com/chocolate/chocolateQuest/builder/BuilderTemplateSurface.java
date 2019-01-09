package com.chocolate.chocolateQuest.builder;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.WorldGeneratorNew;
import com.chocolate.chocolateQuest.API.BuilderBase;
import com.chocolate.chocolateQuest.API.HelperReadConfig;
import com.chocolate.chocolateQuest.builder.BuilderHelper;
import com.chocolate.chocolateQuest.builder.schematic.Schematic;
import com.chocolate.chocolateQuest.builder.support.PlateauBuilder;
import com.chocolate.chocolateQuest.entity.EntitySchematicBuilder;
import java.util.Properties;
import java.util.Random;
import net.minecraft.world.World;

public class BuilderTemplateSurface extends BuilderBase {

   String folderName;
   int underGroundOffset;
   boolean replaceBanners = true;
   PlateauBuilder supportStructure;


   public BuilderBase load(Properties prop) {
      this.folderName = prop.getProperty("folder").trim();
      if(this.folderName == null) {
         return null;
      } else {
         this.underGroundOffset = HelperReadConfig.getIntegerProperty(prop, "underGroundOffset", 10);
         if(HelperReadConfig.getBooleanProperty(prop, "supportStructure", false)) {
            this.supportStructure = new PlateauBuilder();
            this.supportStructure.load(prop);
         }

         this.replaceBanners = HelperReadConfig.getBooleanProperty(prop, "replaceBanners", this.replaceBanners);
         return this;
      }
   }

   public String getName() {
      return "templateSurface";
   }

   public void generate(Random random, World world, int i, int k, int mob) {
      Schematic schematic = BuilderHelper.getRandomNBTMap(this.folderName, random, this.getDungeonName());
      short maxX = schematic.width;
      short maxY = schematic.length;
      i -= maxX / 2;
      k -= maxY / 2;
      boolean cont = false;
      int cant = 0;
      int media = 0;

      int height;
      for(height = 0; height < maxX; ++height) {
         for(int y = 0; y < maxY; ++y) {
            int h = world.getTopSolidOrLiquidBlock(i + height, y + k);
            media += h;
            ++cant;
         }
      }

      height = media / cant;
      this.generate(random, world, i, height, k, mob);
   }

   public void generate(Random random, World world, int i, int j, int k, int idMob) {
      Schematic schematic = BuilderHelper.getRandomNBTMap(this.folderName, random, this.getDungeonName());
      this.generate(random, schematic, world, i, j, k, idMob);
   }

   public void generate(Random random, Schematic schematic, World world, int i, int j, int k, int idMob) {
      j -= this.underGroundOffset;
      j = Math.max(1, j);
      if(ChocolateQuest.config.useInstantDungeonBuilder) {
         WorldGeneratorNew.createChunks(world, i, k, schematic.width, schematic.length);
         if(this.supportStructure != null) {
            this.supportStructure.generate(random, world, i, j + this.underGroundOffset, k, schematic.width, schematic.length);
         }

         BuilderHelper builder = BuilderHelper.builderHelper;
         builder.putSchematicInWorld(random, world, schematic, i, j, k, idMob, this.replaceBanners);
      } else {
         EntitySchematicBuilder builder1 = new EntitySchematicBuilder(world);
         builder1.setMobID(idMob, this.replaceBanners);
         builder1.setPosition((double)i, (double)(j + 10), (double)k);
         if(this.supportStructure != null) {
            Schematic s = this.supportStructure.getSchematic(random, world, i, j + this.underGroundOffset, k, schematic.width, schematic.length);
            builder1.addBuildingPlans(s, s.posX, s.posY, s.posZ);
         }

         builder1.addBuildingPlans(schematic, i, j, k);
         world.spawnEntityInWorld(builder1);
      }

   }
}
