package com.chocolate.chocolateQuest.gui.guinpc;

import com.chocolate.chocolateQuest.entity.ai.npcai.NpcAI;
import com.chocolate.chocolateQuest.gui.GuiButtonIcon;
import com.chocolate.chocolateQuest.gui.guinpc.ButtonAI;
import com.chocolate.chocolateQuest.utils.BDHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiButtonAISlider extends GuiButton {

   float xIndex;
   float yIndex;
   float xSize;
   float ySize;
   List buttonsAI = new ArrayList();
   int selected = -1;


   public GuiButtonAISlider(int id, int posX, int posY, float xIndex, float yIndex, float xSize, float ySize, NpcAI ai) {
      super(id, posX, posY, (int)(xSize * 16.0F * 2.0F), (int)(ySize * 16.0F * 2.0F), "");
      this.xIndex = xIndex;
      this.yIndex = yIndex;
      this.xSize = xSize;
      this.ySize = ySize;

      for(NpcAI currentAI = ai; currentAI != null; currentAI = currentAI.nextAI) {
         this.buttonsAI.add(new ButtonAI(currentAI));
      }

   }

   public void drawButton(Minecraft par1Minecraft, int x, int y) {
      if(super.visible) {
         FontRenderer font = Minecraft.getMinecraft().fontRenderer;
         Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.guiButtonsTexture);
         int textColor = 16777215;
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         boolean mouseOver = x >= super.xPosition && y >= super.yPosition && x < super.xPosition + super.width && y < super.yPosition + super.height;
         GuiButtonIcon.drawTexturedRect(super.xPosition, super.yPosition, super.zLevel, super.width, super.height, this.xIndex, this.yIndex, this.xSize, this.ySize);
         byte separatorWidth = 2;
         int stepSize = super.width / 24;
         boolean c = true;
         int listSize = this.buttonsAI.size();

         for(int i = 0; i < listSize; ++i) {
            ButtonAI b = (ButtonAI)this.buttonsAI.get(i);
            int color = -65536;
            int bx = super.xPosition + b.x * stepSize;
            if(b.isSelected) {
               bx = x;
            }

            boolean mouseOverSeparator = false;
            boolean mouseOverButton = false;
            if(mouseOver) {
               mouseOverSeparator = x >= bx && x < bx + separatorWidth;
               ButtonAI scale = this.getAI(x, y);
               if(scale == b) {
                  mouseOverButton = true;
               }
            }

            int var18;
            if(mouseOverButton && !mouseOverSeparator) {
               var18 = 24 - b.x;
               if(i + 1 < listSize) {
                  var18 = ((ButtonAI)this.buttonsAI.get(i + 1)).x - b.x;
               }

               var18 *= stepSize;
               drawRect(bx, super.yPosition, bx + var18, super.yPosition + super.height, 872349696);
            }

            if(i == this.selected) {
               var18 = 24 - b.x;
               if(i + 1 < listSize) {
                  var18 = ((ButtonAI)this.buttonsAI.get(i + 1)).x - b.x;
               }

               var18 *= stepSize;
               drawRect(bx, super.yPosition, bx + var18, super.yPosition + super.height, 1728052992);
            }

            drawRect(bx, super.yPosition, bx + separatorWidth, super.yPosition + super.height, mouseOverSeparator?-256:color);
            byte var19 = 2;
            GL11.glScalef(1.0F / (float)var19, 1.0F / (float)var19, 1.0F / (float)var19);
            GL11.glTranslatef(0.0F, 0.0F, 2.0F);
            this.drawString(font, b.ai.toString(), (bx + separatorWidth) * var19, (super.yPosition + 4 + i % 4 * 7) * var19, textColor);
            GL11.glTranslatef(0.0F, 0.0F, -2.0F);
            GL11.glScalef((float)var19, (float)var19, (float)var19);
            c = !c;
         }
      }

   }

   public boolean mousePressed(Minecraft mc, int x, int y) {
      boolean mouseOver = x >= super.xPosition && y >= super.yPosition && x < super.xPosition + super.width && y < super.yPosition + super.height;
      if(mouseOver) {
         int stepSize = super.width / 24;
         Iterator button = this.buttonsAI.iterator();

         int i;
         while(button.hasNext()) {
            ButtonAI listSize = (ButtonAI)button.next();
            i = super.xPosition + listSize.x * stepSize;
            boolean b = false;
            if(mouseOver) {
               b = x >= i && x < i + 3;
            }

            if(b) {
               listSize.isSelected = true;
               return false;
            }
         }

         ButtonAI var10 = this.getAI(x, y);
         int var11 = this.buttonsAI.size();

         for(i = 0; i < var11; ++i) {
            ButtonAI var12 = (ButtonAI)this.buttonsAI.get(i);
            if(var12 == var10) {
               this.selected = i;
               return true;
            }
         }
      }

      return super.mousePressed(mc, x, y);
   }

   public void mouseReleased(int x, int y) {
      super.mouseReleased(x, y);
      int stepSize = super.width / 24;
      Iterator i$ = this.buttonsAI.iterator();

      while(i$.hasNext()) {
         ButtonAI b = (ButtonAI)i$.next();
         if(b.isSelected) {
            b.isSelected = false;
            b.x = Math.max(0, Math.min(23, (x - super.xPosition) / stepSize));
         }
      }

      this.sortList();
   }

   protected void mouseDragged(Minecraft mc, int x, int y) {
      super.mouseDragged(mc, x, y);
   }

   public void sortList() {
      ArrayList newButtonsAI = new ArrayList();
      int listSize = this.buttonsAI.size();

      for(int i = 0; i < listSize; ++i) {
         ButtonAI first = null;
         Iterator i$ = this.buttonsAI.iterator();

         while(i$.hasNext()) {
            ButtonAI b = (ButtonAI)i$.next();
            if(first == null) {
               first = b;
            } else if(first.x > b.x) {
               first = b;
            }
         }

         newButtonsAI.add(first);
         this.buttonsAI.remove(first);
      }

      this.buttonsAI = newButtonsAI;
   }

   public ButtonAI getAI(int x, int y) {
      int stepSize = super.width / 24;
      int listSize = this.buttonsAI.size();

      for(int i = 0; i < listSize; ++i) {
         ButtonAI b = (ButtonAI)this.buttonsAI.get(i);
         int bx = super.xPosition + b.x * stepSize;
         int aiWidth = 24 - b.x;
         if(i + 1 < listSize) {
            aiWidth = ((ButtonAI)this.buttonsAI.get(i + 1)).x - b.x;
         }

         aiWidth *= stepSize;
         if(x >= bx && x < bx + aiWidth) {
            return b;
         }
      }

      return null;
   }

   public void addAI(NpcAI ai) {
      if(this.buttonsAI.size() < 24) {
         int emptyHour = 0;

         ButtonAI b;
         for(Iterator i$ = this.buttonsAI.iterator(); i$.hasNext(); emptyHour = b.x + 1) {
            b = (ButtonAI)i$.next();
            if(b.x != emptyHour) {
               break;
            }
         }

         ai.hour = emptyHour * 1000;
         this.buttonsAI.add(new ButtonAI(ai));
         this.sortList();
         this.selected = -1;
      }

   }

   public void removeSelectedAI() {
      if(this.selected > -1) {
         this.buttonsAI.remove(this.selected);
         this.selected = -1;
      }

   }

   public NpcAI getSelectedAI() {
      return this.selected > -1?((ButtonAI)this.buttonsAI.get(this.selected)).ai:null;
   }

   public NpcAI getAI() {
      NpcAI ai = null;
      NpcAI prev = null;

      ButtonAI b;
      for(Iterator i$ = this.buttonsAI.iterator(); i$.hasNext(); prev = b.ai) {
         b = (ButtonAI)i$.next();
         b.ai.hour = b.x * 1000;
         b.ai.nextAI = null;
         if(prev != null) {
            prev.nextAI = b.ai;
         }

         if(ai == null) {
            ai = b.ai;
         }
      }

      return ai;
   }
}
