package com.chocolate.chocolateQuest.gui.guinpc;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.gui.GuiButtonTextBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

public class GuiButtonSoulBottleSearch extends GuiButtonTextBox {

   GuiScreen holder;
   ItemStack[] cachedBottles;
   String displayName = "";
   int textBoxHeight = 16;
   public NBTTagCompound entityTag;


   public GuiButtonSoulBottleSearch(int id, int x, int y, int width, int height, FontRenderer font) {
      super(id, x, y, width, height, font);
      this.updateItems();
      super.textbox.setEnabled(false);
      super.textbox.setVisible(false);
   }

   public void drawButton(Minecraft mc, int x, int y) {
      int despX = 0;
      int despY = 0;
      drawRect(super.xPosition - 1, super.yPosition + despY + this.textBoxHeight, super.xPosition + super.width + 1, super.yPosition + despY + this.textBoxHeight + super.height, -7829368);
      drawRect(super.xPosition + despX - 1, super.yPosition, super.xPosition + super.width + 1, super.yPosition + this.textBoxHeight, -16777216);
      super.fontRenderer.drawString(this.displayName, super.xPosition + 5, super.yPosition + super.fontRenderer.FONT_HEIGHT / 2, 16777215);
      if(this.cachedBottles != null) {
         for(int i = 0; i < this.cachedBottles.length; ++i) {
            if(this.cachedBottles[i] != null) {
               ItemStack cargoItem = this.cachedBottles[i];
               float scale = 16.0F;
               RenderItem r = new RenderItem();
               r.renderItemIntoGUI(super.fontRenderer, mc.getTextureManager(), cargoItem, super.xPosition + despX, super.yPosition + despY + 16);
               despX = (int)((float)despX + scale);
               if(despX + this.textBoxHeight > super.width) {
                  despX = 0;
                  despY = (int)((float)despY + scale);
                  if(despY + this.textBoxHeight > super.height) {
                     break;
                  }
               }
            }
         }
      }

      GL11.glDisable(2896);
   }

   public boolean mousePressed(Minecraft mc, int x, int y) {
      boolean b = super.mousePressed(mc, x, y);
      if(b) {
         ItemStack is = this.getCurrentItem(x, y);
         if(is != null) {
            this.displayName = is.stackTagCompound.getString("itemName");
            super.textbox.setText(this.displayName);
            this.entityTag = is.stackTagCompound;
         }
      }

      return b;
   }

   protected void updateItems() {
      EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
      InventoryPlayer inventory = player.inventory;
      int countBottles = 0;

      int currentBottle;
      for(currentBottle = 0; currentBottle < inventory.getSizeInventory(); ++currentBottle) {
         ItemStack i = inventory.getStackInSlot(currentBottle);
         if(i != null && i.getItem() == ChocolateQuest.soulBottle && i.stackTagCompound != null) {
            ++countBottles;
         }
      }

      currentBottle = 0;
      this.cachedBottles = new ItemStack[countBottles];

      for(int var7 = 0; var7 < inventory.getSizeInventory(); ++var7) {
         ItemStack is = inventory.getStackInSlot(var7);
         if(is != null && is.getItem() == ChocolateQuest.soulBottle && is.stackTagCompound != null) {
            this.cachedBottles[currentBottle++] = is;
         }
      }

   }

   public ItemStack getCurrentItem(int i, int j) {
      i -= super.xPosition;
      j -= super.yPosition + this.textBoxHeight;
      if(this.cachedBottles != null && j >= 0) {
         int x = i / 16;
         int y = j / 16;
         int index = y * (super.width / 16) + x;
         if(index < this.cachedBottles.length) {
            return this.cachedBottles[index];
         }
      }

      return null;
   }

   public String getValue() {
      return super.textbox.getText();
   }
}
