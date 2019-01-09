package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.packets.PacketUpdateShopRecipe;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketUpdateShopRecipeFromClient implements IMessageHandler<PacketUpdateShopRecipe, IMessage> {

   public IMessage onMessage(PacketUpdateShopRecipe message, MessageContext ctx) {
      EntityPlayerMP entityPlayer = ctx.getServerHandler().playerEntity;
      message.execute(entityPlayer);
      return null;
   }
}
