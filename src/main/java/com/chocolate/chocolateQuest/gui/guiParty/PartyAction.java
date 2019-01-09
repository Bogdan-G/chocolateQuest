package com.chocolate.chocolateQuest.gui.guiParty;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.EnumAiCombat;
import com.chocolate.chocolateQuest.entity.ai.EnumAiState;
import com.chocolate.chocolateQuest.gui.guiParty.PartyActionAI;
import com.chocolate.chocolateQuest.gui.guiParty.PartyActionAICombat;
import com.chocolate.chocolateQuest.gui.guiParty.PartyActionAIWard;
import com.chocolate.chocolateQuest.gui.guiParty.PartyActionAttack;
import com.chocolate.chocolateQuest.gui.guiParty.PartyActionMount;
import com.chocolate.chocolateQuest.gui.guiParty.PartyActionMove;
import com.chocolate.chocolateQuest.gui.guiParty.PartyActionStop;
import com.chocolate.chocolateQuest.gui.guiParty.PartyActionUnmount;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;

public class PartyAction {

   public static List actions = new ArrayList();
   public final byte id;
   public static byte lastID = 0;
   public String name;
   public int icon;
   public static PartyAction move = new PartyActionMove("ai.move.name", 0);
   public static PartyAction attack = new PartyActionAttack("ai.attack.name", 1);
   public static PartyAction aggressive = new PartyActionAICombat("ai.ofensive.name", 2, EnumAiCombat.OFFENSIVE.ordinal());
   public static PartyAction defensive = new PartyActionAICombat("ai.defensive.name", 3, EnumAiCombat.DEFENSIVE.ordinal());
   public static PartyAction evasive = new PartyActionAICombat("ai.evasive.name", 4, EnumAiCombat.EVASIVE.ordinal());
   public static PartyAction flee = new PartyActionAICombat("ai.flee.name", 5, EnumAiCombat.FLEE.ordinal());
   public static PartyAction follow = new PartyActionAI("ai.follow.name", 6, EnumAiState.FOLLOW.ordinal());
   public static PartyAction formation = new PartyActionAI("ai.formation.name", 7, EnumAiState.FORMATION.ordinal());
   public static PartyAction stand = new PartyActionAIWard("ai.ward.name", 8, EnumAiState.WARD.ordinal());
   public static PartyAction sit = new PartyActionAI("ai.sit.name", 10, EnumAiState.SIT.ordinal());
   public static PartyAction stop = new PartyActionStop("ai.stop.name", 9);
   public static PartyAction mount = new PartyActionMount("ai.mount.name", 11);
   public static PartyAction unmount = new PartyActionUnmount("ai.unmount.name", 12);


   public PartyAction(String name, int icon) {
      this.name = name;
      this.icon = icon;
      this.id = lastID++;
      actions.add(this.id, this);
   }

   public void write(ByteBuf inputStream, MovingObjectPosition playerMop, EntityPlayer player) {}

   public void read(ByteBuf inputStream) {}

   public void execute(EntityHumanBase e) {}

}
