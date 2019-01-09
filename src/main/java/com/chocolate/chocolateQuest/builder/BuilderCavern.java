package com.chocolate.chocolateQuest.builder;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.API.BuilderBase;
import com.chocolate.chocolateQuest.API.HelperReadConfig;
import com.chocolate.chocolateQuest.block.BlockMobSpawnerTileEntity;
import com.chocolate.chocolateQuest.builder.BuilderEmptyCave;
import com.chocolate.chocolateQuest.builder.schematic.Schematic;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.EntitySchematicBuilder;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import com.chocolate.chocolateQuest.entity.mob.registry.RegisterDungeonMobs;
import com.chocolate.chocolateQuest.items.mobControl.ItemMobToSpawner;
import java.util.Properties;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class BuilderCavern extends BuilderBase {

   String folder;
   int posY = 30;
   int rooms = 3;
   int floors = 3;
   boolean replaceBanners = true;
   BuilderEmptyCave cave = new BuilderEmptyCave();


   public BuilderBase load(Properties prop) {
      this.folder = prop.getProperty("folder");
      if(this.folder == null) {
         this.folder = "unspecified";
      }

      this.rooms = Math.max(3, HelperReadConfig.getIntegerProperty(prop, "rooms", this.rooms));
      this.floors = Math.max(1, HelperReadConfig.getIntegerProperty(prop, "floors", this.floors));
      this.replaceBanners = HelperReadConfig.getBooleanProperty(prop, "replaceBanners", this.replaceBanners);
      this.cave.size = 16;
      this.cave.height = 10;
      this.cave.inverted = true;
      return super.load(prop);
   }

   public String getName() {
      return "cavern";
   }

   public void generate(Random random, World world, int x, int z, int mob) {
      this.generate(random, world, x, this.posY, z, mob);
   }

   public void generate(Random random, World world, int i, int j, int k, int mob) {
      this.cave.caveBlock.id = Blocks.stone;
      this.cave.borderWidth = 0;
      int center = this.rooms / 2;
      boolean height = true;
      byte roomSize = 15;
      i -= center * roomSize;
      k -= center * roomSize;
      EntitySchematicBuilder builder = new EntitySchematicBuilder(world);
      builder.setMobID(mob, this.replaceBanners);
      builder.setPosition((double)i, (double)j, (double)k);
      byte rows = 6;

      for(int x = 0; x < rows; k -= (this.cave.size + 3) * rows) {
         for(int z = 0; z < rows; k += this.cave.size + 3) {
            Schematic s = this.cave.getSchematic(random, world, i, j, k, this.cave.size, this.cave.size, this.cave.height);
            builder.addBuildingPlans(s, i, j, k);
            ++z;
         }

         ++x;
         i += this.cave.size + 3;
      }

      world.spawnEntityInWorld(builder);
   }

   public void decorateSchematic(World world, int i, int j, int k, Schematic schematic, Random random, int mobType) {
      for(int x = 0; x < schematic.width; ++x) {
         int z = 0;

         while(z < schematic.length) {
            int y = 0;

            while(true) {
               if(y < schematic.height) {
                  if(schematic.getBlock(x, y, z) != ChocolateQuest.emptyBlock) {
                     ++y;
                     continue;
                  }

                  schematic.setBlock(x, y, z, Blocks.grass);
                  if(y + 1 < schematic.height) {
                     if(random.nextInt(200) == 0) {
                        schematic.setBlockAndMetadata(x, y + 1, z, Blocks.red_flower, (byte)random.nextInt(9));
                     }

                     if(random.nextInt(100) == 0) {
                        schematic.setBlock(x, y + 1, z, Blocks.tallgrass);
                     } else if(random.nextInt(100) == 0) {
                        schematic.setBlock(x, y + 1, z, Blocks.lit_pumpkin);
                     }
                  }
               }

               ++z;
               break;
            }
         }
      }

   }

   public void addMobsToSchematic(World world, int i, int j, int k, Schematic schematic, Random random, int mobType) {
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
         DungeonMonstersBase var15 = (DungeonMonstersBase)RegisterDungeonMobs.mobList.get(mobType);
         EntityHumanBase human = (EntityHumanBase)var15.getEntity(world, i, j, k);
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
