package com.chocolate.chocolateQuest;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.API.BuilderBase;
import com.chocolate.chocolateQuest.API.DungeonBase;
import com.chocolate.chocolateQuest.API.DungeonRegister;
import com.chocolate.chocolateQuest.builder.BuilderHelper;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import com.chocolate.chocolateQuest.entity.mob.registry.RegisterDungeonMobs;
import com.chocolate.chocolateQuest.quest.worldManager.TerrainManager;
import com.chocolate.chocolateQuest.utils.BDHelper;
import cpw.mods.fml.common.IWorldGenerator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class WorldGeneratorNew implements IWorldGenerator {

   public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
      if(!world.isRemote) {
         Random rnd = new Random();
         rnd.setSeed(getSeed(world, chunkX, chunkZ));
         int dungeonSeparation = TerrainManager.getTerritorySeparation();
         if(chunkX % dungeonSeparation == 0 && chunkZ % dungeonSeparation == 0) {
            this.generateSurface(world, rnd, chunkX * 16 + 1, chunkZ * 16 + 1);
         }

      }
   }

   public static long getSeed(World world, int chunkX, int chunkZ) {
      long mix = xorShift64((long)chunkX) + Long.rotateLeft(xorShift64((long)chunkZ), 32) + -1094792450L;
      long result = xorShift64(mix);
      return world.getSeed() + result;
   }

   public static long xorShift64(long x) {
      x ^= x << 21;
      x ^= x >>> 35;
      x ^= x << 4;
      return x;
   }

   public static void createChunks(World world, int posX, int posZ, int sizeX, int sizeZ) {}

   public void generateSurface(World world, Random random, int i, int k) {
      if(world.getWorldInfo().getTerrainType().getWorldTypeName() != "flat" || ChocolateQuest.config.dungeonsInFlat) {
         this.generateBigDungeon(world, random, i, k, true);
      }
   }

   public void generateBigDungeon(World world, Random random, int i, int k, boolean addDungeon) {
      BuilderBase builder = null;
      BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
      DungeonBase dungeon = null;
      Iterator idMob = DungeonRegister.dungeonList.iterator();

      while(idMob.hasNext()) {
         DungeonBase d = (DungeonBase)idMob.next();
         if((!d.isUnique() || !TerrainManager.getInstance().isDungeonSpawned(d.getName())) && d.getChance() > 0) {
            int[] dimension = d.getDimension();
            boolean dimensionPass = false;

            for(int b = 0; b < dimension.length; ++b) {
               if(dimension[b] == world.provider.dimensionId) {
                  dimensionPass = true;
                  break;
               }
            }

            if(dimensionPass) {
               String[] var19 = d.getBiomes();
               String[] arr$ = var19;
               int len$ = var19.length;

               for(int i$ = 0; i$ < len$; ++i$) {
                  String currentName = arr$[i$];
                  if(this.isValidBiome(currentName, biome)) {
                     if(random.nextInt(d.getChance()) == 0) {
                        dungeon = d;
                     }
                     break;
                  }
               }
            }
         }
      }

      if(dungeon != null) {
         BuilderHelper.builderHelper.initialize(i);
         builder = dungeon.getBuilder();
         int var18 = ((DungeonMonstersBase)RegisterDungeonMobs.mobList.get(dungeon.getMobID())).getDungeonMonster(world, i, 60, k).getID();
         builder.generate(random, world, i, k, var18);
         BDHelper.println("Generatig " + dungeon.getName() + " at x: " + i + ",  z:" + k);
         BuilderHelper.builderHelper.flush(world);
         if(dungeon.isUnique()) {
            TerrainManager.instance.dungeonSpawned(dungeon.getName());
         }
      }

   }

   public boolean isValidBiome(String biomeName, BiomeGenBase biome) {
      if(!biomeName.equals("ALL") && !biomeName.equals("*")) {
         if(biomeName.equals("NONWATER")) {
            return !BiomeDictionary.isBiomeOfType(biome, Type.WATER);
         } else if(biome.biomeName.equals(biomeName)) {
            return true;
         } else {
            Type type = null;
            Type[] arr$ = Type.values();
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               Type e = arr$[i$];
               if(biomeName.equals(e.toString())) {
                  type = e;
               }
            }

            if(type != null) {
               return BiomeDictionary.isBiomeOfType(biome, type);
            } else {
               return false;
            }
         }
      } else {
         return true;
      }
   }

   public static int getMobIndex(ArrayList chestList, Random random) {
      int[] weights = new int[chestList.size()];
      int maxNum = 0;

      int randomNum;
      for(randomNum = 0; randomNum < chestList.size(); ++randomNum) {
         weights[randomNum] = ((DungeonMonstersBase)chestList.get(randomNum)).getWeight();
         maxNum += weights[randomNum];
      }

      randomNum = random.nextInt(maxNum);
      int index = 0;

      for(int weightSum = weights[0]; weightSum <= randomNum; weightSum += weights[index]) {
         ++index;
      }

      return index;
   }
}
