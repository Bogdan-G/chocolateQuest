package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.gui.ContainerBDChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiInventoryPlayer extends GuiContainer {

   protected static final int ICON_SIZE = 16;
   protected static final int ICONS_PER_ROW = 16;
   private static final ResourceLocation texture = new ResourceLocation("textures/gui/container/generic_54.png");
   protected IInventory upperChestInventory;
   protected IInventory lowerChestInventory;
   protected EntityPlayer player;


   public GuiInventoryPlayer(ContainerBDChest container, IInventory par1IInventory, EntityPlayer player) {
      super(container);
      this.player = player;
      this.upperChestInventory = par1IInventory;
      this.lowerChestInventory = player.inventory;
      super.allowUserInput = false;
      short var3 = 222;
      int var4 = var3 - 108;
      int inventoryRows = this.lowerChestInventory.getSizeInventory() / 9;
      super.ySize = var4 + inventoryRows * 18 + 28;
   }

   protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      super.mc.renderEngine.bindTexture(texture);
      int width = (super.width - super.xSize) / 2;
      int height = (super.height - super.ySize) / 2;
      int offset = this.getPlayerInventoryOffset();
      this.drawTexturedModalRect(width, height + offset - 3, 0, 0, super.xSize, 17);
      this.drawTexturedModalRect(width, height + 0 + offset, 0, 126, super.xSize, 96);
   }

   public int getPlayerInventoryOffset() {
      return 104;
   }

   public void initGui() {
      super.initGui();
   }

   protected void handleMouseClick(Slot slot, int slotID, int x, int y) {
      super.handleMouseClick(slot, slotID, x, y);
   }

   public void drawIcon(int icon, int xPos, int yPos) {
      this.drawTexturedModalRect(xPos, yPos, icon % 16 * 16, icon / 16 * 16, 16, 16);
   }

}
