package com.chocolate.chocolateQuest.gui.guinpc;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.gui.GuiButtonIcon;
import com.chocolate.chocolateQuest.gui.GuiHumanBase;
import com.chocolate.chocolateQuest.gui.GuiInventoryPlayer;
import com.chocolate.chocolateQuest.gui.guinpc.ContainerAwakement;
import com.chocolate.chocolateQuest.gui.guinpc.GuiButtonAwakements;
import com.chocolate.chocolateQuest.gui.guinpc.InventoryAwakement;
import com.chocolate.chocolateQuest.magic.Awakements;
import com.chocolate.chocolateQuest.packets.PacketUpdateAwakement;
import com.chocolate.chocolateQuest.packets.PacketUpdateConversation;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class GuiAwakement extends GuiInventoryPlayer {

   InventoryAwakement inventory;
   GuiButtonAwakements[] enchantButtons;
   ContainerAwakement containerSlots;
   GuiButton scrollUp;
   GuiButton scrollDown;
   int UP = 1000;
   int DOWN = 1001;
   int scrollAmmount = 0;
   int type;
   int maxLevel = 0;


   public GuiAwakement(InventoryAwakement awInv, EntityPlayer player, int type, int level) {
      super(new ContainerAwakement(player.inventory, awInv, type, level), awInv, player);
      this.containerSlots = (ContainerAwakement)super.inventorySlots;
      this.inventory = awInv;
      this.type = type;
      this.maxLevel = level;
   }

   public void initGui() {
      super.initGui();
      int width = (super.width - super.xSize) / 2;
      int height = (super.height - super.ySize) / 2;
      byte ENCHANTS = 4;
      byte BUTTON_HEIGHT = 20;
      byte BUTTON_WIDTH = 120;
      this.enchantButtons = new GuiButtonAwakements[ENCHANTS];

      int scrollX;
      for(scrollX = 0; scrollX < ENCHANTS; ++scrollX) {
         this.enchantButtons[scrollX] = new GuiButtonAwakements(scrollX, width + 60, height + BUTTON_HEIGHT * scrollX, BUTTON_WIDTH, BUTTON_HEIGHT, " ");
         this.enchantButtons[scrollX].disable();
         super.buttonList.add(this.enchantButtons[scrollX]);
      }

      scrollX = width + BUTTON_WIDTH + 44;
      this.scrollUp = new GuiButtonIcon(this.UP, scrollX, height - 8, 15.0F, 7.0F, 1.0F, 0.5F, "");
      super.buttonList.add(this.scrollUp);
      this.scrollUp.visible = false;
      this.scrollDown = new GuiButtonIcon(this.DOWN, scrollX, height + BUTTON_HEIGHT * 4, 15.0F, 7.5F, 1.0F, 0.5F, "");
      super.buttonList.add(this.scrollDown);
      this.scrollDown.visible = false;
   }

   protected void actionPerformed(GuiButton button) {
      super.actionPerformed(button);
      if(button.id < 256) {
         ((ContainerAwakement)super.inventorySlots).enchantItem(button.id);
         PacketUpdateAwakement packet = new PacketUpdateAwakement(button.id);
         ChocolateQuest.channel.sendPaquetToServer(packet);
      } else if(button.id == this.UP && this.scrollAmmount > 0) {
         --this.scrollAmmount;
      } else if(button.id == this.DOWN) {
         ++this.scrollAmmount;
      }

      this.updateButtons();
   }

   protected void handleMouseClick(Slot slot, int slotID, int x, int y) {
      super.handleMouseClick(slot, slotID, x, y);
      this.updateButtons();
   }

   public void updateButtons() {
      ItemStack is = this.inventory.getStackInSlot(0);
      if(is != null) {
         ContainerAwakement var10001 = this.containerSlots;
         if(this.containerSlots.mode) {
            this.updateButtonsEnchantment(is);
         } else {
            this.updateButtonsAwakement(is);
         }
      } else {
         for(int i = 0; i < this.enchantButtons.length; ++i) {
            this.enchantButtons[i].displayString = "";
            this.enchantButtons[i].xpRequired = 0;
            this.enchantButtons[i].enabled = false;
         }
      }

   }

   public void updateButtonsEnchantment(ItemStack is) {
      int count = 0;
      int expRequired = this.containerSlots.getXPRequiredToEnchantItem();
      Enchantment[] enchantments = Enchantment.enchantmentsList;
      int itemEnchantmentsMap = enchantments.length;

      int i;
      for(i = 0; i < itemEnchantmentsMap; ++i) {
         Enchantment index = enchantments[i];
         if(index != null && index.canApply(is) && EnchantmentHelper.getEnchantmentLevel(index.effectId, is) < index.getMaxLevel()) {
            ++count;
         }
      }

      enchantments = new Enchantment[count];
      count = 0;
      Enchantment[] var14 = Enchantment.enchantmentsList;
      i = var14.length;

      int var16;
      for(var16 = 0; var16 < i; ++var16) {
         Enchantment currentID = var14[var16];
         if(currentID != null && currentID.canApply(is) && EnchantmentHelper.getEnchantmentLevel(currentID.effectId, is) < currentID.getMaxLevel()) {
            enchantments[count] = currentID;
            ++count;
         }
      }

      this.handleScroll(count);
      Map var15 = EnchantmentHelper.getEnchantments(is);

      for(i = 0; i < this.enchantButtons.length; ++i) {
         var16 = i + this.scrollAmmount;
         if(var16 < enchantments.length) {
            int var17 = enchantments[var16].effectId;
            int level = EnchantmentHelper.getEnchantmentLevel(enchantments[var16].effectId, is) + 1;
            int xp = expRequired + this.containerSlots.getXPRequiredForEnchantment(this.enchantButtons[i].id, level);
            xp -= this.containerSlots.getCatalystRebate(xp);
            this.enchantButtons[i].id = var17;
            this.enchantButtons[i].displayString = enchantments[var16].getTranslatedName(level);
            this.enchantButtons[i].xpRequired = xp;
            boolean canEnchant = (super.player.experienceLevel >= expRequired || super.player.capabilities.isCreativeMode) && xp <= this.maxLevel;
            if(canEnchant) {
               Iterator iterator = var15.keySet().iterator();

               while(iterator.hasNext()) {
                  int enchantmentID = ((Integer)iterator.next()).intValue();
                  if(enchantmentID != var17 && !enchantments[var16].canApplyTogether(Enchantment.enchantmentsList[enchantmentID])) {
                     canEnchant = false;
                  }
               }
            }

            if(!canEnchant) {
               this.enchantButtons[i].enabled = false;
            } else {
               this.enchantButtons[i].enabled = true;
            }
         } else {
            this.enchantButtons[i].disable();
         }
      }

   }

   public void updateButtonsAwakement(ItemStack is) {
      int count = 0;
      int expRequired = this.containerSlots.getXPRequiredToEnchantItem();
      Awakements[] awakements = Awakements.awekements;
      int i = awakements.length;

      int index;
      for(index = 0; index < i; ++index) {
         Awakements xp = awakements[index];
         if(xp.canBeUsedOnItem(is) && Awakements.getEnchantLevel(is, xp) < xp.getMaxLevel() && xp.canBeAddedByNPC(this.type)) {
            ++count;
         }
      }

      awakements = new Awakements[count];
      count = 0;
      Awakements[] var9 = Awakements.awekements;
      index = var9.length;

      int var10;
      for(var10 = 0; var10 < index; ++var10) {
         Awakements canEnchant = var9[var10];
         if(canEnchant.canBeUsedOnItem(is) && Awakements.getEnchantLevel(is, canEnchant) < canEnchant.getMaxLevel() && canEnchant.canBeAddedByNPC(this.type)) {
            awakements[count] = canEnchant;
            ++count;
         }
      }

      this.handleScroll(count);

      for(i = 0; i < this.enchantButtons.length; ++i) {
         index = i + this.scrollAmmount;
         if(index < awakements.length) {
            var10 = expRequired + this.containerSlots.getXPRequiredForEnchantment(this.enchantButtons[i].id, Awakements.getEnchantLevel(is, awakements[index]) + 1);
            var10 -= this.containerSlots.getCatalystRebate(var10);
            this.enchantButtons[i].id = awakements[index].id;
            this.enchantButtons[i].displayString = awakements[index].getName();
            this.enchantButtons[i].xpRequired = var10;
            boolean var11 = (super.player.experienceLevel >= expRequired || super.player.capabilities.isCreativeMode) && var10 <= this.maxLevel;
            if(!var11) {
               this.enchantButtons[i].enabled = false;
            } else {
               this.enchantButtons[i].enabled = true;
            }
         } else {
            this.enchantButtons[i].disable();
         }
      }

   }

   public void handleScroll(int entries) {
      this.scrollDown.visible = true;
      this.scrollUp.visible = true;
      if(this.scrollAmmount + 4 >= entries) {
         this.scrollAmmount = Math.max(0, entries - 4);
         this.scrollDown.visible = false;
      }

      if(this.scrollAmmount == 0) {
         this.scrollUp.visible = false;
      }

   }

   protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
      int width = (super.width - super.xSize) / 2;
      int height = (super.height - super.ySize) / 2;
      super.mc.renderEngine.bindTexture(BDHelper.guiButtonsTexture);
      int x = 10 + width;
      int y = 10 + height + 50;
      this.drawTexturedModalRect(width, height, 64, 128, 64, 80);
      this.drawIcon(36, x, y);
      this.drawIcon(86, x + 32, y - 42);
      this.drawIcon(87 + this.type, x + 32, y - 20);
      super.drawGuiContainerBackgroundLayer(par1, par2, par3);
      int enchX = x + 50;
      int enchY = y - 50;
      GuiHumanBase.drawEntity(this.inventory.npc, enchX - 40, enchY + 40);
   }

   public int getPlayerInventoryOffset() {
      return 104;
   }

   public void onGuiClosed() {
      PacketUpdateConversation packet = new PacketUpdateConversation(1, this.inventory.npc);
      ChocolateQuest.channel.sendPaquetToServer(packet);
      super.onGuiClosed();
   }
}
