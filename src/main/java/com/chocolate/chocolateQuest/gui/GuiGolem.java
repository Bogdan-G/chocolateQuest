package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.gui.ContainerGolemInventory;
import com.chocolate.chocolateQuest.gui.GuiHuman;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class GuiGolem extends GuiHuman {

   public GuiGolem(EntityHumanBase human, IInventory par1IInventory, EntityPlayer playerInventory) {
      super(new ContainerGolemInventory(playerInventory.inventory, par1IInventory), human, par1IInventory, playerInventory);
   }

   protected void drawEquipementPanel() {
      super.mc.renderEngine.bindTexture(BDHelper.guiButtonsTexture);
      int width = (super.width - super.xSize) / 2;
      int height = super.height - super.height / 2 - 86;
      this.drawTexturedModalRect(width - 6, height - 3, 0, 144, 64, 80);
   }
}
