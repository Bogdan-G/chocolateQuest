package com.chocolate.chocolateQuest.gui.guiParty;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.gui.guiParty.PartyActionAI;
import com.chocolate.chocolateQuest.utils.Vec4I;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;

public class PartyActionAIWard extends PartyActionAI {

   int x;
   int y;
   int z;
   int w;


   public PartyActionAIWard(String name, int icon, int AIMode) {
      super(name, icon, AIMode);
   }

   public void execute(EntityHumanBase e) {
      super.execute(e);
      e.AIPosition = new Vec4I(this.x, this.y, this.z, this.w);
   }

   public void write(ByteBuf stream, MovingObjectPosition playerMop, EntityPlayer player) {
      stream.writeInt(playerMop.blockX);
      stream.writeInt(playerMop.blockY);
      stream.writeInt(playerMop.blockZ);
      stream.writeInt((int)player.rotationYawHead);
   }

   public void read(ByteBuf stream) {
      this.x = stream.readInt();
      this.y = stream.readInt();
      this.z = stream.readInt();
      this.w = stream.readInt();
   }
}
