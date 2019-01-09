package com.chocolate.chocolateQuest.gui.guinpc;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.gui.GuiButtonIcon;
import com.chocolate.chocolateQuest.gui.GuiButtonItemSearch;
import com.chocolate.chocolateQuest.gui.GuiButtonTextBox;
import com.chocolate.chocolateQuest.gui.GuiButtonTextBoxInteger;
import com.chocolate.chocolateQuest.gui.guinpc.GuiButtonSoulBottleSearch;
import com.chocolate.chocolateQuest.gui.guinpc.GuiScrollOptions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Mouse;

public class GuiLinked extends GuiScreen {

   GuiScreen prevGui;
   GuiButton frontButton;
   int STATIC_ID;
   GuiButton backButton;
   GuiButton scrollUp;
   GuiButton scrollDown;
   List textFieldList;
   int scrollAmmount;
   int maxScrollAmmount;
   boolean hasNavigationMenu;


   public GuiLinked() {
      this.STATIC_ID = 1000;
      this.scrollAmmount = 0;
      this.maxScrollAmmount = 100;
      this.hasNavigationMenu = true;
      this.hasNavigationMenu = false;
      this.maxScrollAmmount = 0;
   }

   public GuiLinked(GuiScreen prevGui) {
      this.STATIC_ID = 1000;
      this.scrollAmmount = 0;
      this.maxScrollAmmount = 100;
      this.hasNavigationMenu = true;
      this.prevGui = prevGui;
   }

   public GuiLinked(GuiScreen prevGui, EntityHumanNPC npc) {
      this(prevGui);
   }

   public void initGui() {
      super.initGui();
      this.scrollAmmount = 0;
      this.textFieldList = new ArrayList();
      this.backButton = new GuiButtonIcon(this.STATIC_ID, 3, 10, 15.0F, 6.0F, 1.0F, 1.0F, "");
      super.buttonList.add(this.backButton);
      this.scrollUp = new GuiButtonIcon(this.STATIC_ID, 3, 30, 15.0F, 7.0F, 1.0F, 0.5F, "");
      super.buttonList.add(this.scrollUp);
      this.scrollDown = new GuiButtonIcon(this.STATIC_ID, 3, 39, 15.0F, 7.5F, 1.0F, 0.5F, "");
      super.buttonList.add(this.scrollDown);
   }

   protected void keyTyped(char c, int i) {
      if(i == 1) {
         if(this.frontButton != null) {
            this.setFrontButton((GuiButton)null);
         } else {
            this.closeGUI();
         }

      } else {
         boolean textBoxFoxused = false;
         Iterator i$ = this.textFieldList.iterator();

         while(i$.hasNext()) {
            GuiTextField field = (GuiTextField)i$.next();
            field.textboxKeyTyped(c, i);
            if(field.isFocused()) {
               textBoxFoxused = true;
            }
         }

         if(!textBoxFoxused) {
            if(i == 208 || i == 31) {
               this.scrollDown(super.height / 2);
            }

            if(i == 200 || i == 17) {
               this.scrollUp(super.height / 2);
            }
         }

      }
   }

   protected void mouseClicked(int x, int y, int r) {
      if(this.frontButton != null) {
         if(this.frontButton.mousePressed(super.mc, x, y)) {
            this.actionPerformed(this.frontButton);
         }

         if(this.backButton != null && this.backButton.mousePressed(super.mc, x, y)) {
            this.actionPerformed(this.backButton);
         }

      } else {
         Iterator i$ = this.textFieldList.iterator();

         while(i$.hasNext()) {
            GuiTextField field = (GuiTextField)i$.next();
            field.mouseClicked(x, y, r);
         }

         super.mouseClicked(x, y, r);
      }
   }

   public void handleMouseInput() {
      super.handleMouseInput();
      int k = Mouse.getEventDWheel();
      if(this.frontButton instanceof GuiScrollOptions) {
         if(k > 0) {
            ((GuiScrollOptions)this.frontButton).scrollUp();
         } else if(k < 0) {
            ((GuiScrollOptions)this.frontButton).scrollDown();
         }
      } else if(k > 0) {
         this.scrollUp(20);
      } else if(k < 0) {
         this.scrollDown(20);
      }

   }

   public void drawScreen(int x, int y, float fl) {
      this.drawBackground(0);
      Iterator i$ = this.textFieldList.iterator();

      while(i$.hasNext()) {
         GuiTextField field = (GuiTextField)i$.next();
         field.drawTextBox();
      }

      super.drawScreen(x, y, fl);
   }

   protected void actionPerformed(GuiButton button) {
      if(this.backButton == button) {
         if(this.frontButton != null) {
            this.setFrontButton((GuiButton)null);
         } else {
            this.closeGUI();
         }
      }

      if(this.scrollUp == button) {
         this.scrollUp(super.height / 2);
      }

      if(this.scrollDown == button) {
         this.scrollDown(super.height / 2);
      }

   }

   protected void setFrontButton(GuiButton button) {
      if(button == null) {
         super.buttonList.remove(this.frontButton);
         this.frontButton = null;
      } else {
         this.frontButton = button;
         super.buttonList.add(this.frontButton);
         this.frontButton.enabled = false;
      }

   }

   public void updateScreen() {
      if(this.frontButton != null) {
         this.frontButton.enabled = true;
      }

      super.updateScreen();
   }

   protected void scrollUp(int ammount) {
      if(this.scrollAmmount - ammount < 0) {
         ammount = this.scrollAmmount;
      }

      Iterator i$ = super.buttonList.iterator();

      while(i$.hasNext()) {
         Object field = i$.next();
         GuiButton b = (GuiButton)field;
         if(b.id != this.STATIC_ID) {
            b.yPosition += ammount;
         }
      }

      GuiTextField field1;
      for(i$ = this.textFieldList.iterator(); i$.hasNext(); field1.yPosition += ammount) {
         field1 = (GuiTextField)i$.next();
      }

      this.scrollAmmount -= ammount;
   }

   protected void scrollDown(int ammount) {
      if(this.scrollAmmount + ammount >= this.maxScrollAmmount) {
         ammount = this.maxScrollAmmount - this.scrollAmmount;
      }

      Iterator i$ = super.buttonList.iterator();

      while(i$.hasNext()) {
         Object field = i$.next();
         GuiButton b = (GuiButton)field;
         if(b.id != this.STATIC_ID) {
            b.yPosition -= ammount;
         }
      }

      GuiTextField field1;
      for(i$ = this.textFieldList.iterator(); i$.hasNext(); field1.yPosition -= ammount) {
         field1 = (GuiTextField)i$.next();
      }

      this.scrollAmmount += ammount;
   }

   public GuiButtonTextBox addButon(int x, int y, int selector, int buttonWidth, int buttonHeight) {
      Object button;
      if(selector == 1) {
         button = new GuiButtonTextBoxInteger(0, x, y, buttonWidth, buttonHeight, super.fontRendererObj);
      } else if(selector == 3) {
         button = new GuiButtonItemSearch(0, x, y, buttonWidth, buttonHeight * 2, super.fontRendererObj);
         super.buttonList.add(button);
      } else if(selector == 4) {
         button = new GuiButtonSoulBottleSearch(0, x, y, buttonWidth, buttonHeight * 2, super.fontRendererObj);
         super.buttonList.add(button);
      } else {
         button = new GuiButtonTextBox(0, x, y, buttonWidth, buttonHeight, super.fontRendererObj);
      }

      super.buttonList.add(button);
      this.textFieldList.add(((GuiButtonTextBox)button).textbox);
      return (GuiButtonTextBox)button;
   }

   public void closeGUI() {
      super.mc.displayGuiScreen(this.prevGui);
   }

   public String[] getNames(Object[] array) {
      String[] dialogNames;
      if(array != null) {
         dialogNames = new String[array.length];

         for(int i = 0; i < dialogNames.length; ++i) {
            dialogNames[i] = array[i].toString();
         }
      } else {
         dialogNames = new String[0];
      }

      return dialogNames;
   }

   public String[] getNames(List list) {
      return list == null?new String[0]:this.getNames(list.toArray());
   }
}
