package com.chocolate.chocolateQuest.gui.guiParty;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.gui.GuiHumanBase;
import com.chocolate.chocolateQuest.gui.guiParty.ButtonAction;
import com.chocolate.chocolateQuest.gui.guiParty.PartyAction;
import com.chocolate.chocolateQuest.packets.PacketPartyMemberAction;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.opengl.GL11;

public class GuiButtonPartyMember extends GuiButton {

   MovingObjectPosition playerMop;
   EntityHumanBase[] entity;
   ButtonAction[] actions;
   ButtonAction[] allActions;
   boolean extended;
   boolean pressed;
   static final int ENTITY_AVATAR_SEPARATION = 8;
   static final int ICON_SIZE = 16;
   static final int ICON_SEPARATION = 2;
   static final int EXTENSION_BUTTON_X = 90;
   static final int EXTENSION_BUTTON_Y = 9;
   static final int EXTENSION_BUTTON_SIZE = 7;
   int xOffset;
   int yOffset;
   public boolean isSelected;
   boolean holdingButton;
   int buttonHeld;
   boolean renderEntity;


   public GuiButtonPartyMember(int id, int x, int y, EntityHumanBase entity) {
      super(id, x, y, "");
      this.actions = new ButtonAction[4];
      this.allActions = null;
      this.xOffset = 0;
      this.yOffset = 0;
      this.holdingButton = false;
      this.buttonHeld = 0;
      this.renderEntity = true;
      this.entity = new EntityHumanBase[]{entity};
      super.width = 100;
      this.actions[0] = new ButtonAction(0, 0, PartyAction.move.id);
      this.actions[1] = new ButtonAction(0, 0, PartyAction.follow.id);
      this.actions[2] = new ButtonAction(0, 0, PartyAction.formation.id);
      this.actions[3] = new ButtonAction(0, 0, PartyAction.flee.id);
   }

   public GuiButtonPartyMember(int id, int x, int y, EntityHumanBase[] entities) {
      this(id, x, y, entities[0]);
      this.entity = entities;
      this.renderEntity = false;
   }

   public void init(MovingObjectPosition playerMop) {
      this.playerMop = playerMop;
      ButtonAction defaultAction = null;
      if(playerMop.entityHit != null) {
         if(this.getEntity().isSuitableMount(playerMop.entityHit)) {
            defaultAction = new ButtonAction(0, 0, PartyAction.mount.id);
         } else {
            defaultAction = new ButtonAction(0, 0, PartyAction.attack.id);
         }
      } else {
         defaultAction = new ButtonAction(0, 0, PartyAction.move.id);
      }

      this.actions[0] = defaultAction;
      this.moveActionButtons();
   }

   public void moveActionButtons() {
      for(int i = 0; i < this.actions.length; ++i) {
         this.actions[i].xPosition = super.xPosition + 16 + i * 18;
         this.actions[i].yPosition = super.yPosition + 1;
      }

   }

   public void drawButton(Minecraft mc, int x, int y) {
      if(this.pressed) {
         super.xPosition = x + this.xOffset;
         super.yPosition = y + this.yOffset;
         this.moveActionButtons();
      }

      Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.guiButtonsTexture);
      short textureX = 128;
      short textureY = 128;
      if(this.renderEntity) {
         this.drawTexturedModalRect(super.xPosition, super.yPosition, textureX, textureY + 20, super.width, super.height);
         this.drawTexturedModalRect(super.xPosition, super.yPosition, textureX, textureY, (int)((float)super.width * this.getEntity().getHealth() / this.getEntity().getMaxHealth()), super.height);
      } else {
         this.drawTexturedModalRect(super.xPosition, super.yPosition, textureX, textureY + 60, super.width, super.height);
      }

      if(this.isSelected) {
         this.drawTexturedModalRect(super.xPosition, super.yPosition, textureX, textureY + 40, super.width, super.height);
      }

      this.drawSubButtons(this.actions, x, y);
      if(this.isMouseOverExtensionButton(x, y)) {
         GL11.glColor4f(0.8F, 0.8F, 0.8F, 1.0F);
      } else {
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

      int textureX1 = 128 + super.width;
      textureY = 128;
      this.drawTexturedModalRect(super.xPosition + 90, super.yPosition + 9, textureX1, textureY, 7, 7);
      if(this.extended) {
         this.drawSubButtons(this.allActions, x, y);
      }

      if(this.holdingButton) {
         ButtonAction button = this.allActions[this.buttonHeld];
         this.drawIcon(224 + button.getIcon(), x, y);
      }

      if(this.renderEntity) {
         GL11.glPushMatrix();
         GL11.glTranslatef((float)(super.xPosition + super.width - 8), (float)(super.yPosition + 3), 0.0F);
         GL11.glScalef(0.5F, 0.5F, 0.5F);
         Minecraft.getMinecraft().fontRenderer.drawString(this.getEntity().potionCount + "", 0, 0, 16777215);
         GL11.glPopMatrix();
         GuiHumanBase.drawEntity(this.getEntity(), super.xPosition + 8, super.yPosition + super.height - 2, 8.0F);
      }

   }

   public boolean mousePressed(Minecraft mc, int x, int y) {
      boolean b = super.mousePressed(mc, x, y);
      if(b) {
         boolean i = false;

         for(int i1 = 0; i1 < this.actions.length; ++i1) {
            if(this.actions[i1].isMouseOver(x, y)) {
               i = true;
            }
         }

         if(this.isMouseOverExtensionButton(x, y)) {
            i = true;
         }

         if(!i) {
            this.pressed = true;
            this.xOffset = super.xPosition - x;
            this.yOffset = super.yPosition - y;
         }
      } else if(this.extended) {
         for(int var7 = 0; var7 < this.allActions.length; ++var7) {
            if(this.allActions[var7].isMouseOver(x, y)) {
               b = true;
               this.holdingButton = true;
               this.buttonHeld = var7;
            }
         }
      }

      return b;
   }

   public void mouseReleased(int x, int y) {
      super.mouseReleased(x, y);
      boolean holdingButton = this.holdingButton;
      this.holdingButton = false;
      if(this.pressed) {
         this.pressed = false;
      } else {
         int i;
         for(i = 0; i < this.actions.length; ++i) {
            if(this.actions[i].isMouseOver(x, y)) {
               if(holdingButton && this.allActions != null && i != 0) {
                  ButtonAction xPos = this.allActions[this.buttonHeld];
                  this.actions[i] = new ButtonAction(xPos.xPosition, xPos.yPosition, xPos.action.id);
                  this.moveActionButtons();
                  return;
               }

               this.sendActionToServer(this.actions[i]);
            }
         }

         if(this.extended) {
            for(i = 0; i < this.allActions.length; ++i) {
               if(this.allActions[i].isMouseOver(x, y)) {
                  this.sendActionToServer(this.allActions[i]);
               }
            }
         }

         if(this.isMouseOverExtensionButton(x, y)) {
            this.extended = !this.extended;
            if(this.extended) {
               this.allActions = new ButtonAction[PartyAction.actions.size()];

               for(i = 0; i < this.allActions.length; ++i) {
                  int var7 = super.xPosition + i % 6 * 18;
                  int yPos = super.yPosition + 20 + 18 * (i / 6);
                  this.allActions[i] = new ButtonAction(var7, yPos, ((PartyAction)PartyAction.actions.get(i)).id);
               }
            } else {
               this.allActions = null;
            }
         }
      }

   }

   public void sendActionToServer(ButtonAction action) {
      EntityHumanBase[] arr$ = this.entity;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         EntityHumanBase e = arr$[i$];
         PacketPartyMemberAction packet = new PacketPartyMemberAction(e, action.action, this.playerMop, Minecraft.getMinecraft().thePlayer);
         ChocolateQuest.channel.sendPaquetToServer(packet);
      }

   }

   public void drawIcon(int icon, int xPos, int yPos) {
      byte ICONS_PER_ROW = 16;
      this.drawTexturedModalRect(xPos, yPos, icon % ICONS_PER_ROW * 16, icon / ICONS_PER_ROW * 16, 16, 16);
   }

   public String getHoveringText(int x, int y) {
      int i;
      for(i = 0; i < this.actions.length; ++i) {
         if(this.actions[i].isMouseOver(x, y)) {
            return this.actions[i].getName();
         }
      }

      if(this.extended) {
         for(i = 0; i < this.allActions.length; ++i) {
            if(this.allActions[i].isMouseOver(x, y)) {
               return this.allActions[i].getName();
            }
         }
      }

      return null;
   }

   public boolean isMouseOverExtensionButton(int x, int y) {
      return x >= super.xPosition + 90 && y >= super.yPosition + 9 && x < super.xPosition + 90 + 7 && y < super.yPosition + 9 + 7;
   }

   public void drawSubButtons(ButtonAction[] buttons, int x, int y) {
      for(int i = 0; i < buttons.length; ++i) {
         if(buttons[i].isMouseOver(x, y)) {
            GL11.glColor4f(0.8F, 0.8F, 0.8F, 1.0F);
         } else {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         }

         this.drawIcon(224 + buttons[i].getIcon(), buttons[i].xPosition, buttons[i].yPosition);
      }

   }

   public EntityHumanBase getEntity() {
      return this.entity[0];
   }
}
