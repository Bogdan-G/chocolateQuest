package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import com.chocolate.chocolateQuest.packets.ChannelHandlerClient;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class PacketEntityAnimation implements IMessage, IMessageHandler<PacketEntityAnimation, IMessage> {

   byte type;
   int entityID;
   public static final byte SWING_ITEM = 0;
   public static final byte LEFT_HAND_SWING = 1;
   public static final byte AIM_RIGHT = 2;
   public static final byte AIM_LEFT = 3;


   public PacketEntityAnimation() {}

   public PacketEntityAnimation(int entityID, byte animType) {
      this.entityID = entityID;
      this.type = animType;
   }

   public IMessage onMessage(PacketEntityAnimation message, MessageContext ctx) {
      World world = ChannelHandlerClient.getClientWorld();
      message.execute(world);
      return null;
   }

   public void execute(World world) {
      Entity entity = world.getEntityByID(this.entityID);
      if(entity != null) {
         if(this.type == 0) {
            if(entity instanceof EntityLivingBase) {
               ((EntityLivingBase)entity).swingItem();
            }

            return;
         }

         if(entity instanceof EntityHumanBase) {
            switch(this.type) {
            case 1:
               ((EntityHumanBase)entity).swingLeftHand();
               return;
            case 2:
               ((EntityHumanBase)entity).toogleAimRight();
               return;
            case 3:
               ((EntityHumanBase)entity).toogleAimLeft();
               return;
            }
         }

         if(entity instanceof EntityBaseBoss) {
            ((EntityBaseBoss)entity).animationBoss(this.type);
            return;
         }
      }

   }

   public void fromBytes(ByteBuf bytes) {
      this.type = bytes.readByte();
      this.entityID = bytes.readInt();
   }

   public void toBytes(ByteBuf bytes) {
      bytes.writeByte(this.type);
      bytes.writeInt(this.entityID);
   }
}
