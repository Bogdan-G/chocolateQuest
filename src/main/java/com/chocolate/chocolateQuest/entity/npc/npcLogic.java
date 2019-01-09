package com.chocolate.chocolateQuest.entity.npc;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import io.netty.buffer.ByteBuf;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

class npcLogic extends CommandBlockLogic {

   EntityHumanNPC owner;


   public npcLogic(EntityHumanNPC owner) {
      this.owner = owner;
   }

   public ChunkCoordinates getPlayerCoordinates() {
      return this.owner.getHomePosition();
   }

   public World getEntityWorld() {
      return this.owner.worldObj;
   }

   public void func_145756_e() {}

   public int func_145751_f() {
      return 0;
   }

   public void func_145757_a(ByteBuf buff) {}

   public void addChatMessage(IChatComponent p_145747_1_) {
      if(this.owner.playerSpeakingTo != null) {
         this.owner.playerSpeakingTo.addChatMessage(p_145747_1_);
      } else {
         super.addChatMessage(p_145747_1_);
      }

   }

   public boolean canCommandSenderUseCommand(int aint, String aString) {
      return aint <= 4;
   }
}
