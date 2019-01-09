package com.chocolate.chocolateQuest.entity.mob.registry;

import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import java.util.ArrayList;
import java.util.Iterator;

public class RegisterDungeonMobs {

   public static ArrayList<DungeonMonstersBase> mobList;


   public static void addMob(DungeonMonstersBase mob) {
      if(mobList == null) {
         mobList = new ArrayList();
      }

      mobList.add(mob);
      mob.setID(mobList.indexOf(mob));
   }

   public static void addMob(DungeonMonstersBase mob, int position) {
      if(mobList == null) {
         mobList = new ArrayList();
      }

      mobList.add(mob);
      mob.setID(mobList.indexOf(mob));
   }

   public static int getMonsterId(String s) {
      int id = 0;
      Iterator<DungeonMonstersBase> i$ = mobList.iterator();

      while(i$.hasNext()) {
         DungeonMonstersBase mob = i$.next();
         if(mob.getEntityName().equals(s)) {
            id = mob.getID();
         }
      }

      return id;
   }

   public static int getMonster(String s) {
      int id = 1;
      Iterator<DungeonMonstersBase> i$ = mobList.iterator();

      while(i$.hasNext()) {
         DungeonMonstersBase mob = i$.next();
         if(mob.getEntityName().equals(s)) {
            id = mob.getID();
         }
      }

      return id;
   }
}
