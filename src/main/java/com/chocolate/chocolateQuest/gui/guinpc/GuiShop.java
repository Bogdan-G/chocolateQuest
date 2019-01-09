package com.chocolate.chocolateQuest.gui.guinpc;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.gui.GuiInventoryPlayer;
import com.chocolate.chocolateQuest.gui.guinpc.ContainerShop;
import com.chocolate.chocolateQuest.gui.guinpc.GuiScrollFiles;
import com.chocolate.chocolateQuest.gui.guinpc.InventoryShop;
import com.chocolate.chocolateQuest.gui.guinpc.ShopRecipe;
import com.chocolate.chocolateQuest.gui.slot.SlotShop;
import com.chocolate.chocolateQuest.packets.PacketUpdateConversation;
import com.chocolate.chocolateQuest.packets.PacketUpdateShopRecipe;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.io.File;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.lwjgl.opengl.GL11;

public class GuiShop extends GuiInventoryPlayer {

   InventoryShop shop;
   int selectedSlot;
   int NEW_TRADE = 0;
   int REMOVE_TRADE = 1;
   int LOAD = 2;
   int SAVE = 3;
   GuiTextField textboxFile;
   GuiScrollFiles loadFolder;


   public GuiShop(InventoryShop shop, EntityPlayer player) {
      super(new ContainerShop(player.inventory, shop), shop, player);
      this.shop = shop;
   }

   public void initGui() {
      super.initGui();
      if(super.player.capabilities.isCreativeMode) {
         int width = (super.width - super.xSize) / 2;
         int height = (super.height - super.ySize) / 2;
         GuiButton b = new GuiButton(this.NEW_TRADE, width + 110, height + 52, 60, 20, "Add trade");
         super.buttonList.add(b);
         GuiButton b1 = new GuiButton(this.REMOVE_TRADE, width + 180, height + 52, 60, 20, "Remove");
         super.buttonList.add(b1);
         File file = new File(BDHelper.getAppDir(), BDHelper.getQuestDir() + "shopRecipes/");
         if(!file.exists()) {
            file.mkdirs();
         }

         int buttonsWidth = super.width / 4;
         this.loadFolder = new GuiScrollFiles(999, 5, 10, buttonsWidth, 80, file, super.fontRendererObj);
         super.buttonList.add(this.loadFolder);
         GuiButton b2 = new GuiButton(this.LOAD, 5, 90, buttonsWidth, 20, "Load");
         super.buttonList.add(b2);
         this.textboxFile = new GuiTextField(super.fontRendererObj, 5, 140, buttonsWidth, 20);
         this.textboxFile.setMaxStringLength(25);
         GuiButton b3 = new GuiButton(this.SAVE, 5, 160, buttonsWidth, 20, "Save");
         super.buttonList.add(b3);
      }

   }

   protected void keyTyped(char c, int i) {
      if(this.textboxFile != null) {
         this.textboxFile.textboxKeyTyped(c, i);
         if(i == super.mc.gameSettings.keyBindInventory.getKeyCode() && this.textboxFile.isFocused()) {
            return;
         }
      }

      super.keyTyped(c, i);
   }

   protected void mouseClicked(int x, int y, int r) {
      super.mouseClicked(x, y, r);
      if(this.textboxFile != null) {
         this.textboxFile.mouseClicked(x, y, r);
      }

   }

   protected void handleMouseClick(Slot slot, int slotID, int x, int y) {
      if(slotID >= 36 && slotID < 54) {
         this.selectedSlot = slotID - 36;
      }

      if(this.shop.trades != null && this.selectedSlot > this.shop.trades.length) {
         this.selectedSlot = this.shop.trades.length;
      }

      if(this.textboxFile != null) {
         this.textboxFile.mouseClicked(x, y, 1);
      }

      super.handleMouseClick(slot, slotID, x, y);
   }

   protected void actionPerformed(GuiButton button) {
      PacketUpdateShopRecipe file;
      if(button.id == this.NEW_TRADE && this.shop.setShopRecipe(this.selectedSlot)) {
         file = new PacketUpdateShopRecipe(this.shop.human, this.selectedSlot);
         ChocolateQuest.channel.sendPaquetToServer(file);
      }

      if(button.id == this.REMOVE_TRADE && this.shop.trades != null && this.selectedSlot < this.shop.trades.length && this.shop.trades.length > 0) {
         this.shop.removeShopRecipe(this.selectedSlot);
         file = new PacketUpdateShopRecipe(this.shop.human, -1);
         ChocolateQuest.channel.sendPaquetToServer(file);
      }

      if(button.id == this.SAVE) {
         NBTTagCompound var9 = new NBTTagCompound();
         NBTTagList tag = new NBTTagList();

         for(int list = 0; list < this.shop.trades.length; ++list) {
            NBTTagCompound recipeAmmount = new NBTTagCompound();
            this.shop.trades[list].writeToNBTWithMapping(recipeAmmount);
            tag.appendTag(recipeAmmount);
         }

         var9.setTag("Recipes", tag);

         try {
            String var12 = this.textboxFile.getText();
            if(var12.length() > 0 && !var12.equals(" ")) {
               BDHelper.writeCompressed(var9, new File(BDHelper.getAppDir(), BDHelper.getQuestDir() + "shopRecipes/" + var12 + ".trade"));
               this.loadFolder.updateFiles();
            }
         } catch (IOException var8) {
            var8.printStackTrace();
         }
      }

      if(button.id == this.LOAD) {
         File var10 = this.loadFolder.getSelectedFile();
         if(var10 != null) {
            NBTTagCompound var11 = BDHelper.readCompressed(var10);
            if(var11 != null) {
               NBTTagList var14 = (NBTTagList)var11.getTag("Recipes");
               int var13 = var14.tagCount();
               if(var13 > 0) {
                  ShopRecipe[] recipes = new ShopRecipe[var13];

                  for(int packet = 0; packet < var13; ++packet) {
                     recipes[packet] = new ShopRecipe((ItemStack)null, (ItemStack[])null);
                     recipes[packet].readFromNBTWithMapping(var14.getCompoundTagAt(packet));
                  }

                  this.shop.human.setRecipes(recipes);
                  this.shop.updateCargo();
                  PacketUpdateShopRecipe var15 = new PacketUpdateShopRecipe(this.shop.human, -1);
                  ChocolateQuest.channel.sendPaquetToServer(var15);
               }
            }
         }
      }

      super.actionPerformed(button);
   }

   protected void drawPrice(int x, int y) {
      Slot slotUnderMouse = null;
      int px = x - (super.width - super.xSize) / 2;
      int py = y - (super.height - super.ySize) / 2;
      int posX = (super.width - super.xSize) / 2 - 28;
      int posY = 8 + (super.height - super.ySize) / 2;

      for(int slot = 36; slot < 54; ++slot) {
         Slot recipe = (Slot)super.inventorySlots.inventorySlots.get(slot);
         if(recipe.xDisplayPosition < px && recipe.xDisplayPosition + 16 > px && recipe.yDisplayPosition < py && recipe.yDisplayPosition + 16 > py) {
            slotUnderMouse = recipe;
         }
      }

      if(slotUnderMouse instanceof SlotShop) {
         SlotShop var15 = (SlotShop)slotUnderMouse;
         ShopRecipe var16 = var15.getRecipe();
         if(var16 != null) {
            ItemStack[] recipeCost = var16.costItems;
            byte x_off = 10;
            int y_off = 0;

            int i;
            for(i = 0; i < recipeCost.length; ++i) {
               super.mc.renderEngine.bindTexture(BDHelper.guiButtonsTexture);
               byte is = 36;
               this.drawIcon(is, posX + x_off, posY + y_off);
               y_off += 16;
            }

            y_off = 0;

            for(i = 0; i < recipeCost.length; ++i) {
               ItemStack var17 = recipeCost[i];
               GuiScreen.itemRender.renderItemAndEffectIntoGUI(super.fontRendererObj, super.mc.getTextureManager(), var17, posX + x_off, posY + y_off);
               if(var17.stackSize > 1) {
                  GuiScreen.itemRender.renderItemOverlayIntoGUI(super.fontRendererObj, super.mc.getTextureManager(), var17, posX + x_off, posY + y_off, "" + var17.stackSize);
               }

               y_off += 16;
            }
         }
      }

   }

   protected void drawGuiContainerBackgroundLayer(float par1, int mouseX, int mouseY) {
      super.drawGuiContainerBackgroundLayer(par1, mouseX, mouseY);
      super.mc.renderEngine.bindTexture(BDHelper.guiButtonsTexture);
      int width = (super.width - super.xSize) / 2;
      int height = (super.height - super.ySize) / 2;
      byte iconsOffset = 11;
      this.drawTexturedModalRect(width, height - 2, 64, 96, super.xSize, 32);
      this.drawTexturedModalRect(width, height - 2 + iconsOffset + 17, 64, 96 + iconsOffset, super.xSize, 21);
      byte AMMO_ICON = 36;
      int posY = 0;

      int x;
      int y;
      int y1;
      for(x = 0; x < 18; ++x) {
         y = 10 + width + x * 17 - posY * 9;
         y1 = 10 + height + posY;
         this.drawIcon(AMMO_ICON, y, y1);
         if(x % 9 == 8) {
            posY += 17;
         }
      }

      if(super.player.capabilities.isCreativeMode) {
         for(x = 0; x < 4; ++x) {
            y = 10 + width + x * 17;
            y1 = 10 + height + 44;
            if(x > 0) {
               y += 16;
            }

            byte icon = 68;
            if(x > 0) {
               icon = 52;
            }

            this.drawIcon(icon, y, y1);
         }

         x = 10 + width + this.selectedSlot % 9 * 17;
         y = 10 + height + this.selectedSlot / 9 * 17;
         this.drawIcon(68, x, y);
         if(this.textboxFile != null) {
            this.textboxFile.drawTextBox();
         }
      }

      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      this.drawPrice(mouseX, mouseY);
   }

   public int getPlayerInventoryOffset() {
      return 104;
   }

   public void onGuiClosed() {
      PacketUpdateConversation packet = new PacketUpdateConversation(1, this.shop.human);
      ChocolateQuest.channel.sendPaquetToServer(packet);
      super.onGuiClosed();
   }
}
