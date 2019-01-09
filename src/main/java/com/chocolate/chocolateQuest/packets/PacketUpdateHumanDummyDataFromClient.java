package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.packets.PacketUpdateHumanDummyData;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketUpdateHumanDummyDataFromClient implements IMessageHandler<PacketUpdateHumanDummyData, IMessage> {

   public IMessage onMessage(PacketUpdateHumanDummyData message, MessageContext ctx) {
      EntityPlayerMP entityPlayer = ctx.getServerHandler().playerEntity;
      message.execute(entityPlayer.worldObj);
      return null;
   }
}
