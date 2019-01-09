package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.packets.PacketBase;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class PacketEditNPC implements IMessage {

   EntityHumanNPC npc;
   int npcID;
   NBTTagCompound tag;
   byte type = 0;
   public static final byte CLIENT_UPDATE = 0;
   public static final byte EDIT_NPC = 1;
   public static final byte EDIT_AI = 2;


   public PacketEditNPC() {}

   public PacketEditNPC(EntityHumanNPC npc, byte type) {
      this.type = type;
      this.npc = npc;
      this.npcID = npc.getEntityId();
   }

   public PacketEditNPC(EntityHumanNPC npc, NBTTagCompound tag, byte type) {
      this.type = type;
      this.npc = npc;
      this.npcID = npc.getEntityId();
      this.tag = tag;
   }

   public void fromBytes(ByteBuf buf) {
      this.type = buf.readByte();
      this.npcID = buf.readInt();
      this.tag = PacketBase.readTag(buf);
   }

   public void toBytes(ByteBuf buf) {
      buf.writeByte(this.type);
      buf.writeInt(this.npcID);
      if(this.tag == null) {
         this.tag = new NBTTagCompound();
         this.npc.writeStats(this.tag, true);
      }

      PacketBase.writeTag(buf, this.tag);
   }

   public void execute(World worldObj) {
      Entity e = worldObj.getEntityByID(this.npcID);
      if(e instanceof EntityHumanNPC) {
         this.npc = (EntityHumanNPC)e;
         this.npc.loadFromTag(this.tag);
         if(!worldObj.isRemote) {
            this.npc.endConversation();
         }
      }

   }
}
