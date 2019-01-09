package com.chocolate.chocolateQuest.API;

import java.util.Properties;
import java.util.Random;
import net.minecraft.world.World;

public abstract class BuilderBase {

   String dungeonName;


   public abstract void generate(Random var1, World var2, int var3, int var4, int var5);

   public abstract void generate(Random var1, World var2, int var3, int var4, int var5, int var6);

   public abstract String getName();

   public BuilderBase load(Properties prop) {
      return this;
   }

   public String getDungeonName() {
      return this.dungeonName;
   }

   public void setDungeonName(String name) {
      this.dungeonName = name;
   }
}
