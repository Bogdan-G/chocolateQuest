package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.packets.ChannelHandlerClient;
import com.chocolate.chocolateQuest.packets.PacketShieldBlock;
import com.chocolate.chocolateQuest.utils.PlayerManager;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PacketShieldBlockFromServer implements IMessage, IMessageHandler<PacketShieldBlockFromServer, IMessage> {

   int playerID;
   int targetID;
   float ammount;


   public PacketShieldBlockFromServer() {}

   public PacketShieldBlockFromServer(int playerID, int targetID, float ammount) {
      this.playerID = playerID;
      this.targetID = targetID;
      this.ammount = ammount;
   }

   public void fromBytes(ByteBuf inputStream) {
      this.playerID = inputStream.readInt();
      this.targetID = inputStream.readInt();
      this.ammount = inputStream.readFloat();
   }

   public void toBytes(ByteBuf outputStream) {
      outputStream.writeInt(this.playerID);
      outputStream.writeInt(this.targetID);
      outputStream.writeFloat(this.ammount);
   }

   public void execute(World world) {
      Entity e = world.getEntityByID(this.targetID);
      Entity e1 = world.getEntityByID(this.playerID);
      if(e instanceof EntityLivingBase && e1 instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)e1;
         ItemStack is = player.getCurrentEquippedItem();
         int useTime = player.getItemInUseDuration();
         if(useTime > 0 && useTime < 5) {
            player.swingItem();
            PacketShieldBlock packet = new PacketShieldBlock(this.playerID, this.targetID);
            ChocolateQuest.channel.sendPaquetToServer(packet);
         }

         PlayerManager.useStamina(player, this.ammount);
      }

   }

   public IMessage onMessage(PacketShieldBlockFromServer message, MessageContext ctx) {
      EntityPlayer entityPlayer = ChannelHandlerClient.getClientPlayer();
      message.execute(entityPlayer.worldObj);
      return null;
   }
}
