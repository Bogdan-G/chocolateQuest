package com.chocolate.chocolateQuest.entity.ai;


class SchematicStorage {

   public String name;
   public String position;
   int buildSpeed;


   public SchematicStorage(String name, String position, int buildSpeed) {
      this.name = name;
      this.position = position;
      this.buildSpeed = buildSpeed;
   }
}
