package com.chocolate.chocolateQuest.quest.worldManager;

import com.chocolate.chocolateQuest.builder.schematic.Schematic;

class SchematicStorage {

   Schematic schematic;
   int posX;
   int posY;
   int posZ;


   public SchematicStorage(int posX, int posY, int posZ) {
      this.posX = posX;
      this.posY = posY;
      this.posZ = posZ;
   }
}
