package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.packets.ChannelHandlerClient;
import com.chocolate.chocolateQuest.packets.PacketUpdateHumanDummyData;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.world.World;

public class PacketUpdateHumanDummyDataFromServer implements IMessageHandler<PacketUpdateHumanDummyData, IMessage> {

   public IMessage onMessage(PacketUpdateHumanDummyData message, MessageContext ctx) {
      World world = ChannelHandlerClient.getClientWorld();
      message.execute(world);
      return null;
   }
}
