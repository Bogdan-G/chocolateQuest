package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.packets.ChannelHandlerClient;
import com.chocolate.chocolateQuest.packets.PacketUpdateShopRecipe;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;

public class PacketUpdateShopRecipeFromServer implements IMessageHandler<PacketUpdateShopRecipe, IMessage> {

   public IMessage onMessage(PacketUpdateShopRecipe message, MessageContext ctx) {
      EntityPlayer entityPlayer = ChannelHandlerClient.getClientPlayer();
      message.execute(entityPlayer);
      return null;
   }
}
