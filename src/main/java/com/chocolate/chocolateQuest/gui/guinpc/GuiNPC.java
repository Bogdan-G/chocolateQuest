package com.chocolate.chocolateQuest.gui.guinpc;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.gui.guinpc.GuiImportNPC;
import com.chocolate.chocolateQuest.gui.guinpc.GuiScrollConversation;
import com.chocolate.chocolateQuest.gui.guinpc.GuiScrollOptions;
import com.chocolate.chocolateQuest.packets.PacketUpdateConversation;
import com.chocolate.chocolateQuest.quest.DialogOption;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class GuiNPC extends GuiScreen {

   String currentName = "";
   EntityHumanNPC npc;
   EntityPlayer player;
   private DialogOption selectedDialog;
   GuiScrollOptions options;
   final int ANSWER = 100;
   int[] breadcrumbs;
   public static GuiNPC instance;
   GuiButton[] buttonAnswer;
   int EDIT_DIALOG = 0;
   int EDIT_NPC = 1;
   int EDIT_INVENTORY = 2;
   int IMPORT = 3;
   int AI_POSITIONS = 4;
   String lang;


   public GuiNPC(EntityHumanNPC e, EntityPlayer player) {
      this.npc = e;
      this.player = player;
      instance = this;
      this.breadcrumbs = new int[0];
      this.selectedDialog = this.npc.conversation;
   }

   public void setDialogOption(DialogOption option, int[] breadCrumbs) {
      this.selectedDialog = option;
      this.breadcrumbs = breadCrumbs;
      this.selectedDialog.readText(this.lang);
      this.selectedDialog.replaceKeys(this.player.getCommandSenderName(), this.npc);
      String[] newOptions;
      if(this.selectedDialog.options != null) {
         newOptions = new String[this.selectedDialog.options.length];

         for(int i = 0; i < newOptions.length; ++i) {
            this.selectedDialog.options[i].readText(this.lang);
            this.selectedDialog.options[i].replaceKeys(this.player.getCommandSenderName(), this.npc);
            newOptions[i] = this.selectedDialog.options[i].prompt;
         }
      } else {
         newOptions = new String[0];
      }

      if(this.options != null) {
         if(newOptions.length == 0) {
            this.options.enabled = false;
         }

         this.options.setModeNames(newOptions);
      }

   }

   public void initGui() {
      super.initGui();
      this.lang = super.mc.getLanguageManager().getCurrentLanguage().getLanguageCode();
      if(this.player.capabilities.isCreativeMode) {
         byte newOptions = 20;
         GuiButton i = new GuiButton(this.EDIT_DIALOG, 0, 0, super.width / 6, 20, "Edit dialogs");
         super.buttonList.add(i);
         i = new GuiButton(this.EDIT_NPC, 0, newOptions, super.width / 6, 20, "Edit stats");
         super.buttonList.add(i);
         i = new GuiButton(this.EDIT_INVENTORY, 0, newOptions * 2, super.width / 6, 20, "Inventory");
         super.buttonList.add(i);
         i = new GuiButton(this.IMPORT, 0, newOptions * 3, super.width / 6, 20, "Import/Export");
         super.buttonList.add(i);
         i = new GuiButton(this.AI_POSITIONS, 0, newOptions * 4, super.width / 6, 20, "Edit AI");
         super.buttonList.add(i);
      }

      if(this.selectedDialog.options != null) {
         String[] var3 = new String[this.selectedDialog.options.length];

         for(int var4 = 0; var4 < var3.length; ++var4) {
            var3[var4] = this.selectedDialog.options[var4].name;
         }

         this.options = new GuiScrollConversation(100, super.width - 140, super.height - super.height / 4, 140, super.height / 4, var3, super.fontRendererObj, 0);
         super.buttonList.add(this.options);
      }

      this.setDialogOption(this.selectedDialog, this.breadcrumbs);
   }

   protected void actionPerformed(GuiButton button) {
      super.actionPerformed(button);
      int[] breadcrumbsTemp = null;
      PacketUpdateConversation packet;
      if(button.id == this.EDIT_DIALOG) {
         packet = new PacketUpdateConversation(2, this.npc);
         ChocolateQuest.channel.sendPaquetToServer(packet);
      } else if(button.id == this.EDIT_NPC) {
         packet = new PacketUpdateConversation(3, this.npc);
         ChocolateQuest.channel.sendPaquetToServer(packet);
      } else if(button.id == this.IMPORT) {
         super.mc.displayGuiScreen(new GuiImportNPC(this.npc, this));
      } else if(button.id == this.EDIT_INVENTORY) {
         packet = new PacketUpdateConversation(4, this.npc);
         ChocolateQuest.channel.sendPaquetToServer(packet);
      } else if(button.id == this.AI_POSITIONS) {
         packet = new PacketUpdateConversation(5, this.npc);
         ChocolateQuest.channel.sendPaquetToServer(packet);
      } else if(this.selectedDialog != null && this.selectedDialog.options != null) {
         int var5 = this.options.selectedMode;
         if(var5 < this.selectedDialog.options.length) {
            breadcrumbsTemp = new int[this.breadcrumbs.length + 1];

            for(int i = 0; i < this.breadcrumbs.length; ++i) {
               breadcrumbsTemp[i] = this.breadcrumbs[i];
            }

            breadcrumbsTemp[this.breadcrumbs.length] = this.selectedDialog.options[var5].id;
         }
      }

      if(breadcrumbsTemp != null) {
         packet = new PacketUpdateConversation(breadcrumbsTemp, this.npc);
         ChocolateQuest.channel.sendPaquetToServer(packet);
      }

   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public void onGuiClosed() {
      instance = null;
      super.onGuiClosed();
   }

   public void drawScreen(int x, int y, float fl) {
      int posY = super.height - super.height / 4;
      byte posX = 5;
      if(this.selectedDialog.text != null) {
         posY = Math.min(posY, super.height - this.selectedDialog.text.length * super.fontRendererObj.FONT_HEIGHT);
      }

      posY = Math.min(posY, posY - (super.fontRendererObj.FONT_HEIGHT + 2) * (this.selectedDialog.text.length - 5));
      drawRect(0, posY, super.width, super.height, -1147561575);
      super.drawScreen(x, y, fl);
      this.drawString(super.fontRendererObj, this.npc.displayName, posX, posY - 4 - super.fontRendererObj.FONT_HEIGHT, 8947967);
      if(this.selectedDialog.text != null) {
         for(int i = 0; i < this.selectedDialog.text.length; ++i) {
            String currentText = this.selectedDialog.text[i];
            drawTextWithSpecialChars(currentText, posX, posY + i * (super.fontRendererObj.FONT_HEIGHT + 2), super.fontRendererObj, 16777215);
         }
      }

   }

   public static void drawTextWithSpecialChars(String currentText, int posX, int posY, FontRenderer fontRendererObj, int color) {
      if(currentText.contains("[") && currentText.contains("]") && currentText.indexOf("[") < currentText.indexOf("]")) {
         Minecraft mc = Minecraft.getMinecraft();
         String s1 = currentText.substring(0, currentText.indexOf("["));
         String s2 = currentText.substring(currentText.indexOf("]") + 1);
         String itemName = currentText.substring(currentText.indexOf("[") + 1, currentText.indexOf("]"));
         int x = posX + fontRendererObj.getStringWidth(s1);
         Object item = Item.itemRegistry.getObject(itemName);
         if(item != null) {
            ItemStack is;
            if(item instanceof Block) {
               is = new ItemStack((Block)item);
            } else {
               is = new ItemStack((Item)item);
            }

            GuiScreen.itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), is, x, posY - 4);
            x += 16;
            GL11.glDisable(2896);
         }

         currentText = s1;
         drawTextWithSpecialChars(s2, x, posY, fontRendererObj, color);
      }

      fontRendererObj.drawStringWithShadow(currentText, posX, posY, color);
   }

   protected void mouseClicked(int x, int y, int button) {
      super.mouseClicked(x, y, button);
   }

   protected void keyTyped(char c, int i) {
      byte esc = 1;
      if(i == esc) {
         PacketUpdateConversation UP = new PacketUpdateConversation(1, this.npc);
         ChocolateQuest.channel.sendPaquetToServer(UP);
      }

      super.keyTyped(c, i);
      short UP1 = 200;
      int FORWARD = Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode();
      short DOWN = 208;
      int BACK = Minecraft.getMinecraft().gameSettings.keyBindBack.getKeyCode();
      byte INTRO = 28;
      short INSERT = 206;
      short RIGHT = 205;
      int MOVE_RIGHT = Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode();
      if(this.options != null) {
         if(i != DOWN && i != BACK) {
            if(i != UP1 && i != FORWARD) {
               if((i == INTRO || i == INSERT || i == RIGHT || i == MOVE_RIGHT) && this.selectedDialog.options != null && this.options.selectedMode >= 0 && this.options.selectedMode < this.selectedDialog.options.length) {
                  this.actionPerformed(this.options);
               }
            } else if(this.options.selectedMode - 1 >= 0) {
               this.options.scrollUp();
            }
         } else if(this.options.selectedMode + 1 < this.options.modeNames.length) {
            this.options.scrollDown();
         }
      }

   }
}
