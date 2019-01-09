package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.block.BlockArmorStandTileEntity;
import com.chocolate.chocolateQuest.gui.ContainerArmorStand;
import com.chocolate.chocolateQuest.gui.GuiHumanBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class GuiArmorStand extends GuiHumanBase {

   public GuiArmorStand(BlockArmorStandTileEntity te, EntityPlayer player, IInventory par1IInventory) {
      super(new ContainerArmorStand(player.inventory, par1IInventory), par1IInventory, player);
   }

   public void onGuiClosed() {
      super.onGuiClosed();
   }

   public void initGui() {
      super.initGui();
   }
}
