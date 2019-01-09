package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.gui.guinpc.GuiNPC;
import com.chocolate.chocolateQuest.packets.ChannelHandlerClient;
import com.chocolate.chocolateQuest.packets.PacketBase;
import com.chocolate.chocolateQuest.quest.DialogCondition;
import com.chocolate.chocolateQuest.quest.DialogOption;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class PacketStartConversation implements IMessage, IMessageHandler<PacketStartConversation, IMessage> {

   int npcID;
   EntityHumanNPC npc;
   EntityPlayer playerSpeakingTo;
   DialogOption options;
   int[] breadCrumbs;


   public PacketStartConversation() {}

   public PacketStartConversation(EntityHumanNPC npc, int[] breadCrumbs, EntityPlayer playerSpeakingTo) {
      this.npcID = npc.getEntityId();
      this.npc = npc;
      this.breadCrumbs = breadCrumbs;
      this.playerSpeakingTo = playerSpeakingTo;
   }

   public IMessage onMessage(PacketStartConversation message, MessageContext ctx) {
      message.execute(ChannelHandlerClient.getClientPlayer());
      return null;
   }

   public void execute(EntityPlayer player) {
      Entity entity = player.worldObj.getEntityByID(this.npcID);
      if(entity instanceof EntityHumanNPC) {
         this.npc = (EntityHumanNPC)entity;
         if(GuiNPC.instance != null && this.breadCrumbs.length != 0) {
            GuiNPC.instance.setDialogOption(this.options, this.breadCrumbs);
         } else if(this.breadCrumbs.length == 0) {
            this.npc.conversation = this.options;
            player.openGui(ChocolateQuest.instance, 6, player.worldObj, this.npcID, 0, 0);
         }
      }

   }

   public void fromBytes(ByteBuf bytes) {
      this.npcID = bytes.readInt();
      this.options = this.readOption(bytes);
      int length = bytes.readInt();
      this.breadCrumbs = new int[length];

      for(int i = 0; i < this.breadCrumbs.length; ++i) {
         this.breadCrumbs[i] = bytes.readInt();
      }

   }

   public void toBytes(ByteBuf bytes) {
      bytes.writeInt(this.npcID);
      DialogOption conversation = this.npc.getDialogOption(this.breadCrumbs);
      this.writeOption(bytes, conversation);
      bytes.writeInt(this.breadCrumbs.length);
      int[] arr$ = this.breadCrumbs;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         int i = arr$[i$];
         bytes.writeInt(i);
      }

   }

   public void writeOption(ByteBuf bytes, DialogOption mainOption) {
      bytes.writeInt(mainOption.id);
      PacketBase.writeString(bytes, mainOption.folder);
      PacketBase.writeString(bytes, mainOption.name);
      if(mainOption.options != null) {
         ArrayList list = new ArrayList();
         DialogOption[] i$ = mainOption.options;
         int option = i$.length;

         for(int i$1 = 0; i$1 < option; ++i$1) {
            DialogOption option1 = i$[i$1];
            boolean shouldOptionPass = true;
            if(option1.conditions != null) {
               Iterator i$2 = option1.conditions.iterator();

               while(i$2.hasNext()) {
                  DialogCondition condition = (DialogCondition)i$2.next();
                  if(!condition.matches(this.playerSpeakingTo, this.npc)) {
                     shouldOptionPass = false;
                     break;
                  }
               }
            }

            if(shouldOptionPass) {
               list.add(option1);
            }
         }

         bytes.writeInt(list.size());
         Iterator var11 = list.iterator();

         while(var11.hasNext()) {
            DialogOption var12 = (DialogOption)var11.next();
            bytes.writeInt(var12.id);
            PacketBase.writeString(bytes, var12.folder);
            PacketBase.writeString(bytes, var12.name);
         }
      } else {
         bytes.writeInt(0);
      }

   }

   public DialogOption readOption(ByteBuf bytes) {
      DialogOption option = new DialogOption();
      option.id = bytes.readInt();
      option.folder = PacketBase.readString(bytes);
      option.name = PacketBase.readString(bytes);
      int length = bytes.readInt();
      if(length > 0) {
         DialogOption[] options = new DialogOption[length];

         for(int i = 0; i < options.length; ++i) {
            options[i] = new DialogOption();
            options[i].id = bytes.readInt();
            options[i].folder = PacketBase.readString(bytes);
            options[i].name = PacketBase.readString(bytes);
         }

         option.options = options;
      }

      return option;
   }
}
