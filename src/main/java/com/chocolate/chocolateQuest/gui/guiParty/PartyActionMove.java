package com.chocolate.chocolateQuest.gui.guiParty;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.gui.guiParty.PartyAction;
import com.chocolate.chocolateQuest.utils.Vec4I;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;

public class PartyActionMove extends PartyAction {

   int x;
   int y;
   int z;


   public PartyActionMove(String name, int icon) {
      super(name, icon);
   }

   public void write(ByteBuf stream, MovingObjectPosition playerMop, EntityPlayer player) {
      stream.writeInt(playerMop.blockX);
      stream.writeInt(playerMop.blockY);
      stream.writeInt(playerMop.blockZ);
   }

   public void read(ByteBuf stream) {
      this.x = stream.readInt();
      this.y = stream.readInt();
      this.z = stream.readInt();
   }

   public void execute(EntityHumanBase e) {
      e.currentPos = new Vec4I(this.x, this.y, this.z, 0);
   }
}
