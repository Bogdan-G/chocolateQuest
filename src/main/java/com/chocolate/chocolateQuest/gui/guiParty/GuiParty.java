package com.chocolate.chocolateQuest.gui.guiParty;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.client.KeyBindings;
import com.chocolate.chocolateQuest.entity.EntityCursor;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.gui.guiParty.GuiButtonPartyMember;
import com.chocolate.chocolateQuest.gui.guiParty.PartyManager;
import com.chocolate.chocolateQuest.items.ItemStaffBase;
import com.chocolate.chocolateQuest.packets.PacketClientAsks;
import com.chocolate.chocolateQuest.packets.PacketPartyCreation;
import com.chocolate.chocolateQuest.utils.HelperPlayer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class GuiParty extends GuiScreen {

   MovingObjectPosition playerMop;
   static final int BUTTONS_MEMBER_ID = 0;
   static final int BUTTON_RESET_ID = 1;
   static final int BUTTON_CREATE_NEW_PARTY_ID = 2;
   static final int BUTTON_ALL_MEMBERS_ID = 3;
   static final int BUTTON_EDIT_SPELLS = 4;
   static GuiButtonPartyMember buttonAllMembers;
   GuiButton buttonCreate;
   EntityCursor selected;


   public void initGui() {
      super.initGui();
      EntityClientPlayerMP player = super.mc.thePlayer;
      this.playerMop = HelperPlayer.getMovingObjectPositionFromPlayer(player, super.mc.theWorld, 120.0D);
      if(this.playerMop == null) {
         this.playerMop = new MovingObjectPosition(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ), 0, Vec3.createVectorHelper(0.0D, 0.0D, 0.0D));
      }

      EntityCursor dist;
      if(this.playerMop.entityHit != null) {
         dist = new EntityCursor(player.worldObj, this.playerMop.entityHit, 16711680, this);
         player.worldObj.spawnEntityInWorld(dist);
      } else {
         dist = new EntityCursor(player.worldObj, (double)this.playerMop.blockX + 0.5D, (double)(this.playerMop.blockY + 1), (double)this.playerMop.blockZ + 0.5D, player.rotationYawHead);
         player.worldObj.spawnEntityInWorld(dist);
      }

      double var14 = 0.0D;
      List list = PartyManager.instance.getMembers();

      EntityHumanBase members;
      for(Iterator screenSize = list.iterator(); screenSize.hasNext(); var14 = Math.max(var14, (double)members.partyDistanceToLeader)) {
         members = (EntityHumanBase)screenSize.next();
      }

      int var15 = Math.min(super.width - 50, super.height - 15);
      var14 = (double)(var15 / 2) / var14;
      int var16 = 0;

      for(int entities = 0; entities < list.size(); ++entities) {
         EntityHumanBase buttonPosY = (EntityHumanBase)list.get(entities);
         if(buttonPosY != null) {
            ++var16;
            GuiButtonPartyMember buttonReset;
            if(PartyManager.instance.getButton(entities) != null) {
               buttonReset = (GuiButtonPartyMember)PartyManager.instance.getButton(entities);
               buttonReset.playerMop = this.playerMop;
            } else {
               double is = (double)buttonPosY.partyPositionAngle * 3.1416D / 180.0D;
               int x = (int)(Math.sin(is) * (double)buttonPosY.partyDistanceToLeader * var14);
               int y = (int)(-Math.cos(is) * (double)buttonPosY.partyDistanceToLeader * var14);
               buttonReset = new GuiButtonPartyMember(0, super.width / 2 - 50 + x, super.height / 2 - 10 + y, buttonPosY);
               PartyManager.instance.setButton(entities, buttonReset);
            }

            buttonReset.init(this.playerMop);
            super.buttonList.add(buttonReset);
            EntityCursor var21 = new EntityCursor(player.worldObj, buttonPosY, '\uff00', this);
            player.worldObj.spawnEntityInWorld(var21);
            if(buttonReset.isSelected) {
               this.selected = new EntityCursor(player.worldObj, buttonPosY, 255, this);
               player.worldObj.spawnEntityInWorld(this.selected);
            }
         }
      }

      EntityHumanBase[] var17 = PartyManager.instance.getEntitiesArray();
      if(var17.length > 0) {
         if(buttonAllMembers == null) {
            buttonAllMembers = new GuiButtonPartyMember(3, super.width / 2 - 50, super.height / 2 - 10, var17);
         }

         buttonAllMembers.entity = var17;
         buttonAllMembers.init(this.playerMop);
         super.buttonList.add(buttonAllMembers);
      }

      byte var19 = 0;
      GuiButton var20 = new GuiButton(1, 0, var19, 40, 12, (new ChatComponentTranslation("strings.reset", new Object[0])).getFormattedText());
      super.buttonList.add(var20);
      int var18 = var19 + 12;
      if(var16 > 1) {
         this.buttonCreate = new GuiButton(2, 0, var18, 40, 12, (new ChatComponentTranslation("strings.createparty", new Object[0])).getFormattedText());
         super.buttonList.add(this.buttonCreate);
         var18 += 12;
      }

      if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemStaffBase) {
         ItemStack var22 = player.getCurrentEquippedItem();
         if(var22.stackTagCompound != null && !var22.stackTagCompound.hasKey("locked")) {
            GuiButton button = new GuiButton(4, 0, var18, 40, 12, (new ChatComponentTranslation("strings.spells", new Object[0])).getFormattedText());
            super.buttonList.add(button);
            var18 += 12;
         }
      }

   }

   protected void moveButtons() {
      boolean id = false;
      double dist = 0.0D;
      List list = PartyManager.instance.getMembers();

      EntityHumanBase i;
      for(Iterator screenSize = list.iterator(); screenSize.hasNext(); dist = Math.max(dist, (double)i.partyDistanceToLeader)) {
         i = (EntityHumanBase)screenSize.next();
      }

      int var13 = Math.min(super.width - 50, super.height - 15);
      dist = (double)(var13 / 2) / dist;

      for(int var14 = 0; var14 < list.size(); ++var14) {
         EntityHumanBase e = (EntityHumanBase)list.get(var14);
         if(e != null && PartyManager.instance.getButton(var14) != null) {
            double angleRads = (double)e.partyPositionAngle * 3.1416D / 180.0D;
            int x = (int)(Math.sin(angleRads) * (double)e.partyDistanceToLeader * dist);
            int y = (int)(-Math.cos(angleRads) * (double)e.partyDistanceToLeader * dist);
            GuiButtonPartyMember button = (GuiButtonPartyMember)PartyManager.instance.getButton(var14);
            button.xPosition = super.width / 2 - 50 + x;
            button.yPosition = super.height / 2 - 10 + y;
            button.moveActionButtons();
            button.isSelected = false;
         }
      }

   }

   protected void actionPerformed(GuiButton button) {
      super.actionPerformed(button);
      if(button.id == 1) {
         this.moveButtons();
      }

      if(button.id == 2) {
         EntityHumanBase[] packet = PartyManager.instance.getEntitiesArray();
         if(packet.length > 0) {
            GuiButtonPartyMember currentButton = this.getSelectedButton();
            if(currentButton != null) {
               EntityHumanBase leader = currentButton.getEntity();
               int[] newPartyMembers = new int[packet.length - 1];
               int cont = 0;

               for(int packet1 = 0; packet1 < packet.length; ++packet1) {
                  if(packet[packet1] != leader) {
                     newPartyMembers[cont++] = packet[packet1].getEntityId();
                     PartyManager.instance.removeMember(packet[packet1]);
                  }
               }

               PacketPartyCreation var11 = new PacketPartyCreation(leader.getEntityId(), newPartyMembers);
               ChocolateQuest.channel.sendPaquetToServer(var11);
               super.mc.displayGuiScreen((GuiScreen)null);
            }
         }
      }

      if(button.id == 0 || button.id == 3) {
         for(int var8 = 0; var8 < super.buttonList.size(); ++var8) {
            GuiButton var10 = (GuiButton)super.buttonList.get(var8);
            if(var10.id == 0) {
               ((GuiButtonPartyMember)var10).isSelected = false;
            }
         }

         buttonAllMembers.isSelected = false;
         this.setSelectedButton((GuiButtonPartyMember)button);
      }

      if(button.id == 4) {
         PacketClientAsks var9 = new PacketClientAsks((byte)0);
         ChocolateQuest.channel.sendPaquetToServer(var9);
      }

   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public void drawScreen(int x, int y, float f) {
      super.drawScreen(x, y, f);
      ArrayList list = new ArrayList();
      if(this.buttonCreate != null && x >= this.buttonCreate.xPosition && y >= this.buttonCreate.yPosition && x < this.buttonCreate.xPosition + this.buttonCreate.width && y < this.buttonCreate.yPosition + this.buttonCreate.height) {
         list.add((new ChatComponentTranslation("strings.createpartylong", new Object[0])).getFormattedText());
      }

      for(int i = 0; i < super.buttonList.size(); ++i) {
         GuiButton b = (GuiButton)super.buttonList.get(i);
         if(b instanceof GuiButtonPartyMember) {
            GuiButtonPartyMember button = (GuiButtonPartyMember)b;
            String text = ((GuiButtonPartyMember)b).getHoveringText(x, y);
            if(text != null) {
               list.add((new ChatComponentTranslation(text, new Object[0])).getFormattedText());
            }
         }
      }

      if(!list.isEmpty()) {
         this.drawHoveringText(list, x - 10, y + 20, super.fontRendererObj);
      }

   }

   protected void keyTyped(char c, int keyCode) {
      super.keyTyped(c, keyCode);
      if(KeyBindings.partyKey.getKeyCode() == keyCode) {
         super.mc.displayGuiScreen((GuiScreen)null);
         super.mc.setIngameFocus();
      } else if(keyCode == 16 || keyCode == 17 || keyCode == 18 || keyCode == 19) {
         int key = keyCode - 16;
         GuiButtonPartyMember button = this.getSelectedButton();
         if(button == null) {
            button = buttonAllMembers;
         }

         if(button != null) {
            button.sendActionToServer(button.actions[key]);
         }
      }

   }

   protected void setSelectedButton(GuiButtonPartyMember button) {
      button.isSelected = true;
      if(this.selected != null) {
         this.selected.forceDead();
      }

      this.selected = null;
      if(button != buttonAllMembers) {
         EntityHumanBase e = button.getEntity();
         this.selected = new EntityCursor(e.worldObj, e, 255, this);
         e.worldObj.spawnEntityInWorld(this.selected);
      }

   }

   protected GuiButtonPartyMember getSelectedButton() {
      if(buttonAllMembers != null && buttonAllMembers.isSelected) {
         return buttonAllMembers;
      } else {
         for(int i = 0; i < super.buttonList.size(); ++i) {
            GuiButton currentButton = (GuiButton)super.buttonList.get(i);
            if(currentButton.id == 0 && ((GuiButtonPartyMember)currentButton).isSelected) {
               return (GuiButtonPartyMember)currentButton;
            }
         }

         return null;
      }
   }
}
