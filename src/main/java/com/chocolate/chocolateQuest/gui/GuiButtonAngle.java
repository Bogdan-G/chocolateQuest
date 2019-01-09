package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.utils.BDHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiButtonAngle extends GuiButton {

   public boolean dragging;
   double xPoint = 0.0D;
   double yPoint = 0.0D;
   EntityHumanBase human;
   final int maxRadio = 10;


   public GuiButtonAngle(int par1, int par2, int par3, String par5Str, float par6, EntityHumanBase human) {
      super(par1, par2, par3, 80, 80, par5Str);
      super.displayString = "";
      this.human = human;
      this.setCoords(human.partyPositionAngle);
   }

   public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
      super.drawButton(par1Minecraft, par2, par3);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.guiButtonsTexture);
      this.drawTexturedRect(super.xPosition, super.yPosition, 0, 0, super.width, 4);
      int radius = super.width / 2;
      int iconSize = (int)((double)radius * 0.2D);
      radius -= iconSize / 2;
      double x = this.xPoint;
      double y = this.yPoint;
      x = (double)(super.xPosition + radius) - x * (double)radius / 10.0D;
      y = (double)(super.yPosition + radius) - y * (double)radius / 10.0D;
      this.drawTexturedRect((int)x, (int)y, 4, 0, iconSize);
   }

   public int getAngle() {
      return (int)(Math.atan2(this.yPoint, this.xPoint) * 180.0D / 3.141592653589793D - 90.0D);
   }

   public int getDistance() {
      return (int)Math.max(1.0D, Math.sqrt(this.xPoint * this.xPoint + this.yPoint * this.yPoint));
   }

   public void setCoords(int angle) {
      angle += 90;
      double fangle = (double)angle * 3.141592653589793D / 180.0D;
      int dist = this.human.partyDistanceToLeader;
      this.xPoint = Math.cos(fangle) * (double)dist;
      this.yPoint = Math.sin(fangle) * (double)dist;
   }

   protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3) {
      super.displayString = "";
      int radius = super.width / 2;
      if(this.dragging) {
         float x = (float)(super.xPosition + radius - par2) * 10.0F / (float)radius;
         float y = (float)(super.yPosition + radius - par3) * 10.0F / (float)radius;
         this.xPoint = (double)((int)Math.min(10.0F, Math.max(-10.0F, x)));
         this.yPoint = (double)((int)Math.min(10.0F, Math.max(-10.0F, y)));
      }

   }

   public void drawTexturedRect(int x, int y, int indexX, int indexZ, int size, int iconSize) {
      float f = 0.0625F;
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawingQuads();
      tessellator.addVertexWithUV((double)x, (double)(y + size), (double)super.zLevel, (double)((float)(indexX + 0) * f), (double)((float)(indexZ + iconSize) * f));
      tessellator.addVertexWithUV((double)(x + size), (double)(y + size), (double)super.zLevel, (double)((float)(indexX + iconSize) * f), (double)((float)(indexZ + iconSize) * f));
      tessellator.addVertexWithUV((double)(x + size), (double)(y + 0), (double)super.zLevel, (double)((float)(indexX + iconSize) * f), (double)((float)(indexZ + 0) * f));
      tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)super.zLevel, (double)((float)(indexX + 0) * f), (double)((float)(indexZ + 0) * f));
      tessellator.draw();
   }

   public void drawTexturedRect(int x, int y, int indexX, int indexZ, int size) {
      this.drawTexturedRect(x, y, indexX, indexZ, size, 1);
   }

   public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
      if(super.mousePressed(par1Minecraft, par2, par3)) {
         this.dragging = !this.dragging;
         return true;
      } else {
         return false;
      }
   }

   public void mouseReleased(int par1, int par2) {
      this.dragging = false;
   }
}
