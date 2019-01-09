package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.gui.guinpc.GuiAIPositions;
import com.chocolate.chocolateQuest.gui.guinpc.GuiEditNpc;
import com.chocolate.chocolateQuest.packets.ChannelHandlerClient;
import com.chocolate.chocolateQuest.packets.PacketEditNPC;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;

public class PacketEditNPCFromServer implements IMessageHandler<PacketEditNPC, IMessage> {

   public IMessage onMessage(PacketEditNPC message, MessageContext ctx) {
      message.execute(ChannelHandlerClient.getClientWorld());
      this.test(message);
      return null;
   }

   @SideOnly(Side.CLIENT)
   public void test(PacketEditNPC message) {
      if(message.npc != null) {
         if(message.type == 1) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiEditNpc(message.npc));
         } else if(message.type == 2) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiAIPositions(message.npc));
         }
      }

   }
}
