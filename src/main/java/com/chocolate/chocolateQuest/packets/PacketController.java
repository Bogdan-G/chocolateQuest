package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.items.mobControl.ItemController;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class PacketController implements IMessage, IMessageHandler<PacketController, IMessage> {

   byte type;


   public PacketController() {}

   public PacketController(byte type) {
      this.type = type;
   }

   public IMessage onMessage(PacketController message, MessageContext ctx) {
      EntityPlayerMP entityPlayer = ctx.getServerHandler().playerEntity;
      message.execute(entityPlayer);
      return null;
   }

   public void execute(EntityPlayer player) {
      ItemStack stack = player.getCurrentEquippedItem();
      if(stack != null && stack.getItem() instanceof ItemController) {
         switch(this.type) {
         case 0:
            ((ItemController)ChocolateQuest.controller).setMode(stack, this.type);
            break;
         case 1:
            ((ItemController)ChocolateQuest.controller).setMode(stack, this.type);
            break;
         case 2:
            ((ItemController)ChocolateQuest.controller).setMode(stack, this.type);
            break;
         case 3:
            if(stack.stackTagCompound != null) {
               stack.stackTagCompound.removeTag("Entities");
            }

            stack.setItemDamage(0);
         }
      }

   }

   public void fromBytes(ByteBuf bytes) {
      this.type = bytes.readByte();
   }

   public void toBytes(ByteBuf bytes) {
      bytes.writeByte(this.type);
   }
}
