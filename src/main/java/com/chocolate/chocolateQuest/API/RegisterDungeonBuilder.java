package com.chocolate.chocolateQuest.API;

import com.chocolate.chocolateQuest.API.BuilderBase;
import java.util.ArrayList;
import java.util.Iterator;

public class RegisterDungeonBuilder {

   public static ArrayList builderList;


   public static void addDungeonBuilder(BuilderBase mob) {
      if(builderList == null) {
         builderList = new ArrayList();
      }

      builderList.add(mob);
   }

   public static void addMob(BuilderBase mob, int position) {
      if(builderList == null) {
         builderList = new ArrayList();
      }

      builderList.add(mob);
   }

   public static BuilderBase getBuilderByName(String s) {
      try {
         Iterator e = builderList.iterator();

         while(e.hasNext()) {
            BuilderBase builder = (BuilderBase)e.next();
            if(builder.getName().equals(s)) {
               return (BuilderBase)builder.getClass().newInstance();
            }
         }
      } catch (InstantiationException var3) {
         var3.printStackTrace();
      } catch (IllegalAccessException var4) {
         var4.printStackTrace();
      }

      return null;
   }
}
