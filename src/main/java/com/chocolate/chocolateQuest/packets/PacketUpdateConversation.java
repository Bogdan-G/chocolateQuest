package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketUpdateConversation implements IMessage, IMessageHandler<PacketUpdateConversation, IMessage> {

   public static final int END_CONVERSATION = 1;
   public static final int EDIT_CONVERSATION = 2;
   public static final int EDIT_NPC = 3;
   public static final int INVENTORY = 4;
   public static final int EDIT_AI = 5;
   int[] step;
   int npc;
   byte type;


   public PacketUpdateConversation() {}

   public PacketUpdateConversation(int actionType, EntityHumanBase npc) {
      this.type = (byte)actionType;
      this.npc = npc.getEntityId();
      this.step = new int[0];
   }

   public PacketUpdateConversation(int[] step, EntityHumanBase npc) {
      this.step = step;
      this.npc = npc.getEntityId();
   }

   public IMessage onMessage(PacketUpdateConversation message, MessageContext ctx) {
      EntityPlayerMP entityPlayer = ctx.getServerHandler().playerEntity;
      message.execute(entityPlayer);
      return null;
   }

   public void execute(EntityPlayer player) {
      Entity e = player.worldObj.getEntityByID(this.npc);
      if(e instanceof EntityHumanNPC) {
         EntityHumanNPC npc = (EntityHumanNPC)e;
         switch(this.type) {
         case 1:
            npc.endConversation();
            return;
         case 2:
            npc.editConversation(player);
            return;
         case 3:
            npc.editNPC(player);
            return;
         case 4:
            npc.openEquipement(player);
            return;
         case 5:
            npc.editAI(player);
            return;
         default:
            npc.updateConversation(player, this.step);
         }
      }

   }

   public void fromBytes(ByteBuf bytes) {
      this.npc = bytes.readInt();
      this.type = bytes.readByte();
      int length = bytes.readInt();
      this.step = new int[length];

      for(int i = 0; i < length; ++i) {
         this.step[i] = bytes.readInt();
      }

   }

   public void toBytes(ByteBuf bytes) {
      bytes.writeInt(this.npc);
      bytes.writeByte(this.type);
      bytes.writeInt(this.step.length);

      for(int i = 0; i < this.step.length; ++i) {
         bytes.writeInt(this.step[i]);
      }

   }
}
