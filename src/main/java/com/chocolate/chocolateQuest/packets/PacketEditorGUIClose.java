package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.block.BlockEditorTileEntity;
import com.chocolate.chocolateQuest.block.BlockMobSpawnerTileEntity;
import com.chocolate.chocolateQuest.builder.schematic.Schematic;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.items.mobControl.ItemMobToSpawner;
import com.chocolate.chocolateQuest.packets.Coords;
import com.chocolate.chocolateQuest.packets.PacketBase;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class PacketEditorGUIClose implements IMessage, IMessageHandler<PacketEditorGUIClose, IMessage> {

   int x;
   int y;
   int z;
   int width;
   int height;
   int length;
   String text;
   byte action;
   List coords;


   public PacketEditorGUIClose() {}

   public PacketEditorGUIClose(int posX, int posY, int posZ, int width, int length, int height, String name, byte action) {
      this.x = posX;
      this.y = posY;
      this.z = posZ;
      this.width = width;
      this.length = length;
      this.height = height;
      this.text = name;
      this.action = action;
   }

   public void fromBytes(ByteBuf inputStream) {
      this.x = inputStream.readInt();
      this.y = inputStream.readInt();
      this.z = inputStream.readInt();
      this.width = inputStream.readInt();
      this.length = inputStream.readInt();
      this.height = inputStream.readInt();
      this.text = PacketBase.readString(inputStream);
      this.action = inputStream.readByte();
   }

   public void toBytes(ByteBuf outputStream) {
      outputStream.writeInt(this.x);
      outputStream.writeInt(this.y);
      outputStream.writeInt(this.z);
      outputStream.writeInt(this.width);
      outputStream.writeInt(this.length);
      outputStream.writeInt(this.height);
      PacketBase.writeString(outputStream, this.text);
      outputStream.writeByte(this.action);
   }

   public IMessage onMessage(PacketEditorGUIClose message, MessageContext ctx) {
      EntityPlayerMP entityPlayer = ctx.getServerHandler().playerEntity;
      message.execute(entityPlayer);
      return null;
   }

   public void execute(EntityPlayer player) {
      World world = player.worldObj;
      if(world.getBlock(this.x, this.y, this.z) == ChocolateQuest.exporter) {
         BlockEditorTileEntity eb = (BlockEditorTileEntity)world.getTileEntity(this.x, this.y, this.z);
         eb.red = this.width;
         eb.yellow = this.length;
         eb.height = this.height;
         eb.name = this.text;
      }

      if(this.action == 1) {
         this.putEntitiesIntoSpawners(world, this.x, this.y, this.z, this.width, this.height, this.length);
         copy(world, this.x, this.y, this.z, this.width, this.height, this.length, this.text);
         this.spawnEntitiesFromSpawners(world);
      }

   }

   public void putEntitiesIntoSpawners(World world, int x, int y, int z, int sx, int sy, int sz) {
      List humans = world.getEntitiesWithinAABB(EntityHumanBase.class, AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)(x + sx), (double)(y + sy), (double)(z + sz)));
      this.coords = new ArrayList();
      Iterator i$ = humans.iterator();

      while(i$.hasNext()) {
         EntityHumanBase human = (EntityHumanBase)i$.next();
         if(!human.isDead) {
            if(human.party != null) {
               if(human.party.getLeader() == human) {
                  this.putEntityIntoSpawner(human);
               }
            } else {
               this.putEntityIntoSpawner(human);
            }
         }
      }

   }

   public void putEntityIntoSpawner(EntityHumanBase human) {
      int x = MathHelper.floor_double(human.posX);
      int y = MathHelper.floor_double(human.posY);
      int z = MathHelper.floor_double(human.posZ);
      this.coords.add(new Coords(x, y, z));
      ItemMobToSpawner.saveToSpawner(x, y, z, human);
   }

   public void spawnEntitiesFromSpawners(World world) {
      Iterator i$ = this.coords.iterator();

      while(i$.hasNext()) {
         Coords coord = (Coords)i$.next();
         TileEntity te = world.getTileEntity(coord.x, coord.y, coord.z);
         if(te instanceof BlockMobSpawnerTileEntity) {
            BlockMobSpawnerTileEntity teSpawner = (BlockMobSpawnerTileEntity)te;
            teSpawner.spawnEntity();
            world.setBlockToAir(coord.x, coord.y, coord.z);
         }
      }

   }

   public static void copy(World world, int i, int j, int k, int sx, int sy, int sz, String name) {
      ++i;
      ++k;
      Schematic data = new Schematic(sx, sy, sz, i, j, k, name);
      boolean cont = false;

      int file;
      for(int li = 0; li < sx; ++li) {
         for(file = 0; file < sy; ++file) {
            for(int e = 0; e < sz; ++e) {
               int x = li + i;
               int y = file + j;
               int z = e + k;
               data.setBlock(x, y, z, world.getBlock(x, y, z));
               data.setBlockMetadata(x, y, z, (byte)world.getBlockMetadata(x, y, z));
               TileEntity te = world.getTileEntity(x, y, z);
               if(te != null) {
                  data.addTileEntity(te);
               }
            }
         }
      }

      List var17 = world.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox((double)i, (double)j, (double)k, (double)(i + sx), (double)(j + sy), (double)(k + sz)));

      for(file = 0; file < var17.size(); ++file) {
         Entity var19 = (Entity)var17.get(file);
         if(!(var19 instanceof EntityPlayer) && !(var19 instanceof EntityBat)) {
            data.addEntity(var19);
         }
      }

      File var18 = new File(Minecraft.getMinecraft().mcDataDir, "config/Chocolate/DungeonConfig/Building/export/" + name + ".schematic");
      data.save(var18);
   }
}
