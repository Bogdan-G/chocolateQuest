package com.chocolate.chocolateQuest.builder.schematic;

import com.chocolate.chocolateQuest.entity.EntityDungeonCrystal;
import com.chocolate.chocolateQuest.entity.EntityPortal;
import com.chocolate.chocolateQuest.utils.BDHelper;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.relauncher.ReflectionHelper;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;
import net.minecraft.block.Block;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;

public class Schematic {

   public short width;
   public short height;
   public short length;
   short[] blocks;
   byte[] metadata;
   String schematicName;
   public int posX;
   public int posY;
   public int posZ;
   public NBTTagList entities;
   public NBTTagList tileEntities;
   Map<String, Integer> idMap;


   public Schematic() {
      this(new File(BDHelper.getAppDir(), BDHelper.getInfoDir() + "Building/test.schematic"));
   }

   public Schematic(File file) {
      this(getNBTMap(file));
   }

   public Schematic(NBTTagCompound tag) {
      this.schematicName = "ChocolateQuest_Schematic";
      this.load(tag);
   }

   public Schematic(int width, int height, int length) {
      this.schematicName = "ChocolateQuest_Schematic";
      this.width = (short)width;
      this.height = (short)height;
      this.length = (short)length;
      int total = width * length * height;
      this.blocks = new short[total];
      this.metadata = new byte[total];
   }

   public Schematic(int width, int height, int length, int posX, int posY, int posZ, String name) {
      this(width, height, length);
      this.posX = posX;
      this.posY = posY;
      this.posZ = posZ;
      this.schematicName = name;
   }

   public Schematic(int width, int height, int length, Block filler) {
      this(width, height, length);

      for(int x = 0; x < width; ++x) {
         for(int y = 0; y < height; ++y) {
            for(int z = 0; z < length; ++z) {
               this.setBlock(x, y, z, filler);
            }
         }
      }

   }

   public void setPosition(int posX, int posY, int posZ) {
      this.posX = posX;
      this.posY = posY;
      this.posZ = posZ;
   }

   public Block getBlock(int x, int y, int z) {
      int index = y * this.width * this.length + z * this.width + x;
      return Block.getBlockById(this.blocks[index]);
   }

   public int getBlockMetadata(int x, int y, int z) {
      int index = y * this.width * this.length + z * this.width + x;
      return this.metadata[index];
   }

   public List getTileEntities() {
      if(this.tileEntities != null) {
         ArrayList list = new ArrayList();

         for(int i = 0; i < this.tileEntities.tagCount(); ++i) {
            NBTTagCompound tag = this.tileEntities.getCompoundTagAt(i);
            TileEntity te = TileEntity.createAndLoadEntity(tag);
            if(te != null) {
               int x = te.xCoord;
               int y = te.yCoord;
               int z = te.zCoord;
               te.xCoord = x + this.posX;
               te.yCoord = y + this.posY;
               te.zCoord = z + this.posZ;
               list.add(te);
            }
         }

         return list;
      } else {
         return new ArrayList();
      }
   }

   public TileEntity getTileEntity(int x, int y, int z) {
      List list = this.getTileEntities();
      Iterator i$ = list.iterator();

      TileEntity t;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         t = (TileEntity)i$.next();
      } while(t.xCoord != x || t.yCoord != y || t.zCoord != z);

      return t;
   }

   public NBTTagList getTileEntitiesTag() {
      return this.tileEntities;
   }

   public List getEntities(World world) {
      if(this.entities != null) {
         ArrayList list = new ArrayList();

         for(int i = 0; i < this.entities.tagCount(); ++i) {
            EntityEgg e = new EntityEgg(world);
            int entityID = e.getEntityId();
            e = null;
            NBTTagCompound tag = this.entities.getCompoundTagAt(i);
            Object var9 = EntityList.createEntityFromNBT(tag, world);
            if(var9 != null) {
               if(var9 instanceof EntityPainting) {
                  EntityPainting portal = (EntityPainting)var9;
                  var9 = new EntityPainting(world, portal.field_146063_b + this.posX, portal.field_146064_c + this.posY, portal.field_146062_d + this.posZ, portal.hangingDirection);
                  ((EntityPainting)var9).art = portal.art;
               } else if(var9 instanceof EntityItemFrame) {
                  EntityItemFrame var10 = (EntityItemFrame)var9;
                  var9 = new EntityItemFrame(world, var10.field_146063_b + this.posX, var10.field_146064_c + this.posY, var10.field_146062_d + this.posZ, var10.hangingDirection);
                  if(var10.getDisplayedItem() != null) {
                     ((EntityItemFrame)var9).setDisplayedItem(var10.getDisplayedItem());
                  }
               } else {
                  ((Entity)var9).setPosition(((Entity)var9).posX + (double)this.posX, ((Entity)var9).posY + (double)this.posY, ((Entity)var9).posZ + (double)this.posZ);
                  if(var9 instanceof EntityCreature) {
                     EntityCreature var12 = (EntityCreature)var9;
                     if(var12.hasHome()) {
                        ChunkCoordinates home = var12.getHomePosition();
                        var12.setHomeArea(home.posX + this.posX, home.posY + this.posY, home.posZ + this.posZ, (int)var12.func_110174_bM());
                     }
                  } else if(var9 instanceof EntityDungeonCrystal) {
                     byte var11 = 10;
                     ((EntityDungeonCrystal)var9).setBounds(this.posX - var11, this.posY, this.posZ - var11, this.posX + this.width + var11, this.posY + this.height, this.posZ + this.length + var11);
                  } else if(var9 instanceof EntityPortal) {
                     EntityPortal var13 = (EntityPortal)var9;
                     var13.onLoadFromSchematic(this.posX, this.posY, this.posZ);
                  }
               }

               ((Entity)var9).chunkCoordX = MathHelper.floor_double(((Entity)var9).posX % 16.0D);
               ((Entity)var9).chunkCoordY = MathHelper.floor_double(((Entity)var9).posY % 16.0D);
               ((Entity)var9).chunkCoordZ = MathHelper.floor_double(((Entity)var9).posZ % 16.0D);
               ((Entity)var9).setEntityId(entityID);
               list.add(var9);
            }
         }

         return list;
      } else {
         return new ArrayList();
      }
   }

   public void setBlock(int x, int y, int z, Block block) {
      x -= this.posX;
      y -= this.posY;
      z -= this.posZ;
      int index = y * this.width * this.length + z * this.width + x;
      this.blocks[index] = (short)GameData.blockRegistry.getId(block);
   }

   public void setBlockMetadata(int x, int y, int z, byte metaData) {
      x -= this.posX;
      y -= this.posY;
      z -= this.posZ;
      int index = y * this.width * this.length + z * this.width + x;
      this.metadata[index] = metaData;
   }

   public void setBlockAndMetadata(int x, int y, int z, Block block, byte metaData) {
      x -= this.posX;
      y -= this.posY;
      z -= this.posZ;
      int index = y * this.width * this.length + z * this.width + x;
      this.blocks[index] = (short)GameData.blockRegistry.getId(block);
      this.metadata[index] = metaData;
   }

   public void addTileEntity(TileEntity te) {
      if(this.tileEntities == null) {
         this.tileEntities = new NBTTagList();
      }

      int x = te.xCoord;
      int y = te.yCoord;
      int z = te.zCoord;
      te.xCoord = x - this.posX;
      te.yCoord = y - this.posY;
      te.zCoord = z - this.posZ;
      NBTTagCompound data = new NBTTagCompound();
      te.writeToNBT(data);
      this.tileEntities.appendTag(data);
      te.xCoord = x;
      te.yCoord = y;
      te.zCoord = z;
   }

   public void addEntity(Entity e) {
      if(this.entities == null) {
         this.entities = new NBTTagList();
      }

      if(e instanceof EntityHanging) {
         EntityHanging data = (EntityHanging)e;
         data.field_146063_b -= this.posX;
         data.field_146064_c -= this.posY;
         data.field_146062_d -= this.posZ;
      } else {
         e.setPosition(e.posX - (double)this.posX, e.posY - (double)this.posY, e.posZ - (double)this.posZ);
      }

      if(e instanceof EntityCreature) {
         EntityCreature data1 = (EntityCreature)e;
         if(data1.hasHome()) {
            ChunkCoordinates portal = data1.getHomePosition();
            int home = (int)data1.func_110174_bM();
            data1.setHomeArea(portal.posX - this.posX, portal.posY - this.posY, portal.posZ - this.posZ, home);
         }
      } else if(e instanceof EntityPortal) {
         EntityPortal data2 = (EntityPortal)e;
         data2.onSaveToSchematic(this.posX, this.posY, this.posZ);
      }

      NBTTagCompound data3 = new NBTTagCompound();
      e.writeToNBTOptional(data3);
      this.entities.appendTag(data3);
      if(e instanceof EntityHanging) {
         EntityHanging portal1 = (EntityHanging)e;
         portal1.field_146063_b += this.posX;
         portal1.field_146064_c += this.posY;
         portal1.field_146062_d += this.posZ;
      } else {
         e.setPosition(e.posX + (double)this.posX, e.posY + (double)this.posY, e.posZ + (double)this.posZ);
      }

      if(e instanceof EntityCreature) {
         EntityCreature portal2 = (EntityCreature)e;
         if(portal2.hasHome()) {
            ChunkCoordinates home1 = portal2.getHomePosition();
            int distance = (int)portal2.func_110174_bM();
            portal2.setHomeArea(home1.posX + this.posX, home1.posY + this.posY, home1.posZ + this.posZ, distance);
         }
      } else if(e instanceof EntityPortal) {
         EntityPortal portal3 = (EntityPortal)e;
         portal3.onLoadFromSchematic(this.posX, this.posY, this.posZ);
      }

   }

   public void load(NBTTagCompound schematic) {
      this.width = schematic.getShort("Width");
      this.height = schematic.getShort("Height");
      this.length = schematic.getShort("Length");
      int total = this.width * this.length * this.height;
      this.blocks = new short[total];
      this.metadata = new byte[total];
      byte[] blockBytes = schematic.getByteArray("Blocks");
      byte[] addedBytes = null;
      if(schematic.hasKey("Add")) {
         addedBytes = schematic.getByteArray("Add");
      }

      for(int idMappingNBT = 0; idMappingNBT < total; ++idMappingNBT) {
         short currentID = (short)(blockBytes[idMappingNBT] & 255);
         if(addedBytes != null) {
            currentID = (short)(currentID ^ addedBytes[idMappingNBT] << 8);
         }

         this.blocks[idMappingNBT] = currentID;
      }

      this.metadata = schematic.getByteArray("Data");
      this.entities = schematic.getTagList("Entities", schematic.getId());
      this.tileEntities = schematic.getTagList("TileEntities", schematic.getId());
      NBTTagList var7 = schematic.getTagList("IDMapping", schematic.getId());
      if(var7 != null && var7.tagCount() > 0) {
         this.loadMappings(var7);
         this.translateToLocal();
      }

   }

   public void saveToNBT(NBTTagCompound data) {
      data.setShort("Width", this.width);
      data.setShort("Height", this.height);
      data.setShort("Length", this.length);
      data.setString("Materials", "Alpha");
      data.setString("Name", this.schematicName);
      byte[] vanilaBlockIds = new byte[this.blocks.length];
      byte[] addedBits = new byte[this.blocks.length];

      for(int i = 0; i < this.blocks.length; ++i) {
         vanilaBlockIds[i] = (byte)(this.blocks[i] & 255);
         addedBits[i] = (byte)((this.blocks[i] & 3840) >> 8);
      }

      data.setByteArray("Blocks", vanilaBlockIds);
      data.setByteArray("Add", addedBits);
      data.setByteArray("Data", this.metadata);
      if(this.tileEntities != null) {
         data.setTag("TileEntities", this.tileEntities);
      }

      if(this.entities != null) {
         data.setTag("Entities", this.entities);
      }

      data.setTag("IDMapping", this.saveMappings());
   }

   public void save(File file) {
      NBTTagCompound data = new NBTTagCompound();
      this.saveToNBT(data);

      try {
         FileOutputStream e = new FileOutputStream(file);
         writeCompressed(data, e);
         e.close();
      } catch (FileNotFoundException var4) {
         var4.printStackTrace();
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }

   public void translateToLocal() {
      HashMap<Integer, String> tMap = new HashMap<Integer, String>();
      Iterator i = this.idMap.keySet().iterator();

      while(i.hasNext()) {
         String currentID = (String)i.next();
         tMap.put(this.idMap.get(currentID), currentID);
      }

      for(int var5 = 0; var5 < this.blocks.length; ++var5) {
         if(this.blocks[var5] != 0) {
            short var6 = this.blocks[var5];
            if(this.idMap.containsValue(Integer.valueOf(var6))) {
               Block block = Block.getBlockFromName((String)tMap.get(Integer.valueOf(var6)));
               if(block != null) {
                  this.blocks[var5] = (short)Block.getIdFromBlock(block);
               } else {
                  this.blocks[var5] = 0;
               }
            }
         }
      }

   }

   public void loadMappings(NBTTagList list) {
      this.idMap = new HashMap<String, Integer>();

      for(int i = 0; i < list.tagCount(); ++i) {
         NBTTagCompound tag = list.getCompoundTagAt(i);
         this.idMap.put(tag.getString("ItemType"), Integer.valueOf(tag.getInteger("ItemId")));
      }

   }

   public NBTBase saveMappings() {
      this.idMap = new HashMap<String, Integer>();

      String key;
      for(int list = 0; list < this.blocks.length; ++list) {
         if(!this.idMap.containsValue(Short.valueOf(this.blocks[list]))) {
            Block i$ = Block.getBlockById(this.blocks[list]);
            key = Block.blockRegistry.getNameForObject(i$);
            this.idMap.put(key, Integer.valueOf(this.blocks[list]));
         }
      }

      NBTTagList var5 = new NBTTagList();
      Iterator var6 = this.idMap.keySet().iterator();

      while(var6.hasNext()) {
         key = (String)var6.next();
         NBTTagCompound tag = new NBTTagCompound();
         tag.setString("ItemType", key);
         tag.setInteger("ItemId", ((Integer)this.idMap.get(key)).intValue());
         var5.appendTag(tag);
      }

      return var5;
   }

   public static void writeCompressed(NBTBase tagCompound, FileOutputStream file) throws IOException {
      DataOutputStream dataOutputStream = new DataOutputStream(new GZIPOutputStream(file));

      try {
         Method e = ReflectionHelper.findMethod(NBTTagCompound.class, null, (String[])new String[]{"func_150298_a", "a"}, (Class[])new Class[]{String.class, NBTBase.class, DataOutput.class});
         e.invoke(null, new Object[]{"Schematic", tagCompound, dataOutputStream});
      } catch (Exception var7) {
         var7.printStackTrace();
      } finally {
         dataOutputStream.close();
      }

   }

   public static NBTTagCompound getNBTMap(File file) {
      CrashReport crashreport;
      try {
         FileInputStream e = new FileInputStream(file);
         NBTTagCompound crashreport1 = CompressedStreamTools.readCompressed(e);
         if(crashreport1 instanceof NBTTagCompound) {
            e.close();
            return (NBTTagCompound)crashreport1;
         } else {
            BDHelper.println("Found corrupted better dungeons template :" + file.getPath() + ", Skipping generation.");
            return null;
         }
      } catch (FileNotFoundException var3) {
         crashreport = CrashReport.makeCrashReport(var3, "File not found at better dungeons mod, file: " + file.getPath());
         throw new ReportedException(crashreport);
      } catch (IOException var4) {
         crashreport = CrashReport.makeCrashReport(var4, "Error reading file at better dungeons mod, file: " + file.getPath());
         throw new ReportedException(crashreport);
      }
   }
}
