package com.chocolate.chocolateQuest.builder;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.API.BuilderBase;
import com.chocolate.chocolateQuest.API.HelperReadConfig;
import com.chocolate.chocolateQuest.block.BlockMobSpawnerTileEntity;
import com.chocolate.chocolateQuest.builder.BuilderHelper;
import com.chocolate.chocolateQuest.builder.schematic.Schematic;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.EntitySchematicBuilder;
import com.chocolate.chocolateQuest.entity.ai.EnumAiState;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import com.chocolate.chocolateQuest.entity.mob.registry.RegisterDungeonMobs;
import com.chocolate.chocolateQuest.items.mobControl.ItemMobToSpawner;
import com.chocolate.chocolateQuest.misc.EquipementHelper;
import java.util.Properties;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class BuilderStronghold extends BuilderBase {

   String folder;
   String folderEntance;
   String folderEntanceStairs;
   String folderStairs;
   String folderBossRoom;
   int rooms = 3;
   int floors = 3;
   int mobs = 3;
   boolean replaceBanners = true;


   public BuilderBase load(Properties prop) {
      this.folder = prop.getProperty("folder");
      if(this.folder == null) {
         this.folder = "unspecified";
      }

      this.folderEntance = prop.getProperty("folder_entrance");
      if(this.folderEntance == null) {
         this.folderEntance = "unspecified";
      }

      this.folderStairs = prop.getProperty("folder_stairs");
      if(this.folderStairs == null) {
         this.folderStairs = "unspecified";
      }

      this.folderEntanceStairs = prop.getProperty("folder_entrance_stairs");
      if(this.folderEntanceStairs == null) {
         this.folderEntanceStairs = "unspecified";
      }

      this.folderBossRoom = prop.getProperty("folder_boss");
      if(this.folderBossRoom == null) {
         this.folderBossRoom = "unspecified";
      }

      this.rooms = Math.max(3, HelperReadConfig.getIntegerProperty(prop, "rooms", this.rooms));
      this.floors = Math.max(1, HelperReadConfig.getIntegerProperty(prop, "floors", this.floors));
      this.mobs = HelperReadConfig.getIntegerProperty(prop, "mobs", this.mobs);
      this.replaceBanners = HelperReadConfig.getBooleanProperty(prop, "replaceBanners", this.replaceBanners);
      return super.load(prop);
   }

   public String getName() {
      return "stronghold";
   }

   public void generate(Random random, World world, int x, int z, int mob) {
      byte maxX = 15;
      byte maxY = 15;
      boolean cont = false;
      int cant = 0;
      int media = 0;

      int height;
      for(height = 0; height < maxX; ++height) {
         for(int j = 0; j < maxY; ++j) {
            int h = world.getTopSolidOrLiquidBlock(height + x, j + z);
            media += h;
            ++cant;
         }
      }

      height = media / cant;
      this.generate(random, world, x, height, z, mob);
   }

   public void generate(Random random, World world, int i, int j, int k, int mob) {
      int center = this.rooms / 2;
      byte height = 10;
      byte roomSize = 15;
      i -= center * roomSize;
      k -= center * roomSize;
      j = Math.max(2, j - (this.floors + 1) * height);
      EntitySchematicBuilder builder = new EntitySchematicBuilder(world);
      builder.setMobID(mob, this.replaceBanners);
      builder.setPosition((double)i, (double)j, (double)k);
      builder.addBuildingPlans(BuilderHelper.getRandomNBTMap(this.folderEntance, random, this.getDungeonName()), center * roomSize + i, (this.floors + 1) * height + j, center * roomSize + k);
      int mobLevel = (int)Math.sqrt((double)((world.getSpawnPoint().posX - i) * (world.getSpawnPoint().posX - i) + (world.getSpawnPoint().posZ - j) * (world.getSpawnPoint().posZ - j))) / 1000 - 1;
      int stairsX = random.nextInt(this.rooms);
      int stairsZ = random.nextInt(this.rooms);
      int prevStairsX = center;
      int prevStairsZ = center;

      int x;
      int z;
      for(int s = this.floors - 1; s >= 0; --s) {
         while(stairsX == prevStairsX && stairsZ == prevStairsZ) {
            stairsX = random.nextInt(this.rooms);
            stairsZ = random.nextInt(this.rooms);
         }

         for(x = 0; x < this.rooms; ++x) {
            for(z = 0; z < this.rooms; ++z) {
               Schematic schematic = null;
               if(x == stairsX && z == stairsZ) {
                  if(s == 0) {
                     schematic = BuilderHelper.getRandomNBTMap(this.folderBossRoom, random, this.getDungeonName());
                  }
               } else if(x == prevStairsX && z == prevStairsZ) {
                  if(s != this.floors - 1) {
                     schematic = BuilderHelper.getRandomNBTMap(this.folderStairs, random, this.getDungeonName());
                  }
               } else {
                  schematic = BuilderHelper.getRandomNBTMap(this.folder, random, this.getDungeonName());
               }

               if(schematic != null) {
                  int posX = x * roomSize + i;
                  int posY = s * height + j;
                  int posZ = z * roomSize + k;

                  for(int index = 0; index < this.mobs; ++index) {
                     this.addMobsToSchematic(world, posX, posY, posZ, schematic, random, mob, mobLevel);
                  }

                  builder.addBuildingPlans(schematic, posX, posY, posZ);
               }
            }
         }

         prevStairsX = stairsX;
         prevStairsZ = stairsZ;
      }

      Schematic var24 = new Schematic(this.rooms * roomSize, this.floors * height, this.rooms * roomSize, ChocolateQuest.emptyBlock);

      for(x = 0; x < var24.width; ++x) {
         for(z = 0; z < var24.height; ++z) {
            var24.setBlock(x, z, 0, Blocks.stonebrick);
            var24.setBlock(0, z, x, Blocks.stonebrick);
            var24.setBlock(x, z, var24.width - 1, Blocks.stonebrick);
            var24.setBlock(var24.width - 1, z, x, Blocks.stonebrick);
         }

         for(z = 0; z < var24.width; ++z) {
            var24.setBlock(x, 0, z, Blocks.stonebrick);
            var24.setBlock(x, var24.height - 1, z, Blocks.stonebrick);
         }
      }

      var24.setPosition(i - center * roomSize, j, k - center * roomSize);
      builder.addBuildingPlans(var24, i, j, k);
      var24 = BuilderHelper.getRandomNBTMap(this.folderEntanceStairs, random, this.getDungeonName());
      builder.addBuildingPlans(var24, center * roomSize + i, (this.floors - 1) * height + j, center * roomSize + k);
      world.spawnEntityInWorld(builder);
   }

   public void addMobsToSchematic(World world, int i, int j, int k, Schematic schematic, Random random, int mobType, int mobLevel) {
      int x = random.nextInt(schematic.width);
      int z = random.nextInt(schematic.length);
      int y = -1;

      for(int mob = 0; mob < schematic.height - 1; ++mob) {
         if(schematic.getBlock(x, mob, z) == Blocks.air && schematic.getBlock(x, mob + 1, z) == Blocks.air) {
            y = mob;
            break;
         }
      }

      if(y > -1) {
         DungeonMonstersBase var16 = (DungeonMonstersBase)RegisterDungeonMobs.mobList.get(mobType);
         EntityHumanBase human = (EntityHumanBase)var16.getEntity(world, i, j, k);
         EquipementHelper.equipHumanRandomly(human, mobLevel, EquipementHelper.getRandomType(human, mobLevel > 6?1:5));
         human.partyPositionPersistance = false;
         human.AIMode = EnumAiState.WANDER.ordinal();
         schematic.setBlock(x, y, z, ChocolateQuest.spawner);
         BlockMobSpawnerTileEntity te = new BlockMobSpawnerTileEntity();
         NBTTagCompound tag = ItemMobToSpawner.getHumanSaveTagAndKillIt(i, j, k, human);
         te.mobNBT = tag;
         te.mob = -1;
         te.xCoord = x;
         te.yCoord = y;
         te.zCoord = z;
         schematic.addTileEntity(te);
      }

   }
}
