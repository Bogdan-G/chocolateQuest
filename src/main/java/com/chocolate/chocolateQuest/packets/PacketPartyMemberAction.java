package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.gui.guiParty.PartyAction;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class PacketPartyMemberAction implements IMessage, IMessageHandler<PacketPartyMemberAction, IMessage> {

   private byte actionID;
   int id;
   PartyAction action;
   public static final byte MOVE = 0;
   MovingObjectPosition playerMop;
   EntityPlayer player;


   public PacketPartyMemberAction() {}

   public PacketPartyMemberAction(EntityHumanBase e, PartyAction action, MovingObjectPosition playerMop, EntityPlayer player) {
      this.id = e.getEntityId();
      this.action = action;
      this.actionID = action.id;
      this.playerMop = playerMop;
      this.player = player;
   }

   public void fromBytes(ByteBuf inputStream) {
      this.id = inputStream.readInt();
      this.actionID = inputStream.readByte();
      this.action = (PartyAction)PartyAction.actions.get(this.actionID);
      if(this.action != null) {
         this.action.read(inputStream);
      }

   }

   public void toBytes(ByteBuf outputStream) {
      outputStream.writeInt(this.id);
      outputStream.writeByte(this.actionID);
      this.action.write(outputStream, this.playerMop, this.player);
   }

   public void execute(World world) {
      Entity e = world.getEntityByID(this.id);
      if(e instanceof EntityHumanBase && this.action != null) {
         this.action.execute((EntityHumanBase)e);
      }

   }

   public IMessage onMessage(PacketPartyMemberAction message, MessageContext ctx) {
      EntityPlayerMP entityPlayer = ctx.getServerHandler().playerEntity;
      message.execute(entityPlayer.worldObj);
      return null;
   }
}
