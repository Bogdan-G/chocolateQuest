package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.magic.Awakements;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PacketShieldBlock implements IMessage, IMessageHandler<PacketShieldBlock, IMessage> {

   int playerID;
   int targetID;


   public PacketShieldBlock() {}

   public PacketShieldBlock(int playerID, int targetID) {
      this.playerID = playerID;
      this.targetID = targetID;
   }

   public void fromBytes(ByteBuf inputStream) {
      this.playerID = inputStream.readInt();
      this.targetID = inputStream.readInt();
   }

   public void toBytes(ByteBuf outputStream) {
      outputStream.writeInt(this.playerID);
      outputStream.writeInt(this.targetID);
   }

   public void execute(World world) {
      Entity e = world.getEntityByID(this.targetID);
      Entity e1 = world.getEntityByID(this.playerID);
      if(e instanceof EntityLivingBase && e1 instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)e1;
         ItemStack is = player.getCurrentEquippedItem();
         double damage = (double)(3 + Awakements.getEnchantLevel(is, Awakements.parryDamage) * 2);
         AttributeModifier attack = new AttributeModifier("parryMod", damage, 0);
         player.getEntityAttribute(SharedMonsterAttributes.attackDamage).applyModifier(attack);
         player.attackTargetEntityWithCurrentItem(e);
         player.getEntityAttribute(SharedMonsterAttributes.attackDamage).removeModifier(attack);
      }

   }

   public IMessage onMessage(PacketShieldBlock message, MessageContext ctx) {
      EntityPlayerMP entityPlayer = ctx.getServerHandler().playerEntity;
      message.execute(entityPlayer.worldObj);
      return null;
   }
}
