package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.packets.PacketEditConversation;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketEditConversationFromClient implements IMessageHandler<PacketEditConversation, IMessage> {

   public IMessage onMessage(PacketEditConversation message, MessageContext ctx) {
      EntityPlayerMP entityPlayer = ctx.getServerHandler().playerEntity;
      message.executeServer(entityPlayer);
      return null;
   }
}
