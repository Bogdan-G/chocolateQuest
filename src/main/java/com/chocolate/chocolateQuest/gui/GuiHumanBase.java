package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.gui.ContainerBDChest;
import com.chocolate.chocolateQuest.gui.ContainerHumanInventory;
import com.chocolate.chocolateQuest.gui.GuiInventoryPlayer;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import org.lwjgl.opengl.GL11;

public class GuiHumanBase extends GuiInventoryPlayer {

   public GuiHumanBase(ContainerBDChest container, IInventory par1IInventory, EntityPlayer playerInventory) {
      super(container, par1IInventory, playerInventory);
   }

   public GuiHumanBase(IInventory par1IInventory, EntityPlayer player) {
      this(new ContainerHumanInventory(player.inventory, par1IInventory), par1IInventory, player);
   }

   public void initGui() {
      super.initGui();
   }

   protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
      super.drawGuiContainerBackgroundLayer(par1, par2, par3);
      this.drawEquipementPanel();
   }

   protected void drawEquipementPanel() {
      super.mc.renderEngine.bindTexture(BDHelper.guiButtonsTexture);
      int width = (super.width - super.xSize) / 2;
      int height = super.height - super.height / 2 - 86;
      this.drawTexturedModalRect(width - 6, height - 2, 0, 64, 64, 80);
   }

   public static void drawEntity(EntityLivingBase human, int x, int y) {
      drawEntity(human, x, y, 23.0F);
   }

   public static void drawEntity(EntityLivingBase human, int x, int y, float scale) {
      GL11.glPushMatrix();
      RenderHelper.enableStandardItemLighting();
      GL11.glDisable(2896);
      GL11.glTranslatef((float)x, (float)y, 0.0F);
      GL11.glScalef(scale, -scale, scale);
      float prevYaw = human.rotationYaw;
      float prevYawHead = human.rotationYawHead;
      float prevYawOffset = human.renderYawOffset;
      human.rotationYaw = 0.0F;
      human.rotationYawHead = 0.0F;
      human.renderYawOffset = 0.0F;
      RenderManager.instance.renderEntityWithPosYaw(human, 0.0D, 0.0D, 5.0D, 0.0F, 1.0F);
      human.rotationYaw = prevYaw;
      human.rotationYawHead = prevYawHead;
      human.renderYawOffset = prevYawOffset;
      RenderHelper.disableStandardItemLighting();
      GL11.glDisable('\u803a');
      OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GL11.glDisable(3553);
      OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GL11.glPopMatrix();
   }
}
