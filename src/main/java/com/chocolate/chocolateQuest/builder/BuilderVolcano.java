package com.chocolate.chocolateQuest.builder;

import com.chocolate.chocolateQuest.API.BuilderBase;
import com.chocolate.chocolateQuest.API.BuilderBlockData;
import com.chocolate.chocolateQuest.API.HelperReadConfig;
import java.util.Properties;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BuilderVolcano extends BuilderBase {

   int numTiles;
   static int filer = 1;
   BuilderBlockData blockWalls;
   BuilderBlockData blockPath;
   int oreChance = 8;
   String folder = "dungeons";
   int dungeonRooms = 25;
   boolean generatePath = true;
   boolean generateDungeon = true;
   static int vacio = -1;
   int counter = 120;


   public BuilderBase load(Properties prop) {
      this.oreChance = HelperReadConfig.getIntegerProperty(prop, "oreChance", 16);
      this.blockWalls = HelperReadConfig.getBlock(prop, "blockWalls", new BuilderBlockData(Blocks.stone));
      this.blockPath = HelperReadConfig.getBlock(prop, "blockPath", new BuilderBlockData(Blocks.netherrack));
      this.dungeonRooms = HelperReadConfig.getIntegerProperty(prop, "dungeonRooms", 25);
      this.generatePath = HelperReadConfig.getBooleanProperty(prop, "generatePath", true);
      this.generateDungeon = HelperReadConfig.getBooleanProperty(prop, "generateDungeon", false);
      return this;
   }

   public String getName() {
      return "volcano";
   }

   public void generate(Random random, World world, int x, int z, int mob) {
      byte y = 5;
      this.generate(random, world, x, y, z, mob);
   }

   public void generate(Random random, World world, int i, int j, int k, int mob) {}

}
