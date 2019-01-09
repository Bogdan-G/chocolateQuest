package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.packets.PacketAttackToXYZ;
import com.chocolate.chocolateQuest.packets.PacketClientAsks;
import com.chocolate.chocolateQuest.packets.PacketController;
import com.chocolate.chocolateQuest.packets.PacketEditConversation;
import com.chocolate.chocolateQuest.packets.PacketEditConversationFromClient;
import com.chocolate.chocolateQuest.packets.PacketEditNPC;
import com.chocolate.chocolateQuest.packets.PacketEditNPCFromClient;
import com.chocolate.chocolateQuest.packets.PacketEditNPCFromServer;
import com.chocolate.chocolateQuest.packets.PacketEditorGUIClose;
import com.chocolate.chocolateQuest.packets.PacketEntityAnimation;
import com.chocolate.chocolateQuest.packets.PacketHookImpact;
import com.chocolate.chocolateQuest.packets.PacketPartyCreation;
import com.chocolate.chocolateQuest.packets.PacketPartyMemberAction;
import com.chocolate.chocolateQuest.packets.PacketSaveNPC;
import com.chocolate.chocolateQuest.packets.PacketSaveNPCFromClient;
import com.chocolate.chocolateQuest.packets.PacketSaveNPCFromServer;
import com.chocolate.chocolateQuest.packets.PacketShieldBlock;
import com.chocolate.chocolateQuest.packets.PacketShieldBlockFromServer;
import com.chocolate.chocolateQuest.packets.PacketSpawnParticlesAround;
import com.chocolate.chocolateQuest.packets.PacketStartConversation;
import com.chocolate.chocolateQuest.packets.PacketUpdateAwakement;
import com.chocolate.chocolateQuest.packets.PacketUpdateConversation;
import com.chocolate.chocolateQuest.packets.PacketUpdateHumanData;
import com.chocolate.chocolateQuest.packets.PacketUpdateHumanDataFromClient;
import com.chocolate.chocolateQuest.packets.PacketUpdateHumanDataFromServer;
import com.chocolate.chocolateQuest.packets.PacketUpdateHumanDummyData;
import com.chocolate.chocolateQuest.packets.PacketUpdateHumanDummyDataFromClient;
import com.chocolate.chocolateQuest.packets.PacketUpdateHumanDummyDataFromServer;
import com.chocolate.chocolateQuest.packets.PacketUpdateShopRecipe;
import com.chocolate.chocolateQuest.packets.PacketUpdateShopRecipeFromClient;
import com.chocolate.chocolateQuest.packets.PacketUpdateShopRecipeFromServer;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;

public class ChannelHandler {

   public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("chocolateQuest".toLowerCase());
   protected static int id = 0;


   public static void init() {
      INSTANCE.registerMessage(PacketEditorGUIClose.class, PacketEditorGUIClose.class, id++, Side.SERVER);
      INSTANCE.registerMessage(PacketUpdateHumanDataFromClient.class, PacketUpdateHumanData.class, id++, Side.SERVER);
      INSTANCE.registerMessage(PacketUpdateHumanDummyDataFromClient.class, PacketUpdateHumanDummyData.class, id++, Side.SERVER);
      INSTANCE.registerMessage(PacketUpdateAwakement.class, PacketUpdateAwakement.class, id++, Side.SERVER);
      INSTANCE.registerMessage(PacketUpdateShopRecipeFromClient.class, PacketUpdateShopRecipe.class, id++, Side.SERVER);
      INSTANCE.registerMessage(PacketUpdateConversation.class, PacketUpdateConversation.class, id++, Side.SERVER);
      INSTANCE.registerMessage(PacketEditConversationFromClient.class, PacketEditConversation.class, id++, Side.SERVER);
      INSTANCE.registerMessage(PacketEditNPCFromClient.class, PacketEditNPC.class, id++, Side.SERVER);
      INSTANCE.registerMessage(PacketController.class, PacketController.class, id++, Side.SERVER);
      INSTANCE.registerMessage(PacketSaveNPCFromClient.class, PacketSaveNPC.class, id++, Side.SERVER);
      INSTANCE.registerMessage(PacketPartyMemberAction.class, PacketPartyMemberAction.class, id++, Side.SERVER);
      INSTANCE.registerMessage(PacketPartyCreation.class, PacketPartyCreation.class, id++, Side.SERVER);
      INSTANCE.registerMessage(PacketShieldBlock.class, PacketShieldBlock.class, id++, Side.SERVER);
      INSTANCE.registerMessage(PacketClientAsks.class, PacketClientAsks.class, id++, Side.SERVER);
      INSTANCE.registerMessage(PacketEntityAnimation.class, PacketEntityAnimation.class, id++, Side.CLIENT);
      INSTANCE.registerMessage(PacketAttackToXYZ.class, PacketAttackToXYZ.class, id++, Side.CLIENT);
      INSTANCE.registerMessage(PacketSpawnParticlesAround.class, PacketSpawnParticlesAround.class, id++, Side.CLIENT);
      INSTANCE.registerMessage(PacketHookImpact.class, PacketHookImpact.class, id++, Side.CLIENT);
      INSTANCE.registerMessage(PacketUpdateHumanDataFromServer.class, PacketUpdateHumanData.class, id++, Side.CLIENT);
      INSTANCE.registerMessage(PacketUpdateHumanDummyDataFromServer.class, PacketUpdateHumanDummyData.class, id++, Side.CLIENT);
      INSTANCE.registerMessage(PacketUpdateShopRecipeFromServer.class, PacketUpdateShopRecipe.class, id++, Side.CLIENT);
      INSTANCE.registerMessage(PacketStartConversation.class, PacketStartConversation.class, id++, Side.CLIENT);
      INSTANCE.registerMessage(PacketEditConversation.class, PacketEditConversation.class, id++, Side.CLIENT);
      INSTANCE.registerMessage(PacketEditNPCFromServer.class, PacketEditNPC.class, id++, Side.CLIENT);
      INSTANCE.registerMessage(PacketSaveNPCFromServer.class, PacketSaveNPC.class, id++, Side.CLIENT);
      INSTANCE.registerMessage(PacketShieldBlockFromServer.class, PacketShieldBlockFromServer.class, id++, Side.CLIENT);
   }

   public void sendPaquetToServer(IMessage packet) {
      INSTANCE.sendToServer(packet);
   }

   public void sendToAllAround(Entity entity, IMessage packet) {
      this.sendToAllAround(entity, packet, 32);
   }

   public void sendToAllAround(Entity entity, IMessage packet, int range) {
      this.sendToAllAround(packet, new TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, (double)range));
   }

   public void sendToAllAround(IMessage packet, TargetPoint point) {
      INSTANCE.sendToAllAround(packet, point);
   }

   public void sendToPlayer(IMessage packet, EntityPlayerMP player) {
      INSTANCE.sendTo(packet, player);
   }

}
