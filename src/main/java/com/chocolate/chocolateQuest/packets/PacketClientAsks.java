package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.entity.projectile.EntityHookShoot;
import com.chocolate.chocolateQuest.gui.InventoryBag;
import com.chocolate.chocolateQuest.items.IHookLauncher;
import com.chocolate.chocolateQuest.items.gun.ILoadableGun;
import com.chocolate.chocolateQuest.utils.AIPosition;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class PacketClientAsks implements IMessage, IMessageHandler<PacketClientAsks, IMessage> {

   public static final byte OPEN_STAFF_GUI = 0;
   public static final byte NEXT = 1;
   public static final byte PREV = 2;
   public static final byte NEXT_RELEASE = 3;
   public static final byte PREV_RELEASE = 4;
   public static final byte GET_POSITION_MARKER = 5;
   public static final byte GET_SPAWN_BOTTLE = 6;
   byte eventID;
   int entityID;
   int data;


   public PacketClientAsks() {}

   public PacketClientAsks(byte eventID) {
      this.eventID = eventID;
   }

   public PacketClientAsks(byte eventID, int npcID, int data) {
      this(eventID);
      this.entityID = npcID;
      this.data = data;
   }

   public void fromBytes(ByteBuf inputStream) {
      this.eventID = inputStream.readByte();
      if(this.eventID == 5 || this.eventID == 6) {
         this.entityID = inputStream.readInt();
         this.data = inputStream.readInt();
      }

   }

   public void toBytes(ByteBuf outputStream) {
      outputStream.writeByte(this.eventID);
      if(this.eventID == 5 || this.eventID == 6) {
         outputStream.writeInt(this.entityID);
         outputStream.writeInt(this.data);
      }

   }

   public void execute(EntityPlayer player) {
      switch(this.eventID) {
      case 0:
         player.openGui(ChocolateQuest.instance, 3, player.worldObj, 0, 0, 0);
         break;
      case 1:
         this.nextPress(player);
         break;
      case 2:
         this.prevPress(player);
         break;
      case 3:
         this.nextRelease(player);
         break;
      case 4:
         this.prevRelease(player);
         break;
      case 5:
         this.dropPositionMarket(player);
      }

   }

   public IMessage onMessage(PacketClientAsks message, MessageContext ctx) {
      EntityPlayerMP entityPlayer = ctx.getServerHandler().playerEntity;
      message.execute(entityPlayer);
      return null;
   }

   public void nextPress(EntityPlayer player) {
      if(player.ridingEntity != null && player.ridingEntity instanceof EntityGolemMecha) {
         ((EntityGolemMecha)player.ridingEntity).leftClick(false);
      } else {
         ItemStack itemstack = player.getEquipmentInSlot(0);
         if(itemstack != null) {
            if(itemstack.getItem() instanceof ILoadableGun) {
               ItemStack[] id = InventoryBag.getCargo(itemstack);
               ItemStack[] e = new ItemStack[id.length];
               int last = id.length - 1;

               ItemStack firstItem;
               for(firstItem = id[last]; firstItem == null && last > 1; firstItem = id[last]) {
                  --last;
               }

               for(int i = last - 1; i >= 0; --i) {
                  if(id[i] != null) {
                     e[i + 1] = id[i];
                  }
               }

               e[0] = firstItem;
               InventoryBag.saveCargo(itemstack, e);
            } else if(itemstack.getItem() instanceof IHookLauncher) {
               int var8 = ((IHookLauncher)itemstack.getItem()).getHookID(itemstack);
               Entity var9 = player.worldObj.getEntityByID(var8);
               if(var9 instanceof EntityHookShoot) {
                  ((EntityHookShoot)var9).reel(1);
               }
            }
         }

      }
   }

   public void prevPress(EntityPlayer player) {
      if(player.ridingEntity != null && player.ridingEntity instanceof EntityGolemMecha) {
         ((EntityGolemMecha)player.ridingEntity).rightClick(false);
      } else {
         ItemStack itemstack = player.getEquipmentInSlot(0);
         if(itemstack != null) {
            if(itemstack.getItem() instanceof ILoadableGun) {
               ItemStack[] id = InventoryBag.getCargo(itemstack);
               ItemStack[] e = new ItemStack[id.length];
               int last = 0;

               ItemStack lastItem;
               for(lastItem = id[0]; lastItem == null && last < id.length - 1; lastItem = id[last]) {
                  ++last;
               }

               for(int i = last + 1; i < id.length; ++i) {
                  if(id[i] != null) {
                     e[last] = id[i];
                     ++last;
                  }
               }

               e[last] = lastItem;
               InventoryBag.saveCargo(itemstack, e);
            } else if(itemstack.getItem() instanceof IHookLauncher) {
               int var8 = ((IHookLauncher)itemstack.getItem()).getHookID(itemstack);
               Entity var9 = player.worldObj.getEntityByID(var8);
               if(var9 instanceof EntityHookShoot) {
                  ((EntityHookShoot)var9).reel(-1);
               }
            }
         }

      }
   }

   public void nextRelease(EntityPlayer player) {
      ItemStack itemstack = player.getEquipmentInSlot(0);
      if(itemstack != null && itemstack.getItem() instanceof IHookLauncher) {
         int id = ((IHookLauncher)itemstack.getItem()).getHookID(itemstack);
         Entity e = player.worldObj.getEntityByID(id);
         if(e instanceof EntityHookShoot) {
            ((EntityHookShoot)e).reel(0);
         }
      }

   }

   public void prevRelease(EntityPlayer player) {
      ItemStack itemstack = player.getEquipmentInSlot(0);
      if(itemstack != null && itemstack.getItem() instanceof IHookLauncher) {
         int id = ((IHookLauncher)itemstack.getItem()).getHookID(itemstack);
         Entity e = player.worldObj.getEntityByID(id);
         if(e instanceof EntityHookShoot) {
            ((EntityHookShoot)e).reel(0);
         }
      }

   }

   public void dropPositionMarket(EntityPlayer player) {
      EntityHumanNPC npc = (EntityHumanNPC)player.worldObj.getEntityByID(this.entityID);
      AIPosition position = (AIPosition)npc.AIPositions.get(this.data);
      ItemStack is = new ItemStack(ChocolateQuest.pointMarker);
      ChocolateQuest.pointMarker.addPoint(is, position);
      EntityItem e = new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, is);
      player.worldObj.spawnEntityInWorld(e);
   }
}
