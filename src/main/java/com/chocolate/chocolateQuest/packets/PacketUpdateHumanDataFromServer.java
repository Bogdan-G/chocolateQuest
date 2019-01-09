package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.packets.ChannelHandlerClient;
import com.chocolate.chocolateQuest.packets.PacketUpdateHumanData;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.world.World;

public class PacketUpdateHumanDataFromServer implements IMessageHandler<PacketUpdateHumanData, IMessage> {

   public IMessage onMessage(PacketUpdateHumanData message, MessageContext ctx) {
      World world = ChannelHandlerClient.getClientWorld();
      message.execute(world);
      return null;
   }
}
