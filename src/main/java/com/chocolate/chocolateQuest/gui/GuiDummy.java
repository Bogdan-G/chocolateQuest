package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanDummy;
import com.chocolate.chocolateQuest.gui.GuiButtonIconOnOff;
import com.chocolate.chocolateQuest.gui.GuiButtonSlider;
import com.chocolate.chocolateQuest.gui.GuiHuman;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class GuiDummy extends GuiHuman {

   GuiButtonSlider healthScale;
   GuiButtonSlider dropRight;
   GuiButtonSlider dropHelmet;
   GuiButtonSlider dropBody;
   GuiButtonSlider dropLegs;
   GuiButtonSlider dropFeet;
   GuiButtonSlider dropLeft;
   GuiButtonIconOnOff lockInventory;


   public GuiDummy(EntityHumanBase human, IInventory par1iInventory, EntityPlayer playerInventory) {
      super(human, par1iInventory, playerInventory);
      super.combatAINames = new String[]{"Offensive", "Defensive", "Evasive", "Flee", "Backstab"};
   }

   public void initGui() {
      super.initGui();
      if(super.human instanceof EntityHumanDummy) {
         this.healthScale = new GuiButtonSlider(10, 10, 10, "Health scale", (float)(super.human.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() / 10.0D));
         super.buttonList.add(this.healthScale);
      }

      byte desp = 22;
      byte pos = 1;
      byte posY = 20;
      this.dropRight = new GuiButtonSlider(10 + pos, 10, posY + pos * desp, "Drop hand R.", super.human.getEquipmentDropChances(0), 1);
      super.buttonList.add(this.dropRight);
      int var4 = pos + 1;
      this.dropLeft = new GuiButtonSlider(10 + var4, 10, posY + var4 * desp, "Drop hand L.", super.human.leftHandDropChances, 1);
      super.buttonList.add(this.dropLeft);
      ++var4;
      this.dropHelmet = new GuiButtonSlider(10 + var4, 10, posY + var4 * desp, "Drop head", super.human.getEquipmentDropChances(4), 1);
      super.buttonList.add(this.dropHelmet);
      ++var4;
      this.dropBody = new GuiButtonSlider(10 + var4, 10, posY + var4 * desp, "Drop body", super.human.getEquipmentDropChances(3), 1);
      super.buttonList.add(this.dropBody);
      ++var4;
      this.dropLegs = new GuiButtonSlider(10 + var4, 10, posY + var4 * desp, "Drop legs", super.human.getEquipmentDropChances(2), 1);
      super.buttonList.add(this.dropLegs);
      ++var4;
      this.dropFeet = new GuiButtonSlider(10 + var4, 10, posY + var4 * desp, "Drop feet", super.human.getEquipmentDropChances(1), 1);
      super.buttonList.add(this.dropFeet);
      this.lockInventory = new GuiButtonIconOnOff(654321, super.width / 2 - 52, super.height / 2 - 83, 13.0F, 3.0F, 1.0F, 1.0F, "", super.human.inventoryLocked);
      super.buttonList.add(this.lockInventory);
   }

   public void onGuiClosed() {
      if(super.human.worldObj.isRemote) {
         super.human.partyPositionAngle = super.teamPositionButton.getAngle();
         super.human.partyDistanceToLeader = super.teamPositionButton.getDistance();
         super.human.AIMode = super.passiveAI.selectedMode;
         super.human.AICombatMode = super.combatAI.selectedMode;
         super.human.partyPositionPersistance = true;
         super.human.updateHands();
         if(this.healthScale != null) {
            super.human.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((double)(this.healthScale.sliderValue * (float)this.healthScale.SLIDER_MAX_VALUE));
         }

         super.human.leftHandDropChances = this.dropLeft.sliderValue;
         super.human.setEquipmentDropChances(0, this.dropRight.sliderValue);
         super.human.setEquipmentDropChances(4, this.dropHelmet.sliderValue);
         super.human.setEquipmentDropChances(3, this.dropBody.sliderValue);
         super.human.setEquipmentDropChances(2, this.dropLegs.sliderValue);
         super.human.setEquipmentDropChances(1, this.dropFeet.sliderValue);
         super.human.inventoryLocked = this.lockInventory.isOn;
      }

      super.onGuiClosed();
   }
}
