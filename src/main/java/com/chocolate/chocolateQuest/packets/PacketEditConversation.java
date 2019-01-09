package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.packets.ChannelHandlerClient;
import com.chocolate.chocolateQuest.packets.PacketBase;
import com.chocolate.chocolateQuest.quest.DialogOption;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class PacketEditConversation implements IMessage, IMessageHandler<PacketEditConversation, IMessage> {

   int npcID;
   EntityHumanNPC npc;
   DialogOption options;
   int[] breadCrumbs;


   public PacketEditConversation() {}

   public PacketEditConversation(EntityHumanNPC npc, int[] breadCrumbs) {
      this.npcID = npc.getEntityId();
      this.npc = npc;
      this.breadCrumbs = breadCrumbs;
   }

   public IMessage onMessage(PacketEditConversation message, MessageContext ctx) {
      message.execute(ChannelHandlerClient.getClientPlayer());
      return null;
   }

   public void execute(EntityPlayer player) {
      Entity entity = player.worldObj.getEntityByID(this.npcID);
      if(entity instanceof EntityHumanNPC) {
         this.npc = (EntityHumanNPC)entity;
         this.npc.conversation = this.options;
         player.openGui(ChocolateQuest.instance, 6, player.worldObj, this.npcID, 1, 0);
      }

   }

   public void executeServer(EntityPlayer player) {
      Entity entity = player.worldObj.getEntityByID(this.npcID);
      if(entity instanceof EntityHumanNPC) {
         this.npc = (EntityHumanNPC)entity;
         this.npc.conversation = this.options;
         this.npc.endConversation();
      }

   }

   public void fromBytes(ByteBuf bytes) {
      this.npcID = bytes.readInt();
      int length = bytes.readInt();
      this.breadCrumbs = new int[length];

      for(int tag = 0; tag < this.breadCrumbs.length; ++tag) {
         this.breadCrumbs[tag] = bytes.readInt();
      }

      NBTTagCompound var4 = PacketBase.readTag(bytes);
      this.options = new DialogOption();
      this.options.readFromNBT(var4);
   }

   public void toBytes(ByteBuf bytes) {
      bytes.writeInt(this.npcID);
      bytes.writeInt(this.breadCrumbs.length);
      int[] conversation = this.breadCrumbs;
      int tag = conversation.length;

      for(int i$ = 0; i$ < tag; ++i$) {
         int i = conversation[i$];
         bytes.writeInt(i);
      }

      DialogOption var6 = this.npc.conversation;
      NBTTagCompound var7 = new NBTTagCompound();
      var6.writeToNBT(var7);
      PacketBase.writeTag(bytes, var7);
   }
}
