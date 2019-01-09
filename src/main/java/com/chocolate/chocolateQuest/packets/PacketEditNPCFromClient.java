package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.packets.PacketEditNPC;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketEditNPCFromClient implements IMessageHandler<PacketEditNPC, IMessage> {

   public IMessage onMessage(PacketEditNPC message, MessageContext ctx) {
      EntityPlayerMP entityPlayer = ctx.getServerHandler().playerEntity;
      message.execute(entityPlayer.worldObj);
      return null;
   }
}
