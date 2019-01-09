package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.gui.ContainerBDChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GuiChestBD extends GuiContainer {

   private static final ResourceLocation texture = new ResourceLocation("textures/gui/container/generic_54.png");
   private IInventory upperChestInventory;
   private IInventory lowerChestInventory;
   private int inventoryRows = 0;


   public GuiChestBD(IInventory playerInventory, IInventory chestInventory) {
      super(new ContainerBDChest(playerInventory, chestInventory));
      this.upperChestInventory = chestInventory;
      this.lowerChestInventory = playerInventory;
      super.allowUserInput = false;
      short var3 = 222;
      int var4 = var3 - 108;
      this.inventoryRows = chestInventory.getSizeInventory() / 9;
      super.ySize = var4 + this.inventoryRows * 18;
   }

   protected void drawGuiContainerForegroundLayer() {
      super.fontRendererObj.drawString(StatCollector.translateToLocal(this.lowerChestInventory.getInventoryName()), 8, 6, 4210752);
      super.fontRendererObj.drawString(StatCollector.translateToLocal(this.upperChestInventory.getInventoryName()), 8, super.ySize - 96 + 2, 4210752);
   }

   protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      super.mc.renderEngine.bindTexture(texture);
      int width = (super.width - super.xSize) / 2;
      int height = (super.height - super.ySize) / 2;
      this.drawTexturedModalRect(width, height, 0, 0, super.xSize, this.inventoryRows * 18 + 17);
      this.drawTexturedModalRect(width, height + this.inventoryRows * 18 + 17, 0, 126, super.xSize, 96);
   }

}
