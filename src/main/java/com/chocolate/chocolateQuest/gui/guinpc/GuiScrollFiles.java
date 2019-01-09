package com.chocolate.chocolateQuest.gui.guinpc;

import com.chocolate.chocolateQuest.gui.guinpc.GuiScrollOptions;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

@SideOnly(Side.CLIENT)
public class GuiScrollFiles extends GuiScrollOptions {

   File file;
   public File[] files;
   public String prevFile;


   public GuiScrollFiles(int id, int posX, int posY, int width, int height, File file, FontRenderer font) {
      this(id, posX, posY, width, height, file, font, 4);
   }

   public GuiScrollFiles(int id, int posX, int posY, int width, int height, File file, FontRenderer font, int values) {
      super(id, posX, posY, width, height, font);
      this.prevFile = "";
      super.MAX_ENTRIES_FINAL = values;
      this.file = file;
      this.files = file.listFiles();
      if(this.files != null && this.files.length > 0) {
         super.modeNames = new String[this.files.length];

         for(int i = 0; i < this.files.length; ++i) {
            super.modeNames[i] = this.files[i].getName();
            if(this.files[i].isDirectory()) {
               super.modeNames[i] = super.modeNames[i] + "/";
            }
         }
      }

      this.setModeNames(super.modeNames);
   }

   public void updateFiles() {
      this.files = this.file.listFiles();
      if(this.files != null && this.files.length > 0) {
         super.modeNames = new String[this.files.length];

         for(int i = 0; i < this.files.length; ++i) {
            String name = this.files[i].getName();
            if(name.contains(".")) {
               name = name.substring(0, name.lastIndexOf("."));
            }

            super.modeNames[i] = name;
         }
      }

   }

   public File getSelectedFile() {
      return super.selectedMode >= 0 && super.selectedMode < this.files.length?this.files[super.selectedMode]:null;
   }

   public String getFileString() {
      File file = this.getSelectedFile();
      return file == null?null:(file.isDirectory()?null:this.prevFile + file.getName());
   }

   public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
      boolean pressed = super.mousePressed(par1Minecraft, par2, par3);
      if(this.files.length > super.selectedMode && this.files[super.selectedMode].isDirectory()) {
         this.prevFile = this.prevFile + this.files[super.selectedMode].getName() + "/";
         this.file = this.files[super.selectedMode];
         this.updateFiles();
         return false;
      } else {
         return pressed;
      }
   }
}
