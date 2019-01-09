package com.chocolate.chocolateQuest.API;

import com.chocolate.chocolateQuest.API.DungeonBase;
import java.util.ArrayList;

public class DungeonRegister {

   public static ArrayList dungeonList;


   public static void addDungeon(DungeonBase dungeon) {
      if(dungeonList == null) {
         dungeonList = new ArrayList();
      }

      dungeonList.add(dungeon);
   }
}
