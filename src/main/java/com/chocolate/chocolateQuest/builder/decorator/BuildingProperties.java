package com.chocolate.chocolateQuest.builder.decorator;

import com.chocolate.chocolateQuest.API.BuilderBlockData;
import com.chocolate.chocolateQuest.API.HelperReadConfig;
import com.chocolate.chocolateQuest.builder.decorator.DecoratorDoor;
import com.chocolate.chocolateQuest.builder.decorator.DecoratorFloor;
import com.chocolate.chocolateQuest.builder.decorator.DecoratorRoof;
import com.chocolate.chocolateQuest.builder.decorator.DecoratorWindow;
import java.util.Properties;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BuildingProperties {

   public int doorWidth;
   public int doorHeight;
   public int floorHeight;
   public int mobID;
   public int mobRatio;
   public BuilderBlockData wallBlock;
   public DecoratorDoor doors;
   public DecoratorFloor floor;
   public DecoratorWindow window;
   public DecoratorRoof roof;


   public void initialize(Random random) {
      this.doors = new DecoratorDoor(this.doorWidth, this.doorHeight);
      this.floor = new DecoratorFloor(random);
      this.window = new DecoratorWindow(random, this);
      this.roof = new DecoratorRoof(random, this);
   }

   public void load(Properties prop) {
      this.doorWidth = Math.max(2, HelperReadConfig.getIntegerProperty(prop, "doorWidth", 2));
      this.doorHeight = Math.max(2, HelperReadConfig.getIntegerProperty(prop, "doorheight", 3));
      this.floorHeight = Math.max(3, HelperReadConfig.getIntegerProperty(prop, "floorHeight", 6));
      this.wallBlock = HelperReadConfig.getBlock(prop, "wallBlock", new BuilderBlockData(Blocks.stonebrick));
      this.mobRatio = HelperReadConfig.getIntegerProperty(prop, "mobRatio", 1);
   }

   public void setWallBlock(World world, int x, int y, int z) {
      world.setBlock(x, y, z, this.wallBlock.id, this.wallBlock.metadata, 3);
   }
}
