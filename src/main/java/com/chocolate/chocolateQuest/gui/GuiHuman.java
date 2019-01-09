package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.EnumAiState;
import com.chocolate.chocolateQuest.gui.ContainerHumanInventory;
import com.chocolate.chocolateQuest.gui.GuiButtonAIMode;
import com.chocolate.chocolateQuest.gui.GuiButtonAngle;
import com.chocolate.chocolateQuest.gui.GuiButtonBattleAIMode;
import com.chocolate.chocolateQuest.gui.GuiButtonIcon;
import com.chocolate.chocolateQuest.gui.GuiHumanBase;
import com.chocolate.chocolateQuest.gui.guiParty.PartyManager;
import com.chocolate.chocolateQuest.utils.BDHelper;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ChatComponentTranslation;
import org.lwjgl.opengl.GL11;

public class GuiHuman extends GuiHumanBase {

   GuiButtonAngle teamPositionButton;
   GuiButtonAIMode combatAI;
   GuiButtonAIMode passiveAI;
   EntityHumanBase human;
   String[] combatAINames;
   boolean owned;
   boolean ownedOriginal;
   final int FOLLOW_BUTTON_ID;
   final String FOLLOW;
   final String LEAVE;


   public GuiHuman(ContainerHumanInventory container, EntityHumanBase human, IInventory par1IInventory, EntityPlayer playerInventory) {
      super(container, par1IInventory, playerInventory);
      this.combatAINames = new String[]{"Offensive", "Defensive", "Evasive", "Flee"};
      this.FOLLOW_BUTTON_ID = 100;
      this.human = human;
      this.FOLLOW = (new ChatComponentTranslation("ai.join.name", new Object[0])).getFormattedText();
      this.LEAVE = (new ChatComponentTranslation("ai.leave.name", new Object[0])).getFormattedText();
   }

   public GuiHuman(EntityHumanBase human, IInventory par1IInventory, EntityPlayer playerInventory) {
      this(new ContainerHumanInventory(playerInventory.inventory, par1IInventory), human, par1IInventory, playerInventory);
   }

   public void onGuiClosed() {
      if(this.human.worldObj.isRemote) {
         this.human.partyPositionAngle = this.teamPositionButton.getAngle();
         this.human.partyDistanceToLeader = this.teamPositionButton.getDistance();
         this.human.AIMode = this.passiveAI.selectedMode;
         this.human.AICombatMode = this.combatAI.selectedMode;
         this.human.partyPositionPersistance = true;
         this.human.updateHands();
         this.human.updateOwner = this.owned != this.ownedOriginal;
         this.human.playerSpeakingTo = null;
         IMessage packet = this.human.getEntityGUIUpdatePacket(super.player);
         ChocolateQuest.channel.sendPaquetToServer(packet);
         if(this.owned) {
            PartyManager.instance.addMember(this.human);
         } else {
            PartyManager.instance.removeMember(this.human);
         }
      }

      super.onGuiClosed();
   }

   public void initGui() {
      super.initGui();
      int width = (super.width - super.xSize) / 2 + 80;
      int height = super.height - super.height / 2 - 10;
      this.teamPositionButton = new GuiButtonAngle(10, width - 15, height - 80, "test", 1.0F, this.human);
      super.buttonList.add(this.teamPositionButton);
      this.combatAI = new GuiButtonBattleAIMode(10, width + 72, height - 80, 20, 80, this.combatAINames, super.fontRendererObj, this.human.AICombatMode);
      super.buttonList.add(this.combatAI);
      String[] names = EnumAiState.getNames();

      for(int b = 0; b < names.length; ++b) {
         names[b] = (new ChatComponentTranslation(EnumAiState.values()[b].ainame, new Object[0])).getFormattedText();
      }

      this.passiveAI = new GuiButtonAIMode(10, width + 100, height - 80, 60, 100, names, super.fontRendererObj, this.human.AIMode);
      super.buttonList.add(this.passiveAI);
      this.owned = this.ownedOriginal = this.human.updateOwner;
      this.human.updateOwner = false;
      GuiButtonIcon var5 = new GuiButtonIcon(100, width - 40, height - 100, 9.0F, 2.0F, 6.0F, 1.0F, this.owned?this.LEAVE:this.FOLLOW);
      super.buttonList.add(var5);
   }

   protected void mouseClicked(int par1, int par2, int par3) {
      super.mouseClicked(par1, par2, par3);
   }

   protected void actionPerformed(GuiButton button) {
      if(button.id == 100) {
         this.owned = !this.owned;
         button.displayString = this.owned?this.LEAVE:this.FOLLOW;
      }

      super.actionPerformed(button);
   }

   protected void drawEquipementPanel() {
      super.mc.renderEngine.bindTexture(BDHelper.guiButtonsTexture);
      int width = (super.width - super.xSize) / 2;
      int height = super.height - super.height / 2 - 86;
      int x = width - 48;
      int y = height - 2;
      this.drawTexturedModalRect(x, y, 64, 128, 64, 80);
      Gui.drawRect(x + 3, y + 3, x + 58, y + 76, -16777216);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      super.drawEquipementPanel();
      drawEntity(this.human, width - 24, height + 60);
   }
}
