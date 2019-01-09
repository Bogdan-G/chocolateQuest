package com.chocolate.chocolateQuest.builder;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.API.RegisterChestItem;
import com.chocolate.chocolateQuest.block.BlockBannerStandTileEntity;
import com.chocolate.chocolateQuest.block.BlockMobSpawnerTileEntity;
import com.chocolate.chocolateQuest.builder.BlockData;
import com.chocolate.chocolateQuest.builder.Perlin3D;
import com.chocolate.chocolateQuest.builder.schematic.Schematic;
import com.chocolate.chocolateQuest.entity.EntityDecoration;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import com.chocolate.chocolateQuest.entity.mob.registry.RegisterDungeonMobs;
import com.chocolate.chocolateQuest.items.ItemArmorBase;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ReportedException;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.DungeonHooks;

public class BuilderHelper {

   public static BuilderHelper builderHelper = new BuilderHelper();
   static final byte DEFAULT = 0;
   static final byte FOOD = 1;
   static final byte WEAPONS = 2;
   static final byte MINERALS = 3;
   static final byte TREASURE = 4;
   List specialBlocks;
   List tileEntities;
   public static int BLOCK_NOTIFICATION;
   private int structureGenerationAmmount;
   private int structureGenerationResetTimer = 0;


   public int getStructureGenerationAmmount() {
      return this.structureGenerationAmmount;
   }

   public void setStructureGenerationAmmount(int ammount) {
      this.structureGenerationAmmount = ammount;
   }

   public void resetStructureGenerationAmmount() {
      if(this.structureGenerationResetTimer > 0) {
         this.structureGenerationResetTimer = this.structureGenerationResetTimer--;
      } else {
         this.structureGenerationAmmount = 0;
      }

   }

   public void initialize(int blockNotify) {
      BLOCK_NOTIFICATION = blockNotify;
      if(this.specialBlocks == null) {
         this.specialBlocks = new ArrayList();
      }

      this.specialBlocks.clear();
      if(this.tileEntities == null) {
         this.tileEntities = new ArrayList();
      }

      this.tileEntities.clear();
   }

   public void flush(World world) {
      this.copyTileEntities(world);
   }

   public void putSchematicInWorld(Random random, World world, Schematic schematic, int i, int j, int k, int idMob, boolean replaceFlags) {
      schematic.setPosition(i, j, k);
      short sx = schematic.width;
      short sy = schematic.height;
      short sz = schematic.length;

      int tagCount;
      for(int list = 0; list < sy; ++list) {
         for(int tags = 0; tags < sx; ++tags) {
            for(tagCount = 0; tagCount < sz; ++tagCount) {
               Block listEntity = schematic.getBlock(tags, list, tagCount);
               int i$ = schematic.getBlockMetadata(tags, list, tagCount);
               int e = i + tags;
               int tempEntity = j + list;
               int tag = k + tagCount;
               if(!this.checkIfPlacedOnFirstPass(listEntity)) {
                  this.specialBlocks.add(new BlockData(e, tempEntity, tag, listEntity, i$));
               } else if(listEntity != ChocolateQuest.emptyBlock) {
                  this.putBlockWithLoot(random, world, e, tempEntity, tag, listEntity, i$, idMob);
               }
            }
         }
      }

      this.copySpecialBlocks(world);
      List var23 = schematic.getTileEntities();
      NBTTagList var24 = schematic.getTileEntitiesTag();
      tagCount = 0;

      for(Iterator var25 = var23.iterator(); var25.hasNext(); ++tagCount) {
         TileEntity var27 = (TileEntity)var25.next();
         Block var30 = world.getBlock(var27.xCoord, var27.yCoord, var27.zCoord);
         if(var30 instanceof BlockContainer) {
            TileEntity var32 = ((BlockContainer)var30).createNewTileEntity(world, world.getBlockMetadata(var27.xCoord, var27.yCoord, var27.zCoord));
            NBTTagCompound var31 = var24.getCompoundTagAt(tagCount);
            var32.readFromNBT(var31);
            boolean putTileEntity = true;
            if(var32 instanceof BlockMobSpawnerTileEntity) {
               BlockMobSpawnerTileEntity spawner = (BlockMobSpawnerTileEntity)var32;
               spawner.mob = idMob;
            }

            if(var32 instanceof BlockBannerStandTileEntity && replaceFlags) {
               BlockBannerStandTileEntity var34 = (BlockBannerStandTileEntity)var32;
               int name = ((DungeonMonstersBase)RegisterDungeonMobs.mobList.get(idMob)).getFlagId();
               var34.item = new ItemStack(ChocolateQuest.banner, 1, name);
            }

            if(var32 instanceof TileEntityMobSpawner) {
               TileEntityMobSpawner var35 = (TileEntityMobSpawner)var32;
               String var33 = var35.func_145881_a().getEntityNameToSpawn();
               if(var33.equals("Pig")) {
                  setMobForSpawner(var35, idMob, var27.xCoord, var27.yCoord, var27.zCoord, random);
               }
            }

            if(var30 == Blocks.chest) {
               putTileEntity = false;
            }

            if(var30 == Blocks.furnace && this.isInventoryEmpty((TileEntityFurnace)var32)) {
               putTileEntity = false;
            }

            if(var30 == Blocks.dispenser && this.isInventoryEmpty((TileEntityDispenser)var32)) {
               putTileEntity = false;
            }

            if(putTileEntity) {
               this.addTileEntity(var27.xCoord, var27.yCoord, var27.zCoord, var32);
            }
         }
      }

      List var28 = schematic.getEntities(world);
      Iterator var26 = var28.iterator();

      while(var26.hasNext()) {
         Entity var29 = (Entity)var26.next();
         world.spawnEntityInWorld(var29);
      }

   }

   public void addTileEntity(int x, int y, int z, TileEntity tileEntity) {
      tileEntity.xCoord = x;
      tileEntity.yCoord = y;
      tileEntity.zCoord = z;
      this.tileEntities.add(tileEntity);
   }

   public void copyTileEntities(World world) {
      if(this.tileEntities.size() > 0) {
         Iterator i = this.tileEntities.iterator();

         while(i.hasNext()) {
            TileEntity tempEntity = (TileEntity)i.next();
            world.setTileEntity(tempEntity.xCoord, tempEntity.yCoord, tempEntity.zCoord, tempEntity);
         }
      }

   }

   public boolean checkIfPlacedOnFirstPass(Block id) {
      return id != Blocks.redstone_wire && id != Blocks.hopper && id != Blocks.redstone_torch && id != Blocks.unlit_redstone_torch && id != Blocks.stone_button && id != Blocks.wooden_button && id != Blocks.bed && id != Blocks.torch && id != Blocks.wooden_door && id != Blocks.iron_door && id != Blocks.lever && id != Blocks.torch && id != Blocks.ladder && id != Blocks.bed && id != Blocks.tripwire_hook && id != Blocks.wall_sign && id != Blocks.piston && id != Blocks.sticky_piston && id != Blocks.piston_head && id != Blocks.trapdoor;
   }

   public void copySpecialBlocks(World world) {
      BlockData b = null;
      new Random();
      if(this.specialBlocks.size() > 0) {
         Iterator i = this.specialBlocks.iterator();

         while(i.hasNext()) {
            b = (BlockData)i.next();
            this.putBlock(world, b.x, b.y, b.z, b.block, b.blockMetadata);
         }

         this.specialBlocks.clear();
      }

   }

   public void putBlock(World world, int x, int y, int z, Block block, int metadata) {
      if(block != Blocks.wooden_door && block != Blocks.iron_door) {
         if(block != Blocks.stone_button && block != Blocks.wooden_button) {
            if(block.canProvidePower() && !(block instanceof BlockPressurePlate) && !(block instanceof BlockButton)) {
               world.setBlock(x, y, z, block, metadata, 2);
            } else if(block == Blocks.hopper) {
               world.setBlock(x, y, z, block, metadata, 1);
            } else if(block == Blocks.piston_head) {
               world.setBlock(x, y, z, block, metadata, 0);
            } else {
               world.setBlock(x, y, z, block, metadata, 2);
            }
         } else {
            world.setBlock(x, y, z, block, metadata, 2);
         }
      } else if(metadata < 8) {
         ItemDoor.placeDoorBlock(world, x, y, z, metadata, block == Blocks.wooden_door?Blocks.wooden_door:Blocks.iron_door);
      } else {
         world.setBlock(x, y, z, block, metadata, 0);
      }

   }

   public void putBlockWithLoot(Random random, World world, int posX, int posY, int posZ, Block block, int metadata, int mobType) {
      int var11;
      if(block == ChocolateQuest.exporterChest) {
         if(metadata == 4) {
            world.setBlockToAir(posX, posY, posZ);
            Entity ted = ((DungeonMonstersBase)RegisterDungeonMobs.mobList.get(mobType)).getBoss(world, posX, posY, posZ);
            if(ted != null) {
               ted.setPosition((double)posX, (double)posY, (double)posZ);
               world.spawnEntityInWorld(ted);
               if(ted.ridingEntity != null) {
                  ted.ridingEntity.setPosition((double)posX, (double)posY, (double)posZ);
                  world.spawnEntityInWorld(ted.ridingEntity);
               }
            }
         } else if(metadata == 1) {
            var11 = ((DungeonMonstersBase)RegisterDungeonMobs.mobList.get(mobType)).getDifficulty(world, posX, posY, posZ);
            addFoodChest(random, world, posX, posY, posZ, var11);
         } else if(metadata == 0) {
            var11 = ((DungeonMonstersBase)RegisterDungeonMobs.mobList.get(mobType)).getDifficulty(world, posX, posY, posZ);
            addTreasure(random, world, posX, posY, posZ, var11);
         } else if(metadata == 3) {
            var11 = ((DungeonMonstersBase)RegisterDungeonMobs.mobList.get(mobType)).getDifficulty(world, posX, posY, posZ);
            addMineralChest(random, world, posX, posY, posZ, var11);
         } else if(metadata == 2) {
            var11 = ((DungeonMonstersBase)RegisterDungeonMobs.mobList.get(mobType)).getDifficulty(world, posX, posY, posZ);
            addWeaponChest(random, world, posX, posY, posZ, var11);
         }
      } else if(block == Blocks.chest) {
         var11 = ((DungeonMonstersBase)RegisterDungeonMobs.mobList.get(mobType)).getDifficulty(world, posX, posY, posZ);
         addChest(random, world, posX, posY, posZ, var11);
      } else if(block == Blocks.furnace) {
         world.setBlock(posX, posY, posZ, block, metadata, 3);
         TileEntityFurnace var12 = (TileEntityFurnace)world.getTileEntity(posX, posY, posZ);
         if(random.nextInt(15) == 0) {
            var12.setInventorySlotContents(0, new ItemStack(Items.gold_ingot, random.nextInt(3) + 1));
         }

         if(random.nextInt(3) == 0) {
            var12.setInventorySlotContents(1, new ItemStack(Items.coal, random.nextInt(45) + 1));
         }

         world.setBlockMetadataWithNotify(posX, posY, posZ, metadata, 3);
      } else if(block == Blocks.dispenser) {
         world.setBlock(posX, posY, posZ, block, metadata, 0);
         TileEntityDispenser var13 = (TileEntityDispenser)world.getTileEntity(posX, posY, posZ);

         for(int v = 0; v < 9; ++v) {
            if(random.nextInt(20) == 0) {
               if(random.nextInt(50) == 0) {
                  var13.setInventorySlotContents(v, new ItemStack(Items.experience_bottle, random.nextInt(8) + 1));
               }

               var13.setInventorySlotContents(v, new ItemStack(Items.fire_charge, random.nextInt(16) + 1));
            } else {
               var13.setInventorySlotContents(v, new ItemStack(Items.arrow, random.nextInt(32) + 1));
            }
         }

         world.setBlockMetadataWithNotify(posX, posY, posZ, metadata, 3);
      } else if(block == Blocks.dropper) {
         world.setBlock(posX, posY, posZ, block, metadata, 3);
         world.setBlockMetadataWithNotify(posX, posY, posZ, metadata, 3);
      } else {
         this.putBlock(world, posX, posY, posZ, block, metadata);
      }

   }

   public void putTileEntityWithLoot(World world, TileEntity t, int idMob, boolean replaceFlags, Random random) {
      boolean putTileEntity = true;
      Block block = world.getBlock(t.xCoord, t.yCoord, t.zCoord);
      if(t instanceof BlockMobSpawnerTileEntity) {
         BlockMobSpawnerTileEntity spawner = (BlockMobSpawnerTileEntity)t;
         spawner.mob = idMob;
      }

      if(t instanceof BlockBannerStandTileEntity && replaceFlags) {
         BlockBannerStandTileEntity spawner2 = (BlockBannerStandTileEntity)t;
         int name = ((DungeonMonstersBase)RegisterDungeonMobs.mobList.get(idMob)).getFlagId();
         spawner2.item = new ItemStack(ChocolateQuest.banner, 1, name);
      }

      if(t instanceof TileEntityMobSpawner) {
         TileEntityMobSpawner spawner1 = (TileEntityMobSpawner)t;
         String name1 = spawner1.func_145881_a().getEntityNameToSpawn();
         if(name1.equals("Pig")) {
            setMobForSpawner(spawner1, idMob, t.xCoord, t.yCoord, t.zCoord, random);
         }
      }

      if(block == Blocks.chest) {
         putTileEntity = false;
      }

      if(block == Blocks.furnace && this.isInventoryEmpty((TileEntityFurnace)t)) {
         putTileEntity = false;
      }

      if(block == Blocks.dispenser && this.isInventoryEmpty((TileEntityDispenser)t)) {
         putTileEntity = false;
      }

      if(putTileEntity) {
         world.setTileEntity(t.xCoord, t.yCoord, t.zCoord, t);
      }

   }

   public boolean buildStep(Random rng, World world, Schematic cachedSchematic, int step, boolean isFirstPass, int mobID, boolean replaceFlags, boolean addLoot) {
      boolean blockPlaced = false;
      int posX = cachedSchematic.posX;
      int posY = cachedSchematic.posY;
      int posZ = cachedSchematic.posZ;
      int x = step % cachedSchematic.width;
      int z = step / cachedSchematic.width % cachedSchematic.length;
      int y = step / (cachedSchematic.width * cachedSchematic.length);
      posX += x;
      posY += y;
      posZ += z;
      Block block = cachedSchematic.getBlock(x, y, z);
      if(block == ChocolateQuest.emptyBlock) {
         return false;
      } else if(block == Blocks.air && world.getBlock(posX, posY, posZ) == Blocks.air) {
         return false;
      } else {
         int metadata = cachedSchematic.getBlockMetadata(x, y, z);
         if(isFirstPass) {
            if(this.checkIfPlacedOnFirstPass(block)) {
               blockPlaced = true;
            }
         } else if(!this.checkIfPlacedOnFirstPass(block)) {
            blockPlaced = true;
         }

         if(blockPlaced) {
            if(addLoot) {
               this.putBlockWithLoot(rng, world, posX, posY, posZ, block, metadata, mobID);
            } else {
               this.putBlock(world, posX, posY, posZ, block, metadata);
            }

            if(block instanceof BlockContainer) {
               TileEntity t = cachedSchematic.getTileEntity(posX, posY, posZ);
               if(t != null) {
                  if(addLoot) {
                     this.putTileEntityWithLoot(world, t, mobID, replaceFlags, new Random());
                  } else {
                     world.setTileEntity(t.xCoord, t.yCoord, t.zCoord, t);
                  }
               }
            }
         }

         return blockPlaced;
      }
   }

   public boolean putEntities(World world, Schematic cachedSchematic, int step) {
      return this.putEntities(world, cachedSchematic, step, 0, false);
   }

   public boolean putEntities(World world, Schematic cachedSchematic, int step, int mobID, boolean replaceFlags) {
      List list = cachedSchematic.getEntities(world);
      if(step >= list.size()) {
         return false;
      } else {
         Entity e = (Entity)list.get(step);
         if(replaceFlags && e instanceof EntityDecoration) {
            int flagID = ((DungeonMonstersBase)RegisterDungeonMobs.mobList.get(mobID)).getFlagId();
            ((EntityDecoration)e).type = flagID;
         }

         world.spawnEntityInWorld(e);
         return true;
      }
   }

   public boolean isInventoryEmpty(IInventory inv) {
      for(int i = 0; i < inv.getSizeInventory(); ++i) {
         if(inv.getStackInSlot(i) != null) {
            return false;
         }
      }

      return true;
   }

   public static void clearArea(Random random, World world, int i, int j, int k, int sizeX, int sizeZ) {
      Perlin3D p = new Perlin3D(world.getSeed(), 8, random);
      Perlin3D p2 = new Perlin3D(world.getSeed(), 32, random);
      byte wallSize = 8;
      int size = sizeX + wallSize * 2;
      boolean height = true;
      i -= wallSize;
      k -= wallSize;

      for(int x = 0; x < size; ++x) {
         for(int z = 0; z < size; ++z) {
            int maxHeight = world.getTopSolidOrLiquidBlock(x + i, z + k) - 1 - j;

            for(int biome = 0; biome <= maxHeight; ++biome) {
               if(x > wallSize && z > wallSize && x < size - wallSize && z < size - wallSize) {
                  world.setBlockToAir(x + i, j + biome, z + k);
               } else {
                  float noiseVar = (float)(maxHeight - biome) / ((float)Math.max(1, maxHeight) * 1.5F);
                  noiseVar += Math.max(0.0F, (float)(wallSize - x) / 8.0F);
                  noiseVar += Math.max(0.0F, (float)(wallSize - (size - x)) / 8.0F);
                  noiseVar += Math.max(0.0F, (float)(wallSize - z) / 8.0F);
                  noiseVar += Math.max(0.0F, (float)(wallSize - (size - z)) / 8.0F);
                  double value = (p.getNoiseAt(x + i, biome, z + k) + p2.getNoiseAt(x + i, biome, z + k) + (double)noiseVar) / 3.0D;
                  if(value < 0.5D) {
                     world.setBlockToAir(i + x, j + biome, k + z);
                  }
               }
            }

            maxHeight = world.getTopSolidOrLiquidBlock(x + i, z + k);
            BiomeGenBase var20 = world.getBiomeGenForCoords(x + i, z + k);
            world.setBlock(i + x, maxHeight - 1, k + z, var20.topBlock);
         }
      }

   }

   public static void addSpawner(Random random, World world, int x, int y, int z, int idMob) {
      world.setBlock(x, y, z, Blocks.mob_spawner, 0, 0);
      TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)world.getTileEntity(x, y, z);
      if(tileentitymobspawner != null) {
         setMobForSpawner(tileentitymobspawner, idMob, x, y, z, random);
      }

   }

   public static void setMobForSpawner(TileEntityMobSpawner spawner, int idMob, int x, int y, int z, Random random) {
      String mob = ((DungeonMonstersBase)RegisterDungeonMobs.mobList.get(idMob)).getSpawnerName(x, y, z, random);
      if(mob != null) {
         spawner.func_145881_a().setEntityName(mob);
      } else {
         spawner.func_145881_a().setEntityName(DungeonHooks.getRandomDungeonMob(random));
      }

   }

   public static void addItemToInventory(IInventory inventory, ItemStack itemstack, int slot) {
      WeightedRandomChestContent chestContent = itemstack.getItem().getChestGenBase(ChestGenHooks.getInfo("dungeonChest"), new Random(), new WeightedRandomChestContent(itemstack, 1, 1, 1));
      inventory.setInventorySlotContents(slot, chestContent.theItemId);
   }

   public static boolean addTreasure(Random random, World world, int x, int y, int z, int amount) {
      world.setBlock(x, y, z, Blocks.chest, 0, 0);
      TileEntityChest tileentitychest = new TileEntityChest();
      int itemsCount = random.nextInt(5) + amount;
      ItemStack itemstack = null;

      int slot;
      for(int record = 1; record < itemsCount + 1; ++record) {
         itemstack = RegisterChestItem.getRandomItemStack(RegisterChestItem.treasureList, random);
         slot = random.nextInt(tileentitychest.getSizeInventory());
         addItemToInventory(tileentitychest, itemstack, slot);
      }

      if(random.nextInt(3) == 0) {
         Item var12;
         switch(random.nextInt(10)) {
         case 0:
            var12 = Items.record_blocks;
            break;
         case 1:
            var12 = Items.record_cat;
            break;
         case 2:
            var12 = Items.record_chirp;
            break;
         case 3:
            var12 = Items.record_far;
            break;
         case 4:
            var12 = Items.record_mall;
            break;
         case 5:
            var12 = Items.record_mellohi;
            break;
         case 6:
            var12 = Items.record_stal;
            break;
         case 7:
            var12 = Items.record_strad;
            break;
         case 8:
            var12 = Items.record_wait;
            break;
         case 9:
            var12 = Items.record_ward;
            break;
         default:
            var12 = Items.record_blocks;
         }

         slot = random.nextInt(tileentitychest.getSizeInventory());
         tileentitychest.setInventorySlotContents(slot, new ItemStack(var12));
      }

      world.setTileEntity(x, y, z, tileentitychest);
      return true;
   }

   public static boolean addFoodChest(Random random, World world, int x, int y, int z, int amount) {
      world.setBlock(x, y, z, Blocks.chest, 0, 0);
      TileEntityChest tileentitychest = new TileEntityChest();
      int itemsCount = random.nextInt(6) + amount;

      for(int f = 0; f < itemsCount; ++f) {
         ItemStack itemstack = RegisterChestItem.getRandomItemStack(RegisterChestItem.foodList, random);
         int slot = random.nextInt(tileentitychest.getSizeInventory());
         addItemToInventory(tileentitychest, itemstack, slot);
      }

      world.setTileEntity(x, y, z, tileentitychest);
      return true;
   }

   public static boolean addMineralChest(Random random, World world, int x, int y, int z, int amount) {
      world.setBlock(x, y, z, Blocks.chest, 0, 0);
      TileEntityChest tileentitychest = new TileEntityChest();
      int itemsCount = random.nextInt(6) + amount;
      ItemStack itemstack = null;

      for(int f = 0; f < itemsCount; ++f) {
         itemstack = RegisterChestItem.getRandomItemStack(RegisterChestItem.mineralList, random);
         int slot = random.nextInt(tileentitychest.getSizeInventory());
         addItemToInventory(tileentitychest, itemstack, slot);
      }

      world.setTileEntity(x, y, z, tileentitychest);
      return true;
   }

   public static boolean addWeaponChest(Random random, World world, int x, int y, int z, int amount) {
      world.setBlock(x, y, z, Blocks.chest, 0, 0);
      TileEntityChest tileentitychest = new TileEntityChest();
      int itemsCount = random.nextInt(6) + amount;
      ItemStack itemstack = null;

      for(int f = 0; f < itemsCount; ++f) {
         itemstack = RegisterChestItem.getRandomItemStack(RegisterChestItem.weaponList, random);
         int slot;
         if(itemstack != null) {
            BDHelper.EnchantItemRandomly(itemstack, random);
            if(itemstack.getItem() instanceof ItemArmor && !(itemstack.getItem() instanceof ItemArmorBase)) {
               slot = ((ItemArmor)itemstack.getItem()).armorType;
               Item armorItem = ChocolateQuest.diamondBoots;
               switch(slot) {
               case 0:
                  armorItem = ChocolateQuest.diamondHelmet;
                  break;
               case 1:
                  armorItem = ChocolateQuest.diamondPlate;
                  break;
               case 2:
                  armorItem = ChocolateQuest.diamondPants;
               }

               itemstack = armorItem.getChestGenBase(ChestGenHooks.getInfo("dungeonChest"), random, new WeightedRandomChestContent(itemstack, 1, 1, 1)).theItemId;
            }
         }

         slot = random.nextInt(tileentitychest.getSizeInventory());
         addItemToInventory(tileentitychest, itemstack, slot);
      }

      world.setTileEntity(x, y, z, tileentitychest);
      return true;
   }

   public static boolean addChest(Random random, World world, int x, int y, int z, int amount) {
      world.setBlock(x, y, z, Blocks.chest, 0, 3);
      int itemsCount = random.nextInt(8) + amount;
      TileEntityChest tileentitychest = new TileEntityChest();
      int f = 0;
      if(random.nextInt(150) == 0) {
         Item slot = random.nextBoolean()?Items.record_11:Items.record_13;
         tileentitychest.setInventorySlotContents(f, new ItemStack(slot));
         ++f;
         ++itemsCount;
      }

      while(f < itemsCount) {
         ItemStack itemstack = RegisterChestItem.getRandomItemStack(RegisterChestItem.chestList, random);
         int var11 = random.nextInt(tileentitychest.getSizeInventory());
         addItemToInventory(tileentitychest, itemstack, var11);
         ++f;
      }

      world.setTileEntity(x, y, z, tileentitychest);
      return true;
   }

   public static Schematic getNBTMap(String mapDir) {
      File file = new File(BDHelper.getAppDir(), BDHelper.getInfoDir() + mapDir);
      return !file.exists()?null:getNBTMap(file);
   }

   public static Schematic getRandomNBTMap(String d, Random random, String offendor) {
      File dir = new File(BDHelper.getAppDir(), BDHelper.getInfoDir() + d);
      File[] file = dir.listFiles(new FilenameFilter() {
         public boolean accept(File dir, String name) {
            return !name.startsWith(".");
         }
      });
      int s = 0;

      try {
         if(file.length > 1) {
            s = random.nextInt(file.length);
         }

         File throwable1 = file[s];
         return getNBTMap(throwable1);
      } catch (Throwable var9) {
         String type = dir.exists()?"empty":"missing";
         CrashReport crashreport = CrashReport.makeCrashReport(var9, "Error loading dungeon " + offendor + " from " + type + " folder: " + dir.getPath());
         throw new ReportedException(crashreport);
      }
   }

   public static Schematic getNBTMap(File file) {
      return new Schematic(file);
   }

}
