package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.gui.ContainerBDChest;
import com.chocolate.chocolateQuest.gui.GuiInventoryPlayer;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class GuiInventoryEntity extends GuiInventoryPlayer {

   int rows = 0;


   public GuiInventoryEntity(ContainerBDChest container, IInventory par1iInventory, EntityPlayer player) {
      super(container, par1iInventory, player);
   }

   protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
      super.drawGuiContainerBackgroundLayer(par1, par2, par3);
      super.mc.renderEngine.bindTexture(BDHelper.guiButtonsTexture);
      int width = (super.width - super.xSize) / 2;
      int height = (super.height - super.ySize) / 2 + 7;
      this.drawTexturedModalRect(width, height - 2, 64, 96, super.xSize, 32);
      byte AMMO_ICON;
      int posY;
      if(this.rows > 0) {
         AMMO_ICON = 11;

         for(posY = 1; posY <= this.rows - 1; ++posY) {
            this.drawTexturedModalRect(width, height - 2 + AMMO_ICON + 17 * posY, 64, 96 + AMMO_ICON, super.xSize, 18);
         }

         this.drawTexturedModalRect(width, height - 2 + AMMO_ICON + 17 * this.rows, 64, 96 + AMMO_ICON, super.xSize, 21);
      }

      AMMO_ICON = 36;
      posY = 0;

      for(int i = 0; i < super.upperChestInventory.getSizeInventory(); ++i) {
         int x = 10 + width + i * 17 - posY * 9;
         int y = 10 + height + posY;
         this.drawIcon(AMMO_ICON, x, y);
         if(i % 9 == 8) {
            posY += 17;
         }
      }

   }

   public int getPlayerInventoryOffset() {
      return super.getPlayerInventoryOffset();
   }
}
