package com.chocolate.chocolateQuest.client;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.client.KeyBindings;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import com.chocolate.chocolateQuest.packets.PacketClientAsks;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;

public class KeyInputHandler {

   @SubscribeEvent
   public void onKeyInput(KeyInputEvent event) {
      EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
      if(KeyBindings.partyKey.isPressed()) {
         player.openGui(ChocolateQuest.instance, 9, player.worldObj, 0, 0, 0);
      }

      PacketClientAsks packet;
      if(KeyBindings.nextSpell.isPressed()) {
         packet = new PacketClientAsks((byte)1);
         ChocolateQuest.channel.sendPaquetToServer(packet);
         if(player.ridingEntity != null && player.ridingEntity instanceof EntityGolemMecha) {
            ((EntityGolemMecha)player.ridingEntity).leftClick(false);
            return;
         }
      }

      if(KeyBindings.prevSpell.isPressed()) {
         packet = new PacketClientAsks((byte)2);
         ChocolateQuest.channel.sendPaquetToServer(packet);
         if(player.ridingEntity != null && player.ridingEntity instanceof EntityGolemMecha) {
            ((EntityGolemMecha)player.ridingEntity).rightClick(false);
            return;
         }
      }

   }
}
