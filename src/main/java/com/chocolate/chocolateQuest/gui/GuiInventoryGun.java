package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.gui.ContainerGun;
import com.chocolate.chocolateQuest.gui.GuiInventoryPlayer;
import com.chocolate.chocolateQuest.items.gun.ILoadableGun;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class GuiInventoryGun extends GuiInventoryPlayer {

   ItemStack gunItemStack;
   int rows;


   public GuiInventoryGun(ItemStack is, IInventory par1IInventory, EntityPlayer player) {
      super(new ContainerGun(player.inventory, par1IInventory, is), par1IInventory, player);
      this.gunItemStack = is;
      this.rows = (par1IInventory.getSizeInventory() - 1) / 9;
   }

   protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
      super.drawGuiContainerBackgroundLayer(par1, par2, par3);
      super.mc.renderEngine.bindTexture(BDHelper.guiButtonsTexture);
      int width = (super.width - super.xSize) / 2;
      int height = (super.height - super.ySize) / 2;
      boolean offset = true;
      this.drawTexturedModalRect(width, height - 2, 64, 96, super.xSize, 32);
      int AMMO_ICON;
      if(this.rows > 0) {
         byte gun = 11;

         for(AMMO_ICON = 1; AMMO_ICON <= this.rows - 1; ++AMMO_ICON) {
            this.drawTexturedModalRect(width, height - 2 + gun + 17 * AMMO_ICON, 64, 96 + gun, super.xSize, 18);
         }

         this.drawTexturedModalRect(width, height - 2 + gun + 17 * this.rows, 64, 96 + gun, super.xSize, 21);
      }

      ILoadableGun var13 = (ILoadableGun)this.gunItemStack.getItem();
      AMMO_ICON = var13.getStackIcon(this.gunItemStack);
      int posY = 0;

      for(int i = 0; i < var13.getAmmoLoaderAmmount(this.gunItemStack); ++i) {
         int x = 10 + width + i * 17 - posY * 9;
         int y = 10 + height + posY;
         this.drawIcon(AMMO_ICON, x, y);
         if(i % 9 == 8) {
            posY += 17;
         }
      }

   }

   public int getPlayerInventoryOffset() {
      return 46 + this.rows * 17;
   }
}
