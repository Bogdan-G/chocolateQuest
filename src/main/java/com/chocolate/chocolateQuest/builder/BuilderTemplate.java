package com.chocolate.chocolateQuest.builder;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.API.BuilderBase;
import com.chocolate.chocolateQuest.API.HelperReadConfig;
import com.chocolate.chocolateQuest.builder.BuilderEmptyCave;
import com.chocolate.chocolateQuest.builder.BuilderHelper;
import com.chocolate.chocolateQuest.builder.schematic.Schematic;
import com.chocolate.chocolateQuest.builder.support.PlateauBuilder;
import com.chocolate.chocolateQuest.entity.EntitySchematicBuilder;
import java.util.Properties;
import java.util.Random;
import net.minecraft.world.World;

public class BuilderTemplate extends BuilderBase {

   String folderName;
   int posY;
   PlateauBuilder supportStructure;
   BuilderEmptyCave cave;
   boolean replaceBanners = true;


   public BuilderBase load(Properties prop) {
      this.folderName = prop.getProperty("folder").trim();
      if(this.folderName == null) {
         return null;
      } else {
         this.posY = Math.max(1, HelperReadConfig.getIntegerProperty(prop, "posY", 64));
         if(HelperReadConfig.getBooleanProperty(prop, "supportStructure", false)) {
            this.supportStructure = new PlateauBuilder();
            this.supportStructure.load(prop);
         }

         if(HelperReadConfig.getBooleanProperty(prop, "emptyCave", false)) {
            this.cave = new BuilderEmptyCave();
            this.cave.load(prop);
         }

         this.replaceBanners = HelperReadConfig.getBooleanProperty(prop, "replaceBanners", this.replaceBanners);
         return this;
      }
   }

   public String getName() {
      return "templateFloating";
   }

   public void generate(Random random, World world, int x, int z, int mob) {
      Schematic schematic = BuilderHelper.getRandomNBTMap(this.folderName, random, this.getDungeonName());
      short maxX = schematic.width;
      short maxY = schematic.length;
      this.generate(random, schematic, world, x - maxX / 2, this.posY, z - maxY / 2, mob);
   }

   public void generate(Random random, World world, int i, int j, int k, int idMob) {
      Schematic schematic = BuilderHelper.getRandomNBTMap(this.folderName, random, this.getDungeonName());
      this.generate(random, schematic, world, i, this.posY, k, idMob);
   }

   public void generate(Random random, Schematic schematic, World world, int i, int j, int k, int idMob) {
      if(ChocolateQuest.config.useInstantDungeonBuilder) {
         BuilderHelper builder = BuilderHelper.builderHelper;
         if(this.supportStructure != null) {
            this.supportStructure.generate(random, world, i, j, k, schematic.width, schematic.length);
         }

         if(this.cave != null) {
            this.cave.generate(random, world, i, j + this.cave.posY, k, schematic.width, schematic.length, this.cave.height <= 0?schematic.height:this.cave.height);
         }

         builder.putSchematicInWorld(random, world, schematic, i, j, k, idMob, this.replaceBanners);
      } else {
         EntitySchematicBuilder builder1 = new EntitySchematicBuilder(world);
         builder1.setMobID(idMob, this.replaceBanners);
         builder1.setPosition((double)i, (double)j, (double)k);
         Schematic s;
         if(this.supportStructure != null) {
            s = this.supportStructure.getSchematic(random, world, i, j, k, schematic.width, schematic.length);
            builder1.addBuildingPlans(s, i - this.supportStructure.wallSize, j, k - this.supportStructure.wallSize);
         }

         if(this.cave != null) {
            s = this.cave.getSchematic(random, world, i, j + this.cave.posY, k, schematic.width, schematic.length, this.cave.height <= 0?schematic.height:this.cave.height);
            builder1.addBuildingPlans(s, i - this.cave.borderWidth, j, k - this.cave.borderWidth);
         }

         builder1.addBuildingPlans(schematic, i, j, k);
         world.spawnEntityInWorld(builder1);
      }

   }
}
