package com.chocolate.chocolateQuest.API;

import com.chocolate.chocolateQuest.API.BuilderBase;
import com.chocolate.chocolateQuest.API.HelperReadConfig;
import com.chocolate.chocolateQuest.API.RegisterDungeonBuilder;
import com.chocolate.chocolateQuest.entity.mob.registry.RegisterDungeonMobs;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.StringTokenizer;

public class DungeonBase {

   String[] biomeList;
   int chance = 10;
   int mobID = 1;
   int[] dimensionID = new int[]{0};
   String name;
   int icon = 1;
   BuilderBase builder;
   boolean unique = false;
   String path;


   public int getIcon() {
      return this.icon;
   }

   public String getName() {
      return this.name;
   }

   public BuilderBase getBuilder() {
      return this.builder;
   }

   public String[] getBiomes() {
      return this.biomeList;
   }

   public DungeonBase setBiomes(String[] biomes) {
      this.biomeList = biomes;
      return this;
   }

   public int getChance() {
      return this.chance;
   }

   public DungeonBase setChance(int chance) {
      this.chance = chance;
      return this;
   }

   public int getMobID() {
      return this.mobID;
   }

   public boolean isUnique() {
      return this.unique;
   }

   public int[] getDimension() {
      return this.dimensionID;
   }

   public DungeonBase setDimension(int[] dim) {
      this.dimensionID = dim;
      return this;
   }

   public DungeonBase readData(File file) {
      String[] ret = null;
      Properties prop = new Properties();

      try {
         FileReader e = new FileReader(file);
         prop.load(e);
         String dungeonType = prop.getProperty("builder");
         if(dungeonType == null) {
            return null;
         } else {
            this.path = file.getPath();
            dungeonType.trim();
            this.builder = RegisterDungeonBuilder.getBuilderByName(dungeonType);
            if(this.builder == null) {
               BDHelper.println("Wrong builder: " + this.builder);
               return null;
            } else {
               this.name = file.getName();
               if(!file.getParentFile().getName().contains("DungeonConfig")) {
                  this.name = file.getParentFile().getName() + "-" + file.getName();
               }

               this.builder.setDungeonName(file.getName());
               String s = prop.getProperty("biomes");
               StringTokenizer stkn = new StringTokenizer(s, ",");
               ret = new String[stkn.countTokens()];
               int tknCount = stkn.countTokens();

               for(int i = 0; i < tknCount; ++i) {
                  ret[i] = stkn.nextToken().trim();
               }

               this.setBiomes(ret);
               this.setChance(HelperReadConfig.getIntegerProperty(prop, "chance", 10));
               this.icon = HelperReadConfig.getIntegerProperty(prop, "icon", 10);
               this.setDimension(HelperReadConfig.getIntegerArray(prop, "dimensionID", 0));
               this.mobID = RegisterDungeonMobs.getMonster(prop.getProperty("mob").trim());
               this.unique = HelperReadConfig.getBooleanProperty(prop, "unique", this.unique);
               if(!this.readSpecialData(prop)) {
                  return null;
               } else {
                  e.close();
                  return this;
               }
            }
         }
      } catch (IOException var10) {
         BDHelper.println("Error reading dungeon config file at betterDungeons mod");
         var10.printStackTrace();
         return null;
      }
   }

   public boolean readSpecialData(Properties prop) {
      return this.builder.load(prop) != null;
   }

   public String getPath() {
      return this.path;
   }
}
