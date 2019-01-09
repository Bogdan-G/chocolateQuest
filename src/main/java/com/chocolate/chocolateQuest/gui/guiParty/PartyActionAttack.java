package com.chocolate.chocolateQuest.gui.guiParty;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.gui.guiParty.PartyAction;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;

public class PartyActionAttack extends PartyAction {

   int idTarget;


   public PartyActionAttack(String name, int icon) {
      super(name, icon);
   }

   public void write(ByteBuf stream, MovingObjectPosition playerMop, EntityPlayer player) {
      int id = 0;
      if(playerMop.entityHit != null) {
         id = playerMop.entityHit.getEntityId();
      }

      stream.writeInt(id);
   }

   public void read(ByteBuf stream) {
      this.idTarget = stream.readInt();
   }

   public void execute(EntityHumanBase e) {
      Entity target = e.worldObj.getEntityByID(this.idTarget);
      if(target instanceof EntityLivingBase) {
         e.setAttackTarget((EntityLivingBase)target);
      }

   }
}
