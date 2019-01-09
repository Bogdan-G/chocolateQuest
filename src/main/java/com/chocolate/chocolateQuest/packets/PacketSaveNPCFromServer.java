package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.gui.guinpc.GuiImportNPC;
import com.chocolate.chocolateQuest.packets.ChannelHandlerClient;
import com.chocolate.chocolateQuest.packets.PacketSaveNPC;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;

public class PacketSaveNPCFromServer implements IMessageHandler<PacketSaveNPC, IMessage> {

   public IMessage onMessage(PacketSaveNPC message, MessageContext ctx) {
      message.execute(ChannelHandlerClient.getClientWorld());
      NBTTagCompound tag = message.tag;
      EntityHumanNPC npc = message.npc;
      double x = npc.posX;
      double y = npc.posY;
      double z = npc.posZ;
      npc.readEntityFromSpawnerNBT(tag, MathHelper.floor_double(npc.posX), MathHelper.floor_double(npc.posY), MathHelper.floor_double(npc.posZ));
      npc.setPosition(x, y, z);
      GuiScreen screen = FMLClientHandler.instance().getClient().currentScreen;
      if(screen instanceof GuiImportNPC) {
         ((GuiImportNPC)screen).saveNPC(tag);
      }

      return null;
   }
}
