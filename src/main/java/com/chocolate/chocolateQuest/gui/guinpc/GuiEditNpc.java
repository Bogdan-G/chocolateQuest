package com.chocolate.chocolateQuest.gui.guinpc;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.gui.GuiButtonDisplayString;
import com.chocolate.chocolateQuest.gui.GuiButtonIcon;
import com.chocolate.chocolateQuest.gui.GuiButtonMultiOptions;
import com.chocolate.chocolateQuest.gui.GuiButtonSlider;
import com.chocolate.chocolateQuest.gui.GuiButtonTextBoxInteger;
import com.chocolate.chocolateQuest.gui.GuiHumanBase;
import com.chocolate.chocolateQuest.gui.guinpc.GuiLinked;
import com.chocolate.chocolateQuest.gui.guinpc.GuiScrollOptions;
import com.chocolate.chocolateQuest.misc.EnumRace;
import com.chocolate.chocolateQuest.misc.EnumVoice;
import com.chocolate.chocolateQuest.packets.PacketEditNPC;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.ResourceLocation;

public class GuiEditNpc extends GuiLinked {

   EntityHumanNPC npc;
   GuiTextField name;
   GuiTextField displayName;
   GuiTextField texture;
   GuiButton textureFile;
   GuiScrollOptions textureFileSelector;
   GuiButtonMultiOptions model;
   GuiButtonSlider size;
   GuiButtonSlider colorRed;
   GuiButtonSlider colorGreen;
   GuiButtonSlider colorBlue;
   GuiButtonTextBoxInteger buttonReputationDeath;
   GuiButtonTextBoxInteger buttonReputationToAttack;
   GuiTextField faction;
   GuiButtonMultiOptions gender;
   GuiButtonMultiOptions usePlayerTexture;
   GuiButtonMultiOptions blocksMovement;
   GuiButtonMultiOptions visibleName;
   GuiButtonMultiOptions canPickLoot;
   GuiButtonMultiOptions shouldTargetMonsters;
   GuiButtonMultiOptions canTeleport;
   GuiButtonMultiOptions voice;
   GuiButtonTextBoxInteger buttonHealth;
   GuiButtonTextBoxInteger buttonSpeed;
   GuiButtonTextBoxInteger buttonHomeX;
   GuiButtonTextBoxInteger buttonHomeY;
   GuiButtonTextBoxInteger buttonHomeZ;
   GuiButtonTextBoxInteger buttonHomeDistance;
   int playSound = 99;
   Random rand = new Random();


   public GuiEditNpc(EntityHumanNPC npc) {
      this.npc = npc;
      super.maxScrollAmmount = 110;
   }

   public void initGui() {
      super.initGui();
      byte x = 25;
      byte y = 10;
      short buttonWidth = 200;
      byte buttonHeight = 20;
      byte buttonSeparation = 5;
      int buttonsGridWidth = buttonWidth / 2 + buttonSeparation;
      GuiButtonDisplayString button = new GuiButtonDisplayString(0, x, y, buttonWidth, buttonHeight, "Name (Identifier)");
      super.buttonList.add(button);
      int y1 = y + button.height;
      this.name = new GuiTextField(super.fontRendererObj, x, y1, buttonWidth, buttonHeight);
      this.name.setText(this.npc.name);
      super.textFieldList.add(this.name);
      byte colorSeparation = 70;
      button = new GuiButtonDisplayString(0, x + buttonWidth + buttonSeparation + colorSeparation, y1 - button.height, 108, buttonHeight, "Color");
      super.buttonList.add(button);
      this.colorRed = new GuiButtonSlider(0, x + buttonWidth + buttonSeparation + colorSeparation, y1, "Red", BDHelper.getColorRed(this.npc.color), 255, true);
      super.buttonList.add(this.colorRed);
      this.colorGreen = new GuiButtonSlider(0, x + buttonWidth + buttonSeparation + colorSeparation, y1 + buttonHeight, "Green", BDHelper.getColorGreen(this.npc.color), 255, true);
      super.buttonList.add(this.colorGreen);
      this.colorBlue = new GuiButtonSlider(0, x + buttonWidth + buttonSeparation + colorSeparation, y1 + buttonHeight * 2, "Blue", BDHelper.getColorBlue(this.npc.color), 255, true);
      super.buttonList.add(this.colorBlue);
      y1 += this.name.height + buttonSeparation;
      button = new GuiButtonDisplayString(0, x, y1, buttonWidth, buttonHeight, "DisplayName");
      super.buttonList.add(button);
      y1 += button.height - 5;
      this.displayName = new GuiTextField(super.fontRendererObj, x, y1, buttonWidth, buttonHeight);
      this.displayName.setText(this.npc.displayName);
      super.textFieldList.add(this.displayName);
      y1 += this.displayName.height + buttonSeparation;
      button = new GuiButtonDisplayString(0, x, y1, buttonWidth, buttonHeight, "Texture");
      super.buttonList.add(button);
      y1 += button.height - 5;
      this.texture = new GuiTextField(super.fontRendererObj, x, y1, buttonWidth, buttonHeight);
      this.texture.setMaxStringLength(50);
      this.texture.setText(this.npc.texture);
      super.textFieldList.add(this.texture);
      this.textureFile = new GuiButton(0, x + buttonWidth + 1, y1, buttonHeight, buttonHeight, "+");
      super.buttonList.add(this.textureFile);
      button = new GuiButtonDisplayString(0, x + buttonWidth + buttonSeparation + buttonHeight, y1 - button.height, 50, buttonHeight, "Texture type");
      super.buttonList.add(button);
      this.usePlayerTexture = new GuiButtonMultiOptions(0, x + buttonWidth + buttonSeparation + buttonHeight, y1, 50, buttonHeight, new String[]{"Player", "Entity"}, this.npc.hasPlayerTexture?0:1);
      super.buttonList.add(this.usePlayerTexture);
      button = new GuiButtonDisplayString(0, x + buttonWidth + buttonSeparation * 2 + buttonHeight + 80, y1 - button.height, 50, buttonHeight, "Gender");
      super.buttonList.add(button);
      this.gender = new GuiButtonMultiOptions(0, x + buttonWidth + buttonSeparation * 2 + buttonHeight + 80, y1, 50, buttonHeight, new String[]{"Male", "Female"}, this.npc.isMale?0:1);
      super.buttonList.add(this.gender);
      y1 += this.texture.height + buttonSeparation;
      String[] fileNames = this.getTextureNames();
      this.textureFileSelector = new GuiScrollOptions(super.STATIC_ID, 30, 30, super.width - 60, super.height - 60, fileNames, super.fontRendererObj, 0);
      button = new GuiButtonDisplayString(0, x, y1, buttonWidth, buttonHeight, "Race");
      super.buttonList.add(button);
      y1 += button.height - 5;
      this.model = new GuiButtonMultiOptions(0, x, y1, buttonWidth, buttonHeight, EnumRace.getNames(), this.npc.modelType);
      super.buttonList.add(this.model);
      this.size = new GuiButtonSlider(0, x + buttonWidth + buttonSeparation, y1, "Size", this.npc.size * 0.5F, 20, true);
      this.size.width = buttonWidth;
      super.buttonList.add(this.size);
      y1 += this.model.height + buttonSeparation;
      button = new GuiButtonDisplayString(0, x, y1, buttonWidth, buttonHeight, "Faction");
      super.buttonList.add(button);
      button = new GuiButtonDisplayString(0, x + buttonsGridWidth * 2, y1, buttonWidth / 2, buttonHeight, "Reputation friendly");
      super.buttonList.add(button);
      button = new GuiButtonDisplayString(0, x + buttonsGridWidth * 3, y1, buttonWidth / 2, buttonHeight, "Reputation on kill");
      super.buttonList.add(button);
      y1 += button.height - 5;
      this.faction = new GuiTextField(super.fontRendererObj, x, y1, buttonWidth, buttonHeight);
      this.faction.setText(this.npc.entityTeam);
      super.textFieldList.add(this.faction);
      this.buttonReputationToAttack = new GuiButtonTextBoxInteger(0, x + buttonsGridWidth * 2, y1, buttonWidth / 2, buttonHeight, super.fontRendererObj, this.npc.repToAttack);
      super.buttonList.add(this.buttonReputationToAttack);
      super.textFieldList.add(this.buttonReputationToAttack.textbox);
      this.buttonReputationDeath = new GuiButtonTextBoxInteger(0, x + buttonsGridWidth * 3, y1, buttonWidth / 2, buttonHeight, super.fontRendererObj, this.npc.repOnDeath);
      super.buttonList.add(this.buttonReputationDeath);
      super.textFieldList.add(this.buttonReputationDeath.textbox);
      y1 += this.model.height + buttonSeparation;
      button = new GuiButtonDisplayString(0, x, y1, buttonWidth / 2, buttonHeight, "Is invincible");
      super.buttonList.add(button);
      button = new GuiButtonDisplayString(0, x + buttonWidth / 2 + buttonSeparation, y1, buttonWidth / 2, buttonHeight, "Visible name");
      super.buttonList.add(button);
      button = new GuiButtonDisplayString(0, x + buttonsGridWidth * 2, y1, buttonWidth / 2, buttonHeight, "Can pick loot");
      super.buttonList.add(button);
      button = new GuiButtonDisplayString(0, x + buttonsGridWidth * 3, y1, buttonWidth / 2, buttonHeight, "Target monsters");
      super.buttonList.add(button);
      y1 += button.height;
      String[] names = new String[]{"Yes", "No"};
      this.blocksMovement = new GuiButtonMultiOptions(0, x, y1, buttonWidth / 2, buttonHeight, names, this.npc.isInvincible?0:1);
      super.buttonList.add(this.blocksMovement);
      this.visibleName = new GuiButtonMultiOptions(0, x + buttonWidth / 2 + buttonSeparation, y1, buttonWidth / 2, buttonHeight, names, this.npc.getAlwaysRenderNameTag()?0:1);
      super.buttonList.add(this.visibleName);
      this.canPickLoot = new GuiButtonMultiOptions(0, x + buttonsGridWidth * 2, y1, buttonWidth / 2, buttonHeight, names, this.npc.canPickUpLoot()?0:1);
      super.buttonList.add(this.canPickLoot);
      this.shouldTargetMonsters = new GuiButtonMultiOptions(0, x + buttonsGridWidth * 3, y1, buttonWidth / 2, buttonHeight, names, this.npc.targetMobs?0:1);
      super.buttonList.add(this.shouldTargetMonsters);
      y1 += this.model.height + buttonSeparation;
      button = new GuiButtonDisplayString(0, x, y1, buttonWidth / 2, buttonHeight, "Voice");
      super.buttonList.add(button);
      button = new GuiButtonDisplayString(0, x + buttonsGridWidth, y1, buttonWidth / 2, buttonHeight, "Can teleport");
      super.buttonList.add(button);
      button = new GuiButtonDisplayString(0, x + buttonsGridWidth * 2, y1, buttonWidth / 2, buttonHeight, "Health");
      super.buttonList.add(button);
      button = new GuiButtonDisplayString(0, x + buttonsGridWidth * 3, y1, buttonWidth / 2, buttonHeight, "Speed");
      super.buttonList.add(button);
      y1 += button.height;
      this.voice = new GuiButtonMultiOptions(0, x, y1, buttonWidth / 2 - 13, buttonHeight, EnumVoice.getNames(), this.npc.voice);
      super.buttonList.add(this.voice);
      GuiButtonIcon play = new GuiButtonIcon(this.playSound, x + 88, y1 + 2, 13.0F, 5.0F, 1.0F, 1.0F, "");
      super.buttonList.add(play);
      this.canTeleport = new GuiButtonMultiOptions(0, x + buttonsGridWidth, y1, buttonWidth / 2, buttonHeight, names, this.npc.canTeleport?0:1);
      super.buttonList.add(this.canTeleport);
      int health = (int)this.npc.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue();
      this.buttonHealth = new GuiButtonTextBoxInteger(0, x + buttonsGridWidth * 2, y1, buttonWidth / 2, buttonHeight, super.fontRendererObj, health);
      super.buttonList.add(this.buttonHealth);
      super.textFieldList.add(this.buttonHealth.textbox);
      int speed = (int)(this.npc.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() * 1000.0D);
      this.buttonSpeed = new GuiButtonTextBoxInteger(0, x + buttonsGridWidth * 3, y1, buttonWidth / 2, buttonHeight, super.fontRendererObj, speed);
      super.buttonList.add(this.buttonSpeed);
      super.textFieldList.add(this.buttonSpeed.textbox);
      y1 += this.model.height + buttonSeparation;
      button = new GuiButtonDisplayString(0, x, y1, buttonWidth / 2, buttonHeight, "Home X");
      super.buttonList.add(button);
      button = new GuiButtonDisplayString(0, x + buttonsGridWidth, y1, buttonWidth / 2, buttonHeight, "Home Y");
      super.buttonList.add(button);
      button = new GuiButtonDisplayString(0, x + buttonsGridWidth * 2, y1, buttonWidth / 2, buttonHeight, "Home Z");
      super.buttonList.add(button);
      button = new GuiButtonDisplayString(0, x + buttonsGridWidth * 3, y1, buttonWidth / 2, buttonHeight, "Home radio");
      super.buttonList.add(button);
      y1 += this.model.height + buttonSeparation;
      ChunkCoordinates coords = this.npc.getHomePosition();
      this.buttonHomeX = new GuiButtonTextBoxInteger(0, x, y1, buttonWidth / 2, buttonHeight, super.fontRendererObj, coords.posX);
      super.buttonList.add(this.buttonHomeX);
      super.textFieldList.add(this.buttonHomeX.textbox);
      this.buttonHomeY = new GuiButtonTextBoxInteger(0, x + buttonsGridWidth * 1, y1, buttonWidth / 2, buttonHeight, super.fontRendererObj, coords.posY);
      super.buttonList.add(this.buttonHomeY);
      super.textFieldList.add(this.buttonHomeY.textbox);
      this.buttonHomeZ = new GuiButtonTextBoxInteger(0, x + buttonsGridWidth * 2, y1, buttonWidth / 2, buttonHeight, super.fontRendererObj, coords.posZ);
      super.buttonList.add(this.buttonHomeZ);
      super.textFieldList.add(this.buttonHomeZ.textbox);
      this.buttonHomeDistance = new GuiButtonTextBoxInteger(0, x + buttonsGridWidth * 3, y1, buttonWidth / 2, buttonHeight, super.fontRendererObj, (int)this.npc.getHomeDistance());
      super.buttonList.add(this.buttonHomeDistance);
      super.textFieldList.add(this.buttonHomeDistance.textbox);
   }

   public String[] getTextureNames() {
      String[] fileNames = new String[]{"pirate.png"};
      Class clazz = ChocolateQuest.class;
      String path = "assets/chocolatequest/textures/entity/biped";
      URL dirURL = clazz.getResource(path);
      String file;
      if(dirURL == null) {
         file = clazz.getName().replace(".", "/") + ".class";
         dirURL = clazz.getClassLoader().getResource(file);
      }

      if(dirURL.getProtocol().equals("jar")) {
         try {
            file = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!"));
            JarFile files = new JarFile(URLDecoder.decode(file, "UTF-8"));
            Enumeration i = files.entries();
            ArrayList list = new ArrayList();

            while(i.hasMoreElements()) {
               JarEntry size = (JarEntry)i.nextElement();
               if(!size.isDirectory()) {
                  String i1 = size.getName();
                  if(i1.startsWith(path)) {
                     String entryName = i1.substring(path.length() + 1);
                     list.add(entryName);
                  }
               }
            }

            int var17 = list.size();
            if(list.size() > 0) {
               fileNames = new String[var17];

               for(int var18 = 0; var18 < var17; ++var18) {
                  fileNames[var18] = (String)list.get(var18);
               }
            }
         } catch (UnsupportedEncodingException var12) {
            var12.printStackTrace();
         } catch (IOException var13) {
            var13.printStackTrace();
         }
      } else {
         File var14 = new File(dirURL.getFile());
         File[] var15 = var14.listFiles();
         if(var15 != null && var15.length > 0) {
            fileNames = new String[var15.length];

            for(int var16 = 0; var16 < var15.length; ++var16) {
               fileNames[var16] = var15[var16].getName();
            }
         }
      }

      return fileNames;
   }

   public void drawScreen(int x, int y, float fl) {
      super.drawScreen(x, y, fl);
      if(super.frontButton == null) {
         short posX = 230;
         int posY = -super.scrollAmmount + 20;
         Gui.drawRect(posX, posY + 3, posX + 58, posY + 76, -16777216);
         GuiHumanBase.drawEntity(this.npc, posX + 28, posY + 60);
      }

   }

   protected void actionPerformed(GuiButton button) {
      super.actionPerformed(button);
      String voice;
      if(button == this.textureFileSelector) {
         voice = ((GuiScrollOptions)button).getSelected();
         if(voice != null) {
            this.texture.setText(voice);
         }

         this.setFrontButton((GuiButton)null);
      }

      if(button == this.textureFile) {
         this.setFrontButton(this.textureFileSelector);
      }

      if(button.id == this.playSound) {
         int voiceID = this.rand.nextInt(3);
         switch(voiceID) {
         case 1:
            voice = EnumVoice.getVoice(this.npc.voice).hurt;
            break;
         case 2:
            voice = EnumVoice.getVoice(this.npc.voice).death;
            break;
         default:
            voice = EnumVoice.getVoice(this.npc.voice).say;
         }

         super.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation(voice), 1.0F));
         super.mc.theWorld.playSoundAtEntity(super.mc.thePlayer, "chocolateQuest:handgun", 1.2F, 1.0F);
      }

      this.updateNPC();
   }

   public void onGuiClosed() {
      this.updateNPC();
      PacketEditNPC packet = new PacketEditNPC(this.npc, (byte)0);
      ChocolateQuest.channel.sendPaquetToServer(packet);
      super.onGuiClosed();
   }

   public void updateNPC() {
      this.npc.name = this.name.getText();
      this.npc.displayName = this.displayName.getText();
      this.npc.texture = this.texture.getText();
      this.npc.modelType = this.model.value;
      this.npc.size = this.size.sliderValue * 2.0F;
      this.npc.isMale = this.gender.value == 0;
      float red = this.colorRed.sliderValue;
      float green = this.colorGreen.sliderValue;
      float blue = this.colorBlue.sliderValue;
      int color = ((int)Math.min(red * 256.0F, 255.0F) & 16777215) << 16 | (int)Math.min(green * 256.0F, 255.0F) << 8 | (int)Math.min(blue * 256.0F, 255.0F) << 0;
      this.npc.color = color;
      this.npc.entityTeam = this.faction.getText();
      this.npc.repToAttack = BDHelper.getIntegerFromString(this.buttonReputationToAttack.textbox.getText());
      this.npc.repOnDeath = BDHelper.getIntegerFromString(this.buttonReputationDeath.textbox.getText());
      this.npc.hasPlayerTexture = this.usePlayerTexture.value == 0;
      this.npc.textureLocationPlayer = null;
      this.npc.resize();
      this.npc.isInvincible = this.blocksMovement.value == 0;
      this.npc.setCanPickUpLoot(this.canPickLoot.value == 0);
      this.npc.setAlwaysRenderNameTag(this.visibleName.value == 0);
      this.npc.targetMobs = this.shouldTargetMonsters.value == 0;
      this.npc.canTeleport = this.canTeleport.value == 0;
      this.npc.voice = this.voice.value;
      String health = this.buttonHealth.textbox.getText();
      this.npc.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((double)BDHelper.getIntegerFromString(health));
      String speed = this.buttonSpeed.textbox.getText();
      this.npc.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(Math.min(0.5D, (double)BDHelper.getIntegerFromString(speed) * 0.001D));
      int x = BDHelper.getIntegerFromString(this.buttonHomeX.textbox.getText());
      int y = BDHelper.getIntegerFromString(this.buttonHomeY.textbox.getText());
      int z = BDHelper.getIntegerFromString(this.buttonHomeZ.textbox.getText());
      int dist = BDHelper.getIntegerFromString(this.buttonHomeDistance.textbox.getText());
      this.npc.setHomeArea(x, y, z, dist);
   }
}
