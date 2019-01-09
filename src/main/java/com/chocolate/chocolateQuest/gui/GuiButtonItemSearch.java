package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.gui.GuiButtonTextBox;
import com.chocolate.chocolateQuest.gui.ItemTextField;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class GuiButtonItemSearch extends GuiButtonTextBox {

   GuiScreen holder;
   ItemStack[] is;
   String currentName = "";
   int textBoxHeight = 18;


   public GuiButtonItemSearch(int id, int x, int y, int width, int height, FontRenderer font) {
      super(id, x, y, width, height, font);
      super.textbox = new ItemTextField(font, x, y, width, 20, this);
      super.textbox.setMaxStringLength(255);
      EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
      InventoryPlayer inventory = player.inventory;
      int countBottles = 0;

      int currentBottle;
      for(currentBottle = 0; currentBottle < inventory.getSizeInventory(); ++currentBottle) {
         ItemStack i = inventory.getStackInSlot(currentBottle);
         if(i != null) {
            ++countBottles;
         }
      }

      currentBottle = 0;
      this.is = new ItemStack[countBottles];

      for(int var13 = 0; var13 < inventory.getSizeInventory(); ++var13) {
         ItemStack is = inventory.getStackInSlot(var13);
         if(is != null) {
            this.is[currentBottle++] = is;
         }
      }

      super.height += this.textBoxHeight;
   }

   public void drawButton(Minecraft mc, int x, int y) {
      int despX = 0;
      int despY = 0;
      drawRect(super.xPosition + despX - 1, super.yPosition + despY + this.textBoxHeight, super.xPosition + despX + super.width + 1, super.yPosition + despY + super.height, -7829368);
      if(this.is != null) {
         for(int i = 0; i < this.is.length; ++i) {
            if(this.is[i] != null) {
               ItemStack cargoItem = this.is[i];
               float scale = 16.0F;
               RenderItem r = new RenderItem();
               r.renderItemAndEffectIntoGUI(super.fontRenderer, mc.getTextureManager(), cargoItem, super.xPosition + despX, super.yPosition + despY + 16);
               despX = (int)((float)despX + scale);
               if(despX + this.textBoxHeight > super.width) {
                  despX = 0;
                  despY = (int)((float)despY + scale);
                  if(despY > super.height) {
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
            String text = Item.itemRegistry.getNameForObject(is.getItem());
            if(is.stackTagCompound != null) {
               text = text + " " + is.stackSize + " " + is.getItemDamage() + " " + is.stackTagCompound.toString();
            } else if(is.getItemDamage() > 0 || is.stackSize > 1) {
               text = text + " " + is.stackSize + " " + is.getItemDamage();
            }

            super.textbox.setText(text);
         }
      }

      return b;
   }

   protected void updateItem() {
      String itemText = super.textbox.getText();
      String[] textArray = itemText.split(" ");
      if(textArray.length > 1) {
         ItemStack name = BDHelper.getStackFromString(itemText);
         if(name != null) {
            this.is = new ItemStack[]{name};
         }
      } else if(textArray.length > 0) {
         String var9 = textArray[0];
         ArrayList list = new ArrayList();
         Set registeredItems = Item.itemRegistry.getKeys();
         Iterator index = registeredItems.iterator();

         while(index.hasNext()) {
            Object o = index.next();
            if(o.toString().contains(var9)) {
               Item currentItem = (Item)Item.itemRegistry.getObject(o);
               currentItem.getSubItems(currentItem, currentItem.getCreativeTab(), list);
            }
         }

         this.is = new ItemStack[list.size()];

         for(int var10 = 0; var10 < list.size(); ++var10) {
            this.is[var10] = (ItemStack)list.get(var10);
         }
      }

   }

   public ItemStack getCurrentItem(int i, int j) {
      i -= super.xPosition;
      j -= super.yPosition + this.textBoxHeight;
      if(this.is != null && j >= 0) {
         int x = i / 16;
         int y = j / 16;
         int index = y * (super.width / 16) + x;
         if(index < this.is.length) {
            return this.is[index];
         }
      }

      return null;
   }
}
