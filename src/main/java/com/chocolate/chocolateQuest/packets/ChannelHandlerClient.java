package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.packets.ChannelHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ChannelHandlerClient extends ChannelHandler {

   static World getClientWorld() {
      return Minecraft.getMinecraft().theWorld;
   }

   static EntityPlayer getClientPlayer() {
      return Minecraft.getMinecraft().thePlayer;
   }
}
