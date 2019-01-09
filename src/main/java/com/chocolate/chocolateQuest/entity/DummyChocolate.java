package com.chocolate.chocolateQuest.entity;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class DummyChocolate extends EntityPlayer {

   public DummyChocolate(World par1World) {
      super(par1World, new GameProfile(new UUID(0L, 0L), "ArrgChocolate"));
   }

   public boolean canCommandSenderUseCommand(int i, String s) {
      return false;
   }

   public ChunkCoordinates getPlayerCoordinates() {
      return null;
   }

   public void addChatMessage(IChatComponent var1) {}
}
