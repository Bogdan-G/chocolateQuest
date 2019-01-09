package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class PacketUpdateHumanData implements IMessage {

   int distance;
   int angle;
   int id;
   int aiMode;
   int aiCombatMode;
   boolean updateOwner;
   EntityHumanBase human;


   public PacketUpdateHumanData() {}

   public PacketUpdateHumanData(EntityHumanBase e) {
      this.id = e.getEntityId();
      this.angle = e.partyPositionAngle;
      this.distance = e.partyDistanceToLeader;
      this.aiMode = e.AIMode;
      this.aiCombatMode = e.AICombatMode;
      this.updateOwner = e.updateOwner;
   }

   public void fromBytes(ByteBuf inputStream) {
      this.id = inputStream.readInt();
      this.angle = inputStream.readInt();
      this.distance = inputStream.readInt();
      this.aiMode = inputStream.readInt();
      this.aiCombatMode = inputStream.readInt();
      this.updateOwner = inputStream.readBoolean();
   }

   public void toBytes(ByteBuf outputStream) {
      outputStream.writeInt(this.id);
      outputStream.writeInt(this.angle);
      outputStream.writeInt(this.distance);
      outputStream.writeInt(this.aiMode);
      outputStream.writeInt(this.aiCombatMode);
      outputStream.writeBoolean(this.updateOwner);
   }

   public void execute(World world) {
      Entity e = world.getEntityByID(this.id);
      if(e instanceof EntityHumanBase) {
         this.human = (EntityHumanBase)e;
         this.human.partyPositionAngle = this.angle;
         this.human.partyDistanceToLeader = this.distance;
         this.human.partyPositionPersistance = true;
         this.human.AIMode = this.aiMode;
         this.human.AICombatMode = this.aiCombatMode;
         this.human.setAIForCurrentMode();
         if(this.human.playerSpeakingTo != null && this.updateOwner) {
            if(this.human.getOwner() == this.human.playerSpeakingTo) {
               this.human.setOwner((EntityLivingBase)null, true);
            } else {
               this.human.setOwner(this.human.playerSpeakingTo);
            }
         }

         this.human.updateOwner = this.updateOwner;
         this.human.playerSpeakingTo = null;
      }

   }
}
