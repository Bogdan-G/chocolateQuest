package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import com.chocolate.chocolateQuest.packets.ChannelHandlerClient;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;

public class PacketAttackToXYZ implements IMessage, IMessageHandler<PacketAttackToXYZ, IMessage> {

   byte type;
   int entityID;
   double x;
   double y;
   double z;


   public PacketAttackToXYZ() {}

   public PacketAttackToXYZ(int entityID, byte animType, double x, double y, double z) {
      this.entityID = entityID;
      this.type = animType;
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public IMessage onMessage(PacketAttackToXYZ message, MessageContext ctx) {
      Entity entity = ChannelHandlerClient.getClientWorld().getEntityByID(message.entityID);
      if(entity instanceof EntityBaseBoss) {
         EntityBaseBoss g = (EntityBaseBoss)entity;
         g.attackToXYZ(message.type, message.x, message.y, message.z);
      }

      return null;
   }

   public void fromBytes(ByteBuf bytes) {
      this.type = bytes.readByte();
      this.entityID = bytes.readInt();
      this.x = bytes.readDouble();
      this.y = bytes.readDouble();
      this.z = bytes.readDouble();
   }

   public void toBytes(ByteBuf bytes) {
      bytes.writeByte(this.type);
      bytes.writeInt(this.entityID);
      bytes.writeDouble(this.x);
      bytes.writeDouble(this.y);
      bytes.writeDouble(this.z);
   }
}
