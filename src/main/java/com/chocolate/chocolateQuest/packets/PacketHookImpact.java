package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.entity.projectile.EntityHookShoot;
import com.chocolate.chocolateQuest.packets.ChannelHandlerClient;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class PacketHookImpact implements IMessage, IMessageHandler<PacketHookImpact, IMessage> {

   byte type;
   int hookID;
   int entityID;
   int posX;
   int posY;
   int posZ;
   int angle;
   int distance;
   int height;
   double hitX;
   double hitY;
   double hitZ;
   public static final byte ENTITY = 0;
   public static final byte BLOCK = 1;
   int offsetScale = 100000;


   public PacketHookImpact() {}

   public PacketHookImpact(int hookID, int entityID, double angle, double dist, double height) {
      this.type = 0;
      this.hookID = hookID;
      this.entityID = entityID;
      this.angle = (int)angle;
      this.distance = (int)(dist * (double)this.offsetScale);
      this.height = (int)(height * (double)this.offsetScale);
   }

   public PacketHookImpact(int hookID, int posX, int posY, int posZ, double hitX, double hitY, double hitZ) {
      this.type = 1;
      this.hookID = hookID;
      this.posX = posX;
      this.posY = posY;
      this.posZ = posZ;
   }

   public IMessage onMessage(PacketHookImpact message, MessageContext ctx) {
      World world = ChannelHandlerClient.getClientWorld();
      message.execute(world);
      return null;
   }

   public void execute(World world) {
      Entity entity = world.getEntityByID(this.hookID);
      if(entity instanceof EntityHookShoot) {
         EntityHookShoot hook = (EntityHookShoot)entity;
         switch(this.type) {
         case 0:
            entity = world.getEntityByID(this.entityID);
            if(entity != null) {
               double offX = (double)this.angle;
               double distance = (double)this.distance / (double)this.offsetScale;
               double height = (double)(this.height / this.offsetScale);
               hook.hookedEntity = entity;
               hook.hookedAtHeight = height;
               hook.hookedAtAngle = (double)this.angle;
               hook.hookedAtDistance = distance;
            }
            break;
         case 1:
            hook.blockX = this.posX;
            hook.blockY = this.posY;
            hook.blockZ = this.posZ;
            hook.setPosition(this.hitX, this.hitY, this.hitZ);
         }

         hook.onImpact();
      }

   }

   public void fromBytes(ByteBuf bytes) {
      this.type = bytes.readByte();
      this.hookID = bytes.readInt();
      switch(this.type) {
      case 0:
         this.entityID = bytes.readInt();
         this.angle = bytes.readInt();
         this.distance = bytes.readInt();
         this.height = bytes.readInt();
         break;
      case 1:
         this.posX = bytes.readInt();
         this.posY = bytes.readInt();
         this.posZ = bytes.readInt();
         this.hitX = bytes.readDouble();
         this.hitY = bytes.readDouble();
         this.hitZ = bytes.readDouble();
      }

   }

   public void toBytes(ByteBuf bytes) {
      bytes.writeByte(this.type);
      bytes.writeInt(this.hookID);
      switch(this.type) {
      case 0:
         bytes.writeInt(this.entityID);
         bytes.writeInt(this.angle);
         bytes.writeInt(this.distance);
         bytes.writeInt(this.height);
         break;
      case 1:
         bytes.writeInt(this.posX);
         bytes.writeInt(this.posY);
         bytes.writeInt(this.posZ);
         bytes.writeDouble(this.hitX);
         bytes.writeDouble(this.hitY);
         bytes.writeDouble(this.hitZ);
      }

   }
}
