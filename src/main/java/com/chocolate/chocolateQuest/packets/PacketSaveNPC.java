package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.packets.PacketBase;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class PacketSaveNPC implements IMessage {

   EntityHumanNPC npc;
   int npcID;
   NBTTagCompound tag;
   static String fileName = null;


   public PacketSaveNPC() {}

   public PacketSaveNPC(EntityHumanNPC npc, String fileName) {
      this.npc = npc;
      this.npcID = npc.getEntityId();
   }

   public PacketSaveNPC(EntityHumanNPC npc, NBTTagCompound tag) {
      this.npc = npc;
      this.npcID = npc.getEntityId();
      this.tag = tag;
   }

   public void fromBytes(ByteBuf buf) {
      this.npcID = buf.readInt();
      this.tag = PacketBase.readTag(buf);
   }

   public void toBytes(ByteBuf buf) {
      buf.writeInt(this.npcID);
      if(this.tag == null) {
         this.tag = new NBTTagCompound();
         this.npc.writeEntityToSpawnerNBT(this.tag, MathHelper.floor_double(this.npc.posX), MathHelper.floor_double(this.npc.posY), MathHelper.floor_double(this.npc.posZ));
      }

      PacketBase.writeTag(buf, this.tag);
   }

   public void execute(World worldObj) {
      Entity e = worldObj.getEntityByID(this.npcID);
      if(e instanceof EntityHumanNPC) {
         this.npc = (EntityHumanNPC)e;
      }

   }

}
