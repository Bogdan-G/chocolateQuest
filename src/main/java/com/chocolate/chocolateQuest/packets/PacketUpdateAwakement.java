package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.gui.guinpc.ContainerAwakement;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketUpdateAwakement implements IMessage, IMessageHandler<PacketUpdateAwakement, IMessage> {

   int awakementID;


   public PacketUpdateAwakement() {}

   public PacketUpdateAwakement(int awakementID) {
      this.awakementID = awakementID;
   }

   public IMessage onMessage(PacketUpdateAwakement message, MessageContext ctx) {
      EntityPlayerMP entityPlayer = ctx.getServerHandler().playerEntity;
      message.execute(entityPlayer);
      return null;
   }

   public void execute(EntityPlayer player) {
      if(player.openContainer instanceof ContainerAwakement) {
         ((ContainerAwakement)player.openContainer).enchantItem(this.awakementID);
      }

   }

   public void fromBytes(ByteBuf bytes) {
      this.awakementID = bytes.readInt();
   }

   public void toBytes(ByteBuf bytes) {
      bytes.writeInt(this.awakementID);
   }
}
