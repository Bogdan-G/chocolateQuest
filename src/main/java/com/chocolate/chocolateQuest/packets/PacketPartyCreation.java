package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class PacketPartyCreation implements IMessage, IMessageHandler<PacketPartyCreation, IMessage> {

   int leaderId;
   int[] mobIds;


   public PacketPartyCreation() {}

   public PacketPartyCreation(int leaderID, int[] ids) {
      this.mobIds = ids;
      this.leaderId = leaderID;
   }

   public void fromBytes(ByteBuf inputStream) {
      this.leaderId = inputStream.readInt();
      int size = inputStream.readInt();
      this.mobIds = new int[size];

      for(int i = 0; i < size; ++i) {
         this.mobIds[i] = inputStream.readInt();
      }

   }

   public void toBytes(ByteBuf outputStream) {
      outputStream.writeInt(this.leaderId);
      outputStream.writeInt(this.mobIds.length);

      for(int i = 0; i < this.mobIds.length; ++i) {
         outputStream.writeInt(this.mobIds[i]);
      }

   }

   public void execute(World world) {
      Entity e = world.getEntityByID(this.leaderId);
      if(e instanceof EntityHumanBase) {
         EntityHumanBase leader = (EntityHumanBase)e;

         for(int i = 0; i < this.mobIds.length; ++i) {
            e = world.getEntityByID(this.mobIds[i]);
            if(e instanceof EntityHumanBase) {
               leader.tryPutIntoPArty((EntityHumanBase)e);
            }
         }
      }

   }

   public IMessage onMessage(PacketPartyCreation message, MessageContext ctx) {
      EntityPlayerMP entityPlayer = ctx.getServerHandler().playerEntity;
      message.execute(entityPlayer.worldObj);
      return null;
   }
}
