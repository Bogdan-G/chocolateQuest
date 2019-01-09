package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.packets.PacketSaveNPC;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketSaveNPCFromClient implements IMessageHandler<PacketSaveNPC, IMessage> {

   public IMessage onMessage(PacketSaveNPC message, MessageContext ctx) {
      EntityPlayerMP entityPlayer = ctx.getServerHandler().playerEntity;
      message.execute(entityPlayer.worldObj);
      message.tag = null;
      ChocolateQuest.channel.sendToPlayer(message, (EntityPlayerMP)entityPlayer);
      return null;
   }
}
